package ise308.g18_myhomeworkapp

import androidx.fragment.app.DialogFragment
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.LayoutInflater

import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class NewHomework : DialogFragment() {

    override
    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.new_homework, null)

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
        val btnAdd =
                dialogView.findViewById(R.id.btnAdd) as Button

        builder.setView(dialogView).setMessage(
                resources.getString(
                        R.string.add_new_homework))

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnAdd.setOnClickListener {

            val newHomework = Homework() // Creating a new homework

            // Adjusting properties to match the user's input on the form

            newHomework.title = editTitle.text.toString()
            newHomework.courseTitle = editCourseTitle.text.toString()
            newHomework.description = editDescription.text.toString()
            newHomework.deadline = editDeadline.text.toString()
            newHomework.workload = editWorkload.text.toString().toInt() // TODO: Search
            newHomework.done = checkBoxDone.isChecked

            val callingActivity = activity as MainActivity?

            callingActivity!!.createNewHomework(newHomework)

            dismiss()
        }

        return builder.create()

    }

}