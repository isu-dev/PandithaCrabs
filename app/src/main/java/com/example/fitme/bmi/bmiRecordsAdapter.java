package com.example.fitme.bmi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitme.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class bmiRecordsAdapter extends RecyclerView.Adapter<bmiRecordsAdapter.ViewHolder> {

    private static final String TAG = "fitme.recyclerview.bmiAdapter";
    private ArrayList bHeightFeet = new ArrayList<>();
    private ArrayList bHeightInches = new ArrayList<>();
    private ArrayList bWeightKg = new ArrayList<>();
    private ArrayList bBmiVal = new ArrayList<>();
    private ArrayList<String> bCategory = new ArrayList<>();

    private Context mContext;


    public bmiRecordsAdapter(ArrayList bHeightFeet, ArrayList bHeightInches, ArrayList bWeightKg, ArrayList bBmiVal, ArrayList<String> bCategory) {
        this.bHeightFeet = bHeightFeet;
        this.bHeightInches = bHeightInches;
        this.bWeightKg = bWeightKg;
        this.bBmiVal = bBmiVal;
        this.bCategory = bCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_bmi_records,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.tv_hFeetOfR.setText((Integer) bHeightFeet.get(position));
        holder.tv_hInchesOfR.setText((Integer) bHeightInches.get(position));
        holder.tv_wKgOfR.setText((Integer) bWeightKg.get(position));
        holder.tv_bmiOfR.setText((Integer) bBmiVal.get(position));
        holder.tv_categoryOfR.setText(bCategory.get(position));

        /*holder.buttonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(this, updateBmiRecord.class);
                intent.putExtra("recordID", recordId);
                Toast.makeText(this, "Opening Update View", Toast.LENGTH_SHORT).show(); //toast describing user action of proceed to update
                startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return bCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_hFeetOfR, tv_hInchesOfR, tv_wKgOfR, tv_bmiOfR, tv_categoryOfR;
        Button buttonU, buttonD;
        CardView recordContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_hFeetOfR = itemView.findViewById(R.id.tv_hFeetOfR);
            tv_hInchesOfR = itemView.findViewById(R.id.tv_hInchesOfR);
            tv_wKgOfR = itemView.findViewById(R.id.tv_weightOfR);
            tv_bmiOfR = itemView.findViewById(R.id.tv_bmiOfR);
            tv_categoryOfR = itemView.findViewById(R.id.tv_categoryOfR);
            buttonU = itemView.findViewById(R.id.buttonU);
            buttonD = itemView.findViewById(R.id.buttonD);
            recordContainer = itemView.findViewById(R.id.recordContainer);
        }
    }
}
