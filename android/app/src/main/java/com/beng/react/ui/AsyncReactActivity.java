/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * <p>
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.beng.react.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.beng.react.modle.RnBundle;
import com.demo.R;

/**
 * 异步加载业务bundle的activity
 */
public class AsyncReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    public static final String PARAM_KEY_RN_BUNDLE = "key_rn_bundle";

    private RNBundleView rnBundleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_rn);

        rnBundleView = findViewById(R.id.rn_bundle_view);
        initRnBundle(getIntent());

    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (rnBundleView != null && rnBundleView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initRnBundle(intent);
    }

    private void initRnBundle(Intent intent) {
        if (rnBundleView != null && intent != null) {
            RnBundle rnBundle = intent.getParcelableExtra(PARAM_KEY_RN_BUNDLE);
            rnBundleView.setRnBundle(rnBundle);
        }
    }
}
