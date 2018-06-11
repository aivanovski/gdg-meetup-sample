package com.example.tastymeals.ui.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastymeals.R;
import com.example.tastymeals.ui.core.widget.ErrorPanelView;
import com.example.tastymeals.ui.core.widget.StateView;

public abstract class BaseFragment extends Fragment {

	private ViewGroup contentContainer;
	private ErrorPanelView errorPanelView;
	private StateView stateView;

	protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	@Nullable
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.core_base_fragment, container, false);

		contentContainer = view.findViewById(getContentContainerId());
		stateView = view.findViewById(R.id.state_view);
		errorPanelView = view.findViewById(R.id.error_panel_view);

		View contentView = onCreateContentView(inflater, contentContainer, savedInstanceState);
		if (contentView != null) {
			contentContainer.addView(contentView);
		}

		return view;
	}

	protected int getContentContainerId() {
		//determines view that will be shown/hidden if fragment state will be changed
		return R.id.content_container;
	}

	private void applyStateToViews(ViewState state) {
		switch (state) {
			case PROGRESS:
				contentContainer.setVisibility(View.GONE);
				stateView.setState(StateView.State.PROGRESS);
				stateView.setVisibility(View.VISIBLE);
				errorPanelView.setVisibility(View.GONE);
				break;
			case EMPTY:
				contentContainer.setVisibility(View.GONE);
				stateView.setState(StateView.State.EMPTY);
				stateView.setVisibility(View.VISIBLE);
				errorPanelView.setVisibility(View.GONE);
				break;
			case DATA:
				contentContainer.setVisibility(View.VISIBLE);
				stateView.setVisibility(View.GONE);
				errorPanelView.setVisibility(View.GONE);
				break;
			case ERROR:
				contentContainer.setVisibility(View.GONE);
				stateView.setState(StateView.State.ERROR);
				stateView.setVisibility(View.VISIBLE);
				errorPanelView.setVisibility(View.GONE);
				break;
			case DATA_WITH_ERROR_PANEL:
				contentContainer.setVisibility(View.VISIBLE);
				stateView.setVisibility(View.GONE);
				errorPanelView.setVisibility(View.VISIBLE);
				break;
		}
	}

	public void setViewState(ViewState state) {
		applyStateToViews(state);
	}

	public void setEmptyText(CharSequence text) {
		stateView.setEmptyText(text);
	}

	public void setErrorText(CharSequence text) {
		stateView.setErrorText(text);
		errorPanelView.setText(text);
	}
}
