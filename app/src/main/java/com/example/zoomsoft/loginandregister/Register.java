package com.example.zoomsoft.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomsoft.MainActivity;
import com.example.zoomsoft.MainPageTabs;
import com.example.zoomsoft.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity class for the register screen.
 */
public class Register extends AppCompatActivity {

    protected EditText usernameEditText;
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected Button registerButton;
    final String TAG = "Sample";
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public User user;


    //STEPS TAKEN FOR REGISTER
    //When the user clicks on register,
    //check to make sure that the user did not provide an empty field value
    //check to make sure that email does not exist
    //if it exists, display an alert saying email exists already
    //else the data into the cloud firebase and make a call to the home activity because the user has been authenticated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        final CollectionReference collectionReference = db.collection("User");
        final CollectionReference collectionReference2 = db.collection("Friends");
        final CollectionReference collectionReference3 = db.collection("Received Requests");
        final CollectionReference collectionReference4 = db.collection("Pending Requests");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                user = new User(username, email, password);
                HashMap<String, String> data = new HashMap<>();
                HashMap<String, ArrayList<String>> data2 = new HashMap<>();
                HashMap<String, ArrayList<String>> data3 = new HashMap<>();
                HashMap<String, ArrayList<String>> data4 = new HashMap<>();

                if(username.length() > 0 && email.length() > 0 && password.length() > 0) {
                    data.put("username", username);
                    data.put("password", password);
                    data2.put("friends", new ArrayList<String>());
                    data3.put("Received Requests", new ArrayList<String>());
                    data4.put("pending_requests", new ArrayList<String>());

                    DocumentReference documentReference = db.collection("User").document(email);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                assert document != null;
                                if(document.exists()) {
                                    // display toast message to the user about the error
                                    Toast.makeText(Register.this,
                                            "User already exists with the email provided", Toast.LENGTH_LONG).show();

                                    //use the document to login

                                    Log.d(TAG, "User already exists with the email provided");
                                    //display alert
                                }
                                else {
                                    collectionReference
                                            .document(email)
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Data has been added successfully!");
                                                    //call the home activity from here
                                                    Intent intent = new Intent(Register.this, MainPageTabs.class);
                                                    intent.putExtra(MainActivity.EXTRA_MESSAGE + "email", email);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                                    //specify an error value
                                                }
                                            });
                                    collectionReference2
                                            .document(email)
                                            .set(data2)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Data has been added successfully!");
                                                    //call the home activity from here
                                                    Intent intent = new Intent(Register.this, MainPageTabs.class);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                                    //specify an error value
                                                }
                                            });
                                    collectionReference3
                                            .document(email)
                                            .set(data3)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Data has been added successfully!");
                                                    //call the home activity from here
                                                    Intent intent = new Intent(Register.this, MainPageTabs.class);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                                    //specify an error value
                                                }
                                            });
                                    collectionReference4
                                            .document(email)
                                            .set(data4)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Data has been added successfully!");
                                                    //call the home activity from here
                                                    Intent intent = new Intent(Register.this, MainPageTabs.class);

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                                    //specify an error value
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }

}
