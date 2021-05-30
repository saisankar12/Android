package com.example.bookinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {
    TextView tvtitle,tvauthor,tvdate,tvpreview,download;
    String id,imageUrl;
    ImageView imageView;
    String[] books;
    MaterialFavoriteButton materialFavoriteButton;
    BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        imageView=findViewById(R.id.imgBook);
        tvtitle=findViewById(R.id.texttitle);
        tvauthor=findViewById(R.id.textAuthor);
        tvdate=findViewById(R.id.textDate);
        tvpreview=findViewById(R.id.textPreview);
        materialFavoriteButton=findViewById(R.id.materialFav);
        bookViewModel= ViewModelProviders.of(this).get(BookViewModel.class);

        books=getIntent().getStringArrayExtra(getResources().getString(R.string.book));
        id=books[0];
        tvtitle.setText(books[1]);
        tvauthor.setText(books[2]);
        Glide.with(this).load(books[3]).placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
        tvdate.setText(books[4]);
        tvpreview.setText(books[5]);
        setTitle(books[1]);
        imageUrl=books[3];

        addBooksToRoom();
        searchBook();

    }

    public void addBooksToRoom(){
        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if(favorite){
                    String title=tvtitle.getText().toString();
                    String author=tvauthor.getText().toString();
                    String date=tvdate.getText().toString();
                    String preview=tvpreview.getText().toString();
                    String bid=books[0];
                    Model model=new Model(id,title,author,date,imageUrl,preview);
                    bookViewModel.insertBook(model);
                }
                else {
                    String title=tvtitle.getText().toString();
                    String author=tvauthor.getText().toString();
                    String date=tvdate.getText().toString();
                    String preview=tvpreview.getText().toString();
                    String bid=books[0];
                    Model model=new Model(id,title,author,date,imageUrl,preview);
                    bookViewModel.deleteBook(model);
                }
            }
        });
    }

    public void searchBook(){
        String id=books[0];
        String data=bookViewModel.findBook(id);
        if(data!=null){
            materialFavoriteButton.setFavorite(true);
        }
        else {
            materialFavoriteButton.setFavorite(false);
        }
    }
}
