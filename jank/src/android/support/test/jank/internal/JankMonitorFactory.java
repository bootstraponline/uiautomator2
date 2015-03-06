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

import android.app.UiAutomation;
import android.support.test.jank.WindowAnimationFrameStatsMonitor;
import android.support.test.jank.WindowContentFrameStatsMonitor;
import android.support.test.jank.GfxMonitor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JankMonitorFactory {

    private UiAutomation mUiAutomation;

    public JankMonitorFactory(UiAutomation automation) {
        mUiAutomation = automation;
    }

    public List<JankMonitor> getJankMonitors(Method testMethod) {
        List<JankMonitor> monitors = new ArrayList<JankMonitor>();
        if (testMethod.getAnnotation(GfxMonitor.class) != null) {
            String process = testMethod.getAnnotation(GfxMonitor.class).processName();
            monitors.add(new GfxMonitorImpl(mUiAutomation, process));
        }
        if (testMethod.getAnnotation(WindowContentFrameStatsMonitor.class) != null) {
            monitors.add(new WindowContentFrameStatsMonitorImpl(mUiAutomation));
        }
        if (testMethod.getAnnotation(WindowAnimationFrameStatsMonitor.class) != null) {
            monitors.add(new WindowAnimationFrameStatsMonitorImpl(mUiAutomation));
        }
        return monitors;
    }
}
