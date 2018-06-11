package com.example.tastymeals.domain.repository;

import com.example.tastymeals.domain.db.dao.IngredientDao;
import com.example.tastymeals.domain.db.dao.RecipeDao;
import com.example.tastymeals.domain.model.Ingredient;
import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.model.Resource;
import com.example.tastymeals.domain.network.RecipeService;

import java.util.List;

import io.reactivex.Observable;

public class RecipeRepository {

	private final RecipeDao recipeDao;
	private final IngredientDao ingredientDao;
	private final RecipeService service;

	public RecipeRepository(RecipeDao recipeDao, IngredientDao ingredientDao, RecipeService service) {
		this.recipeDao = recipeDao;
		this.ingredientDao = ingredientDao;
		this.service = service;
	}

	public Observable<Resource<Recipe>> getRecipe(long recipeId) {
		return service.getRecipe(recipeId)
				.map(response -> Resource.fromSuccessResponse(response.getRecipe()))
				.toObservable()
				.onErrorResumeNext(throwable -> {
					return Observable.fromCallable(() ->
							Resource.fromCache(getLocalRecipe(recipeId), throwable));
				})
				.doOnNext(response -> {
					Recipe recipe = response.getResult();

					recipeDao.save(recipe);
					ingredientDao.saveAll(recipe.getIngredients());
				});
	}

	private Recipe getLocalRecipe(long recipeId) {
		Recipe recipe = recipeDao.getById(recipeId);

		if (recipe != null) {
			List<Ingredient> ingredients = ingredientDao.getByRecipeId(recipe.getId());
			recipe.setIngredients(ingredients);
		}

		return recipe;
	}
}
