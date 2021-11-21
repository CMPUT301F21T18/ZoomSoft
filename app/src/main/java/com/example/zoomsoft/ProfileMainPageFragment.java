package com.example.zoomsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ProfileMainPageFragment extends Fragment {
    ArrayList<Profile> profileDataList = new ArrayList<>();
    ArrayAdapter profileAdaptor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        ListView profileList = view.findViewById(R.id.profile_list);
        String[] itemList = {"View Friends","Pending Requests", "Received Requests", "Add Friend"};
        profileDataList.clear();
        for (String s : itemList) {
            Profile profile = new Profile(s);
            profileDataList.add(profile);
        }

        profileAdaptor = new ProfileAdapter(this.getContext(), profileDataList);
        profileList.setAdapter(profileAdaptor);
        profileList.setOnItemClickListener((adapterView, view1, i, l) -> {
             if (i == 0) {
                 Intent intent = new Intent(getActivity(), ViewFriend.class);
                 startActivity(intent);
             }else if (i == 3){
                 Intent in = new Intent(getActivity(), AddFriends.class);
                 startActivity(in);
             }
        });
        return view;
    }
}

