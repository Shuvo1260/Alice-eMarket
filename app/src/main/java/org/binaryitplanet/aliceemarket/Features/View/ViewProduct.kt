package org.binaryitplanet.aliceemarket.Features.View

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_product_item.view.*
import org.binaryitplanet.aliceemarket.Features.Components.DaggerAppComponents
import org.binaryitplanet.aliceemarket.Features.View.Seller.AddProduct
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProductViewModelIml
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityViewProductBinding
import java.lang.Exception
import java.math.RoundingMode

@Suppress("DEPRECATION")
class ViewProduct : AppCompatActivity() {

    private val TAG = "ViewProduct"

    private lateinit var product: ProductUtils
    private var isSeller = false

    private lateinit var binding: ActivityViewProductBinding

    private lateinit var productViewModel: ProductViewModelIml

    private lateinit var progressDialog: ProgressDialog

    private var deleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.greanPeaTransparent)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR //For setting material color into black of the navigation bar
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_product)

        isSeller = intent!!.getBooleanExtra(Config.IS_SELLER, false)
        product = intent!!.getSerializableExtra(Config.PRODUCT) as ProductUtils



        var appComponents = DaggerAppComponents.create()

        productViewModel = appComponents.getProductViewModel()

        setupToolbar()

        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (!deleted)
            productViewModel.getProduct(product.id)
    }

    private fun setupListeners() {
        binding.phone.setOnClickListener {
            openPhone()
        }

        binding.email.setOnClickListener {
            openEmail()
        }

        binding.location.setOnClickListener {
            openLocation()
        }

        productViewModel.onGetProductSuccessLiveData
                .observe(
                        this,
                        {
                            product = it
                            setupViews()
                        }
                )

        productViewModel.onDeleteSuccessLiveData
                .observe(
                        this,
                        {
                            if (it) {
                                Toast.makeText(
                                        this,
                                        Config.PRODUCT_DELETED_SUCCESSFULLY,
                                        Toast.LENGTH_SHORT
                                ).show()
                                progressDialog.dismiss()
                                onBackPressed()
                            }
                        }
                )

        productViewModel.onDeleteFailedLiveData
                .observe(
                        this,
                        {
                            Toast.makeText(
                                    this,
                                    Config.PRODUCT_DELETION_FAILED,
                                    Toast.LENGTH_SHORT
                            ).show()
                            progressDialog.dismiss()
                            deleted = false
                        }
                )
    }

    private fun openLocation() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://maps.google.com/maps?q=loc:${product.sellerLocation}")
        startActivity(intent)
        overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
    }

    private fun openEmail() {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type = "message/rfc822"
            intent.putExtra(
                Intent.EXTRA_EMAIL, arrayOf(
                    product.sellerEmail
                )
            )
            startActivity(Intent.createChooser(intent, Config.CHOOSE_EMAIL_MESSAGE))
            overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                Config.OPEN_EMAIL_FAILED_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "OpenEmailException: ${e.message}")
        }
    }

    private fun openPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${product.sellerPhone}")
        startActivity(intent)
        overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
    }


    private fun deleteProduct() {

        progressDialog = ProgressDialog(this)
        progressDialog.setIcon(R.mipmap.ic_launcher)
        progressDialog.setTitle(Config.PRODUCT_DELETING_TITLE)
        progressDialog.setMessage(Config.PLEASE_WAIT)

        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage(Config.PRODUCT_DELETE_TITLE)

        builder.setPositiveButton(
                Config.YES,

        ){ dialog, which ->
            progressDialog.show()
            deleted = true
            productViewModel
                    .deleteProduct(product)
        }

        builder.setNegativeButton(
                Config.NO
        ) { dialog, which ->}

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun setupViews() {

        binding.toolbar.title = product.name

        Glide
            .with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher)
            .into(binding.image)

        binding.productName.text = product.name
        binding.category.text = product.category
        binding.price.text = product.price
            .toBigDecimal().setScale(2, RoundingMode.UP).toString() +
                " " + Config.CURRENCY_SIGN
        binding.quantity.text = product.quantity + " " + product.unit

        binding.sellerName.text = product.sellerName
        binding.sellerPhone.text = product.sellerPhone
        binding.sellerEmail.text = product.sellerEmail
        binding.sellerLocation.text = product.sellerLocation
        binding.sellerMessage.text = product.sellerMessage
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
                    val intent = Intent(this, AddProduct::class.java)
                    intent.putExtra(Config.IS_EDIT, true)
                    intent.putExtra(Config.PRODUCT, product)
                    startActivity(intent)
                    overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
                } else if ( it.itemId == R.id.delete) {
                    deleteProduct()
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
}