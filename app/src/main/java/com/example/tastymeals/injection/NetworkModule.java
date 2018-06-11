package com.example.tastymeals.injection;

import android.util.Log;

import com.example.tastymeals.BuildConfig;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.model.ShortRecipe;
import com.example.tastymeals.domain.network.CategoryService;
import com.example.tastymeals.domain.network.RecipeService;
import com.example.tastymeals.domain.network.ServerUrlBuilder;
import com.example.tastymeals.domain.network.ShortRecipeService;
import com.example.tastymeals.domain.network.parsing.CategoriesResponseDeserializer;
import com.example.tastymeals.domain.network.parsing.CategoryDeserializer;
import com.example.tastymeals.domain.network.parsing.RecipeDeserializer;
import com.example.tastymeals.domain.network.parsing.RecipeResponseDeserializer;
import com.example.tastymeals.domain.network.parsing.ShortRecipeDeserializer;
import com.example.tastymeals.domain.network.parsing.ShortRecipesResponseDeserializer;
import com.example.tastymeals.domain.network.response.CategoriesResponse;
import com.example.tastymeals.domain.network.response.RecipeResponse;
import com.example.tastymeals.domain.network.response.ShortRecipesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

	private static final String TAG = NetworkModule.class.getSimpleName();

	public NetworkModule() {
	}

	@Provides
	@Singleton
	OkHttpClient provideOkHttp() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.addInterceptor(chain -> {
			if (BuildConfig.DEBUG) {
				Log.d(TAG, "url: " + chain.request().url());
			}

			return chain.proceed(chain.request());
		});

		return builder.build();
	}

	@Provides
	@Singleton
	Retrofit provideRetrofit(OkHttpClient httpClient) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(CategoriesResponse.class, new CategoriesResponseDeserializer())
				.registerTypeAdapter(ShortRecipesResponse.class, new ShortRecipesResponseDeserializer())
				.registerTypeAdapter(RecipeResponse.class, new RecipeResponseDeserializer())
				.registerTypeAdapter(Category.class, new CategoryDeserializer())
				.registerTypeAdapter(ShortRecipe.class, new ShortRecipeDeserializer())
				.registerTypeAdapter(Recipe.class, new RecipeDeserializer())
				.create();

		return new Retrofit.Builder()
				.baseUrl(new ServerUrlBuilder().jsonApi().build())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(httpClient)
				.build();
	}

	@Provides
	@Singleton
	CategoryService provideCategoryService(Retrofit retrofit) {
		return retrofit.create(CategoryService.class);
	}

	@Provides
	@Singleton
	ShortRecipeService provideShortRecipeService(Retrofit retrofit) {
		return retrofit.create(ShortRecipeService.class);
	}

	@Provides
	@Singleton
	RecipeService provideRecipeService(Retrofit retrofit) {
		return retrofit.create(RecipeService.class);
	}
}
