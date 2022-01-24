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

/** Represent the posture of the foldable device. */
public sealed class Posture(public val size: Size) {

    /** Device posture is in tabletop mode (half open with the hinge horizontal). */
    public class TableTop(windowSize: Size) : Posture(windowSize)

    /** Device posture is in book mode (half open with the hinge vertical). */
    public class Book(windowSize: Size) : Posture(windowSize)

    /** Device posture is in normal mode. */
    public object Normal : Posture(Size(0, 0))
}
