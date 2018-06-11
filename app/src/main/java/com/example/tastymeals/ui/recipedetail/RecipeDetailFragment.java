package com.example.tastymeals.ui.recipedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tastymeals.R;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Ingredient;
import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.network.ServerUrlBuilder;
import com.example.tastymeals.ui.core.BaseFragment;

import static android.text.TextUtils.isEmpty;

public class RecipeDetailFragment extends BaseFragment {

	private static final String ARG_RECIPE_ID = "recipeId";

	private long recipeId;
	private ImageView photoImageView;
	private TextView titleTextView;
	private TextView instructionsTextView;
	private TextView categoryTextView;
	private TextView areaTextView;
	private TextView videoTextView;
	private TextView sourceTextView;
	private TextView ingredientsTextView;
	private ViewGroup areaLayout;
	private ViewGroup videoLayout;
	private ViewGroup sourceLayout;
	private ViewGroup ingredientsContainer;
	private RecipeDetailViewModel viewModel;

	public static RecipeDetailFragment newInstance(long recipeId) {
		RecipeDetailFragment fragment = new RecipeDetailFragment();

		Bundle args = new Bundle();
		args.putLong(ARG_RECIPE_ID, recipeId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			recipeId = args.getLong(ARG_RECIPE_ID);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		viewModel.start();
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recipedetail_fragment, container, false);

		photoImageView = view.findViewById(R.id.photo);
		titleTextView = view.findViewById(R.id.title);
		instructionsTextView = view.findViewById(R.id.instructions);
		categoryTextView = view.findViewById(R.id.category);
		areaTextView = view.findViewById(R.id.area);
		areaLayout = view.findViewById(R.id.area_layout);
		videoTextView = view.findViewById(R.id.video_link);
		videoLayout = view.findViewById(R.id.video_layout);
		sourceTextView = view.findViewById(R.id.source_link);
		sourceLayout = view.findViewById(R.id.source_layout);
		ingredientsTextView = view.findViewById(R.id.ingredients);
		ingredientsContainer = view.findViewById(R.id.ingredients_container);

		videoTextView.setMovementMethod(LinkMovementMethod.getInstance());
		sourceTextView.setMovementMethod(LinkMovementMethod.getInstance());

		viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);

		viewModel.setRecipeId(recipeId);

		viewModel.getState().observe(this, this::setViewState);
		viewModel.getEmptyText().observe(this, this::setEmptyText);
		viewModel.getErrorText().observe(this, this::setErrorText);
		viewModel.getCategoryAndRecipe().observe(this,
				catAndRecipe -> setCategoryAndRecipe(catAndRecipe.first, catAndRecipe.second));

		return view;
	}

	public void setCategoryAndRecipe(Category category, Recipe recipe) {
		titleTextView.setText(recipe.getName());
		instructionsTextView.setText(recipe.getInstructions().trim());
		categoryTextView.setText(category.getName());

		if (!isEmpty(recipe.getArea())) {
			areaTextView.setText(recipe.getArea());
			areaLayout.setVisibility(View.VISIBLE);
		} else {
			areaLayout.setVisibility(View.GONE);
		}

		if (!isEmpty(recipe.getVideoUrl())) {
			videoTextView.setText(wrapUrl(recipe.getVideoUrl()));
			videoLayout.setVisibility(View.VISIBLE);
		} else {
			videoLayout.setVisibility(View.GONE);
		}

		if (!isEmpty(recipe.getSourceUrl())) {
			sourceTextView.setText(wrapUrl(recipe.getSourceUrl()));
			sourceLayout.setVisibility(View.VISIBLE);
		} else {
			sourceLayout.setVisibility(View.GONE);
		}

		Drawable placeholder = new PlaceholderDrawable(getContext(), R.drawable.ic_placeholder_white_144dp, 0.4f);
		if (!isEmpty(recipe.getImageUrl())) {
			Glide.with(getContext())
					.load(recipe.getImageUrl())
					.placeholder(placeholder)
					.dontAnimate()
					.into(photoImageView);
		} else {
			photoImageView.setImageDrawable(placeholder);
		}

		ingredientsContainer.removeAllViews();

		if (recipe.getIngredients() != null && recipe.getIngredients().size() != 0) {
			for (Ingredient ingredient : recipe.getIngredients()) {
				View ingredientView = createIngredientView(ingredientsContainer);
				IngredientViewHolder holder = createIngredientViewHolder(ingredientView);

				holder.nameTextView.setText(ingredient.getName());
				holder.measureTextView.setText(ingredient.getQuantity());

				ingredientsContainer.addView(ingredientView);

				String imageUrl = new ServerUrlBuilder()
						.ingredientUrl(ingredient.getName())
						.build();

				Glide.with(getContext())
						.load(imageUrl)
						.placeholder(R.drawable.ic_placeholder_gray_48dp)
						.dontAnimate()
						.into(holder.iconImageView);
			}

			ingredientsTextView.setVisibility(View.VISIBLE);
			ingredientsContainer.setVisibility(View.VISIBLE);
		} else {
			ingredientsTextView.setVisibility(View.GONE);
			ingredientsContainer.setVisibility(View.GONE);
		}
	}

	private CharSequence wrapUrl(String url) {
		return Html.fromHtml("<a href=\"" + url + "\">link</a>");
	}

	private View createIngredientView(ViewGroup parent) {
		return getLayoutInflater().inflate(R.layout.recipedetail_ingredient_item, parent, false);
	}

	private IngredientViewHolder createIngredientViewHolder(View ingredientView) {
		IngredientViewHolder holder = new IngredientViewHolder();

		holder.iconImageView = ingredientView.findViewById(R.id.ingredient_icon);
		holder.nameTextView = ingredientView.findViewById(R.id.ingredient_name);
		holder.measureTextView = ingredientView.findViewById(R.id.ingredient_measure);

		return holder;
	}

	private static class IngredientViewHolder {
		TextView nameTextView;
		TextView measureTextView;
		ImageView iconImageView;
	}
}
