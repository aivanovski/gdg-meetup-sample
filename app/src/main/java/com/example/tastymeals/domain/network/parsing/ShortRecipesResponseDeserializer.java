package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.ShortRecipe;
import com.example.tastymeals.domain.network.response.ShortRecipesResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;

public class ShortRecipesResponseDeserializer implements JsonDeserializer<ShortRecipesResponse> {

	private static final String MEALS = "meals";

	@Override
	public ShortRecipesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		ShortRecipesResponse result = new ShortRecipesResponse();

		JsonObject responseObj = json.getAsJsonObject();
		JsonElement shortRecipeArray = responseObj.get(MEALS);

		if (!shortRecipeArray.isJsonNull()) {
			ShortRecipe[] recipes = context.deserialize(shortRecipeArray, ShortRecipe[].class);
			result.setShortRecipes(Arrays.asList(recipes));
		} else {
			result.setShortRecipes(Collections.emptyList());
		}

		return result;
	}
}
