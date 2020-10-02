package com.example.fitme.DailyCalorieIntake;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CalorieRecordAdapter extends RecyclerView.Adapter{
    List<Daily_Calorie_Record> AllRecordList;
    InputsForCalorieIntake inputsForCalorieIntake;

    public CalorieRecordAdapter(List<Daily_Calorie_Record> allRecordList) {
        AllRecordList = allRecordList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calorie_record_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder)holder;
        final Daily_Calorie_Record daily_calorie_record = AllRecordList.get(position);
        viewHolder.tv_record_date.setText("Date = " + daily_calorie_record.getCurrentDate());
        viewHolder.tv_daily_carb_intake.setText("Total Daily Carbohydrate Intake = " + String.valueOf(daily_calorie_record.getCarbIntake() + " g"));
        viewHolder.tv_total_daily_calorie_intake.setText("Total Daily Calorie Intake = " + String.valueOf(daily_calorie_record.getDailyCalorieIntake() + " Calories"));
        viewHolder.tv_total_daily_protein_intake.setText("Total Daily Protein Intake = " + String.valueOf(daily_calorie_record.getProteinIntake() + " g"));
        viewHolder.tv_daily_fat_intake.setText("Total Daily Fat Intake = " + String.valueOf(daily_calorie_record.getFatIntake() + " g"));

        viewHolder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Daily_Calorie_Record daily_calorie_record = AllRecordList.get(viewHolder.getAdapterPosition());
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                FirebaseUser user = userAuth.getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Calorie Records").child(user.getUid()).child(daily_calorie_record.getRecordId());
                databaseReference.removeValue();
                AllRecordList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        viewHolder.bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                FirebaseUser user = userAuth.getCurrentUser();
                Daily_Calorie_Record daily_calorie_record = AllRecordList.get(viewHolder.getAdapterPosition());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Calorie Records").child(user.getUid()).child(daily_calorie_record.getRecordId());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        inputsForCalorieIntake = snapshot.child("Inputs").getValue(InputsForCalorieIntake.class);
                        System.out.println(inputsForCalorieIntake.getAge());
                        Intent intent = new Intent(view.getContext(),UpdateCalorieIntakeRecord.class);
                        intent.putExtra("Inputs",inputsForCalorieIntake);
                        view.getContext().startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return AllRecordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_record_date, tv_total_daily_calorie_intake, tv_total_daily_protein_intake;
        TextView tv_daily_carb_intake, tv_daily_fat_intake;
        Button bt_delete,bt_update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_record_date = itemView.findViewById(R.id.tv_date_calorie_record_row);
            tv_total_daily_calorie_intake = itemView.findViewById(R.id.tv_calculated_total_daily_calorie_intake_row);
            tv_daily_carb_intake = itemView.findViewById(R.id.tv_calculated_daily_carb_intake_row);
            tv_daily_fat_intake = itemView.findViewById(R.id.tv_calculated_daily_fat_intake_row);
            tv_total_daily_protein_intake = itemView.findViewById(R.id.tv_calculated_daily_protein_intake_row);
            bt_delete = itemView.findViewById(R.id.bt_delete_calorie_record_row);
            bt_update = itemView.findViewById(R.id.bt_update_calorie_record_row);
        }
    }
}
