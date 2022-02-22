package com.example.permissiondemo

import android.Manifest
import androidx.appcompat.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
                isGranted ->
                    if (isGranted) {
                        showToast("Permission granted for the camera.")
                    } else {
                        showToast("Permission is not granted for the camera.")
                    }
                }

    private val arrayOfPermission: Array<String> = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                permission ->
                    permission.entries.forEach {
                        val permissionName = it.key
                        val isGranted = it.value

                        if (isGranted) {
                            when(permissionName){
                                Manifest.permission.CAMERA -> {
                                    showToast("Permission granted for the camera.")
                                }
                                Manifest.permission.ACCESS_FINE_LOCATION -> {
                                    showToast("Permission granted for the fine location.")
                                }
                                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                    showToast("Permission granted for the coarse location.")
                                }
                                else -> {
                                    showToast("Permission granted for the location.")
                                }
                            }
                        } else {
                            when(permissionName){
                                Manifest.permission.CAMERA -> {
                                    showToast("Permission is not granted for the camera.")
                                }
                                Manifest.permission.ACCESS_FINE_LOCATION -> {
                                    showToast("Permission is not granted for the fine location.")
                                }
                                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                    showToast("Permission is not granted for the coarse location.")
                                }
                                else -> {
                                    showToast("Permission is not granted for the location.")
                                }
                            }
                        }
                    }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById<Button>(R.id.buttonRequestPermission)
        btnCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog(
                    "Permission demo requires for the camera access",
                    "Camera can not be used because camera access is denied"
                )
            } else {
                cameraAndLocationResultLauncher.launch(arrayOfPermission)
            }
        }
    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private fun showRationaleDialog(
        title: String,
        message: String
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ask Again") { _, _ ->
                cameraResultLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showToast(title: String) {
        Toast.makeText(
            this,
            title,
            Toast.LENGTH_LONG
        ).show()
    }
}