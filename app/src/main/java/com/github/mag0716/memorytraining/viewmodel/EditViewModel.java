package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.mag0716.memorytraining.BR;
import com.github.mag0716.memorytraining.model.Memory;

/**
 * Created by mag0716 on 2017/07/08.
 */
public class EditViewModel extends BaseObservable {

    @Bindable
    private final Memory memory;

    public EditViewModel(@NonNull Memory memory) {
        this.memory = memory;
        notifyPropertyChanged(BR.question);
        notifyPropertyChanged(BR.answer);
        notifyPropertyChanged(BR.valid);
    }

    public Memory getMemory() {
        return memory;
    }

    @Bindable
    public String getQuestion() {
        return memory.getQuestion();
    }

    public void setQuestion(String question) {
        memory.setQuestion(question);
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public String getAnswer() {
        return memory.getAnswer();
    }

    public void setAnswer(String answer) {
        memory.setAnswer(answer);
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public boolean isValid() {
        return !TextUtils.isEmpty(memory.getQuestion())
                && !TextUtils.isEmpty(memory.getAnswer());
    }
}
