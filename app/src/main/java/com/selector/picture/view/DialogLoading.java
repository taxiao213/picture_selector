package com.selector.picture.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.selector.picture.R;

/**
 * 进度旋转框
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class DialogLoading extends AlertDialog implements DialogInterface.OnDismissListener {
    private Animation animation;
    private Context context;

    public DialogLoading(Context context) {
        this(context, true, null);
    }

    private DialogLoading(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_loading);
        ImageView loading = findViewById(R.id.iv_loading);
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_dialog_loading);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        animation.setInterpolator(interpolator);
        loading.startAnimation(animation);
        setCancelable(false);
        Window window = getWindow();
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable());
        }
    }

    /**
     * 弹框消失
     */
    public void dissMiss() {
        dismiss();
        cancel();
        if (animation != null) {
            animation.cancel();
            animation = null;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dissMiss();
    }
}
