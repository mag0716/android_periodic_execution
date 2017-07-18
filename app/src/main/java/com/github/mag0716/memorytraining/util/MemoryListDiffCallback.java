package com.github.mag0716.memorytraining.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.github.mag0716.memorytraining.viewmodel.ListItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mag0716 on 2017/07/17.
 */
public class MemoryListDiffCallback extends DiffUtil.Callback {

    private final List<ListItemViewModel> oldList = new ArrayList<>();
    private final List<ListItemViewModel> newList = new ArrayList<>();

    public MemoryListDiffCallback(@Nullable List<ListItemViewModel> oldList, @Nullable List<ListItemViewModel> newList) {
        if (oldList != null) {
            this.oldList.addAll(oldList);
        }
        if (newList != null) {
            this.newList.addAll(newList);
        }
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
