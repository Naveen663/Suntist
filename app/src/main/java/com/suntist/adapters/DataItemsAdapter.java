package com.suntist.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suntist.R;
import com.suntist.models.DataItems;

import java.util.ArrayList;


/**
 * Created by Dell on 3/17/2018.
 */

public class DataItemsAdapter extends RecyclerView.Adapter<DataItemsAdapter.ViewHolder> {


    private Context context;
    private ArrayList<DataItems> arrayList;


    public DataItemsAdapter(Context context, ArrayList<DataItems> arrayList) {

        this.context = context;
        this.arrayList = arrayList;

    }

    public DataItemsAdapter(ArrayList<DataItems> data_items_list) {
        this.arrayList = data_items_list;
    }

    @Override
    public DataItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemsAdapter.ViewHolder holder, int position) {

        DataItems data = arrayList.get(position);
        holder.txtName.setText(data.getName());
        holder.txtTel.setText(data.getTel());
        holder.txtSex.setText(data.getSex());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public DataItems getItem(int position) {
        return arrayList.get(position);
    }

    public void changeList( ArrayList<DataItems> arrayList){

        this.arrayList =arrayList;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txtName,txtTel, txtSex;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            txtName = (TextView) view.findViewById(R.id.tv_name);
            txtTel = (TextView) view.findViewById(R.id.tv_tel);
            txtSex = (TextView) view.findViewById(R.id.tv_sex);

            txtTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("##Suntist DIA","txtTel.setOnClick : "+arrayList.get(getPosition()).getTel());


                    Toast.makeText(v.getContext(),"Opening dialer pad",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+arrayList.get(getPosition()).getTel()));
                    //startActivity(intent);
                    v.getContext().startActivity(intent);

                }
            });

        }


        @Override
        public void onClick(View view) {


        }

    }


}
