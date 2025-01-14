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

package ru.yandex.money.android.sdk.impl.logout

import android.content.Context
import android.content.SharedPreferences
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import ru.yandex.money.android.sdk.impl.TokensStorage
import ru.yandex.money.android.sdk.impl.extensions.edit
import ru.yandex.money.android.sdk.impl.payment.SharedPreferencesCurrentUserGateway
import ru.yandex.money.android.sdk.logout.LogoutGateway
import ru.yandex.money.android.sdk.model.AnonymousUser
import ru.yandex.money.android.sdk.model.AuthorizedUser
import ru.yandex.money.android.sdk.model.CurrentUser

@RunWith(RobolectricTestRunner::class)
class LogoutGatewayImplTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var logoutGateway: LogoutGateway
    private lateinit var tokensStorage: TokensStorage
    private lateinit var currentUserGateway: SharedPreferencesCurrentUserGateway

    @Before
    fun setUp() {
        sharedPreferences = RuntimeEnvironment.application.getSharedPreferences("testsp", Context.MODE_PRIVATE)
        currentUserGateway = SharedPreferencesCurrentUserGateway(sharedPreferences)
        tokensStorage = TokensStorage(
            preferences = sharedPreferences,
            encrypt = { it },
            decrypt = { it }
        )
        logoutGateway = LogoutGatewayImpl(
            currentUserGateway = currentUserGateway,
            userAuthTokenGateway = tokensStorage,
            paymentAuthTokenGateway = tokensStorage,
            removeKeys = { }
        )
    }

    @After
    fun tearDown() {
        sharedPreferences.edit {
            clear()
        }
    }

    @Test
    fun shouldRemoveUserData() {
        // prepare
        currentUserGateway.currentUser = AuthorizedUser("test_name")
        tokensStorage.paymentAuthToken = "12345"
        tokensStorage.userAuthToken = "12345"

        // invoke
        logoutGateway.logout()

        // assert
        assertThat(currentUserGateway.currentUser, equalTo(AnonymousUser as CurrentUser))
        assertThat(tokensStorage.paymentAuthToken, nullValue())
        assertThat(tokensStorage.userAuthToken, nullValue())
    }
}
