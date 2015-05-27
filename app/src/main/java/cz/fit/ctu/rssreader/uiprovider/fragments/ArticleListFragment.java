package cz.fit.ctu.rssreader.uiprovider.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.database.tables.ArticleTableManager;
import cz.fit.ctu.rssreader.listeners.OnArticleListener;
import cz.fit.ctu.rssreader.uiprovider.adapters.ArticleCursorAdapter;
import cz.fit.ctu.rssreader.utils.DividerItemDecoration;

/**
 * Created by Jakub on 6. 3. 2015.
 */
public class ArticleListFragment extends Fragment {

    private RecyclerView articleListView;
    private ArticleCursorAdapter articleCursorAdapter;

    public ArticleListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Cursor cursor = ArticleTableManager.getArticleListCursor(getActivity().getContentResolver());
        OnArticleListener onArticleListener = (OnArticleListener) getActivity();
        articleCursorAdapter = new ArticleCursorAdapter(getActivity(), cursor, onArticleListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_list_screen, container, false);
        articleListView = (RecyclerView) rootView.findViewById(R.id.article_list_view);
        articleListView.setHasFixedSize(true);
        articleListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        articleListView.setLayoutManager(new LinearLayoutManager(getActivity()));


        articleListView.setAdapter(articleCursorAdapter);
        return rootView;
    }

    public ArticleCursorAdapter getArticleCursorAdapter() {
        return articleCursorAdapter;
    }

}
