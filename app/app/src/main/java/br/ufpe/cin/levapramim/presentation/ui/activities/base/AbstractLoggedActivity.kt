package br.ufpe.cin.levapramim.presentation.ui.activities.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.ufpe.cin.levapramim.domain.models.User
import br.ufpe.cin.levapramim.domain.models.user.UserType
import br.ufpe.cin.levapramim.domain.repositories.impl.FirebaseUserRepository
import br.ufpe.cin.levapramim.presentation.presenters.LoggedPresenter
import br.ufpe.cin.levapramim.presentation.presenters.impl.LoggedPresenterImpl
import br.ufpe.cin.levapramim.threading.MainThreadImpl
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.Executors

abstract class AbstractLoggedActivity : BaseActivity(), LoggedPresenter.View {
    private val RC_SIG_IN = 0

    private val executor = Executors.newFixedThreadPool(2)
    private lateinit var presenter : LoggedPresenter
    protected var currentUser: FirebaseUser? = null
    protected var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = FirebaseUserRepository(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance())
        this.presenter = LoggedPresenterImpl(
            MainThreadImpl.getInstance(),
            executor,
            this,
            userRepository)
    }

    override fun onResume() {
        super.onResume()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            createSigInIntent()
        } else {
            this.currentUser = currentUser
            this.presenter.resume()
        }
    }

    override fun onUser(type: UserType, user: User) {
        this.user = user
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
                    Log.e("LOGIN", response.toString())
                }
            }
        }
    }
}