package com.edit.picture.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.edit.picture.fragment.PhotoEditFragment;
import com.edit.picture.model.Mode;
import com.edit.picture.model.PhotoEditCorrection;
import com.edit.picture.model.PhotoEditImage;
import com.edit.picture.model.PhotoEditPath;
import com.edit.picture.util.PhotoEditAnimator;
import com.edit.picture.util.PhotoEditCorrectionAnimator;
import com.selector.picture.constant.Constant;
import com.selector.picture.utils.UIUtils;

/**
 * 编辑界面加载ImageView 需要实现 手势放大缩小，裁剪，旋转，马赛克，涂鸦功能
 * 裁剪可以旋转，有取消按钮操作
 * 涂鸦有撤回功能
 * 马赛克有撤回功能
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImageView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener, Runnable, ValueAnimator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    private PhotoEditFragment mPhotoEditFragment;
    private PhotoEditImage mPhotoEditImage;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private PhotoEditAnimator mPhotoEditAnimator;
    private PhotoEditCorrectionAnimator mPhotoEditCorrectionAnimator;
    private final static int DELAY_TIME = 100;//延迟时间
    private int mPointerCount;//手指接触的个数
    private Bitmap mosaicBitmap;
    private int mMosaicColor = getResources().getColor(android.R.color.white);

    public PhotoEditImageView(@NonNull Context context) {
        this(context, null);
    }

    public PhotoEditImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPhotoEditImage = new PhotoEditImage(context, this);
        mPhotoEditAnimator = new PhotoEditAnimator(this, this);
        mPhotoEditCorrectionAnimator = new PhotoEditCorrectionAnimator(this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(PhotoEditFragment fragment, Bitmap bitmap) {
        this.mPhotoEditFragment = fragment;
        if (mPhotoEditImage != null && bitmap != null) {
            mPhotoEditImage.setResourceBitmap(bitmap);
            mosaicBitmap = mPhotoEditImage.getMosaicBitmap();
            invalidate();
        }
    }

    /**
     * 设置编辑按钮是否隐藏
     *
     * @param isVisible true 显示  false 隐藏
     */
    public void onPath(boolean isVisible) {
        if (mPhotoEditFragment != null) {
            mPhotoEditFragment.setEditVisible(isVisible);
        }
    }

    /**
     * 缩放照片
     *
     * @param event MotionEvent
     */
    private boolean onTouch(MotionEvent event) {
        mPointerCount = event.getPointerCount();
        boolean touch = mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(this);
                drawPath(event, Constant.TYPE1);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath(event, Constant.TYPE2);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                postDelayed(this, DELAY_TIME);
                setSacle(false);
                drawPath(event, Constant.TYPE3);
                break;
        }
        return touch;
    }

    /**
     * 手势拖动
     *
     * @param distanceX X移动距离
     * @param distanceY Y移动距离
     * @return boolean
     */
    private boolean onScroll(float distanceX, float distanceY) {
        return onScrollTo(getScrollX() + Math.round(distanceX), getScrollY() + Math.round(distanceY));
    }

    /**
     * 设置是否在缩放中
     *
     * @param isScale true 缩放  false 不缩放
     */
    private void setSacle(boolean isScale) {
        if (mPhotoEditImage != null) {
            mPhotoEditImage.setScale(isScale);
        }
    }

    /**
     * 手势拖动
     * 画笔和马赛克状态禁用拖动
     *
     * @param distanceX X移动距离
     * @param distanceY Y移动距离
     * @return boolean
     */
    private boolean onScrollTo(int distanceX, int distanceY) {
        if (mPhotoEditImage != null) {
            Mode mode = mPhotoEditImage.getMode();
            if (mode != null) {
                if (mode == Mode.PENCIL || mode == Mode.MOSAIC) {
                    return false;
                }
            }
        }
        if (getScrollX() != distanceX || getScrollY() != distanceY) {
            scrollTo(distanceX, distanceY);
            return true;
        }
        return false;
    }

    /**
     * @param event MotionEvent
     * @param type  {@link Constant#ACTION_TYPE1 绘制起点}
     *              {@link Constant#ACTION_TYPE2 绘制过程}
     *              {@link Constant#ACTION_TYPE3 绘制结束}
     */
    private void drawPath(MotionEvent event, int type) {
        if (isCorrectAnimator(event)) {
            clearStatus();
            return;
        }
        mPointerCount = event.getPointerCount();
        if (mPhotoEditImage != null && mPointerCount == 1) {
            mPhotoEditImage.drawPath(event, type);
        }
    }

    /**
     * 撤回路径
     */
    public void withdrawPath() {
        if (mPhotoEditImage != null)
            mPhotoEditImage.withdrawPath();
    }

    /**
     * 绘制图片
     *
     * @param canvas Canvas
     */
    private void onDrawImage(Canvas canvas) {
        if (mPhotoEditImage != null)
            mPhotoEditImage.drawImage(canvas);
    }

    /**
     * 绘制路径
     *
     * @param canvas Canvas
     */
    private void onDrawPath(Canvas canvas) {
        if (mPhotoEditImage != null)
            mPhotoEditImage.drawPath(canvas);
    }

    /**
     * 是否在执行缩放动画
     *
     * @param event MotionEvent
     * @return true 不绘制路径 false 绘制路径
     */
    private boolean isCorrectAnimator(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) return true;
        if (mPhotoEditImage != null) {
            float scaleX = mPhotoEditImage.getMatrixScaleX();
            if (scaleX < 1) {
                return true;
            }
            if (mPhotoEditImage.isScale()) return true;
        }
        return false;
    }

    /**
     * 清除path 路径
     */
    private void clearStatus() {
        if (mPhotoEditImage != null) {
            PhotoEditPath path = mPhotoEditImage.getPath();
            if (path != null) {
                path.clearStatus();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImage(canvas);
        onDrawPath(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            return onTouch(event);
        }
        return false;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        /*缩放进行中，返回值表示是否下次缩放需要重置，如果返回ture，那么detector就会重置缩放事件，如果返回false，detector会在之前的缩放上继续进行计算*/
        setSacle(true);
        if (mPointerCount > 1) {
            if (mPhotoEditImage != null) {
//                mPhotoEditImage.setGestureScale(detector.getScaleFactor(), getScaleX() + detector.getFocusX(), getScaleY() + detector.getFocusY());
                mPhotoEditImage.setGestureScale(detector.getScaleFactor(), getScreenWidth(), getScreenHeight());
                invalidate();
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            return true;
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 手势监听
     * {@link GestureListener#onDoubleTap(MotionEvent) 双击放大缩小}
     * {@link GestureListener#onScroll(MotionEvent, MotionEvent, float, float)}  拖动图片}
     */
    public class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return PhotoEditImageView.this.onScroll(distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            if (mPhotoEditImage != null) {
                Mode mode = mPhotoEditImage.getMode();
                if (mode != null && mode == Mode.NONE) {
                    if (!isCorrectAnimator(event)) {
                        float scaleX = mPhotoEditImage.getMatrixScaleX();
                        float endScale = mPhotoEditImage.getDoubleScale();
                        if (mPhotoEditAnimator != null) {
                            cancelAnimator(PhotoEditCorrection.TYPE1);
                            PhotoEditCorrection photoEditCorrection = new PhotoEditCorrection(PhotoEditCorrection.TYPE1, 0F, 0F, 0F, scaleX);
                            mPhotoEditAnimator.setFloatValues(photoEditCorrection, endScale);
                            mPhotoEditAnimator.start();
                        }
                        return true;
                    } else {
                        clearStatus();
                    }
                }
            }
            return super.onDoubleTap(event);
        }
    }

    @Override
    public void run() {
        if (mPhotoEditImage != null) {
            float scaleX = mPhotoEditImage.getMatrixScaleX();
            if (scaleX < 1) {
                if (mPhotoEditAnimator != null) {
                    cancelAnimator(PhotoEditCorrection.TYPE1);
                    PhotoEditCorrection photoEditCorrection = new PhotoEditCorrection(PhotoEditCorrection.TYPE1, 0F, 0F, 0F, scaleX);
                    mPhotoEditAnimator.setFloatValues(photoEditCorrection, 1.0F);
                    mPhotoEditAnimator.start();
                }
            } else {
                correctionPosition();
            }
        }
        onPath(true);
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        correctionPosition();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (mPhotoEditImage != null) {
            PhotoEditCorrection animatedValue = (PhotoEditCorrection) animation.getAnimatedValue();
            if (animatedValue != null) {
                int type = animatedValue.getType();
                if (type == PhotoEditCorrection.TYPE1) {
                    float scale = animatedValue.getScale();
                    mPhotoEditImage.setMatrixScaleX(scale, getScreenWidth(), getScreenHeight());
                    invalidate();
                } else if (type == PhotoEditCorrection.TYPE2) {
                    float startX = animatedValue.getTranX();
                    float endX = animatedValue.getTranY();
                    scrollTo(Math.round(startX), Math.round(endX));
//                    Log.e("update", " startX == " + startX + " endX == " + endX);
                }
            }
        }
    }

    /**
     * 获取屏幕高度 坐标
     *
     * @return float
     */
    private float getScreenHeight() {
        return UIUtils.getScreenHeight(getContext()) / 2.0F;
    }

    /**
     * 获取屏幕宽度 坐标
     *
     * @return float
     */
    private float getScreenWidth() {
        return UIUtils.getScreenWidth(getContext()) / 2.0F;
    }

    /**
     * 取消动画
     *
     * @param type PhotoEditCorrection.TYPE1 取消缩放动画  PhotoEditCorrection.TYPE2 取消滑动动画
     */
    private void cancelAnimator(int type) {
        if (type == PhotoEditCorrection.TYPE1) {
            if (mPhotoEditAnimator != null) {
                mPhotoEditAnimator.cancel();
            }
        } else if (type == PhotoEditCorrection.TYPE2) {
            if (mPhotoEditCorrectionAnimator != null) {
                mPhotoEditCorrectionAnimator.cancel();
            }
        }
    }

    /**
     * 位置修正动画
     */
    private void correctionPosition() {
        if (mPhotoEditImage != null) {
            float scale = mPhotoEditImage.getMatrixScaleX();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            float absoluteValue = Math.abs(scale - 1);//缩放的系数 绝对值
            float transX = absoluteValue * getScreenWidth();//修正的X移动距离
            float transY = absoluteValue * getScreenHeight();//修正的Y移动距离
            Log.e("scroll ", " scale == " + scale + " scrollX == " + getScrollX() + " scrollY == " + getScrollY() + " transX == " + transX + " transY == " + transY);
            if (Math.abs(scrollX) >= transX && Math.abs(scrollY) >= transY) {
                //相对当前位置移动 四个边角
                if (scrollX < 0 && scrollY < 0) {
                    //左上角
                    scroll(-Math.round(transX), -Math.round(transY));
                } else if (scrollX < 0 && scrollY > 0) {
                    //左下角
                    scroll(-Math.round(transX), Math.round(transY));
                } else if (scrollX >= 0 && scrollY < 0) {
                    //右上角
                    scroll(Math.round(transX), -Math.round(transY));
                } else if (scrollX >= 0 && scrollY > 0) {
                    //右下角
                    scroll(Math.round(transX), Math.round(transY));
                }
            } else if (Math.abs(scrollX) >= transX) {
                //相对当前位置移动 左右移动 如果scrollX < 0,从右向左移动 正 ; 反之 从左向右移动 负
                if (scrollX < 0) {
                    scroll(-Math.round(transX), scrollY);
                } else {
                    scroll(Math.round(transX), scrollY);
                }
            } else if (Math.abs(scrollY) >= transY) {
                //相对当前位置移动 上下移动 如果scrollY < 0,从下向上移动 正 ; 反之 从上向下移动 负
                if (scrollY < 0) {
                    scroll(scrollX, -Math.round(transY));
                } else {
                    scroll(scrollX, Math.round(transY));
                }
            }
            Log.e("scroll ", " scale == " + scale + " scrollX == " + getScrollX() + " scrollY == " + getScrollY() + " transX == " + transX + " transY == " + transY);

        }
    }


    /**
     * View滑动
     *
     * @param transX X移动距离
     * @param transY Y移动距离
     */
    public void scroll(float transX, float transY) {
        cancelAnimator(PhotoEditCorrection.TYPE2);
        PhotoEditCorrection start = new PhotoEditCorrection(PhotoEditCorrection.TYPE2, getScrollX(), getScrollY(), 0F, 0F);
        PhotoEditCorrection end = new PhotoEditCorrection(PhotoEditCorrection.TYPE2, Math.round(transX), Math.round(transY), 0F, 0F);
        mPhotoEditCorrectionAnimator.setCorrectionValues(start, end);
        mPhotoEditCorrectionAnimator.start();
    }

    /**
     * 设置模式
     *
     * @param mode Mode
     */
    public void setMode(Mode mode) {
        if (mPhotoEditImage != null) {
            mPhotoEditImage.setMode(mode);
        }
    }

    /**
     * 设置画笔颜色
     *
     * @param frontColor 画笔颜色
     */
    public void setPaintColor(int frontColor) {
        if (mPhotoEditImage != null) mPhotoEditImage.setPaintColor(frontColor);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator(PhotoEditCorrection.TYPE1);
        cancelAnimator(PhotoEditCorrection.TYPE2);
        if (mPhotoEditImage != null) {
            mPhotoEditImage.onDestroyView();
        }
    }
}
