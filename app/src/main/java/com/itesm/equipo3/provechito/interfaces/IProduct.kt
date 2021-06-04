package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Products.ProductListResponse

class IProduct {

    interface View {
        fun showProducts(productsList: ProductListResponse)
        fun showNewProduct(product: Product)
        fun reloadProducts()
    }

    interface Presenter {
        fun getProducts(context: Context)
        fun addProduct(context: Context, product: Product)
        fun updateProduct(context: Context, product: Product)
        fun removeProduct(context: Context, product: Product)
        fun productAdded(product: Product)
        fun productListUpdated(product: Product)
        fun productsObtained(productsList: ProductListResponse)
    }

    interface Model {
        fun getProducts(context: Context)
        fun updateProduct(context: Context, product: Product)
        fun addProducts(context: Context, product: Product)
        fun removeProducts(context: Context, product: Product)
    }
}