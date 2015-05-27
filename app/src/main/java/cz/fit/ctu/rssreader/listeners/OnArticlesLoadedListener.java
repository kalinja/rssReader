package cz.fit.ctu.rssreader.listeners;

import cz.fit.ctu.rssreader.articles.ArticleList;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public interface OnArticlesLoadedListener {
    public void onArticlesLoaded(ArticleList articleList);
}
