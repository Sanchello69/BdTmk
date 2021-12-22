package com.vas.tmkmanager.tmkclient

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
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.TmkDatabase
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.OrderTMK
import com.vas.tmkmanager.database.entities.Staffer
import com.vas.tmkmanager.databinding.FragmentClientBinding
import com.vas.tmkmanager.tmkmain.StafferAdapter

class ClientFragment : Fragment() {

    private lateinit var viewModel: ClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = Bundle()

        val application = requireNotNull(this.activity).application
        val binding: FragmentClientBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_client, container, false)

        val idClient = arguments?.getInt("idClient")

        val daoOrder = TmkDatabase.getInstance(application).getOrderTmkDao()
        val daoClient = TmkDatabase.getInstance(application).getClientDao()
        val viewModelFactory = ClientViewModelFactory(daoClient, daoOrder, idClient!!.toInt(), application)

        val adapterOrder = OrderAdapter()
        binding.recyclerViewOrder.adapter = adapterOrder

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ClientViewModel::class.java)

        viewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            if (orders != null)
                adapterOrder.data = orders
        })

        viewModel.client.observe(viewLifecycleOwner, Observer { client ->
            if (client != null) {
                binding.textViewName.text = client.name.toString()
                binding.textViewAddress.text = client.address.toString()
                binding.textViewPhone.text = "Номер телефона: " + client.phone.toString()
                if (client.type.toString()=="Физ")
                    binding.textViewType.text = "Физическое лицо"
                else
                    binding.textViewType.text = "Юридическое лицо"
            }
        })

        binding.floatingActionButtonOrder.setOnClickListener {
            showDialogAddOrder(idClient)
        }

        adapterOrder.onClickListener = object : OrderAdapter.OnOrderClickListener{
            override fun onEditClick(order: OrderTMK?) {
                showDialogEditOrder(order!!)
            }

            override fun onDeleteClick(order: OrderTMK?) {
                Log.d("click", "yes")
                viewModel.onDeleteOrder(order!!)
            }
        }

        return binding.root
    }

    fun showDialogEditOrder(orderTMK: OrderTMK){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Редактировать заказ:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        /*val spinner = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Физ", "Юр")
        )
        spinner.adapter = adapter*/

        val inputDataOrder = EditText(this.context)
        inputDataOrder.setHint(orderTMK.dataOrder.toString())
        inputDataOrder.inputType = InputType.TYPE_CLASS_TEXT
        inputDataOrder.setText(orderTMK.dataOrder.toString())

        val inputDataPay = EditText(this.context)
        inputDataPay.setHint(orderTMK.dataPay.toString())
        inputDataPay.inputType = InputType.TYPE_CLASS_TEXT
        inputDataPay.setText(orderTMK.dataPay.toString())

        val inputDataDelivery = EditText(this.context)
        inputDataDelivery.setHint(orderTMK.dataDelivery.toString())
        inputDataDelivery.inputType = InputType.TYPE_CLASS_TEXT
        inputDataDelivery.setText(orderTMK.dataDelivery.toString())

        val inputStatus = EditText(this.context)
        inputStatus.setHint(orderTMK.status.toString())
        inputStatus.inputType = InputType.TYPE_CLASS_TEXT
        inputStatus.setText(orderTMK.status.toString())

        val inputSum = EditText(this.context)
        inputSum.setHint(orderTMK.sum.toString())
        inputSum.inputType = InputType.TYPE_CLASS_NUMBER
        inputSum.setText(orderTMK.sum.toString())

        val inputAmount = EditText(this.context)
        inputAmount.setHint(orderTMK.amount.toString())
        inputAmount.inputType = InputType.TYPE_CLASS_NUMBER
        inputAmount.setText(orderTMK.amount.toString())

        linLayout.addView(inputDataOrder)
        linLayout.addView(inputDataPay)
        linLayout.addView(inputDataDelivery)
        linLayout.addView(inputStatus)
        linLayout.addView(inputSum)
        linLayout.addView(inputAmount)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            var editDataOrder = inputDataOrder.text.trim().toString()
            var editDataPay = inputDataPay.text.trim().toString()
            var editDataDelivery = inputDataDelivery.text.trim().toString()
            var editStatus = inputStatus.text.trim().toString()
            var editSum = inputSum.text.trim().toString().toDouble()
            var editAmount = inputAmount.text.trim().toString().toInt()
            viewModel.onEdit(
                OrderTMK(orderTMK.idOrder, orderTMK.idClient, orderTMK.idItem, editDataOrder,
                editDataPay, editDataDelivery, editStatus, editSum, editAmount)
            )
            //var type = spinner.selectedItem.toString()

        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }

    fun showDialogAddOrder(idClient: Int){
        val builderDialog: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
        builderDialog.setTitle("Добавить заказ:")

        val linLayout = LinearLayout(this.context)
        linLayout.orientation = LinearLayout.VERTICAL

        /*val spinner = Spinner(this.context)
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Физ", "Юр")
        )
        spinner.adapter = adapter*/

        val inputIdOrder = EditText(this.context)
        inputIdOrder.setHint("ID заказа")
        inputIdOrder.inputType = InputType.TYPE_CLASS_NUMBER

        val inputIdItem = EditText(this.context)
        inputIdItem.setHint("ID товара")
        inputIdItem.inputType = InputType.TYPE_CLASS_NUMBER

        val inputDataOrder = EditText(this.context)
        inputDataOrder.setHint("Дата заказа")
        inputDataOrder.inputType = InputType.TYPE_CLASS_TEXT

        val inputDataPay = EditText(this.context)
        inputDataPay.setHint("Дата оплаты")
        inputDataPay.inputType = InputType.TYPE_CLASS_TEXT

        val inputDataDelivery = EditText(this.context)
        inputDataDelivery.setHint("Дата доставки")
        inputDataDelivery.inputType = InputType.TYPE_CLASS_TEXT

        val inputStatus = EditText(this.context)
        inputStatus.setHint("Статус заказа")
        inputStatus.inputType = InputType.TYPE_CLASS_TEXT

        val inputSum = EditText(this.context)
        inputSum.setHint("Сумма заказа")
        inputSum.inputType = InputType.TYPE_CLASS_NUMBER

        val inputAmount = EditText(this.context)
        inputAmount.setHint("Количество товара")
        inputAmount.inputType = InputType.TYPE_CLASS_NUMBER

        linLayout.addView(inputIdOrder)
        linLayout.addView(inputIdItem)
        linLayout.addView(inputDataOrder)
        linLayout.addView(inputDataPay)
        linLayout.addView(inputDataDelivery)
        linLayout.addView(inputStatus)
        linLayout.addView(inputSum)
        linLayout.addView(inputAmount)
        builderDialog.setView(linLayout)

        builderDialog.setPositiveButton("ОК", DialogInterface.OnClickListener { dialog, which ->
            if (!(inputAmount.text.trim().toString().isBlank() || inputDataDelivery.text.trim().toString().isBlank() || inputDataOrder.text.trim().toString().isBlank() || inputDataPay.text.trim().toString().isBlank()
                        || inputIdItem.text.trim().toString().isBlank() || inputIdOrder.text.trim().toString().toInt()==0 || inputStatus.text.trim().toString().isBlank() || inputSum.text.trim().toString().isBlank())){

                var editIdOrder = inputIdOrder.text.trim().toString().toInt()
                var editIdItem = inputIdItem.text.trim().toString().toInt()
                var editDataOrder = inputDataOrder.text.trim().toString()
                var editDataPay = inputDataPay.text.trim().toString()
                var editDataDelivery = inputDataDelivery.text.trim().toString()
                var editStatus = inputStatus.text.trim().toString()
                var editSum = inputSum.text.trim().toString().toDouble()
                var editAmount = inputAmount.text.trim().toString().toInt()

                if (editIdItem>0 && editIdItem<=10)
                    viewModel.onAdd(
                        OrderTMK(editIdOrder, idClient, editIdItem, editDataOrder, editDataPay,
                        editDataDelivery, editStatus, editSum, editAmount)
                    )
                else
                    Toast.makeText(this.context, "Повторите ввод!", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this.context, "Повторите ввод!", Toast.LENGTH_SHORT).show()
            //var type = spinner.selectedItem.toString()

        })
        builderDialog.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builderDialog.show()
    }
}