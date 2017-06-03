package com.github.mag0716.memorytraining.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

import lombok.experimental.Accessors;

/**
 * 訓練対象データ
 * <p>
 * Created by mag0716 on 2017/04/30.
 */
@Table
public class Memory implements Parcelable {

    public Memory(@Setter long id,
                  @Setter String question,
                  @Setter String answer,
                  @Setter int level,
                  @Setter int count,
                  @Setter long nextTrainingDatetime) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.count = count;
        this.nextTrainingDatetime = nextTrainingDatetime;
    }

    /**
     * ID
     */
    @PrimaryKey(autoincrement = true)
    @Column
    private long id;

    /**
     * 質問
     */
    @Column
    private String question;

    /**
     * 回答
     */
    @Column
    private String answer;

    /**
     * 訓練レベル
     */
    @Column(indexed = true)
    private int level;

    /**
     * 訓練回数
     */
    @Column
    private int count;

    /**
     * 次回訓練予定日時
     */
    @Column("next_training_datetime")
    @Accessors(fluent = true)
    @lombok.Setter
    private long nextTrainingDatetime;

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getLevel() {
        return level;
    }

    public int getCount() {
        return count;
    }

    public long getNextTrainingDatetime() {
        return nextTrainingDatetime;
    }

    // region Parcelable

    public Memory() {
    }

    // endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.question);
        dest.writeString(this.answer);
        dest.writeInt(this.level);
        dest.writeInt(this.count);
        dest.writeLong(this.nextTrainingDatetime);
    }

    protected Memory(Parcel in) {
        this.id = in.readLong();
        this.question = in.readString();
        this.answer = in.readString();
        this.level = in.readInt();
        this.count = in.readInt();
        this.nextTrainingDatetime = in.readLong();
    }

    public static final Creator<Memory> CREATOR = new Creator<Memory>() {
        @Override
        public Memory createFromParcel(Parcel source) {
            return new Memory(source);
        }

        @Override
        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };
}
