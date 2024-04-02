package com.example.chatv2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatv2.adapter.MessageAdapter
import com.example.chatv2.databinding.ActivityMainBinding
import com.example.chatv2.model.Message

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val mBinding: ActivityMainBinding get() = requireNotNull(binding)

    private val permissions =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        } else {
            listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }

    private val permissionsV2 =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        } else {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }

    private val requestCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it != null) {
            if (it.resultCode == RESULT_OK) {
                it.getData() // Intent
                it.getData().getData() //  lay data tu Intent // key search: Uri
                Toast.makeText(this@MainActivity, "ng dung da chup anh thanh cong", Toast.LENGTH_SHORT).show()

                mBinding.imgDemo.setImageURI(it.data?.data)
            } else {
                Toast.makeText(this@MainActivity, "ng dung khong chup anh nua", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val requestCameraPermissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Toast.makeText(this@MainActivity, "ng dung cap quyen camera", Toast.LENGTH_SHORT).show()
            openCamera()
        } else {
            Toast.makeText(this@MainActivity, "ng dung KHONG cap quyen camera", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestCameraMultiPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isGranted(permissions)) {
                openCamera()
            } else {
                Toast.makeText(this@MainActivity, "Ban khong cap du quyen!", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestGalleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it != null && it.resultCode == RESULT_OK) {
            Toast.makeText(this, "requestGalleryResult is call", Toast.LENGTH_SHORT).show()

            mBinding.imgDemo.setImageURI(it.data?.data) // Uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MessageAdapter(
                listOf(
                    Message(msg = "Anybody affected by coronavirus?", path = "", time = "09:93", isSelf = false),
                    Message(msg = "", path = "1", time = "09:93", isSelf = true)
                )
            )
        }

        mBinding.apply {
            btnOpenCamera.setOnClickListener {
                if (isGranted(permissions)) {
                    openCamera()
                } else {
                    requestCameraMultiPermissionResult.launch(permissions.toTypedArray())
                }
            }
            btnOpenGallery.setOnClickListener {
                if (isGranted(permissionsV2)) {
                    openGallery()
                } else {
                    requestCameraMultiPermissionResult.launch(permissions.toTypedArray())
                }
            }
        }
    }

    private fun isGranted(permissions: List<String>): Boolean {
        for (permission in permissions) {
            if (isDenied(permission)) {
                return false
            }
        }
        return true
    }

    private fun isDenied(permission: String): Boolean {
        return !isGranted(permission)
    }

    private fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        requestCameraResult.launch(intent)
    }


    private fun openGallery() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        requestGalleryResult.launch(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}