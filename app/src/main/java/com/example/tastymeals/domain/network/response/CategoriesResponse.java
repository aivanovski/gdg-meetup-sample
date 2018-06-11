package com.example.tastymeals.domain.network.response;

import com.example.tastymeals.domain.model.Category;

import java.util.List;

public class CategoriesResponse {

	private List<Category> categories;

	public CategoriesResponse() {
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
