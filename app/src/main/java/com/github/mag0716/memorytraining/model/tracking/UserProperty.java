package com.github.mag0716.memorytraining.model.tracking;

/**
 * Created by mag0716 on 2017/08/31.
 */
public class UserProperty {

    private final String level;

    public UserProperty(long trainingCount) {
        this.level = String.valueOf(trainingCount / 100);
    }

    public String getLevel() {
        return level;
    }
}
