package com.example.ryne.jpmclookalikemvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;
import com.example.ryne.jpmclookalikemvp.presenter.ItemClickListener;

/**
 * Created by rynel on 1/16/2018.
 */

public class CheckingBalanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView nameTxt;
    ItemClickListener itemClickListener;

    public CheckingBalanceHolder(View itemView) {
        super(itemView);

        nameTxt= itemView.findViewById(R.id.tv_checking_account);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
}
