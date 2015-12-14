package jp.ac.dendai.im.cps.footopic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.bean.ArticleBean;

/**
 * Created by naoya on 15/12/14.
 */
public class CommentListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater layoutInflater = null;
    ArticleBean[] commentList;

    public CommentListAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCommentList(ArticleBean[] list) {
        this.commentList = list;
    }

    @Override
    public int getCount() {
        return commentList.length;
    }

    @Override
    public Object getItem(int position) {
        return commentList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.list_comment_item, parent, false);

        ArticleBean comment = commentList[position];

        Log.d("getView", comment.getCreated_at());
        Log.d("getView", comment.getText());

        String time = comment.getCreated_at().substring(0, 9) + " " + comment.getCreated_at().substring(11, 16);

        ((TextView) v.findViewById(R.id.comment)).setText(comment.getText());
        ((TextView) v.findViewById(R.id.comment_time)).setText(time);
        ((TextView) v.findViewById(R.id.comment_user)).setText(comment.getUser().getScreen_name());
        Glide.with(mContext)
                .load(comment.getUser().getImage().getThumb_url())
                .centerCrop()
                .crossFade()
                .into((ImageView) v.findViewById(R.id.comment_item_thumb));

        return v;
    }
}
