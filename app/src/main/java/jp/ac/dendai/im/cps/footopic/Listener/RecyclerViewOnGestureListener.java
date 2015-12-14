package jp.ac.dendai.im.cps.footopic.Listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import jp.ac.dendai.im.cps.footopic.RecyclerFragment;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;

/**
 * ListItemのクリック処理
 * TODO: 透明になって戻らないことがある
 */
public class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

    private RecyclerView mRecyclerView;
    private RecyclerFragment.OnRecyclerFragmentInteractionListener mListener;
    private ArrayList<ArticleBean> articles;

    public RecyclerViewOnGestureListener(RecyclerView recyclerView,
               RecyclerFragment.OnRecyclerFragmentInteractionListener listener, ArrayList<ArticleBean> articles) {
        this.mRecyclerView = recyclerView;
        this.mListener = listener;
        this.articles = articles;
    }

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

