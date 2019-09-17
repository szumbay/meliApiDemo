package com.example.meliapisdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapisdemo.adapter.ProductAdapter
import com.example.meliapisdemo.model.product.Product
import com.example.meliapisdemo.model.product.ProductResponse
import com.example.meliapisdemo.networking.repos.ProductRepository
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.ErrorType.*
import com.example.meliapisdemo.utils.InternetLiveData
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), ProductAdapter.Comunicator {


    private lateinit var internetLiveData: InternetLiveData
    private lateinit var productViewModel: ProductViewModel
    private val products: ArrayList<Product> = ArrayList()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internetLiveData = InternetLiveData(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productAdapter = ProductAdapter(context!!, products, this)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val query = arguments!!.getString("query")
        fetchProducts(query)
    }

    private fun fetchProducts(query: String) {
        internetLiveData.observe(this, Observer {
            if (it && products.isEmpty()){
                productViewModel.getProductRepository(query).observe(this, Observer { productResponse ->
                    when (productResponse) {
                        is ProductResponse.Success -> handleSuccess(productResponse.productDTO.getProducts())
                        is ProductResponse.Error -> handleException(productResponse.cause)
                    }
                })
            }else if(products.isNotEmpty()){handleSuccess(null)}
            else {handleException(NETWORK)}
        })
    }

    private fun handleSuccess(list: List<Product>?) {
        recyclerProducts.apply { visibility = View.VISIBLE }
        errorText.apply { visibility = View.GONE }
        if (list != null) {
            products.addAll(list)
        }
        productAdapter.notifyDataSetChanged()

        recyclerProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }
    }

    private fun handleException(error: ErrorType) {
        errorText.apply { visibility = View.VISIBLE }
        recyclerProducts.apply { visibility = View.GONE }
        if (error == NETWORK) {
            errorText.apply {
                text = "No hay conexion a internet"
                val drawable = resources.getDrawable(R.drawable.error)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }

        } else if (error == CLIENT) {
            errorText.apply {
                text = "Busqueda sin resultados"
                val drawable = resources.getDrawable(R.drawable.errornotfound)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
        } else if (error == SERVER) {
            errorText.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
        } else {
            errorText.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
            }
        }

    }

    override fun sendProduct(product: Product) {

        val comunicator = context as Comunicator

        comunicator.sendProduct(product)
    }

    interface Comunicator {
        fun sendProduct(product: Product)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(internetLiveData.networkReceiver)
        internetLiveData.removeObservers(this)
    }

}
