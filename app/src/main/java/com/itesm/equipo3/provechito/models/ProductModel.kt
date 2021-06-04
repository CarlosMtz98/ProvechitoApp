package com.itesm.equipo3.provechito.models

import android.content.Context
import android.util.Log
import com.itesm.equipo3.provechito.api.ApiClient
import com.itesm.equipo3.provechito.api.ResponseObjects.DeleteResponse
import com.itesm.equipo3.provechito.interfaces.IProduct
import com.itesm.equipo3.provechito.pojo.Products.Product
import com.itesm.equipo3.provechito.pojo.Products.ProductListResponse
import com.itesm.equipo3.provechito.presenters.ProductPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductModel(val presenter: ProductPresenter) : IProduct.Model {
    private val apiClient = ApiClient()

    override fun getProducts(context: Context) {
        apiClient.getApiService(context).getProducts()
                .enqueue(object: Callback<ProductListResponse> {
                    override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                        Log.e("ProductModel", "GetProducts Failed Response")
                    }

                    override fun onResponse(call: Call<ProductListResponse>, response: Response<ProductListResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            serviceResponse?.let { presenter.view.showProducts(it) }
                            Log.i("ProductModel", "GetProducts Success Response")
                        } else {
                            Log.e("ProductModel", "GetProducts Failed Response")
                        }
                    }
                })
    }

    override fun updateProduct(context: Context, product: Product) {
        apiClient.getApiService(context).updateProduct(product.id ?: "", product)
                .enqueue(object: Callback<Product> {
                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        Log.e("ProductModel", "UpdateProduct Failed Response")
                    }

                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            Log.i("ProductModel", "UpdateProduct Success Response")
                            presenter.view.reloadProducts()
                        } else {
                            Log.e("ProductModel", "UpdateProduct Failed Response")
                        }
                    }

                })
    }

    override fun addProducts(context: Context, product: Product) {
        apiClient.getApiService(context).addProduct(product)
                .enqueue(object: Callback<Product> {
                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        Log.e("ProductModel", "AddProduct Failed Response")
                        presenter.view.reloadProducts()
                    }

                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            Log.i("ProductModel", "AddProduct Success Response")
                            if (serviceResponse != null) {
                                presenter.view.showNewProduct(serviceResponse)
                            }
                        } else {
                            Log.e("ProductModel", "AddProduct Failed Response")
                        }
                    }

                })
    }

    override fun removeProducts(context: Context, product: Product) {
        product.id?.let {
            apiClient.getApiService(context).removeProduct(it)
                .enqueue(object: Callback<DeleteResponse> {
                    override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                        Log.e("ProductModel", "RemoveProduct Failed Response")
                    }

                    override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                        val serviceResponse = response.body()
                        if (response.isSuccessful) {
                            Log.e("ProductModel", "RemoveProduct Success Response")
                            presenter.view.reloadProducts()
                        } else {
                            Log.e("ProductModel", "RemoveProduct Failed Response")
                        }
                    }
                })
        }
    }
}