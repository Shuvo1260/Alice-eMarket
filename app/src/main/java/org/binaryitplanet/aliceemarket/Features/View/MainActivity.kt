package org.binaryitplanet.aliceemarket.Features.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import org.binaryitplanet.aliceemarket.Features.Adapter.ProductAdapter
import org.binaryitplanet.aliceemarket.Features.Components.AppComponents
import org.binaryitplanet.aliceemarket.Features.Components.DaggerAppComponents
import org.binaryitplanet.aliceemarket.Features.View.Seller.LoginActivity
import org.binaryitplanet.aliceemarket.Features.View.Seller.SellerActivity
import org.binaryitplanet.aliceemarket.Features.ViewModel.ProductViewModelIml
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private var productList = arrayListOf<ProductUtils>()

    private lateinit var adapter: ProductAdapter

    private lateinit var productViewModel: ProductViewModelIml

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupToolbar()

        var appComponents = DaggerAppComponents.create()

        productViewModel = appComponents.getProductViewModel()

        var category = intent?.getStringExtra(Config.PRODUCT_TYPE)!!

        productViewModel.getProductList(category)

        setupListener()

        binding.addProduct.setOnClickListener {
            val intent = if (FirebaseAuth.getInstance().currentUser == null) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, SellerActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.righttoposition, R.anim.positiontoright)
        }
    }

    private fun setupListener() {
        productViewModel
                .onGetListSuccessLiveData
                .observe(
                        this,
                        {
                            productList = it as ArrayList<ProductUtils>
                            setupRecyclerView()
                        }
                )
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(this, productList, false)
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.setItemViewCacheSize(Config.CACHE_SIZE)
    }

    private fun setupToolbar() {
        binding.toolbar.title = Config.PRODUCTS
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.inflateMenu(R.menu.main_activity_menu)

        var menu = binding.toolbar.menu!!

        val searchView = menu.findItem(R.id.search_bar).actionView as SearchView

        searchView.queryHint = Config.SEARCH_HINT
        
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        Log.d(TAG, "Text: $newText")
                        adapter.filter.filter(newText)
                        return false
                    }

                }
        )
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