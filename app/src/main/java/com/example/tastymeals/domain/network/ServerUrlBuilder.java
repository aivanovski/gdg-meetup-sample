package com.example.tastymeals.domain.network;

public class ServerUrlBuilder {

	private static final String BASE_URL = "https://www.themealdb.com";

	private final StringBuilder builder;

	public ServerUrlBuilder() {
		builder = new StringBuilder();
		builder.append(BASE_URL);
	}

	public ServerUrlBuilder jsonApi() {
		builder.append("/api/json/v1/1/");
		return this;
	}
	
	public ServerUrlBuilder ingredientUrl(String ingredientName) {
		String formattedName = ingredientName.replaceAll(" ", "%20");
		builder.append("/images/ingredients/").append(formattedName).append(".png");
		return this;
	}
	
	public String build() {
		return builder.toString();
	}
}
