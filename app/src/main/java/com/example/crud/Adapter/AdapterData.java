package com.example.crud.Adapter;

import android.content.Context;

import com.example.crud.InsertData;
import com.example.crud.Model.ModelData;
import com.example.crud.R;
import com.example.crud.UpdateDelete;

import android.content.Intent;
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

    holder.md=md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder{

    TextView tvusername, tvnama;
    ModelData md;

    //nyimpen data
    public HolderData( View view) {
        super(view);

        tvnama=(TextView) view.findViewById(R.id.name);
        tvusername=(TextView) view.findViewById(R.id.username);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update=new Intent(context, UpdateDelete.class);
                update.putExtra("update",0);
                update.putExtra("username", md.getUsername());
                update.putExtra("nama", md.getNama());
                update.putExtra("email", md.getEmail());
                update.putExtra("password", md.getPassword());

                context.startActivity(update);
            }
        });

    }
}
}
