package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Stream;
import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.model.Memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mag0716 on 2017/06/16.
 */

public class ListViewModel extends BaseObservable {

    private final List<ListItemViewModel> itemViewModelList = new ArrayList<>();
    private boolean isCompleted = false;

    // TODO: Memory の追加、削除
    public void addAll(List<Memory> memoryList) {
        if (memoryList != null) {
            itemViewModelList.addAll(Stream.of(memoryList)
                    .map(ListItemViewModel::new)
                    .toList());
        }
        isCompleted = itemViewModelList.isEmpty();
        notifyPropertyChanged(BR.completed);
    }

    public List<ListItemViewModel> getItemViewModelList() {
        return itemViewModelList;
    }

    @Bindable
    public boolean isCompleted() {
        return isCompleted;
    }
}
