package com.example.meliapisdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapisdemo.R
import com.example.meliapisdemo.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(context: Context?, products: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var context = context
    private var products : ArrayList<Product> = products




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.product_item, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products.get(position)
        holder.bind(item, context)
    }


    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){

        val title = v.findViewById(R.id.title) as TextView
        val price = v.findViewById(R.id.price) as TextView
        val thumbnail = v.findViewById(R.id.thumbnail) as ImageView

        fun bind(product: Product, context: Context?){
            title.text = product.title
            price.text = "$" + product.price.toString()
            val picasso = Picasso.get()
            picasso.load(product.thumbnail).into(thumbnail)
            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, product.id, Toast.LENGTH_SHORT).show() })
        }



    }
}



