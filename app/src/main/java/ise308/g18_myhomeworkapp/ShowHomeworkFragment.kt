package ise308.g18_myhomeworkapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ShowHomeworkFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homework_frame, container, false)
        val tvTitle = view.findViewById<TextView>(R.id.txtTitle)
        val tvCourseTitle = view.findViewById<TextView>(R.id.txtCourseTitle)
        val tvDescription = view.findViewById<TextView>(R.id.txtDescription)
        val tvDeadline = view.findViewById<TextView>(R.id.txtDeadline)
        val tvWorkload = view.findViewById<TextView>(R.id.txtWorkload)
        val tvDone = view.findViewById<TextView>(R.id.txtDone)

        tvTitle.text = requireArguments().getString("title")
        tvCourseTitle.text = requireArguments().getString("course title")
        tvDescription.text = requireArguments().getString("description")
        tvDeadline.text = requireArguments().getString("deadline")
        tvWorkload.text = requireArguments().getInt("workload").toString()
        if(!requireArguments().getBoolean("done")) tvDone.visibility = View.GONE

        return view
    }

    companion object {
        fun newInstance(homework : Homework) : ShowHomeworkFragment {
            val fragment = ShowHomeworkFragment()
            val bundle = Bundle(1)
            bundle.putString("title", homework.title)
            bundle.putString("course title", homework.courseTitle)
            bundle.putString("description", homework.description)
            bundle.putString("deadline", homework.deadline)
            bundle.putInt("workload", homework.workload!!) // TODO: Search
            bundle.putBoolean("done", homework.done)
            fragment.arguments = bundle
            return fragment
        }
    }

}