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

@file:Suppress("unused")

package io.getstream.butterfly

import android.content.res.Configuration
import android.content.res.Resources
import android.util.Size
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import io.getstream.butterfly.internal.px2dp
import io.getstream.butterfly.internal.toSize

/** Finds a [FoldingFeature] from a list of [DisplayFeature]. */
public fun List<DisplayFeature>.findFoldingFeature(): FoldingFeature? {
    return filterIsInstance<FoldingFeature>().firstOrNull()
}

/** Returns a [Size] spec from a [FoldingFeature]. */
public fun DisplayFeature.toSize(): Size {
    return Size(bounds.right - bounds.left, bounds.bottom - bounds.top)
}

/** Returns a width pixel size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingeWidthPxSize: Int
    @JvmSynthetic inline get() = toSize().width

/** Returns a width pixel size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingeHeightPxSize: Int
    @JvmSynthetic inline get() = toSize().height

/** Returns a pixel size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingePxSize: Int
    @JvmSynthetic inline get() =
        if (Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hingeWidthPxSize
        } else {
            hingeHeightPxSize
        }

/** Returns a width Dp size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingeWidthDpSize: Int
    @JvmSynthetic inline get() = toSize().width.px2dp

/** Returns a width Dp size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingeHeightDpSize: Int
    @JvmSynthetic inline get() = toSize().height.px2dp

/** Returns a dp size of the hinge from a [FoldingFeature]. */
public val FoldingFeature.hingeDpSize: Int
    @JvmSynthetic inline get() =
        if (Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hingeWidthDpSize
        } else {
            hingeHeightDpSize
        }

/** Returns a [Posture] which represent the current posture of the foldable device. */
public fun FoldingFeature.toPosture(): Posture {
    return when {
        isTableTopPosture -> Posture.TableTop(bounds.toSize())
        isBookPosture -> Posture.Book(bounds.toSize())
        else -> Posture.Normal
    }
}

/** Returns whether a [FoldingFeature] is the table-top mode. */
public val FoldingFeature.isTableTopPosture: Boolean
    @JvmSynthetic inline get() {
        return state == FoldingFeature.State.HALF_OPENED &&
            orientation == FoldingFeature.Orientation.HORIZONTAL
    }

/** Returns whether a [FoldingFeature] is the book mode. */
public val FoldingFeature.isBookPosture: Boolean
    @JvmSynthetic inline get() {
        return state == FoldingFeature.State.HALF_OPENED &&
            orientation == FoldingFeature.Orientation.VERTICAL
    }

/** Returns whether the state of a [FoldingFeature] is half-opened. */
public val FoldingFeature.isHalfOpened: Boolean
    @JvmSynthetic inline get() {
        return state == FoldingFeature.State.HALF_OPENED
    }

/** Returns whether the state of a [FoldingFeature] is flat. */
public val FoldingFeature.isFlat: Boolean
    @JvmSynthetic inline get() {
        return state == FoldingFeature.State.FLAT
    }

/** Returns whether the orientation of a [FoldingFeature] is vertical. */
public val FoldingFeature.isVertical: Boolean
    @JvmSynthetic inline get() {
        return orientation == FoldingFeature.Orientation.HORIZONTAL
    }

/** Returns whether the orientation of a [FoldingFeature] is horizontal. */
public val FoldingFeature.isHorizontal: Boolean
    @JvmSynthetic inline get() {
        return orientation == FoldingFeature.Orientation.HORIZONTAL
    }
