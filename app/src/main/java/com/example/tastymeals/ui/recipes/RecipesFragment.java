package com.example.tastymeals.ui.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastymeals.R;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.ShortRecipe;
import com.example.tastymeals.ui.core.BaseFragment;
import com.example.tastymeals.ui.core.adapter.SingleLineWithIconAdapter;
import com.example.tastymeals.ui.recipedetail.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends BaseFragment {

	private static final String ARG_CATEGORY = "category";

	private Category category;
	private SingleLineWithIconAdapter adapter;
	private RecipesViewModel viewModel;
	private SwipeRefreshLayout swipeRefreshLayout;

	public static RecipesFragment newInstance(Category category) {
		RecipesFragment fragment = new RecipesFragment();

		Bundle args = new Bundle();
		args.putParcelable(ARG_CATEGORY, category);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			category = args.getParcelable(ARG_CATEGORY);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		viewModel.start();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recipes_fragment, container, false);

		RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
		swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

		LinearLayoutManager layoutManager =
				new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		DividerItemDecoration dividerItemDecoration =
				new DividerItemDecoration(getContext(), layoutManager.getOrientation());
		adapter = new SingleLineWithIconAdapter(getContext());

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(dividerItemDecoration);
		recyclerView.setAdapter(adapter);

		viewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);

		viewModel.setCategory(category);

		viewModel.getState().observe(this, this::setViewState);
		viewModel.getEmptyText().observe(this, this::setEmptyText);
		viewModel.getErrorText().observe(this, this::setErrorText);
		viewModel.isRefreshing().observe(this, swipeRefreshLayout::setRefreshing);
		viewModel.getRecipes().observe(this, this::setRecipes);
		viewModel.getOpenRecipeEvent().observe(this, this::openRecipeDetails);

		swipeRefreshLayout.setOnRefreshListener(viewModel::refreshData);

		return view;
	}

	public void setRecipes(List<ShortRecipe> recipes) {
		adapter.setItems(createAdapterItems(recipes));
		adapter.setOnItemClickListener(position -> viewModel.showRecipe(recipes.get(position)));
		adapter.notifyDataSetChanged();
	}

	private List<SingleLineWithIconAdapter.Item> createAdapterItems(List<ShortRecipe> recipes) {
		List<SingleLineWithIconAdapter.Item> result = new ArrayList<>();

		for (ShortRecipe recipe : recipes) {
			result.add(new SingleLineWithIconAdapter.Item(recipe.getName(), recipe.getImageUrl()));
		}

		return result;
	}

	private void openRecipeDetails(ShortRecipe recipe) {
		startActivity(RecipeDetailActivity.createStartIntent(getContext(), recipe.getName(), recipe.getId()));
	}
}
