package com.mhx.marcus.mhgendamagecalc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

public class GunlanceCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, SharpnessSelect, ShotTypeSelect, ShotLevelSelect, HeatGaugeSelect,
            NumberofShellsSelect, MonsterSelect, HitzoneSelect, ElementSelect, HunterArtSelect;
    String SelectedMonster;
    Button Calculate;
    static float SelectedSharpnessElmModifier, SelectedSharpnessAtkModifier;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenArt;
    static int ShotLevel,  ShotType, ForLoopCarry, ForLoopValorCarry, NumberofShells;

    //Skill and Hunter Art Selection variables - Start
    Integer Counter;

    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect, GroupSSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck, BludgeonerCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck, FelyneBombardierCheck,
            AirborneCheck, WeaknessExploitCheck, DragonBreathCheck;

    float SkillSharpnessModifier = 1f;
    //-End-


    /*Creates a string/number variables which are arrays.*/
    float[] SharpModAtk = {0.5f, 0.75f, 1, 1.05f, 1.2f, 1.32f, 1.39f};
    float[] SharpModElm = {0.25f, 0.5f, 0.75f, 1, 1.06f, 1.12f, 1.2f};

    String[] TextViewIDsAttacks;
    String[] TextViewIDsNames;
    String[] AllTextViewIDs;

    float[] Motion;
    float[][] MotionGroup;
    String[] SelectedStyle;

    float[] GuildStyleMotion =  {0.21f, 0.21f, 0.21f, 0.36f, 0.25f,/*space*/ 0.16f, 0.28f, 0.39f, 0.22f};
    String[] GuildStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Slam","Upward Strike",
            "Upward Thrust","Run-In Thrust","Jump Attack Slam","Jump Attack Thrust"};

    float[] StrikerStyleMotion = {0.21f, 0.21f, 0.21f, 0.24f,/*space*/ 0.25f, 0.16f, 0.28f, 0.22f};
    String[] StrikerStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Ending Thrust",
            "Upward Strike","Upward Thrust","Run-In Thrust","Jump Attack Thrust"};

    float[] AerialStyleMotion = {0.21f, 0.21f, 0.21f, 0.24f, /*space*/ 0.25f, 0.16f, 0.28f, 0.36f};
    String[] AerialStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Ending Thrust",
            "Upward Strike","Upward Thrust","Run-In Thrust","Vault Attack"};

    float[] AdeptStyleMotion = {0.21f, 0.21f, 0.21f, 0.36f, 0.25f,/*space*/ 0.16f, 0.28f, 0.4f, 0.5f,
            0.39f, /*space*/0.22f};
    String[] AdeptStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Slam","Upward Strike",
            "Upward Thrust","Run-In Thrust","Adept Guard - Thrust","Adept Guard - Slam","Jump Attack Slam",
            "Jump Attack Thrust"};

    float[] ValorStyleMotion =  {0.21f, 0.21f, 0.21f, 0.36f, 0.25f,/*space*/ 0.16f, 0.28f, 0.36f, 0.4f,
            /*space*/ 0.39f, 0.22f};
    String[] ValorStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Slam","Upward Strike",
            "Upward Thrust","Run-In Thrust","Valor Stance - Reload Upward Strike","   -Full Clip",
            "Jump Attack Slam","Jump Attack Thrust"};

    float[] AlchemyStyleMotion =  {0.21f, 0.21f, 0.21f, 0.36f,/*space*/ 0.16f, 0.28f, 0.39f, 0.22f};
    String[] AlchemyStyleNames = {"Draw Attack","Idle Thrust","Follow Up Thrust","Slam",
            "Upward Thrust","Run-In Thrust","Jump Attack Slam","Jump Attack Thrust"};

    String[][] Styles = {GuildStyleNames, StrikerStyleNames, AerialStyleNames, AdeptStyleNames, ValorStyleNames, AlchemyStyleNames};

    float[] DragonBlast = {1.3f, 1.7f, 2.1f};//TODO: 06/12/2018 Change these motion values
    float[] BlastDash = {0.74f, 0.74f, 0.74f};
    float[][] HunterArts = {DragonBlast, BlastDash};
    float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Level I", "Level II", "Level III"};

    String[] ShotNames = {"Shells","     -Fire Damage","Charged Shot","     -Charged Fire Damage",
            "Wyverns Fire (All hits)","Full Burst"};

    String[] ValorRapidShotNames = {"First Shot","Second Shot","Third Shot","Fourth Shot","Fifth Shot","Sixth Shot"};
    float[] ValorRapidShotModifiers = {0.8f, 0.9f, 1, 1.4f, 1.5f, 1.6f};
    float[] SelectedShot;

    float[] NormalShotLevel1Motion = {10f, 4f, 1.2f, 0f, 120f, 1.1f};
    float[] NormalShotLevel2Motion = {14f, 5f, 1.2f, 0f, 140f, 1.1f};
    float[] NormalShotLevel3Motion = {18f, 6f, 1.2f, 0f, 160f, 1.1f};
    float[] NormalShotLevel4Motion = {21f, 7f, 1.2f, 0f, 180f, 1.1f};
    float[] NormalShotLevel5Motion = {24f, 8f, 1.2f, 0f, 200f, 1.1f};

    float[] LongShotLevel1Motion = {15f, 9f, 1.2f, 0f, 144f, 1f};
    float[] LongShotLevel2Motion = {21f, 11f, 1.2f, 0f, 168f, 1f};
    float[] LongShotLevel3Motion = {28f, 14f, 1.2f, 0f, 192f, 1f};
    float[] LongShotLevel4Motion = {32f, 16f, 1.2f, 0f, 216f, 1f};
    float[] LongShotLevel5Motion = {38f, 18f, 1.2f, 0f, 240f, 1f};

    float[] WideShotLevel1Motion = {20f, 6f, 1.45f, 0f, 120f, 0.85f};
    float[] WideShotLevel2Motion = {30f, 8f, 1.45f, 0f, 140f, 0.85f};
    float[] WideShotLevel3Motion = {40f, 10f, 1.45f, 0f, 160f, 0.85f};
    float[] WideShotLevel4Motion = {44f, 11f, 1.45f, 0f, 180f, 0.85f};
    float[] WideShotLevel5Motion = {48f, 12f, 1.45f, 0f, 200f, 0.85f};

    float [][] AllShotMotion = {NormalShotLevel1Motion,NormalShotLevel2Motion,NormalShotLevel3Motion,
            NormalShotLevel4Motion,NormalShotLevel5Motion,LongShotLevel1Motion,LongShotLevel2Motion,
            LongShotLevel3Motion,LongShotLevel4Motion,LongShotLevel5Motion,WideShotLevel1Motion,
            WideShotLevel2Motion,WideShotLevel3Motion,WideShotLevel4Motion,WideShotLevel5Motion};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunlance_calculation);
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

        ShotTypeSelect = (Spinner) findViewById(R.id.ShotTypeSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.GLShotType,
                android.R.layout.simple_spinner_dropdown_item);

        ShotTypeSelect.setAdapter(adapter3);

        ShotTypeSelect.setOnItemSelectedListener(this);

        ShotLevelSelect = (Spinner) findViewById(R.id.ShotLevelSelect);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.GLShotLevel,
                android.R.layout.simple_spinner_dropdown_item);

        ShotLevelSelect.setAdapter(adapter4);

        ShotLevelSelect.setOnItemSelectedListener(this);

        HeatGaugeSelect = (Spinner) findViewById(R.id.HeatGaugeSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.HeatGauge,
                android.R.layout.simple_spinner_dropdown_item);

        HeatGaugeSelect.setAdapter(adapter5);

        HeatGaugeSelect.setOnItemSelectedListener(this);

        NumberofShellsSelect = (Spinner) findViewById(R.id.NumberofShellsSelect);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.GLShellNumber,
                android.R.layout.simple_spinner_dropdown_item);

        NumberofShellsSelect.setAdapter(adapter6);

        NumberofShellsSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        ElementSelect = (Spinner) findViewById(R.id.ElementSelect);

        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(adapter7);

        ElementSelect.setOnItemSelectedListener(this);

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this,R.array.GL_HA_Names,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(adapter8);

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
        FelyneBombardierCheck = (CheckBox) findViewById(R.id.FelyneBombardierCheckBox);
        AirborneCheck = (CheckBox) findViewById(R.id.AirborneCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);
        DragonBreathCheck = (CheckBox) findViewById(R.id.DragonBreathCheckBox);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable and casts it to a TextView*/
        TextView StyleSelectText = (TextView) view;
        TextView ShotTypeSelectText = (TextView) view;
        TextView ShotLevelSelectText = (TextView) view;
        TextView HeatGaugeSelectText = (TextView) view;
        TextView NumberofShellsSelectText = (TextView) view;

        final String Style = StyleSelectText.getText().toString();
        if (Style.equals("Guild")) {
            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[0];
            Motion = GuildStyleMotion;
        } else if (Style.equals("Striker")) {
            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[1];
            Motion = StrikerStyleMotion;
        } else if (Style.equals("Aerial")) {
            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[2];
            Motion = AerialStyleMotion;
        } else if (Style.equals("Adept")) {
            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[3];
            Motion = AdeptStyleMotion;
        } else if(Style.equals("Valor")){
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[4];
            Motion = ValorStyleMotion;
        } else if(Style.equals("Alchemy")){
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[5];
            Motion = AlchemyStyleMotion;
        }


        TextView Sharpness = (TextView) view;
        String SharpnessModifier = Sharpness.getText().toString();
        if (SharpnessModifier.equals("Red")) {
            getAtk(SharpModAtk[0]);
            getElm(SharpModElm[0]);
            Skills.getBludgeonerModifier(30f);
        } else if (SharpnessModifier.equals("Orange")) {
            getAtk(SharpModAtk[1]);
            getElm(SharpModElm[1]);
            Skills.getBludgeonerModifier(30f);
        } else if (SharpnessModifier.equals("Yellow")) {
            getAtk(SharpModAtk[2]);
            getElm(SharpModElm[2]);
            Skills.getBludgeonerModifier(25f);
        } else if (SharpnessModifier.equals("Green")) {
            getAtk(SharpModAtk[3]);
            getElm(SharpModElm[3]);
            Skills.getBludgeonerModifier(15f);
        } else if (SharpnessModifier.equals("Blue")) {
            getAtk(SharpModAtk[4]);
            getElm(SharpModElm[4]);
            Skills.getBludgeonerModifier(0f);
        } else if (SharpnessModifier.equals("White")) {
            getAtk(SharpModAtk[5]);
            getElm(SharpModElm[5]);
            Skills.getBludgeonerModifier(0f);
        } else if(SharpnessModifier.equals("Purple")) {
            getAtk(SharpModAtk[6]);
            getElm(SharpModElm[6]);
            Skills.getBludgeonerModifier(0f);
        }

        final String ShotTypeText = ShotTypeSelectText.getText().toString();
        if (ShotTypeText.equals("Normal")) {
            getShotType(0);
        } else if (ShotTypeText.equals("Long")) {
            getShotType(5);
        } else if (ShotTypeText.equals("Wide")) {
            getShotType(10);
        }

        final String ShotLevelText = ShotLevelSelectText.getText().toString();
        if (ShotTypeText.equals("Level 1")) {
            getShotLevel(0);
        } else if (ShotTypeText.equals("Level 2")) {
            getShotLevel(1);
        } else if (ShotTypeText.equals("Level 3")) {
            getShotLevel(2);
        } else if (ShotTypeText.equals("Level 4")) {
            getShotLevel(3);
        } else if (ShotTypeText.equals("Level 5")) {
            getShotLevel(4);
        }

        final String HeatGauge = HeatGaugeSelectText.getText().toString();
        if (HeatGauge.equals("Yellow Gauge")) {
            Skills.setHeatGaugeModifier(1.1f);
        } else if (HeatGauge.equals("Orange Gauge")) {
            Skills.setHeatGaugeModifier(1.15f);
        } else if (HeatGauge.equals("Red Gauge")) {
            Skills.setHeatGaugeModifier(1.2f);
        }

        String ShellNumber = NumberofShellsSelectText.getText().toString();
        if (ShellNumber.equals("0")) {
            getNumberofShells(0);
        } else if (ShellNumber.equals("1")) {
            getNumberofShells(1);
        } else if (ShellNumber.equals("2")) {
            getNumberofShells(2);
        } else if (ShellNumber.equals("3")) {
            getNumberofShells(3);
        } else if (ShellNumber.equals("4")) {
            getNumberofShells(4);
        } else if (ShellNumber.equals("5")) {
            getNumberofShells(5);
        } else if (ShellNumber.equals("6")) {
            getNumberofShells(6);
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
        if(ArtText.equals("Dragon Blast")){
            getHunterArt("Dragon Blast");
        }
        else if(ArtText.equals("Blast Dash")) {
            getHunterArt("Blast Dash");
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
            Skills.setArtilleryModifier(1.1f);
        }
        else if(GroupSSkillsText.equals("Artillery Expert")) {
            Skills.setArtilleryModifier(1.2f);
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
            affect all the textviews needed.*/
            TextView[] textviews = new TextView[(46)];

            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(GunlanceCalculation.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                TextViewIDsAttacks = new String[]{"GLAttack_1", "GLAttack_2", "GLAttack_3",
                        "GLAttack_4", "GLAttack_5", "GLAttack_6", "GLAttack_7",
                        "GLAttack_8", "GLAttack_9", "GLAttack_10", "GLAttack_11",
                        "GLAttack_12", "GLAttack_13", "GLAttack_14", "GLAttack_15",
                        "GLAttack_16", "GLAttack_17", "GLAttack_18", "GLAttack_19",
                        "GLAttack_20", "GLAttack_21", "GLAttack_22", "GLAttack_23"};

                TextViewIDsNames = new String[]{"GLAttack_1_Name", "GLAttack_2_Name", "GLAttack_3_Name",
                        "GLAttack_4_Name", "GLAttack_5_Name", "GLAttack_6_Name", "GLAttack_7_Name",
                        "GLAttack_8_Name", "GLAttack_9_Name", "GLAttack_10_Name", "GLAttack_11_Name",
                        "GLAttack_12_Name", "GLAttack_13_Name", "GLAttack_14_Name", "GLAttack_15_Name",
                        "GLAttack_16_Name", "GLAttack_17_Name", "GLAttack_18_Name", "GLAttack_19_Name",
                        "GLAttack_20_Name", "GLAttack_21_Name", "GLAttack_22_Name", "GLAttack_23_Name"};

                AllTextViewIDs = new String[]{"GLAttack_1_Name", "GLAttack_2_Name", "GLAttack_3_Name",
                        "GLAttack_4_Name", "GLAttack_5_Name", "GLAttack_6_Name", "GLAttack_7_Name",
                        "GLAttack_8_Name", "GLAttack_9_Name", "GLAttack_10_Name", "GLAttack_11_Name",
                        "GLAttack_12_Name", "GLAttack_13_Name", "GLAttack_14_Name", "GLAttack_15_Name",
                        "GLAttack_16_Name", "GLAttack_17_Name", "GLAttack_18_Name", "GLAttack_19_Name",
                        "GLAttack_20_Name", "GLAttack_21_Name", "GLAttack_22_Name", "GLAttack_23_Name",
                        "GLAttack_1", "GLAttack_2", "GLAttack_3", "GLAttack_4", "GLAttack_5", "GLAttack_6",
                        "GLAttack_7", "GLAttack_8", "GLAttack_9", "GLAttack_10", "GLAttack_11",
                        "GLAttack_12", "GLAttack_13", "GLAttack_14", "GLAttack_15", "GLAttack_16",
                        "GLAttack_17", "GLAttack_18", "GLAttack_19", "GLAttack_20", "GLAttack_21",
                        "GLAttack_22", "GLAttack_23"};

                //String[] Banners = {"ShellingBanner", "AttackBanner","ShellingDisclaimer","StaggerBanner"};

                for (int i = 0; i < AllTextViewIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
                    textviews[i].setVisibility(View.GONE);
                }

                RelativeLayout AttackInfo = (RelativeLayout) findViewById(R.id.AttackInfo);
                RelativeLayout ShellingInfo = (RelativeLayout) findViewById(R.id.ShellingInfo);
                RelativeLayout ValorShellingInfo = (RelativeLayout) findViewById(R.id.ValorShellingInfo);
                TextView StaggerBanner = findViewById(R.id.StaggerBanner);
                AttackInfo.setVisibility(View.GONE);
                ShellingInfo.setVisibility(View.GONE);
                ValorShellingInfo.setVisibility(View.GONE);
                StaggerBanner.setVisibility(View.GONE);

                if((ShotType == 4 && NumberofShells > 4) || (ShotType == 8 && NumberofShells > 3)) {
                    if(ShotType == 4) {
                        Toast.makeText(GunlanceCalculation.this,"Long Types have a maximum clip size of 3 (4 with Load Up)",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(GunlanceCalculation.this,"Wide Types have a maximum clip size of 2 (3 with Load Up)",
                                Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                int[] HunterArtElementCheck = {1};
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                    if(ChosenArt.equals("Dragon Blast")){
                        ChosenHunterArt = HunterArts[0];
                        HunterArtElementCheck = new int[] {0, 0, 0};
                    }
                    else if(ChosenArt.equals("Blast Dash")){
                        ChosenHunterArt = HunterArts[1];
                        HunterArtElementCheck = new int[] {2, 2, 2};
                    }
                }
                //-End-

                MotionGroup = new float[][] {Motion, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawAffinity = 0;

                float TrueRaw;

                TextView Damage = (TextView) findViewById(R.id.DamageInputGL);
                TextView Element = (TextView) findViewById(R.id.ElementInputGL);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputGL);

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

                if(DragonBreathCheck.isChecked()){
                    Skills.setHeatGaugeModifier(1.2f);
                }

                MonsterCalculation M;
                    /*'M' is the new variable in this block of code and is used to call the
                    'MonsterCalculation' class. The calculations for hitzones will be done inside that
                    class.*/
                M = new MonsterCalculation(GunlanceCalculation.this,
                        ChosenMonster + "RawHitzones_Cut",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);

                M.getHitzones(GunlanceCalculation.this, ChosenElement, Skills, WeaknessExploitCheck.isChecked());

                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    if(MotionCheck == 0){
                        if(AirborneCheck.isChecked() && SkillCheck && (SelectedStyle[i].contains("Vault") ||
                                SelectedStyle[i].contains("Jump"))){
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        }
                        else{
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        }
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * Motion[i];
                    }
                    else{
                        TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ChosenHunterArt[i];
                    }
                    //-End-

                    //Hitzone Modification - Start

                    float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier *
                            SkillSharpnessModifier) / 100;
                    float HitzoneRaw = (TrueRaw * Skills.getHeatGaugeModifier()) * ModifiedRawHitzone;

                    float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier *
                            SkillSharpnessModifier) / 100;
                    float HitzoneElm = RawElement * ModifiedElmHitzone;

                    //Hitzone Modification - End

                    float TrueAttack = 0;
                    if (!ChosenArt.equals("-None-")){
                        if (ChosenArt.equals("Blast Dash")){
                            TrueAttack = HitzoneRaw + (HitzoneElm * HunterArtElementCheck[i]);
                        }
                        else if (ChosenArt.equals("Dragon Blast")){
                            TrueAttack = ChosenHunterArt[i];
                        }
                    }
                    else{
                        TrueAttack = HitzoneRaw + HitzoneElm;
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
                    if(MotionCheck == 0){
                        textviews[i].setText(SelectedStyle[i]);
                        TextView Banner = (TextView) findViewById(R.id.AttackBanner);
                        Banner.setText("Attacks");
                    }
                    else{
                        if(ChosenArt.equals("Dragon Blast")){
                            textviews[i].setText(HunterArtsLevels[i]);
                        }
                        else if(ChosenArt.equals("Blast Dash")){
                            textviews[i].setText(HunterArtsLevels[i]);
                            Snackbar.make(view, "Distance, not attack, increases at higher levels",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                        TextView Banner = (TextView) findViewById(R.id.AttackBanner); 
                        Banner.setText(ChosenArt);
                    }
                    textviews[i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    getForLoopCarry(i);
                }

                if(!ChosenMonster.equals("None")){
                    StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                    StaggerBanner.setVisibility(View.VISIBLE);
                }

                TextView Banner = (TextView) findViewById(R.id.AttackBanner);
                Banner.setVisibility(View.VISIBLE);

                if(MotionCheck == 1){
                    return;
                }

                SelectedShot = AllShotMotion[(ShotType + ShotLevel)];
                M.setElmHitzoneValue_GL(GunlanceCalculation.this, ChosenMonster + "ElmHitzones_Fire");
                M.getHitzones(GunlanceCalculation.this, "Fire", Skills, false);

                int MoveCheck = 0;
                if (Motion.length == 11) MoveCheck = 1;
                else if (Motion.length == 9) MoveCheck = 3;
                else if (Motion.length == 8) MoveCheck = 4;

                AttackInfo.setVisibility(View.VISIBLE);
                ShellingInfo.setVisibility(View.VISIBLE);

                Skills.setFelyneBombardierModifier(FelyneBombardierCheck.isChecked());

                for (int i = 0; i < ShotNames.length; i++) {
                    int ii = ForLoopCarry + i;
                    if(ShotNames[i].equals("Full Burst") && SelectedStyle == Styles[1]) {
                        return;
                    }

                    if(ShotNames[i].equals("Charged Shot") && SelectedStyle == Styles[5]){
                        i += 2;
                    }

                    float TrueAttack = (SelectedShot[i] + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                            Skills.getShellingModifier();

                    textviews[ii + MoveCheck] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ii + MoveCheck], "id", getPackageName()));
                    if(ShotNames[i].equals("     -Fire Damage")) {
                        TrueAttack = ((SelectedShot[i] + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                M.getElmHitzoneValue()) / 100;
                    }
                    else if(ShotNames[i].equals("Charged Shot")) {
                        TrueAttack = ((SelectedShot[0] + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                SelectedShot[i]) * Skills.getShellingModifier();
                    }
                    else if(ShotNames[i].equals("     -Charged Fire Damage")) {
                        TrueAttack = (((SelectedShot[1] + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                SelectedShot[2]) * M.getElmHitzoneValue()) / 100;
                    }
                    else if(ShotNames[i].equals("Wyverns Fire")) {
                        float AlchemyMod = 1;
                        if(SelectedStyle == Styles[5]){
                            AlchemyMod = 0.5f;
                        }
                        TrueAttack = ((SelectedShot[i] * AlchemyMod) + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                Skills.getShellingModifier();
                    }
                    else if(ShotNames[i].equals("Full Burst")) {
                        TrueAttack = (((SelectedShot[0] + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                SelectedShot[i]) * Skills.getShellingModifier() * NumberofShells);
                    }
                    textviews[ii + MoveCheck].setText(String.format("%s", Math.round(TrueAttack)));
                    textviews[ii + MoveCheck].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    textviews[ii + MoveCheck] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ii + MoveCheck], "id", getPackageName()));
                    textviews[ii + MoveCheck].setText(ShotNames[i]);
                    textviews[ii + MoveCheck].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    getForLoopValorCarry(ii + MoveCheck);
                }

                if(SelectedStyle != Styles[4]) return;

                int ig = ForLoopValorCarry + 1;
                getForLoopValorCarry(ig);

                TextView ValorShellingDisclaimer = (TextView) findViewById(R.id.ValorShellingDisclaimer);

                if(NumberofShells != 0) {
                    for (int i = 0; i < NumberofShells; i++) {
                        int ii = ForLoopValorCarry + i;
                        float TrueAttack = ((SelectedShot[0]/* * ValorRapidShotModifiers[i]*/) + Skills.getDragonBreathModifier(DragonBreathCheck.isChecked())) *
                                Skills.getShellingModifier();

                        textviews[ii] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ii], "id", getPackageName()));
                        textviews[ii].setText(String.format("%s", Math.round(TrueAttack * ValorRapidShotModifiers[i])));
                        textviews[ii].setVisibility(View.VISIBLE);
                        /*Sets the current textview to the id value of 'Counter' and then sets that
                        textviews value the value of 'test's current value. It also sets the
                        visibility of all the used textboxes to 'visible'.*/

                        textviews[ii] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ii], "id", getPackageName()));
                        textviews[ii].setText(ValorRapidShotNames[i]);
                        textviews[ii].setVisibility(View.VISIBLE);
                        /*Sets the current textview to the id value of 'Counter' and then sets that
                        textviews value the value of 'test's current value. It also sets the
                        visibility of all the used textboxes to 'visible'.*/
                    }
                    ValorShellingDisclaimer.setText(R.string.shelling_damage_disclaimer);
                }
                else{
                    ValorShellingDisclaimer.setText("No Shells loaded");
                }

                ValorShellingInfo.setVisibility(View.VISIBLE);
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
    public static int getShotLevel(int i) {
        ShotLevel = i;
        return ShotLevel;
    }
    public static int getShotType(int i) {
        ShotType = i;
        return ShotType;
    }
    public static int getForLoopCarry(int i) {
        ForLoopCarry = i;
        return ForLoopCarry;
    }
    public static int getForLoopValorCarry(int i) {
        ForLoopValorCarry = i;
        return ForLoopValorCarry;
    }
    public static int getNumberofShells(int i) {
        NumberofShells = i;
        return NumberofShells;
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