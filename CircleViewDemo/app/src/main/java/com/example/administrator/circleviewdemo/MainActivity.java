package com.example.administrator.circleviewdemo;

import android.app.Activity;
import android.os.Bundle;

import java.util.logging.Handler;

/**
 * Created by Administrator on 2015/4/22.
 */
public class MainActivity  extends Activity {

    private CircleView2 mCircle;
    private CircleView3 mCircle3;
    private float mAngle=60;
    int angle=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mCircle= (CircleView2) findViewById(R.id.circle2);
        mCircle3= (CircleView3) findViewById(R.id.circle3);


        mCircle.setPercent(60);
        mCircle3.init();
        mCircle3.launchAnimationByPercent(30);
//        mCircle3.setPercent(30);
    }


}
