package com.edit.picture.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;

import com.edit.picture.view.PhotoEditImageView;
import com.selector.picture.R;
import com.selector.picture.constant.Constant;
import com.selector.picture.utils.UIUtils;

import java.util.ArrayList;


/**
 * 编辑图片model
 * Create by yin13 smyhvae on 2019/6/27
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class PhotoEditImage {
    private Matrix mMatrix = new Matrix();
    private Mode mode = Mode.NONE;//默认
    private float SCALE_MAX = 2.8F;
    private float SCALE_MIN = 1.0F;
    private float SCALE_ADD = 1.0F;
    private float[] MATRIX_FLOAT = new float[9];
    private PhotoEditPath mPath;//绘制路径
    private Context mContext;
    private boolean isScale;//是否在放大缩小

    private RectF mRectFDst;//原始bitmap移动后位置
    private RectF mRectSourceFDst;//原始位置

    private PhotoEditImageView mPhotoEditImageView;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Bitmap mMosaicBitmap;
    private Bitmap mResourceBitmap;
    private ArrayList<PhotoEditMovePath> mPencilPathList;//绘制铅笔路径
    private ArrayList<PhotoEditMovePath> mMosaicPathList;//绘制马赛克路径
    private Paint mPencilPaint;
    private Paint mMosaicPaint;
    private Paint mDrawMosaicPaint;
    private int mPaintColor;//默认画笔颜色
    private float mPaintPencilWidth = 15F;
    private float mPaintMosaicWidth = 60F;
    private float mMosaicScaleSize = 20f;
    private Paint.Style mPaintStyle = Paint.Style.STROKE;

    public PhotoEditImage(Context context, PhotoEditImageView photoEditImageView1) {
        this.mContext = context;
        this.mPhotoEditImageView = photoEditImageView1;
        mPath = new PhotoEditPath();
        mPencilPathList = new ArrayList<>();
        mMosaicPathList = new ArrayList<>();
        initPencilPaint();
        initMosaicPaint();
        initDrawMosaicPaint();
    }

    /**
     * 设置原始图片
     *
     * @param bit Bitmap
     */
    public void setResourceBitmap(Bitmap bit) {
        this.mResourceBitmap = bit;
        mBitmap = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        initBitmap();
        mMosaicBitmap = getMosaicBitmap();
    }

    /**
     * 初始化bitmap
     */
    private void initBitmap() {
        mCanvas.save();
        int width = mResourceBitmap.getWidth();
        int height = mResourceBitmap.getHeight();
        mRectFDst = new RectF(getLeft(width), getTop(height), getRight(width), getBottom(height));
        mRectSourceFDst = mRectFDst;
        mCanvas.drawBitmap(mResourceBitmap, null, mRectFDst, null);
        mCanvas.restore();
        setRectFDst();
    }

    /**
     * 创建马赛克的图
     *
     * @return Bitmap
     */
    public Bitmap getMosaicBitmap() {
        Bitmap mMosaicBitmap = null;
        int mWidth = mResourceBitmap.getWidth();
        int mHeight = mResourceBitmap.getHeight();
        int w = Math.round(mWidth / mMosaicScaleSize);
        int h = Math.round(mHeight / mMosaicScaleSize);
        // 先创建小图
        mMosaicBitmap = Bitmap.createScaledBitmap(mResourceBitmap, w, h, false);
        mMosaicBitmap = Bitmap.createScaledBitmap(mMosaicBitmap, mWidth, mHeight, false);
        return mMosaicBitmap;
    }

    /**
     * 绘制图片 小图片放大问题
     * mCanvas.initSourceBitmap(mResourceBitmap, rect, rectF1, null); 第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
     * 根据矩阵绘制Bimap
     *
     * @param canvas Canvas
     */
    public void drawImage(Canvas canvas) {
        if (mBitmap == null) return;
        canvas.save();
        canvas.drawBitmap(mBitmap, mMatrix, null);
        canvas.restore();
   /*     mRectFDst = new RectF(getLeft(scaleWidth), getTop(scaleHeight), getRight(scaleWidth), getBottom(scaleHeight));
        canvas.initSourceBitmap(mBitmap, null, mRectFDst, null);*/
    }

    /**
     * 绘制路径
     *
     * @param canvas Canvas
     */
    public void drawPath(Canvas canvas) {
        Mode mode = getMode();
        if (mPath != null && !mPath.isEmpty() && mode != null) {
            if (mode == Mode.PENCIL) {
                canvas.save();
                Rect clipBounds = canvas.getClipBounds();
                canvas.clipRect(mRectFDst);
                canvas.translate(clipBounds.left, clipBounds.top);
                canvas.drawPath(mPath, mPencilPaint);
                canvas.restore();
            } else if (mode == Mode.MOSAIC) {
                Rect clipBounds = canvas.getClipBounds();
                int layerCount = canvas.saveLayer(mRectFDst, null, Canvas.ALL_SAVE_FLAG);
                if (mMosaicBitmap != null)
                    canvas.drawBitmap(mMosaicBitmap, null, mRectFDst, mDrawMosaicPaint);
                canvas.clipRect(mRectFDst);
                canvas.translate(clipBounds.left, clipBounds.top);
                canvas.drawPath(mPath, mMosaicPaint);
                canvas.restoreToCount(layerCount);
            } else if (mode == Mode.TEXT) {

            }
        }
    }

    /**
     * 绘制集合路径  先绘制马赛克 后绘制涂鸦
     *
     * @param mode Mode
     * @param list ArrayList<PhotoEditMovePath>
     */
    private void drawPathList(Mode mode, ArrayList<PhotoEditMovePath> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                PhotoEditMovePath path = list.get(i);
                drawPath(mode, path);
            }
        }
    }

    /**
     * 将最后一条路径删除 重绘
     */
    public void withdrawPath() {
        if (mode != null) {
            if (mode == Mode.PENCIL) {
                deletePath(mPencilPathList);
            } else if (mode == Mode.MOSAIC) {
                deletePath(mMosaicPathList);
            }
            recoveryCanvasStatus();
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
        }
    }

    /**
     * 清除画布
     */
    private void cleanCanvas() {
        mCanvas.save();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mCanvas.restore();
    }

    /**
     * 还原画布状态
     */
    private void recoveryCanvasStatus() {
        cleanCanvas();
        initBitmap();
        drawPathList(Mode.MOSAIC, mMosaicPathList);
        drawPathList(Mode.PENCIL, mPencilPathList);
        invalidate();
    }

    /**
     * 设置画笔颜色
     *
     * @param frontColor 画笔颜色
     */
    public void setPaintColor(int frontColor) {
        this.mPaintColor = frontColor;
    }

    /**
     * @param event MotionEvent
     * @param type  {@link Constant#ACTION_TYPE1 绘制起点}
     *              {@link Constant#ACTION_TYPE2 绘制过程}
     *              {@link Constant#ACTION_TYPE3 绘制结束}
     */
    public void drawPath(MotionEvent event, int type) {
        if (mode != null) {
            if (mode == Mode.PENCIL || mode == Mode.MOSAIC) {
                if (mPath != null) {
                    if (type == Constant.TYPE1) {
                        drawPathStart(event);
                    } else if (type == Constant.TYPE2) {
                        drawPathMove(event);
                        onPath(false);
                    } else if (type == Constant.TYPE3) {
                        drawPathEnd(event);
                    }
                }
            }
        }
    }

    /**
     * 绘制开始
     *
     * @param event MotionEvent
     */
    private void drawPathStart(MotionEvent event) {
        float rawX = event.getRawX();//相对于屏幕
        float rawY = event.getRawY();
        mPath.setRest(rawX, rawY);
        if (mode != null) {
            mPencilPaint.setColor(mPaintColor);
            mPencilPaint.setStyle(mPaintStyle);
            if (mode == Mode.PENCIL) {
                mPencilPaint.setStrokeWidth(mPaintPencilWidth);
            } else if (mode == Mode.MOSAIC) {
                mPencilPaint.setStrokeWidth(mPaintMosaicWidth);
            }
        }
    }

    /**
     * 绘制过程
     *
     * @param event MotionEvent
     */
    private void drawPathMove(MotionEvent event) {
        if (mPath.isPointerId(event.getPointerId(0))) {
            mPath.lineTo(event.getRawX(), event.getRawY());
            invalidate();
        }
    }


    /**
     * 绘制结束
     *
     * @param event MotionEvent
     */
    private void drawPathEnd(MotionEvent event) {
        if (mPath.isPointerId(event.getPointerId(0))) {
            Path pathEnd = new Path(mPath);
            float scaleX = getMatrixScaleX();
            if (scaleX != 0F) {
                if (mode != null) {
                    Matrix matrix = new Matrix();
                    if (mPhotoEditImageView != null) {
                        matrix.setTranslate(mPhotoEditImageView.getScrollX(), mPhotoEditImageView.getScrollY());
                    }
                    matrix.postTranslate(-getMatrixTranX(), -getMatrixTranY());
                    matrix.postScale(1 / scaleX, 1 / scaleX);
                    pathEnd.transform(matrix);//路径转换
                    PhotoEditMovePath movePath = new PhotoEditMovePath(pathEnd, mPaintColor, scaleX);
                    movePath.setPaintStyle(mPaintStyle);
                    if (mode == Mode.PENCIL) {
                        movePath.setPaintWidth(mPaintPencilWidth / scaleX);
                        if (mPencilPathList != null) {
                            mPencilPathList.add(movePath);
                        }
                    } else if (mode == Mode.MOSAIC) {
                        movePath.setPaintWidth(mPaintMosaicWidth / scaleX);
                        if (mMosaicPathList != null) {
                            mMosaicPathList.add(movePath);
                        }
                    }
                    // 2019 drawPath(mode, movePath);
                    recoveryCanvasStatus();
                }
            }
        }
        mPath.clearStatus();
    }

    /**
     * 绘制路径
     *
     * @param mode     Mode
     * @param movePath PhotoEditMovePath
     */
    private void drawPath(Mode mode, PhotoEditMovePath movePath) {
        if (mode != null && movePath != null) {
            mCanvas.save();
            if (mode == Mode.PENCIL) {
                mPencilPaint.setColor(movePath.getPaintColor());
                mPencilPaint.setStyle(movePath.getPaintStyle());
                mPencilPaint.setStrokeWidth(movePath.getPaintWidth());
                mCanvas.clipRect(mRectSourceFDst);
                mCanvas.drawPath(movePath.getPath(), mPencilPaint);
            } else if (mode == Mode.MOSAIC) {
                int layerCount = mCanvas.saveLayer(mRectSourceFDst, null, Canvas.ALL_SAVE_FLAG);
                mPencilPaint.setColor(movePath.getPaintColor());
                mPencilPaint.setStyle(movePath.getPaintStyle());
                mPencilPaint.setStrokeWidth(movePath.getPaintWidth());
                mCanvas.clipRect(mRectSourceFDst);
                mCanvas.drawPath(movePath.getPath(), mPencilPaint);
                mDrawMosaicPaint.setStrokeWidth(movePath.getPaintWidth());
                if (mMosaicBitmap != null)
                    mCanvas.drawBitmap(mMosaicBitmap, null, mRectSourceFDst, mDrawMosaicPaint);
                mCanvas.restoreToCount(layerCount);
            }
            mCanvas.restore();
        }
    }

    /**
     * 重绘view
     */
    private void invalidate() {
        if (mPhotoEditImageView != null) mPhotoEditImageView.invalidate();
    }

    /**
     * 设置编辑按钮是否隐藏
     *
     * @param isVisible true 显示  false 隐藏
     */
    private void onPath(boolean isVisible) {
        if (mPhotoEditImageView != null)
            mPhotoEditImageView.onPath(isVisible);
    }

    /**
     * 初始化涂鸦画笔
     */
    private void initPencilPaint() {
        mPencilPaint = new Paint();
        mPencilPaint.setAntiAlias(true);
        mPencilPaint.setDither(true);
        mPencilPaint.setStrokeCap(Paint.Cap.ROUND);
        mPencilPaint.setStrokeJoin(Paint.Join.ROUND);
        mPencilPaint.setPathEffect(new CornerPathEffect(mPaintPencilWidth));
    }

    /**
     * 初始化马赛克画笔
     */
    private void initMosaicPaint() {
        mMosaicPaint = new Paint();
        mMosaicPaint.setAntiAlias(true);
        mMosaicPaint.setDither(true);
        mMosaicPaint.setFilterBitmap(false);
        mMosaicPaint.setStrokeWidth(mPaintMosaicWidth);
        mMosaicPaint.setStrokeCap(Paint.Cap.ROUND);
        mMosaicPaint.setStrokeJoin(Paint.Join.ROUND);
        mMosaicPaint.setStyle(Paint.Style.STROKE);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.picture_edit_bottom_mosaic_grid);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mMosaicPaint.setShader(bitmapShader);
    }

    /**
     * 初始化马赛克画笔
     */
    private void initDrawMosaicPaint() {
        mDrawMosaicPaint = new Paint();
        mDrawMosaicPaint.setAntiAlias(true);
        mDrawMosaicPaint.setDither(true);
        mDrawMosaicPaint.setFilterBitmap(false);
        mDrawMosaicPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mDrawMosaicPaint.setStrokeWidth(mPaintMosaicWidth);
        mDrawMosaicPaint.setStrokeCap(Paint.Cap.SQUARE);
        mDrawMosaicPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 获取坐标 RectF Left
     *
     * @param width bitmap的宽
     * @return float
     */
    private float getLeft(float width) {
        return (getScreenWidth() - width) / 2;
    }

    /**
     * 获取坐标 RectF Top
     *
     * @param height bitmap的高
     * @return float
     */
    private float getTop(float height) {
        return (getScreenHeight() - height) / 2;
    }

    /**
     * 获取坐标 RectF Right
     *
     * @param width bitmap的宽
     * @return float
     */
    private float getRight(float width) {
        return getLeft(width) + width;
    }

    /**
     * 获取坐标 RectF Bottom
     *
     * @param height bitmap的高
     * @return float
     */
    private float getBottom(float height) {
        return getTop(height) + height;
    }

    /**
     * 获取涂鸦集合
     *
     * @return ArrayList<PhotoEditMovePath>
     */
    public ArrayList<PhotoEditMovePath> getPencilList() {
        return mPencilPathList;
    }

    /**
     * 获取马赛克集合
     *
     * @return ArrayList<PhotoEditMovePath>
     */
    public ArrayList<PhotoEditMovePath> getMosaicList() {
        return mMosaicPathList;
    }

    /**
     * 获取图片
     *
     * @return Bitmap
     */
    public Bitmap getPhotoEditBitmap() {
        return mBitmap;
    }

    public Canvas getmCanvas() {
        return mCanvas;
    }


    /**
     * 获取屏幕宽度
     *
     * @return float
     */
    private int getScreenWidth() {
        return UIUtils.getScreenWidth(mContext);
    }

    /**
     * 获取屏幕高度
     *
     * @return float
     */
    private int getScreenHeight() {
        return UIUtils.getScreenHeight(mContext);
    }

    /**
     * 设置手势缩放系数
     *
     * @param scale  float
     * @param focusX float
     * @param focusY float
     */
    public void setGestureScale(float scale, float focusX, float focusY) {
        if (scale >= SCALE_MIN && getMatrixScaleX() >= SCALE_MAX) return;
        mMatrix.postScale(scale, scale, focusX, focusY);
        setRectFDst();
    }

    /**
     * 设置绘制区域RectF
     */
    private void setRectFDst() {
        int width = mResourceBitmap.getWidth();
        int height = mResourceBitmap.getHeight();
        float scale2 = getMatrixScaleX();
        float scaleWidth = scale2 * width;
        float scaleHeight = scale2 * height;
        mRectFDst = new RectF(getLeft(scaleWidth), getTop(scaleHeight), getRight(scaleWidth), getBottom(scaleHeight));
    }

    /**
     * 返回双击缩放系数 放大事件
     *
     * @return float
     */
    public float getDoubleScale() {
        float scale = getMatrixScaleX();
        scale += SCALE_ADD;
        if (scale >= SCALE_MAX) {
            scale = SCALE_MIN;
        }
        return scale;
    }

    /**
     * 获取矩阵的缩放比
     *
     * @return float
     */
    public float getMatrixScaleX() {
        mMatrix.getValues(MATRIX_FLOAT);
        return MATRIX_FLOAT[Matrix.MSCALE_X];
    }

    /**
     * 设置矩阵的缩放比
     *
     * @param scale 缩放比
     */
    public void setMatrixScaleX(float scale, float focusX, float focusY) {
//        MATRIX_FLOAT[Matrix.MSCALE_X] = scale;
//        MATRIX_FLOAT[Matrix.MSCALE_Y] = scale;
//        mMatrix.setValues(MATRIX_FLOAT);
        float matrixScaleX = getMatrixScaleX();
        setGestureScale(scale / matrixScaleX, focusX, focusY);
    }

    /**
     * 获取矩阵的缩放比
     *
     * @return float
     */
    private float getMatrixTranX() {
        mMatrix.getValues(MATRIX_FLOAT);
        return MATRIX_FLOAT[Matrix.MTRANS_X];
    }

    /**
     * 获取矩阵的缩放比
     *
     * @return float
     */
    private float getMatrixTranY() {
        mMatrix.getValues(MATRIX_FLOAT);
        return MATRIX_FLOAT[Matrix.MTRANS_Y];
    }

    /**
     * 设置模式
     *
     * @param mode Mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * 获取模式
     *
     * @return Mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * 获取路径
     *
     * @return PhotoEditPath
     */
    public PhotoEditPath getPath() {
        return mPath;
    }

    private RectF getmRectF() {
        return mRectFDst;
    }

    /**
     * 设置是否在缩放中
     *
     * @param isScale true 缩放  false 不缩放
     */
    public void setScale(boolean isScale) {
        this.isScale = isScale;
    }

    /**
     * 是否缩放
     *
     * @return true 缩放  false 不缩放
     */
    public boolean isScale() {
        return isScale;
    }

    /**
     * 获取马赛克画笔
     *
     * @return Paint
     */
    public Paint getMosaicPaint() {
        return mDrawMosaicPaint;
    }

    /**
     * @return Paint
     */
    public Matrix getmMatrix() {
        return mMatrix;
    }

    public void onDestroyView() {
        onDestroyBitmap(mBitmap);
        onDestroyBitmap(mMosaicBitmap);
        onDestroyBitmap(mResourceBitmap);
        onDestroyPaint(mPencilPaint);
        onDestroyPaint(mMosaicPaint);
        onDestroyPaint(mDrawMosaicPaint);
        onDestroyList(mPencilPathList);
        onDestroyList(mMosaicPathList);
        onDestroyRectF(mRectFDst);
        onDestroyRectF(mRectSourceFDst);
        if (mMatrix != null) mMatrix = null;
        if (mPath != null) {
            mPath.reset();
            mPath = null;
        }
        if (mCanvas != null) {
            mCanvas = null;
        }
        if (MATRIX_FLOAT != null) {
            MATRIX_FLOAT = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }

    private void onDestroyRectF(RectF rectF) {
        if (rectF != null) {
            rectF = null;
        }
    }

    private void onDestroyList(ArrayList<PhotoEditMovePath> list) {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    private void onDestroyPaint(Paint paint) {
        if (paint != null) {
            paint.reset();
            paint = null;
        }
    }

    private void onDestroyBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
