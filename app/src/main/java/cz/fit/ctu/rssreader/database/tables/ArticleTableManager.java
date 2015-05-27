package cz.fit.ctu.rssreader.database.tables;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.database.DatabaseContentProvider;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class ArticleTableManager {

    public static void insertArticle(ContentResolver contentResolver, Article article){
        ContentValues cv = new ContentValues();
        cv.put(ArticleTable.COLUMN_TITLE_NAME, article.getTitle());
        cv.put(ArticleTable.COLUMN_SUMMARY_NAME, article.getSummary());
        cv.put(ArticleTable.COLUMN_CONTENT_NAME, article.getArticleContent());
        cv.put(ArticleTable.COLUMN_LINK_NAME, article.getLink());
        cv.put(ArticleTable.COLUMN_DATE_NAME, article.getDate());
        cv.put(ArticleTable.COLUMN_FEED_ID_NAME, article.getFeedId());
        Uri resultUri = contentResolver.insert(DatabaseContentProvider.CONTENT_URI_ARTICLES, cv);
        article.setId(Integer.parseInt(resultUri.getPathSegments().get(1)));
    }

    public static void deleteArticle(ContentResolver contentResolver, int articleId){
        String selection = "id=?";
        String selectionArgs[] = {articleId + ""};
        int rowsDeleted = contentResolver.delete(DatabaseContentProvider.CONTENT_URI_ARTICLES, selection, selectionArgs);
    }

    public static void updateArticle(ContentResolver contentResolver, Article newArticle){
        ContentValues cv = new ContentValues();
        cv.put(ArticleTable.COLUMN_TITLE_NAME, newArticle.getTitle());
        cv.put(ArticleTable.COLUMN_SUMMARY_NAME, newArticle.getSummary());
        cv.put(ArticleTable.COLUMN_CONTENT_NAME, newArticle.getArticleContent());
        cv.put(ArticleTable.COLUMN_LINK_NAME, newArticle.getLink());
        cv.put(ArticleTable.COLUMN_DATE_NAME, newArticle.getDate());
        cv.put(ArticleTable.COLUMN_FEED_ID_NAME, newArticle.getFeedId());
        String selection = ArticleTable.COLUMN_ID_NAME + "=?";
        String selectionArgs[] = {newArticle.getId() + ""};
        int rowsUpdated = contentResolver.update(DatabaseContentProvider.CONTENT_URI_ARTICLES, cv, selection, selectionArgs);
    }

    public static Cursor getArticleListCursor(ContentResolver contentResolver){
        String projection[] = ArticleTable.getArticleHeaderColumns();
        String sortBy = ArticleTable.COLUMN_DATE_NAME;
        return contentResolver.query(DatabaseContentProvider.CONTENT_URI_ARTICLES, projection, null, null, sortBy);
    }

    public static Article getLastArticle(ContentResolver contentResolver){
        String projection[] = ArticleTable.getArticleHeaderColumns();
        String sortBy = ArticleTable.COLUMN_DATE_NAME;
        Cursor cursor = contentResolver.query(DatabaseContentProvider.CONTENT_URI_ARTICLES, projection, null, null, sortBy);
        Article article = null;
        if (cursor.moveToFirst()){
            article = articleHeaderFromCursor(cursor);
        }
        cursor.close();
        if (article != null){
            loadArticleDetail(contentResolver, article);
        }
        return article;
    }

    public static Article articleHeaderFromCursor(Cursor cursor){
        Article article = new Article();
        //{COLUMN_ID_NAME, COLUMN_TITLE_NAME, COLUMN_SUMMARY_NAME, COLUMN_LINK_NAME, COLUMN_DATE_NAME, COLUMN_FEED_ID_NAME}
        article.setId(cursor.getInt(0));
        article.setTitle(cursor.getString(1));
        article.setSummary(cursor.getString(2));
        article.setLink(cursor.getString(3));
        article.setDate(cursor.getString(4));
        article.setFeedId(cursor.getInt(5));
        return article;
    }

    public static void loadArticleDetail(ContentResolver contentResolver, Article article){
        String projection[] = ArticleTable.getArticleDetailColumns();
        String selection = ArticleTable.COLUMN_ID_NAME + "=?";
        String selectionArgs[] = {article.getId() + ""};
        Cursor cursor = contentResolver.query(DatabaseContentProvider.CONTENT_URI_ARTICLES, projection, selection, selectionArgs, null);
        if (cursor.moveToFirst()){
            String articleContent = cursor.getString(1);
            article.setArticleContent(articleContent);
        }
        cursor.close();
    }

    public static void deleteArticleByFeedId(ContentResolver contentResolver, int feedId) {
        String selection = ArticleTable.COLUMN_FEED_ID_NAME + "=?";
        String selectionArgs[] = {feedId + ""};
        int rowsDeleted = contentResolver.delete(DatabaseContentProvider.CONTENT_URI_ARTICLES, selection, selectionArgs);
    }

    public static int articleExists(ContentResolver contentResolver, String link){
        String projection[] = ArticleTable.getArticleHeaderColumns();
        String selection = ArticleTable.COLUMN_LINK_NAME + "=?";
        String selectionArgs[] = {link + ""};
        Cursor cursor = contentResolver.query(DatabaseContentProvider.CONTENT_URI_ARTICLES, projection, selection, selectionArgs, null);
        int existingId = -1;
        if (cursor.moveToFirst()){
            existingId = cursor.getInt(0);
        }
        cursor.close();
        return existingId;
    }
}
