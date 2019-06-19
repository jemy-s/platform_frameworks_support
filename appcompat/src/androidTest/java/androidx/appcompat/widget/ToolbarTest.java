/*
 * Copyright (C) 2018 The Android Open Source Project
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

package androidx.appcompat.widget;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import androidx.appcompat.test.R;
import androidx.appcompat.testutils.TestUtils;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test {@link Toolbar}.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class ToolbarTest {

    private Instrumentation mInstrumentation;
    private Activity mActivity;
    private Toolbar mToolbar;

    @Rule
    public ActivityTestRule<ToolbarTestActivity> mActivityRule =
            new ActivityTestRule<>(ToolbarTestActivity.class);

    @Before
    public void setup() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mActivity = mActivityRule.getActivity();
        mToolbar = mActivity.findViewById(R.id.toolbar);
    }

    @Test
    public void testCollapseConfiguration() {
        // Inflate menu with action view to display the collapse button.
        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mToolbar.inflateMenu(R.menu.search_action);
            }
        });
        final MenuItem searchMenuItem = mToolbar.getMenu().findItem(R.id.action_search);
        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                searchMenuItem.expandActionView();
            }
        });

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mToolbar.setCollapseIcon(R.drawable.icon_green);
            }
        });
        Drawable toolbarCollapseIcon = mToolbar.getCollapseIcon();
        TestUtils.assertAllPixelsOfColor("Collapse icon is green", toolbarCollapseIcon,
                toolbarCollapseIcon.getIntrinsicWidth(),
                toolbarCollapseIcon.getIntrinsicHeight(),
                true, Color.GREEN, 1, false);

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mToolbar.setCollapseIcon(R.drawable.icon_blue);
            }
        });
        toolbarCollapseIcon = mToolbar.getCollapseIcon();
        TestUtils.assertAllPixelsOfColor("Collapse icon is blue", toolbarCollapseIcon,
                toolbarCollapseIcon.getIntrinsicWidth(),
                toolbarCollapseIcon.getIntrinsicHeight(),
                true, Color.BLUE, 1, false);

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mToolbar.setCollapseContentDescription(R.string.toolbar_collapse);
            }
        });
        assertEquals(mActivity.getResources().getString(R.string.toolbar_collapse),
                mToolbar.getCollapseContentDescription());

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mToolbar.setCollapseContentDescription("Collapse legend");
            }
        });
        assertEquals("Collapse legend", mToolbar.getCollapseContentDescription());
    }
}
