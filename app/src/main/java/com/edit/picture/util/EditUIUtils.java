package com.edit.picture.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edit.picture.fragment.PhotoEditFragment;
import com.edit.picture.view.PhotoEditDialogTextUtils;
import com.edit.picture.view.PhotoEditStickerView;
import com.selector.picture.R;
import com.selector.picture.model.ColorModel;
import com.selector.picture.utils.Function;
import com.selector.picture.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 编辑图片工具类
 * Created by taxiao on 2020/2/6
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 * 微信公众号:他晓
 */
public class EditUIUtils {

    private static EditUIUtils mEditUIUtils;

    private EditUIUtils() {
    }

    public static EditUIUtils getInstances() {
        if (mEditUIUtils == null) {
            synchronized (EditUIUtils.class) {
                if (mEditUIUtils == null) {
                    mEditUIUtils = new EditUIUtils();
                }
            }
        }
        return mEditUIUtils;
    }

    /**
     * 添加字帖View
     *
     * @param context
     * @param dialogList
     * @param viewGroup
     * @param colorModel
     */
    public void addEditDialogView(final Context context, final HashMap<ColorModel, PhotoEditStickerView> dialogList, final ViewGroup viewGroup, final ColorModel colorModel, final Function<ColorModel> function) {
        if (context != null && dialogList != null && colorModel != null && viewGroup != null) {
            final TextView textView = new TextView(context);
            boolean selected = colorModel.isSelected();
            setText(context, colorModel, textView, selected);
            textView.setText(colorModel.getText());
            textView.setGravity(Gravity.START);
            textView.setTextSize(15);
            final PhotoEditStickerView stickerView = new PhotoEditStickerView(context);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            stickerView.setInterface(new IViewInterface() {
                @Override
                public void deleteView() {
                    viewGroup.removeView(stickerView);
                    dialogList.remove(colorModel);
                }

                @Override
                public void onClickView(ColorModel colorModel) {
                    new PhotoEditDialogTextUtils(context, colorModel, function);
                }

                @Override
                public ColorModel getColorModel() {
                    return colorModel;
                }

                @Override
                public View addView() {
                    return textView;
                }
            });
            viewGroup.addView(stickerView, layoutParams);
            viewGroup.updateViewLayout(stickerView, layoutParams);
            dialogList.put(colorModel, stickerView);
        }
    }

    /**
     * 更新字帖View
     *
     * @param context
     * @param dialogList
     * @param viewGroup
     * @param colorModel
     */
    public void updateEditDialogView(final Context context, final HashMap<ColorModel, PhotoEditStickerView> dialogList, final ViewGroup viewGroup, final ColorModel colorModel) {
        if (context != null && dialogList != null && colorModel != null && viewGroup != null) {
            PhotoEditStickerView currentView = dialogList.get(colorModel);
            if (currentView != null) {
                TextView textView = currentView.getContentView();
                boolean selected = colorModel.isSelected();
                setText(context, colorModel, textView, selected);
                textView.setText(colorModel.getText());
                currentView.setColorModel(colorModel);
                viewGroup.updateViewLayout(currentView, currentView.getLayoutParams());
            }
        }
    }

    private void setText(Context context, ColorModel colorModel, TextView textView, boolean selected) {
        GradientDrawable background = (GradientDrawable) context.getResources().getDrawable(R.drawable.picture_edit_shape);
        if (selected) {
            textView.setTextColor(UIUtils.setTextColor(context, colorModel.getFrontColor()));
            background.setColor(colorModel.getFrontColor());
        } else {
            textView.setTextColor(colorModel.getFrontColor());
            background.setColor(context.getResources().getColor(android.R.color.transparent));
        }
        textView.setBackground(background);
    }
}
