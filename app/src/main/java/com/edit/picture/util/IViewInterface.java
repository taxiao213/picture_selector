package com.edit.picture.util;

import android.view.View;

import com.selector.picture.model.ColorModel;

/**
 * Created by A35 on 2019/11/22
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 */
public interface IViewInterface {
    View addView();

    void deleteView();

    void onClickView(ColorModel colorModel);

    ColorModel getColorModel();
}
