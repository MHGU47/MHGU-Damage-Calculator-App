package com.mhx.marcus.mhgendamagecalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WeaponSelect extends AppCompatActivity {

    ImageButton GreatSwordCalculations, LongSwordCalculations, SwordandShieldCalculations,
            DualBladesCalculations, HammerCalculations, HuntingHornCalculations, LanceCalculations,
            GunlanceCalculations, SwitchAxeCalculations, ChargeBladeCalculations, InsectGlaiveCalculations,
            BowCalculations, LightBowgunCalculations, HeavyBowgunCalculations, ProwlerCalculations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GreatSwordCalculations = findViewById(R.id.GreatSwordSelect);

        LongSwordCalculations = findViewById(R.id.LongSwordSelect);


        SwordandShieldCalculations = findViewById(R.id.SwordandShieldSelect);


        DualBladesCalculations = findViewById(R.id.DualBladesSelect);


        HammerCalculations = findViewById(R.id.HammerSelect);


        HuntingHornCalculations = findViewById(R.id.HuntingHornSelect);


        LanceCalculations = findViewById(R.id.LanceSelect);


        GunlanceCalculations = findViewById(R.id.GunlanceSelect);


        SwitchAxeCalculations = findViewById(R.id.SwitchAxeSelect);


        ChargeBladeCalculations = findViewById(R.id.ChargeBladeSelect);


        InsectGlaiveCalculations = findViewById(R.id.InsectGlaiveSelect);


        BowCalculations = findViewById(R.id.BowSelect);


        LightBowgunCalculations = findViewById(R.id.LightBowgunSelect);


        HeavyBowgunCalculations = findViewById(R.id.HeavyBowgunSelect);


        ProwlerCalculations = findViewById(R.id.ProwlerSelect);

        Selection();
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

    private void Selection(){
        GreatSwordCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, GreatSwordCalculation.class);
                intent.putExtra("Weapon", "GS");
                startActivity(intent);
            }
        });

        LongSwordCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LongSwordCalculation.class);
                intent.putExtra("Weapon", "LS");
                startActivity(intent);
            }
        });

        SwordandShieldCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, SwordandShieldCalculation.class);
                intent.putExtra("Weapon", "SNS");
                startActivity(intent);
            }
        });

        DualBladesCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, DualBladesCalculation.class);
                intent.putExtra("Weapon", "DB");
                startActivity(intent);
            }
        });

        HammerCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HammerCalculation.class);
                intent.putExtra("Weapon", "Hammer");
                startActivity(intent);
            }
        });

        HuntingHornCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HuntingHornCalculation.class);
                intent.putExtra("Weapon", "HH");
                startActivity(intent);
            }
        });

        LanceCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LanceCalculation.class);
                intent.putExtra("Weapon", "Lance");
                startActivity(intent);
            }
        });

        GunlanceCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, GunlanceCalculation.class);
                intent.putExtra("Weapon", "GL");
                startActivity(intent);
            }
        });

        SwitchAxeCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, SwitchAxeCalculation.class);
                intent.putExtra("Weapon", "SA");
                startActivity(intent);
            }
        });

        ChargeBladeCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, ChargeBladeCalculation.class);
                intent.putExtra("Weapon", "CB");
                startActivity(intent);
            }
        });

        InsectGlaiveCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, InsectGlaiveCalculation.class);
                intent.putExtra("Weapon", "IG");
                startActivity(intent);
            }
        });

        BowCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, BowCalculation.class);
                intent.putExtra("Weapon", "Bow");
                startActivity(intent);

            }
        });

        LightBowgunCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, LightBowgunCalculation.class);
                intent.putExtra("Weapon", "LBG");
                startActivity(intent);
            }
        });

        HeavyBowgunCalculations.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, HeavyBowgunCalculation.class);
                intent.putExtra("Weapon", "HBG");
                startActivity(intent);
            }
        });

        ProwlerCalculations.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, ProwlerCalculation.class);
                intent.putExtra("Weapon", "Prowler");
                startActivity(intent);
            }
        });

        GreatSwordCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "GS");
                startActivity(intent);
                return true;
            }
        });

        LongSwordCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "LS");
                startActivity(intent);
                return true;
            }
        });

        SwordandShieldCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "SNS");
                startActivity(intent);
                return true;
            }
        });

        DualBladesCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "DB");
                startActivity(intent);
                return true;
            }
        });

        HammerCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "Hammer");
                startActivity(intent);
                return true;
            }
        });

        HuntingHornCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "HH");
                startActivity(intent);
                return true;
            }
        });

        LanceCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "Lance");
                startActivity(intent);
                return true;
            }
        });

        GunlanceCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "GL");
                startActivity(intent);
                return true;
            }
        });

        SwitchAxeCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "SA");
                startActivity(intent);
                return true;
            }
        });

        ChargeBladeCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "CB");
                startActivity(intent);
                return true;
            }
        });

        InsectGlaiveCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "IG");
                startActivity(intent);
                return true;
            }
        });

        BowCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "Bow");
                startActivity(intent);
                return true;
            }
        });

        LightBowgunCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "LBG");
                startActivity(intent);
                return true;
            }
        });

        HeavyBowgunCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "HBG");
                startActivity(intent);
                return true;
            }
        });

        ProwlerCalculations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(WeaponSelect.this, UI.class);
                intent.putExtra("Weapon", "Prowler");
                startActivity(intent);
                return true;
            }
        });
    }
}
