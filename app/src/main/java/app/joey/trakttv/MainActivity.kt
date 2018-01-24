package app.joey.trakttv

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    companion object {
        val RC_AUTH = 122
    }

    @Inject lateinit var service: AuthorizationService
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Movies"
        setupDrawerToggle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        setupViewPager(viewPager)

        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_movies -> {
                    viewPager.setCurrentItem(0, false)
                    supportActionBar?.title = "Movies"
                    true
                }
                R.id.action_shows -> {
                    viewPager.setCurrentItem(1, false)
                    supportActionBar?.title = "Shows"
                    true
                }
                R.id.action_accounts -> {
                    viewPager.setCurrentItem(2, false)
                    supportActionBar?.title = "Accounts"
                    true
                }
                else -> {
                    false
                }
            }
        }

//        val serviceConfig = AuthorizationServiceConfiguration(
//            Uri.parse("https://api.trakt.tv/oauth/authorize"),
//            Uri.parse("https://api.trakt.tv/oauth/token")
//        )
//
//        val request = AuthorizationRequest.Builder(
//            serviceConfig,
//            "952eb54bbfb8e4f0ab6363452a9652a20984e8a6b3a7049a0e91d12141ac4569",
//            ResponseTypeValues.CODE,
//            Uri.parse("jkmoviemanager://done")
//        ).build()

//    val authIntent = service.getAuthorizationRequestIntent(request)
//    startActivityForResult(authIntent, RC_AUTH)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setupDrawerToggle() {
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MoviesFragment())
        adapter.addFragment(ShowsFragment())
        adapter.addFragment(AccountFragment())
        viewPager.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_AUTH) {
            val resp = AuthorizationResponse.fromIntent(data!!)
            // val ex = AuthorizationException.fromIntent(data)
            val clientAuth = ClientSecretPost("0e655cca70fa287ef6c64bec545a2ce57f0f6d68f08302d1d54d2112a4f1ef1d")
            service.performTokenRequest(resp!!.createTokenExchangeRequest(), clientAuth, { tokenResponse: TokenResponse?, authorizationException: AuthorizationException? ->
                Log.w("Token", authorizationException.toString())
                if (tokenResponse != null) {
                    Log.w("Activity", "It works! " + tokenResponse.accessToken)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
