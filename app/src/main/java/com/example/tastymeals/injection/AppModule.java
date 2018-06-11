package com.example.tastymeals.injection;

import com.example.tastymeals.domain.db.dao.CategoryDao;
import com.example.tastymeals.domain.db.dao.IngredientDao;
import com.example.tastymeals.domain.db.dao.RecipeDao;
import com.example.tastymeals.domain.db.dao.ShortRecipeDao;
import com.example.tastymeals.domain.network.CategoryService;
import com.example.tastymeals.domain.network.RecipeService;
import com.example.tastymeals.domain.network.ShortRecipeService;
import com.example.tastymeals.domain.repository.CategoryRepository;
import com.example.tastymeals.domain.repository.RecipeRepository;
import com.example.tastymeals.domain.repository.ShortRecipeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

	public AppModule() {
	}

	@Provides
	@Singleton
	CategoryRepository provideCategoryRepository(CategoryDao dao, CategoryService service) {
		return new CategoryRepository(dao, service);
	}

	@Provides
	@Singleton
	ShortRecipeRepository provideShortRecipeRepository(ShortRecipeDao dao, ShortRecipeService service) {
		return new ShortRecipeRepository(dao, service);
	}

	@Provides
	@Singleton
	RecipeRepository provideRecipeRepository(RecipeDao recipeDao, IngredientDao ingredientDao, RecipeService service) {
		return new RecipeRepository(recipeDao, ingredientDao, service);
	}
}
