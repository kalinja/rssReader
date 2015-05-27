package cz.fit.ctu.rssreader.articles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class Article implements Comparable<Article>, Parcelable {
    public static final int SUMMARY_LENGTH = 300;
    private ArticleHeader articleHeader;
    private String articleContent;

    public Article() {
        articleHeader = new ArticleHeader();
        articleContent = "";
    }

    public Article(ArticleHeader articleHeader) {
        this.articleHeader = articleHeader;
        articleContent = "";
    }

    public Article(Parcel in) {
        this.articleHeader = in.readParcelable(ArticleHeader.class.getClassLoader());
        this.articleContent = in.readString();
    }

    public void createTestContent() {
        articleContent = "Bacon ipsum dolor amet t-bone andouille doner swine " +
                "venison fatback ham hock. Shank boudin picanha, short ribs ball tip" +
                " tenderloin turkey filet mignon pork belly pork tri-tip tail prosciutto." +
                " Beef ribs short ribs hamburger turducken corned beef tri-tip ball tip " +
                "drumstick tongue andouille. Fatback pig tri-tip, cow porchetta shoulder " +
                "pancetta ribeye tenderloin short ribs. Flank leberkas sausage, kielbasa " +
                "turkey pork loin bacon chuck turducken fatback rump ham hock landjaeger " +
                "tail jowl. Turducken landjaeger capicola doner ribeye, salami picanha tri-tip " +
                "beef ribs sausage sirloin pork belly venison filet mignon tongue. Corned beef" +
                " turkey hamburger spare ribs.";
    }

    @Override
    public int compareTo(Article another) {
        return articleHeader.getDate().compareTo(another.articleHeader.getDate());
    }

    public int getId() {
        return articleHeader.getId();
    }

    public void setId(int id) {
        articleHeader.setId(id);
    }

    public String getLink() {
        return articleHeader.getLink();
    }

    public void setLink(String link) {
        articleHeader.setLink(link);
    }

    public String getTitle() {
        return articleHeader.getTitle();
    }

    public void setTitle(String title) {
        articleHeader.setTitle(title);
    }

    public String getDate() {
        return articleHeader.getDate();
    }

    public void setDate(String date) {
        articleHeader.setDate(date);
    }

    public String getSummary() {
        return articleHeader.getSummary();
    }

    public void setSummary(String shortDescription) {
        articleHeader.setSummary(shortDescription);
    }

    public int getFeedId() {
        return articleHeader.getFeedId();
    }

    public void setFeedId(int feedId) {
        articleHeader.setFeedId(feedId);
    }

    public ArticleHeader getArticleHeader() {
        return articleHeader;
    }

    public void setArticleHeader(ArticleHeader articleHeader) {
        this.articleHeader = articleHeader;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(articleHeader, flags);
        dest.writeString(articleContent);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public String toString() {
        return "Article{" +
                "articleHeader=" + articleHeader +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
