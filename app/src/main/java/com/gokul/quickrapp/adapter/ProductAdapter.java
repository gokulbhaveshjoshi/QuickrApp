package com.gokul.quickrapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gokul.quickrapp.R;
import com.gokul.quickrapp.model.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<DataModel> mData;
    private ArrayList<String> product_id;
    private Context mContext;

    public ProductAdapter(Context context, ArrayList<DataModel> dm, ArrayList<String> id){
        mContext = context;
        mData = dm;
        product_id = id;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_buy, parent, false);
        return new ViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {
        DataModel dm = mData.get(position);
        holder.itemName.setText(dm.getCategory());
        holder.itemCategory.setText(dm.getDescription());
        holder.itemPrice.setText(dm.getProductPrice());
        Glide.with(mContext).load(dm.getImage()).placeholder(R.drawable.app_logo).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView itemName, itemCategory, itemPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.ivItem);
            this.itemName = itemView.findViewById(R.id.tvItemName);
            this.itemCategory = itemView.findViewById(R.id.tvCategory);
            this.itemPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}

