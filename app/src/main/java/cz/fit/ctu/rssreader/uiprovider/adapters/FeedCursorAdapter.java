package cz.fit.ctu.rssreader.uiprovider.adapters;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.background.FeedDownloaderTask;
import cz.fit.ctu.rssreader.database.tables.FeedTableManager;
import cz.fit.ctu.rssreader.uiprovider.fragments.FeedDownloadFragment;
import cz.fit.ctu.rssreader.uiprovider.viewholders.FeedViewHolder;
import cz.fit.ctu.rssreader.utils.CursorRecyclerViewAdapter;
import cz.fit.ctu.rssreader.utils.LoginDialog;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedCursorAdapter extends CursorRecyclerViewAdapter<FeedViewHolder> {

    private Activity activity;
    private ProgressBar progress;
    private boolean loadingNewFeed = false;
    private FeedDownloadFragment fragment;

    public FeedCursorAdapter(Activity activity, FeedDownloadFragment fragment, Cursor cursor, ProgressBar progress) {
        super(activity, cursor);
        this.activity = activity;
        this.progress = progress;
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder viewHolder, Cursor cursor) {
        Feed feed = FeedTableManager.feedFromCursor(cursor);
        viewHolder.onFeedLoaded(feed);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_layout, parent, false);
        return new FeedViewHolder(activity, this, itemView);
    }

    public void addNewFeed(final String feedUrl){
        loadingNewFeed = true;
        fragment.setFeed(new Feed());
        fragment.setFeedUrl(feedUrl);
        fragment.executeTask();
    }

    public void tryToLogin(final String feedUrl){
        LoginDialog loginDialog = new LoginDialog(activity, this, feedUrl);
        loginDialog.show();
    }

    public void addNewFeedWithLogin(final String feedUrl, final String username, final String password){
        fragment.setFeed(new Feed());
        fragment.setFeedUrl(feedUrl);
        fragment.setUsername(username);
        fragment.setPassword(password);
        try {
            fragment.executeTaskWithLogin();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setLoadingNewFeed(boolean loadingNewFeed) {
        this.loadingNewFeed = loadingNewFeed;
    }

    public boolean isLoadingNewFeed() {
        return loadingNewFeed;
    }
}
