package com.districthut.islam.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Typeface
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData

import com.mianasad.myislam.models.database.HolyName
import com.mirfatif.noorulhuda.App
import com.mirfatif.noorulhuda.R
import com.mirfatif.noorulhuda.databinding.RowNamesBinding
import java.util.*

/**
 * Created by Sadmansamee on 7/19/15.
 */
class NamesAdapter(val names: List<HolyName>) :
    RecyclerView.Adapter<NamesAdapter.NamesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NamesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_names, parent, false)
        return NamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        val name = names[position]

        holder.binding.name.text = name.name
        holder.binding.transliteration.text = name.transliteration
        holder.binding.engMeaning.text = name.english
        holder.binding.urduMeaning.text = name.urdu
        if (name.urdu == "") holder.binding.engMeaning.gravity = View.TEXT_ALIGNMENT_CENTER
        //holder.textView.setText(namesModels.get(position));
        holder.binding.share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = String.format(
                """
    ${name.name}
    ${name.transliteration}
    ${name.urdu}
    ${name.english}
    """.trimIndent()
            )
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, App.getRes().getString(R.string.app_name))
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            holder.binding.root.context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
            //context.startActivity(Intent.createChooser(sharingIntent,"Share using"));
        }
    }

    val color: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(130), rnd.nextInt(70), rnd.nextInt(100))
        }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int): Any {
        return names[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }

    inner class NamesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: RowNamesBinding

        init {
            binding = RowNamesBinding.bind(view)
        }
    }
}