package com.demo.demo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beng.react.ui.AsyncReactActivity;
import com.beng.react.ui.RNBundleView;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.demo.R;
import com.beng.react.modle.RnBundle;

public class MainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act_loadbundle);
        ReactInstanceManager reactInstanceManager = ((ReactApplication) getApplication()).getReactNativeHost().getReactInstanceManager();
        if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
            reactInstanceManager.createReactContextInBackground();//这里会先加载基础包platform.android.bundle，也可以不加载
        }
        //事先加载基础包可以减少后面页面加载的时间，但相应的会增加内存使用
        // 当然也可以不用事先加载基础包，AsyncReactActivity中已经包含了这个逻辑，如果判断出没加载基础包会先加载基础包再加载业务包
        //请根据自己的需求使用
        findViewById(R.id.btn_go_buz1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击进入rn业务1

                RnBundle rnbundle = new RnBundle();
                rnbundle.scriptType = RnBundle.ASSET;
                rnbundle.scriptPath = "biz1.android.bundle";
                rnbundle.scriptUrl = "biz1.android.bundle";
                rnbundle.mainComponentName = "reactnative_multibundler";

                Intent intent = new Intent(MainActivity.this, AsyncReactActivity.class);
                intent.putExtra(AsyncReactActivity.PARAM_KEY_RN_BUNDLE, (Parcelable) rnbundle);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_go_buz2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击进入rn业务2
                RnBundle rnbundle = new RnBundle();
                rnbundle.scriptType = RnBundle.NETWORK;
                rnbundle.scriptPath = "biz2.android.bundle";
                rnbundle.scriptUrl = "https://github.com/smallnew/react-native-multibundler/raw/master/remotebundles/biz2.android.bundle.zip";
                rnbundle.scriptUrl = "http://10.16.1.1:8080/static/biz2.android.bundle.zip";
                rnbundle.mainComponentName = "reactnative_multibundler2";
                Intent intent = new Intent(MainActivity.this, AsyncReactActivity.class);
                intent.putExtra(AsyncReactActivity.PARAM_KEY_RN_BUNDLE, (Parcelable) rnbundle);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_go_buz3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击进入rn业务3

                RnBundle rnbundle = new RnBundle();
                rnbundle.scriptType = RnBundle.ASSET;
                rnbundle.scriptPath = "biz3.android.bundle";
                rnbundle.scriptUrl = "biz3.android.bundle";
                rnbundle.mainComponentName = "reactnative_multibundler3";

                Intent intent = new Intent(MainActivity.this, AsyncReactActivity.class);
                intent.putExtra(AsyncReactActivity.PARAM_KEY_RN_BUNDLE, (Parcelable) rnbundle);
                startActivity(intent);
            }
        });


//        RnBundle bundle = new RnBundle();
//        bundle.scriptType = RnBundle.ASSET;
//        bundle.scriptPath = "biz1.android.bundle";
//        bundle.scriptUrl = "biz1.android.bundle";
//        bundle.mainComponentName = "reactnative_multibundler";
//        ((RNBundleView)findViewById(R.id.testFrame)).setRnBundle(bundle);

//        RnBundle rnbundle = new RnBundle();
//        rnbundle.scriptType = RnBundle.ASSET;
//        rnbundle.scriptPath = "biz1.android.bundle";
//        rnbundle.scriptUrl = "biz1.android.bundle";
//        rnbundle.mainComponentName = "reactnative_multibundler";
//
//        Intent intent = new Intent(MainActivity.this, AsyncReactActivity.class);
//        intent.putExtra(AsyncReactActivity.PARAM_KEY_RN_BUNDLE, (Parcelable) rnbundle);
//        startActivity(intent);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}
