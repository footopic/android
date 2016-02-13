package jp.ac.dendai.im.cps.footopic.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by naoya on 15/12/14.
 * {@link RecyclerView} が最下部までスクロールされた用の {@link android.support.v7.widget.RecyclerView.OnScrollListener}
 */
public abstract class BottomLoadListener extends RecyclerView.OnScrollListener {

    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private boolean loading = true;
    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    /**
     * {@link RecyclerView} が最下部までスクロールされた用の {@link android.support.v7.widget.RecyclerView.OnScrollListener}
     * @param linearLayoutManager
     */
    public BottomLoadListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleItemCount)) {
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    /**
     * 一番下までスクロールされた時に呼ばれるメソッド
     * @param current_page 次のページ番号
     */
    public abstract void onLoadMore(int current_page);
}
