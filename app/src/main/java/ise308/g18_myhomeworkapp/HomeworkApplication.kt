package ise308.g18_myhomeworkapp

import android.app.Application
import android.content.Context
import android.util.Log

class HomeworkApplication : Application()  {

    private var currentHomeworkIndex: Int = -1;

    companion object {
        private var serializer: JsonSerializer? = null
        private var homeworkList = ArrayList<Homework>()
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        ctx = applicationContext;
        serializer = JsonSerializer("MyHomework.json", applicationContext)

        try {
            homeworkList = serializer!!.load()
        } catch (e: Exception) {
            homeworkList = ArrayList()
            Log.e("Error loading homeworks: ", "", e)
        }
    }

    fun getHomeworkList() : ArrayList<Homework> {
        return  homeworkList;
    }

    fun getSerializer() : JsonSerializer? {
        return  serializer;
    }

    fun setHomeworkIndex(homeworkIndex: Int) {
        currentHomeworkIndex = homeworkIndex;
    }

    fun getHomeworkIndex() : Int {
        return currentHomeworkIndex;
    }

}