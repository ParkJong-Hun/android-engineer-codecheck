package jp.co.yumemi.android.codecheck.feature.top

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.feature.top.databinding.ActivityTopBinding
import jp.co.yumemi.android.codecheck.presentation.extension.setStatusBarPadding

@AndroidEntryPoint
class TopActivity : AppCompatActivity() {
    @Suppress("unused")
    private val viewModel by viewModels<TopViewModel>()

    private val binding by lazy { ActivityTopBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForEdgeToEdge()
        setSupportActionBar(binding.toolbar)
        setupToolbarForNavigation()
    }

    private fun setupForEdgeToEdge() {
        binding.topLayout.setStatusBarPadding()
    }

    private fun setupToolbarForNavigation() {
        // findNavController()がちゃんと動作してないので、こうしている
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.topFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
