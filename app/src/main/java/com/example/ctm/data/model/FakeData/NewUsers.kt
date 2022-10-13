package cl.uandes.pichangapp.data.model.FakeData

import cl.uandes.pichangapp.data.model.modelApi.User

class NewUsers {
    companion object {
        fun createUserList(): MutableList<User> {
            val users = ArrayList<User>()
            users.add(User( 1,"ciconchap@pichang.app"," ","Guapos","masculino"))
            users.add(User(2,"cami@pichang.app","camimi","Camicamiones","femenina"))
            users.add(User(3, "alcalde@pichang.app","alcalde","AlcaldeFc","masculino"))
            return users.toMutableList()
        }
    }
}
