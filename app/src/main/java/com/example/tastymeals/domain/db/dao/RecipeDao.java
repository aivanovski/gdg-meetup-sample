package com.example.tastymeals.domain.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tastymeals.domain.model.Recipe;

@Dao
public interface RecipeDao {

	@Query("SELECT * FROM Recipe WHERE id = :id")
	Recipe getById(long id);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void save(Recipe recipe);
}
