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

package android.support.test.uiautomator.tests;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class BySelectorTests extends InstrumentationTestCase {

    private static final String TAG = BySelectorTests.class.getSimpleName();

    private static final String TEST_APP = "android.support.test.uiautomator.testapp";
    private static final String ANDROID_WIDGET_PACKAGE = "android.widget";

    private UiDevice mDevice;

    public void setUp() throws Exception {
        super.setUp();

        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    public void launchTestActivity(String activity) throws TimeoutException {
        // Launch the test app
        Context context = getInstrumentation().getContext();
        Intent intent = new Intent()
                .setClassName(TEST_APP, String.format("%s.%s", TEST_APP, activity))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for activity to appear
        mDevice.wait(Until.hasObject(By.pkg(TEST_APP)), 10000);
    }

    public void tearDown() throws Exception {
        mDevice.pressHome();

        // Wait for the activity to disappear
        mDevice.wait(Until.gone(By.pkg(TEST_APP)), 5000);

        super.tearDown();
    }

    public void testCopy() throws TimeoutException {
        launchTestActivity("MainActivity");

        // Base selector
        BySelector base = By.clazz(".TextView");

        // Select various TextView instances
        assertNotNull(mDevice.findObject(By.copy(base).text("Text View 1")));
        assertNotNull(mDevice.findObject(By.copy(base).text("Item1")));
        assertNotNull(mDevice.findObject(By.copy(base).text("Item3")));

        // Shouldn't be able to select an object that does not match the base
        assertNull(mDevice.findObject(By.copy(base).text("Accessible button")));
    }

    public void testClazzButton() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // Button
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "Button")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.Button")));
        assertNotNull(mDevice.findObject(By.clazz(".Button")));
        assertNotNull(mDevice.findObject(By.clazz(Button.class)));
    }

    public void testClazzCheckBox() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // CheckBox
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "CheckBox")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.CheckBox")));
        assertNotNull(mDevice.findObject(By.clazz(".CheckBox")));
        assertNotNull(mDevice.findObject(By.clazz(CheckBox.class)));
    }

    public void testClazzEditText() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // EditText
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "EditText")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.EditText")));
        assertNotNull(mDevice.findObject(By.clazz(".EditText")));
        assertNotNull(mDevice.findObject(By.clazz(EditText.class)));
    }

    public void testClazzProgressBar() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // ProgressBar
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "ProgressBar")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.ProgressBar")));
        assertNotNull(mDevice.findObject(By.clazz(".ProgressBar")));
        assertNotNull(mDevice.findObject(By.clazz(ProgressBar.class)));
    }

    public void testClazzRadioButton() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // RadioButton
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "RadioButton")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.RadioButton")));
        assertNotNull(mDevice.findObject(By.clazz(".RadioButton")));
        assertNotNull(mDevice.findObject(By.clazz(RadioButton.class)));
    }

    public void testClazzRatingBar() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // RatingBar
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "RatingBar")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.RatingBar")));
        assertNotNull(mDevice.findObject(By.clazz(".RatingBar")));
        assertNotNull(mDevice.findObject(By.clazz(RatingBar.class)));
    }

    public void testClazzSeekBar() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // SeekBar
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "SeekBar")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.SeekBar")));
        assertNotNull(mDevice.findObject(By.clazz(".SeekBar")));
        assertNotNull(mDevice.findObject(By.clazz(SeekBar.class)));
    }

    public void testClazzSwitch() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // Switch
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "Switch")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.Switch")));
        assertNotNull(mDevice.findObject(By.clazz(".Switch")));
        assertNotNull(mDevice.findObject(By.clazz(Switch.class)));
    }

    /* TextClock (Bug: TextClock does not report its correct class name)
    public void testClazzTextClock() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        assertNotNull(mDevice.findObject(By.clazz("android.widget", "TextClock")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.TextClock")));
        assertNotNull(mDevice.findObject(By.clazz(".TextClock")));
        assertNotNull(mDevice.findObject(By.clazz(TextClock.class)));
    }*/

    public void testClazzTextView() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // TextView
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "TextView")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.TextView")));
        assertNotNull(mDevice.findObject(By.clazz(".TextView")));
        assertNotNull(mDevice.findObject(By.clazz(TextView.class)));
    }

    public void testClazzToggleButton() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // ToggleButton
        assertNotNull(mDevice.findObject(By.clazz("android.widget", "ToggleButton")));
        assertNotNull(mDevice.findObject(By.clazz("android.widget.ToggleButton")));
        assertNotNull(mDevice.findObject(By.clazz(".ToggleButton")));
        assertNotNull(mDevice.findObject(By.clazz(ToggleButton.class)));
    }

    public void testClazzNotFound() throws TimeoutException {
        launchTestActivity("BySelectorTestClazzActivity");

        // Non-existant class
        assertNull(mDevice.findObject(By.clazz("android.widget", "NonExistantClass")));
        assertNull(mDevice.findObject(By.clazz("android.widget.NonExistantClass")));
        assertNull(mDevice.findObject(By.clazz(".NonExistantClass")));
    }

    public void testClazzNull() throws TimeoutException {
        // clazz(String)
        try {
            mDevice.findObject(By.clazz((String)null));
            fail();
        } catch (NullPointerException e) {}

        // clazz(String, String)
        try {
            mDevice.findObject(By.clazz((String)null, "foo"));
            fail();
        } catch (NullPointerException e) {}

        try {
            mDevice.findObject(By.clazz("foo", (String)null));
            fail();
        } catch (NullPointerException e) {}

        // clazz(Class)
        try {
            mDevice.findObject(By.clazz((Class)null));
            fail();
        } catch (NullPointerException e) {}

        // clazz(Pattern)
        try {
            mDevice.findObject(By.clazz((Pattern)null));
            fail();
        } catch (NullPointerException e) {}
    }

    // TODO: For clazz:
    // 1. Custom class
    // 2. Patterns
    // 3. Runtime Widgets

    public void testDescSetFromResource() throws TimeoutException {
        launchTestActivity("BySelectorTestDescActivity");

        // Content Description from resource
        assertNotNull(mDevice.findObject(By.desc("Content Description Set From Layout")));
    }

    public void testDescSetAtRuntime() throws TimeoutException {
        launchTestActivity("BySelectorTestDescActivity");

        // Content Description set at runtime
        assertNotNull(mDevice.findObject(By.desc("Content Description Set At Runtime")));
    }

    public void testDescNotFound() throws TimeoutException {
        launchTestActivity("BySelectorTestDescActivity");

        // No element has this content description
        assertNull(mDevice.findObject(By.desc("No element has this Content Description")));
    }

    public void testDescNull() throws TimeoutException {
        // desc(String)
        try {
            mDevice.findObject(By.desc((String)null));
            fail();
        } catch (NullPointerException e) {}

        // desc(Pattern)
        try {
            mDevice.findObject(By.desc((Pattern)null));
            fail();
        } catch (NullPointerException e) {}
    }

    // TODO: For desc:
    // 1. Patterns
    // 2. Runtime Widgets

    public void testPackage() throws TimeoutException {
        launchTestActivity("MainActivity");

        // Full match with string argument
        assertNotNull(mDevice.findObject(By.pkg(TEST_APP)));
    }

    public void testPkgNull() throws TimeoutException {
        // pkg(String)
        try {
            mDevice.findObject(By.pkg((String)null));
            fail();
        } catch (NullPointerException e) {}

        // pkg(Pattern)
        try {
            mDevice.findObject(By.pkg((Pattern)null));
            fail();
        } catch (NullPointerException e) {}
    }

    public void testResUniqueId() throws TimeoutException {
        launchTestActivity("BySelectorTestResActivity");

        // Unique ID
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "unique_id")));
        assertNotNull(mDevice.findObject(By.res(TEST_APP + ":id/unique_id")));
    }

    public void testResCommonId() throws TimeoutException {
        launchTestActivity("BySelectorTestResActivity");

        // Shared ID
        assertNotNull(mDevice.findObject(By.res(TEST_APP, "shared_id")));
        assertNotNull(mDevice.findObject(By.res(TEST_APP + ":id/shared_id")));
        // 1. Make sure we can see all instances
        // 2. Differentiate between matches by other criteria
    }

    public void testResNull() throws TimeoutException {
        // res(String)
        try {
            mDevice.findObject(By.res((String)null));
            fail();
        } catch (NullPointerException e) {}

        // res(String, String)
        try {
            mDevice.findObject(By.res((String)null, "foo"));
            fail();
        } catch (NullPointerException e) {}

        try {
            mDevice.findObject(By.res("foo", (String)null));
            fail();
        } catch (NullPointerException e) {}

        // res(Pattern)
        try {
            mDevice.findObject(By.res((Pattern)null));
            fail();
        } catch (NullPointerException e) {}
    }

    public void testTextUnique() throws TimeoutException {
        launchTestActivity("BySelectorTestTextActivity");

        // Unique Text
        assertNotNull(mDevice.findObject(By.text("Unique Text")));
    }

    public void testTextCommon() throws TimeoutException {
        launchTestActivity("BySelectorTestTextActivity");

        // Common Text
        assertNotNull(mDevice.findObject(By.text("Common Text")));
        assertEquals(2, mDevice.findObjects(By.text("Common Text")).size());
    }

    public void testTextNull() throws TimeoutException {
        // text(String)
        try {
            mDevice.findObject(By.text((String)null));
            fail();
        } catch (NullPointerException e) {}

        // text(Pattern)
        try {
            mDevice.findObject(By.text((Pattern)null));
            fail();
        } catch (NullPointerException e) {}
    }

    public void testHasUniqueChild() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Find parent with unique child
        UiObject2 object = mDevice.findObject(By.hasChild(By.res(TEST_APP, "toplevel1_child1")));
        assertNotNull(object);
    }

    public void testHasCommonChild() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Find parent(s) with common child
        assertNotNull(mDevice.findObject(By.hasChild(By.clazz(".TextView"))));
        assertEquals(3, mDevice.findObjects(By.hasChild(By.clazz(".TextView"))).size());
    }

    public void testGetChildren() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        UiObject2 parent = mDevice.findObject(By.res(TEST_APP, "toplevel2"));
        assertEquals(2, parent.getChildren().size());
    }

    public void testHasMultipleChildren() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Select parent with multiple hasChild selectors
        UiObject2 object = mDevice.findObject(By
                .hasChild(By.res(TEST_APP, "toplevel2_child1"))
                .hasChild(By.res(TEST_APP, "toplevel2_child2")));
        assertNotNull(object);
    }

    public void testHasMultipleChildrenCollision() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Select parent with multiple hasChild selectors, but single child that matches both
        UiObject2 object = mDevice.findObject(By
                .hasChild(By.res(TEST_APP, "toplevel1_child1"))
                .hasChild(By.clazz(".TextView")));
        assertNotNull(object);
    }

    public void testHasChildThatHasChild() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Select parent with child that has a child
        UiObject2 object = mDevice.findObject(
                By.hasChild(By.hasChild(By.res(TEST_APP, "toplevel3_container1_child1"))));
        assertNotNull(object);
    }

    public void testHasDescendant() throws TimeoutException {
        launchTestActivity("BySelectorTestHasChildActivity");

        // Select a LinearLayout that has a unique descendant
        UiObject2 object = mDevice.findObject(By
                .clazz(".RelativeLayout")
                .hasDescendant(By.res(TEST_APP, "toplevel3_container1_child1")));
        assertNotNull(object);
    }
}
