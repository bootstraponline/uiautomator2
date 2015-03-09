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

package android.support.test.jank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation used to configure a gfx monitor. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GfxMonitor {
    /** The name of the process to monitor */
    String processName();

    public static final String KEY_AVG_NUM_JANKY = "gfx-avg-jank";
    public static final String KEY_MAX_NUM_JANKY = "gfx-max-jank";
    public static final String KEY_AVG_MISSED_VSYNC = "gfx-avg-missed-vsync";
    public static final String KEY_MAX_MISSED_VSYNC = "gfx-max-missed-vsync";
    public static final String KEY_AVG_HIGH_INPUT_LATENCY = "gfx-avg-high-input-latency";
    public static final String KEY_MAX_HIGH_INPUT_LATENCY = "gfx-max-high-input-latency";
    public static final String KEY_AVG_SLOW_UI_THREAD = "gfx-avg-slow-ui-thread";
    public static final String KEY_MAX_SLOW_UI_THREAD = "gfx-max-slow-ui-thread";
    public static final String KEY_AVG_SLOW_BITMAP_UPLOADS = "gfx-avg-slow-bitmap-uploads";
    public static final String KEY_MAX_SLOW_BITMAP_UPLOADS = "gfx-max-slow-bitmap-uploads";
    public static final String KEY_AVG_SLOW_DRAW = "gfx-avg-slow-draw";
    public static final String KEY_MAX_SLOW_DRAW = "gfx-max-slow-draw";
    public static final String KEY_AVG_FRAME_TIME_90TH_PERCENTILE = "gfx-avg-frame-time-90";
    public static final String KEY_MAX_FRAME_TIME_90TH_PERCENTILE = "gfx-max-frame-time-90";
    public static final String KEY_AVG_FRAME_TIME_95TH_PERCENTILE = "gfx-avg-frame-time-95";
    public static final String KEY_MAX_FRAME_TIME_95TH_PERCENTILE = "gfx-max-frame-time-95";
    public static final String KEY_AVG_FRAME_TIME_99TH_PERCENTILE = "gfx-avg-frame-time-99";
    public static final String KEY_MAX_FRAME_TIME_99TH_PERCENTILE = "gfx-max-frame-time-99";
}
