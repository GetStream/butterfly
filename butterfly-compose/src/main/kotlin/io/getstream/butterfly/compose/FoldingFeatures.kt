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