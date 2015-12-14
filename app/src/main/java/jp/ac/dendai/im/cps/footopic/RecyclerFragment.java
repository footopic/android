package jp.ac.dendai.im.cps.footopic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.adapter.RecyclerAdapter;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;
import jp.ac.dendai.im.cps.footopic.util.DividerItemDecoration;
import jp.ac.dendai.im.cps.footopic.util.HttpPostHandler;
import jp.ac.dendai.im.cps.footopic.util.HttpPostTask;

public class RecyclerFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    private Activity mActivity = null;
    private OnRecyclerFragmentInteractionListener mListener;
    private View mView;
    private ArrayList<ArticleBean> articles;

    // RecyclerView, Adapter
    private RecyclerView mRecyclerView = null;
    private RecyclerAdapter mRecyclerAdapter = null;
    private GestureDetectorCompat detector;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mActivity = activity;
            mListener = (OnRecyclerFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recycler, container, false);

        detector = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener());

        // RecyclerViewを参照
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
        mRecyclerView.addOnItemTouchListener(this);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HttpPostHandler postHandler = new HttpPostHandler() {
            @Override
            public void onPostCompleted(String response) {
                Log.d("onPostCompleted", "ok");
                Log.d("onPostCompleted", response);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    articles = new ObjectMapper().readValue(response, new TypeReference<List<ArticleBean>>() {});
                    // ListViewと同じ
                    mRecyclerAdapter = new RecyclerAdapter(mActivity, articles);
                    mRecyclerView.setAdapter(mRecyclerAdapter);

                    for (ArticleBean bean : articles) {
                        Log.d("onPostComplete", bean.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostFailed(String response) {
                Log.d("onPostFailed", "no");
                Log.d("onPostFailed", response);
            }
        };

        HttpPostTask task = new HttpPostTask(getString(R.string.url_article_recent), postHandler, HttpType.Get);
        task.addPostParam("page", "1");
        task.addPostParam("per_page", "20");
        task.addPostParam("include_details", "true");
        task.execute();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        detector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     *
     */
    public interface OnRecyclerFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onRecyclerFragmentInteraction(int position, ArticleBean article);
    }

    /**
     * ListItemのクリック処理
     * TODO: 透明になって戻らないことがある
     */
    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "SingleTap position " + position);

            if (position == -1) {
                return false;
            }

            // handle single tap
            mListener.onRecyclerFragmentInteraction(position, articles.get(position));

            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "SingleTapUp position " + position);

            if (position == -1) {
                return false;
            }
            view.setAlpha(1.0f);

            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "Down position " + position);

            if (position == -1) {
                return false;
            }

            view.setAlpha(0.5f);

            return super.onDown(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "LongPress position " + position);

            if (position == -1) {
                return;
            }

            view.setAlpha(1.0f);

            // handle long press

            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            View view = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "onScroll position " + position);

            if (position == -1) {
                return false;
            }

            view.setAlpha(1.0f);
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "DoubleTap position " + position);

            if (position == -1) {
                return false;
            }

            view.setAlpha(1.0f);
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);

            Log.d("RecyclerFragment", "DoubleTapEvent position " + position);

            if (position == -1) {
                return false;
            }

            view.setAlpha(1.0f);
            return false;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return false;
        }
    }
}
