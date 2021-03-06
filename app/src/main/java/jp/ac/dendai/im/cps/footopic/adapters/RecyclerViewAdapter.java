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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.Article;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.listeners.OnChildItemClickListener;
import jp.ac.dendai.im.cps.footopic.listeners.OnItemClickListener;

/**
 * Created by naoya on 15/12/11.
 * RecyclerViewのアダプター
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private SortedList<Article> sortedList;
    private OnItemClickListener mListener;

    /**
     * RecyclerViewのアダプター
     * @param context
     */
    public RecyclerViewAdapter(Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.sortedList = new SortedList<>(Article.class, new SortedListCallback(this));
        this.mListener = listener;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        // データ表示
        if (sortedList != null && sortedList.size() > position && sortedList.get(position) != null) {
            Article article = sortedList.get(position);

            Log.d("onBindViewHolder", article.toString());

            User user = article.getUser();

            Uri uri = Uri.parse(user.getImage().getThumb_url());
            holder.thumb.setImageURI(uri);
            holder.thumb.setOnClickListener(new OnChildItemClickListener(mListener, OnChildItemClickListener.ItemType.Member, user.getId()));

            String time = article.getCreated_at();
            holder.name.setText(user.getScreen_name() + " が " + time + " に投稿");
            holder.title.setText(article.getTitle());

            String tags = "";
            for (String str : article.getTags()) {
                tags += str + " ";
            }
            holder.tags.setText(tags);

            holder.layout.setOnClickListener(new OnChildItemClickListener(mListener, OnChildItemClickListener.ItemType.Article, article.getId()));
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
    public void addDataOf(List<Article> dataList) {
        sortedList.addAll(dataList);
    }

    public void removeDataOf(List<Article> dataList) {
        sortedList.beginBatchedUpdates();
        for (Article data : dataList) {
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
    public SortedList<Article> getList() {
        return sortedList;
    }

    /**
      ViewHolder
     * ItemのViewは固定なのでインナークラス
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView thumb;
        TextView name;
        TextView title;
        TextView tags;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            thumb = (SimpleDraweeView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name_text);
            title = (TextView) itemView.findViewById(R.id.title_text);
            tags = (TextView) itemView.findViewById(R.id.tags_text);
            layout = (LinearLayout) itemView.findViewById(R.id.list_item_text_container);
        }
    }

    private static class SortedListCallback extends SortedList.Callback<Article> {
        private RecyclerView.Adapter adapter;

        public SortedListCallback(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int compare(Article o1, Article o2) {
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
        public boolean areContentsTheSame(Article oldItem, Article newItem) {
            String oldText = oldItem.getText();
            if (oldText == null) {
                return newItem.getText() == null;
            }
            return oldText.equals(newItem.getText());
        }

        @Override
        public boolean areItemsTheSame(Article item1, Article item2) {
            return item1.getId() == item2.getId();
        }
    }
}
