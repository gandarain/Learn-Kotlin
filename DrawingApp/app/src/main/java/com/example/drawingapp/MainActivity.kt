package com.example.drawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    // activity for select image from gallery
    private val openGalleryResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                var imageViewBackground = findViewById<ImageView>(R.id.imageViewBackground)
                imageViewBackground.setImageURI(result.data?.data)
            } else {
                showToast("Please select an image.")
            }
        }

    // permission for read gallery
    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value

                // if permission is granted show a toast and perform operation
                if (isGranted ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()

                    val pickIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    openGalleryResultLauncher.launch(pickIntent)
                } else {
                    if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                        Toast.makeText(
                            this@MainActivity,
                            "Oops you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }

    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawingView)
        drawingView?.setSizeForBrush(20.toFloat())

        // get item at index 1 on the linear layout
        val linearLayout = findViewById<LinearLayout>(R.id.paintColors)
        mImageButtonCurrentPaint = linearLayout[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_selected)
        )

        var brushPicker: ImageButton = findViewById(R.id.imageButtonBrushPicker)

        brushPicker.setOnClickListener {
            showBrushChooserDialog()
        }

        var galleryImageButton: ImageButton = findViewById(R.id.imageButtonImagePicker)
        galleryImageButton.setOnClickListener {
            requestStoragePermission()
        }

        var undoImageButton: ImageButton = findViewById(R.id.imageButtonUndo)
        undoImageButton.setOnClickListener {
            drawingView?.onUndoHandler()
        }

        var redoImageButton: ImageButton = findViewById(R.id.imageButtonRedo)
        redoImageButton.setOnClickListener {
            drawingView?.onRedoHandler()
        }

        var saveImageButton: ImageButton = findViewById(R.id.imageButtonSave)
        saveImageButton.setOnClickListener {
            if (isReadStorageAllowed()) {
                lifecycleScope.launch {
                    val frameLayout: FrameLayout = findViewById(R.id.frameLayout)
                    saveBitmapFile(getBitmapFromView(frameLayout))
                }
            } else {
                requestStoragePermission()
            }
        }
    }

    private fun showBrushChooserDialog() {
        val brushDialog: Dialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size: ")

        val smallBrush = brushDialog.findViewById<View>(R.id.imageButtonSmallBrush)
        smallBrush.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }

        val mediumBrush = brushDialog.findViewById<View>(R.id.imageButtonMediumBrush)
        mediumBrush.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()

        val largeBrush = brushDialog.findViewById<View>(R.id.imageButtonLargeBrush)
        largeBrush.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
    }

    fun palletClick(view: View) {
        if (view !== mImageButtonCurrentPaint) {
            // get the color
            val selectedImageColor = view as ImageButton
            val selectedColor = selectedImageColor.tag.toString()

            // set the selected color
            drawingView?.setColor(selectedColor)

            // set the selected color image to pallet selected
            selectedImageColor.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_selected)
            )

            // set the global selected color image to pallet normal
            mImageButtonCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )

            mImageButtonCurrentPaint = view
        }
    }

    private fun showToast(title: String) {
        Toast.makeText(
            this,
            title,
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private fun showRationaleDialog(
        title: String,
        message: String,
        permissions: Array<String>
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ask Again") { _, _ ->
                requestPermission.launch(permissions)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun isReadStorageAllowed(): Boolean {
        var result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        /*
          if (result == PackageManager.PERMISSION_GRANTED) {
            return true
          } else {
            return false
          }
        */
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        // Check if the permission was denied and show rationale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
            // call the rationale dialog to tell the user why they need to allow permission request
            showRationaleDialog(
                "Drawing App",
                "Drawing App " + "needs to Access Your Storage",
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        } else {
            // You can directly ask for the permission.
            // if it has not been denied then request for permission
            // The registered ActivityResultCallback gets the result of this request.
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val resultBitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(resultBitmap)
        val backgroundImage = view.background

        // check the background
        if (backgroundImage != null) {
            backgroundImage.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)
        return resultBitmap
    }

    private suspend fun saveBitmapFile(bitmap: Bitmap?): String {
        var result = ""

        withContext(Dispatchers.IO) {
            if (bitmap != null) {
                try {
                    // get the bytes file and compress it
                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

                    // generate file location and name
                    val file = File(externalCacheDir?.absolutePath.toString() +
                            File.separator + "DrawingApp" +
                            System.currentTimeMillis() / 1000 +
                            ".png"
                    )

                    // create file output stream
                    val fileOutput = FileOutputStream(file)
                    fileOutput.write(bytes.toByteArray())
                    fileOutput.close()

                    // assign the result
                    result = file.absolutePath

                    runOnUiThread{
                        if (result.isNotEmpty()) {
                            showToast("File save successfully: $result")
                        } else {
                            showToast("Something when wrong while saving the file")
                        }
                    }
                } catch (e: Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }

        return result
    }
}