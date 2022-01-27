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

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import io.getstream.butterfly.compose.LocalWindowDpSize
import io.getstream.butterfly.compose.LocalWindowLayoutInfo
import io.getstream.butterfly.compose.WindowDpSize
import io.getstream.butterfly.compose.hingeDp
import io.getstream.butterfly.findFoldingFeature
import io.getstream.butterflydemo.R
import io.getstream.butterflydemo.ui.message.MessagesActivity
import io.getstream.butterflydemo.ui.message.MessagesScreen
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen

@Composable
fun MessagingScreen(
    onBackPressed: () -> Unit
) {
    when (LocalWindowDpSize.current) {
        is WindowDpSize.Expanded -> MessagingScreenExpanded(onBackPressed = onBackPressed)
        else -> MessagingScreenRegular(onBackPressed = onBackPressed)
    }

    val foldingFeature = LocalWindowLayoutInfo.current.findFoldingFeature()
    Log.e("test", "${foldingFeature?.isSeparating ?: false}")
}

@Composable
private fun MessagingScreenExpanded(
    onBackPressed: () -> Unit
) {
    val foldingFeature = LocalWindowLayoutInfo.current.findFoldingFeature()
    val windowSize = LocalWindowDpSize.current
    val hingeSize = foldingFeature?.hingeDp ?: 0.dp
    val rowItemWidth = (windowSize.windowSize.width - hingeSize) / 2
    var selectedChannelId by rememberSaveable { mutableStateOf<String?>(null) }

    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.width(rowItemWidth)) {
            ChannelsScreen(
                title = stringResource(id = R.string.app_name),
                onItemClick = { channel -> selectedChannelId = channel.cid },
                onBackPressed = onBackPressed,
            )
        }

        Spacer(modifier = Modifier.width(hingeSize))

        MessagesScreen(
            cid = selectedChannelId,
            width = rowItemWidth,
            onBackPressed = { selectedChannelId = null }
        )
    }
}

@Composable
private fun MessagingScreenRegular(
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    ChannelsScreen(
        title = stringResource(id = R.string.app_name),
        onItemClick = { channel ->
            startActivity(context, MessagesActivity.getIntent(context, channel.cid), null)
        },
        onBackPressed = onBackPressed,
    )
}
