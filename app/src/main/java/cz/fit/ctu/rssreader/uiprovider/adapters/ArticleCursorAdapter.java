package cz.fit.ctu.rssreader.uiprovider.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.database.tables.ArticleTableManager;
import cz.fit.ctu.rssreader.listeners.OnArticleListener;
import cz.fit.ctu.rssreader.uiprovider.viewholders.ArticleHeaderViewHolder;
import cz.fit.ctu.rssreader.utils.CursorRecyclerViewAdapter;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class ArticleCursorAdapter extends CursorRecyclerViewAdapter<ArticleHeaderViewHolder> {
    private OnArticleListener listener;
    public ArticleCursorAdapter(Context context, Cursor cursor, OnArticleListener listener) {
        super(context, cursor);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ArticleHeaderViewHolder viewHolder, Cursor cursor) {
        Article article = ArticleTableManager.articleHeaderFromCursor(cursor);
        viewHolder.onArticleHeaderLoaded(article.getArticleHeader());
    }

    @Override
    public ArticleHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_header, parent, false);
        return new ArticleHeaderViewHolder(itemView, listener);
    }
}
