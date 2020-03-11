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

import java.util.Arrays;
import java.util.List;

public class InsectGlaiveCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, SharpnessSelect, ExtractSelect, MonsterSelect, HitzoneSelect, ElementSelect, HunterArtSelect;
    Button Calculate;
    String SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenArt;
    static float SelectedSharpnessElmModifier, SelectedSharpnessAtkModifier, ExtractModifier, SwarmModifier;
    static int MovesetChange;

    //Skill and Hunter Art Selection variables - Start
    Integer Counter;

    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck, BludgeonerCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck, AirborneCheck,
            WeaknessExploitCheck;

    float SkillSharpnessModifier = 1f;
    //-End-

    /*Creates a string/number variables which are arrays.*/
    float[] SharpModAtk = {0.5f, 0.75f, 1, 1.05f, 1.2f, 1.32f, 1.39f};
    float[] SharpModElm = {0.25f, 0.5f, 0.75f, 1, 1.06f, 1.12f, 1.2f};

    String[] TextViewIDsAttacks;
    String[] TextViewIDsNames;
    String[] AllTextViewIDs;

    float[] SelectedMotion;
    String[] SelectedStyle;
    float[][] Motion;
    float[][] MotionGroup;
    String[][] Style;

    float[] GuildStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.12f, 0.17f, 0.28f,
            0.27f,/*space*/0.23f, 0.1f, 0.21f, 0.22f,/*space*/ 0.6f};
    String[] GuildStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Forward Thrust","Back Flip","Forward Slam","Forward Slam - Follow Up",
            "Downward Strike - Left","Marking Strike","Pole Vault/Jump Attack","Kinsect Shot",
            "Kinsect Shot - Charge"};
    float[] GuildStyleMotionExtract =  {0.28f, 0.53f, 0.78f, /*space*/0.24f, 0.49f, 0.12f, /*space*/0.56f,
            0.42f, 0.27f,/*space*/0.56f, 0.1f, 0.24f,/*space*/ 0.22f, 0.6f};
    String[] GuildStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)","     -During Combo",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)","Forward Thrust",
            "Back Flip (Both hits)","Forward Slam (Both hits)","Forward Slam - Follow Up",
            "Downward Strike - Left (Both hits)","Marking Strike","Pole Vault/Jump Attack (Both hits)",
            "Kinsect Shot","Kinsect Shot - Charge"};
    float[][] GuildStyleMotion = {GuildStyleMotionNoExtract, GuildStyleMotionExtract};
    String[][] GuildStyleNames = {GuildStyleNamesNoExtract, GuildStyleNamesExtract};

    float[] StrikerStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.12f, 0.17f,
            0.27f, 0.1f,/*space*/ 0.21f, 0.22f, 0.6f};
    String[] StrikerStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Forward Thrust","Back Flip","Forward Slam","Marking Strike",
            "Pole Vault/Jump Attack","Kinsect Shot","Kinsect Shot - Charge"};
    float[] StrikerStyleMotionExtract =  {0.28f, 0.53f, /*space*/0.24f, 0.49f, 0.12f, /*space*/0.56f,
            0.42f, 0.1f, /*space*/0.24f, 0.22f, 0.6f};
    String[] StrikerStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)","Forward Thrust",
            "Back Flip (Both hits)","Forward Slam (Both hits)","Marking Strike",
            "Pole Vault/Jump Attack (Both hits)","Kinsect Shot","Kinsect Shot - Charge"};
    float[][] StrikerStyleMotion = {StrikerStyleMotionNoExtract, StrikerStyleMotionExtract};
    String[][] StrikerStyleNames = {StrikerStyleNamesNoExtract, StrikerStyleNamesExtract};

    float[] AerialStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.17f, 0.28f,
            0.27f,/*space*/0.23f, 0.1f, 0.24f, 0.22f,/*space*/ 0.6f};
    String[] AerialStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Back Flip","Forward Slam","Forward Slam - Follow Up",
            "Downward Strike - Left","Marking Strike","Pole Vault/Jump Attack","Kinsect Shot",
            "Kinsect Shot - Charge"};
    float[] AerialStyleMotionExtract =  {0.28f, 0.53f, 0.78f, /*space*/0.24f, 0.49f, /*space*/0.56f,
            0.42f, 0.27f,/*space*/0.56f, 0.1f, 0.17f,/*space*/ 0.22f, 0.6f};
    String[] AerialStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)","     -During Combo",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)",
            "Back Flip (Both hits)","Forward Slam (Both hits)","Forward Slam - Follow Up",
            "Downward Strike - Left (Both hits)","Marking Strike","Pole Vault (Per hit - Max 4 hits)",
            "Kinsect Shot","Kinsect Shot - Charge"};
    float[][] AerialStyleMotion = {AerialStyleMotionNoExtract, AerialStyleMotionExtract};
    String[][] AerialStyleNames = {AerialStyleNamesNoExtract, AerialStyleNamesExtract};

    float[] AdeptStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.12f, 0.17f, 0.28f,
            0.27f,/*space*/0.23f, 0.1f, 0.21f, 0.22f,/*space*/ 0.6f, 0.3f, 0.35f};
    String[] AdeptStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Forward Thrust","Back Flip","Forward Slam","Forward Slam - Follow Up",
            "Downward Strike - Left","Marking Strike","Pole Vault/Jump Attack","Kinsect Shot",
            "Kinsect Shot - Charge","Adept Evade Extract Attack","Adept Evade Lunge Attack"};
    float[] AdeptStyleMotionExtract =  {0.28f, 0.53f, 0.78f, /*space*/0.24f, 0.49f, 0.12f, /*space*/0.56f,
            0.42f, 0.27f,/*space*/0.56f, 0.1f, 0.24f,/*space*/ 0.3f, 0.35f, 0.22f, 0.6f};
    String[] AdeptStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)","     -During Combo",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)","Forward Thrust",
            "Back Flip (Both hits)","Forward Slam (Both hits)","Forward Slam - Follow Up",
            "Downward Strike - Left (Both hits)","Marking Strike","Pole Vault/Jump Attack (Both hits)",
            "Adept Evade Extract Attack","Adept Evade Lunge Attack","Kinsect Shot","Kinsect Shot - Charge"};
    float[][] AdeptStyleMotion = {AdeptStyleMotionNoExtract, AdeptStyleMotionExtract};
    String[][] AdeptStyleNames = {AdeptStyleNamesNoExtract, AdeptStyleNamesExtract};

    float[] ValorStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.12f, 0.17f, 0.28f,
            0.27f,/*space*/0.23f, 0.1f, 0.21f, 0.22f,/*space*/ 0.28f, 0.22f, 0.6f};
    String[] ValorStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Forward Thrust","Back Flip","Forward Slam","Forward Slam - Follow Up",
            "Downward Strike - Left","Marking Strike","Pole Vault/Jump Attack","Kinsect Auto Attack (Valor State)",
            "Strong Kinsect Auto Attack (Valor State)","Kinsect Shot","Kinsect Shot - Charge"};
    float[] ValorStyleMotionExtract =  {0.28f, 0.53f, 0.78f, /*space*/0.24f, 0.49f, 0.12f, /*space*/0.56f,
            0.42f, 0.27f,/*space*/0.56f, 0.1f, 0.24f,/*space*/ 0.22f, 0.28f,/*space*/ 0.22f, 0.6f};
    String[] ValorStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)","     -During Combo",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)","Forward Thrust",
            "Back Flip (Both hits)","Forward Slam (Both hits)","Forward Slam - Follow Up",
            "Downward Strike - Left (Both hits)","Marking Strike","Pole Vault/Jump Attack (Both hits)",
            "Kinsect Auto Attack (Valor State)","Strong Kinsect Auto Attack (Valor State)",
            "Kinsect Shot","Kinsect Shot - Charge"};
    float[][] ValorStyleMotion = {ValorStyleMotionNoExtract, ValorStyleMotionExtract};
    String[][] ValorStyleNames = {ValorStyleNamesNoExtract, ValorStyleNamesExtract};

    float[] AlchemyStyleMotionNoExtract =  {0.28f, 0.4f, 0.21f, /*space*/ 0.36f, 0.12f, 0.28f,
            0.27f,/*space*/0.23f, 0.1f, 0.21f, 0.22f};
    String[] AlchemyStyleNamesNoExtract = {"Draw Attack","Idle Attack (Both hits)","Downward Strike - Right",
            "Reverse Smack (Both hits)","Forward Thrust","Forward Slam","Forward Slam - Follow Up",
            "Downward Strike - Left","Marking Strike","Pole Vault/Jump Attack","Kinsect Shot"};
    float[] AlchemyStyleMotionExtract =  {0.28f, 0.53f, 0.78f, /*space*/0.24f, 0.49f, 0.12f,
            /*space*/0.42f, 0.27f, 0.56f, /*space*/0.1f, 0.24f, 0.22f};
    String[] AlchemyStyleNamesExtract = {"Draw Attack","Idle Attack (All hits)","     -During Combo",
            "Downward Strike - Right (Both hits)","Reverse Smack (All hits)","Forward Thrust",
            "Forward Slam (Both hits)","Forward Slam - Follow Up","Downward Strike - Left (Both hits)",
            "Marking Strike","Pole Vault/Jump Attack (Both hits)","Kinsect Shot"};
    float[][] AlchemyStyleMotion = {AlchemyStyleMotionNoExtract, AlchemyStyleMotionExtract};
    String[][] AlchemyStyleNames = {AlchemyStyleNamesNoExtract, AlchemyStyleNamesExtract};

    float[] ExtractHunter = {0.7f, 0.7f, 0.7f};
    float[] Swarm = {0.05f, 0.05f, 0.05f};
    float[] BugBlow = {1f, 1.15f, 1.3f, 0.9f, 1.2f, 1.5f};
    float[][] HunterArts = {ExtractHunter, Swarm, BugBlow};
    float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Level I", "Level II", "Level III", "Swarm I Bonus Damage",
            "Swarm II Bonus Damage", "Swarm III Bonus Damage"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_glaive_calculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Gives the variable for the spinner 'StyleSelect' the actual value for a spinner.*/
        StyleSelect = (Spinner) findViewById(R.id.StyleSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Styles,android.R.layout.
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

        ExtractSelect = (Spinner) findViewById(R.id.ExtractSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.Extracts,
                android.R.layout.simple_spinner_dropdown_item);

        ExtractSelect.setAdapter(adapter3);

        ExtractSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        ElementSelect = (Spinner) findViewById(R.id.ElementSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(adapter5);

        ElementSelect.setOnItemSelectedListener(this);

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this,R.array.IG_HA_Names,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(adapter6);

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
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible),
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
        AirborneCheck = (CheckBox) findViewById(R.id.AirborneCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable called 'StyleSelectText' and casts it to a TextView*/
        TextView StyleSelectText = (TextView) view;
        TextView ExtractSelectText = (TextView) view;
        /*Toast.makeText(this, "Selected style: " + StyleSelectText.getText().toString(),
                                                            Toast.LENGTH_SHORT).show();*/
        final String StyleText = StyleSelectText.getText().toString();
        if (StyleText.equals("Guild")) {
            Snackbar.make(view, "Selected style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = GuildStyleNames;
            Motion = GuildStyleMotion;
        } else if (StyleText.equals("Striker")) {
            Snackbar.make(view, "Selected style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = StrikerStyleNames;
            Motion = StrikerStyleMotion;
        } else if (StyleText.equals("Aerial")) {
            Snackbar.make(view, "Selected style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = AerialStyleNames;
            Motion = AerialStyleMotion;
        } else if (StyleText.equals("Adept")) {
            Snackbar.make(view, "Selected style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = AdeptStyleNames;
            Motion = AdeptStyleMotion;
        } else if(StyleText.equals("Valor")){
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = ValorStyleNames;
            Motion = ValorStyleMotion;
        } else if(StyleText.equals("Alchemy")){
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Style = AlchemyStyleNames;
            Motion = AlchemyStyleMotion;
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

        final String Extract = ExtractSelectText.getText().toString();
        if (Extract.equals("None")) {
            Skills.setIGExtractModifier(1);
            getMovesetChange(0);
            //Skills.setIGSwarmModifier(1);
        } else if (Extract.equals("Red Extract")) {
            Skills.setIGExtractModifier(1);
            getMovesetChange(1);
            //Skills.setIGSwarmModifier(1.2f);
        } else if (Extract.equals("Red + White Extract")) {
            Skills.setIGExtractModifier(1.15f);
            getMovesetChange(1);
            //Skills.setIGSwarmModifier(1.2f);
        } else if (Extract.equals("All Extracts")) {
            Skills.setIGExtractModifier(1.2f);
            getMovesetChange(1);
            //Skills.setIGSwarmModifier(1.2f);
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
        if(ArtText.equals("Extract Hunter")){
            getHunterArt("Extract Hunter");
        }
        else if(ArtText.equals("Bug Blow")){
            getHunterArt("Bug Blow");
        }
        else if(ArtText.equals("Swarm")) {
            getHunterArt("Swarm");
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
            Skills.setGroupK(15);
        }
        else if(GroupKSkillsText.equals("Cold Blooded - Cold Drink (+5)")) {
            Skills.setGroupK(5);
        }
        else if(GroupKSkillsText.equals("Cold Blooded - Cold Area + Cold Drink (+20)")) {
            Skills.setGroupK(20);
        }
        else if(GroupKSkillsText.equals("Hot Blooded - Hot Area (+15)")) {
            Skills.setGroupK(15);
        }
        else if(GroupKSkillsText.equals("-Cold/Hot Blooded-")) {
            Skills.setGroupK(0);
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
    }

    public void Calculate(){
        /*Binds the variable 'Calculate' to a button, specifically the 'CalculateButton' using its ID.*/
        Calculate = (Button) findViewById(R.id.CalculateButton);

        /*Gives the button an onClickListener() method (function) and makes it akin to an inline function.*/
        Calculate.setOnClickListener(new View.OnClickListener() {

            /*Creates a TextView array variable called 'textviews' and sets it so the values
            affect all the textviews needed.*/
            TextView[] textviews = new TextView[(36)];

            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(InsectGlaiveCalculation.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                TextViewIDsAttacks = new String[]{"IGAttack_1", "IGAttack_2", "IGAttack_3",
                        "IGAttack_4", "IGAttack_5", "IGAttack_6", "IGAttack_7",
                        "IGAttack_8", "IGAttack_9", "IGAttack_10", "IGAttack_11",
                        "IGAttack_12", "IGAttack_13", "IGAttack_14", "IGAttack_15",
                        "IGAttack_16", "IGAttack_17", "IGAttack_18"};

                TextViewIDsNames = new String[]{"IGAttack_1_Name", "IGAttack_2_Name", "IGAttack_3_Name",
                        "IGAttack_4_Name", "IGAttack_5_Name", "IGAttack_6_Name", "IGAttack_7_Name",
                        "IGAttack_8_Name", "IGAttack_9_Name", "IGAttack_10_Name", "IGAttack_11_Name",
                        "IGAttack_12_Name", "IGAttack_13_Name", "IGAttack_14_Name", "IGAttack_15_Name",
                        "IGAttack_16_Name", "IGAttack_17_Name", "IGAttack_18_Name"};

                AllTextViewIDs = new String[]{"IGAttack_1_Name", "IGAttack_2_Name", "IGAttack_3_Name",
                        "IGAttack_4_Name", "IGAttack_5_Name", "IGAttack_6_Name", "IGAttack_7_Name",
                        "IGAttack_8_Name", "IGAttack_9_Name", "IGAttack_10_Name", "IGAttack_11_Name",
                        "IGAttack_12_Name", "IGAttack_13_Name", "IGAttack_14_Name", "IGAttack_15_Name",
                        "IGAttack_16_Name", "IGAttack_17_Name", "IGAttack_18_Name", "IGAttack_1",
                        "IGAttack_2", "IGAttack_3", "IGAttack_4", "IGAttack_5", "IGAttack_6",
                        "IGAttack_7", "IGAttack_8", "IGAttack_9", "IGAttack_10", "IGAttack_11",
                        "IGAttack_12", "IGAttack_13", "IGAttack_14", "IGAttack_15",
                        "IGAttack_16", "IGAttack_17", "IGAttack_18"};

                for (int i = 0; i < AllTextViewIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
                    textviews[i].setVisibility(View.GONE);

                }

                TextView Banner = (TextView) findViewById((R.id.AttackBanner));
                TextView Extract = (TextView) findViewById((R.id.ExtractBanner));
                Extract.setVisibility(View.GONE);
                RelativeLayout Info = (RelativeLayout) findViewById((R.id.AttackInfo));
                Info.setVisibility(View.GONE);

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                int[] HunterArtElementCheck = {1};
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                    if(ChosenArt.equals("Extract Hunter")){
                        ChosenHunterArt = HunterArts[0];
                        HunterArtElementCheck = new int[] {1, 1, 1};
                    }
                    else if(ChosenArt.equals("Swarm")){
                        ChosenHunterArt = HunterArts[1];
                        HunterArtElementCheck = new int[] {1, 1, 1};
                    }
                    else if(ChosenArt.equals("Bug Blow")){
                        ChosenHunterArt = HunterArts[2];
                        HunterArtElementCheck = new int[] {3, 3, 3, 1, 1, 1};
                    }
                }
                //-End-

                SelectedMotion = Motion[MovesetChange];
                SelectedStyle = Style[MovesetChange];

                MotionGroup = new float[][] {SelectedMotion, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawAffinity = 0;

                float TrueRaw;

                TextView Damage = (TextView) findViewById(R.id.DamageInputIG);
                TextView Element = (TextView) findViewById(R.id.ElementInputIG);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputIG);

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

                //Hitzone Modification - Start

                MonsterCalculation M;
                    /*'M' is the new variable in this block of code and is used to call the
                    'MonsterCalculation' class. The calculations for hitzones will be done inside that
                    class.*/
                M = new MonsterCalculation(InsectGlaiveCalculation.this,
                        ChosenMonster + "RawHitzones_Cut",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "_StaggerLimits",
                        ChosenMonster + "_Extracts",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);


                M.getHitzones_IG(InsectGlaiveCalculation.this, ChosenElement, Skills, WeaknessExploitCheck.isChecked());

                float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier *
                        SkillSharpnessModifier) / 100;
                float HitzoneRaw;

                float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier *
                        SkillSharpnessModifier) / 100;
                float HitzoneElm = RawElement * ModifiedElmHitzone;
                //Hitzone Modification - End

                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    int HitMultiplier = 1;
                    if (SelectedStyle[i].contains("Both hits")) {
                        HitMultiplier = 2;
                    }
                    else if (SelectedStyle[i].equals("     -During Combo")) {
                        HitMultiplier = 4;
                    }
                    else if (SelectedStyle[i].contains("All hits")) {
                        HitMultiplier = 3;
                    }

                    if(MotionCheck == 0){
                        if(AirborneCheck.isChecked() && SkillCheck && (SelectedStyle[i].contains("Vault") ||
                                SelectedStyle[i].contains("Jump"))){
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        }
                        else{
                            Skills.setAirborneModifier(false);
                        }
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * SelectedMotion[i];
                    }
                    else{
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ChosenHunterArt[i];
                    }

                    if(!ChosenArt.equals("-None-")){
                        if(SwarmModifier == 1.2f && ChosenArt.equals("Swarm")){
                            HitzoneRaw = TrueRaw * SwarmModifier;
                        }
                        else if(ChosenArt.equals("Swarm")){
                            HitzoneRaw = TrueRaw;
                        }
                        else{
                            HitzoneRaw = TrueRaw * ModifiedRawHitzone;
                        }
                    }
                    else{
                        HitzoneRaw = TrueRaw * ModifiedRawHitzone;
                    }

                    float TrueAttack;
                    if (!ChosenArt.equals("-None-")){
                        TrueAttack = HitzoneRaw + (HitzoneElm * HunterArtElementCheck[i]);
                    }
                    else{
                        TrueAttack = HitzoneRaw + (HitzoneElm * HitMultiplier);
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

                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                    if(MotionCheck == 0){
                        textviews[i].setText(SelectedStyle[i]);
                    }
                    else{
                        if(ChosenArt.equals("Swarm")){
                            Snackbar.make(view, "-Attack rate increases with each level\n-Damage displayed" +
                                    " is fixed damage", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                        else if(ChosenArt.equals("Extract Hunter")){
                            Snackbar.make(view, "Extract effect duration increases with each level",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                        textviews[i].setText(HunterArtsLevels[i]);
                        Banner.setText(ChosenArt);
                    }
                    if(SelectedStyle[i].equals("Kinsect Shot - Charge")) {
                        textviews[i + 1] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i + 1], "id", getPackageName()));
                        textviews[i + 1].setText("     -40 Stun for Impact Type Kinsects");
                        textviews[i + 1].setVisibility(View.VISIBLE);

                        textviews[i + 1] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i + 1], "id", getPackageName()));
                        textviews[i + 1].setText("");
                        textviews[i + 1].setVisibility(View.GONE);
                    }
                    textviews[i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/
                }
                TextView StaggerBanner = (TextView) findViewById((R.id.StaggerBanner));
                if(ChosenMonster.equals("None")){
                    StaggerBanner.setVisibility(View.GONE);
                    Extract.setVisibility(View.GONE);
                }
                else{
                    StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                    StaggerBanner.setVisibility(View.VISIBLE);
                    Extract.setText(M.getExtract());
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
    public static int getMovesetChange(int i) {
        MovesetChange = i;
        return MovesetChange;
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