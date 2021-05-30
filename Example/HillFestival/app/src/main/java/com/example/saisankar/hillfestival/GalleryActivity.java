package com.example.saisankar.hillfestival;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imagesList=new ArrayList<>();
        imagesList.add("https://kotappakondablog.files.wordpress.com/2016/03/pics-25-dec-2015.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016-2.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-21.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-1.jpg?w=1000&h=");imagesList.add("https://gotirupati.com/wp-content/uploads/2016/06/Kotappakonda-temple-timings-Copy.jpg");
        imagesList.add("https://www.rvatemples.com/wp-content/uploads/2018/02/chilkur-balaji-temple-hyderabad.jpg");
        imagesList.add("https://2.bp.blogspot.com/-oyxMsIY0EA0/WKAXND17r4I/AAAAAAAACHs/TqZkRzLkWcwLyD4wzU01tt4YiNFLjlPwQCLcB/s1600/Kotappakonda%2BSri%2BTrikoteswara%2BSwamy%2BTemple.JPG");
        imagesList.add("https://pbs.twimg.com/media/DVOJSRMW0AA9uhB.jpg");
        imagesList.add("https://pbs.twimg.com/media/DWRiVUfXcAEFx05.jpg");

        imagesList.add("https://pbs.twimg.com/media/DOm1bToW0AIiZh2.jpg");
        imagesList.add("https://pbs.twimg.com/media/DNRyid7XcAAGsJ0.jpg");
        imagesList.add("https://pbs.twimg.com/media/DMIDmWeXkAISorh.jpg");
        imagesList.add("https://pbs.twimg.com/media/DMIDVWLXUAAS7fo.jpg");
        imagesList.add("https://pbs.twimg.com/media/DF9Rb7oU0AAVhW4.jpg");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-5.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-6.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-10.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-9.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-11.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-13.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-12.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-16.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-22.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-2.jpg?w=1000&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-7.jpg?w=1000&h=");

        imagesList.add("https://pbs.twimg.com/media/DWRiVU0W0AAc3oo.jpg");
        imagesList.add("https://pbs.twimg.com/media/DWRiVVWX0AMufGF.jpg");
        imagesList.add("https://pbs.twimg.com/media/DWNT-LnX0AE3o0F.jpg");
        imagesList.add("https://pbs.twimg.com/media/DVOHeyVX0AEkKT8.jpg");
        imagesList.add("https://pbs.twimg.com/media/DUqzbzgXUAUkRpV.jpg");
        imagesList.add("https://pbs.twimg.com/media/DSSj-iAW4AA8ZpX.jpg");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-14.jpg?w=370&h=");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-19.jpg?w=1000&h=");
        imagesList.add("https://pbs.twimg.com/ext_tw_video_thumb/963268868016918528/pu/img/87CLKuGIIP0vy3_m.jpg");
        imagesList.add("https://pbs.twimg.com/media/Drj2iWLW4AAkv9l.jpg");
        imagesList.add("https://pbs.twimg.com/media/DZzEJ_2WAAEu_f2.jpg");
        imagesList.add("https://pbs.twimg.com/media/DZzELcmXUAIVehW.jpg");

        imagesList.add("https://pbs.twimg.com/media/DV1JNN5WsAAP3oB.jpg");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016-2.jpg?w=585&h=329");
        imagesList.add("https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016.jpg?w=585&h=328");

        imagesList.add("https://pbs.twimg.com/media/DZzEMmOXUAIiVp8.jpg");
        imagesList.add("https://pbs.twimg.com/media/DWRiTz2X4AE94BS.jpg");


        imagesList.add("https://pbs.twimg.com/media/DV1JNN7X4AExxfa.jpg");
        imagesList.add("https://pbs.twimg.com/media/DV1JNN-W4AAv2vF.jpg");

        imagesList.add("https://pbs.twimg.com/media/DOm1bToW0AIiZh2.jpg");
        imagesList.add("https://pbs.twimg.com/media/DNRyid7XcAAGsJ0.jpg");
        imagesList.add("https://pbs.twimg.com/media/DMIDmWeXkAISorh.jpg");
        imagesList.add("https://pbs.twimg.com/media/DMIDVWLXUAAS7fo.jpg");


        mRecyclerView=(RecyclerView)findViewById(R.id.recycler);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        mRecyclerView.setAdapter(new GalleryAdapter(this,imagesList));
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>{

        private List<String> list;

        private Context mContext;
        public GalleryAdapter(GalleryActivity galleryActivity, List<String> imagesList) {
            this.mContext=galleryActivity;
            this.list = imagesList;
        }

        @NonNull
        @Override
        public GalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gallery_design,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull GalleryAdapter.MyViewHolder holder, int position) {

            Picasso.with(mContext).load(list.get(position)).into(holder.imageIV);
            holder.getView().setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.zoom));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private View view;
            protected ImageView imageIV;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.view=itemView;
                this.imageIV=itemView.findViewById(R.id.imageIV);
            }

            public View getView() {
                return view;
            }
        }
    }
}
