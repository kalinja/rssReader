package cz.fit.ctu.rssreader.background;

import android.content.Context;
import android.os.AsyncTask;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.net.URL;

import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.articles.FeedList;
import cz.fit.ctu.rssreader.database.tables.FeedTableManager;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

/**
 * Created by Jakub on 20. 3. 2015.
 */
public class FeedUpdaterTask extends AsyncTask<Void, Void, Void> {
    private TaskCallbacks callbacks;
    private Context context;

    public FeedUpdaterTask(Context context, TaskCallbacks callbacks) {
        this.callbacks = callbacks;
        this.context = context;
    }

    private void updateFeed(Feed feed) throws Exception {
        URL url = new URL(feed.getUrl());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        SyndFeed feeder;
        SyndFeedInput input = new SyndFeedInput();
        if (feed.getAuth() != null && !feed.getAuth().equals("")) {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(feed.getUrl());
            get.addHeader("Authorization", "Basic " + feed.getAuth());
            HttpResponse response = client.execute(get);
            feeder = input.build(new XmlReader(response.getEntity().getContent()));
        } else {
            feeder = input.build(new XmlReader(con));
        }
        FeedSaver saver = new FeedSaver(context, feeder, feed);
        saver.run();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callbacks.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        FeedList feedList = FeedTableManager.getFeedList(context.getContentResolver());
        for (int i = 0; i < feedList.size(); i++) {
            try {
                updateFeed(feedList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callbacks.onPostExecute();
    }
}
