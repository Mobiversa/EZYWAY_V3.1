package com.mobi.ezyway_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.widget.Toast
import com.mobi.ezyway.PaymentActivity
import com.mobi.ezyway.service.network.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.net.NetworkInterface

class MainActivity : AppCompatActivity() {

    private var mobiApiKey = "b07ad9f31df158edb188a41f725899bc" //Sandbox
    private val loginId = "Mobiversa" //Sandbox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var name = loginId.substring(loginId.length-1)

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
        edit_expectedDate.setText("25-Jul-2018")
        edit_orderId.setText("0192822828")
        edit_nameOnCard.setText("Mobiversa")
        edit_street.setText("Damansara Heights")
        edit_invoiceId.setText("Testing")
        edit_state.setText("KL")
        edit_cardNumber.setText("4918914107195005")
        edit_cardCVV.setText("123")
        edit_cardExpirymonth.setText("12")
        edit_cardExpiryYear.setText("20")
        edit_email.setText("karthik@mobiversa.com")
        edit_orderDesc.setText("Android Description")

        PaymentActivity.getInstance(this, paymentResponse,false)
        val paymentActivity: PaymentActivity = PaymentActivity()

        btn_submit.setOnClickListener {

            val requestMap: HashMap<String, String> = HashMap()
            try {
                requestMap["passportNo"] = edit_passport.text.toString()
                requestMap["country"] = edit_country.text.toString()
                val amount = edit_amount.text.toString()
//                val amount_to_send =
//                        String.format("%012d", amount.replace(".", "").toInt())
                requestMap["amount"] = amount
                requestMap["city"] = edit_city.text.toString()
                requestMap["contactName"] = edit_contactName.text.toString()
                requestMap["postalCode"] = edit_postalCode.text.toString()
                requestMap["ip"] = edit_ip.text.toString()
                requestMap["latitude"] = edit_latitude.text.toString()
                requestMap["longitude"] = edit_longitude.text.toString()
                requestMap["mobileNo"] = edit_mobileNo.text.toString()
                requestMap["expectedDate"] = edit_expectedDate.text.toString()
                requestMap["orderId"] = edit_orderId.text.toString()
                requestMap["nameOnCard"] = edit_nameOnCard.text.toString()
                requestMap["street"] = edit_street.text.toString()
                requestMap["invoiceId"] = edit_invoiceId.text.toString()
                requestMap["email"] = edit_email.text.toString()
                requestMap["state"] = edit_state.text.toString()
                requestMap["orderDesc"] = edit_orderDesc.text.toString()
                //                    requestJSON.put("carddetails", carddetails);


            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val CardNumber = edit_cardNumber.text.toString()
            val CVVNumber = edit_cardCVV.text.toString()
            val ExpiryMonth = edit_cardExpirymonth.text.toString()
            val ExpiryYear = edit_cardExpiryYear.text.toString()

            val cardData: HashMap<String, String> = HashMap()
            cardData["mobiApiKey"] = mobiApiKey.trim { it <= ' ' }
            cardData["loginId"] = loginId.trim { it <= ' ' }
            cardData["CardNumber"] = CardNumber
            cardData["CVVNumber"] = CVVNumber
            cardData["ExpiryMonth"] = ExpiryMonth
            cardData["ExpiryYear"] = ExpiryYear

            paymentActivity.jsonPayByCard(requestMap, cardData)
        }
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

    //Payment Connection Interface
    private val paymentResponse = object: PaymentResponse {
        override fun setSuccess(success: PaymentResult) {
            val responseCode = success.responseCode
            val description = success.responseDescription
            val amount = success.responseData.amount
            val approveCode: String= success.responseData.approveCode.toString()
            val batchNo: String= success.responseData.batchNo.toString()
            val cardHolderName: String? = success.responseData.cardHolderName
            val cardNo: String?= success.responseData.cardNo
            val date: String?= success.responseData.date
            val invoiceId: String?= success.responseData.invoiceId
            val latitude: String?= success.responseData.latitude
            val longitude: String?= success.responseData.longitude
            val mid: String?= success.responseData.mid
            val orderDesc: String?= success.responseData.orderDesc
            val orderId: String?= success.responseData.orderId
            val rrn: String?= success.responseData.rrn
            val tc: String?= success.responseData.tc
            val tid: String= success.responseData.tid
            val time: String= success.responseData.time
            val tips: String?= success.responseData.tips
            val trace: String?= success.responseData.trace
            val trxId: String?= success.responseData.trxId
            val txnType: String?= success.responseData.txnType
        }

        override fun checkBoostStatus(success: BoostStatus) {
            TODO("Not yet implemented")
        }

        override fun getBankList(success: BankListModel) {

        }

        override fun getBoostData(success: BoostResponse) {
            TODO("Not yet implemented")
        }

        override fun setFailure(failure: String) {
            Toast.makeText(applicationContext,failure,Toast.LENGTH_SHORT).show()
            Log.e("Response Failure", "$failure")
        }
    }
}