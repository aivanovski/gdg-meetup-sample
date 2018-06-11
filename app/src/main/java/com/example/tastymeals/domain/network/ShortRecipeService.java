package com.example.tastymeals.domain.network;

import com.example.tastymeals.domain.network.response.ShortRecipesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShortRecipeService {

	@GET("filter.php")
	Single<ShortRecipesResponse> getShortRecipesByCategory(@Query("c") String categoryName);
}
