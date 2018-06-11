package com.example.tastymeals.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "ShortRecipe",
		foreignKeys = {
				@ForeignKey(entity = Category.class,
						parentColumns = "id",
						childColumns = "categoryId",
						onDelete = ForeignKey.CASCADE)
		},
		indices = {
				@Index(value = "categoryId",
						name = "categoryId_idx")
		})
public class ShortRecipe implements Parcelable {

	@PrimaryKey
	private long id;
	private long categoryId;
	private String name;
	private String imageUrl;

	public ShortRecipe() {
	}

	private ShortRecipe(Parcel in) {
		id = in.readLong();
		categoryId = in.readLong();
		name = in.readString();
		imageUrl = in.readString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(categoryId);
		dest.writeString(name);
		dest.writeString(imageUrl);
	}

	public static final Creator<ShortRecipe> CREATOR = new Creator<ShortRecipe>() {
		@Override
		public ShortRecipe createFromParcel(Parcel source) {
			return new ShortRecipe(source);
		}

		@Override
		public ShortRecipe[] newArray(int size) {
			return new ShortRecipe[size];
		}
	};
}
