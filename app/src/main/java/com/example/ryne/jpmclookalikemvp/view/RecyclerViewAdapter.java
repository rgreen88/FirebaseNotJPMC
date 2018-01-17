package com.example.ryne.jpmclookalikemvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;

/**
 * Created by rynel on 1/16/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CheckingBalanceHolder> implements
        View.OnClickListener{

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private int mCheckingBalanceItem;

    public RecyclerViewAdapter(int numOfItems){
        mCheckingBalanceItem = numOfItems;
    }

    @Override
    public CheckingBalanceHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        CheckingBalanceHolder viewHolder = new CheckingBalanceHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckingBalanceHolder holder, int position) {

        Log.d(TAG, "#" + position); //check position
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mCheckingBalanceItem;
    }

    @Override
    public void onClick(View v) {

    }

    class CheckingBalanceHolder extends RecyclerView.ViewHolder{

        //display position in list using getItemCount() - 1
        TextView listCheckingBalanceView;

        //constructor for ViewHolder
        public CheckingBalanceHolder(View itemView){
            super(itemView);

            listCheckingBalanceView = itemView.findViewById(R.id.tv_checking_account);
        }

        void bind(int listIndex) {
            listCheckingBalanceView.setText(String.valueOf(listIndex));
        }
    }



}