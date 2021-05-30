package com.example.user.e_quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {

    Context context;
    String[] s2;
    Dialog rulesdialog;

    public RecyclerAdapter2(JavaActivity javaActivity, String[] s1) {
        this.context = javaActivity;
        this.s2 = s1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design2, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.textViewType.setText(s2[i]);
        myViewHolder.buttonAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Play Or Quit");
                builder.setMessage("Are you ready to attempt the Quiz..? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, DetailActivity1.class);
                        intent.putExtra("pos", i);
                        Toast.makeText(context, "" + i, Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();*/
                new FancyGifDialog.Builder((Activity) context)
                        .setTitle("RULES AND INSTRUCTIONS")
                        .setMessage("1)Double clicking the answer will not be selected\n2)No negative evaluation\n3)For every right answer,1 mark will be awarded\n4)Time alloted-3 minutes\n5)After completion of time your result will be displayed\n6)After completion of questions , your result will be displayed\n7)You can view answers with explanation after completion\n8)When you quit the exam in the middle ,your score is displayed until then.\n" +
                                "ALL THE BEST :)")
                        .setNegativeBtnText("CANCEL")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("TAKE A QUIZ")
                        .setGifResource(R.drawable.rulesinstru)
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .isCancellable(false)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Intent intent = new Intent(context, DetailActivity1.class);
                                intent.putExtra("pos", i);
                                //Toast.makeText(context, "" + i, Toast.LENGTH_SHORT).show();
                                context.startActivity(intent);
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {

                            }
                        })
                        .build();

/*

                rulesdialog.setContentView(R.layout.activity_rules_and_instructions);
                rulesdialog.setTitle("Rules");

               */
/* myViewHolder.buttonTakeQuiz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "custom dialog", Toast.LENGTH_SHORT).show();
                    }
                });
               *//*
 rulesdialog.show();
*/


            }
        });

    }

    @Override
    public int getItemCount() {
        return s2.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewType;
        Button buttonAttempt;
        TextView textViewRules;
        ImageView imgRules;
        Button buttonTakeQuiz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewType = itemView.findViewById(R.id.txtType);
            buttonAttempt = itemView.findViewById(R.id.btnAttempt);

            rulesdialog = new Dialog(context);
            textViewRules = rulesdialog.findViewById(R.id.txtRules);
            imgRules = rulesdialog.findViewById(R.id.ivRules);
            buttonTakeQuiz = rulesdialog.findViewById(R.id.btnTakeQuiz);
        }
    }
}
