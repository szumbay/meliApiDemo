package com.example.meliapisdemo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.meliapisdemo.viewmodel.ProductItemViewModel
import com.example.meliapisdemo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.product_item.*

class DetailFragment : Fragment() {

    private var productItemViewModel = ProductItemViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments!!.getString("productId")
        fetchProductItem(id!!)
    }

    fun fetchProductItem(id : String){
        productItemViewModel = ViewModelProviders.of(this).get(ProductItemViewModel::class.java)
        productItemViewModel.getProduct(id).observe(this, Observer {

            title.apply {
                this.text = it.title
            }

        })
    }


}
