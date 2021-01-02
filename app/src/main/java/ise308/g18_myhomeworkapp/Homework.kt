package ise308.g18_myhomeworkapp

import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Homework {

    private lateinit var mainActivity: MainActivity;

    var title: String? = null
    var courseTitle: String? = null
    var description: String? = null
    var workload: Int? = null
    var deadline: String? = null
    var done: Boolean = false

    private val JSON_TITLE = "Title"
    private val JSON_COURSETITLE = "Course Title"
    private val JSON_DESCRIPTION = "Description"
    private val JSON_WORKLOAD = "Workload"
    private val JSON_DEADLINE = "Deadline"
    private val JSON_DONE = "Done"

    @Throws(JSONException::class)
    constructor(jo: JSONObject) {
        title = jo.getString(JSON_TITLE)
        courseTitle = jo.getString(JSON_COURSETITLE)
        description = jo.getString(JSON_DESCRIPTION)
        workload = jo.getInt(JSON_WORKLOAD)
        deadline = jo.getString(JSON_DEADLINE)
        done = jo.getBoolean(JSON_DONE)
    }

    constructor() {
    }

    fun setMainActivity(ma: MainActivity) {
        mainActivity = ma;
    }

    fun getMainActivity() : MainActivity {
        return mainActivity;
    }

    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {

        val jo = JSONObject()

        jo.put(JSON_TITLE, title)
        jo.put(JSON_COURSETITLE, courseTitle)
        jo.put(JSON_DESCRIPTION, description)
        jo.put(JSON_WORKLOAD, workload)
        jo.put(JSON_DEADLINE, deadline)
        jo.put(JSON_DONE, done)

        return jo
    }

}