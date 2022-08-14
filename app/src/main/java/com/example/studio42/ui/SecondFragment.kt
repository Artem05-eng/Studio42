package com.example.studio42.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studio42.R
import com.example.studio42.databinding.SecondScreenBinding
import com.example.studio42.domain.entity.EmloyerType
import com.example.studio42.domain.entity.RequestEmployer
import com.example.studio42.presentation.SecondViewModel
import com.example.studio42.ui.adapter.EmployerAdapter
import com.example.studio42.util.Listener
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SecondFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SecondViewModel
    private var binding: SecondScreenBinding? = null
    private val args: SecondFragmentArgs by navArgs()
    private var listAdapter: EmployerAdapter? = null
    private var requestBody = RequestEmployer("", "", false)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SecondViewModel::class.java]
        requestBody = args?.requestData
        listener(requestBody)
        binding?.filterButton?.setOnClickListener {
            findNavController().navigate(
                SecondFragmentDirections.actionSecondFragmentToThirdFragment(requestBody)
            )
        }
        binding?.resultList?.apply {
            listAdapter = EmployerAdapter()
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.data.observe(viewLifecycleOwner, {
            listAdapter?.list = it
        })
        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "Network error! Try again!", Toast.LENGTH_SHORT).show()
        })
        binding?.textField?.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                requestBody.text = p0.toString()
                viewModel.getEmploers(requestBody.text, requestBody.type, requestBody.flag)
                if (p0.toString().isNotEmpty()) {
                    viewModel.checkFlag()
                } else {
                    viewModel.uncheckFlag()
                }
            }
        })
        binding?.chip?.setOnCloseIconClickListener {
            requestBody = RequestEmployer("", "", false)
            viewModel.getEmploers(requestBody.text, requestBody.type, requestBody.flag)
            it.isGone = true
            binding?.textField?.editText?.setText("")
            viewModel.uncheckFlag()
        }
        viewModel.flagFilter.observe(viewLifecycleOwner, { flag ->
            if (flag) {
                binding?.chip?.isGone = false
                binding?.filterButton?.setBackgroundColor(resources.getColor(R.color.blue_42))
                binding?.filterButton?.setImageDrawable(resources.getDrawable(R.drawable.filter_light))
            } else {
                binding?.chip?.isGone = true
                binding?.filterButton?.setBackgroundColor(resources.getColor(R.color.grey_42))
                binding?.filterButton?.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_filter_alt_24))
            }
        })
        viewModel.count.observe(viewLifecycleOwner, {
            binding?.chip?.text = "Найдено ${it} работодателей"
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        listAdapter = null
    }

    private fun listener(data: RequestEmployer) {
        requestBody = data
        if (data != RequestEmployer("", "", false)) {
            viewModel.checkFlag()
        } else {
            viewModel.uncheckFlag()
        }
        binding?.textField?.editText?.setText(data.text)
        viewModel.getEmploers(data.text, data.type, data.flag)
    }
}