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

import android.util.Size
import io.getstream.butterfly.internal.Dp

/**
 * Definition of the window size to be on the global scope.
 */
public object GlobalWindowSize {

    /** Defines most phones in portrait mode */
    @Dp
    public var compactWindowDpSize: Int = 600

    /** Defines most foldables and tablets in portrait mode */
    @Dp
    public var mediumWindowDpSize: Int = 840

    /** A global factory class of the [WindowSize]. */
    public var windowSizeFactory: ((windowPixelSize: Size) -> WindowSize)? = null
}
