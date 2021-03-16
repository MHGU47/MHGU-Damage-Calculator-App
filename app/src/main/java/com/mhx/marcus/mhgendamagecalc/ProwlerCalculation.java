package com.mhx.marcus.mhgendamagecalc;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProwlerCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner BoomerangSelect, SharpnessSelect, MonsterSelect, HitzoneSelect, ElementSelect, ProwlerTypeSelect;
    Button Calculate;
    String SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenArt;;
    static float SelectedSharpnessElmModifier, SelectedSharpnessAtkModifier, ProwlerModifier;
    static int NineLivesModifier;

    //Skill and Hunter Art Selection variables - Start
    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner NineLivesAttackSelect, SupportSkillsSelect;
    CheckBox FuryStateCheck, BeastModeCheck, PowertalonCheck, PowercharmCheck, AttackUpSCheck,
            AttackUpLCheck, TriforceCheck, AffinityUpSCheck, AffinityUpLCheck, DemonHornCheck,
            RangedAttackUpCheck, LastStandCheck, FanalisCheck, UniversalCheck, WorldsStrongestCheck,
            BaddestCatEverCheck, WeaponUpgradeCheck;
    //-End-


    /*Creates a string/number variables which are arrays.*/
    float[] SharpModAtk = {0.5f, 0.75f, 1, 1.05f, 1.2f, 1.32f, 1.39f};
    float[] SharpModElm = {0.25f, 0.5f, 0.75f, 1, 1.06f, 1.12f, 1.2f};

    String[] TextViewIDsAttacks;
    String[] TextViewIDsNames;
    String[] AllTextViewIDs;

    Float[][] MotionGroup;
    Float[] NormalMode =  {0.14f, 0.14f, 0.1f, 0.14f, /*Space*/0.16f, 0.18f, 0.12f, 0.08f, /*space*/ 0.09f,
            0.34f, 0.2f, 0.24f, 0.08f, /*Space*/0.3f};
    String[] NormalModeNames = {"Draw Attack","Forward Attack","Idle Attack","Left Slash",
            "Downward Slash","Upward Slash","Spinning Finisher - Per hit","Boomerang",
            "Charged Boomerang","Boomerang Finisher","Lung Attack","Jump Attack","Fury Attack (per hit)",
            "Fury Attack Finisher"};

    Float[] BeastMode = {0.2f, 0.24f, 0.22f, 0.1f, /*Space*/0.24f, 0.24f, 0.04f, 0.06f,
            /*Space*/0.12f, 0.08f, 0.09f, 0.34f, 0.08f, /*Space*/0.3f};
    String[] BeastModeNames = {"Forward Claws (2 hits)","Triple Hook","Backflip","Finisher (per hit)",
            "Frontflip (per hit)","Forward Thrust","Super Finisher (per hit)","Ledge Attack (per hit)",
            "   -Finisher","Boomerang","Charged Boomerang","Boomerang Finisher","Fury Attack (per hit)",
            "Fury Attack Finisher"};

    Float[] AntiMonsterMine = {0.12f, 30f};
    Float[] AntiMonsterMine2 = {0.24f, 60f};
    Float[] ClawDance = {1.56f};
    Float[] ExplosiveRoll = {0.02f, 5f};
    Float[] FelyneComet = {0.6f};
    Float[] MiniBarrelBombay = {0.02f, 5f};
    Float[] BarrelBombay = {0.06f, 15f};
    Float[] BounceBombay = {0.08f, 20f};
    Float[] BigBarrelBombay = {0.12f, 30f};
    Float[] MegaBarrelBombay = {0.24f, 60f};
    Float[] GigaBarrelBombay = {0.44f, 110f};
    Float[] RathofMeow = {0.02f, 10f};
    Float[] ShockTripper = {0.2f, 0.12f};
    Float[] BoomerangType = {0f, 0f, 0f};

    Float[][] HunterArts = {AntiMonsterMine, AntiMonsterMine2, ClawDance, ExplosiveRoll, FelyneComet,
            MiniBarrelBombay, BarrelBombay, BounceBombay, BigBarrelBombay, MegaBarrelBombay,
            GigaBarrelBombay, RathofMeow, ShockTripper};
    Float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Attack Damage", "Fixed Damage"};
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prowler_calculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Gives the variable for the spinner 'BoomerangSelect' the actual value for a spinner.*/
        BoomerangSelect = (Spinner) findViewById(R.id.BoomerangSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Boomerangs' array and tells it where to place it on the layout*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Boomerangs,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        BoomerangSelect.setAdapter(adapter);

        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        BoomerangSelect.setOnItemSelectedListener(this);

        SharpnessSelect = (Spinner) findViewById(R.id.SharpnessSelect);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.Sharpness,
                android.R.layout.simple_spinner_dropdown_item);

        SharpnessSelect.setAdapter(adapter2);

        SharpnessSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Prowler type affects support skill damage",Toast.LENGTH_LONG).show();

        ElementSelect = (Spinner) findViewById(R.id.ElementSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(adapter3);

        ElementSelect.setOnItemSelectedListener(this);

        SupportSkillsSelect = (Spinner) findViewById(R.id.SupportSkillsSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.Prowler_HA_Names,
                android.R.layout.simple_spinner_dropdown_item);

        SupportSkillsSelect.setAdapter(adapter5);

        SupportSkillsSelect.setOnItemSelectedListener(this);

        ProwlerTypeSelect = (Spinner) findViewById(R.id.ProwlerTypeSelect);

        ArrayAdapter ProwlerTypeAdapter = ArrayAdapter.createFromResource(this,R.array.ProwlerType,
                android.R.layout.simple_spinner_dropdown_item);

        ProwlerTypeSelect.setAdapter(ProwlerTypeAdapter);

        ProwlerTypeSelect.setOnItemSelectedListener(this);

        NineLivesAttackSelect = (Spinner) findViewById(R.id.NineLivesAttackSelect);

        ArrayAdapter NineLivesAttackAdapter = ArrayAdapter.createFromResource(this,R.array.NineLivesAttack,
                android.R.layout.simple_spinner_dropdown_item);

        NineLivesAttackSelect.setAdapter(NineLivesAttackAdapter);

        NineLivesAttackSelect.setOnItemSelectedListener(this);

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = (Spinner) findViewById(R.id.MonsterSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(adapter4);

        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        MonsterSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedMonster  = String.valueOf(MonsterSelect.getSelectedItem());
                List<String> MonsterCatchList = Arrays.asList("Lagombi","Snowbaron Lagombi","Arzuros",
                        "Redhelm Arzuros","Plesioth","Rathian","Cephadrome","Lavasioth","Gypceros",
                        "Gendrome","Iodrome","Giadrome","Velocidrome","Bulldrome","Gold Rathian",
                        "Silver Rathalos","Rathalos","Rathian","Yian Kut Ku","Yian Garuga",
                        "Deadeye Yian Garuga","Malfestio","Nightcloak Malfestio");

                String Stripped = SelectedMonster.replaceAll("\\s","");

                if(MonsterCatchList.contains(SelectedMonster)){
                    if(SelectedMonster.contains("Lagombi") || SelectedMonster.contains("Arzuros")){
                        getMonsterType("Beast");

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if(SelectedMonster.equals("Plesioth") || SelectedMonster.equals("Rathian")
                            || SelectedMonster.equals("Cephadrome") || SelectedMonster.equals("Rathalos")
                            || SelectedMonster.equals("Lavasioth") || SelectedMonster.equals("Gypceros")
                            || SelectedMonster.equals("Yian Kut Ku") || SelectedMonster.equals("Yian Garuga")
                            || SelectedMonster.equals("Deadeye Yian Garuga")){
                        getMonsterType("FlyingWyvern");

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if(SelectedMonster.equals("Gold Rathian") || SelectedMonster.equals("Silver Rathalos")){
                        getMonsterType("FlyingWyvernWounded");

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SelectedMonster = Stripped;
                    }
                    else if(SelectedMonster.equals("Malfestio") || SelectedMonster.equals("Nightcloak Malfestio")){
                        getMonsterType("Malfestio");

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SelectedMonster = Stripped;
                    }
                    else{
                        getMonsterType("Raptor");

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                else if(SelectedMonster.equals("None")){
                    getMonsterType("None");
                }
                else{
                    getMonsterType(Stripped);

                    Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                change();
            }

            public void change(){
                String Stripped = SelectedMonster.replaceAll("\\s","");

                int Counter = getResources().getIdentifier(Stripped + "Hitzones", "array", getPackageName());

                switch(MonsterType){
                    case "Beast":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.BeastHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter);
                        getHitzoneGroup("Beast");

                        select();

                        break;

                    case "FlyingWyvern":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter2);
                        getHitzoneGroup("FlyingWyvern");

                        select();

                        break;

                    case "FlyingWyvernWounded":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernWoundedHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter3);
                        getHitzoneGroup("FlyingWyvernWounded");

                        select();

                        break;

                    case "Raptor":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.RaptorHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter4);
                        getHitzoneGroup("Raptor");

                        select();

                        break;

                    case "Malfestio":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.MalfestioHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter5);
                        getHitzoneGroup("Malfestio");

                        select();

                        break;

                    case "None":
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.NoneHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter6);
                        getHitzoneGroup("None");

                        select();

                        break;

                    default:
                        HitzoneSelect = (Spinner) findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(getApplicationContext(),
                                Counter,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter7);
                        getHitzoneGroup(Stripped);

                        select();

                        break;

                }
            }

            public void select(){

                HitzoneSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final String Hitzone = String.valueOf(HitzoneSelect.getSelectedItem());

                        String Stripped = SelectedMonster;
                        String st = Stripped.replaceAll("\\s","");

                        getMonster(st);
                        getHitzone(Hitzone);
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

        //Skill switch
        //Creates a method that checks for the state of the switch and is triggered whenever it is
        //changed, making the skills disappear and reappear as necessary. It also sets the value of
        //the 'SkillCheck' variable to either '1' or '0' depending on the state of the switch in order
        //to make sure that none of the  values from the spinners affect the overall calculation if
        //the switch is set to 'Off'.
        SkillSwitch = (Switch) findViewById(R.id.SkillsSwitch);
        SkillSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout SkillsLayout = (RelativeLayout) findViewById(getResources().getIdentifier("Skills", "id", getPackageName()));
                if(isChecked){
                    SkillsLayout.setVisibility(View.VISIBLE);

                    Calculate = (Button) findViewById(R.id.CalculateButton);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) Calculate.getLayoutParams();
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible_Prowler),
                            0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    Calculate.setLayoutParams(Margin);

                    SkillCheck = true;
                }
                else{
                    SkillsLayout.setVisibility(View.GONE);

                    Calculate = (Button) findViewById(R.id.CalculateButton);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) Calculate.getLayoutParams();
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Hidden),
                            0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    Calculate.setLayoutParams(Margin);

                    SkillCheck = false;
                }
            }
        });

        //Defines the checkboxes for the skills.
        PowercharmCheck = (CheckBox) findViewById(R.id.PowercharmCheckBox);
        PowertalonCheck = (CheckBox) findViewById(R.id.PowertalonCheckBox);
        AttackUpSCheck = (CheckBox) findViewById(R.id.AttackUpSCheckBox);
        AttackUpLCheck = (CheckBox) findViewById(R.id.AttackUpLCheckBox);
        TriforceCheck = (CheckBox) findViewById(R.id.TriforceCheckBox);
        AffinityUpSCheck = (CheckBox) findViewById(R.id.AffinityUpSCheckBox);
        AffinityUpLCheck = (CheckBox) findViewById(R.id.AffinityUpLCheckBox);
        DemonHornCheck = (CheckBox) findViewById(R.id.DemonHornCheckBox);
        RangedAttackUpCheck = (CheckBox) findViewById(R.id.RangedAttackUpCheckBox);
        LastStandCheck = (CheckBox) findViewById(R.id.LastStandCheckBox);
        FanalisCheck = (CheckBox) findViewById(R.id.FanalisCheckBox);
        UniversalCheck = (CheckBox) findViewById(R.id.UniversalCheckBox);
        WorldsStrongestCheck = (CheckBox) findViewById(R.id.WorldsStrongestCheckBox);
        BaddestCatEverCheck = (CheckBox) findViewById(R.id.BaddestCatEverCheckBox);
        FuryStateCheck = (CheckBox)findViewById(R.id.FuryStateCheck);
        BeastModeCheck = (CheckBox) findViewById(R.id.BeastModeCheck);
        WeaponUpgradeCheck = (CheckBox)findViewById(R.id.WeaponUpgradeCheckBox);
        
        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable called 'BoomerangSelectText' and sets it as */
        TextView Boomerang = (TextView) view;
        String SelectedBoomerang = Boomerang.getText().toString();
        if (SelectedBoomerang.equals("Normal")) {
            BoomerangType[0] = 0.08f;
            BoomerangType[1] = 0.09f;
            BoomerangType[2] = 0.34f;
        } else if (SelectedBoomerang.equals("Big")) {
            BoomerangType[0] = 0.12f;
            BoomerangType[1] = 0.14f;
            BoomerangType[2] = 0.38f;
        } else if (SelectedBoomerang.equals("Pierce")) {
            BoomerangType[0] = 0.17f;
            BoomerangType[1] = 0.18f;
            BoomerangType[2] = 0.5f;
        } else if (SelectedBoomerang.equals("Big + Pierce")) {
            BoomerangType[0] = 0.24f;
            BoomerangType[1] = 0.26f;
            BoomerangType[2] = 0.62f;
        }

        TextView Sharpness = (TextView) view;
        String SharpnessModifier = Sharpness.getText().toString();
        if (SharpnessModifier.equals("Red")) {
            getAtk(SharpModAtk[0]);
            getElm(SharpModElm[0]);
        } else if (SharpnessModifier.equals("Orange")) {
            getAtk(SharpModAtk[1]);
            getElm(SharpModElm[1]);
        } else if (SharpnessModifier.equals("Yellow")) {
            getAtk(SharpModAtk[2]);
            getElm(SharpModElm[2]);
        } else if (SharpnessModifier.equals("Green")) {
            getAtk(SharpModAtk[3]);
            getElm(SharpModElm[3]);
        } else if (SharpnessModifier.equals("Blue")) {
            getAtk(SharpModAtk[4]);
            getElm(SharpModElm[4]);
        } else if (SharpnessModifier.equals("White")) {
            getAtk(SharpModAtk[5]);
            getElm(SharpModElm[5]);
        } else if(SharpnessModifier.equals("Purple")) {
            getAtk(SharpModAtk[6]);
            getElm(SharpModElm[6]);
        }

        TextView SelectedElement = (TextView) view;
        String ElementText = SelectedElement.getText().toString();
        if(ElementText.equals("Fire")){
            getChosenElement("Fire");
        }
        else if(ElementText.equals("Water")) {
            getChosenElement("Water");
        }
        else if(ElementText.equals("Ice")) {
            getChosenElement("Ice");
        }
        else if(ElementText.equals("Thunder")) {
            getChosenElement("Thunder");
        }
        else if(ElementText.equals("Dragon")) {
            getChosenElement("Dragon");
        }
        else if(ElementText.equals("NONE")){
            getChosenElement("NONE");
        }

        TextView SelectedArt = (TextView) view;
        String ArtText = SelectedArt.getText().toString();
        if(ArtText.equals("Anti-Monster Mine")){
            getHunterArt("Anti-Monster Mine");
        }
        else if(ArtText.equals("Anti-Monster Mine+")){
            getHunterArt("Anti-Monster Mine+");
        }
        else if(ArtText.equals("Claw Dance")){
            getHunterArt("Claw Dance");
        }
        else if(ArtText.equals("Explosive Roll")) {
            getHunterArt("Explosive Roll");
        }
        else if(ArtText.equals("Felyne Comet")) {
            getHunterArt("Felyne Comet");
        }
        else if(ArtText.equals("Mini Barrel Bombay")) {
            getHunterArt("Mini Barrel Bombay");
        }
        else if(ArtText.equals("Barrel Bombay")) {
            getHunterArt("Barrel Bombay");
        }
        else if(ArtText.equals("Bounce Bombay")) {
            getHunterArt("Bounce Bombay");
        }
        else if(ArtText.equals("Big Barrel Bombay")) {
            getHunterArt("Big Barrel Bombay");
        }
        else if(ArtText.equals("Mega Barrel Bombay")) {
            getHunterArt("Mega Barrel Bombay");
        }
        else if(ArtText.equals("Giga Barrel Bombay")) {
            getHunterArt("Giga Barrel Bombay");
        }
        else if(ArtText.equals("Rath-of-Meow")) {
            getHunterArt("Rath-of-Meow");
        }
        else if(ArtText.equals("Shock Tripper")) {
            getHunterArt("Shock Tripper");
        }
        else if(ArtText.equals("-None-")) {
            getHunterArt("-None-");
        }

        TextView SelectedProwlerType = (TextView) view;
        String ProwlerTypeText = SelectedProwlerType.getText().toString();
        if(ProwlerTypeText.equals("Charisma")){
            getProwler(1);
        }
        else if(ProwlerTypeText.equals("Fighting")){
            getProwler(0.9f);
        }
        else if(ProwlerTypeText.equals("Protection")) {
            getProwler(1.1f);
        }
        else if(ProwlerTypeText.equals("Assisting")) {
            getProwler(0.9f);
        }
        else if(ProwlerTypeText.equals("Healing")) {
            getProwler(0.7f);
        }
        else if(ProwlerTypeText.equals("Bombing")) {
            getProwler(1.2f);
        }
        else if(ProwlerTypeText.equals("Gathering")) {
            getProwler(0.7f);
        }
        else if(ProwlerTypeText.equals("Beast")) {
            getProwler(1);
        }

        TextView NineLivesSelect = (TextView) view;
        String NineLivesText = NineLivesSelect.getText().toString();
        if(NineLivesText.equals("First Revive (+3)")){
            getNineLives(3);
        }
        else if(NineLivesText.equals("Second Revive (+6)")){
            getNineLives(6);
        }
        else if(NineLivesText.equals("Third Revive (+9)")) {
            getNineLives(9);
        }
        else if(NineLivesText.equals("Fourth Revive (+12)")) {
            getNineLives(12);
        }
        else if(NineLivesText.equals("Fifth Revive (+15)")) {
            getNineLives(15);
        }
        else if(NineLivesText.equals("Sixth Revive (+18)")) {
            getNineLives(18);
        }
        else if(NineLivesText.equals("Seventh Revive (+21)")) {
            getNineLives(21);
        }
        else if(NineLivesText.equals("Eighth Revive (+24)")) {
            getNineLives(24);
        }
        else if(NineLivesText.equals("[Nine Lives Attack]")) {
            getNineLives(0);
        }
    }

    public void Calculate(){

        /*Binds the variable 'Calculate' to a button, specifically the 'CalculateButton' using its ID.*/
        Calculate = (Button) findViewById(R.id.CalculateButton);

        /*Gives the button an onClickListener() method (function) and makes it akin to an inline function.*/
        Calculate.setOnClickListener(new View.OnClickListener() {

            /*Creates a TextView array variable called 'textviews' and sets it so the values
            affect all the textviews needed.*/
            TextView[] textviews = new TextView[(40)];

            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(ProwlerCalculation.this.INPUT_METHOD_SERVICE);

                try{
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int Counter;
                    TextViewIDsAttacks = new String[]{"PAttack_1", "PAttack_2", "PAttack_3",
                        "PAttack_4", "PAttack_5", "PAttack_6", "PAttack_7",
                        "PAttack_8", "PAttack_9", "PAttack_10", "PAttack_11",
                        "PAttack_12", "PAttack_13", "PAttack_14", "PAttack_15",
                        "PAttack_16", "PAttack_17", "PAttack_18", "PAttack_19",
                        "PAttack_20"};

                TextViewIDsNames = new String[]{"PAttack_1_Name", "PAttack_2_Name", "PAttack_3_Name",
                        "PAttack_4_Name", "PAttack_5_Name", "PAttack_6_Name", "PAttack_7_Name",
                        "PAttack_8_Name", "PAttack_9_Name", "PAttack_10_Name", "PAttack_11_Name",
                        "PAttack_12_Name", "PAttack_13_Name", "PAttack_14_Name", "PAttack_15_Name",
                        "PAttack_16_Name", "PAttack_17_Name", "PAttack_18_Name", "PAttack_19_Name",
                        "PAttack_20_Name"};

                AllTextViewIDs = new String[]{"PAttack_1_Name", "PAttack_2_Name", "PAttack_3_Name",
                        "PAttack_4_Name", "PAttack_5_Name", "PAttack_6_Name", "PAttack_7_Name",
                        "PAttack_8_Name", "PAttack_9_Name", "PAttack_10_Name", "PAttack_11_Name",
                        "PAttack_12_Name", "PAttack_13_Name", "PAttack_14_Name", "PAttack_15_Name",
                        "PAttack_16_Name", "PAttack_17_Name", "PAttack_18_Name", "PAttack_19_Name",
                        "PAttack_20_Name","PAttack_1", "PAttack_2", "PAttack_3",
                        "PAttack_4", "PAttack_5", "PAttack_6", "PAttack_7",
                        "PAttack_8", "PAttack_9", "PAttack_10", "PAttack_11",
                        "PAttack_12", "PAttack_13", "PAttack_14", "PAttack_15",
                        "PAttack_16", "PAttack_17", "PAttack_18", "PAttack_19",
                        "PAttack_20"};

                for (int i = 0; i < 40; i++) {
                    Counter = getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName());
                    textviews[i] = (TextView) findViewById(Counter);
                    textviews[i].setVisibility(View.GONE);
                }

                RelativeLayout Info = (RelativeLayout) findViewById(R.id.AttackInfo);
                Info.setVisibility(View.GONE);

                TextView StaggerBanner = (TextView) findViewById(R.id.StaggerBanner);
                StaggerBanner.setVisibility(View.GONE);

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                int[] HunterArtElementCheck = {0};
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                    if(ChosenArt.equals("Anti-Monster Mine")){
                        ChosenHunterArt = HunterArts[0];
                    }
                    else if(ChosenArt.equals("Anti-Monster Mine+")){
                        ChosenHunterArt = HunterArts[1];
                    }
                    else if(ChosenArt.equals("Claw Dance")){
                        ChosenHunterArt = HunterArts[2];
                    }
                    else if(ChosenArt.equals("Explosive Roll")){
                        ChosenHunterArt = HunterArts[3];
                    }
                    else if(ChosenArt.equals("Felyne Comet")){
                        ChosenHunterArt = HunterArts[4];
                    }
                    else if(ChosenArt.equals("Mini Barrel Bombay")){
                        ChosenHunterArt = HunterArts[5];
                    }
                    else if(ChosenArt.equals("Barrel Bombay")){
                        ChosenHunterArt = HunterArts[6];
                    }
                    else if(ChosenArt.equals("Bounce Bombay")){
                        ChosenHunterArt = HunterArts[7];
                    }
                    else if(ChosenArt.equals("Big Barrel Bombay")){
                        ChosenHunterArt = HunterArts[8];
                    }
                    else if(ChosenArt.equals("Mega Barrel Bombay")){
                        ChosenHunterArt = HunterArts[9];
                    }
                    else if(ChosenArt.equals("Giga Barrel Bombay")){
                        ChosenHunterArt = HunterArts[10];
                    }
                    else if(ChosenArt.equals("Rath-of-Meow")){
                        ChosenHunterArt = HunterArts[11];
                    }
                    else if(ChosenArt.equals("Shock Tripper")){
                        ChosenHunterArt = HunterArts[12];
                    }
                }
                //-End-
                Spinner ProwlerType = (Spinner) findViewById(R.id.ProwlerTypeSelect);
                String ProwlerTypeText = ProwlerType.getSelectedItem().toString();
                if(!ProwlerTypeText.equals("Beast") && BeastModeCheck.isChecked()){
                    Snackbar.make(view, "Beast Mode only available with Beast Prowler", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                Float[] Motion;
                String[] MotionNames;
                if(BeastModeCheck.isChecked()) {
                    Motion = BeastMode;
                    MotionNames = BeastModeNames;
                    Motion[9] = BoomerangType[0];
                    Motion[10] = BoomerangType[1];
                    Motion[11] = BoomerangType[2];
                } else {
                    Motion = NormalMode;
                    MotionNames = NormalModeNames;
                    Motion[7] = BoomerangType[0];
                    Motion[8] = BoomerangType[1];
                    Motion[9] = BoomerangType[2];
                }

                MotionGroup = new Float[][] {Motion, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawAffinity = 0;
                float TrueRaw;

                int PowercharmModifier = 0;
                int PowertalonModifier = 0;
                int AffinityUpLModifier;
                int AffinityUpSModifier;
                float TotalSkillCritModifier = 0;
                int FuryModifier;
                int WeaponUpgradeAffinityModifier;
                int AttackUpLModifier;
                int AttackUpSModifier;
                int DemonHornModifier;
                int LastStandModifier;
                int FanalisModifier;
                int UniversalModifier;
                int WeaponUpgradeRawModifier;
                float WorldsStrongestModifier = 0;
                float BaddestCatEverModifier = 0;
                float RangedAttackModifier = 0;
                int SkillAttackAddModifier;
                float SkillAttackMultiModifier = 0;

                TextView Damage = (TextView) findViewById(R.id.DamageInputP);
                TextView Element = (TextView) findViewById(R.id.ElementInputP);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputP);

                if(TextUtils.isEmpty(Damage.getText().toString())){
                    Damage.setText("0");
                }
                if(TextUtils.isEmpty(Element.getText().toString())){
                    Element.setText("0");
                }
                if(TextUtils.isEmpty(Affinity.getText().toString())){
                    Affinity.setText("0");
                }

                StatsValidation Stats = new StatsValidation(Float.parseFloat(Damage.getText().toString()),
                        ChosenElement,Float.parseFloat(Element.getText().toString()), Float.parseFloat(Affinity.getText().toString()));

                if(Stats.isValid()){
                    RawDamage = Float.parseFloat(Damage.getText().toString());
                    RawElement = Float.parseFloat(Element.getText().toString());
                    RawAffinity = Float.parseFloat(Affinity.getText().toString());
                }
                else{
                    if(!Stats.isValidAtk()){
                        Damage.setError("Enter a valid attack");
                        return;
                    }
                    else if(!Stats.isValidElm()){
                        Element.setError("Enter a valid element");
                        return;
                    }
                    else if(!Stats.isValidAffinity()){
                        Affinity.setError("Enter a valid affinity");
                        return;
                    }
                }

                //Skill Modification - Raw Attack
                if(WeaponUpgradeCheck.isChecked()) WeaponUpgradeAffinityModifier = 30;
                else WeaponUpgradeAffinityModifier = 0;

                if(WeaponUpgradeCheck.isChecked()) WeaponUpgradeRawModifier = 20;
                else WeaponUpgradeRawModifier = 0;

                if (FuryStateCheck.isChecked()) FuryModifier = 30;
                else FuryModifier = 0;

                if(!SkillCheck){
                    SkillAttackAddModifier = WeaponUpgradeRawModifier;
                    SkillAttackMultiModifier = 1;
                }
                else{
                    if(PowercharmCheck.isChecked()) PowercharmModifier = 6;
                    else PowercharmModifier = 0;

                    if(PowertalonCheck.isChecked()) PowertalonModifier = 9;
                    else PowertalonModifier = 0;

                    if(AttackUpSCheck.isChecked()) AttackUpSModifier = 5;
                    else AttackUpSModifier = 0;

                    if(AttackUpLCheck.isChecked()) AttackUpLModifier = 10;
                    else AttackUpLModifier = 0;

                    if(DemonHornCheck.isChecked()) DemonHornModifier = 10;
                    else DemonHornModifier = 0;

                    if(RangedAttackUpCheck.isChecked()) RangedAttackModifier = 1.1f;
                    else RangedAttackModifier = 1;

                    if(LastStandCheck.isChecked()) LastStandModifier = 40;
                    else LastStandModifier = 0;

                    if(FanalisCheck.isChecked()) FanalisModifier = 30;
                    else FanalisModifier = 0;

                    if(UniversalCheck.isChecked()) UniversalModifier = 20;
                    else UniversalModifier = 0;

                    if(WorldsStrongestCheck.isChecked()) WorldsStrongestModifier = 1.5f;
                    else WorldsStrongestModifier = 1;

                    if(BaddestCatEverCheck.isChecked()) BaddestCatEverModifier = 1.1f;
                    else BaddestCatEverModifier = 1;

                    SkillAttackAddModifier = PowercharmModifier + PowertalonModifier + AttackUpLModifier +
                            AttackUpLModifier + DemonHornModifier + LastStandModifier + UniversalModifier +
                            FanalisModifier + WeaponUpgradeRawModifier + NineLivesModifier;

                    if(AffinityUpSCheck.isChecked()) AffinityUpSModifier = 10;
                    else AffinityUpSModifier = 0;

                    if(AffinityUpLCheck.isChecked()) AffinityUpLModifier = 20;
                    else AffinityUpLModifier = 0;

                    TotalSkillCritModifier = AffinityUpSModifier + AffinityUpLModifier;
                }

                RawAffinity = 1 + 0.25f * ((TotalSkillCritModifier + FuryModifier +
                        WeaponUpgradeAffinityModifier + RawAffinity)/100);

                if(RawAffinity > 1.25f) RawAffinity = 1.25f;

                MonsterCalculation M;
                /*'M' is the new variable in this block of code and is used to call the
                 'MonsterCalculation' class. The calculations for hitzones will be done inside that
                 class.*/
                M = new MonsterCalculation(ProwlerCalculation.this,
                        ChosenMonster + "RawHitzones_Cut",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);

                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    if (MotionNames[i].equals("Fury Attack (per hit)") && !FuryStateCheck.isChecked()) {
                        if(!ChosenMonster.equals("None")){
                            StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                            StaggerBanner.setVisibility(View.VISIBLE);
                        }
                        Info.setVisibility(View.VISIBLE);
                        return;
                    }
                    if(SkillCheck){
                        if(Motion[i].equals(Motion[7]) || Motion[i].equals(Motion[8])){
                            SkillAttackMultiModifier = WorldsStrongestModifier * BaddestCatEverModifier *
                                    RangedAttackModifier;
                        }
                        else{
                            SkillAttackMultiModifier = WorldsStrongestModifier * BaddestCatEverModifier;
                        }
                    }

                    if(MotionCheck == 0){
                        TrueRaw = ((RawDamage + SkillAttackAddModifier) * SkillAttackMultiModifier) *
                                RawAffinity * Motion[i];
                    }
                    else{
                        if(i == 0 || ChosenArt.equals("Shock Tripper")){
                            TrueRaw = ((RawDamage + SkillAttackAddModifier) * SkillAttackMultiModifier) *
                                    RawAffinity * ChosenHunterArt[i];
                        }
                        else{
                            TrueRaw = ChosenHunterArt[i];
                        }
                        if(!ChosenArt.equals("Felyne Comet") || !ChosenArt.equals("Claw Dance") ||
                                !ChosenArt.equals("Shock Tripper")){
                            TrueRaw *= ProwlerModifier;
                        }
                    }

                    //Hitzone Modification - Start

                    M.getHitzones(ProwlerCalculation.this, ChosenElement, Skills, false);

                    float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier) / 100;
                    float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

                    float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier) / 100;
                    float HitzoneElm = RawElement * ModifiedElmHitzone;

                    //Hitzone Modification - End

                    float TrueAttack;
                    if(MotionCheck == 0){
                        TrueAttack = HitzoneRaw + HitzoneElm;
                    }
                    else{
                        if(i == 0){
                            TrueAttack = HitzoneRaw + (HitzoneElm * HunterArtElementCheck[i]);
                        }
                        else{
                            TrueAttack = ChosenHunterArt[i] * ProwlerModifier;
                        }
                    }

                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                    textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                    textviews[i].setVisibility(View.VISIBLE);
                    if ((ModifiedRawHitzone * 100) < 25){
                        textviews[i].setTextColor(Color.argb(255, 242, 16, 16));
                        Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{
                        textviews[i].setTextColor(Color.BLACK);
                    }
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                    if(!ChosenArt.equals("-None-")){
                        textviews[i].setText(HunterArtsLevels[i]);
                    }
                    else{
                        textviews[i].setText(MotionNames[i]);
                    }
                    textviews[i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                }
                TextView Banner = (TextView) findViewById(R.id.AttackBanner);
                if(!ChosenArt.equals("-None-")){
                    Banner.setText(ChosenArt);
                }
                else{
                    Banner.setText("Attacks");
                }

                if(!ChosenMonster.equals("None")){
                    StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                    StaggerBanner.setVisibility(View.VISIBLE);
                }
                Info.setVisibility(View.VISIBLE);
            }
        });
    }

    public static float getAtk(float i) {
        SelectedSharpnessAtkModifier = i;
        return SelectedSharpnessAtkModifier;
    }
    public static float getElm(float i) {
        SelectedSharpnessElmModifier = i;
        return SelectedSharpnessElmModifier;
    }
    public static String getChosenElement(String i) {
        ChosenElement = i;
        return ChosenElement;
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
    public static String getMonsterType(String i) {
        MonsterType = i;
        return MonsterType;
    }
    public static String getHunterArt(String i) {
        ChosenArt = i;
        return ChosenArt;
    }
    public static float getProwler(float i) {
        ProwlerModifier = i;
        return ProwlerModifier;
    }
    public static int getNineLives(int i) {
        NineLivesModifier = i;
        return NineLivesModifier;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}