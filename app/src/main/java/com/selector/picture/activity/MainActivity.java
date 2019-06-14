package com.selector.picture.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.selector.picture.R;
import com.selector.picture.base.BaseActivity;
import com.selector.picture.model.MimeType;
import com.selector.picture.model.PicSelector;
import com.selector.picture.utils.UIUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivPicture;

    @Override
    protected void setThem() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        View tvSelectPic = findViewById(R.id.tv_select_pic);
        tvSelectPic.setOnClickListener(this);
        ivPicture = findViewById(R.id.iv_picture);
        View tv_zoom = findViewById(R.id.tv_zoom);
        View tv_dis_zoom = findViewById(R.id.tv_dis_zoom);
        tv_zoom.setOnClickListener(this);
        tv_dis_zoom.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_pic:
                picSelect();
                break;
            case R.id.tv_zoom:
                UIUtils.setAnimation(ivPicture, true);
                break;
            case R.id.tv_dis_zoom:
                UIUtils.setAnimation(ivPicture, false);
                break;
            default:
                break;
        }
    }


    private void picSelect() {
        PicSelector.create(MainActivity.this)
                .minSelectNum(1)
                .maxSelectNum(9)
                .gridSize(4)
                .choose(MimeType.TYPE_ALL)
                .theme(R.style.pictrue_white_Theme)
                .loadAnimation(true)
                .loadVoice(true)
                .optionOriginalImage(true)
                .editable(true)
//                .glideOverride(100,100)
//                .sizeMultiplier(0.5F)
                .setResult(10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    ArrayList<String> strings = PicSelector.obtainResultFile(data);
                    Log.e("--- ", "list == " + strings.toString());
                    ArrayList<Uri> uris = PicSelector.obtainResultUri(mActivity, data);
                    Log.e("--- ", "uris == " + uris.toString());
                    break;
            }
        }
    }
}
