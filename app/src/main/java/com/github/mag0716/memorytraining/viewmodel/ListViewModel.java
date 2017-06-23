package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.StringRes;

import com.annimon.stream.Stream;
import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.model.Memory;

import java.util.List;

/**
 * Created by mag0716 on 2017/06/16.
 */

public class ListViewModel extends BaseObservable {

    private final ObservableList<ListItemViewModel> itemViewModelList = new ObservableArrayList<>();
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
        final int index = getIndex(id);
        if (0 <= index && index < itemViewModelList.size()) {
            itemViewModelList.remove(index);
            isCompleted = itemViewModelList.isEmpty();
            notifyPropertyChanged(BR.completed);
        }
    }

    public ObservableList<ListItemViewModel> getItemViewModelList() {
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

    /**
     * ID にマッチする ViewModel の index を返却する
     *
     * @param id Memory#id
     * @return id にマッチする ViewModel の index
     * マッチした id がなければ -1 を返却する
     */
    private int getIndex(long id) {
        int index = -1;
        for (int i = 0; i < itemViewModelList.size(); i++) {
            ListItemViewModel viewModel = itemViewModelList.get(i);
            if (viewModel.getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }
}
