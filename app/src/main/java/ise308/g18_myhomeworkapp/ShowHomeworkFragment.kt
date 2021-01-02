package ise308.g18_myhomeworkapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*


class ShowHomeworkFragment : Fragment() {

    private lateinit var supportFragmentManager: FragmentManager;
    private var homeworkToShow: Homework? = null;
    private lateinit var theContext: Context;

    private lateinit var tvTitle : TextView;
    private lateinit var tvCourseTitle : TextView;
    private lateinit var tvDescription : TextView;
    private lateinit var tvDeadline : TextView;
    private lateinit var tvWorkload : TextView;
    private lateinit var tvDone : TextView;

    private lateinit var animFadeOut: Animation

    private lateinit var buttonDelete:Button
    private lateinit var buttonEdit:Button

    private var delayedDeleteButtonClickTimer:Timer = Timer()

    fun foo() {
        val mainActivity = homeworkToShow?.getMainActivity();
        val homeworkApplication = mainActivity?.application as HomeworkApplication;
        val adapter = mainActivity?.getAdapter();

        val homeworkList = mainActivity.getHomeworkList();
        homeworkList.remove(homeworkToShow);
        //adapter?.notifyItemChanged(homeworkIndex);
        adapter?.notifyDataSetChanged();
        //Log.e("INFO", "2//////////// Hooooooooooooo!!!")
        activity?.onBackPressed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homework_frame, container, false)

        tvTitle = view.findViewById<TextView>(R.id.txtTitle)
        tvCourseTitle = view.findViewById<TextView>(R.id.txtCourseTitle)
        tvDescription = view.findViewById<TextView>(R.id.txtDescription)
        tvDeadline = view.findViewById<TextView>(R.id.txtDeadline)
        tvWorkload = view.findViewById<TextView>(R.id.txtWorkload)
        tvDone = view.findViewById<TextView>(R.id.txtDone)

        buttonEdit=view.findViewById(R.id.editButton)
        buttonDelete=view.findViewById(R.id.deleteButton)

        animFadeOut= AnimationUtils.loadAnimation(theContext, R.anim.fade_out)
        animFadeOut.duration=1000

        buttonEdit.setOnClickListener{ view ->
            //Log.e("INFO", "1 //////////// Hooooooooooooo!!!")
            val dialog = EditHomework.newInstance(homeworkToShow, supportFragmentManager, theContext);
            dialog.setTargetFragment(this, 1);
            //val dialog = EditHomework()
            dialog.show(supportFragmentManager, "")
            //Log.e("INFO", "2//////////// Hooooooooooooo!!!")
        }

        buttonDelete.setOnClickListener{ view ->
            Log.i("INFO", "1 //////////// deleteButton Hooooooooooooo!!!")
            buttonDelete.startAnimation(animFadeOut)

            //delayedSendTimer = Timer()
            val setImageRunnable = Runnable { foo() }

            val task: TimerTask = object : TimerTask() {
                override fun run() {
                    activity!!.runOnUiThread(setImageRunnable)
                }
            }
            delayedDeleteButtonClickTimer.schedule(task, 1000);

//            val mainActivity = homeworkToShow?.getMainActivity();
//            val homeworkApplication = mainActivity?.application as HomeworkApplication;
//            val adapter = mainActivity?.getAdapter();
//
//            val homeworkList = mainActivity.getHomeworkList();
//            homeworkList.remove(homeworkToShow);
//            //adapter?.notifyItemChanged(homeworkIndex);
//            adapter?.notifyDataSetChanged();
//            //Log.e("INFO", "2//////////// Hooooooooooooo!!!")
//            activity?.onBackPressed()
        }

        tvTitle.text = requireArguments().getString("title")
        tvCourseTitle.text = requireArguments().getString("course title")
        tvDescription.text = requireArguments().getString("description")
        tvDeadline.text = requireArguments().getString("deadline")
        tvWorkload.text = requireArguments().getInt("workload").toString()
        if(!requireArguments().getBoolean("done")) tvDone.visibility = View.GONE

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == 1) { // 1 is an arbitrary number, can be any int
            // This is the return result of your DialogFragment
            if (resultCode == 1) { // 1 is an arbitrary number, can be any int
                tvTitle.setText(homeworkToShow?.title);
                tvCourseTitle.setText(homeworkToShow?.courseTitle);
                tvDescription.setText(homeworkToShow?.description);
                tvDeadline.setText(homeworkToShow?.deadline);
                tvWorkload.setText(homeworkToShow?.workload!!.toString());
                var isDone = if (homeworkToShow == null) false else homeworkToShow!!.done
                if(isDone){
                    tvDone.visibility = View.VISIBLE
                } else {
                    tvDone.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        fun newInstance(homework: Homework?, fragmentManager: FragmentManager, context: Context) : ShowHomeworkFragment {
            val fragment = ShowHomeworkFragment()
            val bundle = Bundle(1)
            bundle.putString("title", homework?.title)
            bundle.putString("course title", homework?.courseTitle)
            bundle.putString("description", homework?.description)
            bundle.putString("deadline", homework?.deadline)
            bundle.putInt("workload", homework?.workload!!) // TODO: Search
            bundle.putBoolean("done", homework?.done)
            fragment.arguments = bundle
            fragment.supportFragmentManager = fragmentManager
            fragment.homeworkToShow = homework;
            fragment.theContext = context;
            return fragment
        }
    }

}