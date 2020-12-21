package ise308.g18_myhomeworkapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeworkAdapter(
    private val mainActivity: MainActivity,
    private val homeworkList: List<Homework>)
    : RecyclerView.Adapter<HomeworkAdapter.ListItemHolder>() {

    inner class ListItemHolder(view: View) :
            RecyclerView.ViewHolder(view),
            View.OnClickListener {

        internal var title = view.findViewById<View>(R.id.textViewTitle) as TextView
        internal var courseTitle = view.findViewById<View>(R.id.textViewCourseTitle) as TextView
        internal var deadline = view.findViewById<View>(R.id.textViewDeadline) as TextView
        internal var done = view.findViewById<View>(R.id.textViewDone) as TextView
        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            val intentToHomeworkPager= Intent(view!!.context, HomeworkPagerActivity::class.java)
            view.context.startActivity(intentToHomeworkPager)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ListItemHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem, parent, false)

        return ListItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (homeworkList != null) {
            return homeworkList.size
        }

        return -1
    }

    override fun onBindViewHolder(
        holder: ListItemHolder, position: Int) {

        val homework = homeworkList[position]

        holder.title.text = homework.title!!.substring(0,10) // Showing the top 10 characters

        holder.courseTitle.text = homework.courseTitle

        holder.deadline.text = homework.deadline

        when {
            homework.done -> holder.done.text =
                mainActivity.resources.getString(R.string.done_text)
        }

    }

}