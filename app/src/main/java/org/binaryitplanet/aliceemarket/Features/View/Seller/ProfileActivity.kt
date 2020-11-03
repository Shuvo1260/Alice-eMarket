package org.binaryitplanet.aliceemarket.Features.View.Seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.binaryitplanet.aliceemarket.Features.Components.DaggerAppComponents
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"

    private lateinit var binding: ActivityProfileBinding

    private lateinit var currentUser: FirebaseUser

    private lateinit var profileViewModel: ProfileViewModelIml

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        currentUser = FirebaseAuth.getInstance().currentUser!!
        setupToolbar()

        val appComponents = DaggerAppComponents.create()

        profileViewModel = appComponents.getProfileViewModel()

        setupListeners()

        profileViewModel.getProfile()
    }

    override fun onResume() {
        super.onResume()

        setViews()
    }



    private fun setupListeners() {
        profileViewModel.getProfileSuccess.observe(
                this,
                {
                    binding.phone.text = it?.phoneNumber
                    binding.location.text = it?.location
                }
        )

        profileViewModel.getProfileFailed.observe(
                this,
                {
                    Log.d(TAG, "ProfileFetchingFailed")
                }
        )
    }


    private fun setViews() {
        Glide
            .with(this)
            .load(currentUser.photoUrl)
            .placeholder(R.drawable.ic_broker_image)
            .into(binding.profilePicture)
        binding.email.text = currentUser.email
    }


    private fun setupToolbar() {

        binding.toolbar.title = currentUser.displayName
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_activity_menu, menu)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.edit) {
                val intent = Intent(this, EditProfile::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
            }
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "Back pressed")
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }
}