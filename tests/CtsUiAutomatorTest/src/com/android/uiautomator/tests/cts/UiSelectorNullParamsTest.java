/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.android.uiautomator.tests.cts;

import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/*
 * Test that UiSelector methods will not accept null parameters.
 */
public class UiSelectorNullParamsTest extends UiAutomatorTestCase {

    public void testChildSelector() {
        try {
            UiSelector selector = new UiSelector().childSelector(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testClassNameString() {
        try {
            UiSelector selector = new UiSelector().className((String)null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testClassNameClass() {
        try {
            UiSelector selector = new UiSelector().className((Class)null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testClassNameMatches() {
        try {
            UiSelector selector = new UiSelector().classNameMatches(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testDescription() {
        try {
            UiSelector selector = new UiSelector().description(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testDescriptionContains() {
        try {
            UiSelector selector = new UiSelector().descriptionContains(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testDescriptionMatches() {
        try {
            UiSelector selector = new UiSelector().descriptionMatches(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testDescriptionStartsWith() {
        try {
            UiSelector selector = new UiSelector().descriptionStartsWith(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testFromParent() {
        try {
            UiSelector selector = new UiSelector().fromParent(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testPackageName() {
        try {
            UiSelector selector = new UiSelector().packageName(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testPackageNameMatches() {
        try {
            UiSelector selector = new UiSelector().packageNameMatches(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testResourceId() {
        try {
            UiSelector selector = new UiSelector().resourceId(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testResourceIdMatches() {
        try {
            UiSelector selector = new UiSelector().resourceIdMatches(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testText() {
        try {
            UiSelector selector = new UiSelector().text(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testTextContains() {
        try {
            UiSelector selector = new UiSelector().textContains(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }

    public void testTextMatches() {
        try {
            UiSelector selector = new UiSelector().textMatches(null);
            fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // This is expected
        }
    }
}
