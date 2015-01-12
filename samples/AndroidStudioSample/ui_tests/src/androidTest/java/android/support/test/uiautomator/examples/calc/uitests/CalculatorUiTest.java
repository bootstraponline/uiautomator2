/*
 * Copyright 2015, The Android Open Source Project
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

package android.support.test.uiautomator.examples.calc.uitests;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

public class CalculatorUiTest extends InstrumentationTestCase {

    private static final String CALC_PACKAGE = "android.support.test.uiautomator.examples.calc";
    private static final int TIMEOUT = 5000;

    private UiDevice mDevice;

    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();
        mDevice.wait(Until.hasObject(By.pkg(getHomeScreenPackage()).depth(0)), TIMEOUT);

        // Launch our simple calculator app
        Context context = getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(CALC_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear out any previous instances
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(CALC_PACKAGE).depth(0)), TIMEOUT);
    }

    public void testTwoPlusThreeEqualsFive() {
        // Enter an equation: 2 + 3 = ?
        mDevice.findObject(By.res(CALC_PACKAGE, "two")).click();
        mDevice.findObject(By.res(CALC_PACKAGE, "plus")).click();
        mDevice.findObject(By.res(CALC_PACKAGE, "three")).click();
        mDevice.findObject(By.res(CALC_PACKAGE, "equals")).click();

        // Verify the result = 5
        UiObject2 result = mDevice.findObject(By.res(CALC_PACKAGE, "result"));
        assertEquals("5", result.getText());
    }

    private String getHomeScreenPackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager pm = getInstrumentation().getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return resolveInfo.activityInfo.packageName;
    }
}
