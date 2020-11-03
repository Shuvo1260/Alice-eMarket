package org.binaryitplanet.aliceemarket.Features.View.Seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.aliceemarket.Features.Components.DaggerAppComponents
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProfileUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {

    private val TAG = "EditProfile"

    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var profileViewModel: ProfileViewModelIml


    private var profile: ProfileUtils? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        setupToolbar()

        val appComponents = DaggerAppComponents.create()

        profileViewModel = appComponents.getProfileViewModel()

        setupListeners()

        profile = intent?.getSerializableExtra(Config.PROFILE) as ProfileUtils?

        setupViews()
    }

    private fun setupViews() {
        if (profile != null) {
            binding.phone.setText(profile?.phoneNumber)
            binding.location.setText(profile?.location)

            binding.phone.setSelection(profile?.phoneNumber!!.length)
        }
    }

    private fun setupListeners() {
        profileViewModel.setProfileSuccess.observe(
                this,
                {
                    if (it) {
                        Toast.makeText(
                                this,
                                Config.PROFILE_UPDATED_SUCCESSFULLY,
                                Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    }
                }
        )

        profileViewModel
                .setProfileFailed
                .observe(
                        this,
                        {
                            Toast.makeText(
                                    this,
                                    Config.PROFILE_UPDATE_FAILED,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                )
    }


    private fun setupToolbar() {
        binding.toolbar.title = Config.EDIT_PROFILE_TOOLBAR
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_product_menu, menu)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done) {
                if (validationChecker()) {
                    val profile = ProfileUtils(
                            binding.phone.text.toString(),
                            binding.location.text.toString()
                    )
                    profileViewModel.setProfile(profile)
                }
            }
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun validationChecker(): Boolean {
        if (binding.phone.text.toString().isNullOrEmpty()) {
            binding.phone.error = Config.REQUIRED_FIELD
            binding.phone.requestFocus()
            return false
        }
        if (binding.location.text.toString().isNullOrEmpty()) {
            binding.location.error = Config.REQUIRED_FIELD
            binding.location.requestFocus()
            return false
        }

        return true

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