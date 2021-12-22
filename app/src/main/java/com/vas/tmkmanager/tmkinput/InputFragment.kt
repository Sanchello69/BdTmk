package com.vas.tmkmanager.tmkinput

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.TmkDatabase
import com.vas.tmkmanager.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private lateinit var viewModel: TmkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val binding:FragmentInputBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_input, container, false)

        val daoStaffer = TmkDatabase.getInstance(application).getStafferDao()
        val viewModelFactory = TmkViewModelFactory(daoStaffer, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TmkViewModel::class.java)

        binding.button.setOnClickListener {
            var editTextId = binding.editTextId.text.trim().toString().toInt()
            var editTextName = binding.editTextName.text.trim().toString()
            viewModel.onClickCheck(editTextId, editTextName)
        }

        viewModel.navigateToMain.observe(viewLifecycleOwner, Observer { staffer ->
            if (staffer != null) {
                this.findNavController().navigate(R.id.action_inputFragment_to_mainFragment)
                //viewModel.doneNavigating()
            }
            else
                Toast.makeText(context, "Повторите ввод!", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}