package cz.fit.ctu.rssreader.uiprovider.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import cz.fit.ctu.rssreader.background.FeedUpdaterTask;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

/**
 * Created by Jakub on 27. 3. 2015.
 */
public class FeedRefreshFragment extends Fragment {
    private FeedUpdaterTask feedUpdaterTask = null;
    private TaskCallbacks callbacks;

    private boolean isRefreshing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (TaskCallbacks) activity;
    }

    public void executeTask() {
        isRefreshing = true;
        feedUpdaterTask = new FeedUpdaterTask(getActivity(), callbacks);
        feedUpdaterTask.execute();
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }
}
