package com.github.mag0716.memorytraining.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.github.mag0716.memorytraining.R;

/**
 * 設定画面
 */
public class SettingActivity extends AppCompatActivity {

    /**
     * 起動用の Intent を生成
     *
     * @param context Context
     * @return 起動用 Intent
     */
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
