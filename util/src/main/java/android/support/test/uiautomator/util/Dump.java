/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.support.test.uiautomator.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Environment;
import android.support.test.uiautomator.UiDevice;

import java.io.File;
import java.io.IOException;

/** Dumps the current UI hierarchy to an xml file. */
public class Dump extends Instrumentation {

    private boolean mCompressed = false;
    private File mOut;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        if (arguments.containsKey("compressed")) {
            String compressed = arguments.getString("compressed");
            if (!compressed.equalsIgnoreCase("true") && !compressed.equalsIgnoreCase("false)")) {
                throw new IllegalArgumentException("compressed must be either true or false");
            }
            mCompressed = Boolean.parseBoolean(compressed);
        }
        arguments.getString("compressed", "false");

        if (arguments.containsKey("out")) {
            String out = arguments.getString("out");
            mOut = new File(out);
            if (!mOut.isAbsolute())   {
                mOut = new File(Environment.getExternalStorageDirectory(), out);
            }
        } else {
            mOut = new File(Environment.getExternalStorageDirectory(), "window_dump.xml");
        }

        start();
    }

    @Override
    public void onStart() {
        super.onStart();

        UiDevice device = UiDevice.getInstance(this);
        device.setCompressedLayoutHeirarchy(mCompressed);

        Bundle status = new Bundle();
        try {
            // Dump the window hierarchy
            device.dumpWindowHierarchy(mOut);

            status.putString("Status", "Hierarchy dumped successfully");
        } catch (IOException e) {
            status.putString("Error", e.toString());
        }

        finish(Activity.RESULT_OK, status);
    }
}
