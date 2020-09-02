package com.mobi.ezyway_demo

import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobi.ezyway.PaymentActivity
import com.mobi.ezyway.service.network.*
import kotlinx.android.synthetic.main.activity_fpx.*
import kotlinx.android.synthetic.main.activity_fpx.edit_amount
import kotlinx.android.synthetic.main.activity_fpx.edit_city
import kotlinx.android.synthetic.main.activity_fpx.edit_contactName
import kotlinx.android.synthetic.main.activity_fpx.edit_country
import kotlinx.android.synthetic.main.activity_fpx.edit_email
import kotlinx.android.synthetic.main.activity_fpx.edit_invoiceId
import kotlinx.android.synthetic.main.activity_fpx.edit_ip
import kotlinx.android.synthetic.main.activity_fpx.edit_latitude
import kotlinx.android.synthetic.main.activity_fpx.edit_longitude
import kotlinx.android.synthetic.main.activity_fpx.edit_mobileNo
import kotlinx.android.synthetic.main.activity_fpx.edit_orderDesc
import kotlinx.android.synthetic.main.activity_fpx.edit_orderId
import kotlinx.android.synthetic.main.activity_fpx.edit_passport
import kotlinx.android.synthetic.main.activity_fpx.edit_postalCode
import kotlinx.android.synthetic.main.activity_fpx.edit_state
import kotlinx.android.synthetic.main.activity_fpx.edit_street
import org.json.JSONException
import java.net.NetworkInterface


class FPXActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

//    var mobiApiKey = "b07ad9f31df158edb188a41f725899bc" //Sandbox
//    val loginId = "Mobiversa" //Sandbox


    var mobiApiKey = "a787f02ed34fd886eb6d49e60d9c9120" //Live
    val loginId = "MOBI40008" //Live

    var bankType = "01"
    var bankName = ""
    lateinit var dataAdapter: ArrayAdapter<String>

    val paymentActivity: PaymentActivity = PaymentActivity()
    var bankList: ArrayList<String> = ArrayList()
    var corpBankList: List<Bank> = ArrayList()
    var retailBankList: List<BankX> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fpx)

        // Creating adapter for spinner
        dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bankList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bank_spnr.adapter = dataAdapter

        PaymentActivity.getInstance(this, paymentResponse, true)
        paymentActivity.jsonBankList()
        btn_submit.setOnClickListener(this)
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.retail_RB -> {
                    bankType = "01"
                    getBankList("Retail Banking")
                }
                R.id.corparate_RB -> {
                    bankType = "02"
                    getBankList("Corporate Banking")
                }
            }
        }
        bank_spnr.onItemSelectedListener = this

        setValues()
    }

    fun getBankList(bankType: String) {
        when (bankType) {
            "Retail Banking" -> {
                bankList.clear()
                for (data in retailBankList) {
                    bankList.add(data.BankName)
                }
                dataAdapter.notifyDataSetChanged()
            }
            "Corporate Banking" -> {
                bankList.clear()
                for (data in corpBankList) {
                    bankList.add(data.BankName)
                }
                dataAdapter.notifyDataSetChanged()
            }
        }
    }


    //Payment Connection Interface
    private val paymentResponse = object : PaymentResponse {
        override fun setSuccess(success: PaymentResult) {
            val responseCode = success.responseCode
            val description = success.responseDescription
            val mid: String? = success.responseData.mid
            val tid: String = success.responseData.tid
            val amount = success.responseData.amount
            val date: String? = success.responseData.date
            val time: String = success.responseData.time
            val Currency: String? = success.responseData.Currency
            val BuyerName: String? = success.responseData.BuyerName
            val BankType: String? = success.responseData.BankType
            val Bank: String? = success.responseData.Bank
            val trxId: String? = success.responseData.trxId
            val IPAddress: String? = success.responseData.IPAddress
            val MobiLink: String? = success.responseData.MobiLink
            val MerchantName: String? = success.responseData.MerchantName
            val DebitAuthCode: String? = success.responseData.DebitAuthCode
            val DebitAuthCodeString: String? = success.responseData.DebitAuthCodeString
            val CreditAuthCode: String? = success.responseData.CreditAuthCode
            val CreditAuthCodeString: String? = success.responseData.CreditAuthCodeString

        }

        override fun checkBoostStatus(success: BoostStatus) {
            TODO("Not yet implemented")
        }

        override fun getBankList(success: BankListModel) {

            if (success.responseCode.equals("0000")) {
                retailBankList = success.responseDataB2C.bankList
                corpBankList = success.responseDataB2B.bankList

            }

            if (corparate_RB.isChecked)
                getBankList("Corporate Banking")
            else
                getBankList("Retail Banking")
        }

        override fun getBoostData(success: BoostResponse) {
            TODO("Not yet implemented")
        }

        override fun setFailure(failure: String) {
            Toast.makeText(applicationContext, failure, Toast.LENGTH_SHORT).show()
            Log.e("Response Failure", "$failure")
        }
    }


    private fun setValues() {
        edit_ip.setText(getLocalIpAddress())
        edit_passport.setText("N1234567")
        edit_country.setText("Malaysia")
        edit_amount.setText("1.00")
        edit_city.setText("Kuala Lumpur")
        edit_contactName.setText("Mobiversa")
        edit_postalCode.setText("50790")
        edit_latitude.setText("3.1414518")
        edit_longitude.setText("101.6578041")
        edit_mobileNo.setText("01112201234")
        edit_orderId.setText("0192822828")
        edit_street.setText("Damansara Heights")
        edit_invoiceId.setText("Testing")
        edit_state.setText("KL")
        edit_email.setText("karthik@mobiversa.com")
        edit_orderDesc.setText("Android Description")
    }

    private fun getLocalIpAddress(): String? {
        try {
            val en =
                NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr =
                    intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val ip =
                            Formatter.formatIpAddress(inetAddress.hashCode())
                        Log.i("IP", "***** IP=$ip")
                        return ip
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e("IP Address", ex.toString())
        }
        return null
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent!!.getItemAtPosition(position).toString()
        if (retail_RB.isChecked)
            if (retailBankList[position].Active.equals("1", true)) {
                bankName = retailBankList[position].BankCode
            } else {
                if (corpBankList[position].Active.equals("1", true)) {
                    bankName = corpBankList[position].BankCode
                }
            }
    }

    private fun paybyFPX() {
        val requestMap: HashMap<String, String> = HashMap()
        try {
            //SBI Bank A is working for demo
            requestMap["passportNo"] = edit_passport.text.toString()
            requestMap["country"] = edit_country.text.toString()
            val amount = edit_amount.text.toString()
            requestMap["amount"] = amount
            requestMap["city"] = edit_city.text.toString()
            requestMap["contactName"] = edit_contactName.text.toString()
            requestMap["postalCode"] = edit_postalCode.text.toString()
            requestMap["ip"] = edit_ip.text.toString()
            requestMap["latitude"] = edit_latitude.text.toString()
            requestMap["longitude"] = edit_longitude.text.toString()
            requestMap["mobileNo"] = edit_mobileNo.text.toString()
            requestMap["orderId"] = edit_orderId.text.toString()
            requestMap["nameOnCard"] = ""
            requestMap["street"] = edit_street.text.toString()
            requestMap["invoiceId"] = edit_invoiceId.text.toString()
            requestMap["email"] = edit_email.text.toString()
            requestMap["state"] = edit_state.text.toString()
            requestMap["orderDesc"] = edit_orderDesc.text.toString()
            requestMap["mobiApiKey"] = mobiApiKey.trim { it <= ' ' }
            requestMap["loginId"] = loginId.trim { it <= ' ' }
            requestMap["bankType"] = bankType
            requestMap["bank"] = bankName
            requestMap["buyerName"] = edit_contactName.text.toString()

            Log.e("Map", "" + requestMap)
            paymentActivity.jsonPayByFPX(requestMap)

        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit -> {
                paybyFPX()
            }
        }
    }
}