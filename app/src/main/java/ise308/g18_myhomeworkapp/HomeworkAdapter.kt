package ise308.g18_myhomeworkapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

        }

        fun bind(item: Homework, clickListener: OnItemClickListener) {
            title.text = if (item.title!!.length <= 10) item.title else item.title!!.substring(0, 10); // Showing the top 10 characters
            courseTitle.text = item.courseTitle
            deadline.text = item.deadline

            itemView.setOnClickListener {
                clickListener.onItemClicked(item)
            }

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

        homework.setMainActivity(mainActivity);

        holder.bind(homework, mainActivity)

        when {
            homework.done -> holder.done.text =
                mainActivity.resources.getString(R.string.done_text)
        }

    }

}

interface OnItemClickListener{
    fun onItemClicked(homework: Homework)
}