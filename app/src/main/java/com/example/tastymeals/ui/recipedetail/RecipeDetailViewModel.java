package com.example.tastymeals.ui.recipedetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.tastymeals.App;
import com.example.tastymeals.R;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Recipe;
import com.example.tastymeals.domain.model.Resource;
import com.example.tastymeals.domain.repository.CategoryRepository;
import com.example.tastymeals.domain.repository.RecipeRepository;
import com.example.tastymeals.ui.core.ViewState;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeDetailViewModel extends AndroidViewModel {

	@Inject
	RecipeRepository recipeRepository;

	@Inject
	CategoryRepository categoryRepository;

	private long recipeId;
	private MutableLiveData<String> emptyTextData;
	private MutableLiveData<String> errorTextData;
	private MutableLiveData<ViewState> stateData;
	private MutableLiveData<Pair<Category, Recipe>> categoryAndRecipeData;
	private CompositeDisposable disposables;

	public RecipeDetailViewModel(@NonNull Application application) {
		super(application);
		App.getDaggerComponent().inject(this);

		disposables = new CompositeDisposable();
		emptyTextData = new MutableLiveData<>();
		errorTextData = new MutableLiveData<>();
		stateData = new MutableLiveData<>();
	}

	LiveData<ViewState> getState() {
		return stateData;
	}

	LiveData<String> getEmptyText() {
		return emptyTextData;
	}

	LiveData<String> getErrorText() {
		return errorTextData;
	}

	LiveData<Pair<Category, Recipe>> getCategoryAndRecipe() {
		if (categoryAndRecipeData == null) {
			categoryAndRecipeData = new MutableLiveData<>();
		}

		return categoryAndRecipeData;
	}

	void start() {
		loadData(recipeId);
	}

	void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	private void loadData(long recipeId) {
		stateData.setValue(ViewState.PROGRESS);

		Disposable disposable = recipeRepository.getRecipe(recipeId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::onRecipeLoaded);

		disposables.add(disposable);
	}

	private void onRecipeLoaded(Resource<Recipe> result) {
		if (result.getResult() != null && result.getError() == null) {
			Recipe recipe = result.getResult();

			Disposable disposable = categoryRepository.getCategoryByName(recipe.getCategoryName())
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(category -> onCategoryLoaded(category, recipe));
			disposables.add(disposable);
		} else {
			errorTextData.setValue(createErrorMessage(result.getError()));
			stateData.setValue(ViewState.ERROR);
		}
	}

	private void onCategoryLoaded(Category category, Recipe recipe) {
		if (category != null) {
			categoryAndRecipeData.setValue(new Pair<>(category, recipe));
			stateData.setValue(ViewState.DATA);
		} else {
			errorTextData.setValue(getApplication().getString(R.string.error_was_occurred));
			stateData.setValue(ViewState.ERROR);
		}
	}

	private String createErrorMessage(Throwable error) {
		String message;

		if (error instanceof IOException) {
			message = getApplication().getString(R.string.check_your_internet_connection);
		} else {
			message = getApplication().getString(R.string.error_was_occurred);
		}

		return message;
	}
}
