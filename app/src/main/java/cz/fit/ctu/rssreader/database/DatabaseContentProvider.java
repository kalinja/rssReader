package cz.fit.ctu.rssreader.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import cz.fit.ctu.rssreader.database.tables.ArticleTable;
import cz.fit.ctu.rssreader.database.tables.FeedTable;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class DatabaseContentProvider extends ContentProvider{
    private static final String AUTHORITY = "cz.fit.ctu.rssreader";

    private static final String ARTICLE_BASE_PATH = "articles";
    private static final String FEED_BASE_PATH = "feeds";
    public static final Uri CONTENT_URI_ARTICLES = Uri.parse("content://" + AUTHORITY + "/" + ARTICLE_BASE_PATH);
    public static final Uri CONTENT_URI_FEEDS = Uri.parse("content://" + AUTHORITY + "/" + FEED_BASE_PATH);
    private static final int ARTICLES = 1;
    private static final int FEEDS = 2;

    private DatabaseHelper databaseHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, ARTICLE_BASE_PATH, ARTICLES);
        uriMatcher.addURI(AUTHORITY, FEED_BASE_PATH, FEEDS);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                cursor = db.query(ArticleTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FEEDS:
                cursor = db.query(FeedTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long id = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                id = database.insert(ArticleTable.TABLE_NAME, null, values);
                break;
            case FEEDS:
                id = database.insert(FeedTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ARTICLE_BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                rowsDeleted = database.delete(ArticleTable.TABLE_NAME, selection, selectionArgs);
                break;
            case FEEDS:
                rowsDeleted = database.delete(FeedTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                rowsUpdated = database.update(ArticleTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FEEDS:
                rowsUpdated = database.update(FeedTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
