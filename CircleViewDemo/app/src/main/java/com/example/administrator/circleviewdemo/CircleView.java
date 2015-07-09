package com.example.administrator.circleviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View {
    private final  static String TAG="CircleView";
    private final  static int CIRCLE_OVAL=360;

    private int mWidth;
    private int mHeight;

    private Paint mCirclePaint ;
    private Paint mTextPaint ;
    private Paint mOvalPaint ;
    private RectF mOval;
    private int mOvalSweepAngle;
    private float mCircleStrokeWidth=2;
    private float mOvalStrokeWidth=mCircleStrokeWidth*5;
    private int mPercent;
    private int mPercentTextWidth;
    private int mTextSize=60;
    private int mPercentUnitWidth;
    private Context mContext;
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        mContext=context;
    }

    private void initView() {
        mCirclePaint=new Paint();
        mCirclePaint.setColor(Color.WHITE);// 设置红色
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint();
        mTextPaint.setColor(Color.WHITE);// 设置红色
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);

        mOvalPaint=new Paint();
        mOvalPaint.setColor(Color.WHITE);
        mOvalPaint.setAntiAlias(true);
        mOvalPaint.setStyle(Paint.Style.STROKE);//设置空心
        mOvalPaint.setStrokeWidth(mOvalStrokeWidth);

        setPercent(25);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth=getWidth()/2;
        mHeight=getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mOval=new RectF(mOvalStrokeWidth,mOvalStrokeWidth,mWidth*2-mOvalStrokeWidth,mWidth*2-mOvalStrokeWidth);
        canvas.drawArc(mOval, 270,mOvalSweepAngle, false, mOvalPaint);
        canvas.drawCircle(mWidth,mWidth, mWidth - mOvalStrokeWidth * 3 / 2, mCirclePaint);
        mTextPaint.setTextSize(mTextSize);
        canvas.drawText(mPercent+"", mWidth-mPercentTextWidth/2, mWidth+mTextSize/3, mTextPaint);
        mTextPaint.setTextSize(mTextSize/2);
        canvas.drawText("%", mWidth+mPercentTextWidth/2-mPercentUnitWidth,mWidth+mTextSize/3, mTextPaint);
    }

    /**
     * 设置进度
     * @param percent 进度 （max:100 min:0）
     */
    public void setPercent(int percent){
        mPercent=percent;
        mPercentTextWidth=(int)mTextPaint.measureText(percent+"");
        mTextPaint.setTextSize(mTextSize/2);
        mPercentUnitWidth=(int)mTextPaint.measureText("%");
        mPercentTextWidth+=mPercentUnitWidth;
        mOvalSweepAngle=(percent*CIRCLE_OVAL)/100;
        invalidate();
    }
}
