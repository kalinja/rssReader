package cz.fit.ctu.rssreader.listeners;

import cz.fit.ctu.rssreader.articles.Feed;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public interface OnFeedLoadedListener {
    public void onFeedLoaded(Feed feed);
}
