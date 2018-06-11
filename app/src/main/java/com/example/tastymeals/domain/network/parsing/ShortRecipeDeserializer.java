package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.ShortRecipe;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ShortRecipeDeserializer implements JsonDeserializer<ShortRecipe> {

	private static final String ID = "idMeal";
	private static final String NAME = "strMeal";
	private static final String IMAGE_URL = "strMealThumb";

	@Override
	public ShortRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		ShortRecipe result = new ShortRecipe();

		JsonObject obj = json.getAsJsonObject();

		result.setId(obj.get(ID).getAsLong());
		result.setName(obj.get(NAME).getAsString());
		result.setImageUrl(obj.get(IMAGE_URL).getAsString());

		return result;
	}
}
