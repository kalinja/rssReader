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
public class LoginDialog extends Dialog implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button cancel;
    private Button login;
    private FeedCursorAdapter adapter;
    private String feedUrl;

    public LoginDialog(Context context, FeedCursorAdapter adapter, String feedUrl) {
        super(context);
        this.adapter = adapter;
        this.feedUrl = feedUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login);
        setContentView(R.layout.login_dialog_layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        username = (EditText) findViewById(R.id.username_edit);
        password = (EditText) findViewById(R.id.password_edit);
        cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);
        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                cancel();
                break;
            case R.id.login_button:
                adapter.addNewFeedWithLogin(feedUrl, username.getText().toString(), password.getText().toString());
                cancel();
                break;
        }
    }
}
