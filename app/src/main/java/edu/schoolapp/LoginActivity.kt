package edu.schoolapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.SchoolApp.Companion.email
import edu.schoolapp.SchoolApp.Companion.name
import edu.schoolapp.SchoolApp.Companion.profileUrl
import edu.schoolapp.SchoolApp.Companion.token

class LoginActivity : AppCompatActivity() {
    private val requestSignInCode = 7
    private var googleApiClient: GoogleApiClient? = null

    //    private FirebaseAuth firebaseAuth;
    private lateinit var db: FirebaseFirestore
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db = FirebaseFirestore.getInstance()
        val googleSignInButton = findViewById<SignInButton>(R.id.google_sign_in_button)
        //        firebaseAuth = FirebaseAuth.getInstance();
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleApiClient =
            GoogleApiClient.Builder(this@LoginActivity).enableAutoManage(this@LoginActivity) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build()
        googleSignInButton.setOnClickListener {
            val AuthIntent = Auth.GoogleSignInApi.getSignInIntent(
                googleApiClient!!
            )
            startActivityForResult(AuthIntent, requestSignInCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestSignInCode) {
            val googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(
                data!!
            )
            if (googleSignInResult!!.isSuccess) {
                val googleSignInAccount = googleSignInResult.signInAccount
                if (googleSignInAccount != null) {
                    token = googleSignInAccount.id
                    email = googleSignInAccount.email
                    name = googleSignInAccount.displayName
                    profileUrl = googleSignInAccount.photoUrl.toString()
                    checkUserExits(googleSignInAccount)
                }
            }
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

//        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(account.getId());
//        if (databaseReference == null)
//        {
//
//        }
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue(User.class) == null) {
//                    databaseReference.setValue(new User(account.getDisplayName(), account.getEmail()));
//                }
//                navigateToHome();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    } //eyJhbGciOiJSUzI1NiIsImtpZCI6IjAyOWYyNjlmM2YwNmFmMWU5M2RhYzY3MDYzOTc3ZjcxM2E3N2YxOWUifQ.eyJhenAiOiI5NTIxNzk0MTc2MzItMDJzNXNlOG8wNDVvczNudXZqbGp1ZHAxazIyYmE1dnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5NTIxNzk0MTc2MzItbzdmbW4wYWMwaW1xaXJqdWloMmlwbmVpOWI5OWEyNXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTgxNTQ4NDYwMjcyMjExMjU0NDIiLCJlbWFpbCI6ImFuYnU5NHBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImV4cCI6MTUyNzAxNDU2MywiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwiaWF0IjoxNTI3MDEwOTYzLCJuYW1lIjoiQW5iYWxhZ2FuIEQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTnNiNnlOcEJpVS9BQUFBQUFBQUFBSS9BQUFBQUFBQUVtWS9pSmpyc1ZLTzlGay9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQW5iYWxhZ2FuIiwiZmFtaWx5X25hbWUiOiJEIiwibG9jYWxlIjoiZW4tSU4ifQ.KdHXNQWeMbit3N10oh1N87ggNukkmIBtAGlKH0HTVBsno8g8ZWVnhyFvEz4vnLNLxcKuqJFTURy7CN6IRgEN56nP00LDfIw1rIbwLF5ejJGCqNJAQNxgxhZkRnyN1yksd1s6BSHF_ct8KP9LoKTj9CSP-80hAZamowgq4Xrit39ysR25V3wndEEpCfCFkmQhMoZ4s1pfzzGP5DNqAPWwt0WJaZWrndksRCOSgKl3l8RAJQSw3XEfm5oO8w4V6hWaAvLEnO6vklss7ItFjshh6lCXGnp0iGF5YHUl3GTS9KbQVHIPJe37wKTrZSEu0eXuqFwnHzLem1U69My3_QUXoQ
    //eyJhbGciOiJSUzI1NiIsImtpZCI6IjAyOWYyNjlmM2YwNmFmMWU5M2RhYzY3MDYzOTc3ZjcxM2E3N2YxOWUifQ.eyJhenAiOiI5NTIxNzk0MTc2MzItMDJzNXNlOG8wNDVvczNudXZqbGp1ZHAxazIyYmE1dnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5NTIxNzk0MTc2MzItbzdmbW4wYWMwaW1xaXJqdWloMmlwbmVpOWI5OWEyNXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTgxNTQ4NDYwMjcyMjExMjU0NDIiLCJlbWFpbCI6ImFuYnU5NHBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImV4cCI6MTUyNzAxNDU2MywiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwiaWF0IjoxNTI3MDEwOTYzLCJuYW1lIjoiQW5iYWxhZ2FuIEQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTnNiNnlOcEJpVS9BQUFBQUFBQUFBSS9BQUFBQUFBQUVtWS9pSmpyc1ZLTzlGay9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQW5iYWxhZ2FuIiwiZmFtaWx5X25hbWUiOiJEIiwibG9jYWxlIjoiZW4tSU4ifQ.KdHXNQWeMbit3N10oh1N87ggNukkmIBtAGlKH0HTVBsno8g8ZWVnhyFvEz4vnLNLxcKuqJFTURy7CN6IRgEN56nP00LDfIw1rIbwLF5ejJGCqNJAQNxgxhZkRnyN1yksd1s6BSHF_ct8KP9LoKTj9CSP-80hAZamowgq4Xrit39ysR25V3wndEEpCfCFkmQhMoZ4s1pfzzGP5DNqAPWwt0WJaZWrndksRCOSgKl3l8RAJQSw3XEfm5oO8w4V6hWaAvLEnO6vklss7ItFjshh6lCXGnp0iGF5YHUl3GTS9KbQVHIPJe37wKTrZSEu0eXuqFwnHzLem1U69My3_QUXoQ
}