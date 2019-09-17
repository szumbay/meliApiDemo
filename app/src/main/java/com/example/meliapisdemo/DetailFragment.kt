package com.example.meliapisdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.meliapisdemo.adapter.PicturePageAdapter
import com.example.meliapisdemo.model.productItem.*
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.utils.InternetLiveData
import com.example.meliapisdemo.viewmodel.ProductItemViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.product_item.title
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    private var productItemViewModel = ProductItemViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments!!.getString("productId")
        fetchProductItem(id)
    }

    private fun fetchProductItem(id: String) {
        productItemViewModel = ViewModelProviders.of(this).get(ProductItemViewModel::class.java)
        val internetUtils = InternetLiveData(context!!)
        internetUtils.observe(this, Observer {
            if(it ){
                productItemViewModel.getProduct(id).observe(this, Observer { productDetail ->
                    when (productDetail) {
                        is ProductDetailResponse.Response -> handleResponse(productDetail.productItem, productDetail.description)
                        is ProductDetailResponse.Loading -> handleLoading(productDetail.message)
                        else -> handleLoading("loading data")
                    }
                })
            }else if(it && productItemViewModel.productLiveData.value != null){ }
            else{handleItemError(ErrorType.NETWORK)}
        })
    }

    private fun handleLoading(messagge: String) {
        title.apply { this.text = messagge }
    }

    private fun handleResponse(productItem: ProductItemResponse, description: DescriptionResponse) {

        when (productItem) {
            is ProductItemResponse.Success -> handleItemSuccess(productItem.productItem)
            is ProductItemResponse.Error -> handleItemError(productItem.errorType)
        }
        when (description) {
            is DescriptionResponse.Success -> handleDescriptionSuccess(description.description)
            is DescriptionResponse.Error -> handleDescriptionError(description.errorType)
        }

    }


    private fun handleItemSuccess(productItem: ProductItem) {
        responseDetail.apply { visibility = View.VISIBLE }
        errorTextDetail.apply { visibility = View.GONE }
        title.apply { this.text = productItem.title }
        viewpager_pictures.apply {
            adapter = PicturePageAdapter(productItem.pictures, context)
        }
        condition.apply { this.text = productItem.condition }
        price.apply {
            val format = DecimalFormat("###,###,##0.00")
            text = "$ " + format.format(productItem.price)
        }
    }

    private fun handleDescriptionSuccess(descriptiond: Description) {
        description.apply { this.text = descriptiond.description }
    }

    private fun handleItemError(error: ErrorType) {
        errorTextDetail.apply { visibility = View.VISIBLE }
        responseDetail.apply { visibility = View.GONE }
        if (error == ErrorType.NETWORK) {
            errorTextDetail.apply {
                text = "No hay conexion a internet"
                val drawable = resources.getDrawable(R.drawable.error)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
        else if (error == ErrorType.CLIENT){
            errorTextDetail.apply {
                text = "Busqueda sin resultados"
                val drawable = resources.getDrawable(R.drawable.errornotfound)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
        else if (error == ErrorType.SERVER){
            errorTextDetail.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
        else{
            errorTextDetail.apply {
                text = "Problemas con el Server"
                val drawable = resources.getDrawable(R.drawable.errorserver)
                setCompoundDrawablesWithIntrinsicBounds(null, drawable,null, null)
            }
        }
    }

    private fun handleDescriptionError(errorType: ErrorType) {
        description.apply { visibility = View.GONE }
    }

}
