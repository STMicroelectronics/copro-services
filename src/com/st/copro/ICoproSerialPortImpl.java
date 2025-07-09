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

class ICoproSerialPortImpl extends ICoproSerialPort.Stub {
    private static final String LOG_TAG = "ICoproSerialPortImpl";
    private android.hardware.copro.ICoproSerialPort mCoproSerialPortHal = null;

    public ICoproSerialPortImpl(android.hardware.copro.ICoproSerialPort serialPort) {
        Log.d(LOG_TAG, "Building ICoproSerialPortImpl service");
        try {
            mCoproSerialPortHal = serialPort;
            if (mCoproSerialPortHal == null) {
                Log.e(LOG_TAG, "Failed to get HAL ICoproSerialPort service");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while getting HAL ICoproSerialPort service", e);
            mCoproSerialPortHal = null;
        }
    }

    @Override
    public boolean open(int mode) throws RemoteException {
        if (mCoproSerialPortHal != null) {
            return mCoproSerialPortHal.open(mode);
        }
        Log.e(LOG_TAG, "open() failed: HAL service not available");
        return false;
    }

    @Override
    public void close() throws RemoteException {
        if (mCoproSerialPortHal != null) {
            mCoproSerialPortHal.close();
        } else {
            Log.e(LOG_TAG, "close() failed: HAL service not available");
        }
    }

    @Override
    public String read() throws RemoteException {
        if (mCoproSerialPortHal != null) {
            return mCoproSerialPortHal.read();
        }
        Log.e(LOG_TAG, "read() failed: HAL service not available");
        return "";
    }

    @Override
    public byte[] readB(int size) throws RemoteException {
        if (mCoproSerialPortHal != null) {
            return mCoproSerialPortHal.readB(size);
        }
        Log.e(LOG_TAG, "readB() failed: HAL service not available");
        return new byte[0];
    }

    @Override
    public void write(String command) throws RemoteException {
        if (mCoproSerialPortHal != null) {
            mCoproSerialPortHal.write(command);
        } else {
            Log.e(LOG_TAG, "write() failed: HAL service not available");
        }
    }

    @Override
    public int writeB(byte[] command) throws RemoteException {
        if (mCoproSerialPortHal != null) {
            return mCoproSerialPortHal.writeB(command);
        }
        Log.e(LOG_TAG, "writeB() failed: HAL service not available");
        return -1;
    }
}
