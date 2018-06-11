package com.example.tastymeals.domain.network;

import com.example.tastymeals.domain.network.response.CategoriesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CategoryService {

	@GET("categories.php")
	Single<CategoriesResponse> getAllCategories();
}
