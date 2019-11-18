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

package ru.yandex.money.android.sdk.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import ru.yandex.money.android.sdk.Amount
import ru.yandex.money.android.sdk.Checkout
import ru.yandex.money.android.sdk.PaymentParameters
import ru.yandex.money.android.sdk.impl.extensions.RUB
import java.math.BigDecimal
import java.util.Currency

@RunWith(RobolectricTestRunner::class)
class WebViewActivityRobolectricTest {

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception if not https url present`() {
        // prepare
        Checkout.createTokenizeIntent(
            RuntimeEnvironment.application,
            PaymentParameters(
                amount = Amount(BigDecimal.ONE, RUB),
                title = "",
                subtitle = "",
                clientApplicationKey = "",
                shopId = ""
            )
        )
        val url = "http://wrong.scheme.url/"

        // invoke
        Checkout.create3dsIntent(RuntimeEnvironment.application, url)

        // assert that exception thrown
    }
}