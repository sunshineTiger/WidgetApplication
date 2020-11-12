package com.example.widgetlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: WidgetApplication
 * @Package: com.example.widgetlibrary
 * @ClassName: DiffuseView
 * @Description: java类作用描述
 * @Author: zhanghong
 * @CreateDate: 2020/11/12 11:01
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/11/12 11:01
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DiffuseView extends View {

    /**
     * 扩散圆圈颜色
     */
    private int mColor = getResources().getColor(R.color.colorAccent);
    /**
     * 圆圈中心颜色
     */
    private int mCoreColor = getResources().getColor(R.color.colorPrimary);
    /**
     * 中心圆半径
     */
    private float mCoreRadius = 150;
    /**
     * 扩散圆宽度
     */
    private int mDiffuseWidth = 3;
    /**
     * 最大宽度
     */
    private Integer mMaxWidth = 255;
    /**
     * 扩散速度
     */
    private int mDiffuseSpeed = 5;
    /**
     * 是否正在扩散中
     */
    private boolean mIsDiffuse = false;
    // 透明度集合
    private List<Integer> mAlphas = new ArrayList<>();

    // 扩散圆半径集合
    private List<Integer> mWidths = new ArrayList<>();
    private Paint mPaint;

    public DiffuseView(Context context) {
        this(context, null);
    }

    public DiffuseView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DiffuseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init();
        //获取xml属性参数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiffuseView, defStyleAttr, 0);
        //扩散圆圈颜色
        mColor = a.getColor(R.styleable.DiffuseView_diffuse_color, mColor);
        //圆圈中心颜色
        mCoreColor = a.getColor(R.styleable.DiffuseView_diffuse_coreColor, mCoreColor);
        //中心圆半径
        mCoreRadius = a.getFloat(R.styleable.DiffuseView_diffuse_coreRadius, mCoreRadius);
        //扩散圆宽度
        mDiffuseWidth = a.getInt(R.styleable.DiffuseView_diffuse_width, mDiffuseWidth);
        //最大宽度
        mMaxWidth = a.getInt(R.styleable.DiffuseView_diffuse_maxWidth, mMaxWidth);
        //扩散速度
        mDiffuseSpeed = a.getInt(R.styleable.DiffuseView_diffuse_speed, mDiffuseSpeed);
       ;

        //回收TypedArray
        a.recycle();
    }

    private void init() {
        //创建画笔
        mPaint = new Paint();
        //画笔设置抗锯齿
        mPaint.setAntiAlias(true);
        //透明度集合，添加一个透明值为255
        mAlphas.add(255);
        //宽度集合，添加一个宽度为0
        mWidths.add(0);
    }

    /**
     * 绘制
     */
    @Override
    public void invalidate() {
        if (hasWindowFocus()) {
            super.invalidate();
        }
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 绘制扩散圆
        //设置画笔颜色


        mPaint.setColor(mColor);
        for (int i = 0; i < mAlphas.size(); i++) {
            // 设置透明度（获取当前透明度容器的透明值）
            Integer alpha = mAlphas.get(i);
            //给画笔设置透明值
            mPaint.setAlpha(alpha);
            // 绘制扩散圆
            Integer width = mWidths.get(i);
            //绘制圆（获取当前控件的高度和宽度）
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCoreRadius + width, mPaint);

            if (alpha > 0 && width < mMaxWidth) {
                mAlphas.set(i, alpha - mDiffuseSpeed > 0 ? alpha - mDiffuseSpeed : 1);
                mWidths.set(i, width + mDiffuseSpeed);
            }
        }
        // 判断当扩散圆扩散到指定宽度时添加新扩散圆
        if (mWidths.get(mWidths.size() - 1) >= mMaxWidth / mDiffuseWidth) {
            mAlphas.add(255);
            mWidths.add(0);
        }
        // 超过10个扩散圆，删除最外层
        if (mWidths.size() >= 6) {
            mWidths.remove(0);
            mAlphas.remove(0);
        }

        // 绘制中心圆
        mPaint.setAlpha(255);
        mPaint.setColor(mCoreColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCoreRadius, mPaint);

        if (mIsDiffuse) {
            invalidate();
        }
    }

    /**
     * 开始扩散
     */
    public void start() {
        mIsDiffuse = true;
        invalidate();
    }

    /**
     * 停止扩散
     */
    public void stop() {
        mIsDiffuse = false;
        mWidths.clear();
        mAlphas.clear();
        mAlphas.add(255);
        mWidths.add(0);
        invalidate();
    }

    /**
     * 是否扩散中
     */
    public boolean isDiffuse() {
        return mIsDiffuse;
    }

    /**
     * 设置扩散圆颜色
     */
    public void setColor(int colorId) {
        mColor = colorId;
    }

    /**
     * 设置中心圆颜色
     */
    public void setCoreColor(int colorId) {
        mCoreColor = colorId;
    }

    /**
     * 设置中心圆半径
     */
    public void setCoreRadius(int radius) {
        mCoreRadius = radius;
    }

    /**
     * 设置扩散圆宽度(值越小宽度越大)
     */
    public void setDiffuseWidth(int width) {
        mDiffuseWidth = width;
    }

    /**
     * 设置最大宽度
     */
    public void setMaxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
    }

    /**
     * 设置扩散速度，值越大速度越快
     */
    public void setDiffuseSpeed(int speed) {
        mDiffuseSpeed = speed;
    }
}
