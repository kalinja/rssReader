package cz.fit.ctu.rssreader.listeners;

import cz.fit.ctu.rssreader.articles.ArticleHeader;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public interface OnArticleHeaderLoadedListener {
    public void onArticleHeaderLoaded(ArticleHeader articleHeader);
}
