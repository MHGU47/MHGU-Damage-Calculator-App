package com.mhx.marcus.mhgendamagecalc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.view.ViewGroup.MarginLayoutParams;

import java.util.Arrays;
import java.util.List;

public class ChargeBladeCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, MonsterSelect, HitzoneSelect, ElementSelect, HunterArtSelect;
    Spinner SharpnessSelect, PhialSelect, ShieldChargeSelect, NumberofPhialsSelect;
    Button Calculate;
    /*Assigns names to the spinners and the button so that they can be referred to later.*/

    static float SelectedSharpnessElmModifier;
    static float SelectedSharpnessAtkModifier;
    /*Used to apply sharpness modifiers to the inputted element and physical attack values.*/

    static float EffectModifier;
    /*Used to apply to the burst values depending if the shield is charged or not.*/

    static float ChargeModifier;
    /*Used to determine if the shield is charged or not, and then stores the value to be used for
    altering the Axe values if the shield is red charged.*/

    static boolean StrikerCheck;
    /*Used alongside the 'ChargeModifier'. Is used to determine whether the chosen style is 'Striker',
    and if so, the 'ChargeModifier' value is changed from 1.15 to 1.2.*/

    static boolean RedCheck;
    /*Used to check to see if the user has a Red shield selected. Used alongside StrikerCheck to
    determine whether to use add the 1.2x modifier for axe moves.*/

    static boolean YellowCheck;
    /*Used to determine whether the user has a Yellow shield selected. This is used to make sure the
    user can't select a Yellow shield with Adept or Striker style.*/

    static int ForLoopCarry, ForLoopCarryBursts;
    /*Used to carry the current value of 'i' in the 'for' loops over to the next one, so that 'for'
    loop can pick up where the last one left off.*/

    static boolean ImpactPhial;
    /*Used to determine what phial type the user is using.*/

    static int NumberofPhials, EnergyBladePhials;
    /*Stores the amount of phials the user has selected and is used to determine
    how powerful the Ultra Burst phial and Energy Blade damage is.*/

    static String ChosenMonster;
    /*Stores the monster chosen by the user so that it can be used to determine which hitzones to
    use and which monster to display.*/

    static String ChosenHitzone;
    /*Used to store the chosen hitzone chosen by the user so that it can be used to calculate the
    amount of damage done to the selected part.*/

    static String HitzoneGroup;
    /*Used to determine which sub-set of hitzones to use based on which monster was chosen by the user.*/

    static String ChosenElement;
    /*Stores the selected element by the user so that it can be used to calculate the amount of
    elemental damage done to the chosen hitzone.*/

    static String MonsterType;
    /*Used to store the chosen monster so that the correct hitzone set will be selected.*/

    String SelectedMonster;
    /*Used to store the monster name that the user has chosen from the spinner menu.*/

    static String ChosenArt;
    /*Used to store the selected Hunter Art chosen by the user.*/

    //Skill and Hunter Art Selection variables - Start
    Integer Counter;

    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect, GroupSSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck, BludgeonerCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck, AirborneCheck,
            FelyneBombardierCheck, WeaknessExploitCheck;

    Float SkillSharpnessModifier = 1f;
    //-End-

    /*Creates a string/number variables which are arrays.*/
    float[] SharpModAtk = {0.5f, 0.75f, 1, 1.05f, 1.2f, 1.32f, 1.39f};
    float[] SharpModElm = {0.25f, 0.5f, 0.75f, 1, 1.06f, 1.12f, 1.2f};

    String[] TextViewIDsAttacks;
    String[] TextViewIDsNames;
    String[] AllTextViewIDs;

    Float[] MotionSword;
    Float[] MotionAxe;
    Float[][] MotionGroup;
    String[] SelectedStyleSword;
    String[] SelectedStyleAxe;

    Float[] SelectedPhial[];

    Float[] GuildStyleMotionSword =  {0.18f, 0.18f, 0.14f, 0.15f, 0.3f, /*space*/0.5f, 0.16f, 0.2f,
            /*space*/0.42f, 0.22f};
    String[] GuildStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash (2 hits)","Charge Slash (Over/Under charge)","Shield Thrust (2 hits)",
            "Morph to Axe","Jump Attack"};
    Float[] GuildStyleMotionAxe =  {0.42f, 0.42f, 0.33f, 0.4f, 0.18f, /*space*/0.2f, 0.65f/*42 if empty*/,
            /*space*/ 0.75f, 1.05f/*80 if empty*/, /*space*/0.3f, 0.42f};
    String[] GuildStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop","Side Chop",
            "Side Chop Burst","Double Swing Burst (2 hits)","Super Burst","Ultra Burst (2 hits)",
            "Morph to Sword","Jump Attack"};


    Float[] StrikerStyleMotionSword = {0.18f, 0.18f, 0.14f, 0.15f, 0.3f, /*space*/0.5f, 0.16f, 0.42f, 0.22f,};
    String[] StrikerStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash (2 hits)","Charge Slash (Over/Under charge)","Morph to Axe","Jump Attack"};
    Float[] StrikerStyleMotionAxe = {0.42f, 0.42f, 0.33f, 0.4f, /*space*/0.18f, 0.2f, 0.65f/*50 if empty*/,
            /*space*/0.75f/*42 if empty*/, 1.05f/*80 if empty*/, 0.3f, 0.42f};
    String[] StrikerStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop",
            "Side Chop","Side Chop Burst","Double Swing Burst (2 hits)",
            "Super Burst","Ultra Burst (2 hits)","Morph to Sword", "Jump Attack"};


    Float[] AerialStyleMotionSword = {0.18f, 0.18f, 0.14f, 0.15f, 0.3f, /*space*/0.16f, 0.2f, 0.42f, 0.35f};
    String[] AerialStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash","Shield Thrust (2 hits)","Morph to Axe","Aerial Charge Slash"};
    Float[] AerialStyleMotionAxe = {0.42f, 0.42f, 0.33f, 0.4f, /*space*/0.18f, 0.2f, 0.65f/*50 if empty*/,
            /*space*/0.75f/*42 if empty*/, 0.9f/*42 if empty*/, 1.05f/*80 if empty*/, 1f/*60 if empty*/,
            /*Space*/0.3f, 0.42f};
    String[] AerialStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop",
            "Side Chop","Side Chop Burst","Double Swing Burst (2 hits)",
            "Super Burst","Aerial Super Burst","Ultra Burst (2 hits)","Aerial Ultra Burst",
            "Morph to Sword","Aerial Attack"};


    Float[] AdeptStyleMotionSword = {0.18f, 0.18f, 0.14f, 0.15f, 0.3f, /*space*/0.5f, 0.16f, 0.2f,
            /*space*/0.42f, 0.22f};
    String[] AdeptStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash (2 hits)","Charge Slash (Over/Under charge)","Shield Thrust (2 hits)",
            "Morph to Axe","Jump Attack"};
    Float[] AdeptStyleMotionAxe = {0.42f, 0.42f, 0.33f, 0.4f, /*space*/0.18f, 0.2f, 0.65f/*50 if empty*/,
            0.75f,/*42 if empty*/ 1.05f/*80 if empty*/, 0.3f, 0.42f};
    String[] AdeptStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop",
            "Side Chop","Side Chop Burst","Double Swing Burst (2 hits)",
            "Super Burst","Ultra Burst (2 hits)","Morph to Sword","Jump Attack"};


    Float[] ValorStyleMotionSword = {0.18f, 0.18f, 0.14f, 0.15f, 0.3f,/*space*/0.16f, 0.5f, 0.2f, 0.42f,
            /*space*/0.22f};
    String[] ValorStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash", "   -Valor State/Stance (2 hits)","Shield Thrust (2 hits)","Morph to Axe",
            "Jump Attack"};
    Float[] ValorStyleMotionAxe = {0.42f, 0.42f, 0.33f, 0.4f,/*space*/0.18f, 0.2f, 0.65f, 0.65f/*50 if empty*/,
            /*space*/0.7f/*42 if empty*/, 1.15f/*75 if empty*/, 0.85f/*75 if empty*/,
            /*space*/0.3f, 0.42f};
    String[] ValorStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop",
            "Side Chop","Side Chop Burst","Double Swing Burst (2 hits)","EX Double Swing Burst (2 hits) (Valor State)",
            "Super Burst","Ultra Burst (2 hits) (Valor State)","Valor Stance - Ultra Burst (2 hits)",
            "Morph to Sword","Jump Attack"};

    Float[] AlchemyStyleMotionSword =  {0.18f, 0.18f, 0.14f, 0.15f, 0.3f, /*space*/0.5f, 0.42f, 0.22f};
    String[] AlchemyStyleNamesSword = {"Draw Attack","Dash Attack","Idle Slash","Upward Slash","Round Slash",
            "Charge Slash (2 hits)","Morph to Axe","Jump Attack"};
    Float[] AlchemyStyleMotionAxe =  {0.42f, 0.42f, 0.33f, 0.4f, 0.18f, /*space*/0.2f, 0.65f/*50 if empty*/,
            /*space*/0.75f/*42 if empty*/, 0.3f, 0.42f};
    String[] AlchemyStyleNamesAxe = {"Draw Attack","Forward Slam","Upward Swing","Downward Chop","Side Chop",
            "Side Chop Burst","Double Swing Burst (2 hits)","Super Burst","Morph to Sword","Jump Attack"};


    Float[] ImpactBurstModifiers = {0.05f, 0.3f, 0.025f, 0.025f, 0.05f, 0.1f};
    Float[] ElmBurstModifiers = {3f, 13.5f, 1.5f, 1.5f, 3f, 4.5f};

    Float[] ImpactBurstModifiersNoCharge = {0.05f, 0.3f};
    Float[] ElmBurstModifiersNoCharge = {3f, 13.5f};

    String [] BurstModifiersNames = {"(L)Normal Axe Bursts", "Super Axe Bursts (All three)",
            "(S)Rising Slash from Phial Charge", "(S)Shield Thrust Bursts", "(L)Guard Bursts",
            "Ultra Axe Burst (All Phials)"};

    Float [] ImpactBurstMotion[] = {ImpactBurstModifiers, ImpactBurstModifiersNoCharge};
    Float [] ElmBurstMotion[] = {ElmBurstModifiers, ElmBurstModifiersNoCharge};

    Float[] EnergyBlade = {0f, 0f, 0f};
    Float[] RipperShield = {0.8f, 1f, 1.2f};
    Float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Level I", "Level II", "Level III"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_blade_calculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Gives the variable for the spinner 'StyleSelect' the actual value for a spinner.*/
        StyleSelect = (Spinner) findViewById(R.id.StyleSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Styles, android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        StyleSelect.setAdapter(adapter);

        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        StyleSelect.setOnItemSelectedListener(this);

        SharpnessSelect = (Spinner) findViewById(R.id.SharpnessSelect);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.Sharpness,
                android.R.layout.simple_spinner_dropdown_item);

        SharpnessSelect.setAdapter(adapter2);

        SharpnessSelect.setOnItemSelectedListener(this);

        PhialSelect = (Spinner) findViewById(R.id.CBPhialSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.CBPhial,
                android.R.layout.simple_spinner_dropdown_item);

        PhialSelect.setAdapter(adapter3);

        PhialSelect.setOnItemSelectedListener(this);

        NumberofPhialsSelect = (Spinner) findViewById(R.id.NumberofCBPhialsSelect);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.CBPhialNumber,
                android.R.layout.simple_spinner_dropdown_item);

        NumberofPhialsSelect.setAdapter(adapter4);

        NumberofPhialsSelect.setOnItemSelectedListener(this);

       ShieldChargeSelect = (Spinner) findViewById(R.id.ShieldChargeSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.CBShieldCharge,
                android.R.layout.simple_spinner_dropdown_item);

        ShieldChargeSelect.setAdapter(adapter5);

        ShieldChargeSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        ElementSelect = (Spinner) findViewById(R.id.ElementSelect);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(adapter6);

        ElementSelect.setOnItemSelectedListener(this);

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this,R.array.ChargeBlade,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(adapter7);

        HunterArtSelect.setOnItemSelectedListener(this);

        GroupC_1Select = (Spinner) findViewById(R.id.GroupC_1Select);

        ArrayAdapter GroupC_1Adapter = ArrayAdapter.createFromResource(this,R.array.GroupC_1,
                android.R.layout.simple_spinner_dropdown_item);

        GroupC_1Select.setAdapter(GroupC_1Adapter);

        GroupC_1Select.setOnItemSelectedListener(this);

        GroupC_2Select = (Spinner) findViewById(R.id.GroupC_2Select);

        ArrayAdapter GroupC_2Adapter = ArrayAdapter.createFromResource(this,R.array.GroupC_2,
                android.R.layout.simple_spinner_dropdown_item);
        GroupC_2Select.setAdapter(GroupC_2Adapter);

        GroupC_2Select.setOnItemSelectedListener(this);

        GroupDSelect = (Spinner) findViewById(R.id.GroupDSelect);

        ArrayAdapter GroupDAdapter = ArrayAdapter.createFromResource(this,R.array.GroupD,
                android.R.layout.simple_spinner_dropdown_item);

        GroupDSelect.setAdapter(GroupDAdapter);

        GroupDSelect.setOnItemSelectedListener(this);

        GroupFSelect = (Spinner) findViewById(R.id.GroupFSelect);

        ArrayAdapter GroupFAdapter = ArrayAdapter.createFromResource(this,R.array.GroupF,
                android.R.layout.simple_spinner_dropdown_item);

        GroupFSelect.setAdapter(GroupFAdapter);

        GroupFSelect.setOnItemSelectedListener(this);

        GroupGSelect = (Spinner) findViewById(R.id.GroupGSelect);

        ArrayAdapter GroupGAdapter = ArrayAdapter.createFromResource(this,R.array.GroupG,
                android.R.layout.simple_spinner_dropdown_item);

        GroupGSelect.setAdapter(GroupGAdapter);

        GroupGSelect.setOnItemSelectedListener(this);

        GroupHSelect = (Spinner) findViewById(R.id.GroupHSelect);

        ArrayAdapter GroupHAdapter = ArrayAdapter.createFromResource(this,R.array.GroupH,
                android.R.layout.simple_spinner_dropdown_item);

        GroupHSelect.setAdapter(GroupHAdapter);

        GroupHSelect.setOnItemSelectedListener(this);

        GroupISelect = (Spinner) findViewById(R.id.GroupISelect);

        ArrayAdapter GroupIAdapter = ArrayAdapter.createFromResource(this,R.array.GroupI,
                android.R.layout.simple_spinner_dropdown_item);

        GroupISelect.setAdapter(GroupIAdapter);

        GroupISelect.setOnItemSelectedListener(this);

        GroupJSelect = (Spinner) findViewById(R.id.GroupJSelect);

        ArrayAdapter GroupJAdapter = ArrayAdapter.createFromResource(this,R.array.GroupJ,
                android.R.layout.simple_spinner_dropdown_item);

        GroupJSelect.setAdapter(GroupJAdapter);

        GroupJSelect.setOnItemSelectedListener(this);

        GroupKSelect = (Spinner) findViewById(R.id.GroupKSelect);

        ArrayAdapter GroupKAdapter = ArrayAdapter.createFromResource(this,R.array.GroupK,
                android.R.layout.simple_spinner_dropdown_item);

        GroupKSelect.setAdapter(GroupKAdapter);

        GroupKSelect.setOnItemSelectedListener(this);

        GroupOSelect = (Spinner) findViewById(R.id.GroupOSelect);

        ArrayAdapter GroupOAdapter = ArrayAdapter.createFromResource(this,R.array.GroupO,
                android.R.layout.simple_spinner_dropdown_item);

        GroupOSelect.setAdapter(GroupOAdapter);

        GroupOSelect.setOnItemSelectedListener(this);

        GroupPSelect = (Spinner) findViewById(R.id.GroupPSelect);

        ArrayAdapter GroupPAdapter = ArrayAdapter.createFromResource(this,R.array.GroupP,
                android.R.layout.simple_spinner_dropdown_item);

        GroupPSelect.setAdapter(GroupPAdapter);

        GroupPSelect.setOnItemSelectedListener(this);

        GroupSSelect = (Spinner) findViewById(R.id.GroupSSelect);

        ArrayAdapter GroupSAdapter = ArrayAdapter.createFromResource(this,R.array.GroupS,
                android.R.layout.simple_spinner_dropdown_item);

        GroupSSelect.setAdapter(GroupSAdapter);

        GroupSSelect.setOnItemSelectedListener(this);

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = (Spinner) findViewById(R.id.MonsterSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter11 = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(adapter11);

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
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible_CB),
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
        FelyneBoosterCheck = (CheckBox) findViewById(R.id.FelyneBoosterCheckBox);
        CrisisCheck = (CheckBox) findViewById(R.id.CrisisCheckBox);
        FurorCheck = (CheckBox) findViewById(R.id.FurorCheckBox);
        BludgeonerCheck = (CheckBox) findViewById(R.id.BludgeonerCheckBox);
        RepeatOffenderCheck = (CheckBox) findViewById(R.id.RepeatOffenderCheckBox);
        CriticalBoostCheck = (CheckBox) findViewById(R.id.CriticalBoostCheckBox);
        ElementalCritCheck = (CheckBox) findViewById(R.id.ElementalCritCheckBox);
        ElementalAtkUpCheck = (CheckBox) findViewById(R.id.ElementalAtkUpCheckBox);
        FelyneBombardierCheck = (CheckBox) findViewById(R.id.FelyneBombardierCheckBox);
        AirborneCheck = (CheckBox) findViewById(R.id.AirborneCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable called 'StyleSelectText' and sets it as */
        TextView StyleSelectText = (TextView) view;
        final String StyleText = StyleSelectText.getText().toString();
        if (StyleText.equals("Guild")) {
            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = GuildStyleNamesSword;
            MotionSword = GuildStyleMotionSword;
            SelectedStyleAxe = GuildStyleNamesAxe;
            MotionAxe = GuildStyleMotionAxe;
            getStrikerCheck(false);
        } else if (StyleText.equals("Striker")) {
            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = StrikerStyleNamesSword;
            MotionSword = StrikerStyleMotionSword;
            SelectedStyleAxe = StrikerStyleNamesAxe;
            MotionAxe = StrikerStyleMotionAxe;
            getStrikerCheck(true);
        } else if (StyleText.equals("Aerial")) {
            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = AerialStyleNamesSword;
            MotionSword = AerialStyleMotionSword;
            SelectedStyleAxe = AerialStyleNamesAxe;
            MotionAxe = AerialStyleMotionAxe;
            getStrikerCheck(false);
        } else if (StyleText.equals("Adept")) {
            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = AdeptStyleNamesSword;
            MotionSword = AdeptStyleMotionSword;
            SelectedStyleAxe = AdeptStyleNamesAxe;
            MotionAxe = AdeptStyleMotionAxe;
            getStrikerCheck(false);
        }else if(StyleText.equals("Valor")){
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = ValorStyleNamesSword;
            MotionSword = ValorStyleMotionSword;
            SelectedStyleAxe = ValorStyleNamesAxe;
            MotionAxe = ValorStyleMotionAxe;
            getStrikerCheck(false);
        }else if(StyleText.equals("Alchemy")){
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyleSword = AlchemyStyleNamesSword;
            MotionSword = AlchemyStyleMotionSword;
            SelectedStyleAxe = AlchemyStyleNamesAxe;
            MotionAxe = AlchemyStyleMotionAxe;
            getStrikerCheck(false);
        }


        TextView Sharpness = (TextView) view;
        String SharpnessModifier = Sharpness.getText().toString();
        if(SharpnessModifier.equals("Red")){
            getAtk(SharpModAtk[0]);
            getElm(SharpModElm[0]);
            Skills.getBludgeonerModifier(30f);
        }
        else if(SharpnessModifier.equals("Orange")) {
            getAtk(SharpModAtk[1]);
            getElm(SharpModElm[1]);
            Skills.getBludgeonerModifier(30f);
        }
        else if(SharpnessModifier.equals("Yellow")) {
            getAtk(SharpModAtk[2]);
            getElm(SharpModElm[2]);
            Skills.getBludgeonerModifier(25f);
        }
        else if(SharpnessModifier.equals("Green")) {
            getAtk(SharpModAtk[3]);
            getElm(SharpModElm[3]);
            Skills.getBludgeonerModifier(15f);
        }
        else if(SharpnessModifier.equals("Blue")) {
            getAtk(SharpModAtk[4]);
            getElm(SharpModElm[4]);
            Skills.getBludgeonerModifier(0f);
        }
        else if(SharpnessModifier.equals("White")) {
            getAtk(SharpModAtk[5]);
            getElm(SharpModElm[5]);
            Skills.getBludgeonerModifier(0f);
        }
        else if(SharpnessModifier.equals("Purple")) {
            getAtk(SharpModAtk[6]);
            getElm(SharpModElm[6]);
            Skills.getBludgeonerModifier(0f);
        }

        TextView ShieldCharge = (TextView) view;
        String ShieldChargeModifier = ShieldCharge.getText().toString();
        if (ShieldChargeModifier.equals("No Charge")) {
            Skills.setCBPhialModifier(false);
            getChargeModifier(1f);
            getRedCheck(false);
            getYellowCheck(false);
        } else if (ShieldChargeModifier.equals("Yellow Charge")) {
            Skills.setCBPhialModifier(true);
            getChargeModifier(1f);
            getRedCheck(false);
            getYellowCheck(true);
        } else if (ShieldChargeModifier.equals("Red Charge")) {
            Skills.setCBPhialModifier(true);
            getRedCheck(true);
            getYellowCheck(false);
        } else if (ShieldChargeModifier.equals("Blue Charge (Valor)")) {
            Skills.setCBPhialModifier(true);
            getRedCheck(true);
            getYellowCheck(false);
        }

        TextView Burst = (TextView) view;
        final String BurstModifier = Burst.getText().toString();
        if (BurstModifier.equals("Impact")) {
            SelectedPhial = ImpactBurstMotion;
            isImpact(true);
        } else if (BurstModifier.equals("Element")) {
            SelectedPhial = ElmBurstMotion;
            isImpact(false);
        }

        TextView Phial = (TextView) view;
        final String PhialNumber = Phial.getText().toString();
        if (PhialNumber.equals("0")) {
            getNumberofPhials(0);
            getEnergyBladePhials(0);
        } else if (PhialNumber.equals("1")) {
            getNumberofPhials(1);
            getEnergyBladePhials(3);
        } else if (PhialNumber.equals("2")) {
            getNumberofPhials(2);
            getEnergyBladePhials(3);
        } else if (PhialNumber.equals("3")) {
            getNumberofPhials(2);
            getEnergyBladePhials(3);
        } else if (PhialNumber.equals("4")) {
            getNumberofPhials(3);
            getEnergyBladePhials(6);
        } else if (PhialNumber.equals("5")) {
            getNumberofPhials(3);
            getEnergyBladePhials(6);
        } else if (PhialNumber.equals("6")) {
            getNumberofPhials(4);
            getEnergyBladePhials(6);
        } else if (PhialNumber.equals("7")) {
            getNumberofPhials(4);
            getEnergyBladePhials(9);
        } else if (PhialNumber.equals("8")) {
            getNumberofPhials(5);
            getEnergyBladePhials(9);
        } else if (PhialNumber.equals("9")) {
            getNumberofPhials(5);
            getEnergyBladePhials(9);
        } else if (PhialNumber.equals("10")) {
            getNumberofPhials(6);
            getEnergyBladePhials(10);
        }

        TextView SelectedElement = (TextView) view;
        String ElementText = SelectedElement.getText().toString();
        if (ElementText.equals("Fire")) {
            getChosenElement("Fire");
        } else if (ElementText.equals("Water")) {
            getChosenElement("Water");
        } else if (ElementText.equals("Ice")) {
            getChosenElement("Ice");
        } else if (ElementText.equals("Thunder")) {
            getChosenElement("Thunder");
        } else if (ElementText.equals("Dragon")) {
            getChosenElement("Dragon");
        } else if (ElementText.equals("NONE")) {
            getChosenElement("NONE");
        }

        TextView SelectedArt = (TextView) view;
        String ArtText = SelectedArt.getText().toString();
        if(ArtText.equals("Energy Blade")){
            getHunterArt("Energy Blade");
        }
        else if(ArtText.equals("Ripper Shield")) {
            getHunterArt("Ripper Shield");
        }
        else if(ArtText.equals("-None-")) {
            getHunterArt("-None-");
        }

        TextView GroupC_1Skills = (TextView) view;
        String GroupC_1SkillsText = GroupC_1Skills.getText().toString();
        if(GroupC_1SkillsText.equals("Demondrug (+5)")){
            Skills.setGroupC_1(5f);
        }
        else if(GroupC_1SkillsText.equals("Mega Demondrug (+7)")) {
            Skills.setGroupC_1(7f);
        }
        else if(GroupC_1SkillsText.equals("-Demon Drugs-")) {
            Skills.setGroupC_1(0f);
        }

        TextView GroupC_2Skills = (TextView) view;
        String GroupC_2SkillsText = GroupC_2Skills.getText().toString();
        if(GroupC_2SkillsText.equals("Attack Up S - Meal (+3)")){
            Skills.setGroupC_2(3f);
        }
        else if(GroupC_2SkillsText.equals("Attack Up M - Meal (+5)")) {
            Skills.setGroupC_2(5f);
        }
        else if(GroupC_2SkillsText.equals("Attack Up L - Meal (+7)")) {
            Skills.setGroupC_2(7f);
        }
        else if(GroupC_2SkillsText.equals("-Atk Up (Meal)-")) {
            Skills.setGroupC_2(0f);
        }

        TextView GroupDSkills = (TextView) view;
        String GroupDSkillsText = GroupDSkills.getText().toString();
        if(GroupDSkillsText.equals("Might Seed (+10)")){
            Skills.setGroupD(10f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Might Pill (+25)")) {
            Skills.setGroupD(25f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Exciteshroom - Mycology (+10)")) {
            Skills.setGroupD(10f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Demon Horn (+10)")) {
            Skills.setGroupD(10f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Demon S (+10)")) {
            Skills.setGroupD(10f);

            SkillSharpnessModifier = 1.1f;
            Skills.setGroupDCrit(0f);
            Snackbar.make(view, "10% Sharpness Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupDSkillsText.equals("Demon Affinity S (+15)")) {
            Skills.setGroupD(15f);

            SkillSharpnessModifier = 1.1f;
            Skills.setGroupDCrit(10f);
            Snackbar.make(view, "10% Sharpness Increase\n10% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupDSkillsText.equals("Cool Cat (+15)")) {
            Skills.setGroupD(15f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("-Group D-")) {
            Skills.setGroupD(0f);

            SkillSharpnessModifier = 1f;
            Skills.setGroupDCrit(0f);
        }

        TextView GroupFSkills = (TextView) view;
        String GroupFSkillsText = GroupFSkills.getText().toString();
        if(GroupFSkillsText.equals("Attack Up S (+10)")){
            Skills.setGroupF(10f);
        }
        else if(GroupFSkillsText.equals("Attack Up M (+15)")) {
            Skills.setGroupF(15f);
        }
        else if(GroupFSkillsText.equals("Attack Up L (+20)")) {
            Skills.setGroupF(20f);
        }
        else if(GroupFSkillsText.equals("-Atk Up-")) {
            Skills.setGroupF(0f);
        }

        TextView GroupGSkills = (TextView) view;
        String GroupGSkillsText = GroupGSkills.getText().toString();
        if(GroupGSkillsText.equals("Adrenaline +2 (+30%)")){
            Skills.setGroupG(1.3f);
        }
        else if(GroupGSkillsText.equals("Felyne Heroics (+35%)")) {
            Skills.setGroupG(1.35f);
        }
        else if(GroupGSkillsText.equals("-Group G-")) {
            Skills.setGroupG(1f);
        }

        TextView GroupHSkills = (TextView) view;
        String GroupHSkillsText = GroupHSkills.getText().toString();
        if(GroupHSkillsText.equals("Attack Up S - Hunting Horn (+10%)")){
            Skills.setGroupH(1.1f);
        }
        else if(GroupHSkillsText.equals("Attack Up S - Hunting Horn (Recital) (+15%)")) {
            Skills.setGroupH(1.15f);
        }
        else if(GroupHSkillsText.equals("Attack Up L - Hunting Horn (+15%)")) {
            Skills.setGroupH(1.15f);
        }
        else if(GroupHSkillsText.equals("Attack Up L - Hunting Horn (Recital) (+20%)")) {
            Skills.setGroupH(1.2f);
        }
        else if(GroupHSkillsText.equals("-H.Horn Buffs-")) {
            Skills.setGroupH(1f);
        }

        TextView GroupISkills = (TextView) view;
        String GroupISkillsText = GroupISkills.getText().toString();
        if(GroupISkillsText.equals("Fortify - First Cart (+10%)")){
            Skills.setGroupI(1.1f);
        }
        else if(GroupISkillsText.equals("Fortify - Second Cart (+20%)")) {
            Skills.setGroupI(1.2f);
        }
        else if(GroupISkillsText.equals("-Fortify-")) {
            Skills.setGroupI(1f);
        }

        TextView GroupJSkills = (TextView) view;
        String GroupJSkillsText = GroupJSkills.getText().toString();
        if(GroupJSkillsText.equals("Challenger +1 (+10%)")){
            Skills.setGroupJ_1(1.1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(10f);
            Snackbar.make(view, "10% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Challenger +2 (+20%)")) {
            Skills.setGroupJ_1(1.2f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(15f);
            Snackbar.make(view, "15% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Unscathed (+20)")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(20f);

            Skills.setGroupJCrit(0f);
        }
        else if(GroupJSkillsText.equals("Latent Power +1")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(30f);
            Snackbar.make(view, "30% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Latent Power +2")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(50f);
            Snackbar.make(view, "50% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("-Group J-")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(0f);
        }

        TextView GroupKSkills = (TextView) view;
        String GroupKSkillsText = GroupKSkills.getText().toString();
        if(GroupKSkillsText.equals("Cold Blooded - Cold Area (+15)")){
            Skills.setGroupK(15f);
        }
        else if(GroupKSkillsText.equals("Cold Blooded - Cold Drink (+5)")) {
            Skills.setGroupK(5f);
        }
        else if(GroupKSkillsText.equals("Cold Blooded - Cold Area + Cold Drink (+20)")) {
            Skills.setGroupK(20f);
        }
        else if(GroupKSkillsText.equals("Hot Blooded - Hot Area (+15)")) {
            Skills.setGroupK(15f);
        }
        else if(GroupKSkillsText.equals("-Cold/Hot Blooded-")) {
            Skills.setGroupK(0f);
        }

        TextView GroupOSkills = (TextView) view;
        String GroupOSkillsText = GroupOSkills.getText().toString();
        if(GroupOSkillsText.equals("Critical Eye +1")){
            Skills.setGroupO(10f);
        }
        else if(GroupOSkillsText.equals("Critical Eye +2")) {
            Skills.setGroupO(20f);
        }
        else if(GroupOSkillsText.equals("Critical Eye +3")) {
            Skills.setGroupO(30f);
        }
        else if(GroupOSkillsText.equals("-Critical Eye-")) {
            Skills.setGroupO(0f);
        }

        TextView GroupPSkills = (TextView) view;
        String GroupPSkillsText = GroupPSkills.getText().toString();
        if(GroupPSkillsText.equals("[Element] Atk Up +1")){
            Skills.setGroupP(4,1.05f);
        }
        else if(GroupPSkillsText.equals("[Element] Atk Up +2")) {
            Skills.setGroupP(6,1.1f);
        }
        else if(GroupPSkillsText.equals("-Element Atk Up-")) {
            Skills.setGroupP(0,1);
        }

        TextView GroupSSkills = (TextView) view;
        String GroupSSkillsText = GroupSSkills.getText().toString();
        if(GroupSSkillsText.equals("Artillery Novice")){
            Skills.setArtilleryModifier(1.3f);
        }
        else if(GroupSSkillsText.equals("Artillery Expert")) {
            Skills.setArtilleryModifier(1.35f);
        }
        else if(GroupSSkillsText.equals("-Artillery-")) {
            Skills.setArtilleryModifier(1f);
        }
    }

    public void Calculate(){

        /*Binds the variable 'Calculate' to a button, specifically the 'CalculateButton' using its ID.*/
        Calculate = (Button) findViewById(R.id.CalculateButton);

        /*Gives the button an onClickListener() method (function) and makes it akin to an inline function.*/
        Calculate.setOnClickListener(new View.OnClickListener() {

            /*Creates a TextView array variable called 'textviews' and sets it so the values
            affect all the textviews needed, except the burst textviews.*/
            TextView[] textviews = new TextView[(62)];

            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(ChargeBladeCalculation.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                int Counter;
                TextViewIDsAttacks = new String[]{"CBAttack_1", "CBAttack_2", "CBAttack_3",
                        "CBAttack_4", "CBAttack_5", "CBAttack_6", "CBAttack_7", "CBAttack_8",
                        "CBAttack_9", "CBAttack_10", "CBAttack_11", "CBAttack_12", "CBAttack_13",
                        "CBAttack_14", "CBAttack_15", "CBAttack_16", "CBAttack_17", "CBAttack_18",
                        "CBAttack_19", "CBAttack_20", "CBAttack_21", "CBAttack_22", "CBAttack_23",
                        "BurstAttack_1", "BurstAttack_2", "BurstAttack_3", "BurstAttack_4",
                        "BurstAttack_5", "BurstAttack_6"};

                TextViewIDsNames = new String[]{"CBAttack_1_Name", "CBAttack_2_Name", "CBAttack_3_Name",
                        "CBAttack_4_Name", "CBAttack_5_Name", "CBAttack_6_Name", "CBAttack_7_Name",
                        "CBAttack_8_Name", "CBAttack_9_Name", "CBAttack_10_Name", "CBAttack_11_Name",
                        "CBAttack_12_Name", "CBAttack_13_Name", "CBAttack_14_Name", "CBAttack_15_Name",
                        "CBAttack_16_Name", "CBAttack_17_Name", "CBAttack_18_Name", "CBAttack_19_Name",
                        "CBAttack_20_Name", "CBAttack_21_Name", "CBAttack_22_Name", "CBAttack_23_Name",
                        "BurstAttack_1_Name", "BurstAttack_2_Name", "BurstAttack_3_Name",
                        "BurstAttack_4_Name", "BurstAttack_5_Name", "BurstAttack_6_Name",
                        "BurstAttack_Extend"};

                AllTextViewIDs = new String[]{"CBAttack_1_Name", "CBAttack_2_Name", "CBAttack_3_Name",
                        "CBAttack_4_Name", "CBAttack_5_Name", "CBAttack_6_Name", "CBAttack_7_Name",
                        "CBAttack_8_Name", "CBAttack_9_Name", "CBAttack_10_Name", "CBAttack_11_Name",
                        "CBAttack_12_Name", "CBAttack_13_Name", "CBAttack_14_Name", "CBAttack_15_Name",
                        "CBAttack_16_Name", "CBAttack_17_Name", "CBAttack_18_Name", "CBAttack_19_Name",
                        "CBAttack_20_Name", "CBAttack_21_Name", "CBAttack_22_Name", "CBAttack_23_Name",
                         "CBAttack_1", "CBAttack_2", "CBAttack_3", "CBAttack_4", "CBAttack_5",
                        "CBAttack_6", "CBAttack_7", "CBAttack_8", "CBAttack_9", "CBAttack_10",
                        "CBAttack_11", "CBAttack_12", "CBAttack_13", "CBAttack_14", "CBAttack_15",
                        "CBAttack_16", "CBAttack_17", "CBAttack_18", "CBAttack_19", "CBAttack_20",
                        "CBAttack_21", "CBAttack_22", "CBAttack_23", "BurstAttack_1",
                        "BurstAttack_2", "BurstAttack_3", "BurstAttack_4", "BurstAttack_5",
                        "BurstAttack_6", "BurstAttack_1_Name", "BurstAttack_2_Name",
                        "BurstAttack_3_Name", "BurstAttack_4_Name", "BurstAttack_5_Name",
                        "BurstAttack_6_Name", "BurstAttack_Extend"};

                String[] MiscBannersIDs = {"AttackBanner", "SwordMovesBanner", "AxeMovesBanner",
                        "PhialDisclaimer", "BurstBanner", "CBKeyBanner", "StaggerBanner"};

                TextView Banner = (TextView) findViewById(R.id.AttackBanner);
                TextView SwordBanner = (TextView) findViewById(R.id.SwordMovesBanner);
                SwordBanner.setVisibility(View.VISIBLE);

                RelativeLayout Info = (RelativeLayout) findViewById(R.id.AttackInfo);
                Info.setVisibility(View.GONE);

                RelativeLayout BurstInfo = (RelativeLayout) findViewById(R.id.BurstAttackInfo);
                BurstInfo.setVisibility(View.GONE);

                for (int i = 0; i < AllTextViewIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
                    textviews[i].setVisibility(View.GONE);
                }

                TextView StaggerBanner = (TextView) findViewById(R.id.StaggerBanner);
                StaggerBanner.setVisibility(View.GONE);

                TextView Extra = (TextView) findViewById(R.id.BurstAttack_2_Extra);
                Extra.setVisibility(View.GONE);

                Spinner StyleSelectText = (Spinner) findViewById(R.id.StyleSelect);
                String StyleText = StyleSelectText.getSelectedItem().toString();

                Spinner ChargeSelectText = (Spinner) findViewById(R.id.ShieldChargeSelect);
                String ChargeText = ChargeSelectText.getSelectedItem().toString();

                if (StrikerCheck && YellowCheck) {
                    Snackbar.make(view, "Striker Style doesn't have Yellow Shield", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else if (StyleText.equals("Adept") && YellowCheck) {
                    Snackbar.make(view, "Adept Style doesn't have Yellow Shield", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else if (StyleText.equals("Valor") && (ChargeText.equals("Yellow Charge") || ChargeText.equals("Red Charge"))) {
                    Snackbar.make(view, "Valor Style only has Blue Shield", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else if (!StyleText.equals("Valor") && ChargeText.equals("Blue Charge (Valor)")) {
                    Snackbar.make(view, "Blue Shield is exclusive to Valor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                int[] HunterArtElementCheck = {1};
                if (ChosenArt.equals("Energy Blade")){
                    MotionCheck = 2;
                    ChosenHunterArt = EnergyBlade;
                    HunterArtElementCheck = new int[] {1, 1, 1};
                    if(EnergyBladePhials == 0){
                        EnergyBlade[0] = 0.1f;
                        EnergyBlade[1] = 0.2f;
                        EnergyBlade[2] = 0.3f;
                    }
                    else if(EnergyBladePhials == 3){
                        EnergyBlade[0] = 0.8f;
                        EnergyBlade[1] = 1f;
                        EnergyBlade[2] = 1.3f;
                    }
                    else if(EnergyBladePhials == 6){
                        EnergyBlade[0] = 1.3f;
                        EnergyBlade[1] = 1.5f;
                        EnergyBlade[2] = 1.8f;
                    }
                    else if(EnergyBladePhials == 9){
                        EnergyBlade[0] = 1.6f;
                        EnergyBlade[1] = 1.8f;
                        EnergyBlade[2] = 2f;
                    }
                    else if(EnergyBladePhials == 10){
                        EnergyBlade[0] = 2f;
                        EnergyBlade[1] = 2.2f;
                        EnergyBlade[2] = 2.5f;
                    }
                }
                else if (ChosenArt.equals("Ripper Shield")){
                    MotionCheck = 2;
                    ChosenHunterArt = RipperShield;
                    HunterArtElementCheck = new int[] {4, 5, 6};
                }
                //-End-

                MotionGroup = new Float[][] {MotionSword, MotionAxe, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawAffinity = 0;

                float TrueRaw;

                TextView Damage = (TextView) findViewById(R.id.DamageInputCB);
                TextView Element = (TextView) findViewById(R.id.ElementInputCB);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputCB);

                if(TextUtils.isEmpty(Damage.getText().toString())){
                    RawDamage = 0;
                    Damage.setText("0");
                }
                if(TextUtils.isEmpty(Element.getText().toString())){
                    RawElement = 0;
                    Element.setText("0");
                }
                if(TextUtils.isEmpty(Affinity.getText().toString())){
                    RawAffinity = 0;
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

                Skills.setRepeatOffenderModifier(RepeatOffenderCheck.isChecked());
                Skills.setCritBoostModifier(CriticalBoostCheck.isChecked());

                Skills.setPowercharmModifier(PowercharmCheck.isChecked());
                Skills.setPowertalonModifier(PowertalonCheck.isChecked());
                Skills.setFelyneBoosterModifier(FelyneBoosterCheck.isChecked());
                Skills.setCrisisModifier(CrisisCheck.isChecked());
                Skills.setFurorModifier(FurorCheck.isChecked());
                Skills.setBludgeonerModifier(BludgeonerCheck.isChecked());

                Skills.setElementAtkUp(ElementalAtkUpCheck.isChecked());
                Skills.setElementCrit(ElementalCritCheck.isChecked(), RawAffinity);

                if (Skills.CheckElmSkill(RawElement, SkillCheck)){
                    Snackbar.make(view, "Please check your inputted element/skills", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                MonsterCalculation M;
                /*'M' is the new variable in this block of code and is used to call the
                'MonsterCalculation' class. The calculations for hitzones will be done inside that
                class.*/
                M = new MonsterCalculation(ChargeBladeCalculation.this,
                        ChosenMonster + "RawHitzones_Cut",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);

                M.getHitzones(ChargeBladeCalculation.this, ChosenElement, Skills, WeaknessExploitCheck.isChecked());

                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    if(MotionCheck == 0){
                        if(AirborneCheck.isChecked() && SkillCheck && (SelectedStyleSword[i].contains("Aerial") ||
                            SelectedStyleSword[i].contains("Jump"))){
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        }
                        else{
                            Skills.setAirborneModifier(false);
                        }
                        Skills.setShieldModifier(RedCheck,StrikerCheck,false,SelectedStyleSword[i]);
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * MotionSword[i];
                    }
                    else{
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ChosenHunterArt[i];
                    }

                    int HitMultiplier = 1;
                    if (SelectedStyleSword[i].contains("2 hits")) {
                        HitMultiplier = 2;
                    }

                    //Hitzone Modification - Start

                    float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier *
                            SkillSharpnessModifier) / 100;
                    float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

                    float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier *
                            SkillSharpnessModifier) / 100;
                    float HitzoneElm = (Skills.getTrueElm(RawElement, SkillCheck) * ModifiedElmHitzone) * HitMultiplier;

                    //Hitzone Modification - End

                    float TrueAttack;
                    if (!ChosenArt.equals("-None-"))
                        TrueAttack = HitzoneRaw + (HitzoneElm * HunterArtElementCheck[i]);
                    else TrueAttack = HitzoneRaw + HitzoneElm;

                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                    /*Sets the value of 'textviews' to the position of the item in the 'TextViewIDs'
                    array e.g. If the current selected item in the array was 'CBAttack_1',then
                    'Counter' would be 0 etc.*/
                    textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                    textviews[i].setVisibility(View.VISIBLE);
                    if ((ModifiedRawHitzone * 100) < 25){
                        textviews[i].setTextColor(Color.argb(255, 242, 16, 16));
                        Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else textviews[i].setTextColor(Color.BLACK);

                    /*Sets the current textview to the id value of the desired textview and then
                    sets that textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                    if(MotionCheck == 0){
                        textviews[i].setText(SelectedStyleSword[i]);
                        Banner.setText("Attacks");
                    }
                    else{
                        textviews[i].setText(HunterArtsLevels[i]);
                        Banner.setText(ChosenArt);
                    }
                    textviews[i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    getForLoopCarry(i);
                    /*Method (function) used to carry over the value of 'i' so it can be used
                    elsewhere.*/
                }

                if(!ChosenMonster.equals("None")){
                    StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                    StaggerBanner.setVisibility(View.VISIBLE);
                }
                /*Block of code to display the stagger values for the chosen monster.*/

                Info.setVisibility(View.VISIBLE);
                if(MotionCheck != 0){
                    SwordBanner.setVisibility(View.GONE);
                    return;
                }

                /*For loop added to control when the disclaimer, attack and first move banner are
                displayed. This was placed here so it can't be run if the user doesn't input
                correct values.*/
                for (int i = 0; i < MiscBannersIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(MiscBannersIDs[i], "id", getPackageName()));

                    if (MiscBannersIDs[i].equals("CBKeyBanner") && ImpactPhial) {
                        if (Skills.getCBPhialModifier() == 1) {
                            String Key = "(L)KO/Exhaust: " + String.format("%s", Math.round(30f * Skills.getCBPhialModifier())) + "/" +
                                    String.format("%s", Math.round(5f * Skills.getCBPhialModifier())) +
                                    "\nThese values are fixed and are based on raw damage ONLY";
                            textviews[i].setText(Key);
                        } else {
                            String Key = "(S)KO/Exhaust: " + String.format("%s", Math.round(15f * Skills.getCBPhialModifier())) + "/" +
                                    String.format("%s", Math.round(2.5f * Skills.getCBPhialModifier())) + "\n(L)KO/Exhaust: " +
                                    String.format("%s", Math.round(30f * Skills.getCBPhialModifier())) + "/" +
                                    String.format("%s", Math.round(5f * Skills.getCBPhialModifier())) +
                                    "\nThese values are fixed and are based on raw damage ONLY";
                            textviews[i].setText(Key);
                        }

                        TextView BurstBanner = (TextView) findViewById(R.id.BurstAttack_1);
                        MarginLayoutParams Margin = (MarginLayoutParams) BurstBanner.getLayoutParams();
                        Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.convert1), 0, 0);
                        BurstBanner.setLayoutParams(Margin);
                        textviews[i].setVisibility(View.VISIBLE);
                    }
                    else if (MiscBannersIDs[i].equals("CBKeyBanner") && !ImpactPhial) {
                        textviews[i].setText("Elemental bursts aren't affected by sharpness");
                        TextView BurstBanner = (TextView) findViewById(R.id.BurstAttack_1);
                        MarginLayoutParams Margin = (MarginLayoutParams) BurstBanner.getLayoutParams();
                        Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.convert2), 0, 0);
                        BurstBanner.setLayoutParams(Margin);
                        textviews[i].setVisibility(View.VISIBLE);
                    }
                    else if (MiscBannersIDs[i].equals("CBKeyBanner")) {
                        TextView BurstBanner = (TextView) findViewById(R.id.BurstAttack_1);
                        MarginLayoutParams Margin = (MarginLayoutParams) BurstBanner.getLayoutParams();
                        Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.revert1), 0, 0);
                        BurstBanner.setLayoutParams(Margin);
                        textviews[i].setVisibility(View.GONE);
                    }
                    else {
                        textviews[i].setVisibility(View.VISIBLE);
                    }
                }

                /*Block of code here added to control when the 'Axe Move' banner is displayed.*/
                textviews[ForLoopCarry] = (TextView) findViewById(R.id.AxeMovesBanner);
                textviews[ForLoopCarry].setVisibility(View.VISIBLE);
                int ig = ForLoopCarry + 1;
                getForLoopCarry(ig);

                int MoveCheck = 0;
                if (MotionSword.length == 8) MoveCheck = 2;
                else if (MotionSword.length == 9) MoveCheck = 1;
                /*MoveCheck used to make sure the textviews for the moves are displayed correctly
                to compensate for the extra banner for the axe moves. MoveCheck is added to ii
                in order to make sure the textviews are all used accordingly. This is done so that
                the banner for the axe attack is always placed underneath the last textview that
                could possibly be used for the sword attacks, as that's where it's positioned
                originally.*/

                for (int i = 0; i < MotionGroup[MotionCheck + 1].length; i++) {
                    int ii = ForLoopCarry + i;
                    /*Integer 'ii' used to store the value of 'ForLoopCarry' and adds one to it to
                    carry on from where the last for loop left off in order to fill the TextViews in
                    the correct order.*/

                    if ((SelectedStyleAxe[i].equals("Ultra Burst (2 hits)")) && Skills.getCBPhialModifier() == 1f)
                        i = i + 1;
                    
                    if ((SelectedStyleAxe[i].equals("Ultra Burst (2 hits) (Valor State)") || SelectedStyleAxe[i].contains("EX")) &&
                            !ChargeText.equals("Blue Charge (Valor)")){
                        i += 1;
                    }
                    /*If statement used to make sure the motion value for the Ultra Burst isn't
                    displayed if the shield isn't charged.*/

                    if (SelectedStyleAxe[i].equals("Ultra Burst (2 hits)") && NumberofPhials == 0)
                        MotionAxe[i] = 0.8f;
                    else if (SelectedStyleAxe[i].equals("Ultra Burst (2 hits)") && NumberofPhials > 0)
                        MotionAxe[i] = 1.05f;

                    if(SelectedStyleAxe[i].equals("Ultra Burst (2 hits) (Valor State)") && NumberofPhials == 0)
                        MotionAxe[i] = 0.75f;
                    else if(SelectedStyleAxe[i].equals("Ultra Burst (2 hits) (Valor State)") && NumberofPhials > 0)
                        MotionAxe[i] = 1.15f;

                    if(SelectedStyleAxe[i].equals("Valor Stance - Ultra Burst (2 hits)") && NumberofPhials == 0)
                        MotionAxe[i] = 0.75f;
                    else if(SelectedStyleAxe[i].equals("Valor Stance - Ultra Burst (2 hits)") && NumberofPhials > 0)
                        MotionAxe[i] = 0.85f;

                    if((SelectedStyleAxe[i].equals("Aerial Super Burst") ||
                            SelectedStyleAxe[i].equals("Super Burst"))&& NumberofPhials == 0)
                        MotionAxe[i] = 0.42f;
                    else if((SelectedStyleAxe[i].equals("Aerial Super Burst") ||
                            SelectedStyleAxe[i].equals("Super Burst"))&& NumberofPhials > 0)
                        MotionAxe[i] = 0.75f;

                    if(SelectedStyleAxe[i].equals("Aerial Ultra Burst") && NumberofPhials == 0)
                        MotionAxe[i] = 0.6f;
                    else if(SelectedStyleAxe[i].equals("Aerial Ultra Burst") && NumberofPhials > 0)
                        MotionAxe[i] = 1f;

                    if(SelectedStyleAxe[i].contains("Double Swing Burst (2 hits)") && NumberofPhials == 0)
                        MotionAxe[i] = 0.5f;
                    else if(SelectedStyleAxe[i].contains("Double Swing Burst (2 hits)") && NumberofPhials > 0)
                        MotionAxe[i] = 0.65f;

                    /*If statements used to make sure the motion values are altered accordingly if no
                    phials are loaded.*/

                    int HitMultiplier = 1;
                    if (SelectedStyleAxe[i].contains("2 hits")) HitMultiplier = 2;

                    if(AirborneCheck.isChecked() && SkillCheck && (SelectedStyleAxe[i].contains("Aerial") ||
                            SelectedStyleAxe[i].contains("Jump")))
                        Skills.setAirborneModifier(AirborneCheck.isChecked());
                    else Skills.setAirborneModifier(false);

                    Skills.setShieldModifier(RedCheck, StrikerCheck,true, SelectedStyleAxe[i]);
                    TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * MotionAxe[i];

                    //Hitzone Modification - Start

                    float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier) / 100;
                    float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

                    float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier) / 100;
                    float HitzoneElm = (Skills.getTrueElm(RawElement, SkillCheck) * ModifiedElmHitzone) * HitMultiplier;

                    //Hitzone Modification - End

                    float TrueAttack = HitzoneRaw + HitzoneElm;


                    textviews[(ii + MoveCheck)] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[(ii + MoveCheck)], "id", getPackageName()));
                    textviews[(ii + MoveCheck)].setText(String.format("%s", Math.round(TrueAttack)));
                    textviews[(ii + MoveCheck)].setVisibility(View.VISIBLE);
                    if ((ModifiedRawHitzone * 100) < 25){
                        textviews[(ii + MoveCheck)].setTextColor(Color.argb(255, 242, 16, 16));
                        Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{
                        textviews[(ii + MoveCheck)].setTextColor(Color.BLACK);
                    }

                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'ii's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    textviews[(ii + MoveCheck)] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[(ii + MoveCheck)], "id", getPackageName()));
                    textviews[(ii + MoveCheck)].setText(SelectedStyleAxe[i]);
                    textviews[(ii + MoveCheck)].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    getForLoopCarryBursts((ii + MoveCheck));
                }

                int iigg = ForLoopCarryBursts + 1;
                getForLoopCarryBursts(iigg);

                Float[] TruePhial;
                if (Skills.getCBPhialModifier() == 1f) TruePhial = SelectedPhial[1];
                else TruePhial = SelectedPhial[0];
                /*This determines whether to use the motion values for a charged shield or not based
                on whether the effect modifier, found by what shield charge is selected. TruePhial
                will then contain the corresponding array of motion values.*/

                if (MotionAxe.length == 10) MoveCheck = 3;
                else if (MotionAxe.length == 11) MoveCheck = 2;
                /*MoveCheck used to make sure the textviews for the moves are displayed correctly
                to compensate for the extra banner for the axe moves. MoveCheck is added to iig
                in order to make sure the textviews are all used accordingly. This is done so that
                the banner for the axe attack is always placed underneath the last textview that
                could possibly be used for the sword attacks, as that's where it's positioned
                originally.*/

                float TrueBurst;

                for (int i = 0; i < TruePhial.length; i++) {
                    if(BurstModifiersNames[i].equals("(L)Guard Bursts") && (YellowCheck || StyleText.equals("Valor")))
                        i = i + 1;
                    int iig = ForLoopCarryBursts + i;
                    /*Integer 'iig' used to store the value of 'ForLoopCarry' and adds one to it to
                    carry on from where the last for loop left off in order to fill the TextViews in
                    the correct order.*/

                    Skills.setFelyneBombardierModifier(FelyneBombardierCheck.isChecked());

                    if (ImpactPhial)
                        TrueBurst = Skills.getCBPhialAtk(ImpactPhial, RawDamage, RawElement) * TruePhial[i];
                    else TrueBurst = (Skills.getCBPhialAtk(ImpactPhial, RawDamage, RawElement *
                            M.getElmHitzoneValue()) * TruePhial[i]) / 100;

                    textviews[(iig + MoveCheck)] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[(iig + MoveCheck)], "id", getPackageName()));

                    if (i == 5)
                        textviews[(iig + MoveCheck)].setText(String.format("%s", Math.round(TrueBurst * NumberofPhials)));
                    else
                        textviews[(iig + MoveCheck)].setText(String.format("%s", Math.round(TrueBurst)));
                    textviews[(iig + MoveCheck)].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textview's value the value of 'iig's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/


                    textviews[(iig + MoveCheck)] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[(iig + MoveCheck)], "id", getPackageName()));
                    Extra = (TextView) findViewById(R.id.BurstAttack_2_Extra);
                    if (i == 1 && ImpactPhial && Skills.getCBPhialModifier() == 1.35f) {
                        String Key = "KO/Exhaust: " + String.format("%s", Math.round(90f *
                                Skills.getCBPhialModifier())) + "/" + String.format("%s",
                                Math.round(15f * Skills.getCBPhialModifier()));
                        Extra.setText(Key);
                        Extra.setVisibility(View.VISIBLE);
                    } else if (i == 1 && ImpactPhial) {
                        String Key = "KO/Exhaust: 90/15";
                        Extra.setText(Key);
                        Extra.setVisibility(View.VISIBLE);
                    } else if (i == 1 && !ImpactPhial) {
                        Extra.setVisibility(View.GONE);
                    }

                    TextView ExtraExtend = (TextView) findViewById(R.id.BurstAttack_Extend);
                    if (i == 5 && ImpactPhial) {
                        textviews[(iig + MoveCheck)].setText(BurstModifiersNames[i]);
                        String ExtraText = "KO/Exhaust: " + String.format("%s", Math.round(30f *
                                Skills.getCBPhialModifier()) * NumberofPhials) + "/" + String.format("%s",
                                Math.round(5f * Skills.getCBPhialModifier()) * NumberofPhials);
                        ExtraExtend.setText(ExtraText);
                        ExtraExtend.setVisibility(View.VISIBLE);
                    } else {
                        textviews[(iig + MoveCheck)].setText(BurstModifiersNames[i]);
                    }
                    textviews[(iig + MoveCheck)].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'ExtraText'/'Key's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/
                }
                BurstInfo.setVisibility(View.VISIBLE);
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
    public static float getChargeModifier(float i) {
        ChargeModifier = i;
        return ChargeModifier;
    }
    public static boolean isImpact(boolean i){
        ImpactPhial = i;
        return ImpactPhial;
    }
    public static int getNumberofPhials(int i){
        NumberofPhials = i;
        return NumberofPhials;
    }
    public static int getEnergyBladePhials(int i){
        EnergyBladePhials = i;
        return EnergyBladePhials;
    }
    public static boolean getStrikerCheck(boolean i) {
        StrikerCheck = i;
        return StrikerCheck;
    }
    public static boolean getRedCheck(boolean i) {
        RedCheck = i;
        return RedCheck;
    }
    public static boolean getYellowCheck(boolean i) {
        YellowCheck = i;
        return YellowCheck;
    }
    public static int getForLoopCarry(int i) {
        ForLoopCarry = i;
        return ForLoopCarry;
    }
    public static int getForLoopCarryBursts(int i) {
        ForLoopCarryBursts = i;
        return ForLoopCarryBursts;
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
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}