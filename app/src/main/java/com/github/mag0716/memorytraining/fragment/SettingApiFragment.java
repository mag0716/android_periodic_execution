package com.github.mag0716.memorytraining.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.github.mag0716.memorytraining.R;

/**
 * 定期実行 API 設定画面
 * <p>
 * Created by mag0716 on 2017/10/30.
 */
public class SettingApiFragment extends PreferenceFragmentCompat {

    public static final String TAG = SettingApiFragment.class.getCanonicalName();

    /**
     * SettingApiFragment 生成
     *
     * @return SettingApiFragment
     */
    public static SettingApiFragment newInstance() {
        return new SettingApiFragment();
    }

    /**
     * コンストラクタ
     */
    public SettingApiFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_api_preferences, rootKey);
    }
}
