package ise308.g18_myhomeworkapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class EditHomework : DialogFragment(){

    private lateinit var supportFragmentManager: FragmentManager;
    private lateinit var homeworkToEdit: Homework;
    private lateinit var theContext: Context;

    override
    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.edit_homework, null)

        val editTitle =
                dialogView.findViewById(R.id.editTitle) as EditText
        val editCourseTitle =
                dialogView.findViewById(R.id.editCourseTitle) as EditText
        val editDescription =
                dialogView.findViewById(R.id.editDescription) as EditText
        val editDeadline =
                dialogView.findViewById(R.id.editDeadline) as EditText
        val editWorkload =
                dialogView.findViewById(R.id.editWorkload) as EditText
        val checkBoxDone =
                dialogView.findViewById(R.id.checkBoxDone) as CheckBox
        val btnCancel =
                dialogView.findViewById(R.id.btnCancel) as Button
        val btnSave =
                dialogView.findViewById(R.id.btnSave) as Button;


//        editTitle.text = requireArguments().getString("title")
//        editCourseTitle.text = requireArguments().getString("course title")
//        editDescription.text = requireArguments().getString("description")
//        editDeadline.text = requireArguments().getString("deadline")
//        editWorkload.text = requireArguments().getInt("workload").toString()

        editTitle.setText(homeworkToEdit.title);
        editCourseTitle.setText(homeworkToEdit.courseTitle);
        editDescription.setText(homeworkToEdit.description);
        editDeadline.setText(homeworkToEdit.deadline);
        editWorkload.setText(homeworkToEdit.workload!!.toString());
        checkBoxDone.setChecked(homeworkToEdit.done)

        builder.setView(dialogView).setMessage(
                resources.getString(
                        R.string.add_new_homework))

        btnCancel.setOnClickListener {
            getTargetFragment()?.onActivityResult(getTargetRequestCode(), 0, getActivity()?.getIntent());
            dismiss()
        }

        fun showAlert(message: String) {
            val builder = AlertDialog.Builder(theContext)
            builder.setTitle("Hatalı veri ğirişi")
            builder.setMessage(message)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(theContext,
                        android.R.string.yes, Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(theContext,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("Maybe") { dialog, which ->
                Toast.makeText(theContext,
                        "Maybe", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        btnSave.setOnClickListener {
            try {
                homeworkToEdit.workload = editWorkload.text.toString().toInt() // TODO: Search
            } catch (e: Exception) {
                showAlert("Workload sayı değeri olmalı");
                return@setOnClickListener;
            }

            // Edit properties to match the user's input on the form
            homeworkToEdit.title = editTitle.text.toString()
            homeworkToEdit.courseTitle = editCourseTitle.text.toString()
            homeworkToEdit.description = editDescription.text.toString()
            homeworkToEdit.deadline = editDeadline.text.toString()
            homeworkToEdit.done = checkBoxDone.isChecked

            val mainActivity = homeworkToEdit.getMainActivity();
            val homeworkApplication = mainActivity.application as HomeworkApplication;
            //val homeworkList2 = homeworkApplication.getHomeworkList();
            val adapter = mainActivity.getAdapter();
            val homeworkList = mainActivity.getHomeworkList();
            val homeworkIndex = homeworkList.indexOf(homeworkToEdit);
            adapter?.notifyItemChanged(homeworkIndex);
            //theHomeworkAdapter.notifyDataSetChanged();

            //val callingActivity = activity as MainActivity?
            getTargetFragment()?.onActivityResult(getTargetRequestCode(), 1, getActivity()?.getIntent());
            dismiss()
        }

        return builder.create()

    }

    companion object {
        fun newInstance(homework : Homework?, fragmentManager: FragmentManager, context: Context) : EditHomework {
            val fragment = EditHomework()
            val bundle = Bundle(1)
            bundle.putString("title", homework?.title)
            bundle.putString("course title", homework?.courseTitle)
            bundle.putString("description", homework?.description)
            bundle.putString("deadline", homework?.deadline)
            bundle.putInt("workload", homework?.workload!!) // TODO: Search
            val isDone = if (homework == null) false else homework!!.done
            bundle.putBoolean("done", isDone)
            fragment.arguments = bundle
            fragment.homeworkToEdit = homework;
            fragment.supportFragmentManager = fragmentManager
            fragment.theContext = context;
            return fragment
        }
    }

}