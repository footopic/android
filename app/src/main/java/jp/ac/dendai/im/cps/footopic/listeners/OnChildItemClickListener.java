package jp.ac.dendai.im.cps.footopic.listeners;

import android.view.View;

/**
 * Created by naoya on 15/12/15.
 */
public class OnChildItemClickListener implements View.OnClickListener {
    public enum ItemType {
        Article(0),
        Member(1);

        private final int id;

        private ItemType ( final int id ) {
            this.id = id ;
        }

        public int getId() {
            return id;
        }
    }

    private OnItemClickListener mListener;
    private ItemType type;
    private int id;

    public OnChildItemClickListener(OnItemClickListener listener, ItemType type, int id) {
        this.mListener = listener;
        this.type = type;
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(id, type);
    }
}
