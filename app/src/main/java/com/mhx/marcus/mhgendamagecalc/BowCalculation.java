package com.mhx.marcus.mhgendamagecalc;

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
import android.widget.RadioButton;
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

public class BowCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, CoatingSelect, ArcShotSelect;
    Spinner Level1ShotSelect,Level2ShotSelect,Level3ShotSelect,Level4ShotSelect;
    Spinner DistanceSelect, MonsterSelect, HitzoneSelect, ElementSelect, HunterArtSelect;
    Button Calculate;
    String SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenArt;
    static float DistanceModifier;
    static String ArcShot;
    static int ForLoopCarry;

    /*Creates a string/number variables which are arrays.*/
    String[] TextViewIDsAttacks, TextViewIDsNames, AllTextViewIDs;
    Float[] MotionAtk, MotionElm, ChargeModifierAtk, ChargeModifierElm;
    int[] HeavyCheck = {0, 0, 0, 0};
    Float ArcShotMotion;
    int ChargeLevel;

    //Skill and Hunter Art Selection variables - Start
    Integer Counter;

    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck,
            FelyneTemperCheck, AirborneCheck, NormalUpCheck, HeavyUpCheck, PierceUpCheck, PelletUpCheck,
            TrueShotUpCheck, WeaknessExploitCheck, DragonheartCheck;
    RadioButton EvasiveManeuversLevel2Check, EvasiveManeuversLevel3Check, EvasiveManeuversOffCheck;

    //-End-

    Float Level1ChargeMotionAtk, Level2ChargeMotionAtk, Level3ChargeMotionAtk, Level4ChargeMotionAtk;
    Float Level1ChargeMotionElm, Level2ChargeMotionElm, Level3ChargeMotionElm, Level4ChargeMotionElm;
    String SelectedStyle;
    Float SelectedChargeModifier1Atk, SelectedChargeModifier2Atk, SelectedChargeModifier3Atk,
            SelectedChargeModifier4Atk;
    Float SelectedChargeModifier1Elm, SelectedChargeModifier2Elm, SelectedChargeModifier3Elm,
            SelectedChargeModifier4Elm;

    Float[][] MotionGroup;
    Float[] MeleeMotion = {0.1f, 0.24f};
    String[] MeleeAttackName = {"Melee Attack 1", "Melee Attack 2"};

    Float[] BladeWire = {0.12f, 0.15f, 0.18f, 0.21f};
    Float[] TripleVolley = {1.27f, 1.6f, 1.99f};
    Float[][] HunterArts = {BladeWire, TripleVolley};
    Float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Level I", "Level II", "Level III"};
    String[] DefaultLevels = {"Charge Level 1", "Charge Level 2", "Charge Level 3", "Charge Level 4"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bow_calculation);
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

        CoatingSelect = (Spinner) findViewById(R.id.CoatingSelect);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.BowCoatings,
                android.R.layout.simple_spinner_dropdown_item);

        CoatingSelect.setAdapter(adapter2);

        CoatingSelect.setOnItemSelectedListener(this);

        Level1ShotSelect = (Spinner) findViewById(R.id.Level1ShotSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.BowChargeLevels1,
                android.R.layout.simple_spinner_dropdown_item);

        Level1ShotSelect.setAdapter(adapter3);

        Level1ShotSelect.setOnItemSelectedListener(this);

        Level2ShotSelect = (Spinner) findViewById(R.id.Level2ShotSelect);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.BowChargeLevels2,
                android.R.layout.simple_spinner_dropdown_item);

        Level2ShotSelect.setAdapter(adapter4);

        Level2ShotSelect.setOnItemSelectedListener(this);

        Level3ShotSelect = (Spinner) findViewById(R.id.Level3ShotSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.BowChargeLevels3,
                android.R.layout.simple_spinner_dropdown_item);

        Level3ShotSelect.setAdapter(adapter5);

        Level3ShotSelect.setOnItemSelectedListener(this);

        Level4ShotSelect = (Spinner) findViewById(R.id.Level4ShotSelect);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this,R.array.BowChargeLevels4,
                android.R.layout.simple_spinner_dropdown_item);

        Level4ShotSelect.setAdapter(adapter6);

        Level4ShotSelect.setOnItemSelectedListener(this);

        ArcShotSelect = (Spinner) findViewById(R.id.ArcShotSelect);

        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this,R.array.ArcShots,
                android.R.layout.simple_spinner_dropdown_item);

        ArcShotSelect.setAdapter(adapter7);

        ArcShotSelect.setOnItemSelectedListener(this);

        DistanceSelect = (Spinner) findViewById(R.id.DistanceSelect);

        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this,R.array.BowDistance,
                android.R.layout.simple_spinner_dropdown_item);

        DistanceSelect.setAdapter(adapter8);

        DistanceSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        ElementSelect = (Spinner) findViewById(R.id.ElementSelect);

        ArrayAdapter adapter9 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(adapter9);

        ElementSelect.setOnItemSelectedListener(this);

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter10 = ArrayAdapter.createFromResource(this,R.array.Bow_HA_Names,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(adapter10);

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
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible_Bow),
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
        RepeatOffenderCheck = (CheckBox) findViewById(R.id.RepeatOffenderCheckBox);
        CriticalBoostCheck = (CheckBox) findViewById(R.id.CriticalBoostCheckBox);
        ElementalCritCheck = (CheckBox) findViewById(R.id.ElementalCritCheckBox);
        ElementalAtkUpCheck = (CheckBox) findViewById(R.id.ElementalAtkUpCheckBox);
        FelyneTemperCheck = (CheckBox) findViewById(R.id.FelyneTemperCheckBox);
        AirborneCheck = (CheckBox) findViewById(R.id.AirborneCheckBox);

        NormalUpCheck = (CheckBox) findViewById(R.id.NormalUpCheckBox);
        HeavyUpCheck = (CheckBox) findViewById(R.id.HeavyUpCheckBox);
        PierceUpCheck = (CheckBox) findViewById(R.id.PierceUpCheckBox);
        PelletUpCheck = (CheckBox) findViewById(R.id.PelletUpCheckBox);
        TrueShotUpCheck = (CheckBox) findViewById(R.id.TrueShotUpCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);
        DragonheartCheck = findViewById(R.id.DragonheartCheckBox);


        EvasiveManeuversOffCheck = (RadioButton) findViewById(R.id.EvasiveManeuversOffCheck);
        EvasiveManeuversLevel2Check = (RadioButton) findViewById(R.id.EvasiveManeuversLevel2Check);
        EvasiveManeuversLevel3Check = (RadioButton) findViewById(R.id.EvasiveManeuversLevel3Check);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable called 'StyleSelectText' and casts it to a TextView*/
        TextView StyleSelectText = (TextView) view;
        TextView Level1SelectText = (TextView) view;
        TextView Level2SelectText = (TextView) view;
        TextView Level3SelectText = (TextView) view;
        TextView Level4SelectText = (TextView) view;
        TextView CoatingSelectText = (TextView) view;
        TextView ArcShotSelectText = (TextView) view;
        TextView DistanceSelectText = (TextView) view;

        final String Style = StyleSelectText.getText().toString();
        if (Style.equals("Guild")) {
            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Guild";
        } else if (Style.equals("Striker")) {
            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Striker";
        } else if (Style.equals("Aerial")) {
            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Aerial";
        } else if (Style.equals("Adept")) {
            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Adept";
        } else if (Style.equals("Alchemy")) {
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Alchemy";
        } else if (Style.equals("Valor")) {
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Valor";
        }

        String Level1Charge = Level1SelectText.getText().toString();
        if (Level1Charge.equals("[1]Rapid Level 1")) {
            Level1ChargeMotionAtk = 0.12f;
            Level1ChargeMotionElm = 0.13f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Rapid Level 2")) {
            Level1ChargeMotionAtk = 0.16f;
            Level1ChargeMotionElm = 0.14f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Rapid Level 3")) {
            Level1ChargeMotionAtk = 0.19f;
            Level1ChargeMotionElm = 0.15f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Rapid Level 4")) {
            Level1ChargeMotionAtk = 0.21f;
            Level1ChargeMotionElm = 0.16f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Rapid Level 5")) {
            Level1ChargeMotionAtk = 0.22f;
            Level1ChargeMotionElm = 0.16f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Spread Level 1")) {
            Level1ChargeMotionAtk = 0.14f;
            Level1ChargeMotionElm = 0.15f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Spread Level 2")) {
            Level1ChargeMotionAtk = 0.16f;
            Level1ChargeMotionElm = 0.18f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Spread Level 3")) {
            Level1ChargeMotionAtk = 0.23f;
            Level1ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Spread Level 4")) {
            Level1ChargeMotionAtk = 0.24f;
            Level1ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Spread Level 5")) {
            Level1ChargeMotionAtk = 0.26f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Pierce Level 1")) {
            Level1ChargeMotionAtk = 0.18f;
            Level1ChargeMotionElm = 0.15f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Pierce Level 2")) {
            Level1ChargeMotionAtk = 0.24f;
            Level1ChargeMotionElm = 0.16f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Pierce Level 3")) {
            Level1ChargeMotionAtk = 0.3f;
            Level1ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Pierce Level 4")) {
            Level1ChargeMotionAtk = 0.3f;
            Level1ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Pierce Level 5")) {
            Level1ChargeMotionAtk = 0.3f;
            Level1ChargeMotionElm = 0.2f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 0;
        } else if (Level1Charge.equals("[1]Heavy Level 1")) {
            Level1ChargeMotionAtk = 0.11f;
            Level1ChargeMotionElm = 0.14f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 1;
        } else if (Level1Charge.equals("[1]Heavy Level 2")) {
            Level1ChargeMotionAtk = 0.14f;
            Level1ChargeMotionElm = 0.15f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 1;
        } else if (Level1Charge.equals("[1]Heavy Level 3")) {
            Level1ChargeMotionAtk = 0.17f;
            Level1ChargeMotionElm = 0.16f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 1;
        } else if (Level1Charge.equals("[1]Heavy Level 4")) {
            Level1ChargeMotionAtk = 0.19f;
            Level1ChargeMotionElm = 0.17f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 1;
        } else if (Level1Charge.equals("[1]Heavy Level 5")) {
            Level1ChargeMotionAtk = 0.2f;
            Level1ChargeMotionElm = 0.18f;
            SelectedChargeModifier1Atk = 0.4f;
            SelectedChargeModifier1Elm = 0.7f;
            HeavyCheck[0] = 1;
        }

        String Level2Charge = Level2SelectText.getText().toString();
        if (Level2Charge.equals("[2]Rapid Level 1")) {
            Level2ChargeMotionAtk = 0.12f;
            Level2ChargeMotionElm = 0.13f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Rapid Level 2")) {
            Level2ChargeMotionAtk = 0.16f;
            Level2ChargeMotionElm = 0.14f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Rapid Level 3")) {
            Level2ChargeMotionAtk = 0.19f;
            Level2ChargeMotionElm = 0.15f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Rapid Level 4")) {
            Level2ChargeMotionAtk = 0.21f;
            Level2ChargeMotionElm = 0.16f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Rapid Level 5")) {
            Level2ChargeMotionAtk = 0.22f;
            Level2ChargeMotionElm = 0.16f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Spread Level 1")) {
            Level2ChargeMotionAtk = 0.14f;
            Level2ChargeMotionElm = 0.15f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Spread Level 2")) {
            Level2ChargeMotionAtk = 0.16f;
            Level2ChargeMotionElm = 0.18f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Spread Level 3")) {
            Level2ChargeMotionAtk = 0.23f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Spread Level 4")) {
            Level2ChargeMotionAtk = 0.24f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Spread Level 5")) {
            Level2ChargeMotionAtk = 0.26f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Pierce Level 1")) {
            Level2ChargeMotionAtk = 0.18f;
            Level2ChargeMotionElm = 0.15f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Pierce Level 2")) {
            Level2ChargeMotionAtk = 0.24f;
            Level2ChargeMotionElm = 0.16f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Pierce Level 3")) {
            Level2ChargeMotionAtk = 0.3f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Pierce Level 4")) {
            Level2ChargeMotionAtk = 0.3f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Pierce Level 5")) {
            Level2ChargeMotionAtk = 0.3f;
            Level2ChargeMotionElm = 0.2f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 0;
        } else if (Level2Charge.equals("[2]Heavy Level 1")) {
            Level2ChargeMotionAtk = 0.11f;
            Level2ChargeMotionElm = 0.14f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 1;
        } else if (Level2Charge.equals("[2]Heavy Level 2")) {
            Level2ChargeMotionAtk = 0.14f;
            Level2ChargeMotionElm = 0.15f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 1;
        } else if (Level2Charge.equals("[2]Heavy Level 3")) {
            Level2ChargeMotionAtk = 0.17f;
            Level2ChargeMotionElm = 0.16f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 1;
        } else if (Level2Charge.equals("[2]Heavy Level 4")) {
            Level2ChargeMotionAtk = 0.19f;
            Level2ChargeMotionElm = 0.17f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 1;
        } else if (Level2Charge.equals("[2]Heavy Level 5")) {
            Level2ChargeMotionAtk = 0.2f;
            Level2ChargeMotionElm = 0.18f;
            SelectedChargeModifier2Atk = 1f;
            SelectedChargeModifier2Elm = 0.85f;
            HeavyCheck[1] = 1;
        }

        String Level3Charge = Level3SelectText.getText().toString();
        if (Level3Charge.equals("[3]Rapid Level 1")) {
            Level3ChargeMotionAtk = 0.12f;
            Level3ChargeMotionElm = 0.13f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Rapid Level 2")) {
            Level3ChargeMotionAtk = 0.16f;
            Level3ChargeMotionElm = 0.14f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Rapid Level 3")) {
            Level3ChargeMotionAtk = 0.19f;
            Level3ChargeMotionElm = 0.15f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Rapid Level 4")) {
            Level3ChargeMotionAtk = 0.21f;
            Level3ChargeMotionElm = 0.16f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Rapid Level 5")) {
            Level3ChargeMotionAtk = 0.22f;
            Level3ChargeMotionElm = 0.16f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Spread Level 1")) {
            Level3ChargeMotionAtk = 0.14f;
            Level3ChargeMotionElm = 0.15f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Spread Level 2")) {
            Level3ChargeMotionAtk = 0.16f;
            Level3ChargeMotionElm = 0.18f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Spread Level 3")) {
            Level3ChargeMotionAtk = 0.23f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Spread Level 4")) {
            Level3ChargeMotionAtk = 0.24f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Spread Level 5")) {
            Level3ChargeMotionAtk = 0.26f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Pierce Level 1")) {
            Level3ChargeMotionAtk = 0.18f;
            Level3ChargeMotionElm = 0.15f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Pierce Level 2")) {
            Level3ChargeMotionAtk = 0.24f;
            Level3ChargeMotionElm = 0.16f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Pierce Level 3")) {
            Level3ChargeMotionAtk = 0.3f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Pierce Level 4")) {
            Level3ChargeMotionAtk = 0.3f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Pierce Level 5")) {
            Level3ChargeMotionAtk = 0.3f;
            Level3ChargeMotionElm = 0.2f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 0;
        } else if (Level3Charge.equals("[3]Heavy Level 1")) {
            Level3ChargeMotionAtk = 0.11f;
            Level3ChargeMotionElm = 0.14f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 1;
        } else if (Level3Charge.equals("[3]Heavy Level 2")) {
            Level3ChargeMotionAtk = 0.14f;
            Level3ChargeMotionElm = 0.15f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 1;
        } else if (Level3Charge.equals("[3]Heavy Level 3")) {
            Level3ChargeMotionAtk = 0.17f;
            Level3ChargeMotionElm = 0.16f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 1;
        } else if (Level3Charge.equals("[3]Heavy Level 4")) {
            Level3ChargeMotionAtk = 0.19f;
            Level3ChargeMotionElm = 0.17f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 1;
        } else if (Level3Charge.equals("[3]Heavy Level 5")) {
            Level3ChargeMotionAtk = 0.2f;
            Level3ChargeMotionElm = 0.18f;
            SelectedChargeModifier3Atk = 1.5f;
            SelectedChargeModifier3Elm = 1f;
            HeavyCheck[2] = 1;
        } else if (Level3Charge.equals("[3]None")) {
            Level3ChargeMotionAtk = 0f;
            Level3ChargeMotionElm = 0f;
            SelectedChargeModifier4Atk = 0f;
            SelectedChargeModifier3Elm = 0f;
            HeavyCheck[2] = 0;
        }

        String Level4Charge = Level4SelectText.getText().toString();
        if (Level4Charge.equals("[4]Rapid Level 1")) {
            Level4ChargeMotionAtk = 0.12f;
            Level4ChargeMotionElm = 0.13f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Rapid Level 2")) {
            Level4ChargeMotionAtk = 0.16f;
            Level4ChargeMotionElm = 0.14f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Rapid Level 3")) {
            Level4ChargeMotionAtk = 0.19f;
            Level4ChargeMotionElm = 0.15f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Rapid Level 4")) {
            Level4ChargeMotionAtk = 0.21f;
            Level4ChargeMotionElm = 0.16f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Rapid Level 5")) {
            Level4ChargeMotionAtk = 0.22f;
            Level4ChargeMotionElm = 0.16f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Spread Level 1")) {
            Level4ChargeMotionAtk = 0.14f;
            Level4ChargeMotionElm = 0.15f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Spread Level 2")) {
            Level4ChargeMotionAtk = 0.16f;
            Level4ChargeMotionElm = 0.18f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Spread Level 3")) {
            Level4ChargeMotionAtk = 0.23f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Spread Level 4")) {
            Level4ChargeMotionAtk = 0.24f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Spread Level 5")) {
            Level4ChargeMotionAtk = 0.26f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Pierce Level 1")) {
            Level4ChargeMotionAtk = 0.18f;
            Level4ChargeMotionElm = 0.15f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Pierce Level 2")) {
            Level4ChargeMotionAtk = 0.24f;
            Level4ChargeMotionElm = 0.16f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Pierce Level 3")) {
            Level4ChargeMotionAtk = 0.3f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Pierce Level 4")) {
            Level4ChargeMotionAtk = 0.3f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Pierce Level 5")) {
            Level4ChargeMotionAtk = 0.3f;
            Level4ChargeMotionElm = 0.2f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 0;
        } else if (Level4Charge.equals("[4]Heavy Level 1")) {
            Level4ChargeMotionAtk = 0.11f;
            Level4ChargeMotionElm = 0.14f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 1;
        } else if (Level4Charge.equals("[4]Heavy Level 2")) {
            Level4ChargeMotionAtk = 0.14f;
            Level4ChargeMotionElm = 0.15f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 1;
        } else if (Level4Charge.equals("[4]Heavy Level 3")) {
            Level4ChargeMotionAtk = 0.17f;
            Level4ChargeMotionElm = 0.16f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 1;
        } else if (Level4Charge.equals("[4]Heavy Level 4")) {
            Level4ChargeMotionAtk = 0.19f;
            Level4ChargeMotionElm = 0.17f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 1;
        } else if (Level4Charge.equals("[4]Heavy Level 5")) {
            Level4ChargeMotionAtk = 0.2f;
            Level4ChargeMotionElm = 0.18f;
            SelectedChargeModifier4Atk = 1.7f;
            SelectedChargeModifier4Elm = 1.125f;
            HeavyCheck[3] = 1;
        } else if (Level4Charge.equals("[4]None")) {
            Level4ChargeMotionAtk = 0f;
            Level4ChargeMotionElm = 0f;
            SelectedChargeModifier4Atk = 0f;
            SelectedChargeModifier4Elm = 0f;
            HeavyCheck[3] = 0;
        }

        MotionAtk = new Float[]{Level1ChargeMotionAtk, Level2ChargeMotionAtk, Level3ChargeMotionAtk,
                Level4ChargeMotionAtk};
        MotionElm = new Float[]{Level1ChargeMotionElm, Level2ChargeMotionElm, Level3ChargeMotionElm,
                Level4ChargeMotionElm};

        ChargeModifierAtk = new Float[]{SelectedChargeModifier1Atk, SelectedChargeModifier2Atk,
                SelectedChargeModifier3Atk, SelectedChargeModifier4Atk};
        ChargeModifierElm = new Float[]{SelectedChargeModifier1Elm, SelectedChargeModifier2Elm,
                SelectedChargeModifier3Elm, SelectedChargeModifier4Elm};

        final String Coating = CoatingSelectText.getText().toString();
        if (Coating.equals("Power Lv1")) {
            Skills.setCoatingModifier(1.35f, 1);
        } else if (Coating.equals("Power Lv2")) {
            Skills.setCoatingModifier(1.5f, 1);
        } else if (Coating.equals("Elemental Lv1")) {
            Skills.setCoatingModifier(1, 1.35f);
        } else if (Coating.equals("Elemental Lv2")) {
            Skills.setCoatingModifier(1, 1.5f);
        } else if (Coating.equals("No Coating")) {
            Skills.setCoatingModifier(1, 1);
        }

        final String ArcShotText = ArcShotSelectText.getText().toString();
        if (ArcShotText.equals("None")) {
            getArcShot("None");
            ArcShotMotion = 0f;
        } else if (ArcShotText.equals("Blast")) {
            getArcShot("Blast");
            ArcShotMotion = 0.41f;
        } else if (ArcShotText.equals("Focus")) {
            getArcShot("Focus");
            ArcShotMotion = 0.35f;
        } else if (ArcShotText.equals("Wide")) {
            getArcShot("Wide");
            ArcShotMotion = 0.35f;
        }

        final String DistanceText = DistanceSelectText.getText().toString();
        if (DistanceText.equals("Normal/Too Close")) {
            getDistanceModifier(1f);
        } else if (DistanceText.equals("Critical")) {
            getDistanceModifier(1.5f);
        } else if (DistanceText.equals("Far")) {
            getDistanceModifier(0.8f);
        } else if (DistanceText.equals("Too Far")) {
            getDistanceModifier(0.5f);
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
        if(ArtText.equals("Blade Wire")){
            getHunterArt("Blade Wire");
        }
        else if(ArtText.equals("Triple Volley")){
            getHunterArt("Triple Volley");
        }
        else if(ArtText.equals("Round Force")) {
            getHunterArt("Round Force");
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
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Might Pill (+25)")) {
            Skills.setGroupD(25f);
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Exciteshroom - Mycology (+10)")) {
            Skills.setGroupD(10f);
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Demon Horn (+10)")) {
            Skills.setGroupD(10f);
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("Demon S (+10)")) {
            Skills.setGroupD(10f);
            Skills.setGroupDCrit(0f);
            Snackbar.make(view, "10% Sharpness Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupDSkillsText.equals("Demon Affinity S (+15)")) {
            Skills.setGroupD(15f);
            Skills.setGroupDCrit(10f);
            Snackbar.make(view, "10% Sharpness Increase\n10% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupDSkillsText.equals("Cool Cat (+15)")) {
            Skills.setGroupD(15f);
            Skills.setGroupDCrit(0f);
        }
        else if(GroupDSkillsText.equals("-Group D-")) {
            Skills.setGroupD(0f);
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
                        getSystemService(BowCalculation.this.INPUT_METHOD_SERVICE);

                try{
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TextViewIDsAttacks = new String[]{"BAttack_1", "BAttack_2", "BAttack_3",
                        "BAttack_4", "BAttack_5", "BAttack_6", "BAttack_7",
                        "BAttack_8", "BAttack_9", "BAttack_10"};

                TextViewIDsNames = new String[]{"BAttack_1_Name", "BAttack_2_Name", "BAttack_3_Name",
                        "BAttack_4_Name", "BAttack_5_Name", "BAttack_6_Name", "BAttack_7_Name",
                        "BAttack_8_Name", "BAttack_9_Name", "BAttack_10_Name"};

                AllTextViewIDs = new String[]{"BAttack_1_Name", "BAttack_2_Name", "BAttack_3_Name",
                        "BAttack_4_Name", "BAttack_5_Name", "BAttack_6_Name", "BAttack_7_Name",
                        "BAttack_8_Name", "BAttack_9_Name", "BAttack_10_Name", "BAttack_1",
                        "BAttack_2", "BAttack_3", "BAttack_4", "BAttack_5", "BAttack_6",
                        "BAttack_7", "BAttack_8", "BAttack_9", "BAttack_10"};

                for (int i = 0; i < AllTextViewIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
                    textviews[i].setVisibility(View.GONE);
                }

                RelativeLayout Info = (RelativeLayout) findViewById(R.id.AttackInfo);
                TextView ArcShotBanner = (TextView) findViewById(R.id.ArcShotBanner);
                Info.setVisibility(View.GONE);
                ArcShotBanner.setVisibility(View.GONE);

                if ((SelectedStyle.equals("Aerial") && !ArcShot.equals("None")) ||
                        (SelectedStyle.equals("Adept") && !ArcShot.equals("None"))) {
                    Toast.makeText(BowCalculation.this, SelectedStyle + " Style doesn't have the Arc Shot",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Spinner Level1SelectText = (Spinner) findViewById(R.id.Level1ShotSelect);
                Spinner Level2SelectText = (Spinner) findViewById(R.id.Level2ShotSelect);
                Spinner Level3SelectText = (Spinner) findViewById(R.id.Level3ShotSelect);
                Spinner Level4SelectText = (Spinner) findViewById(R.id.Level4ShotSelect);

                String Level1Charge = Level1SelectText.getSelectedItem().toString();
                String Level2Charge = Level2SelectText.getSelectedItem().toString();
                String Level3Charge = Level3SelectText.getSelectedItem().toString();
                String Level4Charge = Level4SelectText.getSelectedItem().toString();

                String [] Levels = {Level1Charge, Level2Charge, Level3Charge, Level4Charge};

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                int[] HunterArtElementCheck = {1};
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                    if(ChosenArt.equals("Blade Wire")){
                        ChosenHunterArt = HunterArts[0];
                        HunterArtElementCheck = new int[] {0, 0, 0};
                    }
                    else if(ChosenArt.equals("Triple Volley")){
                        ChosenHunterArt = HunterArts[1];
                        HunterArtElementCheck = new int[] {3, 3, 3};
                    }
                }
                //-End-

                MotionGroup = new Float[][] {MotionAtk, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawAffinity = 0;

                TextView Damage = (TextView) findViewById(R.id.DamageInputB);
                TextView Element = (TextView) findViewById(R.id.ElementInputB);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputB);

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
                Skills.setAirborneModifier(AirborneCheck.isChecked());
                Skills.setDragonHeartModifier(DragonheartCheck.isChecked());

                Skills.setElementAtkUp(ElementalAtkUpCheck.isChecked());
                Skills.setElementCrit(ElementalCritCheck.isChecked(), RawAffinity);

                if(EvasiveManeuversLevel2Check.isChecked()){
                    Skills.setEvasiveManeuversModifier(EvasiveManeuversLevel2Check.isChecked(),2);
                }
                else if(EvasiveManeuversLevel3Check.isChecked()){
                    Skills.setEvasiveManeuversModifier(EvasiveManeuversLevel3Check.isChecked(),3);
                }
                else if(EvasiveManeuversOffCheck.isChecked()){
                    Skills.setEvasiveManeuversModifier(!EvasiveManeuversOffCheck.isChecked(),0);
                }

                if (Skills.CheckElmSkill(RawElement, SkillCheck)){
                    Snackbar.make(view, "Please check your inputted element/skills", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                MonsterCalculation M;
                /*'M' is the new variable in this block of code and is used to call the
                 'MonsterCalculation' class. The calculations for hitzones will be done inside that
                 class.*/
                M = new MonsterCalculation(BowCalculation.this,
                        ChosenMonster + "RawHitzones_Shot",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);

                M.getHitzones(BowCalculation.this, ChosenElement, Skills, WeaknessExploitCheck.isChecked());

                float TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck);

                //Standard Shot Calculation
                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    Skills.setShotModifier(1);
                    if ((MotionAtk[i] == 0f && MotionCheck == 0) || (ChosenArt.equals("Blade Wire") && MotionAtk[i] == 0f)) {
                        i = 3;
                        continue;
                    }
                    else {
                        if (HeavyCheck[i] == 1 && (DistanceModifier == 0.5f || DistanceModifier == 0.8f)) {
                            getDistanceModifier(1.5f);
                        }

                        if(MotionCheck == 0){
                            if((Levels[i].contains("Heavy") && HeavyUpCheck.isChecked()) ||
                                    (Levels[i].contains("Pierce") && PierceUpCheck.isChecked()) ||
                                    (Levels[i].contains("Rapid") && NormalUpCheck.isChecked())){
                                Skills.setShotModifier(1.1f);
                            }else if((Levels[i].contains("Spread") && PelletUpCheck.isChecked())){
                                Skills.setShotModifier(1.3f);
                            }else{
                                Skills.setShotModifier(1);
                            }

                            TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * MotionAtk[i];
                        }
                        else{
                            TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ChosenHunterArt[i];
                        }

                        float HitzoneRaw;
                        float HitzoneElm;

                        if(ChosenArt.equals("Blade Wire") && MotionCheck == 1){
                            HitzoneRaw = ((TrueRaw * ChargeModifierAtk[i]) * DistanceModifier) * M.getRawHitzoneValue() / 100;
                            HitzoneElm = 0;
                        }
                        else if(ChosenArt.equals("Triple Volley") && MotionCheck ==1){
                            HitzoneRaw = ((TrueRaw * DistanceModifier) * M.getRawHitzoneValue()) / 100;
                            HitzoneElm = ((Skills.getTrueElm(RawElement, SkillCheck) * M.getElmHitzoneValue()) / 100) *
                                    HunterArtElementCheck[i];
                        }
                        else{
                            HitzoneElm = ((Skills.getTrueElm(RawElement, SkillCheck) * ChargeModifierElm[i]) * M.getElmHitzoneValue()) / 100;
                            HitzoneRaw = ((TrueRaw * ChargeModifierAtk[i]) * DistanceModifier) * M.getRawHitzoneValue() / 100;
                        }

                        //Hitzone Modification - End

                        float TrueAttack = HitzoneRaw + HitzoneElm;

                        textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                        textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                        textviews[i].setVisibility(View.VISIBLE);
                        /*Sets the current textview to the id value of 'Counter' and then sets that
                        textviews value the value of 'test's current value. It also sets the
                        visibility of all the used textboxes to 'visible'.*/

                        TextView AttackBanner = (TextView) findViewById(R.id.AttackBanner);
                        textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                        if(MotionCheck == 0){
                            AttackBanner.setText("Attack");
                        }
                        else{
                            textviews[i].setText(HunterArtsLevels[i]);
                            AttackBanner.setText(ChosenArt);
                            AttackBanner.setVisibility(View.VISIBLE);
                            if(ChosenArt.equals("Blade Wire")){
                                Snackbar.make(view, "Blade Wire has the same amount of charges as" +
                                        " your standard shots", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                        textviews[i].setText(DefaultLevels[i]);
                        textviews[i].setVisibility(View.VISIBLE);
                        /*Sets the current textview to the id value of 'Counter' and then sets that
                        textviews value the value of 'test's current value. It also sets the
                        visibility of all the used textboxes to 'visible'.*/
                    }
                    TextView StaggerBanner = (TextView) findViewById(R.id.StaggerBanner);
                    if(!ChosenMonster.equals("None")){
                        StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                        StaggerBanner.setVisibility(View.VISIBLE);
                    }

                    getForLoopCarry(i);
                }

                Info.setVisibility(View.VISIBLE);

                if(MotionCheck == 1){
                    return;
                }
                if (ForLoopCarry != 4) {
                    getForLoopCarry(4);
                }

                //Aerial Shot Calculation
                if((Levels[1].contains("Heavy") && HeavyUpCheck.isChecked()) ||
                        (Levels[1].contains("Pierce") && PierceUpCheck.isChecked()) ||
                        (Levels[1].contains("Rapid") && NormalUpCheck.isChecked())){
                    Skills.setShotModifier(1.1f);
                }else if((Levels[1].contains("Spread") && PelletUpCheck.isChecked()) &&
                        TrueShotUpCheck.isChecked()){
                    Skills.setShotModifier(1.3f);
                }

                //Hitzone Modification - Start

                M.alterHitzones(BowCalculation.this, ChosenMonster, Level2Charge, "Bow", false);

                float HitzoneRaw = (((TrueRaw * MotionAtk[1]) * ChargeModifierAtk[1] * 1.5f) * M.getRawHitzoneValue()) / 100;

                float HitzoneElm = ((Skills.getTrueElm(RawElement, SkillCheck) * ChargeModifierElm[1]) * M.getElmHitzoneValue()) / 100;

                //Hitzone Modification - End

                float TrueAttack = HitzoneRaw + HitzoneElm;


                textviews[ForLoopCarry] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ForLoopCarry], "id", getPackageName()));
                textviews[ForLoopCarry].setText(String.format("%s", Math.round(TrueAttack)));
                textviews[ForLoopCarry].setVisibility(View.VISIBLE);

                textviews[ForLoopCarry] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ForLoopCarry], "id", getPackageName()));
                if (SelectedStyle.equals("Aerial")) {
                    textviews[ForLoopCarry].setText("Vault Attack (Always Critical Distance)");
                } else {
                    textviews[ForLoopCarry].setText("Jump Attack (Always Critical Distance)");
                }
                textviews[ForLoopCarry].setVisibility(View.VISIBLE);
                /*Sets the current textview to the id value of 'Counter' and then sets that
                textviews value the value of 'test's current value. It also sets the
                visibility of all the used textboxes to 'visible'.*/

                getForLoopCarry(ForLoopCarry + 1);
                Skills.setShotModifier(1);
                TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck);
                //Melee Calculation
                for (int i = 0; i < MeleeMotion.length; i++) {
                    textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ForLoopCarry + i], "id", getPackageName()));

                    M.alterHitzones(BowCalculation.this, ChosenMonster, "", "Bow", true);

                    HitzoneRaw = TrueRaw * MeleeMotion[i] * M.getRawHitzoneValue() / 100;

                    HitzoneElm = RawElement * M.getElmHitzoneValue() / 100;

                    float MeleeAttack = HitzoneRaw + HitzoneElm;

                    textviews[ForLoopCarry + i].setText(String.format("%s", Math.round(MeleeAttack)));
                    textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);

                    textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ForLoopCarry + i], "id", getPackageName()));
                    textviews[ForLoopCarry + i].setText(MeleeAttackName[i]);
                    textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/

                    getForLoopCarry(ForLoopCarry + i);
                }
                getForLoopCarry(ForLoopCarry + 1);

                //Arc Shot Calculation
                float ArcShotAttack;
                int Charge;
                TextView PowerShotBanner = (TextView) findViewById(R.id.PowerShotBanner);
                if (!ArcShot.equals("None")) {
                    if(TrueShotUpCheck.isChecked()) Skills.setShotModifier(1.2f);
                    if (SelectedStyle.equals("Striker") || SelectedStyle.equals("Alchemy")) {
                        PowerShotBanner.setVisibility(View.GONE);
                        ArcShotBanner.setVisibility(View.VISIBLE);

                        for (int i = 0; i < MotionAtk.length; i++) {
                            try {
                                textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ForLoopCarry + i], "id", getPackageName()));

                                TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ArcShotMotion;

                                HitzoneRaw = ((TrueRaw * ChargeModifierAtk[1]) * M.getRawHitzoneValue()) / 100;;

                                HitzoneElm = ((RawElement * ChargeModifierElm[1]) * M.getElmHitzoneValue()) / 100;

                                ArcShotAttack = HitzoneRaw + HitzoneElm;

                                textviews[ForLoopCarry + i].setText(String.format("%s", Math.round(ArcShotAttack)));
                                textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);

                                textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ForLoopCarry + i], "id", getPackageName()));
                                Charge = i + 1;
                                String ArcShotFull = ArcShot + " Level " + Integer.toString(Charge) + " Charge";
                                textviews[ForLoopCarry + i].setText(ArcShotFull);
                                textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                return;
                            }
                        }
                    }
                    else {
                        PowerShotBanner.setVisibility(View.VISIBLE);
                        ArcShotBanner.setVisibility(View.VISIBLE);

                        for (int i = 0; i < MotionAtk.length; i++) {
                            try {
                                textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[ForLoopCarry + i], "id", getPackageName()));
                                TrueRaw = Skills.getTrueRaw(RawDamage, RawAffinity, SkillCheck) * ArcShotMotion;

                                HitzoneRaw = ((TrueRaw * ChargeModifierAtk[i]) * M.getRawHitzoneValue()) / 100;

                                HitzoneElm = ((RawElement * ChargeModifierElm[i]) * M.getElmHitzoneValue()) / 100;;

                                ArcShotAttack = HitzoneRaw + HitzoneElm;
                                textviews[ForLoopCarry + i].setText(String.format("%s", Math.round(ArcShotAttack)));
                                textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);

                                textviews[ForLoopCarry + i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[ForLoopCarry + i], "id", getPackageName()));
                                Charge = i + 1;
                                String ArcShotFull = ArcShot + " Level " + Integer.toString(Charge) + " Charge";
                                textviews[ForLoopCarry + i].setText(ArcShotFull);
                                textviews[ForLoopCarry + i].setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                return;
                            }
                        }
                    }
                }
                else if(SelectedStyle.equals("Adept") || SelectedStyle.equals("Guild")){
                    if (SelectedStyle.equals("Adept")) {
                        ChargeLevel = 0;
                        for (int i = 0; i < MotionAtk.length; i++) {
                            if (!MotionAtk[i].equals(0f)){
                                ChargeLevel++;
                            }
                        }
                        if (ChargeLevel == 4){
                            ChargeLevel = 3;
                        }
                        TextView AdeptBanner = (TextView) findViewById(R.id.AdeptBanner);
                        AdeptBanner.setText("Adept Evade leads to a Level " + ChargeLevel + " Charge.");
                        AdeptBanner.setVisibility(View.VISIBLE);
                        PowerShotBanner.setVisibility(View.VISIBLE);
                    }
                    else {
                        PowerShotBanner.setVisibility(View.VISIBLE);
                    }
                }
                else if(SelectedStyle.equals("Alchemy")){
                    PowerShotBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    public static float getDistanceModifier(float i) {
        DistanceModifier = i;
        return DistanceModifier;
    }
    public static String getArcShot(String i) {
        ArcShot = i;
        return ArcShot;
    }
    public static int getForLoopCarry(int i) {
        ForLoopCarry = i;
        return ForLoopCarry;
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