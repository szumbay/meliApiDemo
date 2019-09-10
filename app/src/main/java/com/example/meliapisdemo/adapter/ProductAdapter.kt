package com.example.meliapisdemo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapisdemo.R
import com.example.meliapisdemo.model.product.Product
import com.facebook.drawee.view.SimpleDraweeView
import java.text.DecimalFormat

class ProductAdapter(private val context: Context, private val products: ArrayList<Product>,
                     private val comunicator: Comunicator) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.product_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        holder.bind(item, context)
        holder.view.setOnClickListener {
            comunicator.sendProduct(item)
        }
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){

        val view = v
        val title = v.findViewById(R.id.title) as TextView
        val price = v.findViewById(R.id.price) as TextView
        val thumbnail = v.findViewById(R.id.thumbnail) as SimpleDraweeView

        fun bind(product: Product, context: Context?) {
            title.text = product.title
            val format = DecimalFormat("###,###,##0.00")
            price.text = "$ " + format.format(product.price)
            val uri: Uri = Uri.parse(product.thumbnail)
            thumbnail.setImageURI(uri)
        }
    }

    interface Comunicator {
        fun sendProduct(product: Product)
    }
}



