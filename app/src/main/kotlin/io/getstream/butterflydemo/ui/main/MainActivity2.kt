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

package io.getstream.butterflydemo.ui.main

import android.os.Bundle
import android.util.Log
import androidx.window.layout.WindowLayoutInfo
import io.getstream.butterfly.Posture
import io.getstream.butterfly.WindowInfoActivity
import io.getstream.butterfly.findFoldingFeature
import io.getstream.butterfly.toPosture

class MainActivity2 : WindowInfoActivity() {

    private val tag: String = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "windowSize: $windowSize")
    }

    override fun onWindowLayoutInfoUpdated(windowLayoutInfo: WindowLayoutInfo) {
        val foldingFeature = windowLayoutInfo.displayFeatures.findFoldingFeature() ?: return
        when (val posture = foldingFeature.toPosture()) {
            Posture.Normal -> Log.d(tag, "[Posture.Normal] ${posture.size}")
            is Posture.TableTop -> Log.d(tag, "[Posture.TableTop] ${posture.size}")
            is Posture.Book -> Log.d(tag, "[Posture.Book] ${posture.size}")
        }
    }
}
