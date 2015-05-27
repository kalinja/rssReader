package cz.fit.ctu.rssreader.uiprovider.viewholders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.database.tables.FeedTableManager;
import cz.fit.ctu.rssreader.listeners.OnFeedLoadedListener;
import cz.fit.ctu.rssreader.uiprovider.adapters.FeedCursorAdapter;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedViewHolder extends RecyclerView.ViewHolder implements OnFeedLoadedListener{
    private TextView feedName;
    private TextView feedUrl;
    private Context context;
    private Feed feed;
    private FeedCursorAdapter adapter;

    public FeedViewHolder(Context context, FeedCursorAdapter adapter, View itemView) {
        super(itemView);
        this.context = context;
        this.adapter = adapter;
        itemView.setOnClickListener(onClickListener);
        feedName = (TextView) itemView.findViewById(R.id.feed_name_view);
        feedUrl = (TextView) itemView.findViewById(R.id.feed_url_view);
    }

    @Override
    public void onFeedLoaded(Feed feed) {
        this.feed = feed;
        feedName.setText(feed.getName());
        feedUrl.setText(feed.getUrl());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder  builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.delete_feed));
            builder.setMessage(R.string.confirm_delete_message);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FeedTableManager.deleteFeed(context.getContentResolver(), feed.getId());
                    Cursor newCursor = FeedTableManager.getFeedListCursor(context.getContentResolver());
                    adapter.changeCursor(newCursor);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
}
