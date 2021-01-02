package ise308.g18_myhomeworkapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.*
import java.util.stream.Collectors

class JsonSerializer(
        private val filename: String,
        private val context: Context
) {

    @Throws(IOException::class, JSONException::class)
    fun save(homework: ArrayList<Homework>) {
        val jArray = JSONArray()
        for (h in homework)
            jArray.put(h.convertToJSON())
        var writer: Writer? = null
        try {
            val out = context.openFileOutput(
                    filename,
                    Context.MODE_PRIVATE
            )
            writer = OutputStreamWriter(out)
            writer.write(jArray.toString())
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun load(): ArrayList<Homework> {
        val homeworkList = ArrayList<Homework>()
        var reader: BufferedReader? = null
        try {
            val `in` = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(`in`))
            val jsonContent : String =  reader.lines().collect(Collectors.joining());
            val jArray = JSONTokener(jsonContent).nextValue() as JSONArray
            for (i in 0 until jArray.length()) {
                homeworkList.add(Homework(jArray.getJSONObject(i)))
            }
            homeworkList[0].title = homeworkList[0].title;
        } catch (e: FileNotFoundException) {
        } finally {
            reader!!.close()
        }
        return homeworkList
    }

}