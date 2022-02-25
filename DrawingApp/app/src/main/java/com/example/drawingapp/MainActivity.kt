package com.example.drawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.get

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
    private val galleryResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) {
                showToast("Permission granted for the camera.")
                // start new intent to gallery
                var galleryIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                // launch gallery intent
                openGalleryResultLauncher.launch(galleryIntent)
            } else {
                showToast("Permission is not granted for the camera.")
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
        permissionName: String
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ask Again") { _, _ ->
                galleryResultLauncher.launch(permissionName)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showRationaleDialog(
                "Drawing App",
                "Drawing App need to access your external storage",
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            galleryResultLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
                // TODO - add writing external storage permission
            )
        }
    }
}