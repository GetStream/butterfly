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

package io.getstream.butterfly

import android.app.Activity
import android.util.Size
import androidx.fragment.app.Fragment
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import io.getstream.butterfly.internal.dp
import io.getstream.butterfly.internal.toSize

/**
 * Breakpoints defines the screen size at which a layout will adapt to best fit content and conform to
 * responsive layout requirements. A breakpoint range informs how a screen adjusts to fit its size and orientation.
 *
 * [Design for large screens](https://m3.material.io/foundations/adaptive-design/large-screens/overview)
 *
 * Compact: Most phones in portrait mode
 * Medium: Most foldables and tablets in portrait mode
 * Expanded: Most tablets in landscape mode
 *
 * @param windowSize Represent the current size of the viewport.
 */
public sealed class WindowSize(public val windowSize: Size) {
    public class Compact(windowPixelSize: Size) : WindowSize(windowPixelSize)
    public class Medium(windowPixelSize: Size) : WindowSize(windowPixelSize)
    public class Expanded(windowPixelSize: Size) : WindowSize(windowPixelSize)
}

/**
 * Returns the [WindowSize] by computing the size of the viewport.
 *
 * @return A [WindowSize] corresponding the size of the viewport.
 */
public fun Activity.getWindowSize(): WindowSize {
    val windowSize = computeWindowSize()
    return getWindowSizeClass(windowSize)
}

/**
 * Returns the [WindowSize] by computing the size of the viewport.
 *
 * @return A [WindowSize] corresponding the size of the viewport.
 */
public fun Fragment.getWindowSize(): WindowSize {
    return requireActivity().getWindowSize()
}

/**
 * Computes the size of the Window by using [WindowMetricsCalculator].
 *
 * @return A [Size] from the [WindowMetrics].
 */
private fun Activity.computeWindowSize(): Size {
    val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
    return windowMetrics.bounds.toSize()
}

/**
 * Partitions a width size into a enumerated [WindowSize] class.
 *
 * @param windowPixelSize A pixel size of the viewport.
 *
 * @return A [WindowSize] corresponding the size of the viewport.
 */
private fun getWindowSizeClass(windowPixelSize: Size): WindowSize =
    GlobalWindowSize.windowSizeFactory?.invoke(windowPixelSize) ?: when {
        windowPixelSize.width < 0 -> throw IllegalArgumentException("Pixel value cannot be negative")
        windowPixelSize.width < GlobalWindowSize.compactWindowDpSize.dp -> WindowSize.Compact(
            windowPixelSize
        )
        windowPixelSize.width < GlobalWindowSize.mediumWindowDpSize.dp -> WindowSize.Medium(
            windowPixelSize
        )
        else -> WindowSize.Expanded(windowPixelSize)
    }
