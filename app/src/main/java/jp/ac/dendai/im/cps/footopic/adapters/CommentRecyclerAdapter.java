package jp.ac.dendai.im.cps.footopic.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.Comment;
import jp.ac.dendai.im.cps.footopic.entities.User;

/**
 * Created by naoya on 15/12/11.
 * RecyclerViewのアダプター
 */
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private SortedList<Comment> sortedList;

    /**
     * RecyclerViewのアダプター
     * @param context
     */
    public CommentRecyclerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.sortedList = new SortedList<>(Comment.class, new SortedListCallback(this));
    }

    @Override
    public CommentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final CommentRecyclerAdapter.ViewHolder holder, final int position) {
        // データ表示
        if (sortedList != null && sortedList.size() > position && sortedList.get(position) != null) {
            Comment comment = sortedList.get(position);

            Log.d("onBindViewHolder", comment.toString());

            User user = comment.getUser();

            Uri uri = Uri.parse(user.getImage().getThumb_url());
            holder.thumb.setImageURI(uri);

            String time = comment.getCreated_at();
            holder.name.setText(user.getScreen_name() + " が " + time + " にコメント");
            holder.comment_text.setText(comment.getText());
        }
    }

    @Override
    public long getItemId(int position) {
        return sortedList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (sortedList != null) {
            return sortedList.size();
        } else {
            return 0;
        }
    }

    /**
     * データ追加
     * @param dataList 追加するデータリスト {@link List}
     */
    public void addDataOf(List<Comment> dataList) {
        sortedList.addAll(dataList);
    }

    public void removeDataOf(List<Comment> dataList) {
        sortedList.beginBatchedUpdates();
        for (Comment data : dataList) {
            sortedList.remove(data);
        }
        sortedList.endBatchedUpdates();
    }

    /**
     * データ削除
     */
    public void clearData() {
        sortedList.clear();
    }

    /**
     * 現在のデータリススト取得
     * @return 現在のデータリスト {@link SortedList}
     */
    public SortedList<Comment> getList() {
        return sortedList;
    }

    /**
      ViewHolder
     * ItemのViewは固定なのでインナークラス
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView thumb;
        TextView name;
        TextView comment_text;
        TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            thumb = (SimpleDraweeView) itemView.findViewById(R.id.comment_item_thumb);
            name = (TextView) itemView.findViewById(R.id.comment_user);
            comment_text = (TextView) itemView.findViewById(R.id.comment_text);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }
    }

    private static class SortedListCallback extends SortedList.Callback<Comment> {
        private RecyclerView.Adapter adapter;

        public SortedListCallback(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int compare(Comment o1, Comment o2) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = sdf.parse(o1.getCreated_at());
                Date date2 = sdf.parse(o2.getCreated_at());

                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            adapter.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Comment oldItem, Comment newItem) {
            String oldText = oldItem.getText();
            if (oldText == null) {
                return newItem.getText() == null;
            }
            return oldText.equals(newItem.getText());
        }

        @Override
        public boolean areItemsTheSame(Comment item1, Comment item2) {
            return item1.getId() == item2.getId();
        }
    }
}
