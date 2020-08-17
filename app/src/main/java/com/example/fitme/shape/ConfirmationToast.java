package com.example.fitme.shape;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fitme.R;

public class ConfirmationToast {

    private AlertDialog alertDialog;
    private View view;

    ConfirmationToast(Context ctx, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ctx);
        View view = layoutInflaterAndroid.inflate(R.layout.toast_shape_list, null);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();

        // Store variables for reuse
        this.alertDialog = alertDialog;
        this.view = view;

        // Changing the background of the toast
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Changing the text
        TextView textView = view.findViewById(R.id.shapeToastText);
        textView.setText(message);
    }

    public void setConfirmListener(View.OnClickListener listener) {
        view.findViewById(R.id.shapeToastYes).setOnClickListener(listener);
    }

    public void setCancelListener(View.OnClickListener listener) {
        view.findViewById(R.id.shapeToastNo).setOnClickListener(listener);
    }

    public void show() {
        this.alertDialog.show();
    }

    public void hide() {
        this.alertDialog.dismiss();
    }
}
