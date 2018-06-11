package com.example.tastymeals.domain.network.response;

import com.example.tastymeals.domain.model.ShortRecipe;

import java.util.List;

public class ShortRecipesResponse {

	private List<ShortRecipe> shortRecipes;

	public ShortRecipesResponse() {
	}

	public List<ShortRecipe> getShortRecipes() {
		return shortRecipes;
	}

	public void setShortRecipes(List<ShortRecipe> shortRecipes) {
		this.shortRecipes = shortRecipes;
	}
}
