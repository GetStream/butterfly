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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import io.getstream.butterfly.compose.LocalWindowDpSize
import io.getstream.butterfly.compose.LocalWindowLayoutInfo
import io.getstream.butterfly.compose.rememberWindowDpSize
import io.getstream.butterfly.compose.windowLayoutInfoState
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatTheme {
                CompositionLocalProvider(
                    LocalWindowDpSize provides rememberWindowDpSize(),
                    LocalWindowLayoutInfo provides windowLayoutInfoState.value
                ) {
                    MessagingScreen(onBackPressed = { finish() })
                }
            }
        }
    }
}
