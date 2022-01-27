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

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/** Defines the current orientation of the window.  */
public enum class WindowOrientation {
    ORIENTATION_LANDSCAPE,
    ORIENTATION_PORTRAIT,
}

/** Returns [WindowOrientation] according to the current window orientation. */
public val windowOrientation: WindowOrientation
    @Composable inline get() =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            WindowOrientation.ORIENTATION_LANDSCAPE
        } else {
            WindowOrientation.ORIENTATION_PORTRAIT
        }
