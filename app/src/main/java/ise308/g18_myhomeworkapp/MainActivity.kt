package ise308.g18_myhomeworkapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var serializer: JsonSerializer? = null
    private var homeworkList = ArrayList<Homework>()
    private var recyclerView: RecyclerView? = null
    private var adapter: HomeworkAdapter? = null
    private var showDividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab.setOnClickListener { view ->
            val dialog = NewHomework()
            dialog.show(supportFragmentManager, "")
        }

        serializer = JsonSerializer("MyHomework.json", applicationContext)

        try {
            homeworkList = serializer!!.load()
        } catch (e: Exception) {
            homeworkList = ArrayList()
            Log.e("Error loading homeworks: ", "", e)
        }

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = HomeworkAdapter(this, homeworkList)

        val layoutManager =
            LinearLayoutManager(applicationContext)

        recyclerView!!.layoutManager = layoutManager

        recyclerView!!.itemAnimator = DefaultItemAnimator()

        recyclerView!!.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        )

        recyclerView!!.adapter = adapter

    }

    fun createNewHomework(newHomework: Homework) {

        homeworkList!!.add(newHomework)
        adapter!!.notifyDataSetChanged()

    }

    fun showHomework(adapterPosition: Int) {

    }

    private fun saveHomeworks() {
        try {
            serializer!!.save(this.homeworkList!!)
        } catch (e: Exception) {
            Log.e("Error Saving Homeworks", "", e)
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences(
                "My Homework App",
                Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean(
                "dividers", true)
        // Add a neat dividing line between list items
        if (showDividers)
            recyclerView!!.addItemDecoration(
                    DividerItemDecoration(
                            this, LinearLayoutManager.VERTICAL))
        else {
            // check there are some dividers
            // or the app will crash
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    override fun onPause() {
        super.onPause()

        saveHomeworks()
    }

}