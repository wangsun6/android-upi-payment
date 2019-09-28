package com.wangsun.upi.payment.adapter

import android.content.Context
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wangsun.upi.payment.R
import kotlinx.android.synthetic.main.wangsun_upi_payment_item_upi_list.view.*

/**
 * Created by WANGSUN on 26-Sep-19.
 */

class UpiListAdapter : RecyclerView.Adapter<UpiListAdapter.AppViewHolder>() {

    private lateinit var mContext: Context
    private var mData: MutableList<ResolveInfo> = mutableListOf()
    private var mOnUpiListItemListener: OnUpiListItemListener? = null


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        mContext = parent.context
        val layout = LayoutInflater.from(mContext).inflate(R.layout.wangsun_upi_payment_item_upi_list, parent, false)
        return AppViewHolder(layout)
    }

    override
    fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    fun setListener(listener: OnUpiListItemListener){
        mOnUpiListItemListener = listener
    }

    fun setData(data: MutableList<ResolveInfo>){
        mData = data
        notifyDataSetChanged()
    }

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(info: ResolveInfo) {
            itemView.item_upi_list_txt_name.text = info.loadLabel(mContext.packageManager).toString()
            Glide.with(itemView)
                .load(info.loadIcon(mContext.packageManager))
                .into(itemView.item_upi_list_img_icon)

            itemView.item_upi_list_fl.setOnClickListener {
                mOnUpiListItemListener?.onItemClick(info)
            }
        }
    }

    interface OnUpiListItemListener {
        fun onItemClick(data: ResolveInfo)
    }
}
