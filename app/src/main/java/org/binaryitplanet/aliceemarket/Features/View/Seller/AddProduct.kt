package org.binaryitplanet.aliceemarket.Features.View.Seller

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityAddProductBinding

class AddProduct : AppCompatActivity() {

    private val TAG = "AddProduct"

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var product: ProductUtils

    private var isEdit = false

    private var imageUrl: String? = null

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

        binding.imageCard.setOnClickListener {
            val permissions:Array<String> = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, Config.PICK_IMAGE_REQUEST_CODE)
            }
        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Config.PICK_IMAGE_REQUEST_CODE && grantResults.isNotEmpty()) {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            val intent = Intent.createChooser(galleryIntent, Config.PICK_IMAGE)
            startActivityForResult(intent, Config.PICK_IMAGE_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Config.PICK_IMAGE_REQUEST_CODE
            && data != null
            && data.data != null
        ) {
            try {
                val imageUri = data.data
                imageUrl = imageUri.toString()
                Log.d(TAG, "ImagePath: $imageUrl")
                previewImage(imageUrl!!)
            } catch (e: Exception) {
                Log.d(TAG, "Image loading failed: ${e.message}")
                Toast.makeText(
                    this,
                    Config.FAILED_MESSAGE,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
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
        if (imageUrl.isNullOrEmpty()) {
            Toast.makeText(
                    this,
                    Config.IMAGE_EMPTY_MESSAGE,
                    Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (binding.productName.text.toString().isNullOrEmpty()) {
            binding.productName.error = Config.REQUIRED_FIELD
            binding.productName.requestFocus()
            return false
        }

        if (binding.price.text.toString().isNullOrEmpty()) {
            binding.price.error = Config.REQUIRED_FIELD
            binding.price.requestFocus()
            return false
        }

        if (binding.quantity.text.toString().isNullOrEmpty()) {
            binding.quantity.error = Config.REQUIRED_FIELD
            binding.quantity.requestFocus()
            return false
        }

        return true
    }

    private fun setupView() {

        imageUrl = product.imageUrl
        previewImage(product.imageUrl)
        binding.productName.setText(product.name)
        binding.price.setText(product.price.toString())
        binding.quantity.setText(product.quantity)
        binding.unit.setText(product.unit)
        binding.sellerMessage.setText(product.sellerMessage)

    }


    private fun previewImage(imageUrl: String) {
        Glide
            .with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_broker_image)
            .into(binding.productImage)

        if (binding.addImageIcon.visibility == View.VISIBLE)
            binding.addImageIcon.visibility = View.GONE
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
                    if (isEdit){
                        //
                    } else {
                        //
                    }
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