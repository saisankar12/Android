package com.example.saisankar.hillfestival;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GettingHere extends AppCompatActivity {

    String[] busarray={"నరసరావుపేట నుండి కోటప్పకొండ 15 కి.మీ / Narasarao Pet to Kotappakonda 15 k.m",
    "చిలకలూరిపేట నుండి కోటప్పకొండ 21 కి.మీ / Chilakaluripet to Kotappakonda 21 k.m",
    "వినుకొండ నుండి కోటప్పకొండ 45 కి.మీ (వయా) పెట్లూరివారి పాలెం. / Vinukonda to Kotappakonda via Petloorivari palem 45 k.m"};

    String[] trainarray={"రైలు మార్గం ద్వారా వచ్చే యాత్రికులు నరసరావుపేట చేరుకుని, అక్కడి నుంచి రోడ్డు మార్గం ద్వారా స్వామి వారిని దర్శించుకోవచ్చు.",
    "Nearest railway station is Narasarao pet."};
    String[] flightarray={"గన్నవరం విమానాశ్రయం 100 కి.మీ","Nearest airport is Gannavaram near Vijayawada 100k.m."};
    TextView bustext,traintext,flighttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_here);

        bustext=findViewById(R.id.busroute);
        traintext=findViewById(R.id.trainroute);
        flighttext=findViewById(R.id.flightroute);

        for (String array:busarray){
            bustext.append("\u2023"+"\t"+array+"\n");
        }

        for (String array:trainarray){
            traintext.append("\u2023"+"\t"+array+"\n");
        }
        for (String array:flightarray){
            flighttext.append("\u2023"+"\t"+array+"\n");
        }
    }
}
