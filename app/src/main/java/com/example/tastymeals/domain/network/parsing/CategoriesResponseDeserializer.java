package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.network.response.CategoriesResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;

public class CategoriesResponseDeserializer implements JsonDeserializer<CategoriesResponse> {

	private static final String CATEGORIES = "categories";

	@Override
	public CategoriesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		CategoriesResponse result = new CategoriesResponse();

		JsonObject responseObj = json.getAsJsonObject();
		JsonElement categoryArray = responseObj.get(CATEGORIES);

		Category[] categories = context.deserialize(categoryArray, Category[].class);
		result.setCategories(Arrays.asList(categories));

		return result;
	}
}
