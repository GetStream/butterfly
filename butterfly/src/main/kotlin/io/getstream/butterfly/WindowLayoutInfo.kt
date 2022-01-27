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

import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo

/** Finds a [FoldingFeature] from a [WindowLayoutInfo]. */
public fun WindowLayoutInfo.findFoldingFeature(): FoldingFeature? {
    return displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
}

/**
 * Calculates if a [FoldingFeature] should be thought of as splitting the window into
 * multiple physical areas that can be seen by users as logically separate. Display panels
 * connected by a hinge are always separated. Folds on flexible screens should be treated as
 * separating when they are not [FoldingFeature.State.FLAT].
 */
public val WindowLayoutInfo.isSeparating: Boolean
    @JvmSynthetic inline get() = findFoldingFeature()?.isSeparating ?: false

/**
 * Calculates the occlusion mode to determine if a FoldingFeature occludes a part of the window.
 * This flag is useful for determining if UI elements need to be moved around so that the user can access them.
 * For some devices occluded elements can not be accessed by the user at all.
 */
public val WindowLayoutInfo.occlusionType: FoldingFeature.OcclusionType
    @JvmSynthetic inline get() = findFoldingFeature()?.occlusionType
        ?: FoldingFeature.OcclusionType.NONE

/**
 * Returns [FoldingFeature.Orientation.HORIZONTAL] if the width is greater than the height,
 * [FoldingFeature.Orientation.VERTICAL] otherwise.
 */
public val WindowLayoutInfo.orientation: FoldingFeature.Orientation
    @JvmSynthetic inline get() = findFoldingFeature()?.orientation
        ?: FoldingFeature.Orientation.HORIZONTAL

/**
 * Returns the [FoldingFeature.State] for the [FoldingFeature].
 */
public val WindowLayoutInfo.state: FoldingFeature.State
    @JvmSynthetic inline get() = findFoldingFeature()?.state
        ?: FoldingFeature.State.FLAT
