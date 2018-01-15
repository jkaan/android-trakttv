package app.joey.trakttv.di

import android.content.Context
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService

@Module
class MainActivityModule {
    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }
}
