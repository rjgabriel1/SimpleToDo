package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tell the recyclerView how to display the data we give it
 */
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

//Usually involves inflating a layout from XML and returning the holder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // inflate the custom layout
        val taskView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
        //Returna new instance
        return ViewHolder(taskView)
    }
 // involves populating data into the item through holder
    override fun onBindViewHolder(holder: TaskItemAdapter.ViewHolder, position: Int) {

     // Get data model base on position
     val item = listOfItems.get(position)

     holder.textView.text = item

    }

    override fun getItemCount(): Int {
         return listOfItems.size
    }
    //provide a direct reference to each of the views within a data item
    //Used to cache the views within the item layout for fast access

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Stores references to element in our layout view
        val textView : TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener{
               longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

}