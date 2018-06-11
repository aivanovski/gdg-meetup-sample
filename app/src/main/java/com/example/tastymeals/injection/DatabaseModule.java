package com.example.tastymeals.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.tastymeals.domain.db.AppDatabase;
import com.example.tastymeals.domain.db.dao.CategoryDao;
import com.example.tastymeals.domain.db.dao.IngredientDao;
import com.example.tastymeals.domain.db.dao.RecipeDao;
import com.example.tastymeals.domain.db.dao.ShortRecipeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

	private final AppDatabase database;

	public DatabaseModule(Context context) {
		this.database = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME)
				.build();
	}

	@Provides
	@Singleton
	AppDatabase provideDatabase() {
		return database;
	}

	@Provides
	@Singleton
	CategoryDao provideCategoryDao() {
		return database.getCategoryDao();
	}

	@Provides
	@Singleton
	ShortRecipeDao provideShortRecipeDao() {
		return database.getShortRecipeDao();
	}

	@Provides
	@Singleton
	RecipeDao provideRecipeDao() {
		return database.getRecipeDao();
	}

	@Provides
	@Singleton
	IngredientDao provideIngredientDao() {
		return database.getIngredientDao();
	}
}
