package com.example.sungin.lab11;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    CheckBox cb;
    myCanvas myCanvas1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb.isChecked())myCanvas1.stamp = true;
                else myCanvas1.stamp = false;
            }
        });



    }
    public void init() {
        cb = (CheckBox)findViewById(R.id.checkBox);
        myCanvas1 = (myCanvas)findViewById(R.id.CanvasView);
    }

    public void onClick(View v) {
        if( v.getId()==R.id.button_eraser)
            myCanvas1.clear();

        if(v.getId() == R.id.button_save) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(getFilesDir() + "sample.jpg");
                myCanvas1.mBitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                Toast.makeText(this, "저장되었습니다(sample.jpg)",Toast.LENGTH_SHORT).show();

                fileOutputStream.close();

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        if(v.getId() == R.id.button_open) {
          String fileName = getFilesDir() + "sample.jpg";
            myCanvas1.open(fileName);
        }
        if(v.getId() == R.id.button_rotate) {
            cb.setChecked(true);
            myCanvas1.setOperationType("rotate");
        }
        if(v.getId() == R.id.button_move) {
            cb.setChecked(true);
            myCanvas1.move();
        }
        if(v.getId() == R.id.button_scale){
            cb.setChecked(true);
            myCanvas1.scale();
        }

        if(v.getId() == R.id.button_skew) {
            cb.setChecked(true);
            myCanvas1.skew();
        }
    }

}
