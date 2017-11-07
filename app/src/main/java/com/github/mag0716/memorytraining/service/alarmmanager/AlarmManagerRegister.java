package com.github.mag0716.memorytraining.service.alarmmanager;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.AlarmManagerCompat;

import com.github.mag0716.memorytraining.model.Memory;
import com.github.mag0716.memorytraining.receiver.AlarmManagerReceiver;
import com.github.mag0716.memorytraining.service.ITaskRegister;
import com.github.mag0716.memorytraining.service.TaskConductor;

/**
 * Created by mag0716 on 2017/09/20.
 */
public class AlarmManagerRegister implements ITaskRegister {
    @Override
    public boolean isAvailable(@NonNull Context context) {
        // AlarmManager は常に利用可能
        return true;
    }

    @Override
    public boolean isResolvable(@NonNull Context context) {
        // AlarmManager は常に利用可能
        return false;
    }

    @Override
    public void registerTask(@NonNull Context context, @NonNull Memory memory) {
        final Intent intent = new Intent(context, AlarmManagerReceiver.class);
        intent.putExtra(TaskConductor.TASK_EXTRAS_TRAINING_DATETIME_KEY, memory.getNextTrainingDatetime());
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager,
                    AlarmManager.RTC_WAKEUP,
                    memory.getNextTrainingDatetime(),
                    AlarmManagerReceiver.createPendingIntent(context, intent));
        }
    }
}
