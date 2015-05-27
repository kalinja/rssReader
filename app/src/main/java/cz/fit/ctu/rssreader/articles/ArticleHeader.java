package cz.fit.ctu.rssreader.articles;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class ArticleHeader implements Parcelable{
    private int id;
    private String title;
    private String date;
    private String summary;
    private String link;
    private int feedId;

    public ArticleHeader(){
        id = -1;
        title = "Article Title";
        date = "";
        summary = "";
        link = "http://www.google.com";
        feedId = -1;
    }

    public void setToday(Locale locale){
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd MMMM yyyy", locale);
        date = sdf.format(d);
    }

    public ArticleHeader(Parcel in){
        id = in.readInt();
        title = in.readString();
        date = in.readString();
        summary = in.readString();
        link = in.readString();
        feedId = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(summary);
        dest.writeString(link);
        dest.writeInt(feedId);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArticleHeader createFromParcel(Parcel in) {
            return new ArticleHeader(in);
        }

        public ArticleHeader[] newArray(int size) {
            return new ArticleHeader[size];
        }
    };

    @Override
    public String toString() {
        return "ArticleHeader{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", summary='" + summary + '\'' +
                ", link='" + link + '\'' +
                ", feedId='" + feedId + '\'' +
                '}';
    }
}
