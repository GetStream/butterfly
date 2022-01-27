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

package io.getstream.butterfly.internal

import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowLayoutInfo
import io.getstream.butterfly.windowLayoutInfo
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** Returns size from a dp size. */
internal val Int.dp: Int
    @JvmSynthetic inline get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

/** Returns dp size from a pixel size. */
@PublishedApi
internal val Int.px2dp: Int
    @JvmSynthetic inline get() =
        this / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

/** Returns [Size] class from a [Rect] class. */
@JvmSynthetic
internal fun Rect.toSize(): Size {
    return Size(right - left, bottom - top)
}

@JvmSynthetic
internal inline fun ComponentActivity.collectWindowLayoutInfo(
    crossinline action: suspend (value: WindowLayoutInfo) -> Unit
) {
    lifecycleScope.launch(Dispatchers.Main) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            windowLayoutInfo.collect(action)
        }
    }
}
