package com.github.mag0716.memorytraining.service.gcmnetworkmanager;

import android.os.Bundle;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.repository.database.MemoryDao;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;

/**
 * Created by mag0716 on 2017/06/27.
 */
public class GcmNetworkManagerService extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {
        Timber.d("onRunTask : tag = %s", taskParams.getTag());

        // TODO: 別クラスにトラッキング処理をまとめる
        final FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getApplicationContext());
        final Bundle bundle = new Bundle();
        bundle.putString("type", getClass().getSimpleName());
        bundle.putLong("accuracy", System.currentTimeMillis() - taskParams.getExtras().getLong(TaskConductor.TASK_EXTRAS_TRAINING_DATETIME_KEY));
        analytics.logEvent("notify", bundle);

        final MemoryDao dao = ((Application) getApplication()).getDatabase().memoryDao();
        NotificationConductor.notifyTrainingIfNeeded(getApplicationContext(), dao);
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        Timber.d("onInitializeTasks");
        // TODO: Play Services, アプリのアップデートでタスクがクリアされるので、再セットする
    }
}
