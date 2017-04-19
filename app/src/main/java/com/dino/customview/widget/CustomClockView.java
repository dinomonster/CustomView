package com.dino.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.dino.customview.R;

import java.util.Calendar;

/**
 * Created by Dino on 4/19 0019.
 */

public class CustomClockView extends View {
    private Paint mArcPaint;
    private int radius = 300;
    private int strokeWidth = 4;
    //每一小格之间的角度
    private int fixAngle = 6;
    private int b_len = 50;
    private int s_len = 30;
    private Rect mTextBound;
    //时针画笔
    private Paint hPaint;
    //分针画笔
    private Paint minPaint;
    //秒针画笔
    private Paint sPaint;
    //小时  分钟  秒钟
    private int h, m, s;
    private int len = 50;

    private int testSize = 40;
    private int mHourLen = 200;
    private int mMinLen = 250;
    private int mSecondLen = 280;


    public CustomClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }
    public CustomClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }
    private void init(AttributeSet attrs, Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomClockView);
        for(int i=0;i<array.getIndexCount();i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.CustomClockView_radius:
                    radius = array.getInt(attr, 300);
                    break;
                case R.styleable.CustomClockView_textSize:
                    testSize = array.getInt(attr, 40);
                    break;
                case R.styleable.CustomClockView_hourLen:
                    mHourLen = array.getInt(attr, 200);
                    break;
                case R.styleable.CustomClockView_minLen:
                    mMinLen = array.getInt(attr, 250);
                    break;
                case R.styleable.CustomClockView_secondLen:
                    mSecondLen = array.getInt(attr, 280);
                    break;

            }
        }
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(strokeWidth);

        mTextBound = new Rect();

        hPaint = new Paint();
        hPaint.setAntiAlias(true);
        hPaint.setColor(Color.YELLOW);
        hPaint.setAntiAlias(true);
        hPaint.setStyle(Paint.Style.FILL);
        hPaint.setStrokeWidth(10);

        minPaint = new Paint();
        minPaint.setAntiAlias(true);
        minPaint.setColor(Color.MAGENTA);
        minPaint.setAntiAlias(true);
        minPaint.setStyle(Paint.Style.FILL);
        minPaint.setStrokeWidth(7);

        sPaint = new Paint();
        sPaint.setAntiAlias(true);
        sPaint.setColor(Color.BLACK);
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setStrokeWidth(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //画刻度盘
        drawScale(canvas);
        //画指针
        drawPointer(canvas);
        //画原点
        drawPoint(canvas);

        postInvalidateDelayed(1000);
    }

    private void drawPoint(Canvas canvas) {
        mArcPaint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, 10, mArcPaint);
    }

    private void drawPointer(Canvas canvas) {
        h = Calendar.getInstance().get(Calendar.HOUR);
        m = Calendar.getInstance().get(Calendar.MINUTE);
        s = Calendar.getInstance().get(Calendar.SECOND);

        canvas.save();

        drawH(canvas);
        drawM(canvas);
        drawS(canvas);
        canvas.restore();
    }

    /**
     * 画秒针
     *
     * @param canvas
     */
    private void drawS(Canvas canvas) {

        double angle = (s * fixAngle - 90) * Math.PI / 180;
        int startX = -(int) (len * Math.cos(angle));
        int startY = -(int) (len * Math.sin(angle));

        int endX = (int) ((mSecondLen - len) * Math.cos(angle));
        int endY = (int) ((mSecondLen - len) * Math.sin(angle));
        canvas.drawLine(startX, startY, endX, endY, sPaint);

    }

    /**
     * 画分针
     *
     * @param canvas
     */
    private void drawM(Canvas canvas) {
        double angle = (m * 6 - 90) * Math.PI / 180;
        int startX = -(int) (len * Math.cos(angle));
        int startY = -(int) (len * Math.sin(angle));

        int endX = (int) ((mMinLen - len) * Math.cos(angle));
        int endY = (int) ((mMinLen - len) * Math.sin(angle));
        canvas.drawLine(startX, startY, endX, endY, minPaint);
    }

    /**
     * 画时针
     *
     * @param canvas
     */
    private void drawH(Canvas canvas) {
        double angle = (h * 6 * 5 - 90) * Math.PI / 180 + ((m * 1.0f / 60 * 30) * Math.PI / 180);
        int startX = -(int) (len * Math.cos(angle));
        int startY = -(int) (len * Math.sin(angle));

        int endX = (int) ((mHourLen - len) * Math.cos(angle));
        int endY = (int) ((mHourLen - len) * Math.sin(angle));
        canvas.drawLine(startX, startY, endX, endY, hPaint);
    }

    /**
     * 画刻度盘  通过画直线然后再旋转画布实现
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        //移动画布到屏幕正中间
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        //当前数字
        int currentP = 0;
        //绘制的数字
        String text = "";
        int len;
        for (int i = 0; i < 360 / fixAngle; i++) {
            if (i % 5 == 0) {
                if (currentP == 0) {
                    text = "12";
                } else {
                    text = currentP + "";
                }
                currentP++;
                mArcPaint.setColor(Color.BLUE);
                mArcPaint.setTextSize(testSize);
                mArcPaint.getTextBounds(text, 0, text.length(), mTextBound);
                canvas.drawText(text, 0, text.length(), -mTextBound.width() / 2, -(radius + b_len + 20), mArcPaint);

                mArcPaint.setColor(Color.RED);
                len = b_len;

            } else {
                mArcPaint.setColor(Color.GRAY);
                len = s_len;
            }
            canvas.drawLine(0, -radius, 0, -(radius + len), mArcPaint);
            canvas.rotate(fixAngle);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
