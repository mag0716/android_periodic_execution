package com.github.mag0716.memorytraining.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ViewListItemBinding;
import com.github.mag0716.memorytraining.presenter.ListPresenter;
import com.github.mag0716.memorytraining.viewmodel.ListItemViewModel;

import lombok.Getter;

/**
 * Created by mag0716 on 2017/06/13.
 */
public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.MemoryViewHolder> {

    private final LayoutInflater inflater;
    private final ListPresenter presenter;
    private final ObservableList<ListItemViewModel> viewModelList;

    public MemoryListAdapter(@NonNull Context context, @NonNull ListPresenter presenter, @NonNull ObservableList<ListItemViewModel> viewModelList) {
        inflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.viewModelList = viewModelList;
        this.viewModelList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ListItemViewModel>>() {
            @Override
            public void onChanged(ObservableList<ListItemViewModel> listItemViewModels) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<ListItemViewModel> listItemViewModels, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<ListItemViewModel> listItemViewModels, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<ListItemViewModel> listItemViewModels, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<ListItemViewModel> listItemViewModels, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public MemoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new MemoryViewHolder(inflater.inflate(R.layout.view_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MemoryViewHolder viewHolder, int position) {
        final ListItemViewModel viewModel = viewModelList.get(position);
        ((ViewListItemBinding) viewHolder.binding).setMemory(viewModel);
        ((ViewListItemBinding) viewHolder.binding).setPresenter(presenter);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return viewModelList.size();
    }

    public class MemoryViewHolder extends RecyclerView.ViewHolder {

        @Getter
        private final ViewDataBinding binding;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
