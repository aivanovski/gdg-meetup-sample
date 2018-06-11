package com.example.tastymeals.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tastymeals.R;

public class RecipeDetailActivity extends AppCompatActivity {

	private static final String EXTRA_RECIPE_ID = "recipeId";
	private static final String EXTRA_RECIPE_NAME = "recipeName";

	private long recipeId;
	private String recipeName;

	public static Intent createStartIntent(Context context, String recipeName, long recipeId) {
		Intent result = new Intent(context, RecipeDetailActivity.class);

		result.putExtra(EXTRA_RECIPE_ID, recipeId);
		result.putExtra(EXTRA_RECIPE_NAME, recipeName);

		return result;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.core_base_activity);

		readExtraArgs();
		initActionBar();

		RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipeId);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, fragment)
				.commit();
	}

	private void readExtraArgs() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			recipeId = extras.getLong(EXTRA_RECIPE_ID);
			recipeName = extras.getString(EXTRA_RECIPE_NAME);
		}
	}

	private void initActionBar() {
		setSupportActionBar(findViewById(R.id.tool_bar));

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(recipeName);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeButtonEnabled(true);
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
