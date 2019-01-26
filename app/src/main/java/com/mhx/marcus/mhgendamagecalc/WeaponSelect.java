package com.mhx.marcus.mhgendamagecalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class WeaponSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton GreatSwordCalculations = (ImageButton) findViewById(R.id.GreatSwordSelect);
        GreatSwordCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, GreatSwordCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton LongSwordCalculations = (ImageButton) findViewById(R.id.LongSwordSelect);
        LongSwordCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LongSwordCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton SwordandShieldCalculations = (ImageButton) findViewById(R.id.SwordandShieldSelect);
        SwordandShieldCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, SwordandShieldCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton DualBladesCalculations = (ImageButton) findViewById(R.id.DualBladesSelect);
        DualBladesCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, DualBladesCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton HammerCalculations = (ImageButton) findViewById(R.id.HammerSelect);
        HammerCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HammerCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton HuntingHornCalculations = (ImageButton) findViewById(R.id.HuntingHornSelect);
        HuntingHornCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HuntingHornCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton LanceCalculations = (ImageButton) findViewById(R.id.LanceSelect);
        LanceCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LanceCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton GunlanceCalculations = (ImageButton) findViewById(R.id.GunlanceSelect);
        GunlanceCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, GunlanceCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton SwitchAxeCalculations = (ImageButton) findViewById(R.id.SwitchAxeSelect);
        SwitchAxeCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, SwitchAxeCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton ChargeBladeCalculations = (ImageButton) findViewById(R.id.ChargeBladeSelect);
        ChargeBladeCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, ChargeBladeCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton InsectGlaiveCalculations = (ImageButton) findViewById(R.id.InsectGlaiveSelect);
        InsectGlaiveCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, InsectGlaiveCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton BowCalculations = (ImageButton) findViewById(R.id.BowSelect);
        BowCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, BowCalculation.class);
                startActivity(intent);

            }
        });

        ImageButton LightBowgunCalculations = (ImageButton) findViewById(R.id.LightBowgunSelect);
        LightBowgunCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LightBowgunCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton HeavyBowgunCalculations = (ImageButton) findViewById(R.id.HeavyBowgunSelect);
        HeavyBowgunCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HeavyBowgunCalculation.class);
                startActivity(intent);
            }
        });

        ImageButton ProwlerCalculations = (ImageButton) findViewById(R.id.ProwlerSelect);
        ProwlerCalculations.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, ProwlerCalculation.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_weapon_select, menu);
        //return true;
        MenuInflater inflater = super.getMenuInflater();
        inflater.inflate(R.menu.menu_weapon_select, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.credits) {
            Intent intent = new Intent(WeaponSelect.this, Credit.class);
            startActivity(intent);
        }
        else if (id == R.id.log) {
            Intent intent = new Intent(WeaponSelect.this,ChangeLog.class);
            startActivity(intent);
        }
        else if (id == R.id.feedback) {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "mhgencalc@gmail.com" });
            Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(Intent.createChooser(Email, "Send Feedback:"));
            return true;

            }

        return true;
    }
}
