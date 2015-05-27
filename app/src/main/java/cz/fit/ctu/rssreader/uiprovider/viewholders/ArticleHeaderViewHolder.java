package cz.fit.ctu.rssreader.uiprovider.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.ArticleHeader;
import cz.fit.ctu.rssreader.listeners.OnArticleListener;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class ArticleHeaderViewHolder extends RecyclerView.ViewHolder{
    private TextView articleTitle;
    private TextView articleShortDescription;
    private ArticleHeader articleHeader;
    private OnArticleListener listener;

    public ArticleHeaderViewHolder(View itemView, OnArticleListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(onClickListener);
        articleTitle = (TextView) itemView.findViewById(R.id.article_title);
        articleShortDescription = (TextView) itemView.findViewById(R.id.article_short_description);
    }

    public void onArticleHeaderLoaded(ArticleHeader articleHeader) {
        this.articleHeader = articleHeader;
        articleTitle.setText(articleHeader.getTitle());
        articleShortDescription.setText(articleHeader.getSummary());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onArticleOpen(articleHeader);
        }
    };

}
