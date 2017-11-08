package com.github.mag0716.memorytraining.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.github.mag0716.memorytraining.service.alarmmanager.AlarmManagerRegister;
import com.github.mag0716.memorytraining.service.gcmnetworkmanager.GcmNetworkManagerRegister;

import java.util.List;

/**
 * タスク登録種別
 * <p>
 * Created by mag0716 on 2017/09/16.
 */
public enum TaskRegisterType {

    GCM_NETWORK_MANAGER(new GcmNetworkManagerRegister()),
    ALARM_MANAGER(new AlarmManagerRegister()),
    // TODO: ITaskRegister の実体を追加
    JOB_DISPATCHER(null),
    JOB_SCHEDULER(null);

    TaskRegisterType(ITaskRegister taskRegister) {
        this.taskRegister = taskRegister;
    }

    private final ITaskRegister taskRegister;

    public ITaskRegister getTaskRegister() {
        return taskRegister;
    }

    public static List<String> getRegisterNameList(@NonNull Context context) {
        return Stream.of(TaskRegisterType.values())
                .filter(type -> type.getTaskRegister() != null)
                .map(taskRegisterType -> taskRegisterType.getTaskRegister().getName(context))
                .toList();
    }

}
