package com.github.mag0716.memorytraining.service.gcmnetworkmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.mag0716.memorytraining.R;
import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.service.ITaskRegister;
import com.github.mag0716.memorytraining.service.TaskConductor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;

import java.util.concurrent.TimeUnit;

/**
 * GcmNetworkManager を利用してタスクを登録する
 * <p>
 * <p>
 * Created by mag0716 on 2017/09/14.
 */
public class GcmNetworkManagerRegister implements ITaskRegister {

    private static final String TASK_TAG = "MemoryTask";

    @NonNull
    @Override
    public String getName(@NonNull Context context) {
        return context.getString(R.string.setting_api_gcmnetworkmanager_name);
    }

    @NonNull
    @Override
    public String getDescription(@NonNull Context context) {
        return context.getString(R.string.setting_api_gcmnetworkmanager_description);
    }

    @Override
    public boolean isAvailable(@NonNull Context context) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        return availability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    @Override
    public boolean isResolvable(@NonNull Context context) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        return availability.isUserResolvableError(availability.isGooglePlayServicesAvailable(context));
    }

    @Override
    public void registerTask(@NonNull Context context, @NonNull Memory memory) {
        final long delayMilliseconds = memory.getNextTrainingDatetime() - System.currentTimeMillis();
        final Bundle extras = new Bundle();
        extras.putLong(TaskConductor.TASK_EXTRAS_TRAINING_DATETIME_KEY, memory.getNextTrainingDatetime());
        final GcmNetworkManager manager = GcmNetworkManager.getInstance(context);
        final OneoffTask task = new OneoffTask.Builder()
                .setService(GcmNetworkManagerService.class)
                .setTag(TASK_TAG)
                .setExtras(extras)
                .setExecutionWindow(TimeUnit.MILLISECONDS.toSeconds(delayMilliseconds),
                        TimeUnit.MILLISECONDS.toSeconds(delayMilliseconds) + 60L) // TODO: 固定値の調整
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)
                .setRequiresCharging(false)
                .setPersisted(false)
                .setUpdateCurrent(true)
                .build();
        manager.schedule(task);
    }

    @Override
    public void unregisterTask(@NonNull Context context) {
        final GcmNetworkManager manager = GcmNetworkManager.getInstance(context);
        manager.cancelAllTasks(GcmNetworkManagerService.class);
    }
}
