package com.itesm.equipo3.provechito.presenters

import android.content.Context
import com.itesm.equipo3.provechito.interfaces.IUser
import com.itesm.equipo3.provechito.models.User
import com.itesm.equipo3.provechito.models.UserModel
import com.itesm.equipo3.provechito.pojo.User.UserRequestResponse

// Autor: Diego PB
class UserPresenter(val view: IUser.View): IUser.Presenter {
    private val userModel = UserModel(this)

    override fun getUserData(context: Context) {
        userModel.getUserData(context)
    }

    override fun userResponse(userResponse: UserRequestResponse) {
        userResponse.user?.let { view.showProfile(it) }
    }
}