package com.github.mag0716.memorytraining.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.IntRange;
import android.support.annotation.VisibleForTesting;

import com.github.mag0716.memorytraining.util.DatetimeUtil;

import java.util.Locale;

/**
 * 訓練対象データ
 * <p>
 * Created by mag0716 on 2017/04/30.
 */
@Entity(indices = {@Index("level")})
public class Memory implements Parcelable {

    /**
     * ID
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    private long id;

    /**
     * 質問
     */
    private String question;

    /**
     * 回答
     */
    private String answer;

    /**
     * 訓練レベル
     */
    private int level;

    /**
     * 訓練回数
     */
    private int count;

    /**
     * 次回訓練予定日時
     */
    @ColumnInfo(name = "next_training_datetime")
    private long nextTrainingDatetime;

    @Ignore
    @VisibleForTesting
    public Memory(long id, String question, String answer, int level, int count) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.count = count;
    }

    @Ignore
    @VisibleForTesting
    public Memory(long id, String question, String answer, int level, int count, long nextTrainingDatetime) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.count = count;
        this.nextTrainingDatetime = nextTrainingDatetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getNextTrainingDatetime() {
        return nextTrainingDatetime;
    }

    public void setNextTrainingDatetime(long nextTrainingDatetime) {
        this.nextTrainingDatetime = nextTrainingDatetime;
    }

    public void levelUp(@IntRange(from = 0, to = 4) int nextLevel) {
        if (this.level == nextLevel) {
            count++;
        } else {
            count = 0;
        }
        this.level = nextLevel;
    }

    public void levelDown(@IntRange(from = 0, to = 4) int nextLevel) {
        count = 0;
        this.level = nextLevel;
    }

    @Override
    public String toString() {
        final String format = "Memory(id=%d, count=%d, level=%d, next training datetime=%s\nquestion=%s\nanswer=%s)";
        return String.format(Locale.getDefault(),
                format,
                id,
                count,
                level,
                DatetimeUtil.convertDebugFormat(nextTrainingDatetime),
                question,
                answer);
    }

    // region Parcelable

    public Memory() {
    }

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
    // endregion
}
