package org.binaryitplanet.aliceemarket.Features.View.Seller

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.databinding.ActivityLoginBinding

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private val TAG = "SignIn"

    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth


    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupToolbar()

        setupProgressDialog()



        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInClientCreate()

        binding.signIn.setOnClickListener {
            signIn()
        }
    }


    // Receiving google account
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "Request code: $requestCode $resultCode $data")

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Config.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed "+ e.message)
                Toast.makeText(
                    this,
                    Config.SIGN_IN_FAILED_MESSAGE,
                    Toast.LENGTH_SHORT
                ).show()

                progressDialog.dismiss()
            }
        }
    }

    // Signing in firebase
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        progressDialog.show()

        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Toast.makeText(
                        this,
                        Config.SIGN_IN_SUCCESSFUL_MESSAGE,
                        Toast.LENGTH_SHORT
                    ).show()

                    FirebaseAuth.getInstance().currentUser?.let {
                        val intent = Intent(this, SellerActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.righttoleft, R.anim.lefttoright)
                        finish()
                    }

                    progressDialog.show()

                } else {

                    Log.d(TAG, "Firebase sign in failed "+ (task.exception?.message ?: null))
                    Toast.makeText(
                        this,
                        Config.SIGN_IN_FAILED_MESSAGE,
                        Toast.LENGTH_SHORT
                    ).show()

                    progressDialog.dismiss()
                }
            }

    }

    // Google sign in window is creating
    private fun googleSignInClientCreate() {

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        progressDialog.show()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Config.RC_SIGN_IN)
    }

    private fun setupProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setIcon(R.drawable.ic_launcher)
        progressDialog.setTitle(Config.SIGN_IN_PROGRESS_TITLE)
        progressDialog.setMessage(Config.SIGN_IN_PROGRESS_MESSAGE)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun setupToolbar() {
        binding.toolbar.title = Config.LOGIN_TOOLBAR
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "Back pressed")
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
    }
}