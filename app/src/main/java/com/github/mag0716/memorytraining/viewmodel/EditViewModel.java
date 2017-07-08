package com.github.mag0716.memorytraining.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.github.mag0716.memorytraining.BR;

/**
 * Created by mag0716 on 2017/07/08.
 */
public class EditViewModel extends BaseObservable {

    @Bindable
    private String question;
    @Bindable
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
        notifyPropertyChanged(BR.valid);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public boolean isValid() {
        return !TextUtils.isEmpty(question) && !TextUtils.isEmpty(answer);
    }
}
