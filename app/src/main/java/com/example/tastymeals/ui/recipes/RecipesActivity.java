package com.example.tastymeals.ui.recipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tastymeals.R;
import com.example.tastymeals.domain.model.Category;

public class RecipesActivity extends AppCompatActivity {

	private static final String EXTRA_CATEGORY = "category";

	public static Intent createStartIntent(Context context, Category category) {
		Intent result = new Intent(context, RecipesActivity.class);
		result.putExtra(EXTRA_CATEGORY, category);
		return result;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.core_base_activity);

		Category category = getIntent().getParcelableExtra(EXTRA_CATEGORY);

		initActionBar(category);

		RecipesFragment fragment = RecipesFragment.newInstance(category);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, fragment)
				.commit();
	}

	private void initActionBar(Category category) {
		setSupportActionBar(findViewById(R.id.tool_bar));

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(category.getName());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
