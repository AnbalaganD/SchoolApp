package edu.schoolapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import java.net.URL

class SettingsActivity : AppCompatActivity() {
    private var userImageView: ImageView? = null
    private var userNameTextView: TextView? = null
    private var emailTextView: TextView? = null
    private var managePrimaryMenuTextView: TextView? = null
    private var logoutTextView: TextView? = null
    private lateinit var googleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        userImageView = findViewById(R.id.setting_user_image_view)
        userNameTextView = findViewById(R.id.settings_user_name_text_view)
        emailTextView = findViewById(R.id.settings_email_text_view)
        managePrimaryMenuTextView = findViewById(R.id.manage_text_view)
        logoutTextView = findViewById(R.id.logout_text_view)
        DownloadImageAsync(userImageView).execute(SchoolApp.profileUrl)
        userNameTextView?.text = SchoolApp.name
        emailTextView?.text = SchoolApp.email
        managePrimaryMenuTextView?.setOnClickListener {
            startActivity(
                Intent(
                    this@SettingsActivity,
                    ManageListFragment::class.java
                )
            )
        }
        logoutTextView?.setOnClickListener {
            if (googleApiClient.isConnected) {
                Auth.GoogleSignInApi.signOut(googleApiClient)
                googleApiClient.disconnect()
                googleApiClient.connect()
                SchoolApp.clearSharedPreference()
                val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        googleApiClient.connect()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    //Inner Class
    internal inner class DownloadImageAsync(private val userImage: ImageView?) :
        AsyncTask<String?, Void?, Bitmap?>() {
        override fun doInBackground(vararg urls: String?): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeStream(URL(urls[0]).openStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
            roundedBitmapDrawable.isCircular = true
            userImage!!.setImageDrawable(roundedBitmapDrawable)
        }
    }
}