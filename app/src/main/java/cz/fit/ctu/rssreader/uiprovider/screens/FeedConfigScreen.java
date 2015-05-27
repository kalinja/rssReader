package cz.fit.ctu.rssreader.uiprovider.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.database.tables.FeedTableManager;
import cz.fit.ctu.rssreader.uiprovider.BaseActivity;
import cz.fit.ctu.rssreader.uiprovider.fragments.FeedConfigFragment;
import cz.fit.ctu.rssreader.uiprovider.fragments.FeedDownloadFragment;
import cz.fit.ctu.rssreader.utils.Logger;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;


public class FeedConfigScreen extends BaseActivity implements TaskCallbacks {
    private FeedConfigFragment fragment;
    private FeedDownloadFragment downloadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_config_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFragments();
    }

    private void initFragments() {
        fragment = (FeedConfigFragment) getFragmentManager().findFragmentByTag("fragment");
        if (fragment == null) {
            fragment = new FeedConfigFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment, "fragment");
            transaction.commit();
        }
        downloadFragment = (FeedDownloadFragment) getFragmentManager().findFragmentByTag("download");
        if (downloadFragment == null) {
            downloadFragment = new FeedDownloadFragment();
            getFragmentManager().beginTransaction().add(downloadFragment, "download").commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (fragment.getNewFeedDialog() != null) {
            fragment.getNewFeedDialog().cancel();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_config_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreExecute() {
        fragment.getProgress().setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute() {
        fragment.getFeedsAdapter().setLoadingNewFeed(false);
        if (downloadFragment.getFeed().getState() == Feed.State.COMPLETED) {
            FeedTableManager.insertFeed(getContentResolver(), downloadFragment.getFeed());
            Cursor newCursor = FeedTableManager.getFeedListCursor(getContentResolver());
            fragment.getFeedsAdapter().changeCursor(newCursor);
        }
        /*if (downloadFragment.getFeed().getState() == Feed.State.FAILED_WITHOUT_LOGIN && downloadFragment.getUsername() == null){
            fragment.getFeedsAdapter().tryToLogin(downloadFragment.getFeedUrl());
        }*/
        if (downloadFragment.getFeed().getState() == Feed.State.FAILED_WITH_LOGIN) {
            Logger.makeToast(this, getResources().getString(R.string.failed_to_add_feed));
        }
        fragment.getProgress().setVisibility(View.GONE);
        downloadFragment.setDownloading(false);
    }

    public FeedDownloadFragment getDownloadFragment() {
        return downloadFragment;
    }
}
