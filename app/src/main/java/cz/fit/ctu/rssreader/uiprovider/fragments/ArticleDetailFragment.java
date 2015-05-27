package cz.fit.ctu.rssreader.uiprovider.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.articles.ArticleHeader;
import cz.fit.ctu.rssreader.utils.Logger;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class ArticleDetailFragment extends Fragment {
    private TextView articleTitle;
    private TextView articleDate;
    private TextView articleLink;
    private TextView articleContent;

    private Article article = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_detail_screen, container, false);
        articleTitle = (TextView) rootView.findViewById(R.id.article_title);
        articleDate = (TextView) rootView.findViewById(R.id.article_date);
        articleLink = (TextView) rootView.findViewById(R.id.article_link);
        articleContent = (TextView) rootView.findViewById(R.id.article_content);
        if (getArguments() != null){
            article = getArguments().getParcelable("article");
        }
        if (article != null){
            loadArticle();
        }
        return rootView;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void loadArticle(){
        articleTitle.setText(article.getArticleHeader().getTitle());
        articleDate.setText(article.getArticleHeader().getDate());
        articleLink.setOnClickListener(linkClickListener);
        articleContent.setText(Html.fromHtml(article.getArticleContent()));
    }

    private View.OnClickListener linkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String link = article.getArticleHeader().getLink();
            Uri webPage = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };
}
