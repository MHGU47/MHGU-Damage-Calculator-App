package com.mhx.marcus.mhgendamagecalc;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
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

import java.util.Arrays;
import java.util.List;

public class UI extends AppCompatActivity {

    Spinner StyleSelect, SharpnessSelect, ElementSelect, MonsterSelect, HitzoneSelect, HunterArtSelect;
    Button Calculate;
    String SelectedMonster;

    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, MonsterType, ChosenArt;
    static float SelectedSharpnessElmModifier, SelectedSharpnessAtkModifier;

    //Skill and Hunter Art Selection variables - Start
    Switch SkillSwitch;
    Boolean SkillCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect;
    CheckBox AffinityOilCheck, PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck,
            FurorCheck, BludgeonerCheck, RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck,
            AirborneCheck,ElementalAtkUpCheck, WeaknessExploitCheck;
    RadioButton ChaosOilLevel1_2Radio, ChaosOilLevel3Radio, ChaosOilOffRadio;
    Float SkillSharpnessModifier = 1f;
    //-End-

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Stubs();
        SetUp();
        Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();

        //Defines the checkboxes for the skills.
        PowercharmCheck = findViewById(R.id.PowercharmCheckBox);
        PowertalonCheck = findViewById(R.id.PowertalonCheckBox);
        FelyneBoosterCheck = findViewById(R.id.FelyneBoosterCheckBox);
        CrisisCheck = findViewById(R.id.CrisisCheckBox);
        FurorCheck = findViewById(R.id.FurorCheckBox);
        BludgeonerCheck = findViewById(R.id.BludgeonerCheckBox);
        RepeatOffenderCheck = findViewById(R.id.RepeatOffenderCheckBox);
        CriticalBoostCheck = findViewById(R.id.CriticalBoostCheckBox);
        ElementalCritCheck = findViewById(R.id.ElementalCritCheckBox);
        ElementalAtkUpCheck = findViewById(R.id.ElementalAtkUpCheckBox);
        WeaknessExploitCheck = findViewById(R.id.WeaknessExploitCheckBox);
        AirborneCheck = findViewById(R.id.AirborneCheckBox);

        Calculate();

    }

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    private void Stubs() {
        ViewStub AttackInfoStub, BaseStatsStub, MonsterHitzonesStub, HunterArtsStub, SkillsStub;
        //String SelectedWpn = getIntent().getStringExtra("Weapon");
        String SelectedWpn = "GS";
        switch (SelectedWpn) {
            case "GS":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Great Sword");
                break;

            case "LS":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Long Sword");
                break;

            case "SNS":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Sword and Shield");
                break;

            case "DB":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Dual Blades");
                break;

            case "Hammer":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Hammer");
                break;

            case "HH":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Hunting Horn");
                break;

            case "Lance":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Lance");
                break;

            case "GL":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Gunlance");
                break;

            case "SA":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Switch Axe");
                break;

            case "CB":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Charge Blade");
                break;

            case "IG":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Insect Glaive");
                break;

            case "LBG":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Light Bowgun");
                break;

            case "HBG":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Heavy Bowgun");
                break;

            case "Bow":
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Bow");
                break;

            default:
                AttackInfoStub = findViewById(R.id.AttackInfoStub);
                BaseStatsStub = findViewById(R.id.BaseStatsStub);
                HunterArtsStub = findViewById(R.id.HunterArtsStub);
                SkillsStub = findViewById(R.id.SkillsStub);
                this.setTitle("Prowler");
                break;
        }

        MonsterHitzonesStub = findViewById(R.id.MonsterHitzonesStub);

        AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
        BaseStatsStub.setLayoutResource(R.layout.content_stats_input_sns);
        MonsterHitzonesStub.setLayoutResource(R.layout.content_monster_hitzones);
        HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_sns);
        SkillsStub.setLayoutResource(R.layout.content_skills);

        AttackInfoStub.inflate();
        BaseStatsStub.inflate();
        MonsterHitzonesStub.inflate();
        HunterArtsStub.inflate();
        SkillsStub.inflate();
    }

    private void SetUp() {
        /*Gives the variable for the spinner 'StyleSelect' the actual value for a spinner.*/
        StyleSelect = findViewById(R.id.StyleSelect);

        /*Gives the spinner a place to pull values from. In this case it's using the values from the
        'Styles' array and tells it where to place it on the layout*/
        ArrayAdapter StyleAdapter = ArrayAdapter.createFromResource(this,R.array.Styles,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        StyleSelect.setAdapter(StyleAdapter);

        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        StyleSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String Style = String.valueOf(StyleSelect.getSelectedItem());

                switch (String.valueOf(StyleSelect.getSelectedItem())) {
                    case "Guild":
                        Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;

                    case "Striker":
                        Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;

                    case "Aerial":
                        Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;

                    case "Adept":
                        Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;

                    case "Valor":
                        Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;

                    default:
                        Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //SelectedStyle = Styles[5];
                        //Motion = AlchemyStyleMotion;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SharpnessSelect = findViewById(R.id.SharpnessSelect);

        ArrayAdapter SharpnessAdapter = ArrayAdapter.createFromResource(this,R.array.Sharpness,
                android.R.layout.simple_spinner_dropdown_item);

        SharpnessSelect.setAdapter(SharpnessAdapter);

        SharpnessSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(SharpnessSelect.getSelectedItem())) {
                    case "Red":
                        //getAtk(SharpModAtk[0]);
                        //getElm(SharpModElm[0]);
                        Skills.getBludgeonerModifier(30f);
                        break;

                    case "Orange":
                        //getAtk(SharpModAtk[1]);
                        //getElm(SharpModElm[1]);
                        Skills.getBludgeonerModifier(30f);
                        break;

                    case "Yellow":
                        //getAtk(SharpModAtk[2]);
                        //getElm(SharpModElm[2]);
                        Skills.getBludgeonerModifier(25f);
                        break;

                    case "Green":
                        //getAtk(SharpModAtk[3]);
                        //getElm(SharpModElm[3]);
                        Skills.getBludgeonerModifier(15f);
                        break;

                    case "Blue":
                        //getAtk(SharpModAtk[4]);
                        //getElm(SharpModElm[4]);
                        Skills.getBludgeonerModifier(0f);
                        break;

                    case "White":
                        //getAtk(SharpModAtk[5]);
                        //getElm(SharpModElm[5]);
                        Skills.getBludgeonerModifier(0f);
                        break;

                    default:
                        //getAtk(SharpModAtk[6]);
                        //getElm(SharpModElm[6]);
                        Skills.getBludgeonerModifier(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ElementSelect = findViewById(R.id.ElementSelect);

        ArrayAdapter ElementAdapter = ArrayAdapter.createFromResource(this,R.array.Element,
                android.R.layout.simple_spinner_dropdown_item);

        ElementSelect.setAdapter(ElementAdapter);

        ElementSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(ElementSelect.getSelectedItem())) {
                    case "Fire":
                        getChosenElement("Fire");
                        break;

                    case "Water":
                        getChosenElement("Water");
                        break;

                    case "Ice":
                        getChosenElement("Ice");
                        break;

                    case "Thunder":
                        getChosenElement("Thunder");
                        break;

                    case "Dragon":
                        getChosenElement("Dragon");
                        break;

                    default:
                        getChosenElement("NONE");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        HunterArtSelect = findViewById(R.id.HunterArtSelect);


        //TODO: Alter this to change based on the weapon chosen
        ArrayAdapter HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SwordandShield,
                android.R.layout.simple_spinner_dropdown_item);

        HunterArtSelect.setAdapter(HunterArtsAdapter);

        HunterArtSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getHunterArt(String.valueOf(HunterArtSelect.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupC_1Select = findViewById(R.id.GroupC_1Select);

        ArrayAdapter GroupC_1Adapter = ArrayAdapter.createFromResource(this,R.array.GroupC_1,
                android.R.layout.simple_spinner_dropdown_item);

        GroupC_1Select.setAdapter(GroupC_1Adapter);

        GroupC_1Select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupC_1Select.getSelectedItem())) {
                    case "Demondrug (+5)":
                        Skills.setGroupC_1(5f);
                        break;

                    case "Mega Demondrug (+7)":
                        Skills.setGroupC_1(7f);
                        break;

                    default:
                        Skills.setGroupC_1(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupC_2Select = findViewById(R.id.GroupC_2Select);

        ArrayAdapter GroupC_2Adapter = ArrayAdapter.createFromResource(this,R.array.GroupC_2,
                android.R.layout.simple_spinner_dropdown_item);
        GroupC_2Select.setAdapter(GroupC_2Adapter);

        GroupC_2Select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupC_2Select.getSelectedItem())) {
                    case "Attack Up S - Meal (+3)":
                        Skills.setGroupC_2(3f);
                        break;

                    case "Attack Up M - Meal (+5)":
                        Skills.setGroupC_2(5f);
                        break;

                    case "Attack Up L - Meal (+7)":
                        Skills.setGroupC_2(7f);
                        break;

                    default:
                        Skills.setGroupC_2(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupDSelect = findViewById(R.id.GroupDSelect);

        ArrayAdapter GroupDAdapter = ArrayAdapter.createFromResource(this,R.array.GroupD,
                android.R.layout.simple_spinner_dropdown_item);

        GroupDSelect.setAdapter(GroupDAdapter);

        GroupDSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupDSelect.getSelectedItem())) {
                    case "Might Seed (+10)":
                        Skills.setGroupD(10f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Might Pill (+25)":
                        Skills.setGroupD(25f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Exciteshroom - Mycology (+10)":
                        Skills.setGroupD(10f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Demon Horn (+10)":
                        Skills.setGroupD(10f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Demon S (+10)":
                        Skills.setGroupD(10f);

                        SkillSharpnessModifier = 1.1f;
                        Skills.setGroupDCrit(0f);
                        Snackbar.make(view, "10% Sharpness Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Demon Affinity S (+15)":
                        Skills.setGroupD(15f);

                        SkillSharpnessModifier = 1.1f;
                        Skills.setGroupDCrit(10f);
                        Snackbar.make(view, "10% Sharpness Increase\n10% Affinity Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Cool Cat (+15)":
                        Skills.setGroupD(15f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;

                    default:
                        Skills.setGroupD(0f);

                        SkillSharpnessModifier = 1f;
                        Skills.setGroupDCrit(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupFSelect = findViewById(R.id.GroupFSelect);

        ArrayAdapter GroupFAdapter = ArrayAdapter.createFromResource(this,R.array.GroupF,
                android.R.layout.simple_spinner_dropdown_item);

        GroupFSelect.setAdapter(GroupFAdapter);

        GroupFSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                
                switch (String.valueOf(GroupFSelect.getSelectedItem())) {
                    case "Attack Up S (+10)":
                        Skills.setGroupF(10f);
                        break;

                    case "Attack Up M (+15)":
                        Skills.setGroupF(15f);
                        break;

                    case "Attack Up L (+20)":
                        Skills.setGroupF(20f);
                        break;

                    default:
                        Skills.setGroupF(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupGSelect = findViewById(R.id.GroupGSelect);

        ArrayAdapter GroupGAdapter = ArrayAdapter.createFromResource(this,R.array.GroupG,
                android.R.layout.simple_spinner_dropdown_item);

        GroupGSelect.setAdapter(GroupGAdapter);

        GroupGSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupGSelect.getSelectedItem())) {
                    case "Adrenaline +2 (+30%)":
                        Skills.setGroupG(1.3f);
                        break;

                    case "Felyne Heroics (+35%)":
                        Skills.setGroupG(1.35f);
                        break;

                    default:
                        Skills.setGroupG(1f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupHSelect = findViewById(R.id.GroupHSelect);

        ArrayAdapter GroupHAdapter = ArrayAdapter.createFromResource(this,R.array.GroupH,
                android.R.layout.simple_spinner_dropdown_item);

        GroupHSelect.setAdapter(GroupHAdapter);

        GroupHSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupHSelect.getSelectedItem())) {
                    case "Attack Up S - Hunting Horn (+10%)":
                        Skills.setGroupH(1.1f);
                        break;

                    case "Attack Up S - Hunting Horn (Recital) (+15%)":
                        Skills.setGroupH(1.15f);
                        break;

                    case "Attack Up L - Hunting Horn (+15%)":
                        Skills.setGroupH(1.15f);
                        break;

                    case "Attack Up L - Hunting Horn (Recital) (+20%)":
                        Skills.setGroupH(1.2f);
                        break;

                    default:
                        Skills.setGroupH(1f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupISelect = findViewById(R.id.GroupISelect);

        ArrayAdapter GroupIAdapter = ArrayAdapter.createFromResource(this,R.array.GroupI,
                android.R.layout.simple_spinner_dropdown_item);

        GroupISelect.setAdapter(GroupIAdapter);

        GroupISelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupISelect.getSelectedItem())) {
                    case "Fortify - First Cart (+10%)":
                        Skills.setGroupI(1.1f);
                        break;

                    case "Fortify - Second Cart (+20%)":
                        Skills.setGroupI(1.2f);
                        break;

                    default:
                        Skills.setGroupI(1f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupJSelect = findViewById(R.id.GroupJSelect);

        ArrayAdapter GroupJAdapter = ArrayAdapter.createFromResource(this,R.array.GroupJ,
                android.R.layout.simple_spinner_dropdown_item);

        GroupJSelect.setAdapter(GroupJAdapter);

        GroupJSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupJSelect.getSelectedItem())) {
                    case "Challenger +1 (+10%)":
                        Skills.setGroupJ_1(1.1f);
                        Skills.setGroupJ_2(0f);

                        Skills.setGroupJCrit(10f);
                        Snackbar.make(view, "10% Affinity Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Challenger +2 (+20%)":
                        Skills.setGroupJ_1(1.2f);
                        Skills.setGroupJ_2(0f);

                        Skills.setGroupJCrit(15f);
                        Snackbar.make(view, "15% Affinity Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Unscathed (+20)":
                        Skills.setGroupJ_1(1f);
                        Skills.setGroupJ_2(20f);

                        Skills.setGroupJCrit(0f);
                        break;

                    case "Latent Power +1":
                        Skills.setGroupJ_1(1f);
                        Skills.setGroupJ_2(0f);

                        Skills.setGroupJCrit(30f);
                        Snackbar.make(view, "30% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Latent Power +2":
                        Skills.setGroupJ_1(1f);
                        Skills.setGroupJ_2(0f);

                        Skills.setGroupJCrit(50f);
                        Snackbar.make(view, "50% Affinity Increase (No raw damage increase)", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    default:
                        Skills.setGroupJ_1(1f);
                        Skills.setGroupJ_2(0f);

                        Skills.setGroupJCrit(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupKSelect = findViewById(R.id.GroupKSelect);

        ArrayAdapter GroupKAdapter = ArrayAdapter.createFromResource(this,R.array.GroupK,
                android.R.layout.simple_spinner_dropdown_item);

        GroupKSelect.setAdapter(GroupKAdapter);

        GroupKSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupKSelect.getSelectedItem())) {
                    case "Cold Blooded - Cold Area (+15)":
                        Skills.setGroupK(15f);
                        break;

                    case "Cold Blooded - Cold Drink (+5)":
                        Skills.setGroupK(5f);
                        break;

                    case "Cold Blooded - Cold Area + Cold Drink (+20)":
                        Skills.setGroupK(20f);
                        break;

                    case "Hot Blooded - Hot Area (+15)":
                        Skills.setGroupK(15f);
                        break;

                    default:
                        Skills.setGroupK(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupOSelect = findViewById(R.id.GroupOSelect);

        ArrayAdapter GroupOAdapter = ArrayAdapter.createFromResource(this,R.array.GroupO,
                android.R.layout.simple_spinner_dropdown_item);

        GroupOSelect.setAdapter(GroupOAdapter);

        GroupOSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupOSelect.getSelectedItem())) {
                    case "Critical Eye +1":
                        Skills.setGroupO(10f);
                        break;

                    case "Critical Eye +2":
                        Skills.setGroupO(20f);
                        break;

                    case "Critical Eye +3":
                        Skills.setGroupO(30f);
                        break;

                    default:
                        Skills.setGroupK(0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GroupPSelect = findViewById(R.id.GroupPSelect);

        ArrayAdapter GroupPAdapter = ArrayAdapter.createFromResource(this,R.array.GroupP,
                android.R.layout.simple_spinner_dropdown_item);

        GroupPSelect.setAdapter(GroupPAdapter);

        GroupPSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (String.valueOf(GroupPSelect.getSelectedItem())) {
                    case "[Element] Atk Up +1":
                        Skills.setGroupP(4,1.05f);
                        break;

                    case "[Element] Atk Up +2":
                        Skills.setGroupP(6,1.1f);
                        break;

                    default:
                        Skills.setGroupP(0,1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Gives the variable for the spinner 'MonsterSelect' the actual value for a spinner.*/
        MonsterSelect = findViewById(R.id.MonsterSelect);

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

            private void change(){
                String Stripped = SelectedMonster.replaceAll("\\s","");

                int Counter = getResources().getIdentifier(Stripped + "Hitzones", "array", getPackageName());

                switch(MonsterType){
                    case "Beast":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.BeastHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter);
                        getHitzoneGroup("Beast");

                        select();

                        break;

                    case "FlyingWyvern":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter2);
                        getHitzoneGroup("FlyingWyvern");

                        select();

                        break;

                    case "FlyingWyvernWounded":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernWoundedHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter3);
                        getHitzoneGroup("FlyingWyvernWounded");

                        select();

                        break;

                    case "Raptor":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.RaptorHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter4);
                        getHitzoneGroup("Raptor");

                        select();

                        break;

                    case "Malfestio":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.MalfestioHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter5);
                        getHitzoneGroup("Malfestio");

                        select();

                        break;

                    case "None":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.NoneHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter6);
                        getHitzoneGroup("None");

                        select();

                        break;

                    default:
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(getApplicationContext(),
                                Counter,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter7);
                        getHitzoneGroup(Stripped);

                        select();

                        break;

                }
            }

            private void select(){

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
    }

    private void Calculate(){
        Calculate = findViewById(R.id.CalculateButton);
        Calculate.setOnClickListener(new View.OnClickListener() {

            TextView[] textviews = new TextView[(62)];

            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(UI.this.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
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

}
