package com.selector.picture.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;

import com.selector.picture.R;
import com.selector.picture.adapter.AlbumAdapter;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.utils.OnItemClickListener;
import com.selector.picture.utils.UIUtils;

import java.util.List;

/**
 * 进度旋转框
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class DialogLoadingUtils {
    private Context mContext;
    private AlertDialog dialog;
    private Animation animation;

    public DialogLoadingUtils(Context context) {
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        dialog = new AlertDialog
                .Builder(mContext)
                .setCancelable(true)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dissMiss();
                    }
                }).create();
        initView();
    }

    private void initView() {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null, false);
                window.setContentView(view);
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.setBackgroundDrawable(new ColorDrawable());
                ImageView loading = view.findViewById(R.id.iv_loading);
                animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_dialog_loading);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                animation.setInterpolator(interpolator);
                loading.startAnimation(animation);
            }
        }
    }

    /**
     * 弹框消失
     */
    private void dissMiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (animation != null) {
            animation.cancel();
            animation = null;
        }
    }

    /**
     * 弹框显示
     */
    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

}
