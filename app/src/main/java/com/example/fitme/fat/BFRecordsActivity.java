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

import java.util.ArrayList;
import java.util.List;

public class BFRecordsActivity extends AppCompatActivity {


    private class BFRecordsAdapter extends BaseAdapter {

        private Context superCtx;

        public ArrayList<BFPastRecord> items = new ArrayList();

        BFRecordsAdapter(Context ctx){
            super();

            this.items.add(new BFPastRecord(12.23, 23, 12.90, 123.23, 3.2, 4.4, "Male", "2020/02/010"));
            this.items.add(new BFPastRecord(4.23, 23, 42.90, 12.3, 3.2, 4.4, "Male", "2020/03/01"));
            this.items.add(new BFPastRecord(12.23, 24, 52.90, 1.23, 3.2, 4.4, "Male", "2020/05/07"));
            this.items.add(new BFPastRecord(1.23, 25, 22.90, 4.3, 3.2, 4.4, "Male", "2020/06/02"));
            this.items.add(new BFPastRecord(12.23, 25, 12.90, 1.2, 3.2, 4.4, "Male", "2020/07/01"));
            this.items.add(new BFPastRecord(2.23, 25, 22.90, 2.3, 3.2, 4.4, "Male", "2020/08/01"));

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