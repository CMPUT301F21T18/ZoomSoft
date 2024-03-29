package com.example.zoomsoft;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zoomsoft.loginandregister.Register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for Registration Activity. All the UI tests are written here. Robotium test framework is
 used.
 UI tested: change in activity from Main to registration and verify the data in database and data structure
 */

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    // grant permission
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }



    /**
     * Tests the MainPage UI goes to Registration page and checks the activity and adds the data
     * to the fields and verify with firebase
     */
    @Test
    public void checkRegistrationProcess(){
        //Asserts that the current activity is the MainActivity. Otherwise, show Wrong Activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Go to next activity register
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", Register.class);

        // enter the data and test
        solo.enterText((EditText) solo.getView(R.id.username), "asad70");
        solo.enterText((EditText) solo.getView(R.id.email), "asad70@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "123456");
        solo.clickOnButton("Register");

        // check if activity switched properly
        solo.assertCurrentActivity("Wrong Activity", MainPageTabs.class);

        // delete the added email/username (data) from data base
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("User").document("asad70@gmail.com")

                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    @Test
    // invalid login
    public void invalidRegistration(){
        //Asserts that the current activity is the MainActivity. Otherwise, show Wrong Activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Go to next activity Register
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", Register.class);

        // test email that is already in the firebase
        solo.enterText((EditText) solo.getView(R.id.username), "aidrees");
        solo.enterText((EditText) solo.getView(R.id.email), "asad@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password), "123456");
        solo.clickOnButton("Register");

        // check if activity stays the same, should be same as login isn't correct
        solo.assertCurrentActivity("Wrong Activity", Register.class);

    }


    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}


