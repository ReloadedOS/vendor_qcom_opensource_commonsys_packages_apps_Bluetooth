/*
 * Copyright (C) 2012-2014 The Android Open Source Project
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

package com.android.bluetooth.btservice;

import android.bluetooth.OobData;

final class JniCallbacks {

    private RemoteDevices mRemoteDevices;
    private AdapterProperties mAdapterProperties;
    private AdapterService mAdapterService;
    private BondStateMachine mBondStateMachine;

    JniCallbacks(AdapterService adapterService, AdapterProperties adapterProperties) {
        mAdapterService = adapterService;
        mAdapterProperties = adapterProperties;
    }

    void init(BondStateMachine bondStateMachine, RemoteDevices remoteDevices) {
        mRemoteDevices = remoteDevices;
        mBondStateMachine = bondStateMachine;
    }

    void cleanup() {
        mRemoteDevices = null;
        mAdapterProperties = null;
        mAdapterService = null;
        mBondStateMachine = null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    void sspRequestCallback(byte[] address, byte[] name, int cod, int pairingVariant, int passkey) {
        mBondStateMachine.sspRequestCallback(address, name, cod, pairingVariant, passkey);
    }

    void devicePropertyChangedCallback(byte[] address, int[] types, byte[][] val) {
        mRemoteDevices.devicePropertyChangedCallback(address, types, val);
    }

    void deviceFoundCallback(byte[] address) {
        mRemoteDevices.deviceFoundCallback(address);
    }

    void pinRequestCallback(byte[] address, byte[] name, int cod, boolean min16Digits) {
        mBondStateMachine.pinRequestCallback(address, name, cod, min16Digits);
    }

    void bondStateChangeCallback(int status, byte[] address, int newState, int hciReason) {
        mBondStateMachine.bondStateChangeCallback(status, address, newState, hciReason);
    }

    void aclStateChangeCallback(int status, byte[] address, int newState,
            int transportLinkType, int hciReason) {
        mRemoteDevices.aclStateChangeCallback(status, address, newState,
                transportLinkType, hciReason);
    }

    void stateChangeCallback(int status) {
        mAdapterService.stateChangeCallback(status);
    }

    void discoveryStateChangeCallback(int state) {
        mAdapterProperties.discoveryStateChangeCallback(state);
    }

    void adapterPropertyChangedCallback(int[] types, byte[][] val) {
        mAdapterProperties.adapterPropertyChangedCallback(types, val);
    }

    void oobDataReceivedCallback(int transport, OobData oobData) {
        mAdapterService.notifyOobDataCallback(transport, oobData);
    }
}
