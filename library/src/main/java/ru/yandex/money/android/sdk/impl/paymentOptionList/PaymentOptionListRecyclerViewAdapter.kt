/*
 * The MIT License (MIT)
 * Copyright © 2018 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package ru.yandex.money.android.sdk.impl.paymentOptionList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.ym_item_common.view.*
import ru.yandex.money.android.sdk.R
import ru.yandex.money.android.sdk.impl.extensions.visible
import ru.yandex.money.android.sdk.utils.showLogoutDialog

internal class PaymentOptionListRecyclerViewAdapter internal constructor(
        private val listener: PaymentOptionClickListener,
        private val paymentOptions: List<PaymentOptionListItemViewModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ym_item_common, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val paymentOption = paymentOptions[position]

        (holder as ViewHolder).view.apply {
            image.setImageDrawable(paymentOption.icon)
            primaryText.text = paymentOption.title
            setOnClickListener {
                listener.onPaymentOptionClick(paymentOption.optionId)
            }

            with(secondaryText) {
                visible = paymentOption.additionalInfo != null
                text = paymentOption.additionalInfo
            }

            if (paymentOption.canLogout) {
                primaryText.setOnClickListener { showLogoutDialog(context, paymentOption.title, it.rootView) }
            }

            divider.visible = position != itemCount - 1
        }
    }

    override fun getItemCount() = paymentOptions.size

    class ViewHolder internal constructor(internal var view: View) : RecyclerView.ViewHolder(view)

    interface PaymentOptionClickListener {
        fun onPaymentOptionClick(optionId: Int)
    }
}
