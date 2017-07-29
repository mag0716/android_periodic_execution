package com.github.mag0716.memorytraining.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ViewListItemBinding;
import com.github.mag0716.memorytraining.databinding.ViewTrainableListItemBinding;
import com.github.mag0716.memorytraining.presenter.ListPresenter;
import com.github.mag0716.memorytraining.viewmodel.ListItemViewModel;
import com.github.mag0716.memorytraining.viewmodel.TrainingViewModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by mag0716 on 2017/06/13.
 */
public class MemoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 登録したデータを確認するためだけのセル
     */
    private static final int ONLY_VIEW_TYPE = 0;
    /**
     * 訓練可能なセル
     */
    private static final int TRAINABLE_VIEW_TYPE = 1;

    private final LayoutInflater inflater;
    private final ListPresenter presenter;
    private final int category;
    private final List<ListItemViewModel> viewModelList = new ArrayList<>();

    public MemoryListAdapter(@NonNull Context context, @NonNull ListPresenter presenter, int category) {
        inflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.category = category;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        final int viewType = getItemViewType(position);
        if (viewType == TRAINABLE_VIEW_TYPE) {
            return new TrainableMemoryViewHolder(inflater.inflate(R.layout.view_trainable_list_item, viewGroup, false));
        } else {
            return new MemoryViewHolder(inflater.inflate(R.layout.view_list_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ListItemViewModel viewModel = viewModelList.get(position);
        if (viewHolder instanceof MemoryViewHolder) {
            ((MemoryViewHolder) viewHolder).binding.setViewModel(viewModel);
            ((MemoryViewHolder) viewHolder).binding.executePendingBindings();
        } else if (viewHolder instanceof TrainableMemoryViewHolder) {
            ((TrainableMemoryViewHolder) viewHolder).binding.setViewModel(viewModel);
            ((TrainableMemoryViewHolder) viewHolder).binding.setPresenter(presenter);
            ((TrainableMemoryViewHolder) viewHolder).binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return viewModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (category == TrainingViewModel.CATEGORY_ALL) {
            return ONLY_VIEW_TYPE;
        } else {
            return TRAINABLE_VIEW_TYPE;
        }
    }

    public List<ListItemViewModel> getViewModelList() {
        return viewModelList;
    }

    public void setViewModelList(List<ListItemViewModel> viewModelList) {
        this.viewModelList.clear();
        this.viewModelList.addAll(viewModelList);
    }

    public class MemoryViewHolder extends RecyclerView.ViewHolder {
        @Getter
        private final ViewListItemBinding binding;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public class TrainableMemoryViewHolder extends RecyclerView.ViewHolder {
        @Getter
        private final ViewTrainableListItemBinding binding;

        public TrainableMemoryViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
