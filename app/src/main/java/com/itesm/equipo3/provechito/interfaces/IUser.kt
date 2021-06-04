package com.itesm.equipo3.provechito.interfaces

import android.content.Context
import com.itesm.equipo3.provechito.models.User
import com.itesm.equipo3.provechito.pojo.User.UserRequestResponse

// Autor: Diego PB
class IUser {
    interface View {
        fun showProfile(user: User)
    }

    interface Presenter {
        fun getUserData(context: Context)
        fun userResponse(userResponse: UserRequestResponse)
    }

    interface Model {
        fun getUserData(context: Context)
    }
}