/*
 * Copyright (C) 2014 The Android Open Source Project
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

package android.support.test.jank;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.FrameStats;

import java.util.HashMap;
import java.util.Map;

/** The {@link JankUtil} class provides functionality for monitoring jank. */
public class JankUtil {

    // Singleton instance
    private static JankUtil sInstance;

    private UiAutomation mUiAutomation;
    private JankMonitor mMonitor;

    /** Private constructor. Clients should use {@link JankUtil#getInstance(Instrumentation)}. */
    private JankUtil(UiAutomation automation) {
        mUiAutomation = automation;

        // Subscribe to window information
        AccessibilityServiceInfo info = mUiAutomation.getServiceInfo();
        info.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        mUiAutomation.setServiceInfo(info);
    }

    /** Returns a {@link JankUtil} instance. */
    public static JankUtil getInstance(Instrumentation instrumentation) {
        if (sInstance == null) {
            sInstance = new JankUtil(instrumentation.getUiAutomation());
        }
        return sInstance;
    }

    /** Starts monitoring for janky frames of the given {@code type}. */
    public void startMonitor(JankType type) {
        if (mMonitor != null) {
            throw new IllegalStateException("Monitor already started");
        }
        if (type == JankType.CONTENT_FRAMES) {
            mMonitor = new WindowContentJankMonitor(getCurrentWindow());
        } else if (type == JankType.ANIMATION_FRAMES) {
            mMonitor = new WindowAnimationJankMonitor();
        } else {
            throw new RuntimeException("Invalid type");
        }

        mMonitor.clear();
    }

    /** Stops monitoring and returns the {@link JankResult} for this monitoring session. */
    public JankResult stopMonitor() {
        FrameStats stats = mMonitor.getStats();
        mMonitor = null;
        return JankResult.analyze(stats);
    }

    /** Returns the id of the current application window. */
    private int getCurrentWindow() {
        for (AccessibilityWindowInfo window : mUiAutomation.getWindows()) {
            if (window.getType() == AccessibilityWindowInfo.TYPE_APPLICATION) {
                return window.getId();
            }
        }
        throw new RuntimeException("No application window found");
    }

    /** Generic monitoring interface */
    private static interface JankMonitor {
        public void clear();
        public FrameStats getStats();
    }

    /** Monitor for detecting window content frame jank. */
    private class WindowContentJankMonitor implements JankMonitor {
        private int mWindowId;

        public WindowContentJankMonitor(int windowId) {
            mWindowId = windowId;
        }

        @Override
        public void clear() {
            mUiAutomation.clearWindowContentFrameStats(mWindowId);
        }

        @Override
        public FrameStats getStats() {
            return mUiAutomation.getWindowContentFrameStats(mWindowId);
        }
    }

    /** Monitor for detecting window animation frame jank. */
    private class WindowAnimationJankMonitor implements JankMonitor {
        @Override
        public void clear() {
            mUiAutomation.clearWindowAnimationFrameStats();
        }

        @Override
        public FrameStats getStats() {
            return mUiAutomation.getWindowAnimationFrameStats();
        }
    }
}
