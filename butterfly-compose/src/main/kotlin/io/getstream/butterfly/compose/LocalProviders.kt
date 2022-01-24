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

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import io.getstream.butterfly.Posture

/** Definition of the [WindowDpSize] local provider. */
public val LocalWindowDpSize: ProvidableCompositionLocal<WindowDpSize> = compositionLocalOf {
    error("CompositionLocal WindowDpSize not provided")
}

/** Definition of the [Posture] local provider. */
public val LocalPosture: ProvidableCompositionLocal<Posture> = compositionLocalOf {
    error("CompositionLocal LocalPosture not provided")
}
