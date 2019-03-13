package com.mhx.marcus.mhgendamagecalc;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

    Spinner StyleSelect, SharpnessSelect, ElementSelect, SubElementSelect, MonsterSelect, HitzoneSelect,
            HunterArtSelect, PhialSelect, ShieldChargeSelect, NumberofPhialsSelect, ProwlerTypeSelect,
            NineLivesAttackSelect, SupportSkillsSelect, BoomerangSelect;
    Button Calculate;


    static String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, ChosenSubElement, MonsterType, ChosenArt;
    static float SelectedSharpnessElmModifier, SelectedSharpnessAtkModifier;

    SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    DamageCalculation DmgCalc;

    //Skill and Hunter Art Selection variables - Start
    Switch SkillSwitch;
    Boolean SkillCheck = false, SynergyCheck = false;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect, GroupSSelect;
    CheckBox AffinityOilCheck, PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck,
            FurorCheck, BludgeonerCheck, RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck,
            ElementalAtkUpCheck, AirborneCheck, FelyneBombardierCheck, WeaknessExploitCheck,

            //Prowler
            AttackUpSCheck, AttackUpLCheck, TriforceCheck, AffinityUpSCheck, AffinityUpLCheck,
            DemonHornCheck, RangedAttackUpCheck, LastStandCheck, FanalisCheck, UniversalCheck,
            FuryStateCheck, BeastModeCheck,WorldsStrongestCheck, BaddestCatEverCheck, WeaponUpgradeCheck;

    CheckBox CentreBladeBonusCheck;

    RadioButton ChaosOilLevel1_2Radio, ChaosOilLevel3Radio, ChaosOilOffRadio,
            LionsMawLevel1Check, LionsMawLevel2Check, LionsMawLevel3Check, LionsMawOffCheck,
            EvasiveManeuversOffCheck, EvasiveManeuversLevel2Check, EvasiveManeuversLevel3Check,
            WolfsMawLevel1Check, WolfsMawLevel2Check, WolfsMawLevel3Check, WolfsMawOffCheck;
    Float SkillSharpnessModifier = 1f, BrimstoneCounterModifier = 1f;
    Float[] BoomerangType;
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
        Calculate();

    }

    private void Stubs() {
        ViewStub AttackInfoStub, BaseStatsStub, MonsterHitzonesStub, HunterArtsStub, SkillsStub;
        //String SelectedWpn = getIntent().getStringExtra("Weapon");

        BaseStatsStub = findViewById(R.id.BaseStatsStub);
        MonsterHitzonesStub = findViewById(R.id.MonsterHitzonesStub);
        HunterArtsStub = findViewById(R.id.HunterArtsStub);
        SkillsStub = findViewById(R.id.SkillsStub);
        AttackInfoStub = findViewById(R.id.AttackInfoStub);

        MonsterHitzonesStub.setLayoutResource(R.layout.content_monster_hitzones);

        String SelectedWpn = "GS";
        switch (SelectedWpn) {
            case "GS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_gs);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Great Sword");
                break;

            case "LS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_ls);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_ls);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Long Sword");
                break;

            case "SNS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_sns);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_sns);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Sword and Shield");
                break;

            case "DB":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_db);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_db);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Dual Blades");
                break;

            case "Hammer":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_hh);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hammer);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Hammer");
                break;

            case "HH":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_hh);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hh);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Hunting Horn");
                break;

            case "Lance":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_lance);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_lance);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Lance");
                break;

            case "GL":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_gl);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_gl);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_cb);
                this.setTitle("Gunlance");
                break;

            case "SA":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_sa);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_sa);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_sa);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Switch Axe");
                break;

            case "CB":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_cb);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_cb);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_cb);
                this.setTitle("Charge Blade");
                break;

            case "IG":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_ig);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_ig);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Insect Glaive");
                break;

            case "LBG":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bg);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_lbg);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_bg);
                this.setTitle("Light Bowgun");
                break;

            case "HBG":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bg);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hbg);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_hbg);
                SkillsStub.setLayoutResource(R.layout.content_skills_bg);
                this.setTitle("Heavy Bowgun");
                break;

            case "Bow":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bow);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_bow);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_bow);
                SkillsStub.setLayoutResource(R.layout.content_skills_bow);
                this.setTitle("Bow");
                break;

            default:
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_prowler);
                HunterArtsStub.setLayoutResource(R.layout.content_support_skills);
                SkillsStub.setLayoutResource(R.layout.content_skills_prowler);
                this.setTitle("Prowler");
                break;
        }

        AttackInfoStub.inflate();
        BaseStatsStub.inflate();
        MonsterHitzonesStub.inflate();
        HunterArtsStub.inflate();
        SkillsStub.inflate();
    }

    private void SetUp() {

        String SelectedWpn = getIntent().getStringExtra("Weapon");

        PowercharmCheck = findViewById(R.id.PowercharmCheckBox);
        PowertalonCheck = findViewById(R.id.PowertalonCheckBox);

        if(!SelectedWpn.equals("Prowler")){
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

                    switch (String.valueOf(StyleSelect.getSelectedItem())) {
                        case "Guild":
                            Snackbar.make(view, "Selected Style: Guild", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        case "Striker":
                            Snackbar.make(view, "Selected Style: Striker", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        case "Aerial":
                            Snackbar.make(view, "Selected Style: Aerial", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        case "Adept":
                            Snackbar.make(view, "Selected Style: Adept", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        case "Valor":
                            Snackbar.make(view, "Selected Style: Valor", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;

                        default:
                            Snackbar.make(view, "Selected Style: Alchemy", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

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

            HunterArtSelect = findViewById(R.id.HunterArtSelect);
        }
        else{
            BoomerangSelect = findViewById(R.id.BoomerangSelect);
            ArrayAdapter BoomerangAdapter = ArrayAdapter.createFromResource(this,R.array.Boomerangs,android.R.layout.
                    simple_spinner_dropdown_item);
            BoomerangSelect.setAdapter(BoomerangAdapter);
            BoomerangSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch(String.valueOf(BoomerangSelect.getSelectedItem())){
                        case "Normal":
                            BoomerangType[0] = 0.08f;
                            BoomerangType[1] = 0.09f;
                            BoomerangType[2] = 0.34f;
                            break;
                        case "Big":
                            BoomerangType[0] = 0.12f;
                            BoomerangType[1] = 0.14f;
                            BoomerangType[2] = 0.38f;
                            break;
                        case "Pierce":
                            BoomerangType[0] = 0.17f;
                            BoomerangType[1] = 0.18f;
                            BoomerangType[2] = 0.5f;
                            break;
                        default:
                            BoomerangType[0] = 0.24f;
                            BoomerangType[1] = 0.26f;
                            BoomerangType[2] = 0.62f;
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            /*SupportSkillsSelect = (Spinner) findViewById(R.id.SupportSkillsSelect);
            ArrayAdapter SupportSkillsAdapter = ArrayAdapter.createFromResource(this,R.array.Prowler_HA_Names,
                    android.R.layout.simple_spinner_dropdown_item);

            SupportSkillsSelect.setAdapter(SupportSkillsAdapter);
            SupportSkillsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch(String.valueOf(SupportSkillsSelect.getSelectedItem())){
                        case "Anti-Monster Mine":
                            getHunterArt("Anti-Monster Mine");
                            break;
                        case "Anti-Monster Mine+":
                            getHunterArt("Anti-Monster Mine+");
                            break;
                        case "Claw Dance":
                            getHunterArt("Claw Dance");
                            break;
                        case "Explosive Roll":
                            getHunterArt("Explosive Roll");
                            break;
                        case "Felyne Comet":
                            getHunterArt("Felyne Comet");
                            break;
                        case "Mini Barrel Bombay":
                            getHunterArt("Mini Barrel Bombay");
                            break;
                        case "Barrel Bombay":
                            getHunterArt("Barrel Bombay");
                            break;
                        case "Bounce Bombay":
                            getHunterArt("Bounce Bombay");
                            break;
                        case "Big Barrel Bombay":
                            getHunterArt("Big Barrel Bombay");
                            break;
                        case "Mega Barrel Bombay":
                            getHunterArt("Mega Barrel Bombay");
                            break;
                        case "Giga Barrel Bombay":
                            getHunterArt("Giga Barrel Bombay");
                            break;
                        case "Rath-of-Meow":
                            getHunterArt("Rath-of-Meow");
                            break;
                        case "Shock Tripper":
                            getHunterArt("Shock Tripper");
                            break;
                        default:
                            getHunterArt("-None-");
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/

            ProwlerTypeSelect = findViewById(R.id.ProwlerTypeSelect);
            ArrayAdapter ProwlerTypeAdapter = ArrayAdapter.createFromResource(this,R.array.ProwlerType,
                    android.R.layout.simple_spinner_dropdown_item);
            ProwlerTypeSelect.setAdapter(ProwlerTypeAdapter);
            ProwlerTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (String.valueOf(ProwlerTypeSelect.getSelectedItem())){
                        case "Charisma":
                            getProwler(1);
                            break;
                        case "Fighting":
                            getProwler(0.9f);
                            break;
                        case "Protection":
                            getProwler(1.1f);
                            break;
                        case "Assisting":
                            getProwler(0.9f);
                            break;
                        case "Healing":
                            getProwler(0.7f);
                            break;
                        case "Bombing":
                            getProwler(1.2f);
                            break;
                        case "Gathering":
                            getProwler(0.7f);
                            break;
                        default:
                            getProwler(1);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            NineLivesAttackSelect = findViewById(R.id.NineLivesAttackSelect);
            ArrayAdapter NineLivesAttackAdapter = ArrayAdapter.createFromResource(this,R.array.NineLivesAttack,
                    android.R.layout.simple_spinner_dropdown_item);
            NineLivesAttackSelect.setAdapter(NineLivesAttackAdapter);
            NineLivesAttackSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch(String.valueOf(NineLivesAttackSelect.getSelectedItem())){
                        case "First Revive (+3)":
                            getNineLives(3);
                            break;
                        case "Second Revive (+6)":
                            getNineLives(6);
                            break;
                        case "Third Revive (+9)":
                            getNineLives(9);
                            break;
                        case "Fourth Revive (+12)":
                            getNineLives(12);
                            break;
                        case "Fifth Revive (+15)":
                            getNineLives(15);
                            break;
                        case "Sixth Revive (+18":
                            getNineLives(18);
                            break;
                        case "Seventh Revive (+21)":
                            getNineLives(21);
                            break;
                        case "Eighth Revive (+24)":
                            getNineLives(24);
                            break;
                        default:
                            getNineLives(0);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            AttackUpSCheck = findViewById(R.id.AttackUpSCheckBox);
            AttackUpLCheck = findViewById(R.id.AttackUpLCheckBox);
            TriforceCheck = findViewById(R.id.TriforceCheckBox);
            AffinityUpSCheck = findViewById(R.id.AffinityUpSCheckBox);
            AffinityUpLCheck = findViewById(R.id.AffinityUpLCheckBox);
            DemonHornCheck = findViewById(R.id.DemonHornCheckBox);
            RangedAttackUpCheck = findViewById(R.id.RangedAttackUpCheckBox);
            LastStandCheck = findViewById(R.id.LastStandCheckBox);
            FanalisCheck = findViewById(R.id.FanalisCheckBox);
            UniversalCheck = findViewById(R.id.UniversalCheckBox);
            WorldsStrongestCheck = findViewById(R.id.WorldsStrongestCheckBox);
            BaddestCatEverCheck = findViewById(R.id.BaddestCatEverCheckBox);
            FuryStateCheck = findViewById(R.id.FuryStateCheck);
            BeastModeCheck = findViewById(R.id.BeastModeCheck);
            WeaponUpgradeCheck = findViewById(R.id.WeaponUpgradeCheckBox);

            HunterArtSelect = findViewById(R.id.SupportSkillsSelect);
        }

        if(!SelectedWpn.equals("LBG") || !SelectedWpn.equals("HBG")){
            if(!SelectedWpn.equals("Bow")){
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
            }

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

            if(SelectedWpn.equals("DB")){
                SubElementSelect = findViewById(R.id.SubElementSelect);

                ArrayAdapter SubElementAdapter = ArrayAdapter.createFromResource(this,R.array.SubElement,
                        android.R.layout.simple_spinner_dropdown_item);

                SubElementSelect.setAdapter(SubElementAdapter);

                SubElementSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (String.valueOf(ElementSelect.getSelectedItem())) {
                            case "[Fire]":
                                getChosenSubElement("Fire");
                                break;

                            case "[Water]":
                                getChosenSubElement("Water");
                                break;

                            case "[Ice]":
                                getChosenSubElement("Ice");
                                break;

                            case "[Thunder]":
                                getChosenSubElement("Thunder");
                                break;

                            case "[Dragon]":
                                getChosenSubElement("Dragon");
                                break;

                            default:
                                getChosenSubElement("NONE");
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }

        if(SelectedWpn.equals("CB") || SelectedWpn.equals("GL")){
            if(SelectedWpn.equals("CB")){
                PhialSelect = findViewById(R.id.CBPhialSelect);
                ArrayAdapter PhialAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhial,
                        android.R.layout.simple_spinner_dropdown_item);

                PhialSelect.setAdapter(PhialAdapter);
                PhialSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(String.valueOf(PhialSelect.getSelectedItem())){
                            case "Impact":
                                SelectedPhial = ImpactBurstMotion;
                                isImpact(true);
                                break;
                            default:
                                SelectedPhial = ElmBurstMotion;
                                isImpact(false);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                NumberofPhialsSelect = findViewById(R.id.NumberofCBPhialsSelect);
                ArrayAdapter NumberofPhialsAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhialNumber,
                        android.R.layout.simple_spinner_dropdown_item);

                NumberofPhialsSelect.setAdapter(NumberofPhialsAdapter);
                NumberofPhialsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(Integer.parseInt(String.valueOf(NumberofPhialsSelect.getSelectedItem()))){
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            default:
                                break;
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ShieldChargeSelect = (Spinner) findViewById(R.id.ShieldChargeSelect);
                ArrayAdapter ShieldChargeAdapter = ArrayAdapter.createFromResource(this,R.array.CBShieldCharge,
                        android.R.layout.simple_spinner_dropdown_item);

                ShieldChargeSelect.setAdapter(ShieldChargeAdapter);
                ShieldChargeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(String.valueOf(SharpnessSelect.getSelectedItem())){
                            case "No Charge":
                                Skills.setCBPhialModifier(false);
                                getChargeModifier(1f);
                                getRedCheck(false);
                                getYellowCheck(false);
                                break;
                            case "Yellow Charge":
                                Skills.setCBPhialModifier(true);
                                getChargeModifier(1f);
                                getRedCheck(false);
                                getYellowCheck(true);
                                break;
                            case "Red Charge":
                                Skills.setCBPhialModifier(true);
                                getRedCheck(true);
                                getYellowCheck(false);
                                break;
                            default:
                                Skills.setCBPhialModifier(true);
                                getRedCheck(true);
                                getYellowCheck(false);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            FelyneBombardierCheck = findViewById(R.id.FelyneBombardierCheckBox);

            GroupSSelect = findViewById(R.id.GroupSSelect);

            ArrayAdapter GroupSAdapter = ArrayAdapter.createFromResource(this,R.array.GroupS,
                    android.R.layout.simple_spinner_dropdown_item);

            GroupSSelect.setAdapter(GroupSAdapter);

            GroupSSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    switch(String.valueOf(GroupSSelect.getSelectedItem())){
                        case "Artillery Novice":
                            Skills.setArtilleryModifier(1.3f);
                            break;
                        case "Artillery Expert":
                            Skills.setArtilleryModifier(1.35f);
                            break;
                        default:
                            Skills.setArtilleryModifier(1f);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        switch(SelectedWpn){
            case "GS":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.GS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "LS":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.LS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "SNS":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SNS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "DB":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.DB_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Hammer":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Hammer_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HH":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.HH_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Lance":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Lance_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "GL":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.GL_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "SA":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SA_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "CB":
                PhialSelect = findViewById(R.id.CBPhialSelect);
                ArrayAdapter PhialAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhial,
                        android.R.layout.simple_spinner_dropdown_item);

                PhialSelect.setAdapter(PhialAdapter);
                PhialSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(String.valueOf(PhialSelect.getSelectedItem())){
                            case "Impact":
                                SelectedPhial = ImpactBurstMotion;
                                isImpact(true);
                                break;
                            default:
                                SelectedPhial = ElmBurstMotion;
                                isImpact(false);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                NumberofPhialsSelect = findViewById(R.id.NumberofCBPhialsSelect);
                ArrayAdapter NumberofPhialsAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhialNumber,
                        android.R.layout.simple_spinner_dropdown_item);

                NumberofPhialsSelect.setAdapter(NumberofPhialsAdapter);
                NumberofPhialsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(Integer.parseInt(String.valueOf(NumberofPhialsSelect.getSelectedItem()))){
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                            case 9:
                                break;
                            default:
                                break;
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ShieldChargeSelect = (Spinner) findViewById(R.id.ShieldChargeSelect);
                ArrayAdapter ShieldChargeAdapter = ArrayAdapter.createFromResource(this,R.array.CBShieldCharge,
                        android.R.layout.simple_spinner_dropdown_item);

                ShieldChargeSelect.setAdapter(ShieldChargeAdapter);
                ShieldChargeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch(String.valueOf(SharpnessSelect.getSelectedItem())){
                            case "No Charge":
                                Skills.setCBPhialModifier(false);
                                getChargeModifier(1f);
                                getRedCheck(false);
                                getYellowCheck(false);
                                break;
                            case "Yellow Charge":
                                Skills.setCBPhialModifier(true);
                                getChargeModifier(1f);
                                getRedCheck(false);
                                getYellowCheck(true);
                                break;
                            case "Red Charge":
                                Skills.setCBPhialModifier(true);
                                getRedCheck(true);
                                getYellowCheck(false);
                                break;
                            default:
                                Skills.setCBPhialModifier(true);
                                getRedCheck(true);
                                getYellowCheck(false);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            case "IG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.IG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "LBG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.LBG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HBG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.HBG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Bow":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Bow_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            default:
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Prowler_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
        }
















        ArrayAdapter HunterArtsAdapter;

        switch(SelectedWpn){
            case "GS":
                LionsMawOffCheck = findViewById(R.id.LionsMawOffCheck);
                LionsMawLevel1Check = findViewById(R.id.LionsMawLevel1Check);
                LionsMawLevel2Check = findViewById(R.id.LionsMawLevel2Check);
                LionsMawLevel3Check = findViewById(R.id.LionsMawLevel3Check);
                CentreBladeBonusCheck = findViewById(R.id.CentreBladeCheck);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.GS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "LS":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.LS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "SNS":
                AffinityOilCheck = findViewById(R.id.AffinityOilCheck);
                ChaosOilOffRadio = findViewById(R.id.ChaosOilOffRadio);
                ChaosOilLevel1_2Radio = findViewById(R.id.ChaosOilLevel1_2Radio);
                ChaosOilLevel3Radio = findViewById(R.id.ChaosOilLevel3Radio);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SNS_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "DB":
                WolfsMawOffCheck = (RadioButton) findViewById(R.id.WolfsMawOffCheck);
                WolfsMawLevel1Check = (RadioButton) findViewById(R.id.WolfsMawLevel1Check);
                WolfsMawLevel2Check = (RadioButton) findViewById(R.id.WolfsMawLevel2Check);
                WolfsMawLevel3Check = (RadioButton) findViewById(R.id.WolfsMawLevel3Check);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.DB_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Hammer":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Hammer_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HH":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.HH_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Lance":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Lance_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "GL":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.GL_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "SA":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SA_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "CB":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.CB_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "IG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.IG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "LBG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.LBG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HBG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.HBG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Bow":
                EvasiveManeuversOffCheck = findViewById(R.id.EvasiveManeuversOffCheck);
                EvasiveManeuversLevel2Check = findViewById(R.id.EvasiveManeuversLevel2Check);
                EvasiveManeuversLevel3Check = findViewById(R.id.EvasiveManeuversLevel3Check);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Bow_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            default:
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Prowler_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
        }

        HunterArtSelect.setAdapter(HunterArtsAdapter);
        HunterArtSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getHunterArt(String.valueOf(HunterArtSelect.getSelectedItem()));
                SynergyCheck = !(String.valueOf(HunterArtSelect.getSelectedItem()).equals("Lions Maw (Wide Slash)"));
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
                        Skills.setGroupDSharp(1f);
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Might Pill (+25)":
                        Skills.setGroupD(25f);
                        Skills.setGroupDSharp(1f);
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Exciteshroom - Mycology (+10)":
                        Skills.setGroupD(10f);
                        Skills.setGroupDSharp(1f);
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Demon Horn (+10)":
                        Skills.setGroupD(10f);
                        Skills.setGroupDSharp(1f);
                        Skills.setGroupDCrit(0f);
                        break;

                    case "Demon S (+10)":
                        Skills.setGroupD(10f);
                        Skills.setGroupDSharp(1.1f);
                        Skills.setGroupDCrit(0f);
                        Snackbar.make(view, "10% Sharpness Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Demon Affinity S (+15)":
                        Skills.setGroupD(15f);
                        Skills.setGroupDSharp(1.1f);
                        Skills.setGroupDCrit(10f);
                        Snackbar.make(view, "10% Sharpness Increase\n10% Affinity Increase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    case "Cool Cat (+15)":
                        Skills.setGroupD(15f);
                        Skills.setGroupDSharp(1f);
                        Skills.setGroupDCrit(0f);
                        break;

                    default:
                        Skills.setGroupD(0f);
                        Skills.setGroupDSharp(1f);
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
        ArrayAdapter MonsterAdapter = ArrayAdapter.createFromResource(this,R.array.Monsters,android.R.layout.
                simple_spinner_dropdown_item);
        /*Sets the adapter (array) values to the drop down menu.*/
        MonsterSelect.setAdapter(MonsterAdapter);
        /*Tells the drop down menu to wait for an item to be selected before calling a method
         (function) in this class.*/
        MonsterSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String SelectedMonster;
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

                TextView Damage = findViewById(R.id.DamageInput);
                TextView Element = findViewById(R.id.ElementInput);
                TextView SubElement;
                TextView Affinity = findViewById(R.id.AffinityInput);

                if(TextUtils.isEmpty(Damage.getText().toString())){
                    Damage.setText("0");
                }
                if(TextUtils.isEmpty(Element.getText().toString())){
                    Element.setText("0");
                }
                if(TextUtils.isEmpty(Affinity.getText().toString())){
                    Affinity.setText("0");
                }

                //if(getIntent().getStringExtra("Weapon").equals("DB"))
                    //SubElement = findViewById(R.id.SubElementInputDB);

                switch(getIntent().getStringExtra("Weapon")){
                    case "DB":
                        SubElement = findViewById(R.id.SubElementInputDB);
                        if(TextUtils.isEmpty(SubElement.getText().toString())){
                            SubElement.setText("0");
                        }

                        DmgCalc = new DamageCalculation(UI.this,UI.this, "e",
                                String.valueOf(StyleSelect.getSelectedItem()),
                                String.valueOf(SharpnessSelect.getSelectedItem()),
                                Float.parseFloat(Damage.getText().toString()), ChosenElement,
                                Float.parseFloat(SubElement.getText().toString()), ChosenSubElement,
                                Float.parseFloat(Element.getText().toString()),
                                Float.parseFloat(Affinity.getText().toString()),
                                ChosenMonster, HitzoneGroup, ChosenHitzone);

                        if(!DmgCalc.Stats.isValid()){
                            if(!DmgCalc.Stats.isValidAtk()){
                                Damage.setError("Enter a valid attack");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidElm()){
                                Element.setError("Enter a valid element");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidSubElm()){
                                SubElement.setError("Enter a valid sub element");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidAffinity()){
                                Affinity.setError("Enter a valid affinity");
                                return;
                            }
                        }
                        break;

                    default:
                        DmgCalc = new DamageCalculation(UI.this,UI.this, "e",
                                String.valueOf(StyleSelect.getSelectedItem()),
                                String.valueOf(SharpnessSelect.getSelectedItem()),
                                Float.parseFloat(Damage.getText().toString()), ChosenElement,
                                Float.parseFloat(Element.getText().toString()),
                                Float.parseFloat(Affinity.getText().toString()),
                                ChosenMonster, HitzoneGroup, ChosenHitzone);

                        if(!DmgCalc.Stats.isValid()){
                            if(!DmgCalc.Stats.isValidAtk()){
                                Damage.setError("Enter a valid attack");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidElm()){
                                Element.setError("Enter a valid element");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidAffinity()){
                                Affinity.setError("Enter a valid affinity");
                                return;
                            }
                        }
                        break;
                }



                if (DmgCalc.CalculateSkills()){
                    Snackbar.make(view, "Please check your inputted element/skills", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }


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

}