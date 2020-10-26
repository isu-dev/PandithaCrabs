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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShapeListActivity extends AppCompatActivity {


    private class ShapeListAdapter extends BaseAdapter {

        private Context superCtx;

        public ArrayList<Shape> items = new ArrayList();

        ShapeListAdapter(Context ctx){
            super();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("shapes");

            final ShapeListAdapter parent = this;

            ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    parent.refreshData(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //handle databaseError
                }
            });


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    parent.refreshData(dataSnapshot);
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

            int bustFeat = ((Double)Math.floor(item.getBustSize()/12)).intValue();
            TextView bustMainText = convertView.findViewById(R.id.bustMainText);
            bustMainText.setText( String.valueOf(bustFeat));

            int bustInch = item.getBustSize().intValue() - (bustFeat*12);
            TextView bustSubText = convertView.findViewById(R.id.bustSubText);
            bustSubText.setText( String.valueOf(bustInch));

            int waistFeat = ((Double)Math.floor(item.getWaistSize()/ 12)).intValue();
            TextView waistMainText = convertView.findViewById(R.id.waistMainText);
            waistMainText.setText( String.valueOf(Math.round(waistFeat)));

            int waistInch = item.getWaistSize().intValue() - (waistFeat*12);
            TextView waistSubText = convertView.findViewById(R.id.waistSubText);
            waistSubText.setText( String.valueOf(waistInch));

            int hipFeat = ((Double)Math.floor(item.getHipSize()/ 12)).intValue();
            TextView hipMainText = convertView.findViewById(R.id.hipMainText);
            hipMainText.setText( String.valueOf(Math.round(hipFeat)));

            int hipInch = item.getHipSize().intValue() - (hipFeat*12);
            TextView hipSubText = convertView.findViewById(R.id.hipSubText);
            hipSubText.setText( String.valueOf(hipInch));

            int highHipFeat = ((Double) Math.floor(item.getHighHipSize()/ 12)).intValue();
            TextView highHipMainText = convertView.findViewById(R.id.highHipMainText);
            highHipMainText.setText( String.valueOf(Math.round(highHipFeat)));

            int highHipInch = item.getHighHipSize().intValue() - (highHipFeat*12);
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
                            Shape toDelete = adptr.items.get(position);
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("shapes");

                            DatabaseReference bodyFatRef = ref.child( toDelete.firebaseId());
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

            Button updateBtn = convertView.findViewById(R.id.shapeCardUpdate);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(adptr.superCtx, ShapeSaveActivity.class);
                    intent.putExtra("UPDATE_ID", item.firebaseId());
                    intent.putExtra("BUST_SIZE", String.valueOf(item.getBustSize()));
                    intent.putExtra("WAIST_SIZE", String.valueOf(item.getWaistSize()));
                    intent.putExtra("HIP_SIZE", String.valueOf(item.getHipSize()));
                    intent.putExtra("HIGH_HIP_SIZE", String.valueOf(item.getHighHipSize()));
                    startActivity(intent);
                }
            });

            return convertView;
        }



        protected void refreshData(DataSnapshot snapshot){
            this.items.clear();
            //Get map of users in datasnapshot
            HashMap<String,HashMap<String, Object>> shapes  = (HashMap<String,HashMap<String, Object>>) snapshot.getValue();

            if(shapes==null){
                Toast.makeText(this.superCtx,"No data found. Please add some data",Toast.LENGTH_LONG);
                finish();
                return;
            }

            Iterator entrySet = shapes.entrySet().iterator();
            while(entrySet.hasNext()){
                Map.Entry entry = (Map.Entry) entrySet.next();
                HashMap<String, Object> shapeData = (HashMap<String, Object>) entry.getValue();
                Shape shape = new Shape();
                shape.setBustSize((Long)shapeData.get("bustSize"));
                shape.setWaistSize((Long)shapeData.get("waistSize"));
                shape.setHipSize((Long)shapeData.get("hipSize"));
                shape.setHighHipSize((Long)shapeData.get("highHipSize"));
                shape.setShape( ((Long)shapeData.get("shape")).intValue());
                shape.setTime( (Long)shapeData.get("time"));
                shape.firebaseId((String)entry.getKey());

                this.items.add(shape);
            }

            notifyDataSetInvalidated();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_list);

        // Changing the title
        setTitle("Past Shape Records");

        ListView listView = findViewById(R.id.shapeListView);
        listView.setAdapter(new ShapeListAdapter(this));
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}