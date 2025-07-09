/*
 * Copyright (C) 2025 STMicroelectronics
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

import android.util.Log;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CoproSerialPort {
    private static final String LOG_TAG = CoproSerialPort.class.getName();
    private final ICoproSerialPort mService;

    public CoproSerialPort(@NonNull ICoproSerialPort service) {
        mService = service;
    }

    public void open(int mode) {
        try {
            if (!mService.open(mode)) {
                Log.e(LOG_TAG, "Failed to open Copro serial port");
                RemoteException ex = new RemoteException("Failed to open Copro serial port");
                throw ex.rethrowAsRuntimeException();
            }
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Failed to open Copro serial port", ex);
            throw ex.rethrowAsRuntimeException();
        }
    }

    public void close() {
        try {
            mService.close();
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Unable to contact the remote Copro Service (close)", ex);
        }
    }

    public @Nullable String read() {
        try {
            return mService.read();
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Unable to contact the remote Copro Service (read)", ex);
            return "";
        }
    }

    public @Nullable byte[] readB(int size) {
        try {
            return mService.readB(size);
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Unable to contact the remote Copro Service (readB)", ex);
            return new byte[0];
        }
    }

    public void write(@NonNull String command) {
        try {
            mService.write(command);
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Unable to contact the remote Copro Service (write)", ex);
        }
    }

    public int writeB(@NonNull byte[] command) {
        try {
            return mService.writeB(command);
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Unable to contact the remote Copro Service (writeB)", ex);
            return -1;
        }
    }
}
