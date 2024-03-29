package com.example.zoomsoft.eventInfo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zoomsoft.Habits;
import com.example.zoomsoft.MainPageTabs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Firebase class that interacts with events to get the habits and their information.
 */
public class HabitInfoFirebase {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String email = MainPageTabs.email;
    //    public static String HabitInfo.clickedHabit = HabitInfo.clickedHabit;
    Source source = Source.SERVER;

    public interface MyCallBack {
        void getDays(ArrayList<Long> days);
        void getReason(String reason);
        void getStartDate(String date);
        void getStatus(String status);
    }

    public HabitInfoFirebase() {
    }

    /**
     * This function is responsible for getting the days that the user has selected and populates them
     * in info fragment.
     *
     * @param myCallBack Interface for modifying habits in firebase.
     */
    public void getDaysSelected(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        HashMap hashMap = (HashMap) map.get(HabitInfo.clickedHabit);
                        if(hashMap == null) return;
                        ArrayList<Long> dayList = (ArrayList<Long>) hashMap.get("days");
                        myCallBack.getDays(dayList);
                    }
                }
            }
        });
    }

    /**
     * This function gets the reason for the clicked Habit
     * @param myCallBack Interface for modifying habits in firebase.
     */
    public void getHabitReason(MyCallBack myCallBack) {
        String habitReason = "";
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(HabitInfo.clickedHabit);
                        if(hashMap == null) return;
                        String reason = (String) hashMap.get("reason");
                        myCallBack.getReason(reason);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    /**
     * Gets the start date for the habit
     * @param myCallBack Interface for modifying habits in firebase.
     */
    public void getHabitStartDate(MyCallBack myCallBack) {
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(HabitInfo.clickedHabit);
                        if(hashMap == null) return;
                        String startDate = (String) hashMap.get("startDate");
                        myCallBack.getStartDate(startDate);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    /**
     * Gets the status of the habit.
     * @param myCallBack Interface for modifying habits in firebase.
     */
    public void getHabitStatus(MyCallBack myCallBack) {
        String habitReason = "";
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
//                        Log.d("Map provided: ", map.toString());
                        HashMap hashMap = (HashMap) map.get(HabitInfo.clickedHabit);
                        if(hashMap == null) return;
                        String reason = (String) hashMap.get("status");
                        myCallBack.getStatus(reason);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }

    /**
     * Deletes a habit
     * @param clickedHabit The name of the habit last clicked on
     */
    public void deleteHabit(String clickedHabit){
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        documentReference.update(clickedHabit, FieldValue.delete());
                    }
                }
            }
        });
    }

    /**
     * Edits a habit
     * @param habits The habit that is being edited.
     * @param description The description for the habit.
     */
    public void editHabit(Habits habits, String description){

        //get the title
        String title = habits.getHabitTitle();
        //get the reason
        String reason = habits.getHabitReason();
        //get the start date
        String date = habits.getStartDate();
        //get days of week (Need to change implementation over to ArrayList
        ArrayList<Integer> days = habits.getHabitWeekDay();
        //get privacy status
        String privacy = habits.getPrivacy();
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("startDate", date);
        temp.put("reason", reason);
        temp.put("days", days);
        temp.put("status", privacy);
        temp.put("description", description);
        HashMap<String, HashMap<String, Object>> data = new HashMap<>();
        data.put(title, temp);
        final CollectionReference collectionReference = db.collection("Events");
        DocumentReference documentReference = collectionReference.document(email);
        //add the new habit first
        collectionReference.document(email)
                .set(data, SetOptions.merge());
        String previousHabit = HabitInfo.clickedHabit;
        //set the global variable to the new habit
        HabitInfo.clickedHabit = title;
        //delete the old habit
        if(!title.equals(previousHabit)) deleteHabit(previousHabit);
    }
}
