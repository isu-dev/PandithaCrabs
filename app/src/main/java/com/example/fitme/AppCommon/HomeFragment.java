package com.example.fitme.AppCommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitme.DailyCalorieIntake.CalculateDailyCalorieIntake;
import com.example.fitme.R;
import com.example.fitme.fat.BFCalculatorActivity;
import com.example.fitme.fat.BFMainActivity;
import com.example.fitme.shape.ShapeSaveActivity;

public class HomeFragment extends Fragment {
    private ImageView iv_body_fat_calculator,iv_daily_calorie_calculator;
    private ImageView iv_body_shape_calculator,iv_bmi_calculator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_fragment,container,false);

        iv_bmi_calculator = v.findViewById(R.id.iv_bmi_calculator);
        iv_body_fat_calculator = v.findViewById(R.id.iv_body_fat_calculator);
        iv_body_shape_calculator = v.findViewById(R.id.iv_body_shape_calculator);
        iv_daily_calorie_calculator = v.findViewById(R.id.iv_daily_calorie_calculator);

        iv_bmi_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"bmi calculator",Toast.LENGTH_SHORT).show();
            }
        });

        iv_body_fat_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BFMainActivity.class));
            }
        });

        iv_body_shape_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShapeSaveActivity.class));
            }
        });

        iv_daily_calorie_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalculateDailyCalorieIntake.class));
            }
        });

        return v;

    }

}
