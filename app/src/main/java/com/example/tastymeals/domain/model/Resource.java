package com.example.tastymeals.domain.model;

public class Resource<T> {

	private T result;
	private Throwable error;

	public static <T> Resource<T> fromCache(T result, Throwable error) {
		return new Resource<>(result, error);
	}

	public static <T> Resource<T> fromSuccessResponse(T result) {
		return new Resource<>(result, null);
	}

	private Resource(T result, Throwable error) {
		this.result = result;
		this.error = error;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}
}
