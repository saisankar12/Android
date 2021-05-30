package com.example.bookinfo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    BookRepository bookRepository;
    LiveData<List<Model>> getFavourites;
    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository=new BookRepository(application);
        getFavourites=bookRepository.getAllBooks();
    }

    public LiveData<List<Model>> getFavData(){
        return getFavourites;
    }

    public void insertBook(Model model){
        bookRepository.insertData(model);
    }

    public void deleteBook(Model model){
        bookRepository.deleteData(model);
    }

    public String findBook(String bid){
        return bookRepository.searchBook(bid);
    }
}
