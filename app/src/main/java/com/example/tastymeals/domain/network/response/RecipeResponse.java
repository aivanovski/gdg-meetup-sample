package com.example.tastymeals.domain.network.response;

import com.example.tastymeals.domain.model.Recipe;

public class RecipeResponse {

	private Recipe recipe;

	public RecipeResponse() {
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
