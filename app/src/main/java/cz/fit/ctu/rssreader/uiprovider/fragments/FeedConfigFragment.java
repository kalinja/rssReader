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
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cz.fit.ctu.rssreader.R;
import cz.fit.ctu.rssreader.database.tables.FeedTableManager;
import cz.fit.ctu.rssreader.uiprovider.adapters.FeedCursorAdapter;
import cz.fit.ctu.rssreader.uiprovider.screens.FeedConfigScreen;
import cz.fit.ctu.rssreader.utils.DividerItemDecoration;
import cz.fit.ctu.rssreader.utils.NewFeedDialog;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class FeedConfigFragment extends Fragment {
    private RecyclerView feedsView;
    private FeedCursorAdapter feedsAdapter;
    private ImageButton addFeedButton;
    private ProgressBar progress;
    private NewFeedDialog newFeedDialog = null;
    private FeedDownloadFragment downloadFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_config_screen, container, false);
        feedsView = (RecyclerView) rootView.findViewById(R.id.feed_list_view);
        addFeedButton = (ImageButton) rootView.findViewById(R.id.add_feed_button);
        addFeedButton.setOnClickListener(addFeedButtonOnClickListener);
        progress = (ProgressBar) rootView.findViewById(R.id.downloading_feed);
        feedsView.setHasFixedSize(true);
        feedsView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        feedsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Cursor cursor = FeedTableManager.getFeedListCursor(getActivity().getContentResolver());
        downloadFragment = ((FeedConfigScreen)getActivity()).getDownloadFragment();
        feedsAdapter = new FeedCursorAdapter(getActivity(), downloadFragment, cursor, progress);
        if (downloadFragment.isDownloading()){
            progress.setVisibility(View.VISIBLE);
        }
        feedsView.setAdapter(feedsAdapter);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private View.OnClickListener addFeedButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            newFeedDialog = new NewFeedDialog(getActivity(), feedsAdapter);
            newFeedDialog.show();
        }
    };

    public NewFeedDialog getNewFeedDialog() {
        return newFeedDialog;
    }

    public FeedCursorAdapter getFeedsAdapter() {
        return feedsAdapter;
    }

    public ProgressBar getProgress() {
        return progress;
    }
}
