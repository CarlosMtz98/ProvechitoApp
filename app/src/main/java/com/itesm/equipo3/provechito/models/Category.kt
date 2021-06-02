package com.itesm.equipo3.provechito.models

import java.io.Serializable

data class Category(
        var _id: String?,
        var name: String?,
        var thumbnailUrl: String?,
        var imageUrl: String?
): Serializable {
    constructor(name: String): this("", name, "", "")
}