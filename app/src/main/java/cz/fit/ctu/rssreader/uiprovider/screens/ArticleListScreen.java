package cz.fit.ctu.rssreader.uiprovider.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.articles.Article;
import cz.fit.ctu.rssreader.articles.ArticleHeader;
import cz.fit.ctu.rssreader.background.RefreshAnimator;
import cz.fit.ctu.rssreader.background.SynchronizeScheduler;
import cz.fit.ctu.rssreader.background.UpdateService;
import cz.fit.ctu.rssreader.database.tables.ArticleTableManager;
import cz.fit.ctu.rssreader.listeners.OnArticleListener;
import cz.fit.ctu.rssreader.uiprovider.BaseActivity;
import cz.fit.ctu.rssreader.uiprovider.fragments.ArticleDetailFragment;
import cz.fit.ctu.rssreader.uiprovider.fragments.ArticleListFragment;
import cz.fit.ctu.rssreader.uiprovider.fragments.FeedRefreshFragment;
import cz.fit.ctu.rssreader.utils.Logger;
import cz.fit.ctu.rssreader.utils.TaskCallbacks;

public class ArticleListScreen extends BaseActivity implements OnArticleListener, TaskCallbacks, RefreshAnimator.RefreshAnimationCallbacks {
    private ArticleListFragment articleListFragment;
    private ArticleDetailFragment articleDetailFragment = null;
    private View refreshView;
    private MenuItem refreshItem;
    private FeedRefreshFragment refreshFragment;
    private UpdateService updateService;
    private RefreshAnimator refreshAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article_list_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        refreshView = inflater.inflate(R.layout.refresh, null);
        refreshAnimator = new RefreshAnimator(this);
        refreshAnimator.initAnimation(this);
        initFragments();

        setUpSync();
    }

    private void setUpSync() {
        SynchronizeScheduler.setUpSyncTime(this);
        Intent intent = new Intent(this, UpdateService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void initFragments() {
        articleListFragment = (ArticleListFragment) getFragmentManager().findFragmentById(R.id.article_list_fragment);
        initDetailFragment();
        refreshFragment = (FeedRefreshFragment) getFragmentManager().findFragmentByTag("refresh");
        if (refreshFragment == null) {
            refreshFragment = new FeedRefreshFragment();
            getFragmentManager().beginTransaction().add(refreshFragment, "refresh").commit();
        }
    }

    private void initDetailFragment() {
        if (findViewById(R.id.contentFragment) != null) {
            articleDetailFragment = new ArticleDetailFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, articleDetailFragment);
            transaction.commit();
            Article article = ArticleTableManager.getLastArticle(getContentResolver());
            articleDetailFragment.setArticle(article);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_list_screen, menu);
        refreshItem = menu.findItem(R.id.action_refresh);
        if (refreshFragment.isRefreshing()) {
            refreshItem.setActionView(refreshView);
            refreshView.startAnimation(refreshAnimator.getRefreshAnimation());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                refresh(item);
                return true;
            case R.id.action_open_feed_config:
                openFeedConfig();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(MenuItem item) {
        refreshFragment.executeTask();
    }

    public void openFeedConfig() {
        Intent intent = new Intent(this, FeedConfigScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_in_anim_rl, R.anim.trans_out_anim_rl);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }

    @Override
    public void onArticleOpen(ArticleHeader articleHeader) {
        if (articleDetailFragment != null) {
            openArticleInFragment(articleHeader);
        } else {
            openArticleInActivity(articleHeader);
        }


    }

    @Override
    public void getFirstArticle(Article article) {
        if (articleDetailFragment != null) {
            ArticleTableManager.loadArticleDetail(getContentResolver(), article);
            articleDetailFragment.setArticle(article);
            articleDetailFragment.loadArticle();
        }
    }

    private void openArticleInFragment(ArticleHeader articleHeader) {
        Article article = new Article(articleHeader);
        ArticleTableManager.loadArticleDetail(getContentResolver(), article);
        articleDetailFragment.setArticle(article);
        articleDetailFragment.loadArticle();
    }

    private void openArticleInActivity(ArticleHeader articleHeader) {
        Intent intent = new Intent(this, ArticleDetailScreen.class);
        intent.putExtra("article", articleHeader);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_in_anim_rl, R.anim.trans_out_anim_rl);
    }

    @Override
    public void onPreExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshFragment.setRefreshing(true);
                refreshItem.setActionView(refreshView);
                refreshView.startAnimation(refreshAnimator.getRefreshAnimation());
            }
        });
    }

    @Override
    public void onPostExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshFragment.setRefreshing(false);
                final Cursor cursor = ArticleTableManager.getArticleListCursor(getContentResolver());
                if (articleListFragment.getArticleCursorAdapter() != null) {
                    articleListFragment.getArticleCursorAdapter().changeCursor(cursor);
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            updateService = ((UpdateService.UpdateBinder) service).getService();
            updateService.registerListener(ArticleListScreen.this);
            Logger.debug("ArticleListScreen", "Service is connected to activity.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            updateService.unregisterListener();
            updateService = null;
            Logger.debug("ArticleListScreen", "Service is disconnected from activity.");
        }
    };

    @Override
    public void onAnimationRepeat(Animation animation) {
        if (!refreshFragment.isRefreshing()) {
            refreshItem.getActionView().clearAnimation();
            refreshItem.setActionView(null);
        }
    }
}
