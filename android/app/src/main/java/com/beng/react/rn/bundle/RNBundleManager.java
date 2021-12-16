package com.beng.react.rn.bundle;

import androidx.annotation.NonNull;

import com.beng.react.modle.RnBundle;
import com.beng.react.ui.RNBundleView;
import com.demo.R;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RNBundleManager extends SimpleViewManager<RNBundleView> {
    @NonNull
    @Override
    public String getName() {
        return "BundleView";
    }

    @NonNull
    @Override
    protected RNBundleView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new RNBundleView(reactContext);
    }

    @ReactProp(name="setRnBundle")
    public void setRnBundle(RNBundleView view, ReadableMap readableMap){
        RnBundle bundle = new RnBundle();
        bundle.scriptType = RnBundle.ASSET;
        bundle.scriptPath = "index3.android.bundle";
        bundle.scriptUrl = "index3.android.bundle";
        bundle.mainComponentName = "reactnative_multibundler3";
        view.setRnBundle(bundle);
    }
}