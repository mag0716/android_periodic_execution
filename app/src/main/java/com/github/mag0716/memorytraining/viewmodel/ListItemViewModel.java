package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.model.Memory;

/**
 * Created by mag0716 on 2017/06/16.
 */
public class ListItemViewModel extends BaseObservable {

    private final String question;
    private final String answer;
    private boolean isShowingAnswer = false;

    public ListItemViewModel(@NonNull Memory memory) {
        question = memory.getQuestion();
        answer = memory.getAnswer();
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
    }

    public void toggleShowingAnswer() {
        setShowingAnswer(!isShowingAnswer);
    }
}
