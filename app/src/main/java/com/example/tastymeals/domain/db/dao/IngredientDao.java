package com.example.tastymeals.domain.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tastymeals.domain.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

	@Query("SELECT * FROM Ingredient WHERE recipeId = :recipeId")
	List<Ingredient> getByRecipeId(long recipeId);

	@Insert
	void saveAll(List<Ingredient> items);
}
