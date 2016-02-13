package jp.ac.dendai.im.cps.footopic.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.Article;

/**
 * Created by naoya on 15/12/14.
 * 記事詳細ページのコメントリスト用Adapter
 */
public class CommentListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater layoutInflater = null;
    Article[] commentList;

    /**
     * 記事詳細ページのコメントリスト用Adapter
     * @param context
     */
    public CommentListAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 表示するデータのリスト
     * @param list
     */
    public void setCommentList(Article[] list) {
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

        Article comment = commentList[position];

        Log.d("getView", comment.getCreated_at());
        Log.d("getView", comment.getText());

        String time = comment.getCreated_at();

        ((TextView) v.findViewById(R.id.comment)).setText(comment.getText());
        ((TextView) v.findViewById(R.id.comment_time)).setText(time);
        ((TextView) v.findViewById(R.id.comment_user)).setText(comment.getUser().getScreen_name());
        Uri uri = Uri.parse(comment.getUser().getImage().getThumb_url());
        ((SimpleDraweeView) v.findViewById(R.id.comment_item_thumb)).setImageURI(uri);

        return v;
    }
}
