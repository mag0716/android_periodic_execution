package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.StringRes;

import com.annimon.stream.Stream;
import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.model.Memory;

import java.util.ArrayList;
import java.util.Iterator;
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

    public void remove(long id) {
        Iterator<ListItemViewModel> iterator = itemViewModelList.iterator();
        while (iterator.hasNext()) {
            ListItemViewModel viewModel = iterator.next();
            if (viewModel.getId() == id) {
                iterator.remove();
                break;
            }
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

    @StringRes
    @Bindable
    public int getInformationMessageId() {
        // TODO: DB にデータが1件もない場合は別のメッセージにする
        return R.string.list_completed_training_message;
    }
}
