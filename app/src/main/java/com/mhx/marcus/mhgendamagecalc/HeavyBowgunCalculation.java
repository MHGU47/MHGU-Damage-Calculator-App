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
import android.widget.RadioGroup;
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


public class HeavyBowgunCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, SelectedShot, DistanceSelectSpinner, MonsterSelect, HitzoneSelect, ElementSelect, HunterArtSelect;
    Button Calculate;
    CheckBox AerialShotSelect;
    static float DistanceModifier;
    String SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenDistance, ChosenArt;
    float GroupDCritResrve, GroupJCritResrve, GroupOCritResrve;

    /*Creates a string/number variables which are arrays.*/
    String[] TextViewIDsAttacks, TextViewIDsNames, AllTextViewIDs;
    Float[] MotionAtk = {0f, 0f, 0f, 0f};
    String[] MotionName = {"N/A","N/A","N/A","N/A"};
    String SelectedStyle, ShotType;

    //Skill and Hunter Art Selection variables - Start
    Switch SkillSwitch;
    Boolean SkillCheck = false, GroupPCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect, GroupSSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck,
            FelyneTemperCheck, FelyneSharpshooterCheck, WeaknessExploitCheck, AirborneCheck,
            FelyneBombardierCheck, GunpowderInfusionCheck, PowerReloadCheck, DragonheartCheck;
    RadioButton NoShotUpRadio, NormalUpRadio, PelletUpRadio, PierceUpRadio, HeavyUpRadio,TrueShotUpRadio;

    //-End-

    int[] SuperNova = {100, 30, 130, 40, 175, 50};
    float[] SlicingShell = {0.9f, 1.2f, 1.7f};
    String[] HunterArtsLevels_SN = {"Level I - Fixed Damage (Centre)", "Level I - Fixed Damage (Edge)",
            "Level II - Fixed Damage (Centre)", "Level II - Fixed Damage (Edge)",
            "Level III - Fixed Damage (Centre)", "Level III - Fixed Damage (Edge)"};
    String[] HunterArtsLevels_SS = {"Level I", "Level II", "Level III"};
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heavy_bowgun_calculation);
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

        SelectedShot = (Spinner) findViewById(R.id.ShotSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.BGAmmo,
                android.R.layout.simple_spinner_dropdown_item);

        SelectedShot.setAdapter(adapter3);

        SelectedShot.setOnItemSelectedListener(this);

        DistanceSelectSpinner = (Spinner) findViewById(R.id.DistanceSelect);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.BowDistance,
                android.R.layout.simple_spinner_dropdown_item);

        DistanceSelectSpinner.setAdapter(adapter4);

        DistanceSelectSpinner.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter10 = ArrayAdapter.createFromResource(this,R.array.HBG_HA_Names,
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

        GroupSSelect = (Spinner) findViewById(R.id.GroupSSelect);

        ArrayAdapter GroupSAdapter = ArrayAdapter.createFromResource(this,R.array.GroupS,
                android.R.layout.simple_spinner_dropdown_item);

        GroupSSelect.setAdapter(GroupSAdapter);

        GroupSSelect.setOnItemSelectedListener(this);

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = (Spinner) findViewById(R.id.MonsterSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(adapter6);

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
                    //int pixels = getResources().getDimensionPixelSize(R.dimen.Skills_Visible_BG);
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible_BG),
                            0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    Calculate.setLayoutParams(Margin);

                    SkillCheck = true;
                }
                else{
                    SkillsLayout.setVisibility(View.GONE);

                    Calculate = (Button) findViewById(R.id.CalculateButton);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) Calculate.getLayoutParams();
                    //int pixels = getResources().getDimensionPixelSize(R.dimen.Skills_Hidden);
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
        AirborneCheck = (CheckBox) findViewById(R.id.AirborneCheckBox);
        DragonheartCheck = findViewById(R.id.DragonheartCheckBox);
        FelyneTemperCheck = (CheckBox) findViewById(R.id.FelyneTemperCheckBox);
        FelyneSharpshooterCheck = (CheckBox) findViewById(R.id.FelyneSharpshooterCheckBox);
        FelyneBombardierCheck = (CheckBox) findViewById(R.id.FelyneBombardierCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);
        GunpowderInfusionCheck = (CheckBox) findViewById(R.id.GunpowderInfusionCheckBox);
        PowerReloadCheck = (CheckBox) findViewById(R.id.PowerReloadCheckBox);
        AerialShotSelect = (CheckBox) findViewById(R.id.AerialShotSelect);

        NoShotUpRadio = (RadioButton) findViewById(R.id.NoShotUpRadio);
        NormalUpRadio = (RadioButton) findViewById(R.id.NormalUpRadio);
        PelletUpRadio = (RadioButton) findViewById(R.id.PelletUpRadio);
        HeavyUpRadio = (RadioButton) findViewById(R.id.HeavyUpRadio);
        PierceUpRadio = (RadioButton) findViewById(R.id.PierceUpRadio);
        TrueShotUpRadio = (RadioButton) findViewById(R.id.TrueShotUpRadio);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used
    
    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*Declares a text view variable called 'StyleSelectText' and casts it to a TextView*/
        TextView StyleSelectText = (TextView) view;
        TextView SelectedShotText = (TextView) view;
        TextView DistanceSelectText = (TextView) view;

        final String Style = StyleSelectText.getText().toString();
        if (Style.equals("Guild")) {
            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Guild";
        }
        else if (Style.equals("Striker")) {
            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Striker";
        }
        else if (Style.equals("Aerial")) {
            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Aerial";
        }
        else if (Style.equals("Adept")) {
            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Adept";
        }
        else if (Style.equals("Valor")) {
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Valor";
        }
        else if (Style.equals("Alchemy")) {
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = "Alchemy";
        }

        final String DistanceText = DistanceSelectText.getText().toString();
        if (DistanceText.equals("Normal/Too Close")) {
            getDistanceModifier(1f);
            getChosenDistance(DistanceText);
        }
        else if (DistanceText.equals("Critical")) {
            getDistanceModifier(1.5f);
            getChosenDistance(DistanceText);
        }
        else if (DistanceText.equals("Far")) {
            getDistanceModifier(0.8f);
            getChosenDistance(DistanceText);
        }
        else if (DistanceText.equals("Too Far")) {
            getDistanceModifier(0.5f);
            getChosenDistance(DistanceText);
        }

        final String SelectedShot = SelectedShotText.getText().toString();
        if (SelectedShot.equals("Flaming S Lv1") || SelectedShot.equals("Freeze S Lv1") || SelectedShot.equals("Water S Lv1") || SelectedShot.equals("Thunder S Lv1")) {
            MotionAtk[0] = 0.07f;
            MotionAtk[1] = 0.45f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            if(SelectedShot.equals("Flaming S Lv1")){
                getChosenElement("Fire");
            }
            else if(SelectedShot.equals("Freeze S Lv1")){
                getChosenElement("Ice");
            }
            else if(SelectedShot.equals("Water S Lv1")){
                getChosenElement("Water");
            }
            else if(SelectedShot.equals("Thunder S Lv1")){
                getChosenElement("Thunder");
            }
        }
        else if (SelectedShot.equals("Flaming S Lv2") || SelectedShot.equals("Freeze S Lv2") || SelectedShot.equals("Water S Lv2") || SelectedShot.equals("Thunder S Lv2")) {
            MotionAtk[0] = 0.07f;
            MotionAtk[1] = 0.58f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            if(SelectedShot.equals("Flaming S Lv2")){
                getChosenElement("Fire");
            }
            else if(SelectedShot.equals("Freeze S Lv2")){
                getChosenElement("Ice");
            }
            else if(SelectedShot.equals("Water S Lv2")){
                getChosenElement("Water");
            }
            else if(SelectedShot.equals("Thunder S Lv2")){
                getChosenElement("Thunder");
            }
        }
        else if (SelectedShot.equals("Dragon S Lv1")) {
            MotionAtk[0] = 0.01f;
            MotionAtk[1] = 2f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            getChosenElement("Dragon");
        }
        else if (SelectedShot.equals("Dragon S Lv2")) {
            MotionAtk[0] = 0.01f;
            MotionAtk[1] = 2.4f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            getChosenElement("Dragon");
        }
        else if (SelectedShot.equals("P.Flaming S Lv1") || SelectedShot.equals("P.Freeze S Lv1") || SelectedShot.equals("P.Water S Lv1") || SelectedShot.equals("P.Thunder S Lv1")) {
            MotionAtk[0] = 0.06f;
            MotionAtk[1] = 0.6f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            if(SelectedShot.equals("P.Flaming S Lv1")){
                getChosenElement("Fire");
            }
            else if(SelectedShot.equals("P.Freeze S Lv1")){
                getChosenElement("Ice");
            }
            else if(SelectedShot.equals("P.Water S Lv1")){
                getChosenElement("Water");
            }
            else if(SelectedShot.equals("P.Thunder S Lv1")){
                getChosenElement("Thunder");
            }
        }
        else if (SelectedShot.equals("P.Flaming S Lv2") || SelectedShot.equals("P.Freeze S Lv2") || SelectedShot.equals("P.Water S Lv2") || SelectedShot.equals("P.Thunder S Lv2")) {
            MotionAtk[0] = 0.15f;
            MotionAtk[1] = 1.35f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Elemental";
            if(SelectedShot.equals("P.Flaming S Lv2")){
                getChosenElement("Fire");
            }
            else if(SelectedShot.equals("P.Freeze S Lv2")){
                getChosenElement("Ice");
            }
            else if(SelectedShot.equals("P.Water S Lv2")){
                getChosenElement("Water");
            }
            else if(SelectedShot.equals("P.Thunder S Lv2")){
                getChosenElement("Thunder");
            }
        }
        else if (SelectedShot.equals("Slicing S Lv1")) {
            MotionAtk[0] = 0.01f;
            MotionAtk[1] = 0.24f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Slicing";
        }
        else if (SelectedShot.equals("Slicing S Lv2")) {
            MotionAtk[0] = 0.01f;
            MotionAtk[1] = 0.4f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Slicing";
        }
        else if (SelectedShot.equals("Crag S Lv1")) {
            MotionAtk[0] = 0.03f;
            MotionAtk[1] = 25f;
            MotionAtk[2] = 0.3f;
            MotionAtk[3] = 25f;
            ShotType = "Crag";
        }
        else if (SelectedShot.equals("Crag S Lv2")) {
            MotionAtk[0] = 0.03f;
            MotionAtk[1] = 30f;
            MotionAtk[2] = 0.45f;
            MotionAtk[3] = 30f;
            ShotType = "Crag";
        }
        else if (SelectedShot.equals("Crag S Lv3")) {
            MotionAtk[0] = 0.03f;
            MotionAtk[1] = 40f;
            MotionAtk[2] = 0.6f;
            MotionAtk[3] = 40f;
            ShotType = "Crag";
        }
        else if (SelectedShot.equals("Clust S Lv1")) {
            MotionAtk[0] = 0.18f;
            MotionAtk[1] = 75f;
            MotionAtk[2] = 0.06f;
            MotionAtk[3] = 0f;
            ShotType = "Clust";
        }
        else if (SelectedShot.equals("Clust S Lv2")) {
            MotionAtk[0] = 0.24f;
            MotionAtk[1] = 100f;
            MotionAtk[2] = 0.08f;
            MotionAtk[3] = 0f;
            ShotType = "Clust";
        }
        else if (SelectedShot.equals("Clust S Lv3")) {
            MotionAtk[0] = 0.3f;
            MotionAtk[1] = 125f;
            MotionAtk[2] = 0.1f;
            MotionAtk[3] = 0f;
            ShotType = "Clust";
        }
        else if (SelectedShot.equals("Cannon S Lv1")) {
            MotionAtk[0] = 0.05f;
            MotionAtk[1] = 30f;
            MotionAtk[2] = 10f;
            MotionAtk[3] = 0f;
            ShotType = "Cannon";
        }
        else if (SelectedShot.equals("Cannon S Lv2")) {
            MotionAtk[0] = 0.07f;
            MotionAtk[1] = 40f;
            MotionAtk[2] = 15f;
            MotionAtk[3] = 0f;
            ShotType = "Cannon";
        }
        else if (SelectedShot.equals("Triblast S")) {
            MotionAtk[0] = 0.03f;
            MotionAtk[1] = 75f;
            MotionAtk[2] = 0.75f;
            MotionAtk[3] = 75f;
            ShotType = "Triblast S";
        }
        else if (SelectedShot.equals("Shrapnel S")) {
            MotionAtk[0] = 0.01f;
            MotionAtk[1] = 0.24f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Shrapnel";
        }
        else if (SelectedShot.equals("Pierce S Lv1")) {
            MotionAtk[0] = 0.3f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pierce";
        }
        else if (SelectedShot.equals("Pierce S Lv2")) {
            MotionAtk[0] = 0.36f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pierce";
        }
        else if (SelectedShot.equals("Pierce S Lv3")) {
            MotionAtk[0] = 0.4f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pierce";
        }
        else if (SelectedShot.equals("Normal S Lv1")) {
            MotionAtk[0] = 0.06f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Normal";
        }
        else if (SelectedShot.equals("Normal S Lv2")) {
            MotionAtk[0] = 0.12f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Normal";
        }
        else if (SelectedShot.equals("Normal S Lv3")) {
            MotionAtk[0] = 0.4f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Normal";
        }
        else if (SelectedShot.equals("Pellet S Lv1")) {
            MotionAtk[0] = 0.15f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pellet";
        }
        else if (SelectedShot.equals("Pellet S Lv2")) {
            MotionAtk[0] = 0.2f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pellet";
        }
        else if (SelectedShot.equals("Pellet S Lv3")) {
            MotionAtk[0] = 0.25f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Pellet";
        }
        else if (SelectedShot.equals("Long S Lv1")) {
            MotionAtk[0] = 0.15f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Long";
        }
        else if (SelectedShot.equals("Long S Lv2")) {
            MotionAtk[0] = 0.18f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Long";
        }
        else if (SelectedShot.equals("Dazzling S")) {
            MotionAtk[0] = 0.35f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Dazzling";
        }
        else if (SelectedShot.equals("Force S Lv1")) {
            MotionAtk[0] = 0.15f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Force";
        }
        else if (SelectedShot.equals("Force S Lv2")) {
            MotionAtk[0] = 0.18f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Force";
        }
        else if (SelectedShot.equals("Stone S")) {
            MotionAtk[0] = 0.1f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Stone";
        }
        else if (SelectedShot.equals("Heavy S Lv1")) {
            MotionAtk[0] = 0.09f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Heavy";
        }
        else if (SelectedShot.equals("Heavy S Lv2")) {
            MotionAtk[0] = 0.12f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Heavy";
        }
        else if (SelectedShot.equals("Sting S")) {
            MotionAtk[0] = 0.14f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Sting";
        }
        else if (SelectedShot.equals("Wyvern S")) {
            MotionAtk[0] = 0.25f;
            MotionAtk[1] = 0f;
            MotionAtk[2] = 0f;
            MotionAtk[3] = 0f;
            ShotType = "Wyvern";
        }

        TextView SelectedArt = (TextView) view;
        String ArtText = SelectedArt.getText().toString();
        if(ArtText.equals("Super Nova")){
            getHunterArt("Super Nova");
        }
        else if(ArtText.equals("Slicing Shell")){
            getHunterArt("Slicing Shell");
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
            Skills.setGroupD(10);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("Might Pill (+25)")) {
            Skills.setGroupD(25);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("Exciteshroom - Mycology (+10)")) {
            Skills.setGroupD(10);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("Demon Horn (+10)")) {
            Skills.setGroupD(10);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("Demon S (+10)")) {
            Skills.setGroupD(10);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("Demon Affinity S (+15)")) {
            Skills.setGroupD(15);
            Skills.setGroupDCrit(10);
            GroupDCritResrve = 10;
            Snackbar.make(view, "10% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupDSkillsText.equals("Cool Cat (+15)")) {
            Skills.setGroupD(15);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
        }
        else if(GroupDSkillsText.equals("-Group D-")) {
            Skills.setGroupD(0);
            Skills.setGroupDCrit(0);
            GroupDCritResrve = 0;
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

            Skills.setGroupJCrit(10);
            GroupJCritResrve = 10;
            Snackbar.make(view, "10% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Challenger +2 (+20%)")) {
            Skills.setGroupJ_1(1.2f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(15);
            GroupJCritResrve = 15;
            Snackbar.make(view, "15% Affinity Increase", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Unscathed (+20)")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(20f);

            Skills.setGroupJCrit(0);
            GroupJCritResrve = 0;
        }
        else if(GroupJSkillsText.equals("Latent Power +1")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(30);
            GroupJCritResrve = 30;
            Snackbar.make(view, "30% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("Latent Power +2")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(50);
            GroupJCritResrve = 50;
            Snackbar.make(view, "50% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(GroupJSkillsText.equals("-Group J-")) {
            Skills.setGroupJ_1(1f);
            Skills.setGroupJ_2(0f);

            Skills.setGroupJCrit(0);
            GroupJCritResrve = 0;
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
            Skills.setGroupO(10);
            GroupOCritResrve = 10;
        }
        else if(GroupOSkillsText.equals("Critical Eye +2")) {
            Skills.setGroupO(20);
            GroupOCritResrve = 20;
        }
        else if(GroupOSkillsText.equals("Critical Eye +3")) {
            Skills.setGroupO(30);
            GroupOCritResrve = 30;
        }
        else if(GroupOSkillsText.equals("-Critical Eye-")) {
            Skills.setGroupO(0);
            GroupOCritResrve = 0;
        }

        TextView GroupPSkills = (TextView) view;
        String GroupPSkillsText = GroupPSkills.getText().toString();
        if(GroupPSkillsText.equals("[Element] Atk Up +1")){
            Skills.setGroupP(4,1.05f);
            GroupPCheck = true;
        }
        else if(GroupPSkillsText.equals("[Element] Atk Up +2")) {
            Skills.setGroupP(6,1.1f);
            GroupPCheck = true;
        }
        else if(GroupPSkillsText.equals("-Element Atk Up-")) {
            Skills.setGroupP(0,1);
            GroupPCheck = false;
        }

        TextView GroupSSkills = (TextView) view;
        String GroupSSkillsText = GroupSSkills.getText().toString();
        if(GroupSSkillsText.equals("Artillery Novice")){
            Skills.setArtilleryModifier(1.15f);
        }
        else if(GroupSSkillsText.equals("Artillery Expert")) {
            Skills.setArtilleryModifier(1.3f);
        }
        else if(GroupSSkillsText.equals("-Artillery-")) {
            Skills.setArtilleryModifier(1);
        }
    }

    public void Calculate(){
        /*Binds the variable 'Calculate' to a button, specifically the 'CalculateButton' using its ID.*/
        Calculate = (Button) findViewById(R.id.CalculateButton);

        /*Gives the button an onClickListener() method (function) and makes it akin to an inline function.*/
        Calculate.setOnClickListener(new View.OnClickListener() {

            /*Creates a TextView array variable called 'textviews' and sets it so the values
            affect all the textviews needed.*/
            TextView[] textviews = new TextView[(12)];

            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(HeavyBowgunCalculation.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                int Counter;
                TextViewIDsAttacks = new String[]{"HBGAttack_1", "HBGAttack_2", "HBGAttack_3", "HBGAttack_4",
                        "HBGAttack_5", "HBGAttack_6"};

                TextViewIDsNames = new String[]{"HBGAttack_1_Name", "HBGAttack_2_Name", "HBGAttack_3_Name"
                        , "HBGAttack_4_Name", "HBGAttack_5_Name", "HBGAttack_6_Name"};
                AllTextViewIDs = new String[]{"HBGAttack_1_Name", "HBGAttack_2_Name", "HBGAttack_3_Name",
                        "HBGAttack_4_Name", "HBGAttack_5_Name", "HBGAttack_6_Name", "HBGAttack_1",
                        "HBGAttack_2", "HBGAttack_3", "HBGAttack_4", "HBGAttack_5", "HBGAttack_6"};

                for (int i = 0; i < 12; i++) {
                    Counter = getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName());
                    textviews[i] = (TextView) findViewById(Counter);
                    textviews[i].setVisibility(View.GONE);
                }

                TextView Banner = (TextView) findViewById(R.id.AttackBanner);

                RelativeLayout Info = (RelativeLayout) findViewById(R.id.AttackInfo);
                Info.setVisibility(View.GONE);

                TextView Disclaimer = (TextView) findViewById(R.id.ShotDisclaimer);
                Disclaimer.setVisibility(View.VISIBLE);

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                }
                //-End-

                float RawDamage = 0;
                float RawAffinity = 0;
                float TrueAttack;

                TextView Damage = (TextView) findViewById(R.id.DamageInputHBG);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputHBG);

                List<String> HitzoneCatchList = Arrays.asList("Head", "Chin", "Horn", "NONE");
                List<String> StyleCatchList = Arrays.asList("Guild", "Striker", "Aerial", "Alchemy");

                if(TextUtils.isEmpty(Damage.getText().toString())){
                    RawDamage = 0;
                    Damage.setText("0");
                }
                if(TextUtils.isEmpty(Affinity.getText().toString())){
                    RawAffinity = 0;
                    Affinity.setText("0");
                }

                StatsValidation Stats = new StatsValidation(Float.parseFloat(Damage.getText().toString()),
                        Float.parseFloat(Affinity.getText().toString()));

                if(Stats.isValid_BG()){
                    RawDamage = Float.parseFloat(Damage.getText().toString());
                    RawAffinity = Float.parseFloat(Affinity.getText().toString());
                }
                else{
                    if(!Stats.isValidAtk()){
                        Damage.setError("Enter a valid attack");
                        return;
                    }
                    else if(!Stats.isValidAffinity()){
                        Affinity.setError("Enter a valid affinity");
                        return;
                    }
                }

                Spinner ShotSelectText = (Spinner) findViewById(R.id.ShotSelect);
                Spinner StyleSelectText = (Spinner) findViewById(R.id.StyleSelect);
                String ShotText = ShotSelectText.getSelectedItem().toString();
                String StyleText = StyleSelectText.getSelectedItem().toString();

                if(!StyleText.equals("Adept") && PowerReloadCheck.isChecked()){
                    Snackbar.make(view, "Power Reload is only in Adept Style", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if(((HeavyUpRadio.isChecked() && !ShotText.contains("Heavy")) ||
                        (NormalUpRadio.isChecked() && !ShotText.contains("Normal")) ||
                        (PierceUpRadio.isChecked() && !ShotText.contains("Pierce")) ||
                        (PelletUpRadio.isChecked() && !ShotText.contains("Pellet")) ||
                        (FelyneSharpshooterCheck.isChecked() && !ShotText.contains("Normal"))) && SkillCheck) {
                    Snackbar.make(view, "Please check shot/skill selection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if((ElementalAtkUpCheck.isChecked() || GroupPCheck || ElementalCritCheck.isChecked()) &&
                        !ShotType.equals("Elemental")  && SkillCheck){
                    Snackbar.make(view, "Please check shot/element skill selection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if((FelyneBombardierCheck.isChecked() || Skills.ArtilleryCheck()) &&
                        (!ShotType.equals("Crag") && !ShotType.equals("Cannon")) && SkillCheck) {
                    Snackbar.make(view, "Please check shot/skill selection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                Skills.setRepeatOffenderModifier(RepeatOffenderCheck.isChecked());
                Skills.setCritBoostModifier(CriticalBoostCheck.isChecked());
                Skills.setStingerModifier(false);

                Skills.setPowercharmModifier(PowercharmCheck.isChecked());
                Skills.setPowertalonModifier(PowertalonCheck.isChecked());
                Skills.setFelyneBoosterModifier(FelyneBoosterCheck.isChecked());
                Skills.setCrisisModifier(CrisisCheck.isChecked());
                Skills.setFurorModifier(FurorCheck.isChecked());
                Skills.setDragonHeartModifier(DragonheartCheck.isChecked());

                Skills.setElementAtkUp(ElementalAtkUpCheck.isChecked());
                Skills.setElementCrit(ElementalCritCheck.isChecked(), RawAffinity);

                Skills.setGunpowderInfusionModiifer(GunpowderInfusionCheck.isChecked());

                Skills.setFelyneTemperModifier(FelyneTemperCheck.isChecked());
                Skills.setFelyneSharpshooterModifer(FelyneSharpshooterCheck.isChecked());
                Skills.setReloadModifier(PowerReloadCheck.isChecked());

                Skills.setGroupDCrit(GroupDCritResrve);
                Skills.setGroupJCrit(GroupJCritResrve);
                Skills.setGroupO(GroupOCritResrve);

                if((HeavyUpRadio.isChecked() || PierceUpRadio.isChecked() || NormalUpRadio.isChecked())
                        && SkillCheck){
                    Skills.setShotModifier(1.1f);
                }
                else if((TrueShotUpRadio.isChecked() || PelletUpRadio.isChecked()) && SkillCheck){
                    Skills.setShotModifier(1.2f);
                }
                else{
                    Skills.setShotModifier(1);
                }

                MonsterCalculation M;
                /*'M' is the new variable in this block of code and is used to call the
                 'MonsterCalculation' class. The calculations for hitzones will be done inside that
                 class.*/
                M = new MonsterCalculation(HeavyBowgunCalculation.this,
                        ChosenMonster + "",
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone);

                M.getHitzones(HeavyBowgunCalculation.this, Skills, WeaknessExploitCheck.isChecked());
                float TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck);

                if(MotionCheck == 1){
                    if(ChosenArt.equals("Super Nova")){
                        for (int i = 0; i < SuperNova.length; i++) {
                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(SuperNova[i])));
                            textviews[i].setVisibility(View.VISIBLE);

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(HunterArtsLevels_SN[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                        }
                    }else{
                        for (int i = 0; i < SlicingShell.length; i++) {
                            TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck) * SlicingShell[i];
                            float HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(HitzoneRaw)));
                            textviews[i].setVisibility(View.VISIBLE);

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(HunterArtsLevels_SS[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                        }
                    }
                    Disclaimer.setVisibility(View.GONE);
                    Banner.setText(ChosenArt);
                }
                else{
                    float AerialShotModifier = 1;
                    if (AerialShotSelect.isChecked() && SelectedStyle.equals("Aerial"))
                        AerialShotModifier = 1.5f;
                    else if(AerialShotSelect.isChecked() && !SelectedStyle.equals("Aerial") && SkillCheck){
                        Toast.makeText(HeavyBowgunCalculation.this, "Please select Aerial Style/'Airborne' to use 'Aerial Shot'",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(AirborneCheck.isChecked() && AerialShotSelect.isChecked() && SelectedStyle.equals("Aerial") && SkillCheck)
                        Skills.setAirborneModifier(AirborneCheck.isChecked());
                    else if(AirborneCheck.isChecked() && !SelectedStyle.equals("Aerial") && SkillCheck){
                        if(!AerialShotSelect.isChecked())
                            Toast.makeText(HeavyBowgunCalculation.this, "Please select 'Aerial Shot' to use 'Airborne'",
                                    Toast.LENGTH_SHORT).show();
                        Toast.makeText(HeavyBowgunCalculation.this, "Please select Aerial Style to use 'Airborne'",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (ShotType.equals("Triblast S") || ShotType.equals("Crag") || ShotType.equals("Clust")) {

                        MotionName[0] = "Shot Damage";
                        MotionName[1] = "Fixed Damage";
                        MotionName[2] = "Fire Damage";
                        MotionName[3] = "Stun Damage";

                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck) * MotionAtk[i];

                            //Hitzone Modification - Start

                            float HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;
                            M.setElmHitzoneValue(HeavyBowgunCalculation.this,
                                    ChosenMonster + "ElmHitzones_Fire");
                            float HitzoneElm = (RawDamage * MotionAtk[i] * M.getElmHitzoneValue()) / 100;

                            //Hitzone Modification - End

                            if (i == 0) {
                                if (AerialShotSelect.isChecked()) {
                                    TrueAttack = HitzoneRaw * AerialShotModifier;
                                    Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                                else {
                                    TrueAttack = HitzoneRaw * DistanceModifier;
                                }
                            }
                            else if (i == 1){
                                if(ShotType.equals("Crag")){
                                    TrueAttack = MotionAtk[1] * (Skills.getArtilleryModifier() *
                                            Skills.getFelyneBombardierModifier());
                                }
                                else{
                                    TrueAttack = MotionAtk[1];
                                }

                            }
                            else if (i == 2){
                                TrueAttack = HitzoneElm;
                            }
                            else{
                                TrueAttack = MotionAtk[3];
                            }

                            if(!HitzoneCatchList.contains(ChosenHitzone) && i == 3){
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotText);
                                break;
                            }
                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(MotionName[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/
                        }
                    }

                    else if (ShotType.equals("Cannon")) {

                        MotionName[0] = "Shot Damage";
                        MotionName[1] = "Fixed Damage";
                        MotionName[2] = "Stun Damage";

                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueAttack = (TrueRaw * MotionAtk[i] * M.getRawHitzoneValue()) / 100;

                            if (i == 0) {
                                if (AerialShotSelect.isChecked()) {
                                    TrueAttack *= AerialShotModifier;
                                    Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                                else {
                                    TrueAttack *= DistanceModifier;
                                }
                            }
                            else if (i == 1){
                                TrueAttack = MotionAtk[1] * (Skills.getArtilleryModifier() * Skills.getFelyneBombardierModifier());


                            }
                            else{
                                TrueAttack = MotionAtk[2];
                            }

                            if (MotionAtk[i] == 0f){
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotType);
                                return;
                            }

                            if(!HitzoneCatchList.contains(ChosenHitzone) && i == 2){
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotText);
                                break;
                            }

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(MotionName[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/
                        }
                    }

                    else if (ShotType.equals("Elemental")) {
                        MotionName[0] = "Shot Damage";
                        MotionName[1] = "Elemental Damage";

                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueAttack = (TrueRaw * MotionAtk[i] * M.getRawHitzoneValue()) / 100;

                            if (i == 0) {
                                if (AerialShotSelect.isChecked()) {
                                    TrueAttack *= AerialShotModifier;
                                    Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                                else {
                                    TrueAttack *= DistanceModifier;
                                }
                            }
                            else {
                                //TODO: 17/12/2018 Add disclaimer to notify users that Element isn't affected by crit distance (double check this info)
                                if(!ElementalCritCheck.isChecked()){
                                    Skills.setGroupO(0);
                                    Skills.setGroupJCrit(0);
                                    Skills.setGroupDCrit(0);
                                    RawAffinity = 0;
                                }
                                if(ChosenMonster.equals("None")){
                                    M.setElmHitzoneValue(HeavyBowgunCalculation.this,
                                            "NoneElmHitzones_NONE");
                                }else{
                                    M.setElmHitzoneValue(HeavyBowgunCalculation.this,
                                            ChosenMonster + "ElmHitzones_" + ChosenElement);
                                }
                                TrueAttack = (Skills.getTrueElm(Skills.getTrueRaw(RawDamage * 1.48f,
                                        RawAffinity, SkillCheck), SkillCheck) * 0.95f * M.getElmHitzoneValue()) / 100;
                            }

                            if (MotionAtk[i] == 0f) {
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotText);
                                return;
                            }

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(MotionName[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/
                        }
                    }

                    else if (ShotType.equals("Slicing")) {

                        MotionName[0] = "Shot Damage";
                        MotionName[1] = "Cutting Damage";

                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck) * MotionAtk[i];

                            //Hitzone Modification - Start
                            if(i == 1){
                                M.alterHitzones(HeavyBowgunCalculation.this, ChosenMonster);
                            }
                            float HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;

                            //Hitzone Modification - End

                            if (AerialShotSelect.isChecked()) {
                                TrueAttack = HitzoneRaw * AerialShotModifier;
                                Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else {
                                TrueAttack = HitzoneRaw * DistanceModifier;
                            }

                            if (MotionAtk[i] == 0f) {
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotText);
                                return;
                            }

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(MotionName[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/
                        }
                    }

                    else if (ShotType.equals("Shrapnel")) {

                        MotionName[0] = "Shot Damage";
                        MotionName[1] = "Shrapnel Damage";

                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck) * MotionAtk[i];

                            //Hitzone Modification - Start

                            float HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;

                            //Hitzone Modification - End

                            if (i == 0) {
                                if (AerialShotSelect.isChecked()) {
                                    TrueAttack = HitzoneRaw * AerialShotModifier;
                                    Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                                else {
                                    TrueAttack = HitzoneRaw * DistanceModifier;
                                }
                            }
                            else {
                                TrueAttack = HitzoneRaw;
                            }

                            if (MotionAtk[i] == 0f) {
                                Info.setVisibility(View.VISIBLE);
                                Banner.setText(ShotText);
                                return;
                            }

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                            textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/

                            textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                            textviews[i].setText(MotionName[i]);
                            textviews[i].setVisibility(View.VISIBLE);
                            /*Sets the current textview to the id value of 'Counter' and then sets that
                            textviews value the value of 'test's current value. It also sets the
                            visibility of all the used textboxes to 'visible'.*/
                        }
                    }

                    else {
                        for (int i = 0; i < MotionAtk.length; i++) {
                            TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, RawAffinity, SkillCheck) * MotionAtk[i];

                            //Hitzone Modification - Start

                            float HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;

                            //Hitzone Modification - End

                            TrueAttack = HitzoneRaw;

                            if (MotionAtk[i] != 0f) {
                                if (ShotType.equals("Normal") || ShotType.equals("Pierce")) {
                                    if (AerialShotSelect.isChecked()) {
                                        TrueAttack *= AerialShotModifier;
                                        Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                    else {
                                        TrueAttack *= DistanceModifier;
                                    }
                                }
                                else if (ShotType.equals("Heavy")) {
                                    if (ChosenDistance.equals("Critical") || AerialShotSelect.isChecked()) {
                                        TrueAttack *= 1.5f;
                                    }
                                }
                                else if(ShotType.equals("Sting") && !ChosenHitzone.equals("NONE")){
                                    M.getHitzones_Stinger(HeavyBowgunCalculation.this, Skills);
                                    TrueAttack = (TrueRaw * M.getRawHitzoneValue()) / 100;
                                    if (AerialShotSelect.isChecked()) {
                                        TrueAttack = HitzoneRaw * AerialShotModifier;
                                        Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                                else {
                                    TrueAttack *= DistanceModifier;
                                }

                                textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                                textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
                                textviews[i].setVisibility(View.VISIBLE);
                                /*Sets the current textview to the id value of 'Counter' and then sets that
                                textviews value the value of 'test's current value. It also sets the
                                visibility of all the used textboxes to 'visible'.*/

                                textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
                                textviews[i].setText("Shot Damage");
                                textviews[i].setVisibility(View.VISIBLE);
                                /*Sets the current textview to the id value of 'Counter' and then sets that
                                textviews value the value of 'test's current value. It also sets the
                                visibility of all the used textboxes to 'visible'.*/
                            }
                        }
                    }
                    Disclaimer.setVisibility(View.VISIBLE);
                    Banner.setText(ShotText);
                }

                Info.setVisibility(View.VISIBLE);

                TextView StaggerBanner = (TextView) findViewById(R.id.StaggerBanner);
                if(!ChosenMonster.equals("None")){
                    StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
                    StaggerBanner.setVisibility(View.VISIBLE);
                }
                else{
                    StaggerBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    public static float getDistanceModifier(float i) {
        DistanceModifier = i;
        return DistanceModifier;
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
    public static String getChosenDistance(String i) {
        ChosenDistance = i;
        return ChosenDistance;
    }
    public static String getHunterArt(String i) {
        ChosenArt = i;
        return ChosenArt;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}