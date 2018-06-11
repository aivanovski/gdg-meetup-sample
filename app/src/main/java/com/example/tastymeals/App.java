package com.example.tastymeals;

import android.app.Application;

import com.example.tastymeals.injection.AppComponent;
import com.example.tastymeals.injection.AppModule;
import com.example.tastymeals.injection.DaggerAppComponent;
import com.example.tastymeals.injection.DatabaseModule;
import com.example.tastymeals.injection.NetworkModule;

public class App extends Application {

	private static AppComponent component;

	public static AppComponent getDaggerComponent() {
		return component;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		component = buildComponent();
	}

	private AppComponent buildComponent() {
		return DaggerAppComponent.builder()
				.databaseModule(new DatabaseModule(this))
				.networkModule(new NetworkModule())
				.appModule(new AppModule())
				.build();
	}
}
