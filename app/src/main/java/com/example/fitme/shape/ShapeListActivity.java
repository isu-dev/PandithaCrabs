package com.example.fitme.shape;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShapeListActivity extends AppCompatActivity {


    private class ShapeListAdapter extends BaseAdapter {

        private Context superCtx;

        public ArrayList<Shape> items = new ArrayList();

        ShapeListAdapter(Context ctx){
            super();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("bodyFats");

            final ShapeListAdapter parent = this;

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Shape pastRecord = dataSnapshot.getValue(Shape.class);
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
            final Shape item = this.items.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.card_shape_list, container, false);
            }

            ImageView imgView = convertView.findViewById(R.id.shapeCardImage);

            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), item.getShape());
            imgView.setImageBitmap(bitmapImage);

            long bustFeat = Math.round(item.getBustSize()/12);
            TextView bustMainText = convertView.findViewById(R.id.bustMainText);
            bustMainText.setText( String.valueOf(bustFeat));

            double bustInch = item.getWaistSize() - (bustFeat*12);
            TextView bustSubText = convertView.findViewById(R.id.bustSubText);
            bustSubText.setText( String.valueOf(bustInch));

            long waistFeat = Math.round(item.getWaistSize()/ 12);
            TextView waistMainText = convertView.findViewById(R.id.waistMainText);
            waistMainText.setText( String.valueOf(Math.round(waistFeat)));

            double waistInch = item.getWaistSize() - (waistFeat*12);
            TextView waistSubText = convertView.findViewById(R.id.waistSubText);
            waistSubText.setText( String.valueOf(waistInch));

            long hipFeat = Math.round(item.getWaistSize()/ 12);
            TextView hipMainText = convertView.findViewById(R.id.hipMainText);
            hipMainText.setText( String.valueOf(Math.round(hipFeat)));

            double hipInch = item.getWaistSize() - (hipFeat*12);
            TextView hipSubText = convertView.findViewById(R.id.hipSubText);
            hipSubText.setText( String.valueOf(hipInch));

            long highHipFeat = Math.round(item.getWaistSize()/ 12);
            TextView highHipMainText = convertView.findViewById(R.id.highHipMainText);
            highHipMainText.setText( String.valueOf(Math.round(highHipFeat)));

            double highHipInch = item.getWaistSize() - (highHipFeat*12);
            TextView highHipSubText = convertView.findViewById(R.id.highHipSubText);
            highHipSubText.setText( String.valueOf(highHipInch));

            final ShapeListAdapter adptr = this;
            Button deleteBtn = convertView.findViewById(R.id.shapeCardDelete);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConfirmationToast confirm = new ConfirmationToast( adptr.superCtx,"Are you sure to delete this record?");

                    confirm.setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("shapes");

                            DatabaseReference bodyFatRef = ref.child( String.valueOf(position));
                            bodyFatRef.removeValue();

                            ArrayList<Shape> items = new ArrayList<Shape>();
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

            Button updateBtn = convertView.findViewById(R.id.shapeCardUpdate);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(adptr.superCtx, ShapeSaveActivity.class);
                    intent.putExtra("UPDATE_ID", position+1);
                    intent.putExtra("BUST_SIZE", item.getBustSize());
                    intent.putExtra("WAIST_SIZE", item.getWaistSize());
                    intent.putExtra("HIP_SIZE", item.getHipSize());
                    intent.putExtra("HIGH_HIP_SIZE", item.getHighHipSize());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_list);

        // Changing the title
        setTitle("Past Shape Records");

        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.shapeListView);
        listView.setAdapter(new ShapeListAdapter(this));
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}