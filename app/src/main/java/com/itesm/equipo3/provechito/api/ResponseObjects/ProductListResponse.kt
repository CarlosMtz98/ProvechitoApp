package com.itesm.equipo3.provechito.api.ResponseObjects

import com.itesm.equipo3.provechito.models.Product

data class ProductListResponse(
        var total: Int,
        var products: ArrayList<Product>
)