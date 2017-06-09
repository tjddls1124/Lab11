package com.example.sungin.lab11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by SungIn on 2017-06-05.
 */

public class myCanvas extends View {
    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;
    int oldX = -1;
    int oldY = -1;
    boolean stamp = false;
    public String operationType = "";

    public myCanvas(Context context) {
        super(context);

        this.mPaint = new Paint();
    }


    public myCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.mPaint = new Paint();
        this.mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (stamp) { //스탬프 찍기
            int X = (int) event.getX();
            int Y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                oldY = Y;
                oldX = X;
                invalidate();
            }
        } else { // 검은 선 그리기
            int X = (int) event.getX();
            int Y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                oldY = Y;
                oldX = X;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (oldX != -1) {
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                    invalidate();
                }
                oldX = X;
                oldY = Y;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (oldX != -1) {
                    mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                    invalidate();
                }
                oldX = -1;
                oldY = -1;

            }
            invalidate();
        }

        return true;
    }

    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            drawStamp();
        }


        if (operationType.equals("bluring")) {
            setBluring();
        }
        else if (operationType.equals("reset")) {
            resetPaint();
        }
        else if (operationType.equals("blueColor")) {
            this.mPaint.setColor(Color.BLUE);
        }
        else if (operationType.equals("redColor")) {
            this.mPaint.setColor(Color.RED);
        }
        else if (operationType.equals("pen_Big")) {
            this.mPaint.setStrokeWidth(5);

        }
        else if (operationType.equals("pen_Small")) {
            this.mPaint.setStrokeWidth(3);
        }
        else if (operationType.equals("coloring")) {
            setColorFilter();
        }
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void clear() {
        mBitmap.eraseColor(Color.WHITE);
        stamp = false;
        invalidate();
    }

    public void open(String file_name) {
        Bitmap bitmap = BitmapFactory.decodeFile(file_name);
        if (bitmap != null) {
            clear();
            int x = bitmap.getWidth() / 2;
            int y = bitmap.getHeight() / 2;
            bitmap = Bitmap.createScaledBitmap(bitmap, x, y, false);
            int centerX = mCanvas.getWidth() / 2 - x / 2;
            int centerY = mCanvas.getHeight() / 2 - y / 2;
            mCanvas.drawBitmap(bitmap, centerX, centerY, mPaint);
        } else Toast.makeText(getContext(), "저장된 파일이 없습니다", Toast.LENGTH_SHORT).show();
    }

    public void setBluring() {
        BlurMaskFilter blur = new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL);
        mPaint.setMaskFilter(blur);
        invalidate();
    }


    public void resetPaint() {
        mPaint.reset();
        invalidate();
    }

    public void setColorFilter() {
        float[] matrixarray = {
                2f, 0f, 0f, 0f, -25f,
                0f, 2f, 0f, 0f, -25f,
                0f, 0f, 2f, 0f, -25f,
                0f, 0f, 0f, 1f, 0f,
        };
        ColorMatrix matrix = new ColorMatrix(matrixarray);
        mPaint.setColorFilter(new
                ColorMatrixColorFilter(matrix));
        invalidate();
    }


    public void drawStamp() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        if (operationType.equals("rotate")) {
            stamp = true;
            mCanvas.rotate(30, oldX, oldY);
            mCanvas.drawBitmap(bitmap, oldX, oldY, mPaint);
            mCanvas.rotate(-30, oldX, oldY);
            oldX = -1; oldY = -1;
            operationType = "";
        } else if (operationType.equals("move")) {
            stamp = true;
            mCanvas.translate(10, 10);
            mCanvas.drawBitmap(bitmap, oldX, oldY, mPaint);
            mCanvas.translate(-10, -10);
            oldX = -1; oldY = -1;
            operationType = "";
        } else if (operationType.equals("scale")) {
            stamp = true;
            mCanvas.scale((float) 1.5, (float) 1.5, oldX, oldY);
            mCanvas.drawBitmap(bitmap, oldX, oldY, mPaint);
            mCanvas.scale((float) 2 / 3, (float) 2 / 3, oldX, oldY);
            oldX = -1; oldY = -1;
            operationType = "";

        } else if (operationType.equals("skew")) {
            mCanvas.skew(0.2f, 0.2f);
            mCanvas.drawBitmap(bitmap, oldX, oldY, mPaint);
            mCanvas.skew(-0.2f, -0.2f);
            oldX = -1; oldY = -1;
            operationType = "";
        } else if (stamp == true)
            mCanvas.drawBitmap(bitmap, oldX, oldY, mPaint);
    }
}
