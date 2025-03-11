package jp.co.yumemi.android.codecheck.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class History(
    val id: String,
    val openedDateTime: LocalDateTime,
    val openedSearchedRepository: SearchedRepository,
) : Parcelable
