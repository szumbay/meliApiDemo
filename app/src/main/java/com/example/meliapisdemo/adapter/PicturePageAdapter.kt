package com.example.meliapisdemo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.meliapisdemo.R
import com.example.meliapisdemo.model.productItem.ProductPicture
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest




class PicturePageAdapter(private val pictures: List<ProductPicture>, private val context: Context) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return pictures.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.product_picture, container, false)

        val pic = pictures[position]
        val size = pic.getDensityPixel(context.resources.displayMetrics.density.toInt())


        val image = view.findViewById<SimpleDraweeView>(R.id.picture_product)
        val uri = Uri.parse(pic.pictureS)

        image.apply {
            this.setImageURI(uri)
        }


        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}