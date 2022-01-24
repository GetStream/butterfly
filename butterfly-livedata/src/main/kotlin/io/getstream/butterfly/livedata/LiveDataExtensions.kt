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

package io.getstream.butterfly.livedata

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import io.getstream.butterfly.Posture
import io.getstream.butterfly.postureFlow
import io.getstream.butterfly.windowLayoutInfo
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Returns a [LiveData] of the [WindowLayoutInfo]. */
public fun Activity.windowLayoutInfoLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
): LiveData<WindowLayoutInfo> {
    return windowLayoutInfo.asLiveData(context)
}

/** Returns a [LiveData] of the [WindowLayoutInfo]. */
public fun Fragment.windowLayoutInfoLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
): LiveData<WindowLayoutInfo> {
    return requireActivity().windowLayoutInfoLiveData(context)
}

/** Returns a [LiveData] of the [WindowLayoutInfo]. */
public fun WindowInfoTracker.windowLayoutInfoLiveData(
    activity: Activity,
    context: CoroutineContext = EmptyCoroutineContext,
): LiveData<WindowLayoutInfo> {
    return activity.windowLayoutInfoLiveData(context)
}

/** Returns a [LiveData] of the [Posture] by tracking the [WindowLayoutInfo]. */
public fun Activity.postureLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
): LiveData<Posture> {
    return liveData(context) {
        postureFlow.collect { emit(it) }
    }
}

/** Returns a [LiveData] of the [Posture] by tracking the [WindowLayoutInfo]. */
public fun Fragment.postureLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
): LiveData<Posture> {
    return requireActivity().postureLiveData(context)
}
