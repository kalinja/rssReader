package cz.fit.ctu.rssreader.uiprovider;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cz.fit.ctu.rssreader.R;

public class BaseActivity extends ActionBarActivity {
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("RSS Reader");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.inflateMenu(R.menu.menu_article_list_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
