package org.binaryitplanet.aliceemarket.Features.Adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_product_item.view.*
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.Config
import org.binaryitplanet.aliceemarket.Utils.ProductUtils
import java.math.RoundingMode

class ProductAdapter(
    val context: Context,
    val productList: ArrayList<ProductUtils>
): RecyclerView.Adapter<ProductAdapter.ViewHolder>(), Filterable {

    private val TAG = "ProductAdapter"

    private val allProductList = arrayListOf<ProductUtils>()
    private val allCategoryProduct = arrayListOf<ProductUtils>()

    init {
        allProductList.addAll(productList)
        allCategoryProduct.addAll(productList)
    }
    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_product_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = productList.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        try {

            Glide
                .with(context)
                .load(productList[position].imageUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(view.productImage)

            view.name.text = productList[position].name
            view.price.text = Config.CURRENCY_SIGN + " " + productList[position].price
                .toBigDecimal().setScale(2, RoundingMode.UP).toString()


        } catch (e: Exception) {
            Log.d(TAG, "ExpenseViewError: ${e.message}")
        }

//
//        view.setOnClickListener {
//            val intent = Intent(context, ViewExpense::class.java)
//            intent.putExtra(Config.EXPENSE, expensesList[position])
//            context.startActivity(intent)
//            (context as Activity).overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
//        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}

    fun getCategoryFilter(): Filter{
        return object : Filter() {
            private val filterResult = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var filterList = arrayListOf<ProductUtils>()

                Log.d(TAG, "CategoryFilter")
                if (constraint == Config.PRODUCT_CATEGORIES[0]) {
                    filterList.addAll(allProductList)
                } else {
                    allProductList.forEach {
                        if (it.category?.toLowerCase()?.contains(constraint.toString().toLowerCase())!!) {
                            filterList.add(it)
                        }
                    }
                }
                allCategoryProduct.clear()
                allCategoryProduct.addAll(filterList)

                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productList.clear()
                productList.addAll(results?.values as ArrayList<ProductUtils>)
                notifyDataSetChanged()
            }

        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            private val filterResult = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var filterList = arrayListOf<ProductUtils>()

                Log.d(TAG, "Filter")
                if (constraint.isNullOrEmpty()) {
                    filterList.addAll(allCategoryProduct)
                } else {
                    allCategoryProduct.forEach {
                        if (it.name?.toLowerCase()?.contains(constraint.toString().toLowerCase())!!) {
                            filterList.add(it)
                        }
                    }
                }

                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productList.clear()
                productList.addAll(results?.values as ArrayList<ProductUtils>)
                notifyDataSetChanged()
            }

        }
    }
}