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
import android.os.Bundle;
import android.support.test.jank.WindowAnimationFrameStatsMonitor;
import android.view.FrameStats;

import java.util.Collections;

/**
 * Monitors {@link android.view.WindowAnimationFrameStats} to detect janky frames.
 *
 * Reports average and max jank, as well as average frames per second and max frame times.
 */
class WindowAnimationFrameStatsMonitorImpl extends FrameStatsMonitorBase {

    private UiAutomation mUiAutomation;

    public WindowAnimationFrameStatsMonitorImpl(UiAutomation automation) {
        mUiAutomation = automation;
    }

    public Bundle getMetrics() {
        Bundle metrics = new Bundle();

        // Store average and max jank
        metrics.putDouble(WindowAnimationFrameStatsMonitor.KEY_AVG_NUM_JANKY,
                MetricsHelper.computeAverageInt(mJankyFrames));
        metrics.putInt(WindowAnimationFrameStatsMonitor.KEY_MAX_NUM_JANKY,
                Collections.max(mJankyFrames));

        // Store average fps
        metrics.putDouble(WindowAnimationFrameStatsMonitor.KEY_AVG_FPS,
                MetricsHelper.computeAverageFloat(mFps));

        // Store average max frame duration
        metrics.putDouble(WindowAnimationFrameStatsMonitor.KEY_AVG_LONGEST_FRAME,
                MetricsHelper.computeAverageFloat(mLongestNormalizedFrames));

        return metrics;
    }

    @Override
    public void startIteration() {
        // Clear out any previous data
        mUiAutomation.clearWindowAnimationFrameStats();
    }

    @Override
    public int stopIteration() {
        FrameStats stats = mUiAutomation.getWindowAnimationFrameStats();
        analyze(stats);
        return stats.getFrameCount();
    }
}
