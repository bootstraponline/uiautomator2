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
import android.app.UiAutomation;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;

/** Outputs AccessibilityEvents as Instrumentation status updates. */
public class Events extends Instrumentation {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        // Default implementation doesn't actually call start(), so we need to call it ourselves.
        start();
    }

    @Override
    public void onStart() {
        super.onStart();

        getUiAutomation().setOnAccessibilityEventListener(
                new UiAutomation.OnAccessibilityEventListener() {

            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
                Bundle status = new Bundle();
                status.putString("Event", event.toString());
                sendStatus(Activity.RESULT_OK, status);
            }
        });
    }
}
