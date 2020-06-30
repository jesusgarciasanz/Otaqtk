package com.example.otaqtk.ui.edit_profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityEditProfileBinding
import com.example.otaqtk.pojo.Profile
import com.example.otaqtk.pojo.UpdatedProfile
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private val PERMISOS_INICIALES = 1
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    private lateinit var binding: ActivityEditProfileBinding
    private val otaqtkRepository: OtaqtkRepository = OtaqtkRepository()

    private lateinit var token: String
    private val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    private lateinit var storage: StorageReference
    private lateinit var  imageRef: StorageReference

    private lateinit var profile :Profile
    private lateinit var mUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile )
        binding.lifecycleOwner = this

        val value: SharedPreferences? = getSharedPreferences("user_token",Context.MODE_PRIVATE )
        token = value?.getString("token", "").toString()

        storage = FirebaseStorage.getInstance("gs://otaqtk-ba406.appspot.com/").reference.child("profiles/pictures")
        mUrl = ""
        getProfile()


        initListeners()
    }


    private fun initListeners() {
        binding.buttonBackEditProfile.setOnClickListener {
            finish()
        }
        binding.buttonSave.setOnClickListener {
            if (binding.editUsernameSettings.text.toString().isEmpty()){
                Toast.makeText(this, "completa los campos", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                editProfile()
                finish()
            }
            changePassword()
        }
        binding.circleImageView2.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //Permission denied
                    val permissions = arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }else{
                    //permissions already garantes
                    pickImageFromGallery()
                }
            }else{
                pickImageFromGallery()
            }
        }
    }

    private fun editProfile(){
      CoroutineScope(Dispatchers.Main).launch {
          val updated = UpdatedProfile (binding.editUsernameSettings.text.toString(), mUrl)
          val call = otaqtkRepository.editProfile(FirebaseAuth.getInstance().uid!!,updated, token)

          val profile = call.body()
          if (profile != null && call.code() == 200){
                Toast.makeText(this@EditProfileActivity, "Updated", Toast.LENGTH_SHORT).show()
          }
      }
    }

    private fun getProfile(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.getProfile(FirebaseAuth.getInstance().uid!!, token)
            profile = call.body()!!
            if (profile != null && call.code() == 200){
                binding.editUsernameSettings.setText(profile.username)
                Picasso.get().load(profile.profile_image).fit().centerCrop().into(binding.circleImageView2)
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE ->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    Toast.makeText(this, "permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun changePassword(){
        val user = FirebaseAuth.getInstance().currentUser
        if (!binding.editNewPasswordSettings.text.toString().isEmpty() && binding.editNewPasswordSettings.text.toString().equals(binding.editNewPasswordSettings.text.toString())){
            val newPasswod = binding.editNewPasswordSettings.text.toString()

            user?.updatePassword(newPasswod)?.addOnCompleteListener{task->
                if (task.isSuccessful){

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null){
            if(profile.profile_image != null){
                val storageRef = FirebaseStorage.getInstance("gs://otaqtk-ba406.appspot.com/").getReferenceFromUrl(profile.profile_image!!)
                storageRef.delete().addOnSuccessListener(OnSuccessListener {
                    Toast.makeText(this, "deleted Image", Toast.LENGTH_SHORT).show()
                }).addOnFailureListener {
                    Toast.makeText(this, "tus muetos", Toast.LENGTH_SHORT).show()
                }
            }
            Picasso.get().load(data.data).fit().centerCrop().into(binding.circleImageView2)
            val uri = data.data
            val fileRef = storage.child(UUID.randomUUID().toString() + ".jpg" )
            var uploadTask : StorageTask<*>
            uploadTask = fileRef.putFile(uri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downloadUrl = task.result
                    mUrl = downloadUrl.toString()
                }
            }
        }
    }
}