package ise308.g18_myhomeworkapp

import android.R.attr.data
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager


private const val TAG = "NotePagerActivity"
private var homeworkList : ArrayList<Homework>? = null
private var serializer: JsonSerializer? = null

class HomeworkPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework_pager)

        val homeworkApplication = this.application as HomeworkApplication;
        homeworkList = homeworkApplication.getHomeworkList();
        serializer = homeworkApplication.getSerializer()

        // Create a parts list with one piece for each note
        var homeworkFragmentList = java.util.ArrayList<ShowHomeworkFragment>()
        for (homework in homeworkList!!){
            homeworkFragmentList.add(ShowHomeworkFragment.newInstance(homework, supportFragmentManager, this))
        }

//        val extra: Bundle? = intent.extras
//        val homeworkId = extra?.getInt("HOMEWORK_ID");
//        val homeworkId = intent.getIntExtra("HOMEWORK_ID", -1);
//        if (homeworkId != -1) {
//            val homework = homeworkList?.get(homeworkId!!);
//            ShowHomeworkFragment.newInstance(homework, supportFragmentManager, this)
//        }

        val homeworkIndex = homeworkApplication.getHomeworkIndex();

        //ShowHomeworkFragment
        val homeworkViewPager = findViewById<ViewPager>(R.id.pager_homeworks);
        val pageAdapter = HomeworkPagerAdapter(supportFragmentManager, homeworkFragmentList)
        homeworkViewPager.adapter = pageAdapter
        homeworkViewPager.currentItem = homeworkIndex
    }

    class HomeworkPagerAdapter(fm: FragmentManager, private val homeworkFragmentList: ArrayList<ShowHomeworkFragment>) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = homeworkFragmentList.size

        override fun getItem(position: Int) = homeworkFragmentList[position]

    }

}