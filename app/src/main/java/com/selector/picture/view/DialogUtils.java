package com.selector.picture.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.selector.picture.R;
import com.selector.picture.model.LocalMediaFolder;
import com.selector.picture.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册选择弹框
 * Create by Han on 2019/5/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class DialogUtils {
    private Context mContext;
    private List<LocalMediaFolder> mList;
    private AlertDialog dialog;

    public DialogUtils(Context context, List<LocalMediaFolder> localMediaFolders) {
        this.mContext = context;
        this.mList = localMediaFolders;
        initDialog();
    }

    private void initDialog() {
        dialog = new AlertDialog
                .Builder(mContext)
                .setCancelable(true)
                .create();
        dialog.show();
        initView();
    }

    private void initView() {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_selector_pic, null, false);
                window.setContentView(view);
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.BottomDialogAnimation);
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.y = (int) mContext.getResources().getDimension(R.dimen.picture_selector_bottom_height);
                window.setAttributes(windowAttributes);
                window.setBackgroundDrawable(new ColorDrawable());
                RecyclerView ry = view.findViewById(R.id.ry);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ry.getLayoutParams();
                params.height = UIUtils.getScreenHeight(mContext) - (int) mContext.getResources().getDimension(R.dimen.picture_selector_top_height) - UIUtils.dp2px(mContext, 120);
                ry.setLayoutParams(params);
            }


        }
    }

}
