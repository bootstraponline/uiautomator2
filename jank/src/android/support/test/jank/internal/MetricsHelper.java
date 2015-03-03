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
import android.text.TextUtils;

import java.util.List;

class MetricsHelper {

    private static final String KEY_SEPARATOR = "-";
    private static final String MAX_VALUE_PREFIX = "max";
    private static final String AVG_VALUE_PREFIX = "avg";

    private final String mMonitorPrefix;

    public MetricsHelper(String monitorPrefix) {
        mMonitorPrefix = monitorPrefix;
    }

    /** Stores the average metric for the given set of values. */
    public void putAverageMetric(Bundle metrics, String key, List<Double> values) {
        double sum = 0.0f;
        for (Double value : values) {
            sum += value;
        }
        metrics.putDouble(joinKey(mMonitorPrefix, MAX_VALUE_PREFIX, key), sum / values.size());
    }

    /** Stores the average and max metrics for the given set of values. */
    public void putSummaryMetrics(Bundle metrics, String key, List<Integer> values) {
        int max = -1;
        int sum = 0;
        for (Integer value : values) {
            max = Math.max(max, value);
            sum += value;
        }
        metrics.putInt(joinKey(mMonitorPrefix, MAX_VALUE_PREFIX, key), max);
        metrics.putDouble(joinKey(mMonitorPrefix, AVG_VALUE_PREFIX, key),
                (double)sum / values.size());
    }

    private String joinKey(String... parts) {
        return TextUtils.join(KEY_SEPARATOR, parts);
    }
}
