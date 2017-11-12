package com.github.mag0716.memorytraining.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.service.TaskRegisterType;

import java.util.List;

import timber.log.Timber;

/**
 * 定期実行 API 設定画面
 * <p>
 * Created by mag0716 on 2017/10/30.
 */
public class SettingApiFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

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
        final ListPreference listPreference = (ListPreference) findPreference(getString(R.string.setting_api_key));
        listPreference.setTitle(getString(R.string.setting_api_title));
        listPreference.setSummary(getString(R.string.setting_api_summary));
        final List<String> taskRegisterList = TaskRegisterType.getRegisterNameList(getContext());
        listPreference.setEntries(taskRegisterList.toArray(new CharSequence[taskRegisterList.size()]));
        listPreference.setEntryValues(taskRegisterList.toArray(new CharSequence[taskRegisterList.size()]));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final String taskRegisterName = sharedPreferences.getString(key, null);
        Timber.d("onSharedPreferenceChanged : %s", taskRegisterName);
        ((Application) getActivity().getApplication()).getTaskConductor().updateTaskRegister(TaskRegisterType.getTaskRegister(getContext(), taskRegisterName));
        // TODO: エラーで利用できない項目を選択したら復旧させてみる
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
