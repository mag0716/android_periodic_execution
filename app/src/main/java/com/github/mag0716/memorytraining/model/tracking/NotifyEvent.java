package com.github.mag0716.memorytraining.model.tracking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.NonNull;

/**
 * Created by mag0716 on 2017/08/31.
 */

public class NotifyEvent {

    private final String type;
    private final long accuracy;
    private final Param charge;
    private final Param network;
    private final Param display;
    private final Param doze;

    public NotifyEvent(@NonNull Context context, @NonNull String type, long notifyDatetime) {
        this.type = type;
        this.accuracy = System.currentTimeMillis() - notifyDatetime;
        this.charge = checkBatteryManager(context);
        this.network = checkConnectivityManager(context);
        // TODO: メソッドに分離
        final PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            final boolean isDisplayEnabled;
            if (Build.VERSION.SDK_INT < 20) {
                isDisplayEnabled = powerManager.isScreenOn();
            } else {
                isDisplayEnabled = powerManager.isInteractive();
            }
            this.display = Param.create(isDisplayEnabled);
            this.doze = Build.VERSION.SDK_INT < 23 ? Param.UNKNOWN : Param.create(powerManager.isDeviceIdleMode());
        } else {
            this.display = Param.UNKNOWN;
            this.doze = Param.UNKNOWN;
        }
    }

    @NonNull
    public String getType() {
        return type;
    }

    public long getAccuracy() {
        return accuracy;
    }

    @NonNull
    public Param getCharge() {
        return charge;
    }

    @NonNull
    public Param getNetwork() {
        return network;
    }

    @NonNull
    public Param getDisplay() {
        return display;
    }

    @NonNull
    public Param getDoze() {
        return doze;
    }

    private Param checkBatteryManager(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            final BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            if (batteryManager == null) {
                return Param.UNKNOWN;
            }
            return Param.create(batteryManager.isCharging());
        }
        return Param.UNKNOWN;
    }

    private Param checkConnectivityManager(@NonNull Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return Param.UNKNOWN;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return Param.create(networkInfo != null && networkInfo.isConnected());
    }

    private Param checkScreenEnabled(@NonNull Context context) {
        return Param.UNKNOWN;
    }

    private Param checkDozeMode(@NonNull Context context) {
        return Param.UNKNOWN;
    }

    public enum Param {
        /**
         * 有効
         */
        ON,
        /**
         * 無効
         */
        OFF,
        /**
         * バージョン制限などによる取得不可など
         */
        UNKNOWN;

        static Param create(boolean enabled) {
            return enabled ? ON : OFF;
        }
    }
}
