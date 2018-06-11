package com.example.tastymeals.injection;

import com.example.tastymeals.ui.categories.CategoriesViewModel;
import com.example.tastymeals.ui.recipedetail.RecipeDetailViewModel;
import com.example.tastymeals.ui.recipes.RecipesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = { AppModule.class, DatabaseModule.class, NetworkModule.class })
@Singleton
public interface AppComponent {

	void inject(RecipesViewModel recipesViewModel);
	void inject(CategoriesViewModel categoriesViewModel);
	void inject(RecipeDetailViewModel recipeDetailViewModel);
}
