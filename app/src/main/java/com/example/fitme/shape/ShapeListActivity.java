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

import java.util.ArrayList;
import java.util.List;

public class ShapeListActivity extends AppCompatActivity {


    private class ShapeListAdapter extends BaseAdapter {

        private Context superCtx;

        public ArrayList<Shape> items = new ArrayList();

        ShapeListAdapter(Context ctx){
            super();

            this.items.add(new Shape(12.23, 23.08, 12.90, R.drawable.apple));
            this.items.add(new Shape(4.23, 23.08, 42.90, R.drawable.pear));
            this.items.add(new Shape(12.23, 21.08, 52.90, R.drawable.banana));
            this.items.add(new Shape(1.23, 6.08, 22.90, R.drawable.apple));
            this.items.add(new Shape(12.23, 23.08, 12.90, R.drawable.hour_glass));
            this.items.add(new Shape(2.23, 3.08, 22.90, R.drawable.pear));

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
        public View getView(int position, View convertView, ViewGroup container) {
            Shape item = this.items.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.card_shape_list, container, false);
            }

            ImageView imgView = convertView.findViewById(R.id.shapeCardImage);

            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), item.getShape());
            imgView.setImageBitmap(bitmapImage);

            TextView bustMainText = convertView.findViewById(R.id.bustMainText);
            bustMainText.setText( String.valueOf(Math.round(Math.ceil(item.getBustSize()))));

            TextView bustSubText = convertView.findViewById(R.id.bustSubText);
            bustSubText.setText( String.valueOf(Math.round((item.getBustSize()%1)*100)));

            TextView waistMainText = convertView.findViewById(R.id.waistMainText);
            waistMainText.setText( String.valueOf(Math.round(Math.ceil(item.getWaistSize()))));

            TextView waistSubText = convertView.findViewById(R.id.waistSubText);
            waistSubText.setText( String.valueOf(Math.round((item.getWaistSize()%1)*100)));

            TextView hipMainText = convertView.findViewById(R.id.hipMainText);
            hipMainText.setText( String.valueOf(Math.round(Math.ceil(item.getHipSize()))));

            TextView hipSubText = convertView.findViewById(R.id.hipSubText);
            hipSubText.setText( String.valueOf(Math.round((item.getHipSize()%1)*100)));


            ShapeListAdapter adptr = this;
            Button deleteBtn = convertView.findViewById(R.id.shapeCardDelete);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmationToast confirm = new ConfirmationToast( adptr.superCtx,"Are you sure to delete this record?");

                    confirm.setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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