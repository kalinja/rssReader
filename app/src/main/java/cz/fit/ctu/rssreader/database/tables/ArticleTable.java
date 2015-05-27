package cz.fit.ctu.rssreader.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class ArticleTable {
    public static final String TABLE_NAME = "article";

    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_TITLE_NAME = "title";
    public static final String COLUMN_SUMMARY_NAME = "summary";
    public static final String COLUMN_CONTENT_NAME = "content";
    public static final String COLUMN_LINK_NAME = "link";
    public static final String COLUMN_DATE_NAME = "date";
    public static final String COLUMN_FEED_ID_NAME = "feedId";

    private static final String CREATE_STATEMENT = "create table " +
            TABLE_NAME + " (" +
            COLUMN_ID_NAME + " integer primary key autoincrement, " +
            COLUMN_TITLE_NAME + " text not null, " +
            COLUMN_SUMMARY_NAME + " text not null, " +
            COLUMN_CONTENT_NAME + " text not null, " +
            COLUMN_LINK_NAME + " text not null, " +
            COLUMN_DATE_NAME + " text not null, " +
            COLUMN_FEED_ID_NAME + " integer not null);";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table if exists " + TABLE_NAME + ";");
        onCreate(db);
    }

    public static String[] getArticleHeaderColumns() {
        return new String[]{COLUMN_ID_NAME, COLUMN_TITLE_NAME, COLUMN_SUMMARY_NAME, COLUMN_LINK_NAME, COLUMN_DATE_NAME, COLUMN_FEED_ID_NAME};
    }

    public static String[] getArticleDetailColumns() {
        return new String[]{COLUMN_ID_NAME, COLUMN_CONTENT_NAME};
    }
}
