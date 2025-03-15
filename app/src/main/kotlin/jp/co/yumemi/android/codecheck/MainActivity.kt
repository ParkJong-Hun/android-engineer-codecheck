package jp.co.yumemi.android.codecheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.databinding.ActivityMainBinding
import jp.co.yumemi.android.codecheck.presentation.extension.setStatusBarPadding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForEdgeToEdge()
        setSupportActionBar(binding.toolbar)
        setupToolbarForNavigation()
    }

    private fun setupForEdgeToEdge() {
        binding.mainLayout.setStatusBarPadding()
    }

    private fun setupToolbarForNavigation() {
        // findNavController()がちゃんと動作してないので、こうしている
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
