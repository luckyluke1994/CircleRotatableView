package com.example.mddien.test.swiperotate;

import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.mddien.test.CircularListView;

public class RotateTouchListener implements View.OnTouchListener {
    private float init_x = 0;
    private float init_y = 0;
    private float pre_x = 0;
    private float pre_y = 0;
    private float cur_x = 0;
    private float cur_y = 0;
    private float move_x = 0;
    private float move_y = 0;
    private int mLastAngle = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!(v instanceof RotatableView)) return false;

        final RotatableView rotatableView = (RotatableView) v;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cur_x = event.getX();
                cur_y = event.getY();
                init_x = event.getX();
                init_y = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                pre_x = cur_x;
                pre_y = cur_y;
                cur_x = event.getX();
                cur_y = event.getY();

                float diff_x = cur_x - pre_x;
                float diff_y = cur_y - pre_y;
                move_x = init_x - cur_x;
                move_y = init_y - cur_y;
                float moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
                double radians = Math.atan(diff_y / diff_x);
                //Convert to degrees
                int degrees = (int)(radians * 180 / Math.PI);

                if (cur_y > rotatableView.layoutCenter_y && cur_x > rotatableView.layoutCenter_x) {
                    if (diff_x > 0 && diff_y <= 0) rotatableView.rotateRight();
                    else if (diff_x < 0 && diff_y >= 0)rotatableView.rotateLeft();
                } else if (cur_y < rotatableView.layoutCenter_y && cur_x > rotatableView.layoutCenter_x) {
                    if (diff_x < 0 && diff_y <= 0) rotatableView.rotateRight();
                    else if (diff_x > 0 && diff_y >= 0)rotatableView.rotateLeft();
                } else if (cur_y < rotatableView.layoutCenter_y && cur_x < rotatableView.layoutCenter_x) {
                    if (diff_x < 0 && diff_y >= 0) rotatableView.rotateRight();
                    else if (diff_x > 0 && diff_y <= 0)rotatableView.rotateLeft();
                } else {
                    if (diff_x > 0 && diff_y >= 0) rotatableView.rotateRight();
                    else if (diff_x < 0 && diff_y <= 0)rotatableView.rotateLeft();
                }

                return true;

            case MotionEvent.ACTION_UP:

                // it is an click action if move distance < min distance
                moveDistance = (float) Math.sqrt(move_x * move_x + move_y * move_y);
//                if (moveDistance < minClickDistance && !isCircularMoving) {
//                    for (int i = 0; i < circularView.itemViewList.size(); i++) {
//                        View view = circularView.itemViewList.get(i);
//                        if (isTouchInsideView(cur_x, cur_y, view)) {
//                            itemClickListener.onItemClick(view, i);
//
//                            // set click animation
//                            ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
//                                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
//                                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//                            animation.setDuration(300);
//                            animation.setInterpolator(new OvershootInterpolator());
//                            view.startAnimation(animation);
//
//                            break;
//                        }
//                    }
//                }
//                isCircularMoving = false; // reset moving state when event ACTION_UP
                return true;
        }
        return false;
    }

    private int getCWDirection(float diff_x, float diff_y) {
        if ((diff_x > 0 && diff_y <= 0) ||
                (diff_x < 0 && diff_y <=0) ||
                (diff_x < 0 && diff_y >= 0) ||
                (diff_x > 0 && diff_y >= 0)) {
            return 0;
        } else {
            return 1;
        }
    }

    private float getRotationAngle(final float x1, final float y1, final float x2, final float y2) {
        final float CLOCK_WISE = 1.0f;
        final float ANTI_CLOCK_WISE = -1.0f;

        final float deltaX = Math.abs(x2 - x1);
        final float deltaY = Math.abs(y2 - y1);

        if (deltaX == 0) {
            return 0.0f;
        }

        final float angle = (float) Math.toDegrees(Math.atan(deltaY / deltaX));

        if (x1 <= x2 && y2 <= y1) {
            return CLOCK_WISE * (90.0f - angle);
        } else if (x2 <= x1 && y2 <= y1) {
            return ANTI_CLOCK_WISE * (90.0f - angle);
        } else if (x2 <= x1 && y1 <= y2) {
            return ANTI_CLOCK_WISE * (90.0f + angle);
        } else if (x1 <= x2 && y1 <= y2) {
            return CLOCK_WISE * (90.0f + angle);
        } else {
            return 0.0f;
        }
    }
}
