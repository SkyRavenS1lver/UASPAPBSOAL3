package com.example.uaspapbsoal2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //Tambahan
    ObjectAnimator fadeInAnimation;
    ObjectAnimator fadeOutAnimation;
    ObjectAnimator rotateAnimation;
    AnimatorSet animatorSet = new AnimatorSet();
    int ANIMATION_DURATION = 1000;
    float leftEye;
    float rightEye;
    float eyeRad;
    float headLeft;
    float headTop;
    float headRight;
    float headBtm;
    float centerX;
    float centerY;
    float radiusX;
    float radiusY;
    private Rect rect = new Rect();
    private RectF head = new RectF();
    //End

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.my_img_view);
        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
        mImgView.setAlpha(0f);

        //Tambahan
        fadeInAnimation = ObjectAnimator.ofFloat(mImgView,"alpha",0f, 1f);
        fadeInAnimation.setDuration(ANIMATION_DURATION);
        fadeInAnimation.setInterpolator(new LinearInterpolator());
        fadeOutAnimation = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        fadeOutAnimation.setDuration(ANIMATION_DURATION);
        fadeOutAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation = ObjectAnimator.ofFloat(mImgView, "rotationY", 0f, 180f);
        rotateAnimation.setDuration(ANIMATION_DURATION).setStartDelay(ANIMATION_DURATION);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        animatorSet.play(fadeOutAnimation).after(rotateAnimation).after(fadeInAnimation);


        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.start();
            }
        });
        //End

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        //Tambahan
        centerX = vWidth / 2f;
        centerY = vHeight / 2f;
        radiusX = vWidth / 3f;
        radiusY = vHeight / 4f;
        headLeft = centerX-(centerX/2);
        headTop = centerY-(centerY/5);
        headRight = centerX+(centerX/2);
        headBtm = centerY+(centerY/5);
        eyeRad = radiusX/9;
        head.set(headLeft, headTop, headRight, headBtm);
        //End

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);

        drawHead();
        drawRightEye();
        drawLeftEye();
        drawEyeConnector();

        //Tambahan
        mImgView.invalidate();
        //End
    }
    //Tambahan
    private void drawEyeConnector() {
        rect.set((int)leftEye, (int)centerY+10,(int)rightEye, (int)centerY-10);
        mCanvas.drawRect(rect, mCirclePaint);
    }

    private void drawLeftEye() {
        leftEye = headLeft+(centerX/4);
        mCanvas.drawCircle(leftEye, centerY, eyeRad, mCirclePaint);

    }

    private void drawRightEye() {
        rightEye = headRight-(centerX/4);
        mCanvas.drawCircle(rightEye, centerY, eyeRad, mCirclePaint);
    }

    private void drawHead() {
        mCanvas.drawRoundRect(head, radiusX*2,radiusY, mHeadPaint);
    }

    //End
}