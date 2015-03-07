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

import java.util.List;

class MetricsHelper {

    public static double computeAverageFloat(List<Double> values) {
        double sum = 0.0f;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    public static double computeAverageInt(List<Integer> values) {
        double sum = 0.0f;
        for (Integer value : values) {
            sum += value;
        }
        return sum / values.size();
    }
}
