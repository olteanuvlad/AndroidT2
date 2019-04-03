package com.example.l4;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class F1 extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> mDataset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_f1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(getContext());


        Button btn1 = view.findViewById(R.id.button2);
        btn1.setOnClickListener(v -> {
            EditText firstName = view.findViewById(R.id.editText);
            String fname = firstName.getText().toString();

            EditText lastName = view.findViewById(R.id.editText2);
            String lname = lastName.getText().toString();

            UserRepository usrRep = new UserRepository(getContext());
            usrRep.insertTask(new User(fname, lname), new OnUserRepositoryActionListener() {
                @Override
                public void actionSuccess() {
                    mDataset.add(new User(fname, lname));
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Successfully added.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void actionSuccess(List<User> result) {
                }

                @Override
                public void actionFailed() {
                    Toast.makeText(getContext(), "Failed to add.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button btn2 = view.findViewById(R.id.button3);
        btn2.setOnClickListener(v -> {
            EditText firstName = view.findViewById(R.id.editText);
            String fname = firstName.getText().toString();

            EditText lastName = view.findViewById(R.id.editText2);
            String lname = lastName.getText().toString();

            UserRepository usrRep = new UserRepository(getContext());
            usrRep.findTask(fname, lname, new OnUserRepositoryActionListener() {
                @Override
                public void actionSuccess() {
                    int index = CustomIndexOf(mDataset, fname, lname);
                    if (index != -1) {
                        mDataset.remove(index);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Successfully deleted.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Could not fin user.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void actionSuccess(List<User> result) {
                }

                @Override
                public void actionFailed() {
                    Toast.makeText(getContext(), "Failed to delete.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        UserRepository usrRep = new UserRepository(getContext());
        usrRep.getAllUsers(new OnUserRepositoryActionListener() {
            @Override
            public void actionSuccess() {
            }

            @Override
            public void actionSuccess(List<User> result) {
                mDataset = result;
                mAdapter = new MyAdapter(result);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void actionFailed() {
                Toast.makeText(getContext(), "setUpRecyclerView not working", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int CustomIndexOf(List<User> users, String firstName, String lastName) {
        for (int it = 0; it < users.size(); it++) {
            User user = users.get(it);
            if (user.firstName.equals(firstName) && user.lastName.equals(lastName)) {
                return it;
            }
        }

        return -1;
    }
}
