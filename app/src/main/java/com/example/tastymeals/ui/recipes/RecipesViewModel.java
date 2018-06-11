package com.example.tastymeals.ui.recipes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.tastymeals.App;
import com.example.tastymeals.R;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Resource;
import com.example.tastymeals.domain.model.ShortRecipe;
import com.example.tastymeals.domain.repository.ShortRecipeRepository;
import com.example.tastymeals.ui.core.SingleLiveEvent;
import com.example.tastymeals.ui.core.ViewState;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipesViewModel extends AndroidViewModel {

	@Inject
	ShortRecipeRepository repository;

	private Category category;
	private MutableLiveData<String> emptyTextData;
	private MutableLiveData<String> errorTextData;
	private MutableLiveData<ViewState> stateData;
	private MutableLiveData<Boolean> refreshingData;
	private MutableLiveData<List<ShortRecipe>> recipesData;
	private SingleLiveEvent<ShortRecipe> openRecipeEvent;
	private CompositeDisposable disposables;

	public RecipesViewModel(@NonNull Application application) {
		super(application);
		App.getDaggerComponent().inject(this);

		disposables = new CompositeDisposable();
		emptyTextData = new MutableLiveData<>();
		errorTextData = new MutableLiveData<>();
		stateData = new MutableLiveData<>();
		refreshingData = new MutableLiveData<>();
		recipesData = new MutableLiveData<>();
		openRecipeEvent = new SingleLiveEvent<>();

		stateData.setValue(ViewState.PROGRESS);
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

	LiveData<Boolean> isRefreshing() {
		return refreshingData;
	}

	LiveData<List<ShortRecipe>> getRecipes() {
		return recipesData;
	}

	LiveData<ShortRecipe> getOpenRecipeEvent() {
		return openRecipeEvent;
	}

	void start() {
		loadData(category, true);
	}

	private void loadData(Category category, boolean displayProgress) {
		Disposable disposable = repository.getRecipes(category)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe(d -> {
					if (displayProgress) {
						stateData.postValue(ViewState.PROGRESS);
					}
				})
				.subscribe(this::onRecipesLoaded);

		disposables.add(disposable);
	}

	private void onRecipesLoaded(Resource<List<ShortRecipe>> result) {
		List<ShortRecipe> recipes = result.getResult();

		if (result.getError() == null) {
			if (recipes.size() == 0) {
				stateData.setValue(ViewState.EMPTY);
				emptyTextData.setValue(getApplication().getString(R.string.no_items));
			} else {
				recipesData.setValue(result.getResult());
				stateData.setValue(ViewState.DATA);
			}
		} else {
			if (recipes.size() == 0) {
				stateData.setValue(ViewState.ERROR);
				errorTextData.setValue(createErrorMessage(result.getError()));
			} else {
				recipesData.setValue(result.getResult());
				errorTextData.setValue(createErrorMessage(result.getError()));
				stateData.setValue(ViewState.DATA_WITH_ERROR_PANEL);
			}
		}

		refreshingData.setValue(false);
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

	void showRecipe(ShortRecipe recipe) {
		openRecipeEvent.call(recipe);
	}

	void refreshData() {
		refreshingData.setValue(true);
		loadData(category, false);
	}

	void setCategory(Category category) {
		this.category = category;
	}

	@Override
	protected void onCleared() {
		disposables.clear();
	}
}
