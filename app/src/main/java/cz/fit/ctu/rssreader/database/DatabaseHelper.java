package cz.fit.ctu.rssreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.fit.ctu.rssreader.database.tables.ArticleTable;
import cz.fit.ctu.rssreader.database.tables.FeedTable;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reader.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArticleTable.onCreate(db);
        FeedTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArticleTable.onUpgrade(db);
        FeedTable.onUpgrade(db);
    }
}
