package com.example.tastymeals.domain.network.parsing;

import com.example.tastymeals.domain.model.Ingredient;
import com.example.tastymeals.domain.model.Recipe;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class RecipeDeserializer implements JsonDeserializer<Recipe> {

	private static final int MAX_INGREDIENT_COUNT = 20;

	private static final String ID = "idMeal";
	private static final String NAME = "strMeal";
	private static final String CATEGORY = "strCategory";
	private static final String AREA = "strArea";
	private static final String INSTRUCTIONS = "strInstructions";
	private static final String IMAGE_URL = "strMealThumb";
	private static final String TAGS = "strTags";
	private static final String VIDEO_URL = "strYoutube";
	private static final String SOURCE_URL = "strSource";

	private static final String INGREDIENT = "strIngredient";
	private static final String MEASURE = "strMeasure";

	@Override
	public Recipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Recipe result = new Recipe();

		JsonObject obj = json.getAsJsonObject();

		JsonElement idElement = obj.get(ID);
		JsonElement nameElement = obj.get(NAME);
		JsonElement categoryElement = obj.get(CATEGORY);
		JsonElement areaElement = obj.get(AREA);
		JsonElement instructionsElement = obj.get(INSTRUCTIONS);
		JsonElement imageUrlElement = obj.get(IMAGE_URL);
		JsonElement tagsElement = obj.get(TAGS);
		JsonElement videoUrlElement = obj.get(VIDEO_URL);
		JsonElement sourceUrlElement = obj.get(SOURCE_URL);

		if (idElement != null && !idElement.isJsonNull()) {
			result.setId(idElement.getAsLong());
		}
		if (nameElement != null && !nameElement.isJsonNull()) {
			result.setName(nameElement.getAsString());
		}
		if (categoryElement != null && !categoryElement.isJsonNull()) {
			result.setCategoryName(categoryElement.getAsString());
		}
		if (areaElement != null && !areaElement.isJsonNull()) {
			result.setArea(areaElement.getAsString());
		}
		if (instructionsElement != null && !instructionsElement.isJsonNull()) {
			result.setInstructions(instructionsElement.getAsString());
		}
		if (tagsElement != null && !tagsElement.isJsonNull()) {
			result.setTags(tagsElement.getAsString());
		}
		if (imageUrlElement != null && !imageUrlElement.isJsonNull()) {
			result.setImageUrl(imageUrlElement.getAsString());
		}
		if (videoUrlElement != null && !videoUrlElement.isJsonNull()) {
			result.setVideoUrl(videoUrlElement.getAsString());
		}
		if (sourceUrlElement != null && !sourceUrlElement.isJsonNull()) {
			result.setSourceUrl(sourceUrlElement.getAsString());
		}

		List<Ingredient> ingredients = new ArrayList<>();
		for (int idx = 1; idx <= MAX_INGREDIENT_COUNT; idx++) {
			JsonElement ingredientNameObj = obj.get(INGREDIENT + idx);
			JsonElement ingredientQuantityObj = obj.get(MEASURE + idx);

			if (ingredientNameObj != null
					&& !ingredientNameObj.isJsonNull()
					&& ingredientQuantityObj != null
					&& !ingredientQuantityObj.isJsonNull()) {
				String ingredientName = ingredientNameObj.getAsString();
				String ingredientQuantity = obj.get(MEASURE + idx).getAsString();

				if (!isEmpty(ingredientName)) {
					Ingredient ingredient = new Ingredient();

					ingredient.setName(ingredientName);
					ingredient.setQuantity(ingredientQuantity);
					ingredient.setRecipeId(result.getId());

					ingredients.add(ingredient);
				}
			}
		}

		result.setIngredients(ingredients);

		return result;
	}
}
