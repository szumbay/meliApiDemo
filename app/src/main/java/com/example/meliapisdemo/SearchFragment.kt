package com.example.meliapisdemo


import android.content.Context
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
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.ErrorType.*
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.errorBackground
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private var productViewModel: ProductViewModel = ProductViewModel()
    private val products: ArrayList<Product> = ArrayList()
    private var productAdapter = ProductAdapter(context, products)



    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments!!.getString("query")
        fetchProducts(query!!)
    }

    private fun fetchProducts(query: String){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getProductRepository(query).observe(this, Observer { productResponse ->
            when(productResponse){
                is ProductResponse.Success -> handleSuccess(productResponse.productDTO.getProducts())
                is ProductResponse.Error -> handleException(productResponse.cause)
            }
        })
    }

    private fun handleSuccess(list: List<Product>){
        errorBackground.apply { visibility = View.GONE }
        products.addAll(list)
        productAdapter.notifyDataSetChanged()
        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }
    }

    private fun handleException(error: ErrorType){
        recyclerProducts.apply { visibility = View.GONE }
        if (error == NETWORK) {
            errorImage.setImageResource(R.drawable.error)
            errorText.text = "No hay conexion a internet"
        }
        else if (error == CLIENT){
            errorImage.setImageResource(R.drawable.errornotfound)
            errorText.text = "Busqueda sin resultados"
        }
        else if (error == SERVER) errorImage.setImageResource(R.drawable.errorserver)
        else errorImage.setImageResource(R.drawable.errorserver)

    }


}
