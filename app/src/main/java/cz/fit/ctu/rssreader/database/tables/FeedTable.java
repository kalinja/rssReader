package cz.fit.ctu.rssreader.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedTable {
    public static final String TABLE_NAME = "feed";

    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_URL_NAME = "url";
    public static final String COLUMN_AUTH_NAME = "auth";

    private static final String CREATE_STATEMENT = "create table " +
            TABLE_NAME + " (" +
            COLUMN_ID_NAME + " integer primary key autoincrement, " +
            COLUMN_NAME_NAME + " text not null, " +
            COLUMN_URL_NAME + " text not null, " +
            COLUMN_AUTH_NAME + " text null" + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table if exists " + TABLE_NAME + ";");
        onCreate(db);
    }

    public static String[] getFeedColumns() {
        return new String[]{COLUMN_ID_NAME, COLUMN_NAME_NAME, COLUMN_URL_NAME, COLUMN_AUTH_NAME};
    }
}
