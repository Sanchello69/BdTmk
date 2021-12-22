package com.vas.tmkmanager.tmkmain

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.TmkDatabase
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.database.entities.Staffer
import com.vas.tmkmanager.database.entities.Storeroom
import com.vas.tmkmanager.databinding.FragmentInputBinding
import com.vas.tmkmanager.databinding.FragmentMainBinding
import com.vas.tmkmanager.tmkinput.TmkViewModel
import com.vas.tmkmanager.tmkinput.TmkViewModelFactory


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var clSt = true
        var clCl = true
        var clStRm = true

        val bundle = Bundle()

        val application = requireNotNull(this.activity).application
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main, container, false)

        val daoStaffer = TmkDatabase.getInstance(application).getStafferDao()
        val daoClient = TmkDatabase.getInstance(application).getClientDao()
        val daoStoreroom = TmkDatabase.getInstance(application).getStoreroomDao()
        val viewModelFactory = MainViewModelFactory(daoStaffer, daoClient, daoStoreroom, application)

        val adapterStaffer = StafferAdapter()
        val adapterClient = ClientAdapter()
        val adapterStoreroom = StoreroomAdapter()

        binding.stafferList.adapter = adapterStaffer
        binding.clientList.adapter = adapterClient
        binding.storeList.adapter = adapterStoreroom

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        viewModel.staffers.observe(viewLifecycleOwner, Observer { staffers ->
            if (staffers != null)
                adapterStaffer.data = staffers
        })

        viewModel.clients.observe(viewLifecycleOwner, Observer { clients ->
            if (clients != null)
                adapterClient.data = clients
        })

        viewModel.storerooms.observe(viewLifecycleOwner, Observer { storerooms ->
            if (storerooms != null)
                adapterStoreroom.data = storerooms
        })

        binding.buttonAddStaffer.setOnClickListener {
            showDialogAddStaffer()
        }

        binding.buttonAddClient.setOnClickListener {
            showDialogAddClient()
        }

        adapterStaffer.onClickListener = object : StafferAdapter.OnStafferClickListener{
            override fun onInfoClick(staffer: Staffer?) {
                showDialogInfoStaffer(staffer!!)
            }

            override fun onDeleteClick(staffer: Staffer?) {
                    viewModel.onDelete(staffer!!)
                }

            }

        adapterClient.onClickListener = object : ClientAdapter.OnClientClickListener{
            override fun onInfoClick(client: Client?) {
                bundle.putInt("idClient", client!!.id)
                findNavController().navigate(R.id.action_mainFragment_to_clientFragment, bundle)
            }

            override fun onEditClick(client: Client?) {
                showDialogEditClient(client!!)
            }

            override fun onDeleteClick(client: Client?) {
                viewModel.onDeleteClient(client!!)
            }

        }

        adapterStoreroom.onClickListener = object : StoreroomAdapter.OnStoreroomClickListener{
            override fun onInfoClick(storeroom: Storeroom?) {
                bundle.putInt("id", storeroom!!.number)
                findNavController().navigate(R.id.action_mainFragment_to_storeroomFragment, bundle)
            }
        }

        binding.textViewStaffer.setOnClickListener {
            if (clSt){
                binding.stafferList.visibility = View.VISIBLE
                clSt = false
            }
            else{
                binding.stafferList.visibility = View.GONE
                clSt = true
            }
        }

        binding.textViewClient.setOnClickListener {
            if (clCl){
                binding.clientList.visibility = View.VISIBLE
                clCl = false
            }
            else{
                binding.clientList.visibility = View.GONE
                clCl = true
            }
        }

        binding.textViewStoreRoom.setOnClickListener {
            if (clStRm){
                binding.storeList.visibility = View.VISIBLE
                clStRm = false
            }
            else{
                binding.storeList.visibility = View.GONE
                clStRm = true
            }
        }
        return binding.root
    }

    fun showDialogAddStaffer(){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Добавить сотрудника:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        val spinnerId = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Менеджер", "Кладовщик", "Монтажник")
        )
        spinnerId.adapter = adapter

        val inputName = EditText(this.context)
        inputName.setHint("Фамилия И. О.")
        inputName.inputType = InputType.TYPE_CLASS_TEXT

        val inputEmail = EditText(this.context)
        inputEmail.setHint("Почта")
        inputEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        val inputPhone = EditText(this.context)
        inputPhone.setHint("Телефон")
        inputPhone.inputType = InputType.TYPE_CLASS_NUMBER

        linLayout.addView(inputName)
        linLayout.addView(inputPhone)
        linLayout.addView(inputEmail)
        linLayout.addView(spinnerId)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            var editName = inputName.text.trim().toString()
            var editPhone = inputPhone.text.trim().toString()
            var editEmail = inputEmail.text.trim().toString()
            var type = spinnerId.selectedItem.toString()
            if (!editName.isEmpty()&&!editPhone.isEmpty()&&!editEmail.isEmpty()&&editPhone.length == 11&&editPhone.substring(0,1) == "8")
                viewModel.onAdd(Staffer(editName, editEmail, editPhone, type))
            else
                Toast.makeText(this.context, "Повторите ввод!", Toast.LENGTH_SHORT).show()

        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }

    fun showDialogInfoStaffer(staffer: Staffer){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Информация о сотруднике:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        val inputName = TextView(this.context)
        inputName.text = staffer.name.toString()

        val inputEmail = TextView(this.context)
        inputEmail.text = staffer.email.toString()

        val inputPhone = TextView(this.context)
        inputPhone.text = staffer.phone.toString()

        linLayout.addView(inputName)
        linLayout.addView(inputPhone)
        linLayout.addView(inputEmail)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener {
                dialog, which -> dialog.cancel() })

        builderDialog.show()
    }

    fun showDialogAddClient(){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Добавить клиента:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        val spinner = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Физ", "Юр")
        )
        spinner.adapter = adapter

        val inputName = EditText(this.context)
        inputName.setHint("Имя")
        inputName.inputType = InputType.TYPE_CLASS_TEXT

        val inputAddress = EditText(this.context)
        inputAddress.setHint("Адрес")
        inputAddress.inputType = InputType.TYPE_CLASS_TEXT

        val inputPhone = EditText(this.context)
        inputPhone.setHint("Телефон")
        inputPhone.inputType = InputType.TYPE_CLASS_NUMBER

        linLayout.addView(inputName)
        linLayout.addView(inputAddress)
        linLayout.addView(inputPhone)
        linLayout.addView(spinner)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            var editName = inputName.text.trim().toString()
            var editPhone = inputPhone.text.trim().toString()
            var editAddress = inputAddress.text.trim().toString()
            var type = spinner.selectedItem.toString()
            if (!editName.isEmpty()&&!editPhone.isEmpty()&&!editAddress.isEmpty()&&editPhone.length == 11&&editPhone.substring(0,1) == "8")
                viewModel.onAddClient(Client(editName, editPhone, editAddress, type ))
            else
                Toast.makeText(this.context, "Повторите ввод!", Toast.LENGTH_SHORT).show()

        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }

    fun showDialogEditClient(client: Client){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Редактировать клиента:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        val spinner = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Физ", "Юр")
        )
        spinner.adapter = adapter

        val inputName = EditText(this.context)
        inputName.setHint(client.name.toString())
        inputName.inputType = InputType.TYPE_CLASS_TEXT
        inputName.setText(client.name.toString())

        val inputAddress = EditText(this.context)
        inputAddress.setHint(client.address.toString())
        inputAddress.inputType = InputType.TYPE_CLASS_TEXT
        inputAddress.setText(client.address.toString())

        val inputPhone = EditText(this.context)
        inputPhone.setHint(client.phone)
        inputPhone.inputType = InputType.TYPE_CLASS_NUMBER
        inputPhone.setText(client.phone)

        linLayout.addView(inputName)
        linLayout.addView(inputAddress)
        linLayout.addView(inputPhone)
        linLayout.addView(spinner)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            var editName = inputName.text.trim().toString()
            var editPhone = inputPhone.text.trim().toString()
            var editAddress = inputAddress.text.trim().toString()
            var type = spinner.selectedItem.toString()
            if (!editName.isEmpty()&&!editPhone.isEmpty()&&!editAddress.isEmpty()&&editPhone.length == 11&&editPhone.substring(0,1) == "8")
                viewModel.onEdit(client.id, editName, editAddress, editPhone, type)
            else
                Toast.makeText(this.context, "Повторите ввод!", Toast.LENGTH_SHORT).show()

        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }
}