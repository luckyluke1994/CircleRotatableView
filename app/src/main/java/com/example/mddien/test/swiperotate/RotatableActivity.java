package com.example.mddien.test.swiperotate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mddien.test.R;

public class RotatableActivity extends AppCompatActivity {
    private RotatableView mRotatableView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotatable);
        mRotatableView = findViewById(R.id.rotateView);
        Button btnSetAngle = findViewById(R.id.bt_setAngle);
        btnSetAngle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRotatableView.setAngle(90f);
            }
        });
    }
}
