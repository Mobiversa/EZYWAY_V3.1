package com.mobi.ezyway_demo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.mobi.ezyway.PaymentActivity
import com.mobi.ezyway.service.network.*
import kotlinx.android.synthetic.main.activity_boost.*
import kotlinx.android.synthetic.main.activity_main.*

class BoostActivity : AppCompatActivity() {

    var mobiApiKey = "b07ad9f31df158edb188a41f725899bc" //Sandbox
    val loginId = "Mobiversa" //Sandbox

    var orderId = ""
    var trxId = ""
    var aid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boost)

        PaymentActivity.getInstance(this, paymentResponse,false)
        val paymentActivity: PaymentActivity = PaymentActivity()
        val requestMap: HashMap<String, String> = HashMap()
        requestMap["amount"] = "1.00"
        requestMap["username"] = "Mobi Demo"
        requestMap["invoiceId"] = "ORDER001"
        requestMap["mobiApiKey"] = mobiApiKey.trim { it <= ' ' }
        requestMap["loginId"] = loginId.trim { it <= ' ' }
        paymentActivity.jsonBoostTransaction(requestMap)

        status_txt.setOnClickListener {
            val requestMap : HashMap<String,String> = HashMap()
            requestMap["orderId"] = orderId
            requestMap["trxId"] = trxId
            requestMap["aid"] = aid
            paymentActivity.jsonCheckBoostStatus(requestMap)
        }
    }

    //Payment Connection Interface
    private val paymentResponse = object: PaymentResponse {
        override fun setSuccess(success: PaymentResult) {
            TODO("Not yet implemented")
        }

        override fun checkBoostStatus(success: BoostStatus) {
            Log.e("Response Failure", "$success")
        }

        override fun getBankList(success: BankListModel) {
            TODO("Not yet implemented")
        }

        override fun getBoostData(success: BoostResponse) {
            val orderId = success.responseData.orderId
            val aid = success.responseData.aid
            val trxId = success.responseData.trxId
            val callBackUrl = success.responseData.url
            val qrImage = success.responseData.base64ImageQRCode
            qr_scanner_img.setImageBitmap(success.responseData.base64ImageQRCode.decodeBase64IntoBitmap())
        }

        override fun setFailure(failure: String) {
            Toast.makeText(applicationContext,failure, Toast.LENGTH_SHORT).show()
            Log.e("Response Failure", "$failure")
        }
    }

    fun String.decodeBase64IntoBitmap(): Bitmap {
        val imageBytes = Base64.decode(this, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            imageBytes, 0, imageBytes.size
        )
    }
}