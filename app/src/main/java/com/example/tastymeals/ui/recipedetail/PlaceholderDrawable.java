package com.example.tastymeals.ui.recipedetail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

public class PlaceholderDrawable extends Drawable {

	private final float weight;
	private final Drawable drawable;

	PlaceholderDrawable(Context context, @DrawableRes int drawableId, float weight) {
		this.drawable = ContextCompat.getDrawable(context, drawableId);
		this.weight = weight;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		int size = (int) (bounds.width() * weight);

		int left = (bounds.width() - size) / 2;
		int top = (bounds.height() - size) / 2;

		drawable.setBounds(left, top, left + size, top + size);
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
		drawable.draw(canvas);
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}
}
