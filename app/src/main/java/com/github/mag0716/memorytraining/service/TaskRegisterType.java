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

    /**
     * 選択可能な登録 API 一覧を返却
     *
     * @param context Context
     * @return 選択可能な登録 API 一覧
     */
    public static List<String> getRegisterNameList(@NonNull Context context) {
        return Stream.of(TaskRegisterType.values())
                // TODO: SettingApiFragment 側で復旧処理を実装したら利用不可でも復旧可能だったら選択可能にする
                .filter(type -> type.getTaskRegister() != null && type.getTaskRegister().isAvailable(context))
                .map(taskRegisterType -> taskRegisterType.getTaskRegister().getName(context))
                .toList();
    }

}
