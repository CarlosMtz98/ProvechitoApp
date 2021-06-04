package com.itesm.equipo3.provechito.presenters

import android.content.Context
import com.itesm.equipo3.provechito.interfaces.IProduct
import com.itesm.equipo3.provechito.models.ProductModel
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Products.ProductListResponse

class ProductPresenter(val view: IProduct.View) : IProduct.Presenter{
    private val productModel = ProductModel(this)

    override fun getProducts(context: Context) {
        productModel.getProducts(context)
    }

    override fun addProduct(context: Context, product: Product) {
        productModel.addProducts(context, product)
    }

    override fun updateProduct(context: Context, product: Product) {
        productModel.updateProduct(context, product)
    }

    override fun removeProduct(context: Context, product: Product) {
        productModel.removeProducts(context, product)
    }

    override fun productAdded(product: Product) {
        view.showNewProduct(product)
    }

    override fun productListUpdated(product: Product) {
        view.reloadProducts()
    }

    override fun productsObtained(productsList: ProductListResponse) {
        view.showProducts(productsList)
    }
}