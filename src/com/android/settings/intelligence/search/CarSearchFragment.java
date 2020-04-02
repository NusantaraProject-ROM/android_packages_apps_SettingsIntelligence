/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.settings.intelligence.search;

import static com.android.car.ui.core.CarUi.requireInsets;
import static com.android.car.ui.core.CarUi.requireToolbar;

import android.os.Bundle;

import com.android.car.ui.preference.PreferenceFragment;
import com.android.car.ui.toolbar.ToolbarController;
import com.android.settings.intelligence.R;
import com.android.car.ui.toolbar.MenuItem;
import com.android.car.ui.toolbar.Toolbar;

import java.util.Collections;
import java.util.List;

/**
 * Search fragment for car settings
 */
public class CarSearchFragment extends PreferenceFragment {

    private MenuItem mClearHistoryButton;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.car_search_fragment, rootKey);
    }

    protected ToolbarController getToolbar() {
        return requireToolbar(requireActivity());
    }

    protected List<MenuItem> getToolbarMenuItems() {
        return Collections.singletonList(mClearHistoryButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClearHistoryButton = new MenuItem.Builder(getContext())
                .setTitle(R.string.search_clear_history)
                .setDisplayBehavior(MenuItem.DisplayBehavior.NEVER)
                .setOnClickListener(i -> onClearHistoryButtonClicked())
                .build();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ToolbarController toolbar = getToolbar();
        if (toolbar != null) {
            List<MenuItem> items = getToolbarMenuItems();
            toolbar.setTitle(getPreferenceScreen().getTitle());
            toolbar.setMenuItems(items);
            toolbar.setNavButtonMode(Toolbar.NavButtonMode.BACK);
            toolbar.setState(Toolbar.State.SUBPAGE);
            toolbar.setState(Toolbar.State.SEARCH);
            toolbar.setSearchHint(R.string.abc_search_hint);
            toolbar.registerOnSearchListener(this::onQueryTextChange);
            toolbar.setShowMenuItemsWhileSearching(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        onCarUiInsetsChanged(requireInsets(requireActivity()));
    }

    private void onQueryTextChange(String query) {}

    private void onClearHistoryButtonClicked() {}
}
