package org.binaryitplanet.aliceemarket.Features.View.Seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {

    private val TAG = "EditProfile"

    private lateinit var binding: ActivityEditProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        setupToolbar()
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
//                if (validationChecker()) {
//                    if (isEdit){
//                        //
//                    } else {
//                        //
//                    }
//                }
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