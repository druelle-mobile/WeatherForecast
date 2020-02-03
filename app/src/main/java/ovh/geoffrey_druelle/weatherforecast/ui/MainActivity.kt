package ovh.geoffrey_druelle.weatherforecast.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ovh.geoffrey_druelle.weatherforecast.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.*
import timber.log.Timber
import timber.log.Timber.*

class MainActivity : AppCompatActivity(),
    PermissionCallbacks,
    RationaleCallbacks {

    // Example
    private val location = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val requestCodeGenericPerm = 111

    companion object {
        lateinit var instance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this

        hasGenericPermission()
    }

    /*
    Init EasyPermissions checkin
     */
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
        onRequestPermissionsResult(requestCode, permissions, grantResults, instance)
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
