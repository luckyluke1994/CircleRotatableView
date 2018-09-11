package com.example.mddien.test.swiperotate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class RotatableView extends ImageView {
    private RotateTouchListener rotateTouchListener;
    private Matrix mImageMatrix;
    private Matrix mOriginalImageMatrix;
    public float layoutWidth;
    public float layoutHeight;
    public float layoutCenter_x;
    public float layoutCenter_y;
    public float radius;
    private int mPivotX, mPivotY;
    private float rotateR = -5f, rotateL = 5f;

    public RotatableView(Context context) {
        super(context);
        init();
    }

    public RotatableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotatableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * initialization
     */
    private void init() {

        // need to get the real height and width of view
        post(new Runnable() {
            @Override
            public void run() {
                Log.e("CircularListView", "get layout width and height");
                layoutWidth = getWidth();
                layoutHeight = getHeight();
                layoutCenter_x = layoutWidth / 2;
                layoutCenter_y = layoutHeight / 2;
                radius = layoutWidth / 3;
            }
        });

        rotateTouchListener = new RotateTouchListener();
        setOnTouchListener(rotateTouchListener);
        mImageMatrix = new Matrix();
        //mImageMatrix = getImageMatrix();
        this.setScaleType(ImageView.ScaleType.MATRIX);   //required
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            //Shift the image to the center of the view
            int translateX = (w - getDrawable().getIntrinsicWidth()) / 2;
            int translateY = (h - getDrawable().getIntrinsicHeight()) / 2;
            mImageMatrix.setTranslate(translateX, translateY);

            //Get the center point for future scale and rotate transforms
            mPivotX = w / 2;
            mPivotY = h / 2;

            RectF drawableRect = new RectF(0, 0, getDrawable().getIntrinsicWidth(),
                    getDrawable().getIntrinsicHeight());
            RectF viewRect = new RectF(0, 0, getWidth(),
                    getHeight());
            mImageMatrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
            mOriginalImageMatrix = new Matrix(mImageMatrix);
            setImageMatrix(mImageMatrix);
        }
    }

    public void rotateRight() {
        mImageMatrix.postRotate(rotateR, mPivotX, mPivotY);
        //Post the rotation to the image
        setImageMatrix(mImageMatrix);
        //invalidate();
        Log.e("RotateView", "rotateRight");
    }

    public void rotateLeft() {
        mImageMatrix.postRotate(rotateL, mPivotX, mPivotY);
        //Post the rotation to the image
        setImageMatrix(mImageMatrix);
        //invalidate();
        Log.e("RotateView", "rotateLeft");
    }

    public void rotateAngle(float degrees) {
        mImageMatrix.postRotate(degrees, mPivotX, mPivotY);
        setImageMatrix(mImageMatrix);
    }

    public void setAngle(float degrees) {
        mImageMatrix = new Matrix(mOriginalImageMatrix);
        mImageMatrix.postRotate(degrees, mPivotX, mPivotY);
        setImageMatrix(mImageMatrix);
    }
}
