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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import io.getstream.butterfly.Posture
import io.getstream.butterfly.postureFlow
import io.getstream.butterfly.windowLayoutInfo

/** Returns a [State] of the [WindowLayoutInfo]. */
public val Activity.windowLayoutInfoState: State<WindowLayoutInfo>
    @JvmSynthetic @Composable inline get() =
        windowLayoutInfo.collectAsState(initial = WindowLayoutInfo(emptyList()))

/** Returns a [State] of the [WindowLayoutInfo]. */
public val Fragment.windowLayoutInfoState: State<WindowLayoutInfo>
    @JvmSynthetic @Composable inline get() = requireActivity().windowLayoutInfoState

/** Returns a [State] of the [WindowLayoutInfo]. */
@Composable
public fun WindowInfoTracker.windowLayoutInfoState(
    activity: Activity
): State<WindowLayoutInfo> {
    return windowLayoutInfo(activity).collectAsState(initial = WindowLayoutInfo(emptyList()))
}

/** Returns a [State] of the [Posture] by tracking the [WindowLayoutInfo]. */
public val Activity.postureState: State<Posture>
    @Composable get() = postureFlow.collectAsState(initial = Posture.Normal)

/** Returns a [State] of the [Posture] by tracking the [WindowLayoutInfo]. */
public val Fragment.postureState: State<Posture>
    @Composable get() = requireActivity().postureState
