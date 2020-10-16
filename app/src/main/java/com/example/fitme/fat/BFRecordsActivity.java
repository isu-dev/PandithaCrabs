package com.example.fitme.fat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.R;
import com.example.fitme.fat.BFPastRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BFRecordsActivity extends AppCompatActivity {


    private class BFRecordsAdapter extends BaseAdapter {

        private Context superCtx;

        public ArrayList<BFPastRecord> items = new ArrayList();

        BFRecordsAdapter(Context ctx){
            super();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("bodyFats");

            final BFRecordsAdapter parent = this;

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BFPastRecord pastRecord = dataSnapshot.getValue(BFPastRecord.class);
                    parent.items.add(pastRecord);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
            
            this.superCtx = ctx;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            BFPastRecord item = this.items.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_bf_record, container, false);
            }

            TextView heightTextView = convertView.findViewById(R.id.heightText);
            heightTextView.setText(String.valueOf(item.getHeight()));

            TextView ageTextView = convertView.findViewById(R.id.ageText);
            ageTextView.setText(String.valueOf(item.getAge()));

            TextView neckTextView = convertView.findViewById(R.id.neckText);
            neckTextView.setText(String.valueOf(item.getNeck()));

            TextView waistTextView = convertView.findViewById(R.id.waistText);
            waistTextView.setText(String.valueOf(item.getWaist()));

            TextView hipTextView = convertView.findViewById(R.id.hipText);
            hipTextView.setText(String.valueOf(item.getHip()));

            TextView weightTextView = convertView.findViewById(R.id.weightText);
            weightTextView.setText(String.valueOf(item.getWeight()));

            TextView sexTextView = convertView.findViewById(R.id.sexText);
            sexTextView.setText(String.valueOf(item.getSex()));

            TextView dateTextView = convertView.findViewById(R.id.dateText);
            dateTextView.setText(String.valueOf(item.getDate()));



            final BFRecordsAdapter adptr = this;
            Button deleteBtn = convertView.findViewById(R.id.bmiDeleteButton);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BFCalculatorToast confirm = new BFCalculatorToast( adptr.superCtx,"Are you sure to delete this record?");

                    confirm.setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<BFPastRecord> items = new ArrayList<BFPastRecord>();

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("bodyFats");

                            DatabaseReference bodyFatRef = ref.child( String.valueOf(position));
                            bodyFatRef.removeValue();

                            adptr.items.remove(position);
                            adptr.notifyDataSetInvalidated();
                            confirm.hide();
                        }
                    });

                    confirm.setCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirm.hide();
                        }
                    });

                    confirm.show();

                }
            });

            Button updateBtn = convertView.findViewById(R.id.bmiUpdateButton);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(adptr.superCtx, BFCalculatorActivity.class);
                    intent.putExtra("UPDATE_ID", position+1);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_records);

        // Changing the title
        setTitle("Past Shape Records");

        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.bfListView);
        listView.setAdapter(new BFRecordsAdapter(this));
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}