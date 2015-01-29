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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation used to configure a jank test method. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JankTest {

    /** The type of jank to measure */
    JankType type();

    /** The minimum number of frames expected */
    int expectedFrames();

    /**
     * Alternate method to execute before the test method
     * <p>
     * Note: the annotated method must have same signature as {@link JankTestBase#beforeTest()}
     */
    String beforeTest() default "beforeTest";

    /**
     * Alternate method to execute before each iteration
     * <p>
     * Note: the annotated method must have same signature as {@link JankTestBase#beforeLoop()}
     */
    String beforeLoop() default "beforeLoop";

    /**
     * Alternate method to execute after each iteration
     * <p>
     * Note: the annotated method must have same signature as {@link JankTestBase#afterLoop()}
     */
    String afterLoop() default "afterLoop";

    /**
     * Alternate method to execute after all iterations have completed.
     * <p>
     * <b>Important:</b> the annotated method must take a parameter of type {@link JankMetrics}.
     * See {@link JankTestBase#afterTest(JankMetrics)}
     * */
    String afterTest() default "afterTest";
}
