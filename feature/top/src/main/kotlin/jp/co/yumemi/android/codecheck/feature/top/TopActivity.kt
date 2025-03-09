package jp.co.yumemi.android.codecheck.feature.top

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopActivity : AppCompatActivity(R.layout.activity_top) {
    @Suppress("unused")
    private val viewModel by viewModels<TopViewModel>()
}
