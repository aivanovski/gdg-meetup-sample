package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.network.response.RecipeResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RecipeResponseDeserializer implements JsonDeserializer<RecipeResponse> {

	private static final String MEALS = "meals";

	@Override
	public RecipeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		RecipeResponse result = new RecipeResponse();

		JsonObject responseObj = json.getAsJsonObject();
		JsonElement recipeArray = responseObj.get(MEALS);

		Recipe[] recipes = context.deserialize(recipeArray, Recipe[].class);

		if (recipes != null && recipes.length != 0) {
			result.setRecipe(recipes[0]);
		}

		return result;
	}
}
