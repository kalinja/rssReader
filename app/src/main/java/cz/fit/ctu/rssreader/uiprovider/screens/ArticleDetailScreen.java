package cz.fit.ctu.rssreader.uiprovider.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.articles.ArticleHeader;
import cz.fit.ctu.rssreader.database.tables.ArticleTableManager;
import cz.fit.ctu.rssreader.uiprovider.BaseActivity;
import cz.fit.ctu.rssreader.uiprovider.fragments.ArticleDetailFragment;

public class ArticleDetailScreen extends BaseActivity {
    private Article article;
    private ArticleDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article_detail_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        ArticleHeader articleHeader = data.getParcelable("article");
        article = new Article(articleHeader);
        toolbar.setTitle(article.getTitle());
        ArticleTableManager.loadArticleDetail(getContentResolver(), article);
        initFragment();
    }

    private void initFragment() {
        fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("article", article);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
        fragment.setArticle(article);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_detail_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                shareArticle();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareArticle() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, article.getArticleHeader().getTitle());
        share.putExtra(Intent.EXTRA_TEXT, article.getArticleHeader().getLink());
        startActivity(Intent.createChooser(share, "Share article"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_in_anim_lr, R.anim.trans_out_anim_lr);
    }
}
