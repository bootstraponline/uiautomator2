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

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.jank.internal.JankMonitorFactory;
import android.support.test.jank.internal.JankMonitor;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Base test class for measuring Jank.
 *
 * This test class automatically monitors jank while executing each test method. Each test method is
 * executed several times in a loop, according to the 'iterations' command line parameter.
 *
 * To perform additional setup / tear down steps for each iteration, subclasses can optionally
 * override {@link JankTestBase#beforeLoop()} and {@link JankTestBase#afterLoop()} methods.
 *
 * Test methods must be configured with the {@link JankTest} annotation. At minimum, the type of
 * jank to measure and the number of expected frames must be specified.
 */
public class JankTestBase extends InstrumentationTestCase {

    private Bundle arguments = null;
    private int mCurrentIteration = 0;


    /** Called once before executing a test method. */
    public void beforeTest() throws Exception {
        // Default implementation. Do nothing.
    }

    /** Called before each iteration of the test method. */
    public void beforeLoop() throws Exception {
        // Default implementation. Do nothing.
    }

    /** Called after each iteration of the test method. */
    public void afterLoop() throws Exception {
        // Default implementation. Do nothing.
    }

    /**
     * Called once after all iterations have completed.
     * <p>Note: default implementation reports the aggregated jank metrics via
     * {@link Instrumentation#sendStatus(int, Bundle)}
     * @param metrics the aggregated jank metrics after looped execution
     */
    public void afterTest(Bundle metrics) {
        getInstrumentation().sendStatus(Activity.RESULT_OK, metrics);
    }

    /** Return the index of the currently executing iteration. */
    public final int getCurrentIteration() {
        return mCurrentIteration;
    }

    @Override
    protected final void runTest() throws Throwable {

        // Resolve test methods
        Method testMethod = resolveMethod(getName());
        JankTest annotation = testMethod.getAnnotation(JankTest.class);
        Method beforeTest = resolveMethod(annotation.beforeTest());
        Method beforeLoop = resolveMethod(annotation.beforeLoop());
        Method afterLoop  = resolveMethod(annotation.afterLoop());
        Method afterTest  = resolveAfterTest(annotation.afterTest());

        // Get the appropriate JankMonitors for the test type
        JankMonitorFactory factory = new JankMonitorFactory(getInstrumentation().getUiAutomation());
        List<JankMonitor> monitors = factory.getJankMonitors(testMethod);
        assertTrue("No monitors configured for this test", monitors.size() > 0);

        // Test setup
        beforeTest.invoke(this, (Object[])null);

        // Execute the test several times according to the "iteration" parameter
        int iterations = Integer.valueOf(getArguments().getString("iterations",
                Integer.toString(annotation.defaultIterationCount())));
        for (; mCurrentIteration < iterations; mCurrentIteration++) {
            // Loop setup
            beforeLoop.invoke(this, (Object[])null);

            // Start monitoring jank
            for (JankMonitor monitor : monitors) {
                monitor.startIteration();
            }

            // Run the test method
            testMethod.invoke(this, (Object[])null);

            // Stop monitoring
            for (JankMonitor monitor : monitors) {
                int numFrames = monitor.stopIteration();

                // Fail the test if we didn't get enough frames
                assertTrue(String.format("Too few frames received. Expected: %d, Received: %d.",
                        annotation.expectedFrames(), numFrames),
                        numFrames >= annotation.expectedFrames());
            }

            // Loop tear down
            afterLoop.invoke(this, (Object[])null);
        }

        // Report aggregated results
        Bundle metrics = new Bundle();
        for (JankMonitor monitor : monitors) {
            metrics.putAll(monitor.getMetrics());
        }
        afterTest.invoke(this, metrics);
    }


    /** Returns a {@link Method}} object representing the method with the given {@code name}. */
    private Method resolveMethod(String name) {
        assertNotNull(name);

        Method method = null;
        try {
            method = getClass().getMethod(name, (Class[]) null);
        } catch (NoSuchMethodException e) {
            fail(String.format("Method \"%s\" not found", name));
        }

        if (!Modifier.isPublic(method.getModifiers())) {
            fail(String.format("Method \"%s\" should be public", name));
        }

        return method;
    }

    /**
     * Returns a {@link Method}} object representing the method annotated with
     * {@link JankTest#afterTest()}.
     */
    private Method resolveAfterTest(String name) {
        assertNotNull(name);

        Method method = null;
        try {
            method = getClass().getMethod(name, Bundle.class);
        } catch (NoSuchMethodException e) {
            fail("method annotated with JankTest#afterTest has wrong signature");
        }

        if (!Modifier.isPublic(method.getModifiers())) {
            fail(String.format("Method \"%s\" should be public", name));
        }

        return method;
    }

    /** Returns a {@link Bundle} containing the command line parameters. */
    protected final Bundle getArguments() {
        if (arguments == null) {
            Instrumentation instrumentation = getInstrumentation();
            // Attempt to obtain the command line arguments bundle, but this is only supported by
            // InstrumentationTestRunner or AndroidJUnitRunner
            if (instrumentation instanceof InstrumentationTestRunner) {
                arguments = ((InstrumentationTestRunner) instrumentation).getArguments();
            } else if (instrumentation instanceof AndroidJUnitRunner) {
                arguments = InstrumentationRegistry.getArguments();
            } else {
                throw new RuntimeException("Unsupported test runner");
            }
        }
        return arguments;
    }
}
