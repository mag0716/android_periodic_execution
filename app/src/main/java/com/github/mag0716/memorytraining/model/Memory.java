package com.github.mag0716.memorytraining.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mag0716 on 2017/04/30.
 */
public class Memory implements Parcelable {

    /**
     * ID
     */
    private long mId;

    /**
     * 質問
     */
    private String mQuestion;

    /**
     * 回答
     */
    private String mAnswer;

    /**
     * 訓練レベル
     */
    private int mLevel;

    /**
     * 訓練回数
     */
    private int mCount;

    /**
     * 次回訓練予定日時
     */
    private long mNextTrainingDatetime;

    // region Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mQuestion);
        dest.writeString(this.mAnswer);
        dest.writeInt(this.mLevel);
        dest.writeInt(this.mCount);
        dest.writeLong(this.mNextTrainingDatetime);
    }

    public Memory() {
    }

    private Memory(Parcel in) {
        this.mId = in.readLong();
        this.mQuestion = in.readString();
        this.mAnswer = in.readString();
        this.mLevel = in.readInt();
        this.mCount = in.readInt();
        this.mNextTrainingDatetime = in.readLong();
    }

    public static final Parcelable.Creator<Memory> CREATOR = new Parcelable.Creator<Memory>() {
        @Override
        public Memory createFromParcel(Parcel source) {
            return new Memory(source);
        }

        @Override
        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };

    // endregion
}
