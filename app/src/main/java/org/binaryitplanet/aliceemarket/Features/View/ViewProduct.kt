package org.binaryitplanet.aliceemarket.Features.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_product_item.view.*
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityViewProductBinding

class ViewProduct : AppCompatActivity(), MenuItem.OnMenuItemClickListener {

    private val TAG = "ViewProduct"

    private lateinit var product: ProductUtils
    private var isSeller = false

    private lateinit var binding: ActivityViewProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_product)

        isSeller = intent!!.getBooleanExtra(Config.IS_SELLER, false)

        setupToolbar()
    }
    override fun onResume() {
        super.onResume()

        product = intent!!.getSerializableExtra(Config.PRODUCT) as ProductUtils

        setupViews()
    }

    private fun setupViews() {

        binding.toolbar.title = product.name

        Glide
            .with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher)
            .into(binding.image)
    }


    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isSeller) {
            menuInflater.inflate(R.menu.view_product_menu, menu)

            binding.toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.edit) {
                    Log.d(TAG, "Edit")
                } else if ( it.itemId == R.id.delete) {
                    //
                }
                return@setOnMenuItemClickListener true
            }
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
        overridePendingTransition(R.anim.righttoposition, R.anim.bottomtotop)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }
}