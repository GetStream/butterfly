package io.getstream.butterfly.compose.internal

import android.content.res.Resources
import android.util.DisplayMetrics

/** Returns dp size from a pixel size. */
@PublishedApi
internal val Int.px2dp: Int
    @JvmSynthetic inline get() =
        this / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)