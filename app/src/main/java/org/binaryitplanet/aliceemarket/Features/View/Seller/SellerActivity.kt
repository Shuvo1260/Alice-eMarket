package org.binaryitplanet.aliceemarket.Features.View.Seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.binaryitplanet.aliceemarket.R

class SellerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)
    }



    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
    }
}