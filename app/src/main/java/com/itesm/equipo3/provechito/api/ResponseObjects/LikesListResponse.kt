package com.itesm.equipo3.provechito.api.ResponseObjects

import com.itesm.equipo3.provechito.models.Like

data class LikesListResponse(
        var total: Int,
        var likes: ArrayList<Like>
)