package com.example.tastymeals.ui.core;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {

	private final AtomicBoolean pending;

	public SingleLiveEvent() {
		pending = new AtomicBoolean(false);
	}

	@MainThread
	public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
		super.observe(owner, value -> {
			if (pending.compareAndSet(true, false)) {
				observer.onChanged(value);
			}
		});
	}

	@MainThread
	public void setValue(@Nullable T value) {
		pending.set(true);
		super.setValue(value);
	}

	@MainThread
	public void call(T value) {
		setValue(value);
	}
}
