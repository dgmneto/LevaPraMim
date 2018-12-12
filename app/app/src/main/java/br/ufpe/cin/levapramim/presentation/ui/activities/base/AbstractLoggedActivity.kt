package br.ufpe.cin.levapramim.presentation.ui.activities.base

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

abstract class AbstractLoggedActivity : AppCompatActivity() {
    private val RC_SIG_IN = 0

    var currentUser: FirebaseUser? = null

    override fun onResume() {
        super.onResume()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            createSigInIntent()
        } else {
            this.currentUser = currentUser
        }
    }

    fun createSigInIntent() {
        val providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
            RC_SIG_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIG_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode != Activity.RESULT_OK) {
                if (response == null) {
                    finishAffinity()
                } else {
                    Log.e("ERROR", response.toString())
                }
            }
        }
    }
}