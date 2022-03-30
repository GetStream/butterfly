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

package io.getstream.butterflydemo.ui.message

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.getstream.butterfly.compose.WindowOrientation
import io.getstream.butterflydemo.R
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.common.state.Reply
import io.getstream.chat.android.compose.state.imagepreview.ImagePreviewResultType
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

@Composable
fun MessagesScreen(
    cid: String?,
    itemSize: Dp,
    windowOrientation: WindowOrientation,
    onBackPressed: () -> Unit = {},
) {
    if (cid != null) {
        val context = LocalContext.current
        val factory = MessagesViewModelFactory(
            context = context,
            channelId = cid,
            enforceUniqueReactions = true,
            messageLimit = 30
        )
        val listViewModel = factory.create(MessageListViewModel::class.java)
        val composerViewModel = factory.create(MessageComposerViewModel::class.java)
        val attachmentsPickerViewModel = factory.create(AttachmentsPickerViewModel::class.java)
        val messageMode = listViewModel.messageMode

        val connectionState by listViewModel.connectionState.collectAsState()
        val user by listViewModel.user.collectAsState()

        val backAction = {
            val isInThread = listViewModel.isInThread
            val isShowingOverlay = listViewModel.isShowingOverlay
            when {
                attachmentsPickerViewModel.isShowingAttachments -> attachmentsPickerViewModel.changeAttachmentState(
                    false
                )
                isShowingOverlay -> listViewModel.selectMessage(null)
                isInThread -> {
                    listViewModel.leaveThread()
                    composerViewModel.leaveThread()
                }
                else -> onBackPressed()
            }
        }

        BackHandler(enabled = true, onBack = backAction)

        Box(
            modifier = Modifier.apply {
                if (windowOrientation == WindowOrientation.ORIENTATION_LANDSCAPE) {
                    width(itemSize)
                } else {
                    height(itemSize)
                }
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    MessageListHeader(
                        modifier = Modifier.height(56.dp),
                        channel = listViewModel.channel,
                        currentUser = user,
                        typingUsers = listViewModel.typingUsers,
                        connectionState = connectionState,
                        messageMode = messageMode,
                        onBackPressed = backAction,
                    )
                },
                bottomBar = {
                    MessageComposer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.Center),
                        viewModel = composerViewModel,
                        onAttachmentsClick = { attachmentsPickerViewModel.changeAttachmentState(true) },
                        onCommandsClick = { composerViewModel.toggleCommandsVisibility() },
                        onCancelAction = {
                            listViewModel.dismissAllMessageActions()
                            composerViewModel.dismissMessageActions()
                        }
                    )
                }
            ) {
                MessageList(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ChatTheme.colors.appBackground)
                        .padding(it),
                    viewModel = listViewModel,
                    onThreadClick = { message ->
                        composerViewModel.setMessageMode(MessageMode.MessageThread(message))
                        listViewModel.openMessageThread(message)
                    },
                    onImagePreviewResult = { result ->
                        when (result?.resultType) {
                            ImagePreviewResultType.QUOTE -> {
                                val message = listViewModel.getMessageWithId(result.messageId)

                                if (message != null) {
                                    composerViewModel.performMessageAction(Reply(message))
                                }
                            }

                            ImagePreviewResultType.SHOW_IN_CHAT -> {
                                listViewModel.focusMessage(result.messageId)
                            }
                            null -> Unit
                        }
                    }
                )
            }
        }
    } else {
        EmptyContent()
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier
            .background(color = ChatTheme.colors.appBackground)
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.search_no_results),
            style = ChatTheme.typography.bodyBold,
            color = ChatTheme.colors.textHighEmphasis,
            textAlign = TextAlign.Center
        )
    }
}
