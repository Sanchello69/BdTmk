package com.vas.tmkmanager.tmkstoreroom

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.TmkDatabase
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.databinding.FragmentStoreroomBinding


class StoreroomFragment : Fragment() {

    private lateinit var viewModel: StoreroomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val binding: FragmentStoreroomBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_storeroom, container, false
        )

        val numberStoreroom = arguments?.getInt("id")

        val daoItemStoreroom = TmkDatabase.getInstance(application).getItemStoreroomDao()
        val daoItem = TmkDatabase.getInstance(application).getItemDao()
        val viewModelFactory = StoreroomViewModelFactory(
            daoItemStoreroom, daoItem,
            numberStoreroom!!, application
        )

        val adapterStoreroomItem = StoreroomItemAdapter()

        binding.recyclerViewStoreItem.adapter = adapterStoreroomItem
        binding.textViewStore.text = "Склад № $numberStoreroom"

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StoreroomViewModel::class.java)

        viewModel.itemStorerooms.observe(viewLifecycleOwner, Observer { itemStorerooms ->
            if (itemStorerooms != null)
                adapterStoreroomItem.data = viewModel.itemStorerooms.value!!
        })

        adapterStoreroomItem.onClickListener = object : StoreroomItemAdapter.OnStoreroomItemClickListener{
            override fun onEditClick(storeroomItem: ItemStoreroomForList?) {
                showDialogEdit(storeroomItem, numberStoreroom)
            }

        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.items.observe(viewLifecycleOwner, Observer { items ->
                if (items != null)
                    showDialogAdd(numberStoreroom, items)
            })
        }

        return binding.root
    }

    fun showDialogEdit(storeroomItem: ItemStoreroomForList?, idStore: Int){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Количество:")

        // Set up the input
        val input = EditText(this.context)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(storeroomItem!!.quanity_storeroom.toString())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builderDialog.setView(input)

        // Set up the buttons
        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            if (!input.text.trim().isEmpty()) {
                var editText = input.text.trim().toString().toInt()
                viewModel.onEdit(ItemStoreroom(storeroomItem.id_item, idStore, editText))
            }
        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }

    fun showDialogAdd(idStore: Int, items: List<String>){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Добавить товар:")

        val linLayout = LinearLayout(this.context)

        val spinnerId = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            items
        )
        spinnerId.adapter = adapter

        val inputQuanity = EditText(this.context)
        inputQuanity.setHint("Количество")
        inputQuanity.setText("0")
        inputQuanity.inputType = InputType.TYPE_CLASS_NUMBER

        linLayout.addView(spinnerId)
        linLayout.addView(inputQuanity)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            var editText = inputQuanity.text.trim().toString().toInt()
            var itemName = spinnerId.selectedItem.toString()

            viewModel.itemsData.observe(viewLifecycleOwner, Observer {
                it?.forEach { i ->
                    if (i.name==itemName)
                        viewModel.onAdd(ItemStoreroom(i.id, idStore, editText))
                }
            })
        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }
}