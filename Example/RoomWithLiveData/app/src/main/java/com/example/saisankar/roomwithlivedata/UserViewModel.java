package com.example.saisankar.roomwithlivedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import java.util.List;

public class UserViewModel extends AndroidViewModel {

    public UserRepository userRepository;
    public LiveData<List<User>> liveData;
    public UserViewModel(Application application) {
        super(application);

        userRepository=new UserRepository(application);
        liveData=userRepository.getliveData();
    }
public void insert(User user){
        userRepository.insert(user);
}

    public void update(User user){
        userRepository.update(user);
    }

public LiveData<List<User>> getUserLiveData(){
        return liveData;
}

public void delete(User user){
        userRepository.delete(user);
}

public void deleteAll(User user){
        userRepository.deleteAlldata(user);
}
}
