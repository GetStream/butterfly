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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import io.getstream.butterfly.compose.internal.px2dp

/** Returns a Dp size from a [FoldingFeature]. */
public val FoldingFeature.hingeDp: Dp
    @JvmSynthetic inline get() {
        return (bounds.right - bounds.left).px2dp.dp
    }

/** Returns a Dp size from a [FoldingFeature]. */
public val FoldingFeature.hingeDpSize: DpSize
    @JvmSynthetic inline get() {
        return DpSize(
            width = (bounds.right - bounds.left).px2dp.dp,
            height = (bounds.bottom - bounds.top).px2dp.dp
        )
    }
