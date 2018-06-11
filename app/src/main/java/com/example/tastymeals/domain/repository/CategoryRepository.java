package com.example.tastymeals.domain.repository;

import com.example.tastymeals.domain.db.dao.CategoryDao;
import com.example.tastymeals.domain.model.Category;
import com.example.tastymeals.domain.model.Resource;
import com.example.tastymeals.domain.network.CategoryService;

import java.util.List;

import io.reactivex.Observable;

public class CategoryRepository {

	private final CategoryDao dao;
	private final CategoryService service;

	public CategoryRepository(CategoryDao dao, CategoryService service) {
		this.dao = dao;
		this.service = service;
	}

	public Observable<Resource<List<Category>>> getCategories() {
		return service.getAllCategories()
				.map(response -> Resource.fromSuccessResponse(response.getCategories()))
				.toObservable()
				.onErrorResumeNext(throwable -> {
					return Observable.fromCallable(() -> Resource.fromCache(dao.getAll(), throwable));
				})
				.doOnNext(res -> {
					dao.saveAll(res.getResult());
				});
	}

	public Observable<Category> getCategoryByName(String name) {
		return Observable.fromCallable(() -> dao.getByName(name));
	}
}
