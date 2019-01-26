package com.mhx.marcus.mhgendamagecalc;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    Spinner MonsterSelect;
    Spinner HitzoneSelect;
    Button Calculate;
    int SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = (Spinner) findViewById(R.id.spinner);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(adapter);

        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        MonsterSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedMonster = position;
                change();
            }

            public void change(){

                switch(SelectedMonster){
                    case 3:
                        HitzoneSelect = (Spinner) findViewById(R.id.spinner2);

                        /*Gives the spinner a place to pull values from. In this case it's using the values from the
                        'Styles' array and tells it where to place it on the layout*/
                        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.BeastHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        adapter2.notifyDataSetChanged();
                        HitzoneSelect.setAdapter(adapter2);
                        getHitzoneGroup("");

                        select();

                        break;

                    default:
                        HitzoneSelect = (Spinner) findViewById(R.id.spinner2);

                        /*Gives the spinner a place to pull values from. In this case it's using the values from the
                        'Styles' array and tells it where to place it on the layout*/
                        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.NoneHitzones,android.R.layout.simple_spinner_dropdown_item);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter3);
                        getHitzoneGroup("Normal");

                        select();

                        break;

                }
            }

            public void select(){

                HitzoneSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final String Monster = String.valueOf(MonsterSelect.getSelectedItem());
                        final String Hitzone = String.valueOf(HitzoneSelect.getSelectedItem());

                        getMonster(Monster);
                        getHitzone(Hitzone);


                        Snackbar.make(view, Hitzone, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*HitzoneSelect = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.NormalMonsterHitzones,
                android.R.layout.simple_spinner_dropdown_item);

        HitzoneSelect.setAdapter(adapter2);

        HitzoneSelect.setOnItemSelectedListener(this);*/

        calculate();
    }
    public void calculate(){
        //Toast.makeText(Test.this, "Hi", Toast.LENGTH_SHORT).show();
        Calculate = (Button) findViewById(R.id.button);
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Test.this, "Hi", Toast.LENGTH_SHORT).show();

                int Counter, temp;
                CharSequence[] Counter2;

                String Mon = ChosenMonster + "Hitzones";
                String HitzoneCheck = HitzoneGroup + "MonsterHitzones";

                Counter = getResources().getIdentifier(Mon, "array", getPackageName());

                //temp used to hold the name of the array that holds the hitzone names
                temp = getResources().getIdentifier(HitzoneCheck, "array", getPackageName());

                //Counter2 used to store the names of the hitzone to make sure the correct value is
                //used when calculating the damage done
                Counter2 = getResources().getTextArray(temp);

                TextView text = (TextView) findViewById(R.id.editText);

                float text2 = Float.parseFloat(text.getText().toString());

                //hitzones carries the Hitzones of the selected monster
                int[] hitzones = getResources().getIntArray(Counter);

                //Attack is used to hold the modified value of the users calculated attack: ((Motion *
                //Raw Damage) * (Hitzone * Sharpness)) + (Element * (Hitzone * Sharpness))
                int attack = 0;
                for (int i = 0; i < Counter2.length; i++) {
                    if (Counter2[i].equals(ChosenHitzone)) {
                        attack = hitzones[i];
                        i = Counter2.length;
                    }
                }

                float output = (text2 * attack) / 100;

                TextView textview = (TextView) findViewById(R.id.textView5);

                textview.setText(String.format("%s", Math.round(output)));

            }
        });
    }

    public static String getMonster(String i) {
        ChosenMonster = i;
        return ChosenMonster;
    }
    public static String getHitzoneGroup(String i) {
        HitzoneGroup = i;
        return HitzoneGroup;
    }
    public static String getHitzone(String i) {
        ChosenHitzone = i;
        return ChosenHitzone;
    }
}

