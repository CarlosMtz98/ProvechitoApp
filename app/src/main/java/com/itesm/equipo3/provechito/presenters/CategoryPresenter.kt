package com.itesm.equipo3.provechito.presenters

import android.content.Context
import android.util.Log
import com.itesm.equipo3.provechito.interfaces.ICategory
import com.itesm.equipo3.provechito.models.CategoryModel
import com.itesm.equipo3.provechito.pojo.Category.*

class CategoryPresenter(val view: ICategory.View) : ICategory.Presenter {
    private val categoryModel = CategoryModel(this)
    override fun getCategory(context: Context, categoryId: String) {
        categoryModel.getCategory(context, categoryId)
    }

    override fun getCategories(context: Context) {
        categoryModel.getCategories(context)
    }

    override fun categoryDetailResponse(category: Category) {
        view.showCategory(category)
    }

    override fun categoriesObtained(categoryList: CategoryListResponse) {
        view.showCategories(categoryList)
    }


}