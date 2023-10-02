package edu.schoolapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.SchoolApp.Companion.email
import edu.schoolapp.SchoolApp.Companion.name
import edu.schoolapp.SchoolApp.Companion.profileUrl
import edu.schoolapp.SchoolApp.Companion.token

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db = FirebaseFirestore.getInstance()
        val googleSignInButton = findViewById<SignInButton>(R.id.google_sign_in_button)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, googleSignInOptions)

        googleSignInButton.setOnClickListener {
            resultLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun checkUserExits(account: GoogleSignInAccount) {
        val documentReference = db.collection("users").document(
            account.id!!
        )
        documentReference.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                navigateToHome()
            } else {
                documentReference.set(User(account.email, account.displayName))
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                if (task.isSuccessful) {
                    token = task.result.id
                    email = task.result.email
                    name = task.result.displayName
                    profileUrl = task.result.photoUrl.toString()
                    checkUserExits(task.result)
                }
            }
        }
}