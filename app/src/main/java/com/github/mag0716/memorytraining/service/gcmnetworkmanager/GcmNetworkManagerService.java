package com.github.mag0716.memorytraining.service.gcmnetworkmanager;

import com.github.mag0716.memorytraining.Application;
import com.github.mag0716.memorytraining.notification.NotificationConductor;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import timber.log.Timber;

/**
 * Created by mag0716 on 2017/06/27.
 */
public class GcmNetworkManagerService extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {
        Timber.d("onRunTask : tag = %s", taskParams.getTag());

        final Application application = ((Application) getApplication());

        application.getTrackerConductor().trackNotifyEvent(getApplicationContext(), getClass().getSimpleName(),
                taskParams.getExtras().getLong(TaskConductor.TASK_EXTRAS_TRAINING_DATETIME_KEY));

        NotificationConductor.notifyTrainingIfNeeded(getApplicationContext(),
                application.getDatabase().memoryDao());

        application.getTaskConductor().registerTaskIfNeeded();

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        Timber.d("onInitializeTasks");
        // TODO: Play Services, アプリのアップデートでタスクがクリアされるので、再セットする
    }
}
