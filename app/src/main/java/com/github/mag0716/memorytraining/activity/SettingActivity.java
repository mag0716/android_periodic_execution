package com.github.mag0716.memorytraining.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.databinding.ActivitySettingBinding;
import com.github.mag0716.memorytraining.fragment.SettingApiFragment;

/**
 * 設定画面
 */
public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, SettingApiFragment.newInstance(), SettingApiFragment.TAG);
            fragmentTransaction.commit();
        }
    }
}
