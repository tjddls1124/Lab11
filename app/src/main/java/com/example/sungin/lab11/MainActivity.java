package com.example.sungin.lab11;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    boolean blurred = false;
    boolean colored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb.isChecked()) myCanvas1.stamp = true;
                else myCanvas1.stamp = false;
            }
        });


    }

    public void init() {
        cb = (CheckBox) findViewById(R.id.checkBox);
        myCanvas1 = (myCanvas) findViewById(R.id.CanvasView);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_eraser){
            cb.setChecked(false);
            myCanvas1.clear();

        }

        if (v.getId() == R.id.button_save) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(getFilesDir() + "sample.jpg");
                myCanvas1.mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                Toast.makeText(this, "저장되었습니다(sample.jpg)", Toast.LENGTH_SHORT).show();

                fileOutputStream.close();

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        if (v.getId() == R.id.button_open) {
            String fileName = getFilesDir() + "sample.jpg";
            myCanvas1.open(fileName);
        }
        if (v.getId() == R.id.button_rotate) {
            cb.setChecked(true);
            myCanvas1.setOperationType("rotate");
        }
        if (v.getId() == R.id.button_move) {
            cb.setChecked(true);
            myCanvas1.setOperationType("move");
        }
        if (v.getId() == R.id.button_scale) {
            cb.setChecked(true);
            myCanvas1.setOperationType("scale");
        }

        if (v.getId() == R.id.button_skew) {
            cb.setChecked(true);
            myCanvas1.setOperationType("skew");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Blurring");
        menu.add(0, 1, 1, "Coloring");
        menu.add(0, 2, 2, "Pen With Big");
        menu.add(1, 3, 3, "Pen Color Red");
        menu.add(2, 4, 4, "Pen Color Blue");

        menu.setGroupCheckable(0, true, false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            if (!item.isChecked()) {
                myCanvas1.setOperationType("bluring");
                item.setChecked(true);
                blurred = true;
            } else {
                myCanvas1.setOperationType("reset");
                if (colored)
                    myCanvas1.setOperationType("coloring");


                item.setChecked(false);
                blurred = false;
            }
        }
        if (item.getItemId() == 1) {
            if (!item.isChecked()) {
                myCanvas1.setOperationType("coloring");
                item.setChecked(true);
                colored = true;
            } else {
                myCanvas1.setOperationType("reset");

                if (blurred)
                    myCanvas1.setOperationType("bluring");

                item.setChecked(false);
                colored = false;
            }
        }
        if (item.getItemId() == 4) {

            myCanvas1.setOperationType("blueColor");
        }
        if (item.getItemId() == 3) {

            myCanvas1.setOperationType("redColor");
        }
        if (item.getItemId() == 2) {
            if (!item.isChecked()) {
                myCanvas1.setOperationType("pen_Big");
                item.setChecked(true);
            } else {
                myCanvas1.setOperationType("pen_Small");
                item.setChecked(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
