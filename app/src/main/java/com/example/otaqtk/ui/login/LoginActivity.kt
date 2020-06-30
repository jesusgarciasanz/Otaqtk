package com.example.otaqtk.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityLoginBinding
import com.example.otaqtk.ui.home.HomeActivity
import com.example.otaqtk.ui.register.RegisterActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private val otaqtkRepository: OtaqtkRepository = OtaqtkRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FacebookSdk.sdkInitialize(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        val googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignIn)
        callbackManager = CallbackManager.Factory.create()

        auth = FirebaseAuth.getInstance()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonGotoRegister.setOnClickListener {
            goToRegister()
        }
        binding.buttonLogin.setOnClickListener {
            doLogin()
        }
        binding.buttonLoginGoogle.setOnClickListener {
            signInWithGoogle()
        }
        binding.buttonLoginFacebook.setOnClickListener {
            //signInWithFAcebook()
        }

        binding.buttonLoginAnonymous.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun doLogin() {
        binding.buttonLogin.isEnabled = false

        if (binding.editTextUsername.text.toString().isEmpty()) {
            binding.editTextUsername.error = "Please enter a email"
            binding.editTextUsername.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextUsername.text.toString()).matches()) {
            binding.editTextUsername.error = "Please enter a valid email"
            binding.editTextUsername.requestFocus()
            return
        }
        if (binding.editTextPassword.text.toString().isEmpty()) {
            binding.editTextPassword.error = "Please enter a Password"
            binding.editTextPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(
            binding.editTextUsername.text.toString(),
            binding.editTextPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("login", "signInWithEmail:success")
                    getLoginToken()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this,
                        "Failed to login, are the email and password correct?",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun getLoginToken(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.loginToken(FirebaseAuth.getInstance().currentUser!!.uid)
            val token = call.body()
            if(token != null && call.code() == 200){
                val pref = getSharedPreferences("user_token", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("token", token?.token)
                editor.apply()
            }
        }
    }

    //LOGIN PERSISTENTE
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            binding.buttonLogin.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
        //callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

            }
    }


    /*    private fun signInWithFAcebook(){
        binding.buttonLoginFacebook.setReadPermissions("email", "public_profile")
        binding.buttonLoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })
    }*/

   /* private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
    }*/

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}