package com.example.bookinfo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class BookRepository {

    public static BookDao bookDao;
    LiveData<List<Model>> getData;

    public BookRepository(Context context){
        BookDataBase bookDataBase=BookDataBase.getInstance(context);
        bookDao=bookDataBase.bookDao();
        getData=bookDao.getBooks();
    }

    public String searchBook(String bid){
        String bookPresent=bookDao.checkBook(bid);
        return bookPresent;
    }

    LiveData<List<Model>> getAllBooks(){
        return getData;
    }

    public void insertData(Model model){
        new InsertTask(bookDao).execute(model);

    }

    public void deleteData(Model model){
        new DeleteTask(bookDao).execute(model);
    }

    public class InsertTask extends AsyncTask<Model,Void,Void>{

        BookDao bookDao;

        public InsertTask(BookDao bookDao){
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Model... models) {
            bookDao.insert(models[0]);
            return null;
        }
    }

    public class DeleteTask extends AsyncTask<Model,Void,Void>{
        BookDao bookDao;

        public DeleteTask(BookDao bookDao){
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Model... models) {

            bookDao.delete(models[0]);
            return null;
        }
    }


}
