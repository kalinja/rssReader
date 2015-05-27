package cz.fit.ctu.rssreader.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.uiprovider.adapters.FeedCursorAdapter;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class NewFeedDialog extends Dialog implements View.OnClickListener {
    private EditText feedUrlEdit;
    private Button cancel;
    private Button add;
    private FeedCursorAdapter adapter;

    public NewFeedDialog(Context context, FeedCursorAdapter adapter) {
        super(context);
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_feed);
        setContentView(R.layout.add_feed_dialog_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        feedUrlEdit = (EditText) findViewById(R.id.feed_url_edit);
        cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
        add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                cancel();
                break;
            case R.id.add_button:
                adapter.addNewFeed(feedUrlEdit.getText().toString());
                cancel();
                break;
        }
    }
}
