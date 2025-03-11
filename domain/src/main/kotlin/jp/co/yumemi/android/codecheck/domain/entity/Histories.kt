package jp.co.yumemi.android.codecheck.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Histories(
    val histories: List<History>
) : Parcelable