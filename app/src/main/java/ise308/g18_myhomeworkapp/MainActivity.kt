package ise308.g18_myhomeworkapp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

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

        val homeworkApplication = this.application as HomeworkApplication;
        homeworkList = homeworkApplication.getHomeworkList();
        serializer = homeworkApplication.getSerializer()

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

    override fun onItemClicked(homework: Homework) {
        val homeworkId = homeworkList.indexOf(homework);
        val homeworkApplication = this.application as HomeworkApplication;
        homeworkApplication.setHomeworkIndex(homeworkId);

        val intentToHomeworkPager= Intent(this, HomeworkPagerActivity::class.java)
        //intent.putExtra("HOMEWORK_ID", homeworkId);
        startActivity(intentToHomeworkPager)
        Log.i("INFO",homework.title.toString())
    }

    fun createNewHomework(newHomework: Homework) {
        newHomework.setMainActivity(this);
        homeworkList!!.add(newHomework)
        adapter!!.notifyDataSetChanged()

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
        if (showDividers)
            recyclerView!!.addItemDecoration(
                    DividerItemDecoration(
                            this, LinearLayoutManager.VERTICAL))
        else {

            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    override fun onPause() {
        super.onPause()
        saveHomeworks()
    }

    fun getHomeworkList() : ArrayList<Homework> {
        return  homeworkList;
    }

    fun getAdapter() : HomeworkAdapter? {
        return adapter;
    }

}
