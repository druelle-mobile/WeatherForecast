package ovh.geoffrey_druelle.weatherforecast.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions.*
import timber.log.Timber.d

class MainActivity : AppCompatActivity(),
    PermissionCallbacks,
    RationaleCallbacks {


    companion object {
        lateinit var instance: MainActivity
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        viewModel = getViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initObservers()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupToolbar()

//        hasGenericPermission()
    }

    private fun setupToolbar() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashScreenFragment -> binding.toolbar.visibility = GONE
                R.id.homeFragment -> {
                    binding.toolbar.visibility = VISIBLE
                }
//                R.id.detailsFragment -> {
//                    binding.toolbar.visibility = VISIBLE
//                }
//                R.id.aboutFragment -> {
//                    binding.toolbar.visibility = VISIBLE
//                }
            }
        }
    }

    private fun initObservers() {

    }

    /*
    Init EasyPermissions checkin
     */
    private val location = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val requestCodeGenericPerm = 111

    private fun hasGenericPermission(): Boolean {
        return if (!hasPermissions(instance, *location)) {
            requestPermissions(
                instance,
                getString(R.string.rationale_example),
                requestCodeGenericPerm,
                *location
            )
            false
        } else
            true
    }

    /*
    Permissions management
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults,
            instance
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        d("onPermissionsDenied: %s : %s", requestCode, perms.size)
        if (somePermissionPermanentlyDenied(instance, perms))
            AppSettingsDialog.Builder(instance).build().show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        d("onPermissionsGranted: %s : %s", requestCode, perms.size)
    }

    override fun onRationaleDenied(requestCode: Int) {
        d("onRationaleDenied: %s", requestCode)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        d("onRationaleAccepted: %s", requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
            Toast.makeText(
                this,
                getString(
                    R.string.returned_from_settings,
                    if (hasGenericPermission()) yes
                    else no
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
