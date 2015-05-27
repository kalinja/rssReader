package cz.fit.ctu.rssreader.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.fit.ctu.rssreader.articles.Feed;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedDownloaderTask extends AsyncTask<Void, Void, Void> {
    private String feedUrl;
    private Feed feed;
    private String authKey;
    private Context context;
    private TaskCallbacks callbacks;

    public FeedDownloaderTask(Context context, Feed feed, TaskCallbacks callbacks, String url) {
        this.context = context;
        this.callbacks = callbacks;
        feedUrl = url;
        this.feed = feed;
        authKey = null;
    }

    public FeedDownloaderTask(Context context, Feed feed, TaskCallbacks callbacks, String url, String username, String password) throws UnsupportedEncodingException{
        this.context = context;
        this.callbacks = callbacks;
        feedUrl = url;
        this.feed = feed;
        byte []auth = (username + ":" + password).getBytes();
        authKey = Base64.encodeToString(auth, Base64.NO_WRAP);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        completeHttp();
        callbacks.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(feedUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            SyndFeed feeder;
            SyndFeedInput input = new SyndFeedInput();
            if (authKey != null) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(feedUrl);
                get.addHeader("Authorization", "Basic: " + authKey);
                HttpResponse response = client.execute(get);
                feeder = input.build(new XmlReader(response.getEntity().getContent()));
                feed.setAuth(authKey);
            }else{
                feeder = input.build(new XmlReader(con));
            }
            feed.setName(feeder.getTitle());
            feed.setUrl(feedUrl);
            feed.setState(Feed.State.COMPLETED);
            FeedSaver saver = new FeedSaver(context, feeder, feed);
            saver.start();
        } catch (Exception e) {
            e.printStackTrace();
            if (authKey == null) {
                feed.setState(Feed.State.FAILED_WITHOUT_LOGIN);
            }else{
                feed.setState(Feed.State.FAILED_WITH_LOGIN);
            }
            feed.setState(Feed.State.FAILED_WITH_LOGIN);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callbacks.onPostExecute();
        super.onPostExecute(aVoid);
    }

    private void completeHttp(){
        if (!feedUrl.startsWith("http://") && !feedUrl.startsWith("https://")){
            feedUrl = "http://" + feedUrl;
        }
    }
}
