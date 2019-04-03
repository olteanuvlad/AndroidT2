package com.example.l4;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private AppDatabase appDatabase;

    public UserRepository(Context context) {
        appDatabase = ApplicationController.getAppDatabase();
    }

    public void insertTask(final User user,
                           final OnUserRepositoryActionListener listener)   {
        new InsertTask(listener).execute(user);
    }

    public void deleteTask(final User user,
                           final OnUserRepositoryActionListener listener) {
        new DeleteTask(listener).execute(user);
    }

    public void findTask(final String firstName,
                         final String lastName,
                         final OnUserRepositoryActionListener listener) {
        new FindTask(listener).execute(firstName, lastName);
    }

    public void getAllUsers(final OnUserRepositoryActionListener listener) {
        new GetAllUsers(listener).execute();
    }

    public User getUserByName(String firstName, String lastName){
        return appDatabase.userDao().findByName(firstName, lastName);
    }

    // DO NOT PERFORM OPERATION ON MAIN THREAD AS APP WILL CRASH
    // See more details about AsyncTask in the next chapter
    private class InsertTask extends AsyncTask<User, Void, Void> {
        OnUserRepositoryActionListener listener;
        InsertTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().insertTask(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class DeleteTask extends AsyncTask<User, Void, Void> {
        OnUserRepositoryActionListener listener;
        DeleteTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().deleteTask(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class FindTask extends AsyncTask<String, Void, User> {
        OnUserRepositoryActionListener listener;
        FindTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected User doInBackground(String... strings) {
            User user = appDatabase.userDao().findByName(strings[0], strings[1]);
            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            super.onPostExecute(result);
            if (result != null) {
                deleteTask(result, listener);
            } else {
                listener.actionFailed();
            }
        }
    }

    private class GetAllUsers extends AsyncTask<Void, Void, List<User>> {
        OnUserRepositoryActionListener listener;
        GetAllUsers(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = appDatabase.userDao().getAll();

            return  users;
        }

        @Override
        protected void onPostExecute(List<User> result) {
            super.onPostExecute(result);
            if (result != null) {
                listener.actionSuccess(result);
            } else {
                listener.actionFailed();
            }
        }
    }
}
