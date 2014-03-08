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

package android.support.test.uiautomator.tests.unit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.TimeoutException;

public class UiObject2Tests extends InstrumentationTestCase {

    private static final String TEST_APP = "android.support.test.uiautomator.tests.unit.testapp";

    private UiDevice mDevice;

    public void setUp() throws Exception {
        super.setUp();

        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();
    }

    private class LaunchActivityRunnable implements Runnable {

        private String mActivity;

        public LaunchActivityRunnable(String activity) {
            mActivity = activity;
        }

        @Override
        public void run() {
            Context context = getInstrumentation().getContext();
            Intent intent = new Intent()
                    .setClassName(TEST_APP, String.format("%s.%s", TEST_APP, mActivity))
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    public void launchTestActivity(String activity) throws TimeoutException {
        // Launch the test app
        mDevice.performActionAndWait(new LaunchActivityRunnable(activity), Until.newWindow(), 5000);
    }

    public void tearDown() throws Exception {
        mDevice.pressHome();

        // Wait for the activity to disappear
        mDevice.wait(Until.gone(By.pkg(TEST_APP)), 5000);

        super.tearDown();
    }

    public void testExists() {}

    public void testGetChildCount() {}

    public void testGetVisibleBounds() {}

    public void testGetClassNameButton() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "button"));
        assertEquals("android.widget.Button", object.getClassName());
    }

    public void testGetClassNameCheckBox() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "check_box"));
        assertEquals("android.widget.CheckBox", object.getClassName());
    }

    public void testGetClassNameEditText() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "edit_text"));
        assertEquals("android.widget.EditText", object.getClassName());
    }

    public void testGetClassNameProgressBar() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "progress_bar"));
        assertEquals("android.widget.ProgressBar", object.getClassName());
    }

    public void testGetClassNameRadioButton() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "radio_button"));
        assertEquals("android.widget.RadioButton", object.getClassName());
    }

    public void testGetClassNameRatingBar() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "rating_bar"));
        assertEquals("android.widget.RatingBar", object.getClassName());
    }

    public void testGetClassNameSeekBar() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "seek_bar"));
        assertEquals("android.widget.SeekBar", object.getClassName());
    }

    public void testGetClassNameSwitch() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "switch_toggle"));
        assertEquals("android.widget.Switch", object.getClassName());
    }

    /* TextClock (Bug: TextClock does not report its correct class name)
    public void testGetClassNameTextClock() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "text_clock"));
        assertEquals("android.widget.TextClock", object.getClassName());
    }
    */

    public void testGetClassNameTextView() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "text_view"));
        assertEquals("android.widget.TextView", object.getClassName());
    }

    public void testGetClassNameToggleButton() throws UiObjectNotFoundException, TimeoutException {
        launchTestActivity("UiObject2TestGetClassNameActivity");

        UiObject2 object = mDevice.findObject(By.res(TEST_APP, "toggle_button"));
        assertEquals("android.widget.ToggleButton", object.getClassName());
    }

    public void testGetContentDescription() {}

    public void testGetApplicationPackage() {}

    public void testGetResourceName() {}

    public void testGetText() {}

    public void testIsCheckable() {}

    public void testIsChecked() {}

    public void testIsClickable() {}

    public void testIsEnabled() {}

    public void testIsFocusable() {}

    public void testIsFocused() {}

    public void testIsLongClickable() {}

    public void testIsScrollable() {}

    public void testIsSelected() {}

    public void testClearTextField() {}

    public void testClickButton() throws TimeoutException {
        launchTestActivity("UiObject2TestClickActivity");

        // Find the button and verify its initial state
        UiObject2 button = mDevice.findObject(By.res(TEST_APP, "button"));
        assertEquals("Click Me!", button.getText());
        SystemClock.sleep(1000);

        // Click on the button and verify that the text has changed
        button.click();
        button.wait(Until.textEquals("I've been clicked!"), 10000);
        assertEquals("I've been clicked!", button.getText());
    }

    public void testClickCheckBox() throws TimeoutException {
        launchTestActivity("UiObject2TestClickActivity");

        // Find the checkbox and verify its initial state
        UiObject2 checkbox = mDevice.findObject(By.res(TEST_APP, "check_box"));
        assertEquals(false, checkbox.isChecked());

        // Click on the checkbox and verify that it is now checked
        checkbox.click();
        checkbox.wait(Until.checked(true), 10000);
        assertEquals(true, checkbox.isChecked());
    }

    public void testClickAndWaitForNewWindow() throws TimeoutException {
        launchTestActivity("UiObject2TestClickAndWaitActivity");

        // Click the button and wait for a new window
        UiObject2 button = mDevice.findObject(By.res(TEST_APP, "new_window_button"));
        button.clickAndWait(Until.newWindow(), 5000);
    }

    public void testLongClickButton() throws TimeoutException {
        launchTestActivity("UiObject2TestLongClickActivity");

        // Find the button and verify its initial state
        UiObject2 button = mDevice.findObject(By.res(TEST_APP, "button"));
        assertEquals("Long Click Me!", button.getText());

        // Click on the button and verify that the text has changed
        button.longClick();
        button.wait(Until.textEquals("I've been long clicked!"), 10000);
        assertEquals("I've been long clicked!", button.getText());
    }

    public void testPinchIn100Percent() throws TimeoutException {
        launchTestActivity("UiObject2TestPinchActivity");

        // Find the area to pinch
        UiObject2 pinchArea = mDevice.findObject(By.res(TEST_APP, "pinch_area"));
        UiObject2 scaleText = pinchArea.findObject(By.res(TEST_APP, "scale_factor"));
        pinchArea.pinchClose(1.0f, 100);
        scaleText.wait(Until.textNotEquals("1.0f"), 1000);
    }

    public void testPinchIn75Percent() throws TimeoutException {
        launchTestActivity("UiObject2TestPinchActivity");

        // Find the area to pinch
        UiObject2 pinchArea = mDevice.findObject(By.res(TEST_APP, "pinch_area"));
        UiObject2 scaleText = pinchArea.findObject(By.res(TEST_APP, "scale_factor"));
        pinchArea.pinchClose(.75f, 100);
        scaleText.wait(Until.textNotEquals("1.0f"), 1000);
    }

    public void testPinchIn50Percent() throws TimeoutException {
        launchTestActivity("UiObject2TestPinchActivity");

        // Find the area to pinch
        UiObject2 pinchArea = mDevice.findObject(By.res(TEST_APP, "pinch_area"));
        UiObject2 scaleText = pinchArea.findObject(By.res(TEST_APP, "scale_factor"));
        pinchArea.pinchClose(.5f, 100);
        scaleText.wait(Until.textNotEquals("1.0f"), 1000);
    }

    public void testPinchIn25Percent() throws TimeoutException {
        launchTestActivity("UiObject2TestPinchActivity");

        // Find the area to pinch
        UiObject2 pinchArea = mDevice.findObject(By.res(TEST_APP, "pinch_area"));
        UiObject2 scaleText = pinchArea.findObject(By.res(TEST_APP, "scale_factor"));
        pinchArea.pinchClose(.25f, 100);
        scaleText.wait(Until.textNotEquals("1.0f"), 1000);
    }

    public void testScrollDown() throws TimeoutException {
        launchTestActivity("UiObject2TestVerticalScrollActivity");

        // Make sure we're at the top
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "top_text")));

        UiObject2 scrollView = mDevice.findObject(By.res(TEST_APP, "scroll_view"));
        Rect bounds = scrollView.getVisibleBounds();
        float distance = 50000 / (bounds.height() - 2*10);

        //
        //scrollView.scroll(Direction.DOWN, 1.0f);
        //assertNull(mDevice.findObject(By.res(TEST_APP, "top_text")));
        //while (scrollView.scroll(Direction.DOWN, 1.0f)) {
        //}
        scrollView.scroll(Direction.DOWN, distance);
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "bottom_text")));
    }

    public void testScrollDistance() throws TimeoutException {
        launchTestActivity("UiObject2TestVerticalScrollActivity");

        /*
        // Make sure we're at the top
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "top_text")));
        int MARGIN = 1;

        // Scroll to an element 5000px from the top
        UiObject2 scrollView = mDevice.findObject(By.res(TEST_APP, "scroll_view"));
        Rect bounds = scrollView.getVisibleBounds();
        float distance = 5000.0f / (float)(bounds.height() - 2*MARGIN);
        scrollView.scroll(Direction.DOWN, distance);
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "from_top_5000")));

        // Scroll to an element 10000px from the top
        scrollView.scroll(Direction.DOWN, distance);
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "from_top_10000")));

        // Scroll to an element 15000px from the top
        scrollView.scroll(Direction.DOWN, distance);
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "from_top_15000")));
        */
    }

    public void testScrollDownToEnd() throws TimeoutException {
        launchTestActivity("UiObject2TestVerticalScrollActivity");

        // Make sure we're at the top
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "top_text")));

        // Scroll as much as we can
        UiObject2 scrollView = mDevice.findObject(By.res(TEST_APP, "scroll_view"));
        scrollView.wait(Until.scrollable(true), 5000);
        while (scrollView.scroll(Direction.DOWN, 1.0f)) { }

        // Make sure we're at the bottom
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "bottom_text")));
    }

    public void testSetText() {}

    public void testWaitForExists() {}

    public void testWaitForGone() {}
}
