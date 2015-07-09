package com.example.administrator.circleviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class CircleView3 extends View {
    private final static String TAG = "CircleView";
    private final static int CIRCLE_OVAL = 360;
    private final static int DEFAULT_PERCENT_TYPE = 0;
    private final static int DEFAULT_LABEL_TYPE = 1;

    private int mWidth;
    private int mCenterX;
    private int mCenterY;

    private Paint mCirclePaint;
    private float mCircleStrokeWidth = 2;

    private Paint mCircleInnerPaint;
    private float mCircleInnerStrokeWidth = mCircleStrokeWidth * 5;

    private Paint mCircInnerlePaint2;

    private Paint mTextPaint;
    private int mPercentTextSize;
    private int mLabelSize ;

    private Paint mOvalPaint;
    private RectF mOval;
    private float mOvalSweepAngle=0;
    private float mOvalStrokeWidth = mCircleStrokeWidth * 5;

    private int mPercent=0;
    private int mPercentTextWidth;
    private int mPercentUnitWidth;
    private int mLabelTextWidth;

    private Paint mDotPaint;

    private float mGoalAngle;
    private float mGoalPercent;
    private Handler mHandler;
    private String mStartColor ="#77b25e";
    private String mEndColor="#efc253";

    private String mLabel1="100元/份";
    private String mLabel2="1份起投";
    private int mProgressColor;

    public CircleView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mHandler = new Handler();
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.WHITE);// ���ú�ɫ
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);// ���ú�ɫ
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mPercentTextSize);

        mOvalPaint = new Paint();
        mOvalPaint.setColor(Color.parseColor(mStartColor));
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
        mProgressColor = Color.parseColor(mStartColor);
        setBackgroundColor(mProgressColor);
        mProgressColor = getColorByProgress(mPercent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //��Բ��
        canvas.drawCircle(mCenterX, mCenterY, mWidth - mOvalStrokeWidth * 3 / 2, mCirclePaint);


        //��Բ��
        canvas.drawCircle(mCenterX, mCenterY, mWidth - mCircleInnerStrokeWidth * 4, mCircleInnerPaint);
        //��Բ�����
        canvas.drawCircle(mCenterX, mCenterY, mWidth - mCircleInnerStrokeWidth * 5, mCircInnerlePaint2);

        //���ٷֱȽǶȵ�����
        //��������ĳ���
        float rectf=mWidth - mCircleInnerStrokeWidth * 5;
        mOvalPaint.setColor(mProgressColor);
        mOval = new RectF(mCenterX-rectf, mCenterY-rectf, mCenterX  + rectf, mCenterY + rectf);
        canvas.drawArc(mOval, 270, mOvalSweepAngle, true, mOvalPaint);
        mOvalPaint.setColor(Color.WHITE);
        mOval = new RectF(mCenterX-rectf+15, mCenterY-1, mCenterX  + rectf-15, mCenterY + 1);
        canvas.drawRect(mOval, mOvalPaint);
        //���
        mTextPaint.setTextSize(mPercentTextSize);
        mPercentTextWidth=(int)mTextPaint.measureText(mPercent+"");//�������ֿ��
        mTextPaint.setTextSize(mLabelSize);
        mPercentUnitWidth=(int)mTextPaint.measureText("%");//���㣥���
        mPercentTextWidth+=mPercentUnitWidth;
        mTextPaint.setTextSize(mPercentTextSize);
        canvas.drawText(mPercent + "", mCenterX - mPercentTextWidth / 2, mCenterY - mPercentTextSize / 3, mTextPaint);
        mTextPaint.setTextSize(mLabelSize);
        canvas.drawText("%", mCenterX + mPercentTextWidth / 2 - mPercentUnitWidth, mCenterY - mPercentTextSize / 3, mTextPaint);
        mTextPaint.setTextSize(mLabelSize);
        mLabelTextWidth =(int)mTextPaint.measureText(mLabel1);
        canvas.drawText(mLabel1, mCenterX - mLabelTextWidth / 2, mCenterY + mLabelSize*3/2 , mTextPaint);
        mLabelTextWidth =(int)mTextPaint.measureText(mLabel2);
        canvas.drawText(mLabel2, mCenterX - mLabelTextWidth / 2, mCenterY + mLabelSize*3, mTextPaint);

        //����Բ��
        float r=mWidth - mOvalStrokeWidth * 3 / 2;
        double ovalAngle = Math.toRadians(mOvalSweepAngle-90);
        float x=(float)(Math.cos(ovalAngle) * r)+mCenterX;
        float y=((float)(Math.sin(ovalAngle) * r))+mCenterY;
        canvas.drawCircle(x, y,mCircleInnerStrokeWidth,mDotPaint);

        setBackgroundColor(mProgressColor);
    }


    /**
     *
     * @param percent ��� max:100 min:0
     */
    public void setPercent(int percent) {
        reset();
        mGoalPercent = percent;
        mGoalAngle=(percent * CIRCLE_OVAL) / 100;
        postDelayed(mAnimRunnable, 0);
    }

//    public void setName(String name){
//        mFinanceName=name;
//        mTextPaint.setTextSize(mNameTextSize);
//        mNameTextWidth=mTextPaint.measureText(name);
//    }

    private Runnable mAnimRunnable=new Runnable() {
        @Override
        public void run() {
            if (mPercent >= mGoalPercent) {
                removeCallbacks(mAnimRunnable);
            } else {
                mOvalSweepAngle+=3.6;
                mPercent+=1;
                mProgressColor=getColorByProgress(mPercent);
                postInvalidate();
//                invalidate();
                postDelayed(mAnimRunnable,1);
            }
        }
    };

    /**
     * ��ʼ���߶� ��Ļ��640*240�ı������ø߶�
     */
    public void setInitHeight() {
        int width = getWidth();

        int height = width * 505 / 740;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
        lp.height = height;
        setLayoutParams(lp);
        mWidth=getCircleRectArea()/2;
        mCenterX=width/2;
        mCenterY =height/2;
        mPercentTextSize =getTextSizeByType(DEFAULT_PERCENT_TYPE);
        mLabelSize=getTextSizeByType(DEFAULT_LABEL_TYPE);

    }

    private int getCircleRectArea(){
        int width = getWidth();
        return width * 426 / 740;
    }
    private int getTextSizeByType(int type){
        int width = getWidth();
        switch (type){
            case DEFAULT_PERCENT_TYPE:
            return width * 118 / 740;
            case DEFAULT_LABEL_TYPE:
            return width * 36 / 740;
        }
        return 0;
    }


    public void init(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                setInitHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

        });
    }

    /**
     * ��������
     * @param percent ���
     */
    public void launchAnimationByPercent(final int percent) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPercent(percent);
            }
        }, 500);
    }

    public void reset(){
        mPercent=0;
        mGoalAngle=0;
        mOvalSweepAngle=0;
        invalidate();
    }

    private int getColorByProgress(int p){
        int r=119,g=178,b=94;

        if(p<25){

//            int startColor=Color.rgb(119,178,94);
//            int endColor=Color.rgb(239,194 ,83);
            r=119+(239-119)*p/25;
            g=178+(194-178)*p/25;
            b=94-(94-84)*p/25;
        }else if(p>=25&& p<50){
//            int startColor=Color.rgb(239,194 ,83);
//            int endColor=Color.rgb(237 ,150 ,73);
            p=p-25;
            r=239-(239-237)*p/25;
            g=194-(194-150)*p/25;
            b=83-(83-73)*p/25;
        }else if(p>=50&& p<75){
            p=p-50;
//            int startColor=Color.rgb(237 ,150 ,73);
//            int endColor=Color.rgb(229 ,100 ,37);
            r=237-(237-229)*p/25;
            g=150-(150-100)*p/25;
            b=73-(73-37)*p/25;
        }else{
//            int startColor=Color.rgb(229 ,100 ,37);
//            int endColor=Color.rgb(239 ,50 ,0);
            p=p-75;
            r=239+(239-229)*p/25;
            g=100-(100-50)*p/25;
            b=37-(37-0)*p/25;
        }
        return Color.rgb(r,g,b);

    }
}
