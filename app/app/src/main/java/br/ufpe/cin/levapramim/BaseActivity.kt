package br.ufpe.cin.levapramim

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem


open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(_menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actbar, _menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_old_trips -> {
                startActivity(Intent(applicationContext, OldTripActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
