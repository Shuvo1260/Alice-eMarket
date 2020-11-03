package org.binaryitplanet.aliceemarket.Features.View.Seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.binaryitplanet.aliceemarket.Features.Adapter.ProductAdapter
import org.binaryitplanet.aliceemarket.Features.Components.DaggerAppComponents
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModel
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProfileViewModelIml
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.Utils.ProfileUtils
import org.binaryitplanet.aliceemarket.databinding.ActivitySellerBinding

class SellerActivity : AppCompatActivity() {

    private val TAG = "SellerActivity"

    private lateinit var binding: ActivitySellerBinding
    private lateinit var currentUser: FirebaseUser

    private var productList = arrayListOf<ProductUtils>()

    private lateinit var adapter: ProductAdapter

    private lateinit var profileViewModel: ProfileViewModelIml

    private var profileUtls: ProfileUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_seller)

        currentUser = FirebaseAuth.getInstance().currentUser!!
        setupToolbar()

        binding.addProduct.setOnClickListener {
            if (profileUtls == null) {
                Snackbar
                        .make(
                                binding.addProduct,
                                Config.COMPLETE_PROFILE_MESSAGE,
                                Snackbar.LENGTH_LONG
                        ).setAction(
                                Config.SET_PROFILE
                        ) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
                        }
                        .setActionTextColor(ContextCompat.getColor(this, R.color.robinsEggBlue))
                        .show()

            } else {
                val intent = Intent(this, AddProduct::class.java)
                intent.putExtra(Config.IS_EDIT, false)
                startActivity(intent)
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
            }
        }

        val appComponents = DaggerAppComponents.create()

        profileViewModel = appComponents.getProfileViewModel()

        setupListeners()

        profileViewModel.getProfile()

    }

    private fun setupListeners() {
        profileViewModel.getProfileSuccess.observe(
                this,
                {
                    profileUtls = it
                }
        )

        profileViewModel.getProfileFailed.observe(
                this,
                {
                    Log.d(TAG, "ProfileFetchingFailed")
                }
        )
    }

    override fun onResume() {
        super.onResume()
        dummyData()
    }

    private fun dummyData() {
        productList.clear()
        productList.add(ProductUtils("1", "Product1", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Fruit", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "New Paltan, Azimpur, Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product2", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product3", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product4", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Vegetable", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product5", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Fruit", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product6", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Piece","Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(this, productList, true)
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.setItemViewCacheSize(Config.CACHE_SIZE)
    }

    // Method to sign out
    private fun signOut() {
        Log.d(TAG, "Sign out")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        if (googleSignInClient != null) {
            googleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.title = currentUser.displayName
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.seller_activity_menu, menu)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.profile) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
            } else if ( it.itemId == R.id.logout) {
                signOut()
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
        overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
    }
}