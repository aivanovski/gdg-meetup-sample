package com.example.tastymeals.domain.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tastymeals.domain.model.ShortRecipe;

import java.util.List;

@Dao
public interface ShortRecipeDao {

	@Query("SELECT * FROM ShortRecipe WHERE categoryId = :categoryId")
	List<ShortRecipe> getAllByCategoryId(long categoryId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveAll(List<ShortRecipe> recipes);
}
