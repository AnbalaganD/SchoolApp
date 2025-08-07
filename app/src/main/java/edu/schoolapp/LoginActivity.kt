package edu.schoolapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.SignInButton
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.googleSingIn.GoogleAuthenticationService
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private val googleAuthenticationService = GoogleAuthenticationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db = FirebaseFirestore.getInstance()
        val googleSignInButton = findViewById<SignInButton>(R.id.google_sign_in_button)

        googleSignInButton.setOnClickListener {
            lifecycleScope.launch {
                val user = googleAuthenticationService.signIn(this@LoginActivity)
                if (user != null) {
                    checkUserExits(user.userId, user.userName, user.email)
                }
            }
        }
    }

    private fun checkUserExits(userId: String, name: String, email: String?) {
        val documentReference = db.collection("users").document(
            userId
        )
        documentReference.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                navigateToHome()
            } else {
                documentReference.set(User(name, email))
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}