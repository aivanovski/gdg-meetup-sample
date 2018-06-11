package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.Category;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CategoryDeserializer implements JsonDeserializer<Category> {

	private static final String ID = "idCategory";
	private static final String NAME = "strCategory";
	private static final String DESCRIPTION = "strCategoryDescription";
	private static final String IMAGE_URL = "strCategoryThumb";

	@Override
	public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Category result = new Category();

		JsonObject obj = json.getAsJsonObject();

		result.setId(obj.get(ID).getAsLong());
		result.setName(obj.get(NAME).getAsString());
		result.setDescription(obj.get(DESCRIPTION).getAsString());
		result.setImageUrl(obj.get(IMAGE_URL).getAsString());

		return result;
	}
}
