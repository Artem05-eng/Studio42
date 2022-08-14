package com.example.studio42.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studio42.databinding.CardWorkBinding
import com.example.studio42.domain.entity.Employer

class EmployerAdapter : RecyclerView.Adapter<EmployerHolder>() {

    var list = emptyList<Employer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployerHolder {
        val binding = CardWorkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployerHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployerHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class EmployerHolder(
    private val binding: CardWorkBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Employer) {
        binding.companyName?.text = data.name
        binding.vacancyInfo?.text = "Вакансий в компании: ${data.open_vacancies}"
        Glide.with(binding.root)
            .load(data.logo_urls?.original)
            .centerCrop()
            .into(binding.companyLogo)
    }
}