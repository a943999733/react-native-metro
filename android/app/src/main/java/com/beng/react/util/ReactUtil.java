package com.beng.react.util;



import androidx.annotation.Nullable;

import com.facebook.react.bridge.CatalystInstance;

public class ReactUtil {

    @Nullable
    public static String getSourceUrl(CatalystInstance instance) {
        return instance.getSourceURL();
    }

}
