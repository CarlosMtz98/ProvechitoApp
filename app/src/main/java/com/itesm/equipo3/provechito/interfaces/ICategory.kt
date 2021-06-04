package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.models.CategoryModel
import com.itesm.equipo3.provechito.pojo.Category.Category
import com.itesm.equipo3.provechito.pojo.Category.CategoryListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class ICategory {
    interface View {
        fun showCategory(category: Category)
        fun showCategories(categoryList: CategoryListResponse)
    }

    interface Presenter {
        fun getCategory(context: Context, categoryId: String)
        fun getCategories(context: Context)
        fun categoryDetailResponse(category: Category)
        fun categoriesObtained(categoryList: CategoryListResponse)
    }

    interface Model {
        fun getCategory(context: Context, categoryId: String)
        fun getCategories(context: Context)
    }
}