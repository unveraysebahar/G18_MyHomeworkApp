package ise308.g18_myhomeworkapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

private const val TAG = "NotePagerActivity"
private var homeworkList : ArrayList<Homework>? = null
private var serializer: JsonSerializer? = null

class HomeworkPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework_pager)

        serializer = JsonSerializer("MyHomework.json", applicationContext)

        try {
            homeworkList = serializer!!.load()
        } catch (e: Exception) {
            homeworkList = ArrayList()
            Log.e("Error loading homeworks: ", "", e)
        }

        // Create a parts list with one piece for each note
        var homeworkFragmentList = java.util.ArrayList<Fragment>()
        for (homework in homeworkList!!){
            homeworkFragmentList.add(ShowHomeworkFragment.newInstance(homework))
        }

        val pageAdapter = HomeworkPagerAdapter(supportFragmentManager, homeworkFragmentList)
        findViewById<ViewPager>(R.id.pager_homeworks).adapter = pageAdapter

    }

    class HomeworkPagerAdapter(fm: FragmentManager,  private val homeworkFragmentList: ArrayList<Fragment>) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = homeworkFragmentList.size

        override fun getItem(position: Int) = homeworkFragmentList[position]

    }

}