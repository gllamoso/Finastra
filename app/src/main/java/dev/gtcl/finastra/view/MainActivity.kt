package dev.gtcl.finastra.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import dev.gtcl.finastra.R

class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(this, navController)
        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            val enableBackButton = navHostFragment.childFragmentManager.backStackEntryCount > 0
            supportActionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
            supportActionBar?.setHomeButtonEnabled(enableBackButton)
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()

}