package com.topcoder.timobile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.timobileapp.R;
import com.topcoder.timobile.adapter.TradingCardAdapter;
import com.topcoder.timobile.baseclasses.BaseFragment;
import com.topcoder.timobile.dialogs.TradingCardDialog;
import com.topcoder.timobile.model.TradingCardModel;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Author: Harshvardhan
 * Date: 01/11/17
 */

public class TradingFragment extends BaseFragment {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  Unbinder unbinder;

  private List<TradingCardModel> tradingCardModelList = new ArrayList<>();
  private TradingCardAdapter adapter;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.recyclerview, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    apiService.getTradingCard().subscribe(this::onSuccess, this::onError);
  }

  private void onError(Throwable throwable) {
    Timber.d(throwable);
  }

  private void onSuccess(List<TradingCardModel> tradingCardModels) {
    tradingCardModelList.addAll(tradingCardModels);
    adapter = new TradingCardAdapter(getActivity(), tradingCardModelList);
    recyclerView.setAdapter(adapter);
    adapter.setRecycleOnItemClickListner((view, position) -> {
      TradingCardDialog dialog = new TradingCardDialog();
      dialog.show(getChildFragmentManager(), dialog.getClass().getName());
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
