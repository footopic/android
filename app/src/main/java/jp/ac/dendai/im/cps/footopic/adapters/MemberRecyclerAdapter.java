package jp.ac.dendai.im.cps.footopic.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import jp.ac.dendai.im.cps.footopic.FragmentEnum;
import jp.ac.dendai.im.cps.footopic.R;
import jp.ac.dendai.im.cps.footopic.entities.User;
import jp.ac.dendai.im.cps.footopic.listeners.OnItemClickListener;

/**
 * Created by naoya on 15/12/18.
 */
public class MemberRecyclerAdapter extends RecyclerView.Adapter<MemberRecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private SortedList<User> sortedList;
    private OnItemClickListener mListener;

    /**
     * RecyclerViewのアダプター
     * @param context
     */
    public MemberRecyclerAdapter(Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.sortedList = new SortedList<>(User.class, new SortedListCallback(this));
        this.mListener = listener;
    }

    public FragmentEnum getItemFragmentType() {
        return FragmentEnum.UserInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (sortedList != null && sortedList.size() > position && sortedList.get(position) != null) {
            User user = sortedList.get(position);

            Uri uri = Uri.parse(user.getImage().getThumb_url());
            holder.thumb.setImageURI(uri);

            holder.screenName.setText(user.getScreen_name());
            holder.name.setText(user.getName());
        }
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
    public void addDataOf(List<User> dataList) {
        sortedList.addAll(dataList);
    }

    public void removeDataOf(List<User> dataList) {
        sortedList.beginBatchedUpdates();
        for (User data : dataList) {
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
    public SortedList<User> getList() {
        return sortedList;
    }

    /**
     * Listに入っているFragmentのTypeを返す
     * @param position なんでもいい
     * @return FragmentEnum
     */
    @Override
    public int getItemViewType(int position) {
        return FragmentEnum.UserInfo.getId();
    }

    /**
     ViewHolder
     * ItemのViewは固定なのでインナークラス
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView thumb;
        TextView name;
        TextView screenName;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            thumb = (SimpleDraweeView) itemView.findViewById(R.id.list_item_user_thumb);
            name = (TextView) itemView.findViewById(R.id.list_item_user_name);
            screenName = (TextView) itemView.findViewById(R.id.list_item_user_screen_name);
            layout = (LinearLayout) itemView.findViewById(R.id.list_item_user_main);
        }
    }

    private static class SortedListCallback extends SortedList.Callback<User> {
        private RecyclerView.Adapter adapter;

        public SortedListCallback(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int compare(User o1, User o2) {
            return o1.getId() < o2.getId() ? -1 : (o1.getId() == o2.getId() ? 0 : 1);
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
        public boolean areContentsTheSame(User oldItem, User newItem) {
            String oldText = oldItem.getScreen_name();
            if (oldText == null) {
                return newItem.getScreen_name() == null;
            }
            return oldText.equals(newItem.getScreen_name());
        }

        @Override
        public boolean areItemsTheSame(User item1, User item2) {
            return item1.getId() == item2.getId();
        }
    }
}
