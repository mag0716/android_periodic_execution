package com.github.mag0716.memorytraining.model;

import android.support.annotation.IntRange;

import java.util.concurrent.TimeUnit;

import lombok.Getter;

/**
 * 訓練レベル
 * // TODO: 訓練回数による level down, up 処理修正 & 訓練間隔をランダムにする
 * <p>
 * Created by mag0716 on 2017/06/20.
 */
public enum Level {

    LEVEL1(0) {
        @Override
        long getTrainingInterval() {
            return TimeUnit.SECONDS.toMillis(30);
        }

        @Override
        Level getPreviousLevel(int trainingCount) {
            return LEVEL1;
        }

        @Override
        Level getNextLevel(int trainingCount) {
            return LEVEL2;
        }
    },
    LEVEL2(1) {
        @Override
        long getTrainingInterval() {
            return TimeUnit.MINUTES.toMillis(3);
        }

        @Override
        Level getPreviousLevel(int trainingCount) {
            return LEVEL1;
        }

        @Override
        Level getNextLevel(int trainingCount) {
            return LEVEL3;
        }
    },
    LEVEL3(2) {
        @Override
        long getTrainingInterval() {
            return TimeUnit.HOURS.toMillis(1);
        }

        @Override
        Level getPreviousLevel(int trainingCount) {
            return LEVEL2;
        }

        @Override
        Level getNextLevel(int trainingCount) {
            return LEVEL4;
        }
    },
    LEVEL4(3) {
        @Override
        long getTrainingInterval() {
            return TimeUnit.DAYS.toMillis(1);
        }

        @Override
        Level getPreviousLevel(int trainingCount) {
            return LEVEL3;
        }

        @Override
        Level getNextLevel(int trainingCount) {
            return LEVEL5;
        }
    },
    LEVEL5(4) {
        @Override
        long getTrainingInterval() {
            return TimeUnit.DAYS.toMillis(30);
        }

        @Override
        Level getPreviousLevel(int trainingCount) {
            return LEVEL4;
        }

        @Override
        Level getNextLevel(int trainingCount) {
            return LEVEL5;
        }
    };

    /**
     * ID
     */
    @Getter
    private final int id;

    Level(int id) {
        this.id = id;
    }

    /**
     * 訓練間隔取得
     *
     * @return 訓練間隔[msec]
     */
    abstract long getTrainingInterval();

    /**
     * 前のレベルを取得する
     *
     * @param trainingCount 現在のレベルでの訓練回数
     * @return 前のレベル
     */
    abstract Level getPreviousLevel(@IntRange(from = 0) int trainingCount);

    /**
     * 次のレベルを取得する
     *
     * @param trainingCount 現在のレベルでの訓練回数
     * @return 次のレベル
     */
    abstract Level getNextLevel(@IntRange(from = 0) int trainingCount);

}
