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
import com.example.meliapisdemo.model.product.Product
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.ErrorType.*
import com.example.meliapisdemo.utils.InternetUtils
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), ProductAdapter.Comunicator{


    private var productViewModel: ProductViewModel = ProductViewModel()
    private val products: ArrayList<Product> = ArrayList()
    private lateinit var productAdapter : ProductAdapter
    private var call = true

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments!!.getString("query")
        if (savedInstanceState != null) {
            val callVM = savedInstanceState.getBoolean("vmProduct")
            call = callVM
            fetchProducts(query)
        } else {
            fetchProducts(query)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productAdapter = ProductAdapter(context!!, products, this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("vmProduct", call)
    }

    private fun fetchProducts(query: String){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val internetUtils = InternetUtils(context!!)
        internetUtils.observe(this, Observer {
            if (it && call){
                call = false
                productViewModel.getProductRepository(query).observe(this, Observer { productResponse ->
                    when(productResponse){
                        is ProductResponse.Success -> handleSuccess(productResponse.productDTO.getProducts())
                        is ProductResponse.Error -> handleException(productResponse.cause)
                    }
                })
            }else if (it && products.isEmpty()){
                productViewModel.mutableLiveData.observe(this, Observer { productResponse ->
                    when(productResponse){
                        is ProductResponse.Success -> handleSuccess(productResponse.productDTO.getProducts())
                        is ProductResponse.Error -> handleException(productResponse.cause)
                    }
                })
            }
            else if (products.isNotEmpty())handleSuccess(null)
            else handleException(NETWORK)
        })

    }

    private fun handleSuccess(list: List<Product>?){
        recyclerProducts.apply { visibility = View.VISIBLE }
        errorText.apply { visibility = View.GONE }
        if(list != null ){
            products.addAll(list)
        }
        productAdapter.notifyDataSetChanged()

        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }
    }

    private fun handleException(error: ErrorType){
        errorText.apply { visibility = View.VISIBLE }
        recyclerProducts.apply { visibility = View.GONE }
        if (error == NETWORK) {
            errorText.apply {
                text = "No hay conexion a internet"
                val drawable = resources.getDrawable(R.drawable.error)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }

        }
        else if (error == CLIENT){
            errorText.apply {
                text = "Busqueda sin resultados"
                val drawable = resources.getDrawable(R.drawable.errornotfound)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
        else if (error == SERVER){
            errorText.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
        else{
            errorText.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }

    }

    override fun sendProduct(product: Product) {

        val comunicator = context as Comunicator

        comunicator.sendProduct(product)
    }

    interface Comunicator{
        fun sendProduct(product: Product)
    }


}
