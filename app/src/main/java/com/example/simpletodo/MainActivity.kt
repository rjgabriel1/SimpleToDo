package com.example.simpletodo
import org.apache.commons.io.FileUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. delete item from the list
                listOfTasks.removeAt(position)
                //2.Notify the adapter the data set has changed
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }
        //1. Let's detect when the user press button

//        findViewById<Button>(R.id.button).setOnClickListener{
//            Log.i("Gabe","User clicked")
//        }
        loadItem()
    // Look up the recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // create adapter passing in the sample user data
         adapter =  TaskItemAdapter(listOfTasks, onLongClickListener)

        // attach the adapter to recyclerView
        recyclerView.adapter = adapter
        // set layout manager

        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input field, so that te user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get reference to the button
        //and then set onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {

            // 1.Grab the task the user has inputted into the text field @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2.Add the string to the list of task
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size -1)

            // 3. Reset the entry field
            inputTextField.setText("")

            saveItems()
        }
    }
    // Save the data that the user has entered
    //save data by writing an reading from the file
    
    // Get the file we need
     fun getDataFile(): File {
        
        // Each line represent a TASK in our TaskList
         return File(filesDir,"data.txt")
         
     }
   
    
    // Load the items by reading every lines in the file
    fun loadItem(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    
    // Save items by writing them into files

    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}