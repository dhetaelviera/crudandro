package com.example.crud.Adapter;

import android.content.Context;

import com.example.crud.Model.ModelData;
import com.example.crud.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
private List<ModelData> mItems;
private Context context;

public AdapterData(Context context, List<ModelData> items){
    this.mItems=items;
    this.context=context;
}

    @Override
    public HolderData onCreateViewHolder( ViewGroup parent, int viewType) {
       View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,parent,false);
       HolderData holderData=new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int i) {
    ModelData md=mItems.get(i);
    holder.tvnama.setText(md.getNama());
    holder.tvusername.setText(md.getUsername());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder{

    TextView tvusername, tvnama;
    public HolderData( View view) {
        super(view);

        tvnama=(TextView) view.findViewById(R.id.name);
        tvusername=(TextView) view.findViewById(R.id.username);
    }
}
}
