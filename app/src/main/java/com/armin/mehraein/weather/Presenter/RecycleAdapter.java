package com.armin.mehraein.weather.Presenter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armin.mehraein.weather.MainActivity;
import com.armin.mehraein.weather.Model.City;
import com.armin.mehraein.weather.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    ArrayList<String> arrayList ;
    Context context ;
    private ISendName iSendName;

    public RecycleAdapter(ArrayList<String> arrayList, Context context,ISendName iSendName) {
        this.arrayList = arrayList;
        this.context = context;
        this.iSendName = iSendName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_view,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txt_city.setText(arrayList.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSendName.sendName(arrayList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_city ;
        public LinearLayout linearLayout ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_city = itemView.findViewById(R.id.txt_city);
            linearLayout = itemView.findViewById(R.id.linear);

        }
    }
}
