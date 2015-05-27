package cz.fit.ctu.rssreader.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import cz.fit.ctu.rssreader.utils.Logger;

/**
 * Created by Jakub on 24. 4. 2015.
 */
public class SynchronizeScheduler {
    public static void setUpSyncTime(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES/90, pendingIntent);

        Logger.debug("SynchronizeScheduler", "Alarm was set.");
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
