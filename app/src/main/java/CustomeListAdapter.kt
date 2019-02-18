package com.jackperryjr.mooglekt

import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.content.Context
import android.view.View

class CustomListAdapter(internal var context: Context, private val listData: ArrayList<Message>) : BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(position: Int): Any {
        return listData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null)
            holder = ViewHolder()
            holder.messageView = convertView!!.findViewById(R.id.message)
            holder.dateView = convertView!!.findViewById(R.id.date)
            convertView!!.setTag(holder)
        } else {
            //holder = convertView.getTag()
        }

        holder?.messageView!!.setText(listData[position].message)
        holder?.dateView!!.setText(listData[position].date)
        return convertView
    }

    internal class ViewHolder {
        var messageView: TextView? = null
        var dateView: TextView? = null
    }
}