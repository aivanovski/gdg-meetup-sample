package com.example.tastymeals.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Ingredient",
		foreignKeys = {
				@ForeignKey(entity = Recipe.class,
						parentColumns = "id",
						childColumns = "recipeId",
						onDelete = ForeignKey.CASCADE)
		},
		indices = {
				@Index(value = "recipeId",
						name = "recipeId_idx")
		})
public class Ingredient {

	@PrimaryKey(autoGenerate = true)
	private long id;
	private long recipeId;
	private String name;
	private String quantity;

	public Ingredient() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
