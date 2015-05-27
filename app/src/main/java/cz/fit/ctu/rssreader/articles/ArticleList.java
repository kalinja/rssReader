package cz.fit.ctu.rssreader.articles;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class ArticleList {
    private ArrayList<Article> articles;

    public ArticleList() {
        articles = new ArrayList<>();
    }

    public boolean add(Article article) {
        boolean added = articles.add(article);
        Collections.sort(articles);
        return added;
    }

    public Article get(int index) {
        return articles.get(index);
    }

    public Article remove(int index) {
        Article removed = articles.remove(index);
        Collections.sort(articles);
        return removed;
    }

    public int size() {
        return articles.size();
    }

    public boolean isEmpty() {
        return articles.isEmpty();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void fillWithTestingData() {
        for (int i = 0;i < 30;i++){
            Article article = new Article();
            article.getArticleHeader().setTitle("Some RSS Article");
            article.getArticleHeader().setSummary("This is sample article short description. This will contains some introduction to this article.");
            articles.add(article);
        }
    }
}
