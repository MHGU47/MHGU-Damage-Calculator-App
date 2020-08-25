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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DualBladesCalculation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner StyleSelect, SharpnessSelect, ModeSelect;
    Spinner MonsterSelect, HitzoneSelect, MainElementSelect, SubElementSelect, HunterArtSelect;

    Button Calculate;
    String SelectedMonster;
    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement,ChosenSubElement, MonsterType, ChosenArt;
    static float SelectedSharpnessElmModifier;
    static float SelectedSharpnessAtkModifier;

    //Skill and Hunter Art Selection variables - Start
    Integer Counter;

    Switch SkillSwitch;
    boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect;
    CheckBox PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck, FurorCheck, BludgeonerCheck,
            RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck, ElementalAtkUpCheck, AirborneCheck,
            WeaknessExploitCheck, DragonheartCheck;
    RadioButton WolfsMawLevel1Check, WolfsMawLevel2Check, WolfsMawLevel3Check, WolfsMawOffCheck;

    Float SkillSharpnessModifier = 1f;
    //-End-

    /*Creates a string/number variables which are arrays.*/
    float[] SharpModAtk = {0.5f, 0.75f, 1, 1.05f, 1.2f, 1.32f, 1.39f};
    float[] SharpModElm = {0.25f, 0.5f, 0.75f, 1, 1.06f, 1.12f, 1.2f};

    String[] TextViewIDsAttacks;
    String[] TextViewIDsNames;
    String[] AllTextViewIDs;

    Float[][] MotionGroup;
    Float[][] SelectedMotion;
    String[][] SelectedStyle;

    Float[] GuildStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.21f,
            0.3f, /*space*/0.17f, 0.3f, 0.23f};
    String[] GuildStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Jumping Slash - Left (3 hits)",
            "Horizontal Slash - Right (2 hits)","Jumping Slash - Right (3 hits)","Jump Attack (2 hits)"};

    Float[] GuildStyleMotionDemon = {0.18f, 0.34f, 0.46f, /*A button*/0.28f, 0.32f, 0.28f, 0.32f, /*space*/
            0.48f, 1.03f, 0.54f, /*space*/0.68f};
    String[] GuildStyleNamesDemon = {"Upward Slash","Circle Slash (3 hits)","Demon Combo (6 hits)",/*A*/
            "Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","Devil's Dance (12 hits)","Demon Dodge Jump Attack (4 hits)",
            "     -Follow Up (6 hits)"};

    Float[] GuildStyleMotionArchdemon = {0.48f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.21f,
            0.28f, 0.32f, /*space*/0.17f, 0.28f, 0.32f, /*space*/0.48f, 0.78f, 0.54f, /*space*/0.68f};
    String[] GuildStyleNamesArchdemon = {"Draw/Dash Attack (6 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Horizontal Slash - Right (2 hits)","Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","Demon Flurry (7 hits)","Demon Dodge Jump Attack (4 hits)",
            "     -Follow Up (6 hits)"};
    String[][] GuildStyleNames = {GuildStyleNamesNormal, GuildStyleNamesDemon, GuildStyleNamesArchdemon};
    Float[][] GuildStyleMotion = {GuildStyleMotionNormal, GuildStyleMotionDemon, GuildStyleMotionArchdemon};

    Float[] StrikerStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.17f,
            0.21f, 0.3f, /*space*/0.23f};
    String[] StrikerStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Horizontal Slash - Right (2 hits)","Jumping Slash (3 hits)",
            "Jump Attack (2 hits)"};

    Float[] StrikerStyleMotionDemon = {0.18f, 0.34f, 0.46f, /*A button*/0.28f, 0.32f, 0.28f, /*space*/
            0.32f, 0.48f, 1.03f, /*space*/0.54f, 0.68f};
    String[] StrikerStyleNamesDemon = {"Upward Slash","Circle Slash (3 hits)","Demon Combo (6 hits)",/*A*/
            "Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)","Right Jumping Slash (3 hits)",
            "     -Second Jump[R] (3 hits)","Demon Flurry Rush (6 hits)","Devil's Dance (12 hits)",
            "Demon Dodge Jump Attack (4 hits)","     -Follow Up (6 hits)"};
    String[][] StrikerStyleNames = {StrikerStyleNamesNormal, StrikerStyleNamesDemon};
    Float[][] StrikerStyleMotion = {StrikerStyleMotionNormal, StrikerStyleMotionDemon};

    Float[] AerialStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.21f,
            0.3f, 0.17f, /*space*/0.3f, 0.23f};
    String[] AerialStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Jumping Slash - Left (3 hits)","Horizontal Slash - Right (2 hits)",
            "Jumping Slash - Right (3 hits)","Mid-Air Attack (2 hits)"};

    Float[] AerialStyleMotionDemon = {0.18f, 0.34f, /*space*/0.46f, /*A button*/ 0.28f, 0.32f, /*space*/
            0.28f, 0.32f, 0.48f, /*space*/0.54f, 0.68f, 0.54f, /*space*/0.68f};
    String[] AerialStyleNamesDemon = {"Upward Slash","Circle Slash (3 hits)",
            "Demon Combo (6 hits)",/*A*/"Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)","Demon Flurry Rush (6 hits)",
            "     -Off Ledge (4 hits)","     -Follow up (6 hits)","Vault Attack (4 hits)",
            "     -Follow Up (6 hits)"};

    Float[] AerialStyleMotionArchdemon = {0.48f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/
            0.21f, 0.28f, 0.32f, /*space*/0.17f, 0.28f, 0.32f, /*space*/0.48f, 0.54f, 0.68f, /*space*/
            0.78f, 0.54f, 0.23f};
    String[] AerialStyleNamesArchdemon = {"Draw/Dash Attack (6 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Downward Slash  (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Horizontal Slash - Right (2 hits)","Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","     -Off Ledge (4 hits)","     -Follow up (6 hits)",
            "Demon Flurry (7 hits)","Vault Attack (4 hits)","     -Follow up (6 hits)"};
    String[][] AerialStyleNames = {AerialStyleNamesNormal, AerialStyleNamesDemon, AerialStyleNamesArchdemon};
    Float[][] AerialStyleMotion = {AerialStyleMotionNormal, AerialStyleMotionDemon, AerialStyleMotionArchdemon};

    Float[] AdeptStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/
            0.21f, 0.3f, 0.17f, /*space*/0.3f, 0.23f};
    String[] AdeptStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Jumping Slash - Left (3 hits)","Horizontal Slash - Right (2 hits)",
            "Jumping Slash - Right (3 hits)","Jump Attack (2 hits)"};

    Float[] AdeptStyleMotionDemon = {0.18f, 0.34f, /*space*/0.46f, /*A button*/0.28f, 0.32f, /*space*/
            0.28f, 0.32f, /*space*/0.42f, 1.03f, 0.23f, 0.55f};
    String[] AdeptStyleNamesDemon = {"Upward Slash","Circle Slash (3 hits)","Demon Combo (6 hits)",/*A*/
            "Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","Devil's Dance (12 hits)","Jump Attack (2 hits)","Adept Evade (4 hits)"};

    Float[] AdeptStyleMotionArchdemon = {0.4f, 0.18f, 0.2f, /*space*/0.17f, 0.34f, /*A button*/
            0.21f, /*space*/0.28f, 0.32f, 0.17f, /*space*/0.28f, 0.32f, 0.4f, /*space*/0.78f, 0.23f, 0.48f,};
    String[] AdeptStyleNamesArchdemon = {"Draw/Dash Attack (6 hits)","Upward Slash","Idle Slash (2 hits)",
            "Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/"Horizontal Slash - Left (2 hits)",
            "Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)","Horizontal Slash - Right (2 hits)",
            "Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)","Demon Flurry Rush (6 hits)",
            "Demon Flurry (7 hits)","Jump Attack (2 hits)","Adept Evade (4 hits)"};
    String[][] AdeptStyleNames = {AdeptStyleNamesNormal, AdeptStyleNamesDemon, AdeptStyleNamesArchdemon};
    Float[][] AdeptStyleMotion = {AdeptStyleMotionNormal, AdeptStyleMotionDemon, AdeptStyleMotionArchdemon};

    Float[] ValorStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/
            0.21f, 0.3f, 0.17f, /*space*/0.3f, 0.78f, 0.23f};
    String[] ValorStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Jumping Slash - Left (3 hits)","Horizontal Slash - Right (2 hits)",
            "Jumping Slash - Right (3 hits)","Valor Stance - Demon Flurry (7 hits)","Jump Attack (2 hits)"};

    Float[] ValorStyleMotionArchdemon = {0.4f, 0.18f, 0.2f, /*space*/0.17f, 0.34f, 0.48f,
            /*space A button*/0.21f, 0.28f, 0.32f, /*space*/0.17f, 0.28f, 0.32f,
            /*space*/0.48f, 0.54f, 0.68f, /*space*/0.78f, 0.34f, 1.16f,
            /*space*/0.23f};
    String[] ValorStyleNamesArchdemon = {"Draw/Dash Attack (6 hits)","Upward Slash","Idle Slash (2 hits)",
            "Reverse Slash (2 hits)","Circle Slash (3 hits)","Demon Combo (6 hits)",
            /*A*/"Horizontal Slash - Left (2 hits)","Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Horizontal Slash - Right (2 hits)","Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","     -Off Ledge (4 hits)","     -Follow up (6 hits)",
            "Demon Flurry (7 hits)","Valor Guard Point (4 hits)","True Devil's Dance (13 hits)",
            "Jump Attack (2 hits)"};
    String[][] ValorStyleNames = {ValorStyleNamesNormal, ValorStyleNamesArchdemon};
    Float[][] ValorStyleMotion = {ValorStyleMotionNormal, ValorStyleMotionArchdemon};

    Float[] AlchemyStyleMotionNormal =  {0.28f, 0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.21f,
            0.3f, /*space*/0.17f, 0.3f, 0.23f};
    String[] AlchemyStyleNamesNormal = {"Draw Attack (4 hits)","Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Jumping Slash - Left (3 hits)",
            "Horizontal Slash - Right (2 hits)","Jumping Slash - Right (3 hits)","Jump Attack (2 hits)"};

    Float[] AlchemyStyleMotionDemon = {0.18f, 0.34f, 0.46f, /*A button*/0.28f, 0.32f,
            /*space*/0.28f, 0.32f, 1.03f};
    String[] AlchemyStyleNamesDemon = {"Upward Slash","Circle Slash (3 hits)","Demon Combo (6 hits)",/*A*/
            "Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)", "Devil's Dance (12 hits)"};

    Float[] AlchemyStyleMotionArchdemon = {0.28f, 0.18f, /*space*/0.2f, 0.17f, 0.34f, /*A button*/0.21f,
            0.28f, 0.32f, /*space*/0.17f, 0.28f, 0.32f, /*space*/0.48f, 0.78f, 0.54f, /*space*/0.68f};
    String[] AlchemyStyleNamesArchdemon = {"Draw/Dash Attack (4 hits)","Upward Slash",
            "Idle Slash (2 hits)","Reverse Slash (2 hits)","Circle Slash (3 hits)",/*A*/
            "Horizontal Slash - Left (2 hits)","Left Jumping Slash (3 hits)","     -Second Jump[L] (3 hits)",
            "Horizontal Slash - Right (2 hits)","Right Jumping Slash (3 hits)","     -Second Jump[R] (3 hits)",
            "Demon Flurry Rush (6 hits)","Demon Flurry (7 hits)","Demon Dodge Jump Attack (4 hits)",
            "     -Follow Up (6 hits)"};
    String[][] AlchemyStyleNames = {AlchemyStyleNamesNormal, AlchemyStyleNamesDemon, AlchemyStyleNamesArchdemon};
    Float[][] AlchemyStyleMotion = {AlchemyStyleMotionNormal, AlchemyStyleMotionDemon, AlchemyStyleMotionArchdemon};

    String[][][] Styles = {GuildStyleNames, StrikerStyleNames, AerialStyleNames, AdeptStyleNames, ValorStyleNames, AlchemyStyleNames};
    Float[][][] Motions = {GuildStyleMotion, StrikerStyleMotion, AerialStyleMotion, AdeptStyleMotion, ValorStyleMotion, AlchemyStyleMotion};

    Float[] BloodWind = {2.16f, 2.68f, 3.2f};
    Float[] AerialSlam = {1.7f, 2f, 2.4f};
    Float[] SpiralRend = {0.64f, 1.14f, 1.34f};
    Float[][] HunterArts = {BloodWind, AerialSlam, SpiralRend};
    Float[] ChosenHunterArt;
    String[] HunterArtsLevels = {"Level I", "Level II", "Level III"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_blades_calculation);
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

        ModeSelect = (Spinner) findViewById(R.id.ModeSelect);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,R.array.DBModes,
                android.R.layout.simple_spinner_dropdown_item);

        ModeSelect.setAdapter(adapter3);

        ModeSelect.setOnItemSelectedListener(this);

        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        MainElementSelect = (Spinner) findViewById(R.id.MainElementSelect);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        MainElementSelect.setAdapter(adapter4);

        MainElementSelect.setOnItemSelectedListener(this);

        SubElementSelect = (Spinner) findViewById(R.id.SubElementSelect);

        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this,R.array.SubElement,
                android.R.layout.simple_spinner_dropdown_item);

        SubElementSelect.setAdapter(adapter7);

        SubElementSelect.setOnItemSelectedListener(this);

        HunterArtSelect = (Spinner) findViewById(R.id.HunterArtSelect);

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this,R.array.DB_HA_Names,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(adapter5);

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

        ArrayAdapter GroupPAdapter = ArrayAdapter.createFromResource(this,R.array.GroupP_DB,
                android.R.layout.simple_spinner_dropdown_item);

        GroupPSelect.setAdapter(GroupPAdapter);

        GroupPSelect.setOnItemSelectedListener(this);

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = (Spinner) findViewById(R.id.MonsterSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(adapter8);

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
        DragonheartCheck = findViewById(R.id.DragonheartCheckBox);

        WolfsMawOffCheck = (RadioButton) findViewById(R.id.WolfsMawOffCheck);
        WolfsMawLevel1Check = (RadioButton) findViewById(R.id.WolfsMawLevel1Check);
        WolfsMawLevel2Check = (RadioButton) findViewById(R.id.WolfsMawLevel2Check);
        WolfsMawLevel3Check = (RadioButton) findViewById(R.id.WolfsMawLevel3Check);

        Calculate();
    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    /*Method (function) that is called whenever an item from the drop down menu is selected.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*Declares a text view variable called 'StyleSelectText' and sets it as */
        TextView StyleSelectText = (TextView) view;
        /*Toast.makeText(this, "Selected style: " + StyleSelectText.getText().toString(),
                                                            Toast.LENGTH_SHORT).show();*/
        final String Style = StyleSelectText.getText().toString();
        if (Style.equals("Guild")) {
            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[0];
            SelectedMotion = Motions[0];
        } else if (Style.equals("Striker")) {
            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[1];
            SelectedMotion = Motions[1];
        } else if (Style.equals("Aerial")) {
            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[2];
            SelectedMotion = Motions[2];
        } else if (Style.equals("Adept")) {
            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[3];
            SelectedMotion = Motions[3];
        } else if (Style.equals("Valor")) {
            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[4];
            SelectedMotion = Motions[4];
        } else if (Style.equals("Alchemy")) {
            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SelectedStyle = Styles[5];
            SelectedMotion = Motions[5];
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

        TextView SelectedSubElement = (TextView) view;
        String SubElementText = SelectedSubElement.getText().toString();
        if (SubElementText.equals("[Fire]")) {
            getChosenSubElement("Fire");
        } else if (SubElementText.equals("[Water]")) {
            getChosenSubElement("Water");
        } else if (SubElementText.equals("[Ice]")) {
            getChosenSubElement("Ice");
        } else if (SubElementText.equals("[Thunder]")) {
            getChosenSubElement("Thunder");
        } else if (SubElementText.equals("[Dragon]")) {
            getChosenSubElement("Dragon");
        } else if (SubElementText.equals("[NONE]")) {
            getChosenSubElement("NONE");
        }

        TextView SelectedArt = (TextView) view;
        String ArtText = SelectedArt.getText().toString();
        if(ArtText.equals("Blood Wind")){
            getHunterArt("Blood Wind");
        }
        else if(ArtText.equals("Aerial Slam")){
            getHunterArt("Aerial Slam");
        }
        else if(ArtText.equals("Spiral Rend")){
            getHunterArt("Spiral Rend");
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
            Skills.setGroupP_Sub(0, 1);
        }
        else if(GroupPSkillsText.equals("[Element] Atk Up +2")) {
            Skills.setGroupP(6, 1.1f);
            Skills.setGroupP_Sub(0, 1);
        }
        else if(GroupPSkillsText.equals("[Sub Element] Atk Up +1")){
            Skills.setGroupP(0, 1);
            Skills.setGroupP_Sub(4, 1.05f);
        }
        else if(GroupPSkillsText.equals("[Sub Element] Atk Up +2")) {
            Skills.setGroupP(0, 1);
            Skills.setGroupP_Sub(6, 1.1f);
        }
        else if(GroupPSkillsText.equals("-Element Atk Up-")) {
            Skills.setGroupP(0,1);
            Skills.setGroupP_Sub(0, 1);
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
                        getSystemService(DualBladesCalculation.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                TextViewIDsAttacks = new String[]{"DBAttack_1", "DBAttack_2", "DBAttack_3",
                        "DBAttack_4", "DBAttack_5", "DBAttack_6", "DBAttack_7",
                        "DBAttack_8", "DBAttack_9", "DBAttack_10", "DBAttack_11",
                        "DBAttack_12", "DBAttack_13", "DBAttack_14", "DBAttack_15",
                        "DBAttack_16", "DBAttack_17", "DBAttack_18", "DBAttack_19",
                        "DBAttack_20"};

                TextViewIDsNames = new String[]{"DBAttack_1_Name", "DBAttack_2_Name", "DBAttack_3_Name",
                        "DBAttack_4_Name", "DBAttack_5_Name", "DBAttack_6_Name", "DBAttack_7_Name",
                        "DBAttack_8_Name", "DBAttack_9_Name", "DBAttack_10_Name", "DBAttack_11_Name",
                        "DBAttack_12_Name", "DBAttack_13_Name", "DBAttack_14_Name", "DBAttack_15_Name",
                        "DBAttack_16_Name", "DBAttack_17_Name", "DBAttack_18_Name", "DBAttack_19_Name",
                        "DBAttack_20_Name"};

                AllTextViewIDs = new String[]{"DBAttack_1_Name", "DBAttack_2_Name", "DBAttack_3_Name",
                        "DBAttack_4_Name", "DBAttack_5_Name", "DBAttack_6_Name", "DBAttack_7_Name",
                        "DBAttack_8_Name", "DBAttack_9_Name", "DBAttack_10_Name", "DBAttack_11_Name",
                        "DBAttack_12_Name", "DBAttack_13_Name", "DBAttack_14_Name", "DBAttack_15_Name",
                        "DBAttack_16_Name", "DBAttack_17_Name", "DBAttack_18_Name", "DBAttack_19_Name",
                        "DBAttack_20_Name", "DBAttack_1", "DBAttack_2", "DBAttack_3",
                        "DBAttack_4", "DBAttack_5", "DBAttack_6", "DBAttack_7",
                        "DBAttack_8", "DBAttack_9", "DBAttack_10", "DBAttack_11",
                        "DBAttack_12", "DBAttack_13", "DBAttack_14", "DBAttack_15",
                        "DBAttack_16", "DBAttack_17", "DBAttack_18", "DBAttack_19",
                        "DBAttack_20"};

                for (int i = 0; i < AllTextViewIDs.length; i++) {
                    textviews[i] = (TextView) findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
                    textviews[i].setVisibility(View.GONE);
                }

                RelativeLayout Info = (RelativeLayout) findViewById(R.id.AttackInfo);
                Info.setVisibility(View.GONE);

                TextView StaggerBanner = findViewById(R.id.StaggerBanner);
                StaggerBanner.setVisibility(View.GONE);

                Spinner StyleSpinner = (Spinner) findViewById(R.id.StyleSelect);
                String StyleText = StyleSpinner.getSelectedItem().toString();

                Spinner ModeSpinner = (Spinner) findViewById(R.id.ModeSelect);
                String ModeText = ModeSpinner.getSelectedItem().toString();

                Float[] Motion;
                String[] Style;
                if (!StyleText.equals("Striker") && ModeText.equals("Archdemon")) {
                    if(StyleText.equals("Valor")){
                        Motion = SelectedMotion[1];
                        Style = SelectedStyle[1];
                    }
                    else{
                        Motion = SelectedMotion[2];
                        Style = SelectedStyle[2];
                    }
                }
                else if (ModeText.equals("Normal")) {
                    Motion = SelectedMotion[0];
                    Style = SelectedStyle[0];
                }
                else if (!StyleText.equals("Valor") && ModeText.equals("Demon")) {
                    Motion = SelectedMotion[1];
                    Style = SelectedStyle[1];
                }
                else {
                    Snackbar.make(view,  StyleText + " Style doesn't have " + ModeText + " Mode", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                //Hunter Art Resource - Start
                int MotionCheck = 0;
                float[] HunterArtElementCheck = {1};
                if (!ChosenArt.equals("-None-")){
                    MotionCheck = 1;
                    if(ChosenArt.equals("Blood Wind")){
                        ChosenHunterArt = HunterArts[0];
                        HunterArtElementCheck = new float[] {13.4f, 17.4f, 21.4f};
                    }
                    else if(ChosenArt.equals("Aerial Slam")){
                        ChosenHunterArt = HunterArts[1];
                        HunterArtElementCheck = new float[] {7, 7, 7};
                    }
                    else if(ChosenArt.equals("Spiral Rend")){
                        ChosenHunterArt = HunterArts[2];
                        HunterArtElementCheck = new float[] {2.4f, 5.2f, 6.6f};
                    }
                }
                //-End-

                MotionGroup = new Float[][] {Motion, ChosenHunterArt};

                float RawDamage = 0;
                float RawElement = 0;
                float RawSubElement = 0;
                float RawAffinity = 0;

                float TrueRaw, TrueAttack;
                int ElementCheck;
                float TrueElement = 0;
                float TrueSubElement = 0;

                TextView Damage = (TextView) findViewById(R.id.DamageInputDB);
                TextView Element = (TextView) findViewById(R.id.MainElementInputDB);
                TextView SubElement = (TextView) findViewById(R.id.SubElementInputDB);
                TextView Affinity = (TextView) findViewById(R.id.AffinityInputDB);

                if(TextUtils.isEmpty(Damage.getText().toString())){
                    RawDamage = 0;
                    Damage.setText("0");
                }
                if(TextUtils.isEmpty(Element.getText().toString())){
                    RawElement = 0;
                    Element.setText("0");
                }
                if(TextUtils.isEmpty(SubElement.getText().toString())){
                    RawSubElement = 0;
                    SubElement.setText("0");
                }
                if(TextUtils.isEmpty(Affinity.getText().toString())){
                    RawAffinity = 0;
                    Affinity.setText("0");
                }

                StatsValidation Stats = new StatsValidation(Float.parseFloat(Damage.getText().toString()),
                        ChosenElement,Float.parseFloat(Element.getText().toString()),
                        ChosenSubElement,Float.parseFloat(SubElement.getText().toString()),
                        Float.parseFloat(Affinity.getText().toString()));

                if(Stats.isValid_DB()){
                    RawDamage = Float.parseFloat(Damage.getText().toString());
                    RawElement = Float.parseFloat(Element.getText().toString());
                    RawSubElement = Float.parseFloat(SubElement.getText().toString());
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
                    else if(!Stats.isValidSubElm()){
                        SubElement.setError("Enter a valid sub element");
                        return;
                    }
                    else if(!Stats.isValidAffinity()){
                        Affinity.setError("Enter a valid affinity");
                        return;
                    }
                }

                if(RawElement == 0 && RawSubElement > 0){
                    Element.setError("Enter a valid element");
                    Snackbar.make(view, "Sub elements must have a main element", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if((RawElement == RawSubElement) && (ChosenElement.equals(ChosenSubElement)) && RawElement > 0){
                    Snackbar.make(view, "Main and sub element should be different", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (RawSubElement > 0) ElementCheck = 2;
                else ElementCheck = 1;

                Skills.setRepeatOffenderModifier(RepeatOffenderCheck.isChecked());
                Skills.setCritBoostModifier(CriticalBoostCheck.isChecked());

                Skills.setPowercharmModifier(PowercharmCheck.isChecked());
                Skills.setPowertalonModifier(PowertalonCheck.isChecked());
                Skills.setFelyneBoosterModifier(FelyneBoosterCheck.isChecked());
                Skills.setCrisisModifier(CrisisCheck.isChecked());
                Skills.setFurorModifier(FurorCheck.isChecked());
                Skills.setBludgeonerModifier(BludgeonerCheck.isChecked());
                Skills.setDragonHeartModifier(DragonheartCheck.isChecked());

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
                M = new MonsterCalculation(DualBladesCalculation.this,
                        ChosenMonster + "RawHitzones_Cut",
                        ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ChosenMonster + "ElmHitzones_" + ChosenSubElement,
                        ChosenMonster + "_StaggerLimits",
                        HitzoneGroup + "Hitzones",
                        ChosenHitzone,
                        "Dual Blades");

                M.getHitzones(DualBladesCalculation.this, ChosenElement, ChosenSubElement, Skills, WeaknessExploitCheck.isChecked());

                for (int i = 0; i < MotionGroup[MotionCheck].length; i++) {
                    float WolfsMawModifier = 0;

                    if ((ElementCheck == 2) && (MotionCheck == 0)){
                        if (Style[i].equals("Draw/Dash Attack (6 hits)")) {
                            TrueElement = RawElement  * 2.7f;
                            TrueSubElement = RawSubElement * 2.7f;
                        }
                        else if (Style[i].equals("Demon Dodge Jump Attack (4 hits)")) {
                            TrueElement = RawElement  * 2;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("     -Follow Up (6 hits)")) {
                            TrueElement = RawElement * 2.1f;
                            TrueSubElement = RawSubElement * 2.1f;
                        }
                        else if (Style[i].equals("Upward Slash")) {
                            TrueElement = 0;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Demon Combo (6 hits)")) {
                            TrueElement = RawElement * 2.1f;
                            TrueSubElement = RawSubElement * 2.1f;
                        }
                        else if (Style[i].equals("Demon Flurry Rush (6 hits)")) {
                            TrueElement = RawElement * 2.1f;
                            TrueSubElement = RawSubElement * 2.1f;
                        }
                        else if (Style[i].equals("     -Off Ledge (4 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Left Jumping Slash (3 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("     -Second Jump[L] (3 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Right Jumping Slash (3 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("     -Second Jump[R] (3 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Jump Attack (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Vault Attack (4 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Devil's Dance (12 hits)")) {
                            TrueElement = RawElement * 5.7f;
                            TrueSubElement = RawSubElement * 5.7f;
                        }
                        else if (Style[i].equals("Adept Evade (4 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Idle Slash (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Reverse Slash (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Circle Slash (3 hits)")) {
                            TrueElement = RawElement * 1.7f;
                            TrueSubElement = RawSubElement * 0.7f;
                        }
                        else if (Style[i].equals("Horizontal Slash - Left (2 hits)")) {
                            TrueElement = 0;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Horizontal Slash - Right (2 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = 0;
                        }
                        else if (Style[i].equals("Demon Flurry (7 hits)")) {
                            TrueElement = RawElement * 3.7f;
                            TrueSubElement = RawSubElement * 2.7f;
                        }

                        //Normal Mode If Statment

                        if (Style[i].equals("Draw Attack (4 hits)")) {
                            TrueElement = RawElement  * 1.4f;
                            TrueSubElement = RawSubElement * 1.4f;
                        }
                        else if (Style[i].equals("Dash Attack (4 hits)")) {
                            TrueElement = RawElement * 1.4f;
                            TrueSubElement = RawSubElement * 1.4f;
                        }
                        else if (Style[i].equals("Upward Slash")) {
                            TrueElement = 0;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Idle Slash (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Reverse Slash (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Horizontal Slash - Left (2 hits)")) {
                            TrueElement = 0;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Jumping Slash - Left (3 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement * 2;
                        }
                        else if (Style[i].equals("Horizontal Slash - Right (2 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = 0;
                        }
                        else if (Style[i].equals("Jumping Slash - Right (3 hits)")) {
                            TrueElement = RawElement * 2;
                            TrueSubElement = RawSubElement;
                        }
                        else if (Style[i].equals("Mid-Air Attack (2 hits)")) {
                            TrueElement = RawElement;
                            TrueSubElement = RawSubElement;
                        }
                    }
                    else if ((ElementCheck == 1) && (MotionCheck == 0)){
                        TrueElement = RawElement;
                        TrueSubElement = 0;
                        if (Style[i].equals("Draw/Dash Attack (6 hits)")) {
                            TrueElement = RawElement  * 5.4f;
                        }
                        else if (Style[i].equals("Demon Dodge Jump Attack (4 hits)")) {
                            TrueElement = RawElement  * 4;
                        }
                        else if (Style[i].equals("     -Follow Up (6 hits)")) {
                            TrueElement = RawElement * 4.2f;
                        }
                        else if (Style[i].equals("Upward Slash")) {
                            TrueElement = RawElement;
                        }
                        else if (Style[i].equals("Demon Combo (6 hits)")) {
                            TrueElement = RawElement * 4.2f;
                        }
                        else if (Style[i].equals("Demon Flurry Rush (6 hits)")) {
                            TrueElement = RawElement * 4.2f;
                        }
                        else if (Style[i].equals("     -Off Ledge (4 hits)")) {
                            TrueElement = RawElement * 4;
                        }
                        else if (Style[i].equals("Left Jumping Slash (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("     -Second Jump[L] (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("Right Jumping Slash (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("     -Second Jump[R] (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("Jump Attack (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Vault Attack (4 hits)")) {
                            TrueElement = RawElement * 4;
                        }
                        else if (Style[i].equals("Devil's Dance (12 hits)")) {
                            TrueElement = RawElement * 11.4f;
                        }
                        else if (Style[i].equals("Adept Evade (4 hits)")) {
                            TrueElement = RawElement * 4;
                        }
                        else if (Style[i].equals("Idle Slash (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Reverse Slash (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Circle Slash (3 hits)")) {
                            TrueElement = RawElement * 2.4f;
                        }
                        else if (Style[i].equals("Horizontal Slash - Left (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Horizontal Slash - Right (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Demon Flurry (7 hits)")) {
                            TrueElement = RawElement * 6.4f;
                        }

                        //Normal Mode If Statment

                        if (Style[i].equals("Draw Attack (4 hits)")) {
                            TrueElement = RawElement * 2.8f;
                        }
                        else if (Style[i].equals("Dash Attack (4 hits)")) {
                            TrueElement = RawElement * 2.8f;
                        }
                        else if (Style[i].equals("Upward Slash")) {
                            TrueElement = RawElement;
                        }
                        else if (Style[i].equals("Idle Slash (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Horizontal Slash - Left (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Jumping Slash - Left (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("Horizontal Slash - Right (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                        else if (Style[i].equals("Jumping Slash - Right (3 hits)")) {
                            TrueElement = RawElement * 3;
                        }
                        else if (Style[i].equals("Mid-Air Attack (2 hits)")) {
                            TrueElement = RawElement * 2;
                        }
                    }
                    else if (MotionCheck == 1){
                        if(ElementCheck == 2) {
                            TrueElement = RawElement / 2;
                            TrueSubElement = RawSubElement / 2;
                        }
                        else{
                            TrueElement = RawElement;
                            TrueSubElement = 0;
                        }
                    }

                    if(WolfsMawLevel1Check.isChecked()) WolfsMawModifier = 1.2f;
                    else if(WolfsMawLevel2Check.isChecked()) WolfsMawModifier = 1.25f;
                    else if(WolfsMawLevel3Check.isChecked()) WolfsMawModifier = 1.3f;
                    else if(WolfsMawOffCheck.isChecked()) WolfsMawModifier = 1f;
                    
                    if(MotionCheck == 0){
                        if(AirborneCheck.isChecked() && SkillCheck && (Style[i].contains("Vault") ||
                                Style[i].contains("Jump ") || Style[i].contains("Ledge")
                                || Style[i].contains("Follow Up") || Style[i].contains("Mid-Air")))
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        else Skills.setAirborneModifier(false);
                        TrueRaw = Skills.getTrueRaw(RawDamage * WolfsMawModifier, RawAffinity, SkillCheck) * Motion[i];
                    }
                    else{
                        if(AirborneCheck.isChecked() && SkillCheck && ChosenArt.contains("Aerial"))
                            Skills.setAirborneModifier(AirborneCheck.isChecked());
                        else Skills.setAirborneModifier(false);
                        TrueRaw = Skills.getTrueRaw(RawDamage * WolfsMawModifier, RawAffinity, SkillCheck) * ChosenHunterArt[i];
                    }
                    //-End-

                    //Hitzone Modification - Start

                    float RawModifiers = SelectedSharpnessAtkModifier * SkillSharpnessModifier;
                    float ElmModifiers = SelectedSharpnessElmModifier * SkillSharpnessModifier;

                    float ModifiedRawHitzone = (M.getRawHitzoneValue() * RawModifiers) / 100;
                    float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

                    //Skills.setElementAtkUp(ElementalAtkUpCheck.isChecked());
                    //Skills.setElementCrit(ElementalCritCheck.isChecked(), RawAffinity);

                    float ModifiedElmHitzone = (M.getElmHitzoneValue() * ElmModifiers) / 100;
                    float HitzoneElm = Skills.getTrueElm(TrueElement * WolfsMawModifier, SkillCheck) * ModifiedElmHitzone;

                    float ModifiedSubElmHitzone = (M.getSubElmHitzoneValue() * ElmModifiers) / 100;
                    float HitzoneSubElm = Skills.getTrueSubElm(TrueSubElement * WolfsMawModifier, SkillCheck) * ModifiedSubElmHitzone;

                    float CombinedElm = HitzoneElm + HitzoneSubElm;
                    //Hitzone Modification - End

                    if (!ChosenArt.equals("-None-"))
                        TrueAttack = HitzoneRaw + (CombinedElm * HunterArtElementCheck[i]);
                    else TrueAttack = HitzoneRaw + CombinedElm;

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
                    TextView Banner = findViewById(R.id.AttackBanner);
                    if(MotionCheck == 0){
                        textviews[i].setText(Style[i]);
                        Banner.setText("Attacks");
                    }
                    else{
                        textviews[i].setText(HunterArtsLevels[i]);
                        Banner.setText(ChosenArt);
                    }
                    Banner.setVisibility(View.VISIBLE);
                    textviews[i].setVisibility(View.VISIBLE);
                    /*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*/
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
    public static String getChosenSubElement(String i) {
        ChosenSubElement = i;
        return ChosenSubElement;
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