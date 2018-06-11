package com.example.tastymeals.domain.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.tastymeals.domain.db.dao.CategoryDao;
import com.example.tastymeals.domain.db.dao.IngredientDao;
import com.example.tastymeals.domain.db.dao.RecipeDao;
import com.example.tastymeals.domain.db.dao.ShortRecipeDao;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Ingredient;
import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.model.ShortRecipe;

@Database(entities = {
		Category.class,
		Recipe.class,
		Ingredient.class,
		ShortRecipe.class
},
		version = 1,
		exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	public static final String DATABASE_NAME = "news.db";

	public abstract CategoryDao getCategoryDao();
	public abstract RecipeDao getRecipeDao();
	public abstract IngredientDao getIngredientDao();
	public abstract ShortRecipeDao getShortRecipeDao();
}
