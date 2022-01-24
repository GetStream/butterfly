/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.butterfly.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.window.layout.WindowMetricsCalculator
import io.getstream.butterfly.GlobalWindowSize
import io.getstream.butterfly.WindowSize

/**
 * Copyright 2022 Google LLC.
 * SPDX-License-Identifier: Apache-2.0
 *
 * Breakpoints defines the screen size at which a layout will adapt to best fit content and conform to
 * responsive layout requirements. A breakpoint range informs how a screen adjusts to fit its size and orientation.
 *
 * [Design for large screens](https://m3.material.io/foundations/adaptive-design/large-screens/overview)
 *
 * Compact: Most phones in portrait mode
 * Medium: Most foldables and tablets in portrait mode
 * Expanded: Most tablets in landscape mode
 *
 * @param windowSize Represent the current dp size of the viewport.
 */
public sealed class WindowDpSize(public val windowSize: DpSize) {
    public class Compact(windowDpSize: DpSize) : WindowDpSize(windowDpSize)
    public class Medium(windowDpSize: DpSize) : WindowDpSize(windowDpSize)
    public class Expanded(windowDpSize: DpSize) : WindowDpSize(windowDpSize)
}

/**
 * Copyright 2022 Google LLC.
 * SPDX-License-Identifier: Apache-2.0
 *
 * Remembers the [WindowSize] class for the window corresponding to the current window metrics.
 *
 * [References]()https://github.com/android/compose-samples/blob/d38047520c00d5eed71eb731b1fa5ecd99f59a32/JetNews/app/src/main/java/com/example/jetnews/utils/WindowSize.kt)
 */
@Composable
public fun Activity.rememberWindowDpSize(): WindowDpSize {
    // Get the size (in pixels) of the window
    val windowSize = computeWindowDpSize()

    // Convert the window size to [Dp]
    val windowDpSize = with(LocalDensity.current) {
        windowSize.toDpSize()
    }

    // Calculate the window size class
    return getWindowSizeClass(windowDpSize)
}

/**
 * Remembers the [WindowSize] class for the window corresponding to the current window metrics.
 *
 * [References]()https://github.com/android/compose-samples/blob/d38047520c00d5eed71eb731b1fa5ecd99f59a32/JetNews/app/src/main/java/com/example/jetnews/utils/WindowSize.kt)
 */
@Composable
public fun Fragment.rememberWindowDpSize(): WindowDpSize {
    return requireActivity().rememberWindowDpSize()
}

/**
 * Copyright 2022 Google LLC.
 * SPDX-License-Identifier: Apache-2.0
 *
 * Remembers the [Size] in pixels of the window corresponding to the current window metrics.
 */
@Composable
private fun Activity.computeWindowDpSize(): Size {
    val configuration = LocalConfiguration.current
    // WindowMetricsCalculator implicitly depends on the configuration through the activity,
    // so re-calculate it upon changes.
    val windowMetrics = remember(configuration) {
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
    }
    return windowMetrics.bounds.toComposeRect().size
}

/**
 * Copyright 2022 Google LLC.
 * SPDX-License-Identifier: Apache-2.0
 *
 * Partitions a [DpSize] into a enumerated [WindowSize] class.
 */
private fun getWindowSizeClass(windowDpSize: DpSize): WindowDpSize = when {
    windowDpSize.width < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
    windowDpSize.width < GlobalWindowSize.compactWindowDpSize.dp -> WindowDpSize.Compact(
        windowDpSize
    )
    windowDpSize.width < GlobalWindowSize.mediumWindowDpSize.dp -> WindowDpSize.Medium(windowDpSize)
    else -> WindowDpSize.Expanded(windowDpSize)
}
