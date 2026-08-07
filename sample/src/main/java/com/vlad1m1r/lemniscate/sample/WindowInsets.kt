/*
 * Copyright 2026 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vlad1m1r.lemniscate.sample

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Adds the system bar and display cutout insets to this view's padding, on the requested
 * edges only.
 *
 * Each view takes just the edges it is responsible for, so the toolbar can keep drawing its
 * background behind the status bar while the content below it stays clear of the navigation
 * bar. The insets are returned unconsumed so sibling views still receive them.
 *
 * The padding set in the layout is kept as the baseline, which makes this safe to call more
 * than once (for example after a configuration change).
 */
fun View.addSystemBarInsetsToPadding(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
) {
    val initialLeft = paddingLeft
    val initialTop = paddingTop
    val initialRight = paddingRight
    val initialBottom = paddingBottom

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.setPadding(
                initialLeft + if (left) insets.left else 0,
                initialTop + if (top) insets.top else 0,
                initialRight + if (right) insets.right else 0,
                initialBottom + if (bottom) insets.bottom else 0
        )
        windowInsets
    }
    ViewCompat.requestApplyInsets(this)
}
