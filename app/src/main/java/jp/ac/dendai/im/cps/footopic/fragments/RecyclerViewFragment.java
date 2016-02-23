package jp.ac.dendai.im.cps.footopic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.adapters.ArticleRecyclerViewAdapter;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.listeners.BottomLoadListener;
import jp.ac.dendai.im.cps.footopic.listeners.OnChildItemClickListener;
import jp.ac.dendai.im.cps.footopic.listeners.OnItemClickListener;
import jp.ac.dendai.im.cps.footopic.utils.DividerItemDecoration;

/**
 * Articlesページの {@link Fragment}
 */
public class RecyclerViewFragment extends Fragment implements OnItemClickListener {
    private Activity mActivity = null;
    private OnItemClickListener mListener;
    private RecyclerViewAction mActionListener;
    private View mView;
    private ArrayList<Article> articles;

    private RecyclerView mRecyclerView = null;
    private ArticleRecyclerViewAdapter mArticleRecyclerViewAdapter = null;
    private RecyclerViewFragment mFragment;

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        return recyclerViewFragment;
    }

    public RecyclerViewFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActivity = activity;
            mListener = (OnItemClickListener) activity;
            mFragment = this;
            mActionListener = (RecyclerViewAction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItemClickListener or RecyclerViewAction");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_recycler, container, false);

        // RecyclerViewを参照
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
        mRecyclerView.addOnScrollListener(new BottomLoadListener((LinearLayoutManager) mRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                mActionListener.onLoadMoreAction(mRecyclerView, current_page);
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener.onActivityCreatedAction(mRecyclerView, mFragment);
    }

    @Override
    public void onItemClick(int id, OnChildItemClickListener.ItemType type) {
        mListener.onItemClick(id, type);
    }

    public interface RecyclerViewAction {
        public void onLoadMoreAction(RecyclerView recyclerView, int current_page);
        public void onActivityCreatedAction(RecyclerView recyclerView, RecyclerViewFragment fragment);
    }
}
