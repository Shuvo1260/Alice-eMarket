package org.binaryitplanet.aliceemarket.Features.View

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
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import org.binaryitplanet.aliceemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private var productList = arrayListOf<ProductUtils>()

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupToolbar()
        setupCategory()

        dummyData()

        binding.addProduct.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                //
            } else {
                //
            }
        }
    }

    private fun setupCategory() {
        binding.categorySelection.setText(Config.PRODUCT_CATEGORIES[0])
        val dropDownAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Config.PRODUCT_CATEGORIES
        )

        binding.categorySelection.setAdapter(dropDownAdapter)

        binding.categorySelection.setOnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "ClickedPosition: $position")
            adapter.getCategoryFilter().filter(Config.PRODUCT_CATEGORIES[position])
        }
    }

    private fun dummyData() {
        productList.clear()
        productList.add(ProductUtils("1", "Product1", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Fruit", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product2", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product3", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product4", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Vegetable", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product5", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Fruit", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))
        productList.add(ProductUtils("1", "Product6", "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg", 12.304, "Poultry", "5", "Hasibul", "123456789", "email@gmail.com", "Dhaka, Bangladesh", "This is a text message."))

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(this, productList, false)
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.setItemViewCacheSize(Config.CACHE_SIZE)
    }

    private fun setupToolbar() {
        binding.toolbar.title = Config.PRODUCTS

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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
    }
}