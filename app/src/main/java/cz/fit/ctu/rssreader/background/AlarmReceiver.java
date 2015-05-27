package cz.fit.ctu.rssreader.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jakub on 24. 4. 2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SynchronizeScheduler.setUpSyncTime(context);
    }
}
