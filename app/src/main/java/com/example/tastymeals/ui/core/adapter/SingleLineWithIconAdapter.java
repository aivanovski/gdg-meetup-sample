package com.example.tastymeals.ui.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tastymeals.R;

import java.util.ArrayList;
import java.util.List;

public class SingleLineWithIconAdapter extends RecyclerView.Adapter<SingleLineWithIconAdapter.ItemViewHolder> {

	private OnItemClickListener clickListener;
	private final Context context;
	private final LayoutInflater inflater;
	private final List<Item> items;

	public interface OnItemClickListener {
		void onItemClicked(int position);
	}

	public SingleLineWithIconAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.items = new ArrayList<>();
	}

	public void setOnItemClickListener(OnItemClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void setItems(List<Item> newItems) {
		items.clear();

		if (newItems != null) {
			items.addAll(newItems);
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.core_single_line_with_icon_item, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position) {
		Item item = items.get(position);

		holder.title.setText(item.title);

		Glide.with(context)
				.load(item.imageUrl)
				.placeholder(R.drawable.ic_placeholder_gray_48dp)
				.dontAnimate()
				.into(holder.icon);

		holder.root.setOnClickListener(view -> onItemClicked(position));
	}

	private void onItemClicked(int position) {
		if (clickListener != null) {
			clickListener.onItemClicked(position);
		}
	}

	static class ItemViewHolder extends RecyclerView.ViewHolder {

		ViewGroup root;
		TextView title;
		ImageView icon;

		ItemViewHolder(View view) {
			super(view);
			root = view.findViewById(R.id.root);
			title = view.findViewById(R.id.title);
			icon = view.findViewById(R.id.icon);
		}
	}

	public static class Item {

		final String title;
		final String imageUrl;

		public Item(String title, String imageUrl) {
			this.title = title;
			this.imageUrl = imageUrl;
		}
	}
}
