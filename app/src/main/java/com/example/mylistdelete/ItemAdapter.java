package com.example.mylistdelete;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<Iteme> mItemList;
    private ItemAdapterListener listener;

    public ItemAdapter(List<Iteme> ItemList, ItemAdapterListener listener) {
        this.listener = listener;
        this.mItemList = ItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Iteme Item = mItemList.get(position);
        holder.title.setText(Item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void removeItem(int position) {
        mItemList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Iteme item, int position) {
        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    public interface ItemAdapterListener {
        void onItemSelected(Iteme contact);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(mItemList.get(getAdapterPosition()));
                }
            });
        }
    }
}
