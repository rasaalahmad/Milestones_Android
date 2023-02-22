package com.example.milestone2

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.milestone2.databinding.ActivityContactsNavigationDrawerBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsNavigationDrawerActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityContactsNavigationDrawerBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarContactsNavigationDrawer.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController =
            findNavController(R.id.nav_host_fragment_content_contacts_navigation_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about_us,R.id.nav_add_modify_contact_dialog
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        permissionCheck()


    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),100)
        }

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(POST_NOTIFICATIONS),100)
        }

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.FOREGROUND_SERVICE)
            == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.FOREGROUND_SERVICE),100)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_contacts_navigation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}