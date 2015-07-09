package com.example.administrator.circleviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView2 extends View {
    private final static String TAG = "CircleView";
    private final static int CIRCLE_OVAL = 360;

    private int mWidth;

    private Paint mCirclePaint;
    private float mCircleStrokeWidth = 2;

    private Paint mCircleInnerPaint;
    private float mCircleInnerStrokeWidth = mCircleStrokeWidth * 5;

    private Paint mCircInnerlePaint2;

    private Paint mTextPaint;
    private int mTextSize = 60;

    private Paint mOvalPaint;
    private RectF mOval;
    private float mOvalSweepAngle=0;
    private float mOvalStrokeWidth = mCircleStrokeWidth * 5;

    private int mPercent;
    private int mPercentTextWidth;
    private int mPercentUnitWidth;

    private Paint mDotPaint;

    private float mGoalAngle;
    //#77b25e  0
    //#e56425  100

    public CircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.WHITE);// ���ú�ɫ
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);// ���ú�ɫ
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);

        mOvalPaint = new Paint();
        mOvalPaint.setColor(Color.parseColor("#ff33b5e5"));
        mOvalPaint.setAntiAlias(true);
//        mOvalPaint.setStyle(Paint.Style.STROKE);//���ÿ���
        mOvalPaint.setStrokeWidth(mOvalStrokeWidth);

        mCircleInnerPaint = new Paint();
        mCircleInnerPaint.setColor(Color.WHITE);// ���ú�ɫ
        mCircleInnerPaint.setStrokeWidth(mCircleInnerStrokeWidth);
        mCircleInnerPaint.setAntiAlias(true);
        mCircleInnerPaint.setStyle(Paint.Style.STROKE);

        mCircInnerlePaint2 = new Paint();
        mCircInnerlePaint2.setColor(Color.WHITE);// ���ú�ɫ
        mCircInnerlePaint2.setAntiAlias(true);
        mCircInnerlePaint2.setAlpha(30);

        mDotPaint = new Paint();
        mDotPaint.setColor(Color.WHITE);
        mDotPaint.setAntiAlias(true);
//        setPercent(50);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mOval=new RectF(mOvalStrokeWidth,mOvalStrokeWidth,mWidth*2-mOvalStrokeWidth,mWidth*2-mOvalStrokeWidth);
//        canvas.drawArc(mOval, 270,mOvalSweepAngle, false, mOvalPaint);

        //��Բ��
        canvas.drawCircle(mWidth, mWidth, mWidth - mOvalStrokeWidth * 3 / 2, mCirclePaint);


        //��Բ��
        canvas.drawCircle(mWidth, mWidth, mWidth - mCircleInnerStrokeWidth * 4, mCircleInnerPaint);
        //��Բ�����
        canvas.drawCircle(mWidth, mWidth, mWidth - mCircleInnerStrokeWidth * 5, mCircInnerlePaint2);

        //���ٷֱȽǶȵ�����
        mOval = new RectF(mCircleInnerStrokeWidth * 5, mCircleInnerStrokeWidth * 5, mWidth * 2 - mCircleInnerStrokeWidth * 5, mWidth * 2 - mCircleInnerStrokeWidth * 5);
        canvas.drawArc(mOval, 270, mOvalSweepAngle, true, mOvalPaint);


        //����
        mTextPaint.setTextSize(mTextSize);
        canvas.drawText(mPercent + "", mWidth - mPercentTextWidth / 2, mWidth + mTextSize / 3, mTextPaint);
        mTextPaint.setTextSize(mTextSize / 2);
        canvas.drawText("%", mWidth + mPercentTextWidth / 2 - mPercentUnitWidth, mWidth + mTextSize / 3, mTextPaint);

        //����Բ��
        float r=mWidth - mOvalStrokeWidth * 3 / 2;
        double ovalAngle = Math.toRadians(mOvalSweepAngle-90);
        float x=(float)(Math.cos(ovalAngle) * r)+mWidth;
        float y=((float)(Math.sin(ovalAngle) * r))+mWidth;
        canvas.drawCircle(x, y,mCircleInnerStrokeWidth,mDotPaint);

    }

    /**
     * ���ý���
     *
     * @param percent ���� ��max:100 min:0��
     */
    public void setPercent(int percent) {
        mPercent = percent;
        mPercentTextWidth = (int) mTextPaint.measureText(percent + "");
        mTextPaint.setTextSize(mTextSize / 2);
        mPercentUnitWidth = (int) mTextPaint.measureText("%");
        mPercentTextWidth += mPercentUnitWidth;
        mGoalAngle=(percent * CIRCLE_OVAL) / 100;
//        invalidate();
        postDelayed(mAnimRunnable, 500);
    }

    private Runnable mAnimRunnable=new Runnable() {
        @Override
        public void run() {
            if (mOvalSweepAngle >= mGoalAngle) {
                removeCallbacks(mAnimRunnable);
            } else {
                mOvalSweepAngle+=5;
                postInvalidate();
                postDelayed(mAnimRunnable,10);
            }
        }
    };
}
