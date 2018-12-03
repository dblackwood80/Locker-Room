package com.dblackwood.lockerroom

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.Accessories

class DataAdapter (private val myDataset: MutableList<String>) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    constructor (myDataset: MutableList<String>, n: MutableList<Accessories>) : this(myDataset) {

    }

    val l:DeleteLockerPage = DeleteLockerPage()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        val textView: TextView = v.findViewById(R.id.deleteLockerNames)
    }

    class AccViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        fun AccViewHolder(itemView: View)
        {
            super.itemView
        }
    }


    // Create new views (invoked by the layout manager)
    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        val tv: TextView = view.findViewById(R.id.deleteLockerNames)
        var vh: MyViewHolder(tv)
        return vh
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        //val textView: TextView = view.findViewById(R.id.deleteLockerNames)
        //val vh = MyViewHolder(textView)
        return ViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = myDataset[position]

        /*holder.textView.setOnClickListener(object:View.OnClickListener {
            override fun onClick(view: View)
            {
                if (!holder.textView.isSelected)
                {
                    holder.textView.setBackgroundColor(Color.CYAN)
                    holder.textView.isSelected = true
                    Toast.makeText(view.context, "SELECTED: " + myDataset[position], Toast.LENGTH_LONG).show()
                    l.checked2.add(myDataset[position])//mutableListOf(myDataset[position])
                }
                else
                {
                    holder.textView.setBackgroundColor(Color.TRANSPARENT)
                    holder.textView.isSelected = false
                    Toast.makeText(view.context, "UNSELECTED: " + myDataset[position], Toast.LENGTH_LONG).show()
                    l.checked2.remove(myDataset[position])
                }

                l.pr()

                //val trashCan = l.findViewById<ImageView>(R.id.imageViewTrash)

                if (l.trashCan == null)
                {
                    Log.i("STILL NULL SOMEHOW", "WHY")

                }

               l.trashCan?.setOnClickListener()
                {
                    if (l.checked2.size > 0)
                        l.trashCan?.visibility = View.VISIBLE
                    else
                        l.trashCan?.visibility = View.INVISIBLE

                    Toast.makeText(view.context, "toolbarHeader#click", Toast.LENGTH_SHORT).show()
                }
            }
        })*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}