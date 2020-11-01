package org.binaryitplanet.aliceemarket.Features.View.Seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityAddProductBinding

class AddProduct : AppCompatActivity() {

    private val TAG = "AddProduct"

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var product: ProductUtils

    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)

        isEdit = intent?.getBooleanExtra(Config.IS_EDIT, false)!!
        if (isEdit) {
            product = intent?.getSerializableExtra(Config.PRODUCT) as ProductUtils
            setupView()
        }

        setupDropDowns()
        setupToolbar()

    }

    private fun setupDropDowns() {

        if (isEdit) {
            binding.category.setText(product.category)
            binding.unit.setText(product.unit)
        }
        else {
            binding.category.setText(Config.CATEGORIES[0])
            binding.unit.setText(Config.UNITS[0])
        }

        val categoryAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Config.CATEGORIES
        )

        binding.category.setAdapter(categoryAdapter)

        val unitAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Config.UNITS
        )
        binding.unit.setAdapter(unitAdapter)
    }


    private fun validationChecker(): Boolean {
        return true
    }

    private fun setupView() {
        binding.productName.setText(product.name)
        binding.price.setText(product.price.toString())
        binding.quantity.setText(product.quantity)
        binding.unit.setText(product.unit)
        binding.sellerMessage.setText(product.sellerMessage)

    }


    private fun setupToolbar() {
        if (isEdit) {
            binding.toolbar.title = Config.EDIT_PRODUCT_TOOLBAR
        } else {
            binding.toolbar.title = Config.ADD_PRODUCT_TOOLBAR
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_product_menu, menu)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.done) {
                if (validationChecker()) {
                    //
                }
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