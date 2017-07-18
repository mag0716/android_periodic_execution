package com.github.mag0716.memorytraining.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.FragmentListBinding;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.presenter.ListPresenter;
import com.github.mag0716.memorytraining.view.ListView;
import com.github.mag0716.memorytraining.view.adapter.MemoryListAdapter;
import com.github.mag0716.memorytraining.view.decoration.CardItemDecoration;
import com.github.mag0716.memorytraining.viewmodel.ListViewModel;

import java.util.List;

/**
 * 訓練データ一覧画面
 * <p>
 * Created by mag0716 on 2017/07/07.
 */
public class ListFragment extends Fragment implements ListView {

    public static final String TAG = ListFragment.class.getCanonicalName();

    private FragmentListBinding binding;
    private final ListViewModel viewModel = new ListViewModel();
    private ListPresenter presenter;

    private MemoryListAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public ListFragment() {
    }

    // region lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setViewModel(viewModel);
        presenter = new ListPresenter(((Application) getContext().getApplicationContext()).getDatabase().memoryDao());
        binding.setPresenter(presenter);
        adapter = new MemoryListAdapter(getContext(), presenter);
        binding.trainingList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemDecoration = new CardItemDecoration(getContext());
        binding.trainingList.addItemDecoration(itemDecoration);
        binding.trainingList.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.loadTrainingData(System.currentTimeMillis());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.trainingList.removeItemDecoration(itemDecoration);
    }

    // endregion

    // region ListView

    @Override
    public void showMemoryList(@NonNull List<Memory> memoryList) {
        viewModel.set(memoryList);
    }

    @Override
    public void dismissMemory(long id) {
        viewModel.remove(id);
    }

    @Override
    public void completedTraining() {

    }

    // endregion
}
