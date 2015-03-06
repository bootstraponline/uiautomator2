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

import android.os.Bundle;
import android.util.Log;
import android.view.FrameStats;

import java.util.ArrayList;

/**
 * Abstract base class for {@link android.view.FrameStats} based {@link JankMonitor}s.
 *
 * Reports average and max jank, as well as average frames per second and the longest normalized
 * frame time.
 */
abstract class FrameStatsMonitorBase implements JankMonitor {

    private static final String TAG = "JankTestHelper";

    // Maximum normalized error in frame duration before the frame is considered janky
    private static final double MAX_ERROR = 0.5f;

    // Maximum normalized frame duration before the frame is considered a pause
    private static final double PAUSE_THRESHOLD = 15.0f;

    // Jank metrics namespace and helper class
    private static final String MONITOR_PREFIX = "frame";
    private static final MetricsHelper mMetricsHelper = new MetricsHelper(MONITOR_PREFIX);

    // Key values for the metrics reported by this monitor
    private static final String KEY_NUM_JANKY = "jank";
    private static final String KEY_FPS = "fps";
    private static final String KEY_LONGEST_FRAME = "max-frame-duration";

    // Accumulated stats
    ArrayList<Integer> mJankyFrames = new ArrayList<Integer>();
    ArrayList<Double> mFps = new ArrayList<Double>();
    ArrayList<Double> mLongestNormalizedFrames = new ArrayList<Double>();


    public Bundle getMetrics() {
        Bundle metrics = new Bundle();

        // Store average and max jank
        mMetricsHelper.putSummaryMetrics(metrics, KEY_NUM_JANKY, mJankyFrames);

        // Store average fps
        mMetricsHelper.putAverageMetric(metrics, KEY_FPS, mFps);

        // Store average max frame duration
        mMetricsHelper.putAverageMetric(metrics, KEY_LONGEST_FRAME, mLongestNormalizedFrames);

        return metrics;
    }

    protected void analyze(FrameStats stats) {
        int frameCount = stats.getFrameCount();
        long refreshPeriod = stats.getRefreshPeriodNano();

        int numJanky = 0;
        double longestFrameNormalized = 0.0f;
        double totalDuration = 0.0f;
        // Skip first frame
        for (int i = 2; i < frameCount; i++) {
            // Handle frames that have not been presented.
            if (stats.getFramePresentedTimeNano(i) == -1) {
                // The animation must not have completed. Warn and break out of the loop.
                Log.w(TAG, "Skipping fenced frame.");
                frameCount = i;
                break;
            }
            long frameDuration = stats.getFramePresentedTimeNano(i) -
                    stats.getFramePresentedTimeNano(i - 1);
            double normalized = (double)frameDuration / refreshPeriod;
            if (normalized < PAUSE_THRESHOLD) {
                if (normalized > 1.0f + MAX_ERROR) {
                    numJanky++;
                }
                longestFrameNormalized = Math.max(longestFrameNormalized, normalized);
            }
            totalDuration += frameDuration;
        }
        double fps = (double)(frameCount - 2) / totalDuration * 1000000000;

        // Store metrics from this run
        mJankyFrames.add(numJanky);
        mFps.add(fps);
        mLongestNormalizedFrames.add(longestFrameNormalized);
    }
}
