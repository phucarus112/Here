package com.myapp.werhere.Adapter

import android.content.Context
import android.media.Rating
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.myapp.werhere.Object.Place
import com.myapp.werhere.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_result.view.*

class PlaceAdapter(var context : Context, var placeList: ArrayList<Place> ) : BaseAdapter() {

    class ViewHolder(row: View){
        var icon : ImageView
        var name : TextView
        var address : TextView
        var rating : RatingBar
        var num : TextView
        var direct: LinearLayout

        init{
            icon = row.findViewById(R.id.row_result_icon) as ImageView
            name = row.findViewById(R.id.row_result_name) as TextView
            address = row.findViewById(R.id.row_result_address) as TextView
            num = row.findViewById(R.id.row_result_num) as TextView
            rating = row.findViewById(R.id.row_result_rating) as RatingBar
            direct = row.findViewById(R.id.row_result_direct) as LinearLayout
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view: View?
        var viewholder : ViewHolder
        if(convertView == null){
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.row_result,null)
            viewholder = ViewHolder(view)
            view.tag = viewholder
        }else{
           view = convertView
            viewholder = convertView.tag as ViewHolder
        }
        var row : Place = getItem(position) as Place
        Picasso.get().load(row.icon).placeholder(R.drawable.load).into(viewholder.icon)
        viewholder.name.text = row.name
        viewholder.address.text = row.address
        viewholder.num.text = "("+row.total_rating_turns+")"
        viewholder.direct.setOnClickListener {
            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show()
        }
        viewholder.rating.rating = row.rating.toFloat()
        return view
    }

    override fun getItem(position: Int): Any {
        return placeList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return placeList.size
    }
}