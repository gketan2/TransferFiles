package com.k10.transferfiles.ui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.k10.transferfiles.ui.main.MainActivity
import com.k10.transferfiles.ui.splash.SplashActivity
import com.k10.transferfiles.utils.Constants.STORAGE_PERMISSION_CODE
import java.lang.Exception


open class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        //check permission
        if (!checkStoragePermission()) {
            requestPermission()
        } else {
            if (this is SplashActivity) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
        } else {
            MaterialAlertDialogBuilder(this).setTitle("Storage Permission")
                .setMessage("For app to function properly, it requires storage permission, please provide it by going to settings.")
                .setPositiveButton("Go to Settings") { dialogInterface: DialogInterface, _: Int ->
                    try {
                        startActivity(
                            Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                                addCategory("android.intent.category.DEFAULT")
                                data = Uri.fromParts("package", packageName, null)
                            }
                        )
                    } catch (e: Exception) {
                        startActivity(
                            Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        )
                    }
                    dialogInterface.dismiss()
                }
                .setCancelable(false)
                .show()

        }
    }

    private fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            Environment.isExternalStorageManager()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                checkStoragePermission()
            }
        }
    }
}