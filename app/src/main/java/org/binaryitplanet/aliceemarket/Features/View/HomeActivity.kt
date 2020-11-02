package org.binaryitplanet.aliceemarket.Features.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import org.binaryitplanet.aliceemarket.Features.View.Seller.LoginActivity
import org.binaryitplanet.aliceemarket.Features.View.Seller.SellerActivity
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "HomeActivity"

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.fruits.setOnClickListener(this)
        binding.livestock.setOnClickListener(this)
        binding.poultry.setOnClickListener(this)
        binding.vegetable.setOnClickListener(this)

        binding.addProduct.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.addProduct) {
            val intent = if (FirebaseAuth.getInstance().currentUser == null) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, SellerActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            if (v?.id == R.id.fruits)
                intent.putExtra(Config.PRODUCT_TYPE, Config.CATEGORIES[0])
            else if (v?.id == R.id.livestock)
                intent.putExtra(Config.PRODUCT_TYPE, Config.CATEGORIES[1])
            else if (v?.id == R.id.poultry)
                intent.putExtra(Config.PRODUCT_TYPE, Config.CATEGORIES[2])
            else if (v?.id == R.id.vegetable)
                intent.putExtra(Config.PRODUCT_TYPE, Config.CATEGORIES[3])
            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }
}