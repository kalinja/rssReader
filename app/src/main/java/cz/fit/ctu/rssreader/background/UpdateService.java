package cz.fit.ctu.rssreader.background;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import cz.fit.ctu.rssreader.utils.Logger;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

/**
 * Created by Jakub on 24. 4. 2015.
 */
public class UpdateService extends IntentService implements TaskCallbacks{
    private final IBinder binder = new UpdateBinder();
    private TaskCallbacks callbacks;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        FeedUpdaterTask updateTask;
        updateTask = new FeedUpdaterTask(getBaseContext(), this);
        updateTask.execute();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onPreExecute() {
        //Logger.debug("UpdateService", "service refresh feed start");
        if (callbacks != null){
            callbacks.onPreExecute();
        }
    }

    @Override
    public void onPostExecute() {
        if (callbacks != null) {
            callbacks.onPostExecute();
        }
    }

    public class UpdateBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    public void registerListener(TaskCallbacks callbacks){
        this.callbacks = callbacks;
    }

    public void unregisterListener(){
        callbacks = null;
    }
}
