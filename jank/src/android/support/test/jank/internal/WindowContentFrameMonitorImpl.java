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

package android.support.test.jank.internal;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.UiAutomation;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.FrameStats;

/**
 * Monitors {@link android.view.WindowContentFrameStats} to detect janky frames.
 *
 * Reports average and max jank, as well as average frames per second and max frame times.
 */
class WindowContentFrameStatsMonitorImpl extends FrameStatsMonitorBase {

    private static final String TAG = "JankTestHelper";

    private UiAutomation mUiAutomation;
    private int mWindowId = -1;

    public WindowContentFrameStatsMonitorImpl(UiAutomation automation) {
        mUiAutomation = automation;
    }

    @Override
    public void startIteration() {
        // Save the window id
        mWindowId = getCurrentWindow();

        // Clear out any previous data
        mUiAutomation.clearWindowContentFrameStats(mWindowId);
    }

    @Override
    public int stopIteration() {
        int currentWindow = getCurrentWindow();
        if (currentWindow != mWindowId) {
            Log.w(TAG, "Current window changed during the test. Did you mean to use "
                    + "WindowAnimationFrameStatsMonitor?");
        }
        FrameStats stats = mUiAutomation.getWindowContentFrameStats(currentWindow);
        analyze(stats);

        mWindowId = -1;
        return stats.getFrameCount();
    }

    /** Returns the id of the current window. */
    private int getCurrentWindow() {
        // Subscribe to window information
        AccessibilityServiceInfo info = mUiAutomation.getServiceInfo();
        info.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        mUiAutomation.setServiceInfo(info);

        AccessibilityNodeInfo activeWindowRoot = mUiAutomation.getRootInActiveWindow();

        for (AccessibilityWindowInfo window : mUiAutomation.getWindows()) {
            if (window.getRoot().equals(activeWindowRoot)) {
                return window.getId();
            }
        }
        throw new RuntimeException("Could not find active window");
    }
}
