package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.model.Memory;

/**
 * Created by mag0716 on 2017/06/16.
 */
public class ListItemViewModel extends BaseObservable {

    private final long id;
    private final String question;
    private final String answer;
    private boolean isShowingAnswer = false;

    public ListItemViewModel(@NonNull Memory memory) {
        id = memory.getId();
        question = memory.getQuestion();
        answer = memory.getAnswer();
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Bindable
    public boolean isShowingAnswer() {
        return isShowingAnswer;
    }

    public void setShowingAnswer(boolean showingAnswer) {
        isShowingAnswer = showingAnswer;
        notifyPropertyChanged(BR.showingAnswer);
        notifyPropertyChanged(BR.openAndCloseIcon);
    }

    public void toggleShowingAnswer() {
        setShowingAnswer(!isShowingAnswer);
    }

    @DrawableRes
    @Bindable
    public int getOpenAndCloseIcon() {
        return isShowingAnswer ? R.drawable.ic_arrow_up_black_24dp : R.drawable.ic_arrow_down_black_24dp;
    }
}
