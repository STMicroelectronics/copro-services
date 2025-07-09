/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.st.copro;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class FirmwareInfo implements Parcelable {
    private final int id;
    private final String name;
    private final boolean state;

    /** @hide */
    public FirmwareInfo() {
        id = -1;
        name = "";
        state = false;
    }
    /** @hide */
    public FirmwareInfo(int _id, String _name, boolean _state) {
        id = _id;
        name = _name;
        state = _state;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FirmwareInfo) {
            FirmwareInfo fwinfo = (FirmwareInfo)obj;
            return id == fwinfo.id && name.equals(fwinfo.name) && state == fwinfo.state;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id ^ name.hashCode() ^ (state ? 1: 0);
    }

    @Override
    public String toString() {
        return "FirmwareInfo{id=" + id + ", name=" + name + ", state=" + state + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeBoolean(state);
    }

    public static final @NonNull Parcelable.Creator<FirmwareInfo> CREATOR
            = new Parcelable.Creator<FirmwareInfo>() {
        @Override
        public FirmwareInfo createFromParcel(Parcel in) {
            return new FirmwareInfo(in);
        }

        @Override
        public FirmwareInfo[] newArray(int size) {
            return new FirmwareInfo[size];
        }
    };

    private FirmwareInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        state = in.readBoolean();
    }

    public int getId() {
        return id;
    }

    public @NonNull String getName() {
        return name;
    }

    public boolean getState() {
        return state;
    }
}
