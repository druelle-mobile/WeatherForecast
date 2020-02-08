package ovh.geoffrey_druelle.weatherforecast.ui.main

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.core.BaseActivity
import ovh.geoffrey_druelle.weatherforecast.databinding.ActivityMainBinding
import ovh.geoffrey_druelle.weatherforecast.utils.extension.hide
import ovh.geoffrey_druelle.weatherforecast.utils.extension.show
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions.*
import timber.log.Timber.d

class MainActivity : BaseActivity<ActivityMainBinding>(),
    PermissionCallbacks,
    RationaleCallbacks {


    companion object {
        lateinit var instance: MainActivity
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        setupToolbar()

//        hasGenericPermission()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> binding.toolbar.hide()
                R.id.homeFragment -> {
                    binding.toolbar
                }
//                R.id.aboutFragment -> {
//                    binding.toolbar.show()
//                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        setSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setSearchView(menu: Menu?) {
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchView.onActionViewCollapsed()
                Toast.makeText(instance, "Looking for $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(instance, "Looking for $newText", Toast.LENGTH_SHORT).show()
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
            Init EasyPermissions checking
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
