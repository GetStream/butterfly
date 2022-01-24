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
import androidx.fragment.app.Fragment
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

/** Returns a [Flow] of the [WindowLayoutInfo]. */
public val Activity.windowLayoutInfo: Flow<WindowLayoutInfo>
    @JvmSynthetic inline get() {
        return WindowInfoTracker.getOrCreate(this)
            .windowLayoutInfo(this)
    }

/** Returns a [Flow] of the [WindowLayoutInfo]. */
public val Fragment.windowLayoutInfo: Flow<WindowLayoutInfo>
    @JvmSynthetic inline get() {
        return requireActivity().windowLayoutInfo
    }

/** Returns a [Flow] of the [Posture] by tracking the [WindowLayoutInfo]. */
public fun WindowInfoTracker.postureFlow(
    activity: Activity
): Flow<Posture> {
    return activity.windowLayoutInfo.map { layoutInfo ->
        layoutInfo.findFoldingFeature()?.toPosture()
    }.filterNotNull()
}

/** Returns a [Flow] of the [Posture] by tracking the [WindowLayoutInfo]. */
public val Activity.postureFlow: Flow<Posture>
    @JvmSynthetic inline get() = WindowInfoTracker.getOrCreate(this).postureFlow(this)

/** Returns a [Flow] of the [Posture] by tracking the [WindowLayoutInfo]. */
public val Fragment.postureFlow: Flow<Posture>
    @JvmSynthetic inline get() = requireActivity().postureFlow
