package br.ufpe.cin.levapramim.presentation.ui.activities.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.ufpe.cin.levapramim.ItemOldTrip
import br.ufpe.cin.levapramim.R

private lateinit var historyList: RecyclerView
private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager

class HistoryActivity: AppCompatActivity(){

    lateinit var db =

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        viewManager = LinearLayoutManager(this)
    }

    inner class UpdateReceiver: BroadcastReceiver(){
        override fun onReceive(ctx: Context?, i: Intent?) {
            val list = db.getItens()
            if (list != null) {
                viewAdapter = RssAdapter(list, db)
                history = findViewById<RecyclerView>(R.id.history).apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
        }
    }

    private inner class RssAdapter(private val result: List<ItemOldTrip>, private val db: SqlHelper) :
        RecyclerView.Adapter<CardChangeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardChangeHolder {
            val v = layoutInflater.inflate(R.layout.item_lista, parent, false)
            return CardChangeHolder(v)
        }
        override fun onBindViewHolder(holder: CardChangeHolder, position: Int) {
            holder.bindModel(result.get(position))
        }
        override fun getItemCount(): Int {
            return result.size
        }

    }

    //CardHolder com as informações que vão aparecer na tela
    internal class CardChangeHolder(row: View) : RecyclerView.ViewHolder(row) {
        var destination: TextView
        var origin: TextView
        var date: TextView
        var value: TextView

        init {
            destination = row.findViewById(R.id.item_destination)
            origin = row.findViewById(R.id.item_origin)
            date = row.findViewById(R.id.item_date)
            value = row.findViewById(R.id.item_value)
        }

        fun bindModel(trip: ItemOldTrip) {
            destination.text = trip.destiny
            origin.text = trip.origin
            date.text = trip.date
            value.text = trip.value
        }

    }

}