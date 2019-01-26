package com.mhx.marcus.mhgendamagecalc;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Test2 extends Activity {

    Spinner sp1,sp2;
    ArrayAdapter<String> adp1,adp2;
    List<String> l1,l2;
    int pos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_test);


        l1=new ArrayList<String>();

        l1.add("A");
        l1.add("B");

        sp1= (Spinner) findViewById(R.id.spinner);
        sp2= (Spinner) findViewById(R.id.spinner2);

        adp1=new ArrayAdapter<String> (this,android.R.layout.simple_dropdown_item_1line,l1);
        adp1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp1.setAdapter(adp1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                pos=arg2;
                add();

            }

            private void add() {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();

                switch(pos)
                {
                    case 0:
                        l2= new ArrayList<String>();
                        l2.add("A 1");
                        l2.add("A 2");

                        adp2=new ArrayAdapter<String>(Test2.this,
                                android.R.layout.simple_dropdown_item_1line,l2);
                        adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        sp2.setAdapter(adp2);

                        select();

                        break;
                    case 1:
                        l2= new ArrayList<String>();
                        l2.add("B 1");
                        l2.add("B 2");

                        adp2=new ArrayAdapter<String>(Test2.this,
                                android.R.layout.simple_dropdown_item_1line,l2);
                        adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        sp2.setAdapter(adp2);

                        select();

                        break;
                }

            }

            private void select() {
                // TODO Auto-generated method stub

                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getBaseContext(), "Test "+arg2, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}