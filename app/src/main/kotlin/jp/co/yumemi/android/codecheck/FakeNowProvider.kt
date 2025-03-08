package jp.co.yumemi.android.codecheck

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FakeNowProvider : NowProvider {
    override fun localTimeNow(): LocalTime {
        return fakeLocalTimeNow
    }

    override fun localDateNow(): LocalDate {
        return fakeLocalDateNow
    }

    override fun localDateTimeNow(): LocalDateTime {
        return fakeLocalDateTimeNow
    }

    companion object {
        val fakeLocalDateTimeNow = LocalDateTime.of(
            2025,
            3,
            8,
            16,
            36,
            22,
            0
        )
        val fakeLocalDateNow = fakeLocalDateTimeNow.toLocalDate()
        val fakeLocalTimeNow = fakeLocalDateTimeNow.toLocalTime()
    }
}
