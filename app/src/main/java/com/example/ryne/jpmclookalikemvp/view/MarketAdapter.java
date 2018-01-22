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

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketHolder>{

    private static final String TAG = MarketAdapter.class.getSimpleName();

    private int mCheckingBalanceItem;
    Context context;


    public MarketAdapter(int numOfItems, Context context){
        mCheckingBalanceItem = numOfItems;
//        this.checkingBalanceHolders = checkingBalanceHolders;
        this.context = context;
    }

    @Override
    public MarketHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_layout_market;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MarketHolder viewHolder = new MarketHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MarketHolder holder, int position) {

        Log.d(TAG, "#" + position); //check position
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mCheckingBalanceItem;
    }

    class MarketHolder extends RecyclerView.ViewHolder{

        //display position in list using getItemCount() - 1
        TextView listCheckingBalanceView;

        //constructor for ViewHolder with int reference to weather condition
        public MarketHolder(View itemView){
            super(itemView);

            listCheckingBalanceView = itemView.findViewById(R.id.tv_checking_account);
        }

        void bind(int listIndex) {
            listCheckingBalanceView.setText(String.valueOf(listIndex));
        }
    }

}