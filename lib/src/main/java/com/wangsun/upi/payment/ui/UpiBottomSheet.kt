package com.wangsun.upi.payment.ui


import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wangsun.upi.payment.R
import com.wangsun.upi.payment.UpiPayment
import com.wangsun.upi.payment.adapter.UpiListAdapter
import kotlinx.android.synthetic.main.wangsun_upi_payment_upi_bottom_sheet.*
import org.jetbrains.anko.AnkoLogger
import java.util.*

/**
 * Created by WANGSUN on 26-Sep-19.
 */

class UpiBottomSheet : BottomSheetDialogFragment(),AnkoLogger {

    private var mAdapter = UpiListAdapter()
    private var mOnUpiTypeSelectedListener: OnUpiTypeSelectedListener? =null
    private var mUpiApps: ArrayList<String> = arrayListOf()

    private lateinit var mUri: Uri

    override fun getTheme(): Int {
        return R.style.wangsun_style_Dialog_BottomSheet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUri = Uri.parse(arguments!!.getString("uri"))
        mUpiApps = arguments!!.getStringArrayList(UpiPayment.ARG_UPI_APPS_LIST)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //dialog.setCanceledOnTouchOutside(false)
        return inflater.inflate(R.layout.wangsun_upi_payment_upi_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getAllUpiApps()

        upi_bottom_sheet_img_cancel_btn.setOnClickListener {
            mOnUpiTypeSelectedListener?.onUpiAppClosed()
            this.dismiss()
        }

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun getAllUpiApps() {
        val paymentIntent = Intent(Intent.ACTION_VIEW)
        paymentIntent.data = mUri
        val appList = context!!.packageManager.queryIntentActivities(paymentIntent, 0)


        if(mUpiApps.isNotEmpty()){
            val finalList: MutableList<ResolveInfo> = mutableListOf()
            for(i in appList){
                if(mUpiApps.contains(i.loadLabel(context!!.packageManager).toString().toLowerCase()))
                    finalList.add(i)
            }
            mAdapter.setData(finalList)
        }
        else{
            mAdapter.setData(appList)
        }

    }

    private fun initAdapter() {
        mAdapter.setListener(object : UpiListAdapter.OnUpiListItemListener{
            override fun onItemClick(data: ResolveInfo) {
                mOnUpiTypeSelectedListener?.onUpiAppSelected(data)
                this@UpiBottomSheet.dismiss()
            }
        })

        upi_bottom_sheet_recycler_view.layoutManager = LinearLayoutManager(context)
        upi_bottom_sheet_recycler_view.adapter = mAdapter
    }


    fun setListener(pListener: OnUpiTypeSelectedListener?) {
        mOnUpiTypeSelectedListener = pListener
    }


    interface OnUpiTypeSelectedListener {
        fun onUpiAppSelected(data: ResolveInfo)
        fun onUpiAppClosed()
    }
}