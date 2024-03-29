package com.example.zoomsoft;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MainPage firebase connectivity for getting lists of habits.
 */
public class MainPageFirebase {

    interface MainPageInterface {
        void getHabitInterface(ArrayList<String> habitArrayList);
        void getAllHabitsForToday(ArrayList<String> habitsToday);
    }

    public MainPageFirebase() {
    }

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    //Switch over to Events collection once completed
//    final CollectionReference collectionReference = rootRef.collection("Events");
    private final String email = MainPageTabs.email;
    Source source = Source.SERVER;

    /**
     * Retrieves info for MainPage
     * @param mainPageInterface
     */
    public void getListOfHabits(MainPageInterface mainPageInterface){
        final CollectionReference collectionReference = rootRef.collection("Events");
        collectionReference.document(MainPageTabs.email).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        ArrayList<String> habitNameList = new ArrayList<>(map.keySet());
                        mainPageInterface.getHabitInterface(habitNameList);
                    }
                }
                else {
                    //nothing for now
                }
            }
        });
    }

    /**
     * generates all temp strings
     * @param habits
     * @param description
     */
    public void addNewHabit(Habits habits, String description){
        final CollectionReference collectionReference = rootRef.collection("Events");
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
        temp.put("description",description);

        HashMap<String, HashMap<String, Object>> data = new HashMap<>();
        data.put(title, temp);

        collectionReference
                .document(email)
                .set(data, SetOptions.merge());
    }

    /**
     * DailyHabits added to Firebase
     * @param day
     * @param mainPageInterface
     */
    public void getDailyHabits(int day, MainPageInterface mainPageInterface) {
        final CollectionReference collectionReference = rootRef.collection("Events");
        collectionReference.document(MainPageTabs.email).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    ArrayList<String> list = new ArrayList<>();
                    if(documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        Log.d("All habits:", map.toString());
                        for(String habit : map.keySet()) {
                            HashMap hashMap = (HashMap) map.get(habit);
                            ArrayList<Long> listDays = (ArrayList<Long>) hashMap.get("days");
                            if(listDays.get(day - 1) == 1) {
                                list.add(habit);
                            }
                        }
                        mainPageInterface.getAllHabitsForToday(list);
                    }
                }
                else {
                    int x = 6; //will decide on this later
                }
            }
        });
    }
}
