package edu.schoolapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.lifecycleScope
import edu.schoolapp.googleSingIn.GoogleAuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class SettingsActivity : AppCompatActivity() {
    private lateinit var userImageView: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var managePrimaryMenuTextView: TextView
    private lateinit var logoutTextView: TextView

    private val googleAuthenticationService = GoogleAuthenticationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userImageView = findViewById(R.id.setting_user_image_view)
        userNameTextView = findViewById(R.id.settings_user_name_text_view)
        emailTextView = findViewById(R.id.settings_email_text_view)
        managePrimaryMenuTextView = findViewById(R.id.manage_text_view)
        logoutTextView = findViewById(R.id.logout_text_view)

        userNameTextView.text = SchoolApp.name
        emailTextView.text = SchoolApp.email
        managePrimaryMenuTextView.setOnClickListener {
            startActivity(
                Intent(
                    this@SettingsActivity,
                    ManageListFragment::class.java
                )
            )
        }

        logoutTextView.setOnClickListener {
            googleAuthenticationService.logout()
            SchoolApp.clearSharedPreference()
            val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        downloadProfileImage()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun downloadProfileImage() {
        lifecycleScope.launch(Dispatchers.IO) {
            val bitmap = BitmapFactory.decodeStream(URL(SchoolApp.profileUrl).openStream())
            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
            roundedBitmapDrawable.isCircular = true
            userImageView.setImageDrawable(roundedBitmapDrawable)
        }
    }
}