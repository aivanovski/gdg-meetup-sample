package com.example.tastymeals.domain.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tastymeals.domain.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

	@Query("SELECT * FROM Category")
	List<Category> getAll();

	@Query("SELECT * FROM Category WHERE id = :id")
	Category getById(long id);

	@Query("SELECT * FROM Category WHERE name = :name")
	Category getByName(String name);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveAll(List<Category> items);
}
