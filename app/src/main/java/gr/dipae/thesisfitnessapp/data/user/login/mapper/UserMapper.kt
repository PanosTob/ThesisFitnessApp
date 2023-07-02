package gr.dipae.thesisfitnessapp.data.user.login.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteUser: RemoteUser?): User? {
        remoteUser ?: return null
        return User(
            name = remoteUser.name ?: "unknown name",
            email = remoteUser.email ?: "unknown email"
        )
    }
}
