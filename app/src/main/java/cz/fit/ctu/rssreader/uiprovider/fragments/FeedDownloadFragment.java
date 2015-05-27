package cz.fit.ctu.rssreader.uiprovider.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;

import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.background.FeedDownloaderTask;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

/**
 * Created by Jakub on 27. 3. 2015.
 */
public class FeedDownloadFragment extends Fragment{
    private FeedDownloaderTask feedDownloaderTask = null;
    private TaskCallbacks callbacks;
    private Feed feed;
    private String feedUrl;
    private String username = null;
    private String password = null;

    private boolean isDownloading = false;

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
        isDownloading = true;
        feedDownloaderTask = new FeedDownloaderTask(getActivity(), feed, callbacks, feedUrl);
        feedDownloaderTask.execute();
    }

    public void executeTaskWithLogin() throws UnsupportedEncodingException {
        isDownloading = true;
        feedDownloaderTask = new FeedDownloaderTask(getActivity(), feed, callbacks, feedUrl, username, password);
        feedDownloaderTask.execute();
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
