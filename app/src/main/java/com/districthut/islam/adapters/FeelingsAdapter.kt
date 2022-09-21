package com.districthut.islam.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.mianasad.myislam.models.database.feeling.Feeling
import com.mirfatif.noorulhuda.App
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.ItemFeelingBinding


class FeelingsAdapter(var feelings: List<Feeling>, var listener: onFeelingSelected) :
    RecyclerView.Adapter<FeelingsAdapter.FeelingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feeling, parent, false)
        return FeelingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeelingsViewHolder, position: Int) {
        val feeling = feelings[position]

            getDrawable(feeling.image).let {
                holder.binding.image.setImageDrawable(it)
            }

        holder.binding.feeling.text = feeling.mood

        holder.itemView.setOnClickListener { view ->
            listener!!.onSelected(feeling.id, feeling.mood)
//            listener!!.onRecyclerViewItemClicked(
//                position,
//                view.id
//            )
        }
    }

    private fun getDrawable(name: String?): Drawable? {
        val context: Context = App.getCxt()
        val resourceId = context.resources.getIdentifier(
            name,
            "drawable",
            App.getCxt().packageName
        )
        return ActivityCompat.getDrawable(context,resourceId)
    }



    override fun getItemCount(): Int {
        return feelings.size
    }

    inner class FeelingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemFeelingBinding

        init {
            binding = ItemFeelingBinding.bind(itemView)
        }
    }
}

interface onFeelingSelected {
    fun onSelected(id: Long, mood: String)
}