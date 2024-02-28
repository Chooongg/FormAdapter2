package com.chooongg.android.form.app

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.android.formAdapter.app.databinding.ActivityFlexBoxDemoBinding
import com.chooongg.android.formAdapter.app.databinding.ItemFlexBoxDemoBinding
import com.chooongg.android.ktx.dp2px
import com.chooongg.android.utils.ColorUtils
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlin.random.Random

class FlexBoxDemoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFlexBoxDemoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = FlexboxLayoutManager(this).apply {
            alignItems = AlignItems.STRETCH
            justifyContent = JustifyContent.CENTER
        }
        binding.recyclerView.addItemDecoration(FlexboxItemDecoration(this).apply {
            setOrientation(FlexboxItemDecoration.BOTH)
        })
        binding.recyclerView.adapter = Adapter()
    }

    private class Adapter : RecyclerView.Adapter<Adapter.Holder>() {

        private class Holder(val binding: ItemFlexBoxDemoBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun getItemCount(): Int = 50

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(
                ItemFlexBoxDemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.itemView.background = ColorDrawable(ColorUtils.getRandomColor(false))
            holder.itemView.updateLayoutParams<FlexboxLayoutManager.LayoutParams> {
                flexGrow = 1f
                width = dp2px(90f) + (0..dp2px(20f)).random()
            }
            holder.binding.tvText.text = generateRandomString((0..50).random())
        }

        fun generateRandomString(length: Int): String {
            val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return (1..length)
                .map { Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        }
    }
}