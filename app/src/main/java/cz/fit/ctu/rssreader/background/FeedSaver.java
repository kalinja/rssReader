package cz.fit.ctu.rssreader.background;

import android.content.Context;
import android.text.Html;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.database.tables.ArticleTableManager;

/**
 * Created by Jakub on 20. 3. 2015.
 */
public class FeedSaver extends Thread {

    private Context context;
    private SyndFeed syndFeed;
    private Feed feed;

    public FeedSaver(Context context, SyndFeed syndFeed, Feed feed) {
        this.context = context;
        this.syndFeed = syndFeed;
        this.feed = feed;
    }

    @Override
    public void run() {
        super.run();
        List syndFeeds = syndFeed.getEntries();
        for (Object entryObject : syndFeeds){
            SyndEntry entry = (SyndEntry) entryObject;
            Article article = new Article();
            article.setFeedId(feed.getId());
            article.setDate(getDateString(entry.getPublishedDate()));
            article.setLink(entry.getLink());
            article.setTitle(entry.getTitle());
            article.setArticleContent(getContent(entry));
            article.setSummary(getSummary(article.getArticleContent()));
            int existingId = ArticleTableManager.articleExists(context.getContentResolver(), article.getLink());
            if (existingId > 0){
                article.setId(existingId);
                ArticleTableManager.updateArticle(context.getContentResolver(), article);
            }else {
                ArticleTableManager.insertArticle(context.getContentResolver(), article);
            }
        }
    }

    private String getDateString(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("dd. MMMM yyyy HH:mm");
        return dt.format(date);
    }

    private String getContent(SyndEntry entry){
        if (entry.getContents().size() > 0) {
            String contentString = "";
            for (int i = 0; i < entry.getContents().size();i++){
                contentString += ((SyndContent) entry.getContents().get(i)).getValue();
            }
            return contentString;
        }
        if (entry.getDescription() != null){
            return  entry.getDescription().getValue();
        }else{
            return "";
        }
    }

    private String getSummary(String content){
        content = content.replaceAll("(?s)]*>.*?", "").replaceAll("(?s)", "");
        content = Html.fromHtml(content).toString();
        String summary;
        if (content.length() > Article.SUMMARY_LENGTH){
            summary = content.substring(0, Article.SUMMARY_LENGTH);
        }else{
            summary = content;
        }
        return summary;
    }
}
