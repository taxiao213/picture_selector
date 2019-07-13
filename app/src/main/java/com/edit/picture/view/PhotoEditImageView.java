package com.edit.picture.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import com.edit.picture.model.Mode;
import com.edit.picture.model.PhotoEditCorrection;
import com.edit.picture.model.PhotoEditImage;
import com.edit.picture.model.PhotoEditMovePath;
import com.edit.picture.model.PhotoEditPath;
import com.edit.picture.util.PhotoEditAnimator;
import com.edit.picture.util.PhotoEditCorrectionAnimator;
import com.selector.picture.constant.Constant;
import com.selector.picture.utils.UIUtils;

import java.util.ArrayList;

/**
 * 编辑界面加载ImageView 需要实现 手势放大缩小，裁剪，旋转，马赛克，画笔功能
 * 裁剪可以旋转，有取消按钮操作
 * 画笔有撤回功能
 * 马赛克有撤回功能
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImageView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener, Runnable, ValueAnimator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private PhotoEditImage photoEditImage;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private PhotoEditAnimator mPhotoEditAnimator;
    private PhotoEditCorrectionAnimator mPhotoEditCorrectionAnimator;
    private final int DELAY_TIME = 100;//延迟时间
    private ArrayList<PhotoEditMovePath> mPencilPath;//绘制铅笔路径
    private ArrayList<PhotoEditMovePath> mMosaicPath;//绘制马赛克路径
    private int mPointerCount;//手指接触的个数
    private Paint mPaint;
    private float mPaintPencilWidth = 12F;
    private float mPaintMosaicWidth = 40F;
    private int mPaintColor;
    private Paint.Style mPaintPencilStyle = Paint.Style.STROKE;
    private Paint.Style mPaintMosaicStyle = Paint.Style.FILL_AND_STROKE;

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
        photoEditImage = new PhotoEditImage();
        mPhotoEditAnimator = new PhotoEditAnimator(this, this);
        mPhotoEditCorrectionAnimator = new PhotoEditCorrectionAnimator(this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureListener());
        mPencilPath = new ArrayList<>();
        mMosaicPath = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    /**
     * 设置图片
     *
     * @param bitmap Bitmap
     */
    public void setPhotoEditImage(Bitmap bitmap) {
        if (photoEditImage != null && bitmap != null) {
            photoEditImage.setPhotoEditImage(bitmap);
            invalidate();
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
     * 手势拖动
     * 画笔和马赛克状态禁用拖动
     *
     * @param distanceX X移动距离
     * @param distanceY Y移动距离
     * @return boolean
     */
    private boolean onScrollTo(int distanceX, int distanceY) {
        if (photoEditImage != null) {
            Mode mode = photoEditImage.getMode();
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
        Log.e("drawPath == ", type + "");
        mPointerCount = event.getPointerCount();
        if (photoEditImage != null && mPointerCount == 1) {
            Mode mode = photoEditImage.getMode();
            if (mode != null) {
                if (mode == Mode.PENCIL || mode == Mode.MOSAIC) {
                    PhotoEditPath path = photoEditImage.getPath();
                    if (path != null) {
                        if (type == Constant.TYPE1) {
                            drawPathStart(mode, event, path);
                        } else if (type == Constant.TYPE2) {
                            drawPathMove(event, path);
                        } else if (type == Constant.TYPE3) {
                            drawPathEnd(mode, event, path);
                        }
                    }
                }
            }
        }
    }

    /**
     * 绘制开始
     *
     * @param mode  Mode
     * @param event MotionEvent
     * @param path  PhotoEditPath
     */
    private void drawPathStart(Mode mode, MotionEvent event, PhotoEditPath path) {
        float rawX = event.getRawX();//相对于屏幕
        float rawY = event.getRawY();
        float x = event.getX();//相对于画布
        float y = event.getY();
        path.setPointerId(event.getPointerId(0));
        path.setRest(rawX, rawY);
        mPaint.setColor(mPaintColor);
        if (mode == Mode.PENCIL) {
            mPaint.setStyle(mPaintPencilStyle);
            mPaint.setStrokeWidth(mPaintPencilWidth);
        } else if (mode == Mode.MOSAIC) {
            mPaint.setStyle(mPaintMosaicStyle);
            mPaint.setStrokeWidth(mPaintMosaicWidth);
        }
    }

    /**
     * 绘制过程
     *
     * @param event MotionEvent
     * @param path  PhotoEditPath
     */
    private void drawPathMove(MotionEvent event, PhotoEditPath path) {
        if (path.isPointerId(event.getPointerId(0))) {
            path.lineTo(event.getRawX(), event.getRawY());
            invalidate();
        }
    }

    /**
     * 绘制结束
     *
     * @param mode  Mode
     * @param event MotionEvent
     * @param path  PhotoEditPath
     */
    private void drawPathEnd(Mode mode, MotionEvent event, PhotoEditPath path) {
        if (path.isPointerId(event.getPointerId(0))) {
            PhotoEditMovePath movePath = new PhotoEditMovePath(new Path(path), mPaintColor);
            if (mode == Mode.PENCIL) {
                movePath.setPaintWidth(mPaintPencilWidth);
                movePath.setPaintStyle(mPaintPencilStyle);
                mPencilPath.add(movePath);
            } else if (mode == Mode.MOSAIC) {
                movePath.setPaintWidth(mPaintMosaicWidth);
                movePath.setPaintStyle(mPaintMosaicStyle);
                mMosaicPath.add(movePath);
            }
        }
    }

    /**
     * 绘制集合路径
     *
     * @param canvas Canvas
     * @param list   ArrayList<PhotoEditMovePath>
     */
    private void drawPathList(Canvas canvas, ArrayList<PhotoEditMovePath> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                PhotoEditMovePath editMovePath = list.get(i);
                if (editMovePath != null) {
                    mPaint.setStyle(editMovePath.getPaintStyle());
                    mPaint.setStrokeWidth(editMovePath.getPaintWidth());
                    mPaint.setColor(editMovePath.getPaintColor());
                    canvas.drawPath(editMovePath.getPath(), mPaint);
                }
            }
        }
    }

    /**
     * 绘制图片
     *
     * @param canvas Canvas 画布
     */
    private void onDrawImage(Canvas canvas) {
        canvas.save();
        photoEditImage.drawImage(getContext(), canvas);
        canvas.restore();
    }


    /**
     * 绘制路径
     *
     * @param canvas Canvas 画布
     */
    private void onDrawPath(Canvas canvas) {
        if (photoEditImage != null) {
            canvas.save();
            PhotoEditPath path = photoEditImage.getPath();
            Mode mode = photoEditImage.getMode();
            if (path != null && mode != null) {
                mPaint.setColor(mPaintColor);
                if (mode == Mode.PENCIL) {
                    mPaint.setStyle(mPaintPencilStyle);
                    mPaint.setStrokeWidth(mPaintPencilWidth);
                    canvas.drawPath(path, mPaint);
                } else if (mode == Mode.MOSAIC) {
                    mPaint.setStrokeWidth(mPaintMosaicWidth);
                    mPaint.setStyle(mPaintMosaicStyle);
                    canvas.drawPath(path, mPaint);
                }
            }
            drawPathList(canvas, mPencilPath);
            drawPathList(canvas, mMosaicPath);
            canvas.restore();
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
                    drawPath(event, Constant.TYPE3);
                    break;
            }
            return onTouch(event);
        }
        return false;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            if (photoEditImage != null) {
//                photoEditImage.setGestureScale(detector.getScaleFactor(), getScaleX() + detector.getFocusX(), getScaleY() + detector.getFocusY());
                photoEditImage.setGestureScale(detector.getScaleFactor(), getScreenWidth(), getScreenHeight());
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
    }

    @Override
    public void run() {
        if (photoEditImage != null) {
            float scaleX = photoEditImage.getMatrixScaleX();
            if (scaleX < 1) {
                if (mPhotoEditAnimator != null) {
                    cancelAnimator(PhotoEditCorrection.TYPE1);
                    PhotoEditCorrection photoEditCorrection = new PhotoEditCorrection(PhotoEditCorrection.TYPE1, 0F, 0F, 0F, scaleX);
                    mPhotoEditAnimator.setFloatValues(photoEditCorrection);
                    mPhotoEditAnimator.start();
                }
            } else {
                correctionPosition();
            }
        }
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
        if (photoEditImage != null) {
            PhotoEditCorrection animatedValue = (PhotoEditCorrection) animation.getAnimatedValue();
            if (animatedValue != null) {
                int type = animatedValue.getType();
                if (type == PhotoEditCorrection.TYPE1) {
                    float scale = animatedValue.getScale();
                    photoEditImage.setMatrixScaleX(scale, getScreenWidth(), getScreenHeight());
                    invalidate();
                } else if (type == PhotoEditCorrection.TYPE2) {
                    float startX = animatedValue.getTranX();
                    float endX = animatedValue.getTranY();
                    scrollTo(Math.round(startX), Math.round(endX));
                    Log.e("update", " startX == " + startX + " endX == " + endX);
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
        if (photoEditImage != null) {
            float scale = photoEditImage.getMatrixScaleX();
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
        if (photoEditImage != null) {
            photoEditImage.setMode(mode);
        }
    }

    /**
     * 设置画笔颜色
     *
     * @param frontColor 画笔颜色
     */
    public void setPaintColor(int frontColor) {
//        if (mPaint != null) {
//            mPaint.setColor(frontColor);
//        }
        this.mPaintColor = frontColor;
    }

    /**
     * 撤回路径
     */
    public void withdrawPath() {
        if (photoEditImage != null) {
            Mode mode = photoEditImage.getMode();
            if (mode != null) {
                if (mode == Mode.PENCIL) {
                    deletePath(mPencilPath);
                } else if (mode == Mode.MOSAIC) {
                    deletePath(mMosaicPath);
                }
            }
        }
    }

    /**
     * 将最后一条路径删除 重绘
     *
     * @param pathList ArrayList<PhotoEditMovePath>
     */
    private void deletePath(ArrayList<PhotoEditMovePath> pathList) {
        if (pathList != null && pathList.size() > 0) {
            pathList.remove(pathList.size() - 1);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator(PhotoEditCorrection.TYPE1);
        cancelAnimator(PhotoEditCorrection.TYPE2);
        mPencilPath.clear();
        mMosaicPath.clear();
        Log.e("onDetachedFromWindow ", "取消动画");
    }
}
