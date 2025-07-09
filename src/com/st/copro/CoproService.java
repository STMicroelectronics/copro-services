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

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.hardware.copro.ICopro;

public class CoproService extends Service {
    private static final String LOG_TAG = CoproService.class.getSimpleName();
    private ICoproService.Stub mBinder;

    @Override
    public IBinder onBind(final Intent intent) {
        if (mBinder == null) {
            mBinder = new ICoproServiceImpl();
            Log.d(LOG_TAG, "Copro service instance created");
        }
        return mBinder;
    }

    private class ICoproServiceImpl extends ICoproService.Stub {

        private ICopro mCoproHal = null;

        public ICoproServiceImpl() {
            IBinder binder = ServiceManager.getService("android.hardware.copro.ICopro/default");
            if (binder == null) {
                Log.e(LOG_TAG, "Failed to get Copro HAL from ServiceManager");
                return;
            } else {
                mCoproHal = ICopro.Stub.asInterface(binder);
                if (mCoproHal == null) {
                    Log.e(LOG_TAG, "Failed to get Copro HAL service");
                    return;
                }
                Log.d(LOG_TAG, "Copro HAL binded");
            }
        }

        @Override
        public FirmwareInfo[] getFirmwareList() throws RemoteException {
            ArrayList<FirmwareInfo> fwInfoList = new ArrayList<FirmwareInfo>();
            if (mCoproHal != null) {
                android.hardware.copro.FirmwareInfo[] HalFwInfoList = mCoproHal.getFirmwareList();
                for (android.hardware.copro.FirmwareInfo halFwInfo : HalFwInfoList) {
                     fwInfoList.add(new FirmwareInfo(halFwInfo.id, halFwInfo.name, halFwInfo.state));
                }
            }
            return fwInfoList.toArray(new FirmwareInfo[fwInfoList.size()]);
        }

        @Override
        public FirmwareInfo getFirmwareByName(String name) throws RemoteException {
            if (mCoproHal != null) {
                android.hardware.copro.FirmwareInfo halFwInfo = mCoproHal.getFirmwareByName(name);
                return new FirmwareInfo(halFwInfo.id, halFwInfo.name, halFwInfo.state);
            }
            return null;
        }

        @Override
        public boolean isFirmwareRunning(int id) throws RemoteException {
            if (mCoproHal != null) {
                return mCoproHal.isFirmwareRunning(id);
            }
            return false;
        }

        @Override
        public void startFirmware(int id) throws RemoteException {
            if (mCoproHal != null) {
                mCoproHal.startFirmware(id);
            }
        }

        @Override
        public void stopFirmware() throws RemoteException {
            if (mCoproHal != null) {
                mCoproHal.stopFirmware();
            }
        }

        @Override
        public ICoproSerialPort getSerialPort() {
            if (mCoproHal != null) {
                try {
                     android.hardware.copro.ICoproSerialPort serialPort = mCoproHal.getSerialPort();
                     return new ICoproSerialPortImpl(serialPort);
                } catch (RemoteException e) {
                     Log.e(LOG_TAG, "Can't get serial port service" + e);
                }
            }
            return null;
        }

    }

}
