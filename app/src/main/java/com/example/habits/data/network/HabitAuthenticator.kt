package com.example.habits.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class HabitAuthenticator() : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {

        val token = "4ba6888e-5b53-45aa-b255-925967d68ec3"

        return response.request.newBuilder()
            .removeHeader(TOKEN_KEY)
            .addHeader(TOKEN_KEY, token)
            .build()
    }

    companion object {
        private const val TOKEN_KEY = "auth_token_key"
    }
}