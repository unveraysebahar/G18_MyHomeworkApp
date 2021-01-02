package ise308.g18_myhomeworkapp

import android.app.Application
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer

class HomeworkApplication : Application()  {

    private var currentHomeworkIndex: Int = -1;

    companion object {
        private var serializer: JsonSerializer? = null
        private var homeworkList = ArrayList<Homework>()
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        val homeworkJsonFileName = "MyHomework.json";
        ctx = applicationContext;
        serializer = JsonSerializer(homeworkJsonFileName, applicationContext)

        try {
            val file = File(homeworkJsonFileName)
            val doesHomeworkJsonFileExists = file.exists()
            if (!doesHomeworkJsonFileExists) {
                createJsonFileWithEmptyArray(ctx, homeworkJsonFileName)
            }

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

    // create json file with empty array "[]"
    //@Throws(IOException::class)
    fun createJsonFileWithEmptyArray(context: Context?, fileName: String) {
        var writer: Writer? = null
        try {
            val out = context?.openFileOutput(
                    fileName,
                    Context.MODE_PRIVATE
            )
            writer = OutputStreamWriter(out)
            writer.write("[]")
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }

}