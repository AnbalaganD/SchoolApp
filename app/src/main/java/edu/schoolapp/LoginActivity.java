package edu.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    private final int RequestSignInCode = 7;

    private GoogleApiClient googleApiClient;
//    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
//        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(LoginActivity.this).enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(AuthIntent, RequestSignInCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                if (googleSignInAccount != null) {
                    SchoolApp.setToken(googleSignInAccount.getId());
                    SchoolApp.setEmail(googleSignInAccount.getEmail());
                    SchoolApp.setName(googleSignInAccount.getDisplayName());
                    SchoolApp.setProfileUrl(googleSignInAccount.getPhotoUrl().toString());

                    checkUserExits(googleSignInAccount);
                }
            }
        }
    }

    private void checkUserExits(final GoogleSignInAccount account) {

        final DocumentReference documentReference = db.collection("users").document(account.getId());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    navigateToHome();
                } else {
                    documentReference.set(new User(account.getEmail(), account.getDisplayName()));
                }
            }
        });

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

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //eyJhbGciOiJSUzI1NiIsImtpZCI6IjAyOWYyNjlmM2YwNmFmMWU5M2RhYzY3MDYzOTc3ZjcxM2E3N2YxOWUifQ.eyJhenAiOiI5NTIxNzk0MTc2MzItMDJzNXNlOG8wNDVvczNudXZqbGp1ZHAxazIyYmE1dnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5NTIxNzk0MTc2MzItbzdmbW4wYWMwaW1xaXJqdWloMmlwbmVpOWI5OWEyNXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTgxNTQ4NDYwMjcyMjExMjU0NDIiLCJlbWFpbCI6ImFuYnU5NHBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImV4cCI6MTUyNzAxNDU2MywiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwiaWF0IjoxNTI3MDEwOTYzLCJuYW1lIjoiQW5iYWxhZ2FuIEQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTnNiNnlOcEJpVS9BQUFBQUFBQUFBSS9BQUFBQUFBQUVtWS9pSmpyc1ZLTzlGay9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQW5iYWxhZ2FuIiwiZmFtaWx5X25hbWUiOiJEIiwibG9jYWxlIjoiZW4tSU4ifQ.KdHXNQWeMbit3N10oh1N87ggNukkmIBtAGlKH0HTVBsno8g8ZWVnhyFvEz4vnLNLxcKuqJFTURy7CN6IRgEN56nP00LDfIw1rIbwLF5ejJGCqNJAQNxgxhZkRnyN1yksd1s6BSHF_ct8KP9LoKTj9CSP-80hAZamowgq4Xrit39ysR25V3wndEEpCfCFkmQhMoZ4s1pfzzGP5DNqAPWwt0WJaZWrndksRCOSgKl3l8RAJQSw3XEfm5oO8w4V6hWaAvLEnO6vklss7ItFjshh6lCXGnp0iGF5YHUl3GTS9KbQVHIPJe37wKTrZSEu0eXuqFwnHzLem1U69My3_QUXoQ
    //eyJhbGciOiJSUzI1NiIsImtpZCI6IjAyOWYyNjlmM2YwNmFmMWU5M2RhYzY3MDYzOTc3ZjcxM2E3N2YxOWUifQ.eyJhenAiOiI5NTIxNzk0MTc2MzItMDJzNXNlOG8wNDVvczNudXZqbGp1ZHAxazIyYmE1dnUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5NTIxNzk0MTc2MzItbzdmbW4wYWMwaW1xaXJqdWloMmlwbmVpOWI5OWEyNXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTgxNTQ4NDYwMjcyMjExMjU0NDIiLCJlbWFpbCI6ImFuYnU5NHBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImV4cCI6MTUyNzAxNDU2MywiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwiaWF0IjoxNTI3MDEwOTYzLCJuYW1lIjoiQW5iYWxhZ2FuIEQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTnNiNnlOcEJpVS9BQUFBQUFBQUFBSS9BQUFBQUFBQUVtWS9pSmpyc1ZLTzlGay9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQW5iYWxhZ2FuIiwiZmFtaWx5X25hbWUiOiJEIiwibG9jYWxlIjoiZW4tSU4ifQ.KdHXNQWeMbit3N10oh1N87ggNukkmIBtAGlKH0HTVBsno8g8ZWVnhyFvEz4vnLNLxcKuqJFTURy7CN6IRgEN56nP00LDfIw1rIbwLF5ejJGCqNJAQNxgxhZkRnyN1yksd1s6BSHF_ct8KP9LoKTj9CSP-80hAZamowgq4Xrit39ysR25V3wndEEpCfCFkmQhMoZ4s1pfzzGP5DNqAPWwt0WJaZWrndksRCOSgKl3l8RAJQSw3XEfm5oO8w4V6hWaAvLEnO6vklss7ItFjshh6lCXGnp0iGF5YHUl3GTS9KbQVHIPJe37wKTrZSEu0eXuqFwnHzLem1U69My3_QUXoQ
}
