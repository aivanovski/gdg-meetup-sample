package com.example.tastymeals.domain.repository;

import com.example.tastymeals.domain.db.dao.ShortRecipeDao;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Resource;
import com.example.tastymeals.domain.model.ShortRecipe;
import com.example.tastymeals.domain.network.ShortRecipeService;

import java.util.List;

import io.reactivex.Observable;

public class ShortRecipeRepository {

	private final ShortRecipeDao dao;
	private final ShortRecipeService service;

	public ShortRecipeRepository(ShortRecipeDao dao, ShortRecipeService service) {
		this.dao = dao;
		this.service = service;
	}

	public Observable<Resource<List<ShortRecipe>>> getRecipes(Category category) {
		return service.getShortRecipesByCategory(category.getName())
				.map(response -> Resource.fromSuccessResponse(response.getShortRecipes()))
				.toObservable()
				.onErrorResumeNext(throwable -> {
					return Observable.fromCallable(() ->
							Resource.fromCache(dao.getAllByCategoryId(category.getId()), throwable));
				})
				.doOnNext(response -> {
					List<ShortRecipe> recipes = response.getResult();

					for (ShortRecipe recipe : recipes) {
						recipe.setCategoryId(category.getId());
					}

					dao.saveAll(recipes);
				});
	}
}
