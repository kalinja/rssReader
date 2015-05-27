package cz.fit.ctu.rssreader.database.tables;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.articles.FeedList;
import cz.fit.ctu.rssreader.database.DatabaseContentProvider;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedTableManager {

    public static void insertFeed(ContentResolver contentResolver, Feed feed) {
        ContentValues cv = new ContentValues();
        cv.put(FeedTable.COLUMN_NAME_NAME, feed.getName());
        cv.put(FeedTable.COLUMN_URL_NAME, feed.getUrl());
        if (!feed.getAuth().equals("")) {
            cv.put(FeedTable.COLUMN_AUTH_NAME, feed.getAuth());
        }
        Uri resultUri = contentResolver.insert(DatabaseContentProvider.CONTENT_URI_FEEDS, cv);
        feed.setId(Integer.parseInt(resultUri.getPathSegments().get(1)));
    }

    public static void deleteFeed(ContentResolver contentResolver, int feedId) {
        String selection = "id=?";
        String selectionArgs[] = {feedId + ""};
        int rowsDeleted = contentResolver.delete(DatabaseContentProvider.CONTENT_URI_FEEDS, selection, selectionArgs);
        ArticleTableManager.deleteArticleByFeedId(contentResolver, feedId);
    }

    public static void updateFeed(ContentResolver contentResolver, Feed newFeed) {
        ContentValues cv = new ContentValues();
        cv.put(FeedTable.COLUMN_NAME_NAME, newFeed.getName());
        cv.put(FeedTable.COLUMN_URL_NAME, newFeed.getUrl());
        if (!newFeed.getAuth().equals("")) {
            cv.put(FeedTable.COLUMN_AUTH_NAME, newFeed.getAuth());
        }
        String selection = "id=?";
        String selectionArgs[] = {newFeed.getId() + ""};
        int rowsUpdated = contentResolver.update(DatabaseContentProvider.CONTENT_URI_ARTICLES, cv, selection, selectionArgs);
    }

    public static Cursor getFeedListCursor(ContentResolver contentResolver) {
        String projection[] = FeedTable.getFeedColumns();
        return contentResolver.query(DatabaseContentProvider.CONTENT_URI_FEEDS, projection, null, null, null);
    }

    public static Feed feedFromCursor(Cursor cursor){
        Feed feed = new Feed();
        //{COLUMN_ID_NAME, COLUMN_NAME_NAME, COLUMN_URL_NAME, COLUMN_AUTH_NAME}
        feed.setId(cursor.getInt(0));
        feed.setName(cursor.getString(1));
        feed.setUrl(cursor.getString(2));
        feed.setAuth(cursor.getString(3));
        return feed;
    }

    public static FeedList getFeedList(ContentResolver contentResolver) {
        String projection[] = FeedTable.getFeedColumns();
        Cursor cursor = contentResolver.query(DatabaseContentProvider.CONTENT_URI_FEEDS, projection, null, null, null);
        FeedList feedList = new FeedList();
        while(cursor.moveToNext()){
            Feed feed = feedFromCursor(cursor);
            feedList.add(feed);
        }
        cursor.close();
        return feedList;
    }
}
