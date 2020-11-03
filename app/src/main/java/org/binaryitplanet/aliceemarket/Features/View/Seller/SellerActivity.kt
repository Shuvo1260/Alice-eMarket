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
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProductViewModelIml
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

    private lateinit var productViewModel: ProductViewModelIml

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
                intent.putExtra(Config.PROFILE, profileUtls)
                startActivity(intent)
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
            }
        }

        val appComponents = DaggerAppComponents.create()

        profileViewModel = appComponents.getProfileViewModel()
        productViewModel = appComponents.getProductViewModel()

        productViewModel.getProductListByUserId()

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

        productViewModel.onGetListByUserIdSuccessLiveData
                .observe(
                        this,
                        {
                            productList = it as ArrayList<ProductUtils>
                            setupRecyclerView()
                        }
                )
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