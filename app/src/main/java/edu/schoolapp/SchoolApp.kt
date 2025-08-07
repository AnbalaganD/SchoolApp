package edu.schoolapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.database.FirebaseDatabase

class SchoolApp : Application() {
    override fun onCreate() {
        super.onCreate()

        appContext = this
        sharedPreferences = getSharedPreferences(this.javaClass.simpleName, MODE_PRIVATE)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    companion object {
        //FIXME we can pass data using intent but its take time to serialize and deserialize so use global property
        // If we use ViewModel this type of bad style

        var data: Any? = null
        var appContext: Context? = null
            private set
        private var sharedPreferences: SharedPreferences? = null
        var token: String?
            get() = sharedPreferences?.getString("token", null)
            set(value) {
                sharedPreferences?.edit {
                    putString("token", value)
                }
            }
        var name: String?
            get() = sharedPreferences?.getString("userName", null)
            set(value) {
                sharedPreferences?.edit {
                    putString("userName", value)
                }
            }
        var email: String?
            get() = sharedPreferences?.getString("email", null)
            set(value) {
                sharedPreferences?.edit {
                    putString("email", value)
                }
            }
        var profileUrl: String?
            get() = sharedPreferences?.getString("profileUrl", null)
            set(value) {
                sharedPreferences?.edit {
                    putString("profileUrl", value)
                }
            }

        fun clearSharedPreference() {
            token = null
            email = null
            name = null
            profileUrl = null
        }
    }
}