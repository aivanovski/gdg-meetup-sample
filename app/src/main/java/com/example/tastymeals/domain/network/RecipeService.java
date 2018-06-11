package com.example.tastymeals.domain.network;

import com.example.tastymeals.domain.network.response.RecipeResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {

	@GET("lookup.php")
	Single<RecipeResponse> getRecipe(@Query("i") long id);
}
