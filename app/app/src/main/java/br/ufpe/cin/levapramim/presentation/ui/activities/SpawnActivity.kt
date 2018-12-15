package br.ufpe.cin.levapramim.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import br.ufpe.cin.levapramim.presentation.ui.activities.carrier.MapsActivity
import br.ufpe.cin.levapramim.R
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.presentation.ui.activities.base.AbstractLoggedActivity
import br.ufpe.cin.levapramim.presentation.ui.activities.client.ClientMainActivity

class SpawnActivity : AbstractLoggedActivity() {
    override fun onUser(type: UserType, user: User) {
        super.onUser(type, user)
        val intent = when (type) {
            UserType.CLIENT -> Intent(this, ClientMainActivity::class.java)
            UserType.CARRIER -> Intent(this, MapsActivity::class.java)
        }
        startActivity(intent)
        this.finish()
    }

    override fun showError(messageString: String) {
        Log.e("ERROR", messageString)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spawn)
    }
}
