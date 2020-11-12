package com.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: WidgetApplication
 * @Package: com.example.widgetlibrary
 * @ClassName: CircleImage
 * @Description: java类作用描述
 * @Author: zhanghong
 * @CreateDate: 2020/11/12 9:48
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/12 9:48
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CircleImage extends View {

    private static final String TAG = CircleImage.class.getSimpleName();
    private Paint myPaint;
    private int myColor = getResources().getColor(R.color.colorAccent);
    private List<Integer> widthList;
    private List<Integer> alphaList;
    private int radius = 20;
    private int step = 6;

    /**
     * 在java代码里new的时候会用到
     *
     * @param context
     */
    public CircleImage(Context context) {
//        super(context);
        this(context, null);
    }

    /**
     * 在xml布局文件中使用时自动调用
     *
     * @param context
     */
    public CircleImage(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);

    }


    /**
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImage);
        float height = typedArray.getDimension(R.styleable.CircleImage_circleHeight, 1);
        float width = typedArray.getDimension(R.styleable.CircleImage_circleWidth, 1);
        Log.e(TAG, "CircleImage width:" + width + "  height:" + height);
        typedArray.recycle();
    }

    private void init() {
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setColor(myColor);
        widthList = new ArrayList<>();
        widthList.add(0);
        alphaList = new ArrayList<>();
        alphaList.add(255);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.e(TAG, "canvas start");
        for (int i = 0; i < alphaList.size(); i++) {
            //获取当前透明度值
            Integer alpha = alphaList.get(i);
            myPaint.setAlpha(alpha);
            Integer width = widthList.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, width + radius, myPaint);
            widthList.set(i, Math.min(width + step, 255));
            alphaList.set(i, alpha-step >= 0 ? alpha-step : 1);
        }
        if (widthList.get(widthList.size() - 1) > 80) {
            widthList.add(0);
            alphaList.add(255);
        }
        if (widthList.size() > 10) {
            widthList.remove(0);
            alphaList.remove(0);
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout start");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure start");
        // 获取测量模式（Mode）
        Log.e(TAG, "onMeasure widthMeasureSpec getMode:" + MeasureSpec.getMode(widthMeasureSpec));
        Log.e(TAG, "onMeasure widthMeasureSpec getSize:" + MeasureSpec.getSize(widthMeasureSpec));
        // 获取测量大小（Size）
        Log.e(TAG, "onMeasure heightMeasureSpec getMode:" + MeasureSpec.getMode(heightMeasureSpec));
        Log.e(TAG, "onMeasure heightMeasureSpec getSize:" + MeasureSpec.getSize(heightMeasureSpec));
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                //精准模式
                Log.e(TAG, "widthMeasureSpec onMeasure 精准模式:");
                break;
            case MeasureSpec.AT_MOST:
                //最大模式
                Log.e(TAG, "widthMeasureSpec onMeasure 最大模式:");
                break;
            case MeasureSpec.UNSPECIFIED:
                //无限制
                Log.e(TAG, "widthMeasureSpec onMeasure 无限制:");
                break;
            default:
                break;
        }
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                //精准模式
                Log.e(TAG, "heightMeasureSpec onMeasure 精准模式:");
                break;
            case MeasureSpec.AT_MOST:
                //最大模式
                Log.e(TAG, "heightMeasureSpec onMeasure 最大模式:");
                break;
            case MeasureSpec.UNSPECIFIED:
                //无限制
                Log.e(TAG, "heightMeasureSpec onMeasure 无限制:");
                break;
            default:
                break;
        }
    }
}
