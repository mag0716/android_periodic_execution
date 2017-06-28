package com.github.mag0716.memorytraining.service.gcmnetworkmanager;

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
        // TODO: 現在日時を超えたデータがあれば BroadcastReceiver に通知を投げる
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        Timber.d("onInitializeTasks");
        // TODO: Play Services, アプリのアップデートでタスクがクリアされるので、再セットする
    }
}
