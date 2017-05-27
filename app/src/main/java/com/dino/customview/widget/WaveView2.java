package com.dino.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.dino.customview.R;

import java.util.Random;

/**
 * Created by Dino on 4/19 0019.
 */

public class WaveView2 extends View {

    private Paint mPaint;

    private Path mPath1;
    private Path mPath2;
    //一个波纹长度
    private int mWL = 1000;
    private int mWL2 = 1200;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int offset;

    private int waveHeight = 100;
    private int waveHeight2 = (int)(100*Math.sqrt(2));

    private int mCenterY;

    //屏幕高度
    private int mScreenHeight;
    //屏幕宽度
    private int mScreenWidth;


    public WaveView2(Context context) {
        this(context,null);
    }

    public WaveView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initPaint(attrs,context);
        initAnimator();
    }

    public WaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(attrs,context);
        initAnimator();
    }

    private void initPaint(AttributeSet attrs, Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        for(int i=0;i<array.getIndexCount();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.WaveView_waveLen:
                    mWL = array.getInt(attr, 1000);
                    break;
                case R.styleable.WaveView_waveHeight:
                    waveHeight = array.getInt(attr, 60);
                    break;
                case R.styleable.WaveView_waveCount:
                    mWaveCount = array.getInt(attr, 3);
                    break;
                case R.styleable.WaveView_waveColor:
                    break;
            }
        }

        mPath1 = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.wave_clor1));
        mPaint.setStyle(Paint.Style.STROKE);


        mPath2 = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.wave_clor1));
        mPaint.setStyle(Paint.Style.FILL);

    }

    private void initAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWL);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
//        animator.start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        mWaveCount = (int) Math.round(mScreenWidth / mWL + 1.5);
        mCenterY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath1.reset();
        mPath1.moveTo(-mWL + offset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath1.quadTo((-mWL * 3 / 4) + (i * mWL) + offset, mCenterY + waveHeight, (-mWL / 2) + (i * mWL) + offset, mCenterY);
            mPath1.quadTo((-mWL / 4) + (i * mWL) + offset, mCenterY - waveHeight, i * mWL + offset, mCenterY);
        }

        mPath2.moveTo(-mWL2 + offset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath2.quadTo((-mWL2 * 3 / 4) + (i * mWL2) + offset, mCenterY + waveHeight2, (-mWL2 / 2) + (i * mWL2) + offset, mCenterY);
            mPath2.quadTo((-mWL2 / 4) + (i * mWL2) + offset, mCenterY - waveHeight2, i * mWL2 + offset, mCenterY);
        }



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        mPath1.op(mPath2,Path.Op.XOR);

//        canvas.drawPath(mPath, mPaint);

//        mPath.addPath(mPath,100,0);
//        mPaint.setColor(getResources().getColor(R.color.wave_clor2));

//        mPath.addPath(mPath,-50,0);
//        mPaint.setColor(getResources().getColor(R.color.wave_clor3));

//        mPath.setFillType(Path.FillType.WINDING);
//        mPath.close();
        canvas.drawPath(mPath1, mPaint);
    }

    public void setmWL(int mWL){
        this.mWL = mWL;
        invalidate();
    }

    public void setmWaveCount(int mWaveCount){
        this.mWaveCount = mWaveCount;
        invalidate();
    }

    public void setWaveHeight(int waveHeight){
        this.waveHeight = waveHeight;
        invalidate();
    }
}