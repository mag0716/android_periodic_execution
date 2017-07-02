package com.github.mag0716.memorytraining.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mag0716 on 2017/07/02.
 */
public final class DatetimeUtil {

    private DatetimeUtil() {
    }

    /**
     * デバッグで確認しやすいフォーマットに変換
     *
     * @param datetime 日時
     * @return yyyy/MM/dd HH:mm:ss フォーマットの文字列
     */
    public static String convertDebugFormat(long datetime) {
        final SimpleDateFormat debugFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        return debugFormat.format(new Date(datetime));
    }
}
