package com.beng.react.modle;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.io.Serializable;

/***
 * 加载rn bundle的信息
 */
public class RnBundle implements Parcelable, Serializable {
    public String mainComponentName;
    public String scriptPath;
    @ScriptType
    public int scriptType;
    public String scriptUrl;

    public RnBundle() {
    }

    protected RnBundle(Parcel in) {
        mainComponentName = in.readString();
        scriptPath = in.readString();
        scriptUrl = in.readString();
        scriptType = in.readInt();
    }

    public static final Creator<RnBundle> CREATOR = new Creator<RnBundle>() {
        @Override
        public RnBundle createFromParcel(Parcel in) {
            return new RnBundle(in);
        }

        @Override
        public RnBundle[] newArray(int size) {
            return new RnBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainComponentName);
        dest.writeString(scriptPath);
        dest.writeString(scriptUrl);
        dest.writeInt(scriptType);

    }

    public static final int ASSET = 0;
    public static final int FILE = 1;
    public static final int NETWORK = 2;

    @IntDef(value = {ASSET, FILE, NETWORK})
    public @interface ScriptType {
    }

}
