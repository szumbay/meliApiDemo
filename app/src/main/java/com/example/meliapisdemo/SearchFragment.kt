package com.example.meliapisdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapisdemo.adapter.ProductAdapter
import com.example.meliapisdemo.model.Product
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var productViewModel: ProductViewModel = ProductViewModel()
    private val products: ArrayList<Product> = ArrayList()
    private var productAdapter: ProductAdapter = ProductAdapter(context, products)


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productAdapter= ProductAdapter(view.context,products)
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getProductRepository().observe(this, Observer { productResponse ->
            var products1 :List<Product> = productResponse.getProducts()
            products.addAll(products1)
            productAdapter.notifyDataSetChanged()
        })
        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }

    }

}
