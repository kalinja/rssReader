package cz.fit.ctu.rssreader.articles;

import java.util.ArrayList;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedList {
    private ArrayList<Feed> feeds;

    public FeedList() {
        feeds = new ArrayList<>();
    }

    public boolean add(Feed object) {
        return feeds.add(object);
    }

    public void clear() {
        feeds.clear();
    }

    public Feed get(int index) {
        return feeds.get(index);
    }

    public int size() {
        return feeds.size();
    }

    public boolean isEmpty() {
        return feeds.isEmpty();
    }

    public Feed remove(int index) {
        return feeds.remove(index);
    }

    public boolean remove(Object object) {
        return feeds.remove(object);
    }
}
