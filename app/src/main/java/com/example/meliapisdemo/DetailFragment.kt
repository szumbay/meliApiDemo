package com.example.meliapisdemo


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.meliapisdemo.model.productItem.*
import com.example.meliapisdemo.utils.ErrorType
import com.example.meliapisdemo.viewmodel.ProductItemViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.product_item.title

class DetailFragment : Fragment() {

    private var productItemViewModel = ProductItemViewModel()
    private lateinit var productItem : ProductItem


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments!!.getString("productId")
        fetchProductItem(id)
    }

    fun fetchProductItem(id : String){
        productItemViewModel = ViewModelProviders.of(this).get(ProductItemViewModel::class.java)
        productItemViewModel.getProduct(id).observe(this, Observer {
            when(it){
                is ProductDetailResponse.Response -> handleResponse(it.productItem,it.description)
                is ProductDetailResponse.Loading -> handleLoading(it.message)
            }
        })
    }

    fun handleLoading(messagge: String){
        title.apply { this.text = messagge }
    }

    fun handleResponse(productItem: ProductItemResponse, description: DescriptionResponse) {

        when(productItem){
            is ProductItemResponse.Success -> handleItemSuccess(productItem.productItem)
            is ProductItemResponse.Error -> handleItemError(productItem.errorType)
        }
        when(description){
            is DescriptionResponse.Success -> handleDescriptionSuccess(description.description)
            is DescriptionResponse.Error -> handleDescriptionError(description.errorType)
        }

    }


    fun handleItemSuccess(productItem: ProductItem){
        title.apply { this.text = productItem.title }
        pictures.apply {
            var picture = productItem.pictures[0]
            val uri: Uri = Uri.parse(picture.pictureS)
            setImageURI(uri)
        }
    }

    fun handleDescriptionSuccess(descriptiond: Description){
        description.apply { this.text  = descriptiond.description }
    }

    fun handleItemError(errorType: ErrorType){

    }

    fun handleDescriptionError(errorType: ErrorType){

    }

}
