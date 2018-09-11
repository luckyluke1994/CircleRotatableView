package com.example.mddien.test.frequency;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.mddien.test.R;

public class FrequencyView extends FrameLayout {
    private static final String TAG = FrequencyView.class.getSimpleName();
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float radius;

    public FrequencyView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FrequencyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FrequencyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.frequency_view,this);

//        // need to get the real height and width of view
//        post(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("CircularListView", "get layout width and height");
//                layoutWidth = getWidth();
//                layoutHeight = getHeight();
//                layoutCenter_x = layoutWidth / 2;
//                layoutCenter_y = layoutHeight / 2;
//                radius = layoutWidth / 3;
//            }
//        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "get layout width and height");
        layoutWidth = w;
        layoutHeight = h;
        layoutCenter_x = layoutWidth / 2;
        layoutCenter_y = layoutHeight / 2;
        radius = Math.min(layoutWidth, layoutHeight) / 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawCircle(layoutCenter_x, layoutCenter_y, radius, paint);
        Log.d(TAG, "dispatchDraw: "+getWidth()+"/"+getHeight());

        // Draw a tick for each "second" (1 through 60)
        //Tick Styles
        int tickLen = 10;  // short tick
        int medTickLen = 30;  // at 5-minute intervals
        int longTickLen = 50; // at the quarters
        int tickColor = 0xCCCCCC;
        for ( int i=1; i<= 60; i++){
            // default tick length is short
            int len = tickLen;
            if ( i % 15 == 0 ){
                // Longest tick on quarters (every 15 ticks)
                len = longTickLen;
            } else if ( i % 5 == 0 ){
                // Medium ticks on the '5's (every 5 ticks)
                len = medTickLen;
            }

            double di = (double)i; // tick num as double for easier math

            // Get the angle from 12 O'Clock to this tick (radians)
            double angleFrom12 = di/60.0*2.0*Math.PI;
            // Get the angle from 3 O'Clock to this tick
            // Note: 3 O'Clock corresponds with zero angle in unit circle
            // Makes it easier to do the math.
            double angleFrom3 = Math.PI/2.0-angleFrom12;
            // Move to the outer edge of the circle at correct position
            canvas.drawLine((float) (layoutCenter_x+Math.cos(angleFrom3)*radius),
                    (float) (layoutCenter_y-Math.sin(angleFrom3)*radius),
                    (float) (layoutCenter_x+Math.cos(angleFrom3)*(radius-len)),
                    (float) (layoutCenter_y-Math.sin(angleFrom3)*(radius-len)),
                    paint);
        }
    }
}
