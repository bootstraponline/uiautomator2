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

import android.util.Log;
import android.view.FrameStats;

/** A {@link JankResult} contains the results of a jank monitoring session. This includes the number
 * of frames analyzed, the number of frames that were janky, the average frames per second, as well
 * as the nomalized longest frame time.*/
public class JankResult {

    private static final String TAG = JankResult.class.getSimpleName();

    // Maximum normalized error in frame duration before the frame is considered janky
    private static final double MAX_ERROR = 0.5f;
    // Maximum normalized frame duration before the frame is considered a pause
    private static final double PAUSE_THRESHOLD = 15.0f;

    public final int numFrames;
    public final int numJanky;
    public final double fps;
    public final double longestFrameNormalized;

    /** Private constructor. Clients should use {@link JankResult#analyze(FrameStats)} instead. */
    private JankResult(int numFrames, int numJanky, double fps, double longestFrameNormalized) {
        this.numFrames = numFrames;
        this.numJanky  = numJanky;
        this.fps       = fps;
        this.longestFrameNormalized = longestFrameNormalized;
    }

    /** Analyze the given {@link FrameStats} and return the resulting jank info. */
    public static JankResult analyze(FrameStats stats) {
        int frameCount = stats.getFrameCount();
        long refreshPeriod = stats.getRefreshPeriodNano();

        int numJanky = 0;
        double longestFrameNormalized = 0.0f;
        long totalDuration = 0;
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

        return new JankResult(frameCount, numJanky, fps, longestFrameNormalized);
    }
}
