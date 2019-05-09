package com.example.mylistdelete;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        ItemAdapter.ItemAdapterListener {
    private List<Iteme> mItemList = new ArrayList<Iteme>();
    private RecyclerView mRecyclerViewSongs;
    private ItemAdapter mAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setUpAdapter();
        getItems();
    }

    private void init() {
        mRecyclerViewSongs = findViewById(R.id.recycler_view);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
    }


    private void setUpAdapter() {
        mAdapter = new ItemAdapter(mItemList, this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        mRecyclerViewSongs.setLayoutManager(mLayoutManager);
        mRecyclerViewSongs.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSongs.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerViewSongs);
    }

    private void getItems() {
        Iteme itemOne = new Iteme(1, "Item One");
        Iteme itemTwo = new Iteme(2, "Item Two");
        Iteme itemThree = new Iteme(3, "Item Three");
        mItemList.add(itemOne);
        mItemList.add(itemTwo);
        mItemList.add(itemThree);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(Iteme item) {
        Toast.makeText(this, "Selected " +item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ItemAdapter.MyViewHolder) {
            String name = mItemList.get(viewHolder.getAdapterPosition()).getTitle();
            final Iteme deletedItem = mItemList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            mAdapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar
                    .make(mCoordinatorLayout, name + " removed from library!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
