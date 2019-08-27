package com.example.meliapisdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapisdemo.adapter.ProductAdapter
import com.example.meliapisdemo.model.Product
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.UnknownHostException

class SearchFragment : Fragment() {

    private var productViewModel: ProductViewModel = ProductViewModel()
    private val products: ArrayList<Product> = ArrayList()
    private var productAdapter: ProductAdapter = ProductAdapter(context, products)
    var n = ArrayList<Product>()

    init {
        n.add(Product("dasd","no internet connection","d",23.33))
    }


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productAdapter= ProductAdapter(view.context,products)
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getProductRepository("iphone 7").observe(this, Observer { productResponse ->
            var products1 : List<Product> = when(productResponse){
                is ProductResponse.Success -> productResponse.productDTO.getProducts()
                is ProductResponse.Error -> handleException(productResponse.cause)
            }
            products.addAll(products1)
            productAdapter.notifyDataSetChanged()
        })
        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }

    }

    fun handleException(exception: Exception) : List<Product>{
        when(exception.cause){
            is UnknownHostException -> n.add(Product("da","404","tonerg",3.2))
            is IllegalArgumentException -> n.add(Product("DFA","400","dada", 2.3))
            is InternalError -> n.add(Product("da","500", "da",32.2))
        }
        return n
    }

}
