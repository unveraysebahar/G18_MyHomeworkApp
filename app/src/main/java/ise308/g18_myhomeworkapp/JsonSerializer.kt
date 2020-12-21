package ise308.g18_myhomeworkapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import kotlin.collections.ArrayList

class JsonSerializer (
        private val filename: String,
        private val context: Context) {

    @Throws(IOException::class, JSONException::class)
    fun save(homeworks: ArrayList<Homework>) {
        val jArray = JSONArray()
        for (h in homeworks)
            jArray.put(h.convertToJSON())
        var writer: Writer? = null
        try {
            val out = context.openFileOutput(filename,
                    Context.MODE_PRIVATE)
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
            val jsonString = StringBuilder()
            for (line in reader.readLine()) {
                jsonString.append(line)
            }
            val jArray = JSONTokener(jsonString.toString()).
            nextValue() as JSONArray
            for (i in 0 until jArray.length()) {
                homeworkList.add(Homework(jArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
        } finally {
            reader!!.close()
        }
        return homeworkList
    }

}