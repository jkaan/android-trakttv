package app.joey.trakttv.di

import app.joey.trakttv.MainActivity
import app.joey.trakttv.MoviesFragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun movieFragmentInjector(): MoviesFragment
}
