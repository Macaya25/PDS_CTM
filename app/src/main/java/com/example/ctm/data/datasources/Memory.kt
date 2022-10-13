package cl.uandes.pichangapp.data.datasources

import cl.uandes.pichangapp.data.model.modelApi.User

object Memory {
    var userToken: String? = null
    var userEmail: String? = null
    var currentUser: User? = null
}