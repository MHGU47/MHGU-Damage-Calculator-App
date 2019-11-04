package com.mhx.marcus.mhgendamagecalc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UI extends AppCompatActivity {

    Spinner StyleSelect, SharpnessSelect, ElementSelect, SubElementSelect, MonsterSelect, HitzoneSelect,
            HunterArtSelect, PhialSelect, ShieldChargeSelect, NumberofPhialsSelect, SpiritGaugeColourSelect,
            DBModeSelect, ShotTypeSelect, ShotLevelSelect, HeatGaugeSelect, NumberofShellsSelect,
            ExtractSelect, SelectedShot, DistanceSelect,
            ProwlerTypeSelect, NineLivesAttackSelect, SupportSkillsSelect, BoomerangSelect;
    Button Calculate;


    String ChosenMonster, ChosenHitzone, HitzoneGroup, ChosenElement, ChosenSubElement, Sharpness,
            ChosenStyle, MonsterType, ChosenArt, DBMode, SpiritGaugeColour;

    public SkillsCalculation Skills = new SkillsCalculation();
    //Creates an instance of 'SkillsCalculation' so it's functions for calculating skills can be used

    private DamageCalculation DmgCalc;
    private RelativeLayout AttackInfo, ExtraInfo;

    //Skill and Hunter Art Selection variables - Start
    Switch SkillSwitch;
    Boolean SkillCheck = false, SynergyCheck = false, isImpact = true;
    Spinner GroupC_1Select, GroupC_2Select, GroupDSelect, GroupFSelect, GroupGSelect, GroupHSelect,
            GroupISelect, GroupJSelect, GroupKSelect, GroupOSelect, GroupPSelect, GroupSSelect;
    CheckBox AffinityOilCheck, PowercharmCheck, PowertalonCheck, FelyneBoosterCheck, CrisisCheck,
            FurorCheck, BludgeonerCheck, RepeatOffenderCheck, CriticalBoostCheck, ElementalCritCheck,
            ElementalAtkUpCheck, AirborneCheck, FelyneBombardierCheck, WeaknessExploitCheck,

            CentreBladeBonusCheck,//Great/Long Sword

            MaxSpiritGaugeCheck,//Long Sword

            ProvokeCheck,//Hammer

            DragonBreathCheck,//Gunlance

            TempestAxeCheck, AwakenedCheck,//Switch Axe

            AerialShotSelect, PowerReloadCheck,//Light/Heavy Bowgun

            RapidFireCheck,//Light Bowgun

            GunpowderInfusionCheck,//Heavy Bowgun

            //Prowler
            AttackUpSCheck, AttackUpLCheck, TriforceCheck, AffinityUpSCheck, AffinityUpLCheck,
            DemonHornCheck, RangedAttackUpCheck, LastStandCheck, FanalisCheck, UniversalCheck,
            FuryStateCheck, BeastModeCheck,WorldsStrongestCheck, BaddestCatEverCheck, WeaponUpgradeCheck;

    RadioButton ChaosOilLevel1_2Radio, ChaosOilLevel3Radio, ChaosOilOffRadio,
            LionsMawLevel1Check, LionsMawLevel2Check, LionsMawLevel3Check, LionsMawOffCheck,
            EvasiveManeuversOffCheck, EvasiveManeuversLevel2Check, EvasiveManeuversLevel3Check,
            WolfsMawLevel1Check, WolfsMawLevel2Check, WolfsMawLevel3Check, WolfsMawOffCheck,
            SacrificialBladeLevel1Check, SacrificialBladeLevel2Check, SacrificialBladeLevel3Check,
            SacrificialBladeOffCheck, EnragedGuardRed, EnragedGuardYellow, EnragedGuardOrange,
            NoEnragedGuardAura, DemonRiotLevel1Check, DemonRiotLevel2Check, DemonRiotLevel3Check,
            DemonRiotOffCheck, EnergyChargeLevel2Check, EnergyChargeLevel3Check, EnergyChargeOffCheck,
            NoShotUpRadio, NormalUpRadio, PelletUpRadio, PierceUpRadio, HeavyUpRadio,TrueShotUpRadio;

    Float[] BoomerangType, MotionAtk;
    String Extract, ShotType, Element;
    int SelectedPhialCharge, SelectedPhialNoCharge, ShellNumber, NumberofPhials, EnergyBladePhials,
            filler = 0;

    List<TextView> Banners = new ArrayList<>();
    String[] TextViewIDsAttacks, TextViewIDsNames, AllTextViewIDs, MotionName;
    TextView textviews[];
    List<String> ElementShots = Arrays.asList("Flaming S Lv1","Freeze S Lv1","Water S Lv1",
            "Thunder S Lv1","Dragon S Lv1","Flaming S Lv2","Freeze S Lv2","Water S Lv2",
            "Thunder S Lv2","Dragon S Lv2","P.Flaming S Lv1","P.Freeze S Lv1","P.Water S Lv1",
            "P.Thunder S Lv1","P.Flaming S Lv2","P.Freeze S Lv2","P.Water S Lv2","P.Thunder S Lv2");
    //-End-

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Stubs(getIntent().getStringExtra("Weapon"));
        SetUp(getIntent().getStringExtra("Weapon"));
        //Toast.makeText(this, "Values vary depending on the hitzone",Toast.LENGTH_LONG).show();
        Toast.makeText(this, "-BETA CALCULATION PROCESS IN USE-",Toast.LENGTH_LONG).show();
        Calculate(getIntent().getStringExtra("Weapon"));

    }

    private void Stubs(String Wpn) {
        ImageButton Icon = findViewById(R.id.WeaponIcon);
        TextView Banner = findViewById(R.id.NameBanner);

        ViewStub BaseStatsStub = findViewById(R.id.BaseStatsStub);
        ViewStub MonsterHitzonesStub = findViewById(R.id.MonsterHitzonesStub);
        ViewStub HunterArtsStub = findViewById(R.id.HunterArtsStub);
        ViewStub SkillsStub = findViewById(R.id.SkillsStub);
        ViewStub AttackInfoStub = findViewById(R.id.AttackInfoStub);

        MonsterHitzonesStub.setLayoutResource(R.layout.content_monster_hitzones);

        TextViewIDsAttacks = getResources().getStringArray(getResources().getIdentifier("TextViewIDsAttacks","array", getPackageName()));
        TextViewIDsNames = getResources().getStringArray(getResources().getIdentifier("TextViewIDsNames","array", getPackageName()));
        AllTextViewIDs = getResources().getStringArray(getResources().getIdentifier("AllTextViewIDs","array", getPackageName()));

        switch (Wpn) {
            case "GS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_gs);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_gs);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Great Sword");
                Icon.setImageResource(R.drawable.great_sword_icon);
                Banner.setText(this.getTitle());
                break;

            case "LS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_ls);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_ls);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Long Sword");
                Icon.setImageResource(R.drawable.long_sword_icon);
                Banner.setText(this.getTitle());
                break;

            case "SNS":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_sns);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_sns);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Sword and Shield");
                Icon.setImageResource(R.drawable.sword_and_shield_icon);
                Banner.setText(this.getTitle());
                break;

            case "DB":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_db);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_db);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Dual Blades");
                Icon.setImageResource(R.drawable.dual_blades_icon);
                Banner.setText(this.getTitle());
                break;

            case "Hammer":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_hh);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hammer);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_hammer);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Hammer");
                Icon.setImageResource(R.drawable.hammer_icon);
                Banner.setText(this.getTitle());
                break;

            case "HH":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_hh);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hh);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Hunting Horn");
                Icon.setImageResource(R.drawable.hunting_horn_icon);
                Banner.setText(this.getTitle());
                break;

            case "Lance":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_lance);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_lance);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Lance");
                Icon.setImageResource(R.drawable.lance_icon);
                Banner.setText(this.getTitle());
                break;

            case "GL":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_gl);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_gl);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_cb);
                this.setTitle("Gunlance");
                Icon.setImageResource(R.drawable.gunlance_icon);
                Banner.setText(this.getTitle());
                break;

            case "SA":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_sa);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_sa);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_sa);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Switch Axe");
                Icon.setImageResource(R.drawable.switch_axe_icon);
                Banner.setText(this.getTitle());
                break;

            case "CB":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_cb);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_cb);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_cb);
                this.setTitle("Charge Blade");
                Icon.setImageResource(R.drawable.charge_blade_icon);
                Banner.setText(this.getTitle());

                TextViewIDsAttacks = getResources().getStringArray(getResources().getIdentifier("TextViewIDsAttacks_CB","array", getPackageName()));
                TextViewIDsNames = getResources().getStringArray(getResources().getIdentifier("TextViewIDsNames_CB","array", getPackageName()));
                AllTextViewIDs = getResources().getStringArray(getResources().getIdentifier("AllTextViewIDs_CB","array", getPackageName()));
                break;

            case "IG":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_ig);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_ig);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills);
                this.setTitle("Insect Glaive");
                Icon.setImageResource(R.drawable.insect_glaive_icon);
                Banner.setText(this.getTitle());
                break;

            case "LBG":
                TextViewIDsAttacks = getResources().getStringArray(getResources().getIdentifier("TextViewIDsAttacks_BG","array", getPackageName()));
                TextViewIDsNames = getResources().getStringArray(getResources().getIdentifier("TextViewIDsNames_BG","array", getPackageName()));
                AllTextViewIDs = getResources().getStringArray(getResources().getIdentifier("AllTextViewIDs_BG","array", getPackageName()));

                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bg);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_lbg);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts);
                SkillsStub.setLayoutResource(R.layout.content_skills_bg);
                this.setTitle("Light Bowgun");
                Icon.setImageResource(R.drawable.light_bowgun_icon);
                Banner.setText(this.getTitle());
                break;

            case "HBG":
                TextViewIDsAttacks = getResources().getStringArray(getResources().getIdentifier("TextViewIDsAttacks_BG","array", getPackageName()));
                TextViewIDsNames = getResources().getStringArray(getResources().getIdentifier("TextViewIDsNames_BG","array", getPackageName()));
                AllTextViewIDs = getResources().getStringArray(getResources().getIdentifier("AllTextViewIDs_BG","array", getPackageName()));

                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bg);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_hbg);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_hbg);
                SkillsStub.setLayoutResource(R.layout.content_skills_bg);
                this.setTitle("Heavy Bowgun");
                Icon.setImageResource(R.drawable.heavy_bowgun_icon);
                Banner.setText(this.getTitle());
                break;

            case "Bow":
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info_bow);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_bow);
                HunterArtsStub.setLayoutResource(R.layout.content_hunter_arts_bow);
                SkillsStub.setLayoutResource(R.layout.content_skills_bow);
                this.setTitle("Bow");
                Icon.setImageResource(R.drawable.bow_icon);
                Banner.setText(this.getTitle());
                break;

            default:
                AttackInfoStub.setLayoutResource(R.layout.content_attack_info);
                BaseStatsStub.setLayoutResource(R.layout.content_stats_input_prowler);
                HunterArtsStub.setLayoutResource(R.layout.content_support_skills);
                SkillsStub.setLayoutResource(R.layout.content_skills_prowler);
                this.setTitle("Prowler");
                Icon.setImageResource(R.drawable.prowler_icon);
                Banner.setText(this.getTitle());
                break;
        }

        AttackInfoStub.inflate();
        BaseStatsStub.inflate();
        MonsterHitzonesStub.inflate();
        HunterArtsStub.inflate();
        SkillsStub.inflate();

        AttackInfo = findViewById(R.id.AttackInfo);
        if(Wpn.equals("CB")) ExtraInfo = findViewById(R.id.BurstAttackInfo);
        else if (Wpn.equals("GL")) ExtraInfo = findViewById(R.id.ShellingInfo);

        Banners.add((TextView)findViewById(R.id.AttackBanner));
        Banners.add((TextView)findViewById(R.id.StaggerBanner));

        if(Wpn.equals("SA") || Wpn.equals("CB")){
            if(Wpn.equals("CB")) {
                Banners.add((TextView)findViewById(R.id.BurstBanner));
                Banners.add((TextView)findViewById(R.id.PhialDisclaimer));
                Banners.add((TextView)findViewById(R.id.KeyBanner));
            }
            Banners.add((TextView)findViewById(R.id.AxeBanner));
            Banners.add((TextView)findViewById(R.id.SwordBanner));
        }
        else if(Wpn.equals("IG")) Banners.add((TextView)findViewById(R.id.ExtractBanner));

    }

    private void SetUp(final String Wpn) {
        PowercharmCheck = findViewById(R.id.PowercharmCheckBox);
        PowertalonCheck = findViewById(R.id.PowertalonCheckBox);

        //Set Prowler/Hunter specific skills
        if(!Wpn.equals("Prowler")){
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

                    ChosenStyle = String.valueOf(StyleSelect.getSelectedItem());

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
                    Skills.setProwlerModifier(String.valueOf(ProwlerTypeSelect.getSelectedItem()));
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
                    Skills.setNineLivesModifier(String.valueOf(NineLivesAttackSelect.getSelectedItem()));
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

        //Set gunner weapon specific checkboxes
        if(Wpn.equals("Bow") || Wpn.equals("LBG") || Wpn.equals("HBG")) {
            if (!Wpn.equals("Bow")) {
                if (Wpn.equals("LBG")) RapidFireCheck = findViewById(R.id.RapidFireCheckBox);
                PowerReloadCheck = findViewById(R.id.PowerReloadCheckBox);
                AerialShotSelect = findViewById(R.id.AerialShotSelect);
            }

            SelectedShot = findViewById(R.id.ShotSelect);
            ArrayAdapter ShotAdapter = ArrayAdapter.createFromResource(this,R.array.BGAmmo,
                    android.R.layout.simple_spinner_dropdown_item);
            SelectedShot.setAdapter(ShotAdapter);
//            SelectedShot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    ChosenElement = String.valueOf(ElementSelect.getSelectedItem());
////                    switch (String.valueOf(ElementSelect.getSelectedItem())) {
////                        case "Fire":
////                            getChosenElement("Fire");
////                            break;
////
////                        case "Water":
////                            getChosenElement("Water");
////                            break;
////
////                        case "Ice":
////                            getChosenElement("Ice");
////                            break;
////
////                        case "Thunder":
////                            getChosenElement("Thunder");
////                            break;
////
////                        case "Dragon":
////                            getChosenElement("Dragon");
////                            break;
////
////                        default:
////                            getChosenElement("NONE");
////                            break;
////                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            DistanceSelect = findViewById(R.id.DistanceSelect);
            ArrayAdapter DistanceAdapter = ArrayAdapter.createFromResource(this,R.array.BowDistance,
                    android.R.layout.simple_spinner_dropdown_item);
            DistanceSelect.setAdapter(DistanceAdapter);

            NoShotUpRadio = findViewById(R.id.NoShotUpRadio);
            NormalUpRadio = findViewById(R.id.NormalUpRadio);
            PelletUpRadio = findViewById(R.id.PelletUpRadio);
            HeavyUpRadio = findViewById(R.id.HeavyUpRadio);
            PierceUpRadio = findViewById(R.id.PierceUpRadio);
            TrueShotUpRadio = findViewById(R.id.TrueShotUpRadio);

        }

        //Sharpness/Element Spinner set up
        if(!Wpn.equals("LBG") && !Wpn.equals("HBG")){
            if(!Wpn.equals("Bow")){
                SharpnessSelect = findViewById(R.id.SharpnessSelect);
                ArrayAdapter SharpnessAdapter = ArrayAdapter.createFromResource(this,R.array.Sharpness,
                        android.R.layout.simple_spinner_dropdown_item);
                SharpnessSelect.setAdapter(SharpnessAdapter);
                SharpnessSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        Sharpness = String.valueOf(SharpnessSelect.getSelectedItem());

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

//                            case "Blue":
//                                //getAtk(SharpModAtk[4]);
//                                //getElm(SharpModElm[4]);
//                                Skills.getBludgeonerModifier(0f);
//                                break;
//
//                            case "White":
//                                //getAtk(SharpModAtk[5]);
//                                //getElm(SharpModElm[5]);
//                                Skills.getBludgeonerModifier(0f);
//                                break;

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
                    ChosenElement = String.valueOf(ElementSelect.getSelectedItem());
//                    switch (String.valueOf(ElementSelect.getSelectedItem())) {
//                        case "Fire":
//                            getChosenElement("Fire");
//                            break;
//
//                        case "Water":
//                            getChosenElement("Water");
//                            break;
//
//                        case "Ice":
//                            getChosenElement("Ice");
//                            break;
//
//                        case "Thunder":
//                            getChosenElement("Thunder");
//                            break;
//
//                        case "Dragon":
//                            getChosenElement("Dragon");
//                            break;
//
//                        default:
//                            getChosenElement("NONE");
//                            break;
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            if(Wpn.equals("DB")){
                SubElementSelect = findViewById(R.id.SubElementSelect);

                SubElementSelect.setAdapter(ElementAdapter);

                SubElementSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ChosenSubElement = String.valueOf(SubElementSelect.getSelectedItem());
//                        switch (String.valueOf(ElementSelect.getSelectedItem())) {
//                            case "[Fire]":
//                                ChosenSubElement = "Fire";
//                                break;
//
//                            case "[Water]":
//                                ChosenSubElement = "Water";
//                                break;
//
//                            case "[Ice]":
//                                ChosenSubElement = "Ice";
//                                break;
//
//                            case "[Thunder]":
//                                ChosenSubElement = "Thunder";
//                                break;
//
//                            case "[Dragon]":
//                                ChosenSubElement = "Dragon";
//                                break;
//
//                            default:
//                                ChosenSubElement = "NONE";
//                                break;
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }

        //Set Artillery spinner and FelyneBombardier check box
        if(Wpn.equals("CB") || Wpn.equals("GL")|| Wpn.equals("LBG") || Wpn.equals("HBG")){
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

        //Set skills
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

        //Set HAs and weapon specific Spinners and RadioButtons
        ArrayAdapter HunterArtsAdapter;
        switch(Wpn){
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
                SpiritGaugeColourSelect = findViewById(R.id.SpiritGaugeColourSelect);
                ArrayAdapter SpiritGaugeAdapter = ArrayAdapter.createFromResource(this,R.array.Spirit_Gauge,
                        android.R.layout.simple_spinner_dropdown_item);
                SpiritGaugeColourSelect.setAdapter(SpiritGaugeAdapter);
                SpiritGaugeColourSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SpiritGaugeColour = String.valueOf(SpiritGaugeColourSelect.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                CentreBladeBonusCheck = findViewById(R.id.CentreBladeCheck);
                MaxSpiritGaugeCheck = findViewById(R.id.MaxSpiritGauge);

                SacrificialBladeOffCheck = findViewById(R.id.SacrificialBladeOffCheck);
                SacrificialBladeLevel1Check = findViewById(R.id.SacrificialBladeLevel1Check);
                SacrificialBladeLevel2Check = findViewById(R.id.SacrificialBladeLevel2Check);
                SacrificialBladeLevel3Check = findViewById(R.id.SacrificialBladeLevel3Check);

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
                DBModeSelect = findViewById(R.id.ModeSelect);
                ArrayAdapter DBModeAdapter = ArrayAdapter.createFromResource(this,R.array.DBModes,
                     android.R.layout.simple_spinner_dropdown_item);

                DBModeSelect.setAdapter(DBModeAdapter);
                DBModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        DBMode = DBModeSelect.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                GroupPAdapter = ArrayAdapter.createFromResource(this,R.array.GroupP_DB,
                        android.R.layout.simple_spinner_dropdown_item);
                GroupPSelect.setAdapter(GroupPAdapter);
                GroupPSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (String.valueOf(GroupPSelect.getSelectedItem())) {
                            case "[Element] Atk Up +1":
                                Skills.setGroupP(4,1.05f);
                                Skills.setGroupP_Sub(0, 1);
                                break;

                            case "[Element] Atk Up +2":
                                Skills.setGroupP(6,1.1f);
                                Skills.setGroupP_Sub(0, 1);
                                break;

                            case "[Sub Element] Atk Up +1":
                                Skills.setGroupP(0, 1);
                                Skills.setGroupP_Sub(4, 1.05f);
                                break;

                            case "[Sub Element] Atk Up +2":
                                Skills.setGroupP(0, 1);
                                Skills.setGroupP_Sub(6, 1.1f);
                                break;

                            default:
                                Skills.setGroupP(0,1);
                                Skills.setGroupP_Sub(0, 1);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                WolfsMawOffCheck = findViewById(R.id.WolfsMawOffCheck);
                WolfsMawLevel1Check = findViewById(R.id.WolfsMawLevel1Check);
                WolfsMawLevel2Check = findViewById(R.id.WolfsMawLevel2Check);
                WolfsMawLevel3Check = findViewById(R.id.WolfsMawLevel3Check);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.DB_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Hammer":
                ProvokeCheck = findViewById(R.id.ProvokeCheck);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Hammer_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HH":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.HH_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "Lance":
                NoEnragedGuardAura = findViewById(R.id.NoEnragedGuardAura);
                EnragedGuardRed = findViewById(R.id.EnragedGuardRed);
                EnragedGuardYellow = findViewById(R.id.EnragedGuardYellow);
                EnragedGuardOrange = findViewById(R.id.EnragedGuardOrange);

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.Lance_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "GL":
                DragonBreathCheck = findViewById(R.id.DragonBreathCheckBox);

                ShotLevelSelect = findViewById(R.id.ShotLevelSelect);
                ShotTypeSelect = findViewById(R.id.ShotTypeSelect);
                HeatGaugeSelect = findViewById(R.id.HeatGaugeSelect);
                NumberofShellsSelect = findViewById(R.id.NumberofShellsSelect);

                ArrayAdapter ShotLevel = ArrayAdapter.createFromResource(this,R.array.GLShotLevel,
                        android.R.layout.simple_spinner_dropdown_item);
                ShotLevelSelect.setAdapter(ShotLevel);

                ArrayAdapter ShotType = ArrayAdapter.createFromResource(this,R.array.GLShotType,
                        android.R.layout.simple_spinner_dropdown_item);
                ShotTypeSelect.setAdapter(ShotType);

                ArrayAdapter HeatGauge = ArrayAdapter.createFromResource(this,R.array.HeatGauge,
                        android.R.layout.simple_spinner_dropdown_item);
                HeatGaugeSelect.setAdapter(HeatGauge);

                HeatGaugeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String HeatGauge = String.valueOf(HeatGaugeSelect.getSelectedItem());
                        switch (HeatGauge){
                            case "Yellow Gauge":
                                Skills.setHeatGaugeModifier(1.1f);
                                break;
                            case "Orange Gauge":
                                Skills.setHeatGaugeModifier(1.15f);
                                break;
                            default:
                                Skills.setHeatGaugeModifier(1.2f);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ArrayAdapter NumberofShells = ArrayAdapter.createFromResource(this,R.array.GLShellNumber,
                        android.R.layout.simple_spinner_dropdown_item);
                NumberofShellsSelect.setAdapter(NumberofShells);

                NumberofShellsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ShellNumber = Integer.parseInt(String.valueOf(NumberofShellsSelect.getSelectedItem()));
//                        switch(String.valueOf(PhialSelect.getSelectedItem())){
//                            case "Impact":
//
//                                HA_ElementCheck = UI.this.getResources().getIntArray(HA_ElementCheck_Array);
//
//
//                                /*SelectedPhialNoCharge = UI.this.getResources().getIdentifier("CB_"
//                                        + PhialSelect.getSelectedItem().toString() + "Burst_NoCharge_MV",
//                                        "integer", UI.this.getPackageName());
//
//                                SelectedPhialCharge = UI.this.getResources().getIdentifier("CB_"
//                                                + PhialSelect.getSelectedItem().toString() + "Burst_Charge_MV",
//                                        "integer", UI.this.getPackageName());*/
//                                isImpact = true;
//                                break;
//                            default:
//                                SelectedPhial = ElmBurstMotion;
//                                isImpact = false;
//                                break;
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.GL_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "SA":
                AwakenedCheck = findViewById(R.id.AwakenedCheckBox);
                TempestAxeCheck = findViewById(R.id.TempestAxeCheckBox);

                DemonRiotOffCheck = findViewById(R.id.DemonRiotOffCheck);
                DemonRiotLevel1Check = findViewById(R.id.DemonRiotLevel1Check);
                DemonRiotLevel2Check = findViewById(R.id.DemonRiotLevel2Check);
                DemonRiotLevel3Check = findViewById(R.id.DemonRiotLevel3Check);

                EnergyChargeOffCheck = findViewById(R.id.EnergyChargeOffCheck);
                EnergyChargeLevel2Check = findViewById(R.id.EnergyChargeLevel2Check);
                EnergyChargeLevel3Check = findViewById(R.id.EnergyChargeLevel3Check);

                PhialSelect = findViewById(R.id.PhialSelect);
                ArrayAdapter SAPhialAdapter = ArrayAdapter.createFromResource(this,R.array.SAPhial,
                        android.R.layout.simple_spinner_dropdown_item);

                PhialSelect.setAdapter(SAPhialAdapter);
                PhialSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String Phial = String.valueOf(PhialSelect.getSelectedItem());
                        Skills.setSAPhialModifier(Phial);
                        switch(Phial){
                            case "Power Phial":
                            case "Element Phial":
                            case "Dragon Phial":
                                Snackbar.make(view, "Selected Phial: " + Phial, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                            default:
                                Snackbar.make(view, "Selected Phial: Other", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Toast.makeText(UI.this, "These phials don't affect attack power, only status effects.",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.SA_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "CB":
                PhialSelect = findViewById(R.id.PhialSelect);
                ArrayAdapter CBPhialAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhial,
                        android.R.layout.simple_spinner_dropdown_item);

                PhialSelect.setAdapter(CBPhialAdapter);
                PhialSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SelectedPhialNoCharge = UI.this.getResources().getIdentifier("CB_"
                                        + String.valueOf(PhialSelect.getSelectedItem()) +
                                        "Burst_NoCharge_MV", "array", UI.this.getPackageName());

                        SelectedPhialCharge = UI.this.getResources().getIdentifier("CB_"
                                        + String.valueOf(PhialSelect.getSelectedItem()) +
                                        "Burst_MV", "array", UI.this.getPackageName());

                        isImpact = String.valueOf(PhialSelect.getSelectedItem()).equals("Impact");
//                        switch(String.valueOf(PhialSelect.getSelectedItem())){
//                            case "Impact":
//
//                                HA_ElementCheck = UI.this.getResources().getIntArray(HA_ElementCheck_Array);
//
//
//                                /*SelectedPhialNoCharge = UI.this.getResources().getIdentifier("CB_"
//                                        + PhialSelect.getSelectedItem().toString() + "Burst_NoCharge_MV",
//                                        "integer", UI.this.getPackageName());
//
//                                SelectedPhialCharge = UI.this.getResources().getIdentifier("CB_"
//                                                + PhialSelect.getSelectedItem().toString() + "Burst_Charge_MV",
//                                        "integer", UI.this.getPackageName());*/
//                                isImpact = true;
//                                break;
//                            default:
//                                SelectedPhial = ElmBurstMotion;
//                                isImpact = false;
//                                break;
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                NumberofPhialsSelect = findViewById(R.id.NumberofPhialsSelect);
                ArrayAdapter NumberofPhialsAdapter = ArrayAdapter.createFromResource(this,R.array.CBPhialNumber,
                        android.R.layout.simple_spinner_dropdown_item);

                NumberofPhialsSelect.setAdapter(NumberofPhialsAdapter);

                NumberofPhialsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch(String.valueOf(NumberofPhialsSelect.getSelectedItem())){
                            case "1":
                                NumberofPhials = 1;
                                EnergyBladePhials = 3;
                                break;
                            case "2":
                            case "3":
                                NumberofPhials = 2;
                                EnergyBladePhials = 3;
                                break;
                            case "4":
                            case "5":
                                NumberofPhials = 3;
                                EnergyBladePhials = 6;
                                break;
                            case "6":
                                NumberofPhials = 4;
                                EnergyBladePhials = 6;
                                break;
                            case "7":
                                NumberofPhials = 4;
                                EnergyBladePhials = 9;
                                break;
                            case "8":
                            case "9":
                                NumberofPhials = 5;
                                EnergyBladePhials = 9;
                                break;
                            case "10":
                                NumberofPhials = 6;
                                EnergyBladePhials = 10;
                                break;
                            default:
                                NumberofPhials = 0;
                                EnergyBladePhials = 0;
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
//                NumberofPhialsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                        switch(Integer.parseInt(String.valueOf(NumberofPhialsSelect.getSelectedItem()))){
//                            case 0:
//                                break;
//                            case 1:
//                                break;
//                            case 2:
//                                break;
//                            case 3:
//                                break;
//                            case 4:
//                                break;
//                            case 5:
//                                break;
//                            case 6:
//                                break;
//                            case 7:
//                                break;
//                            case 8:
//                                break;
//                            case 9:
//                                break;
//                            default:
//                                break;
//                        }
//
//                        TextView Phial = (TextView) view;
//                        final String PhialNumber = Phial.getText().toString();
//                        if (PhialNumber.equals("0")) {
//                            getNumberofPhials(0);
//                            getEnergyBladePhials(0);
//                        } else if (PhialNumber.equals("1")) {
//                            getNumberofPhials(1);
//                            getEnergyBladePhials(3);
//                        } else if (PhialNumber.equals("2")) {
//                            getNumberofPhials(2);
//                            getEnergyBladePhials(3);
//                        } else if (PhialNumber.equals("3")) {
//                            getNumberofPhials(2);
//                            getEnergyBladePhials(3);
//                        } else if (PhialNumber.equals("4")) {
//                            getNumberofPhials(3);
//                            getEnergyBladePhials(6);
//                        } else if (PhialNumber.equals("5")) {
//                            getNumberofPhials(3);
//                            getEnergyBladePhials(6);
//                        } else if (PhialNumber.equals("6")) {
//                            getNumberofPhials(4);
//                            getEnergyBladePhials(6);
//                        } else if (PhialNumber.equals("7")) {
//                            getNumberofPhials(4);
//                            getEnergyBladePhials(9);
//                        } else if (PhialNumber.equals("8")) {
//                            getNumberofPhials(5);
//                            getEnergyBladePhials(9);
//                        } else if (PhialNumber.equals("9")) {
//                            getNumberofPhials(5);
//                            getEnergyBladePhials(9);
//                        } else if (PhialNumber.equals("10")) {
//                            getNumberofPhials(6);
//                            getEnergyBladePhials(10);
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

                ShieldChargeSelect = findViewById(R.id.ShieldChargeSelect);
                ArrayAdapter ShieldChargeAdapter = ArrayAdapter.createFromResource(this,R.array.CBShieldCharge,
                        android.R.layout.simple_spinner_dropdown_item);

                ShieldChargeSelect.setAdapter(ShieldChargeAdapter);
                ShieldChargeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        Skills.setCBPhialModifier(!String.valueOf(ShieldChargeSelect.getSelectedItem())
                                .equals("No Charge"));
//                        String Charge = String.valueOf(ShieldChargeSelect.getSelectedItem());
//                        switch(Charge){
//                            case "No Charge":
//                                Skills.setCBPhialModifier(false);
//                                /*getChargeModifier(1f);
//                                getRedCheck(false);
//                                getYellowCheck(false);*/
//                                break;
//                            case "Yellow Charge":
//                                Skills.setCBPhialModifier(true);
//                                /*getChargeModifier(1f);
//                                getRedCheck(false);
//                                getYellowCheck(true);*/
//                                break;
//                            case "Red Charge":
//                                Skills.setCBPhialModifier(true);
//                                /*getRedCheck(true);
//                                getYellowCheck(false);*/
//                                break;
//                            default:
//                                Skills.setCBPhialModifier(true);
//                                /*getRedCheck(true);
//                                getYellowCheck(false);*/
//                                break;
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

//                GroupSSelect = findViewById(R.id.GroupSSelect);
//
//                ArrayAdapter GroupSAdapter = ArrayAdapter.createFromResource(this,R.array.GroupS,
//                        android.R.layout.simple_spinner_dropdown_item);
//
//                GroupSSelect.setAdapter(GroupSAdapter);
//
//                GroupSSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                        switch(String.valueOf(GroupSSelect.getSelectedItem())){
//                            case "Artillery Novice":
//                                Skills.setArtilleryModifier(1.3f);
//                                break;
//                            case "Artillery Expert":
//                                Skills.setArtilleryModifier(1.35f);
//                                break;
//                            default:
//                                Skills.setArtilleryModifier(1f);
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.CB_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "IG":
                ExtractSelect = findViewById(R.id.ExtractSelect);
                ArrayAdapter ExtractAdapter = ArrayAdapter.createFromResource(this,R.array.Extracts,
                        android.R.layout.simple_spinner_dropdown_item);

                ExtractSelect.setAdapter(ExtractAdapter);
                ExtractSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(String.valueOf(ExtractSelect.getSelectedItem()).equals("None")){
                            Extract = "NoExtract";
                        }
                        else Extract = "Extract";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.IG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "LBG":
                HunterArtsAdapter = ArrayAdapter.createFromResource(this,R.array.LBG_HA_Names,
                        android.R.layout.simple_spinner_dropdown_item);
                break;
            case "HBG":
                GunpowderInfusionCheck = (CheckBox) findViewById(R.id.GunpowderInfusionCheckBox);

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
                ChosenArt = String.valueOf(HunterArtSelect.getSelectedItem());
                SynergyCheck = !(String.valueOf(HunterArtSelect.getSelectedItem()).equals("Lions Maw (Wide Slash)"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Set MonsterSelect spinner
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
                        MonsterType = "Beast";

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if(SelectedMonster.equals("Plesioth") || SelectedMonster.equals("Rathian")
                            || SelectedMonster.equals("Cephadrome") || SelectedMonster.equals("Rathalos")
                            || SelectedMonster.equals("Lavasioth") || SelectedMonster.equals("Gypceros")
                            || SelectedMonster.equals("Yian Kut Ku") || SelectedMonster.equals("Yian Garuga")
                            || SelectedMonster.equals("Deadeye Yian Garuga")){
                        MonsterType = "FlyingWyvern";

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else if(SelectedMonster.equals("Gold Rathian") || SelectedMonster.equals("Silver Rathalos")){
                        MonsterType = "FlyingWyvernWounded";

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SelectedMonster = Stripped;
                    }
                    else if(SelectedMonster.equals("Malfestio") || SelectedMonster.equals("Nightcloak Malfestio")){
                        MonsterType = "Malfestio";

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        SelectedMonster = Stripped;
                    }
                    else{
                        MonsterType = "Raptor";

                        Snackbar.make(view, "Chosen Monster: " + SelectedMonster, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                else if(SelectedMonster.equals("None")){
                    MonsterType = "None";
                }
                else{
                    MonsterType = Stripped;

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
                        HitzoneGroup = "Beast";

                        select();

                        break;

                    case "FlyingWyvern":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter2);
                        HitzoneGroup = "FlyingWyvern";

                        select();

                        break;

                    case "FlyingWyvernWounded":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.FlyingWyvernWoundedHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/

                        HitzoneSelect.setAdapter(adapter3);
                        HitzoneGroup = "FlyingWyvernWounded";

                        select();

                        break;

                    case "Raptor":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.RaptorHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter4);
                        HitzoneGroup = "Raptor";

                        select();

                        break;

                    case "Malfestio":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.MalfestioHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter5);
                        HitzoneGroup = "Malfestio";

                        select();

                        break;

                    case "None":
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.NoneHitzones,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter6);
                        HitzoneGroup = "None";

                        select();

                        break;

                    default:
                        HitzoneSelect = findViewById(R.id.HitzoneSelect);

                        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(getApplicationContext(),
                                Counter,R.layout.secondary_spinner);
                        /*Sets the adapter (array) values to the drop down menu.*/
                        HitzoneSelect.setAdapter(adapter7);
                        HitzoneGroup = Stripped;

                        select();

                        break;
                }
            }

            private void select(){

                HitzoneSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ChosenMonster = SelectedMonster.replaceAll("\\s","");
                        ChosenHitzone = String.valueOf(HitzoneSelect.getSelectedItem());
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

        //Set Skill display
        //Creates a method that checks for the state of the switch and is triggered whenever it is
        //changed, making the skills disappear and reappear as necessary. It also sets the value of
        //the 'SkillCheck' variable to either '1' or '0' depending on the state of the switch in order
        //to make sure that none of the  values from the spinners affect the overall calculation if
        //the switch is set to 'Off'.
        SkillSwitch = findViewById(R.id.SkillsSwitch);
        SkillSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout SkillsLayout = findViewById(getResources().getIdentifier("Skills", "id", getPackageName()));
                if(isChecked){
                    SkillsLayout.setVisibility(View.VISIBLE);

                    Calculate = findViewById(R.id.CalculateButton);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) Calculate.getLayoutParams();
                    if(Wpn.equals("HBG") || Wpn.equals("LBG"))
                        Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible_BG), 0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    else
                        Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Visible), 0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    Calculate.setLayoutParams(Margin);

                    SkillCheck = true;
                }
                else{
                    SkillsLayout.setVisibility(View.GONE);

                    Calculate = findViewById(R.id.CalculateButton);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) Calculate.getLayoutParams();
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.Skills_Hidden), 0, getResources().getDimensionPixelSize(R.dimen.Calculate_Bottom_Margin));
                    Calculate.setLayoutParams(Margin);

                    SkillCheck = false;
                }
            }
        });
    }

    private void GunnerSetUp(){
        String Shot = String.valueOf(SelectedShot.getSelectedItem());
        if(ElementShots.contains(Shot)){
            if (Shot.equals("Flaming S Lv1") || Shot.equals("Freeze S Lv1") || Shot.equals("Water S Lv1") || Shot.equals("Thunder S Lv1")) {
                MotionAtk[0] = 0.07f;
                MotionAtk[1] = 0.45f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (Shot.equals("Flaming S Lv2") || Shot.equals("Freeze S Lv2") || Shot.equals("Water S Lv2") || Shot.equals("Thunder S Lv2")) {
                MotionAtk[0] = 0.07f;
                MotionAtk[1] = 0.58f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (Shot.equals("Dragon S Lv1")) {
                MotionAtk[0] = 0.01f;
                MotionAtk[1] = 2f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
                //getChosenElement("Dragon");
            }
            else if (Shot.equals("Dragon S Lv2")) {
                MotionAtk[0] = 0.01f;
                MotionAtk[1] = 2.4f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
                //getChosenElement("Dragon");
            }
            else if (Shot.equals("P.Flaming S Lv1") || Shot.equals("P.Freeze S Lv1") || Shot.equals("P.Water S Lv1") || Shot.equals("P.Thunder S Lv1")) {
                MotionAtk[0] = 0.06f;
                MotionAtk[1] = 0.6f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (Shot.equals("P.Flaming S Lv2") || Shot.equals("P.Freeze S Lv2") || Shot.equals("P.Water S Lv2") || Shot.equals("P.Thunder S Lv2")) {
                MotionAtk[0] = 0.15f;
                MotionAtk[1] = 1.35f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }

            if(Shot.contains("Flaming")) Element = "Fire";
            else if(Shot.contains("Freeze")) Element = "Ice";
            else if(Shot.contains("Water")) Element = "Water";
            else if(Shot.contains("Thunder")) Element = "Thunder";
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
            MotionAtk[1] = 1f;
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


        switch (ShotType){
            case "Elemental":
                MotionName[0] = "Shot Damage";
                MotionName[1] = "Elemental Damage";
                MotionName[2] = "";
                MotionName[3] = "";
                break;
            case "Crag":
            case "Clust":
            case "Triblast S":
                MotionName[0] = "Shot Damage";
                MotionName[1] = "Fixed Damage";
                MotionName[2] = "Fire Damage";
                MotionName[3] = "Stun Damage";
                break;
            case "Cannon":
                MotionName[0] = "Shot Damage";
                MotionName[1] = "Fixed Damage";
                MotionName[2] = "Stun Damage";
                MotionName[3] = "";
                break;
            case "Slicing":
                MotionName[0] = "Shot Damage";
                MotionName[1] = "Cutting Damage";
                MotionName[2] = "";
                MotionName[3] = "";
                break;
            case "Shrapnel":
                MotionName[0] = "Shot Damage";
                MotionName[1] = "Shrapnel Damage";
                MotionName[2] = "";
                MotionName[3] = "";
                break;
//            case "Normal":
//            case "Pierce":
//                break;
//            case "Wyvern":
//                break;
//            case "Heavy":
//                break;
//            case "Sting":
//                break;
//            case "Stone":
//                break;
//            case "Force":
//                break;
//            case "Dazzling":
//                break;
//            case "Long":
//                break;
//            case "Pellet":
//                break;
            default:
                MotionName[0] = "Shot Damage";
                MotionName[1] = "";
                MotionName[2] = "";
                MotionName[3] = "";
                break;
        }
    }

    private void Calculate(final String Wpn){
        Calculate = findViewById(R.id.CalculateButton);
        Calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(UI.INPUT_METHOD_SERVICE);
                if(inputManager != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                AttackInfo.setVisibility(View.GONE);

                TextView Damage = findViewById(R.id.DamageInput);
                TextView Element = findViewById(R.id.ElementInput);
                TextView SubElement;
                TextView Affinity = findViewById(R.id.AffinityInput);

                switch(Wpn){
                    case "HBG":
                    case "LBG":
                        if(TextUtils.isEmpty(Damage.getText().toString())) Damage.setText("0");
                        if(TextUtils.isEmpty(Affinity.getText().toString())) Affinity.setText("0");

                        DmgCalc = new DamageCalculation(UI.this,UI.this, Wpn,
                                Float.parseFloat(Damage.getText().toString()),
                                Float.parseFloat(Affinity.getText().toString()));

                        if(!DmgCalc.Stats.isValid()){
                            if(!DmgCalc.Stats.isValidAtk()){
                                Damage.setError("Enter a valid attack");
                                return;
                            }
                            else if(!DmgCalc.Stats.isValidAffinity()){
                                Affinity.setError("Enter a valid affinity");
                                return;
                            }
                        }

                        RefreshTextViews(Wpn);
                        if(ErrorCheck(view, Wpn, Float.parseFloat(Element.getText().toString()),
                                0)) return;

                        GunnerSetUp();
                        break;
                    default:
                        if(TextUtils.isEmpty(Damage.getText().toString())) Damage.setText("0");
                        if(TextUtils.isEmpty(Element.getText().toString())) Element.setText("0");
                        if(TextUtils.isEmpty(Affinity.getText().toString())) Affinity.setText("0");

                        switch(Wpn){
                            case "DB":
                                SubElement = findViewById(R.id.SubElementInput);
                                if(TextUtils.isEmpty(SubElement.getText().toString())) SubElement.setText("0");

                                DmgCalc = new DamageCalculation(UI.this,UI.this, Wpn,
                                        Float.parseFloat(Damage.getText().toString()), ChosenElement,
                                        Float.parseFloat(Element.getText().toString()), ChosenSubElement,
                                        Float.parseFloat(SubElement.getText().toString()),
                                        Float.parseFloat(Affinity.getText().toString()));

                                if(!DmgCalc.Stats.isValid_DB()){
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

                                if(ErrorCheck(view, Wpn, Float.parseFloat(Element.getText().toString()),
                                        Float.parseFloat(SubElement.getText().toString()))) return;
                                break;
                            default:
                                DmgCalc = new DamageCalculation(UI.this,UI.this, Wpn,
                                        Float.parseFloat(Damage.getText().toString()), ChosenElement,
                                        Float.parseFloat(Element.getText().toString()),
                                        Float.parseFloat(Affinity.getText().toString()));

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

                                RefreshTextViews(Wpn);
                                if(ErrorCheck(view, Wpn, Float.parseFloat(Element.getText().toString()),
                                        0)) return;
                                break;
                        }
                        break;
                }

                if (DmgCalc.CalculateSkills()){
                    Snackbar.make(view, "Please check your inputted element/skills", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                DisplayBanners(Wpn);
                DisplayInfo(Wpn);
                if(Wpn.equals("HBG") || Wpn.equals("LBG")) DmgCalc.setGunnerMVs(String.valueOf(SelectedShot.getSelectedItem()));

                for (int i = 0; i < DmgCalc.getMVSize(); i++) {
                    DmgCalc.Calculate(i);
                    DisplayTextViews(i, view, Wpn);
                }
            }
        });
    }

    private void RefreshTextViews(String Wpn){
        filler = 0;
        textviews = new TextView[AllTextViewIDs.length];

        for (TextView Banner : Banners) Banner.setVisibility(View.GONE);

        for(int i = 0; i < AllTextViewIDs.length; i++){
            textviews[i] = findViewById(getResources().getIdentifier(AllTextViewIDs[i], "id", getPackageName()));
            textviews[i].setVisibility(View.GONE);
        }

        if(Wpn.equals("GL") || Wpn.equals("CB")) ExtraInfo.setVisibility(View.GONE);
    }

    private void DisplayTextViews(int counter, View view, String Wpn){
        boolean Skip = false, Shelling = false;
        switch(Wpn){
            case "GL":
                if(DmgCalc.getMVName(counter).equals("Shells")
                        || DmgCalc.getMVName(counter).contains("Fire")
                        || DmgCalc.getMVName(counter).contains("Burst")) Shelling = true;
                if((DmgCalc.getMVName(counter).equals("Jump Attack Thrust")
                        || DmgCalc.getMVName(counter).equals("Vault Attack")) && counter < 11)
                    while (!TextViewIDsNames[counter + filler].contains("11")) filler++;

                if((DmgCalc.getMVName(counter).equals("   -Charged Fire Damage")
                    || DmgCalc.getMVName(counter).equals("Charged Shell"))
                        && ChosenStyle.equals("Alchemy")) return;

                if(DmgCalc.getMVName(counter).equals("Full Burst") && ChosenStyle.equals("Striker"))
                    return;

                switch(DmgCalc.getMVName(counter)){
                    case "First Shot":
                        if(ShellNumber < 1) Skip = true;
                    break;
                    case "Second Shot":
                        if(ShellNumber < 2) return;
                        break;
                    case "Third Shot":
                        if(ShellNumber < 3) return;
                        break;
                    case "Fourth Shot":
                        if(ShellNumber < 4) return;
                        break;
                    case "Fifth Shot":
                        if(ShellNumber < 5) return;
                        break;
                    case "Sixth Shot":
                        if(ShellNumber < 6) return;
                        break;
                }
                break;
            case "SA":
                if(DmgCalc.getMVName(counter).equals("Jump Attack") && counter < 10)
                    while (!TextViewIDsNames[counter + filler].contains("10")) filler++;

                else if(DmgCalc.getMVName(counter).contains("Tempest") && !TempestAxeCheck.isChecked())
                    return;

                else if(DmgCalc.getMVName(counter).equals("Jump Attack") && counter > 10)
                    while (!TextViewIDsNames[counter + filler].contains("23")) filler++;

                break;
            case "CB":
                if((DmgCalc.getMVName(counter).equals("Jump Attack")
                        || DmgCalc.getMVName(counter).equals("Aerial Attack")
                        || DmgCalc.getMVName(counter).equals("Aerial Charge Slash")) && counter < 10)
                    while (!TextViewIDsNames[counter + filler].contains("10")) filler++;

                else if(((DmgCalc.getMVName(counter).equals("Ultra Burst (2 hits) (Valor State)")
                        || (DmgCalc.getMVName(counter).contains("EX")))
                        && !String.valueOf(ShieldChargeSelect.getSelectedItem()).equals("Blue Charge (Valor)"))
                        || (DmgCalc.getMVName(counter).equals("Ultra Burst (2 hits)") && Skills.getCBPhialModifier() == 1f)){
                    return;
                }

                else if(DmgCalc.getMVName(counter).equals("Jump Attack") && counter > 10)
                    while (!TextViewIDsNames[counter + filler].contains("23")) filler++;

                else if(DmgCalc.getMVName(counter).equals("(L)Guard Bursts") &&
                        String.valueOf(ShieldChargeSelect.getSelectedItem()).equals("Yellow Charge"))
                    return;
                break;
            case "LBG":
            case "HBG":
                List<String> HitzoneCatchList = Arrays.asList("Head", "Chin", "Horn", "NONE");

                switch(ShotType){
                    case "Triblast":
                    case "Crag":
                    case "Clust":
                        if (!HitzoneCatchList.contains(ChosenHitzone) && counter == 3) {
                            //Info.setVisibility(View.VISIBLE);
                            Banners.get(0).setText(SelectedShot.getSelectedItem().toString());
                        }
                        break;

                    case "Cannon":
                        if (!HitzoneCatchList.contains(ChosenHitzone) && counter == 2) {
                            //Info.setVisibility(View.VISIBLE);
                            Banners.get(0).setText(SelectedShot.getSelectedItem().toString());
                        }
                        break;
                }
                break;
        }

        if(!Skip){
            textviews[counter + filler] = findViewById(getResources().getIdentifier(TextViewIDsNames[counter + filler], "id", getPackageName()));
            textviews[counter + filler].setText(DmgCalc.getMVName(counter));
            textviews[counter + filler].setVisibility(View.VISIBLE);

            if(DmgCalc.getBounce(counter, DmgCalc.getAtkPwr(counter)) && !ChosenMonster.equals("None") &&
                    !Shelling) {
                textviews[counter + filler].setTextColor(Color.argb(255, 242, 16, 16));
                Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else textviews[counter + filler].setTextColor(Color.BLACK);

            textviews[counter + filler] = findViewById(getResources().getIdentifier(TextViewIDsAttacks[counter + filler], "id", getPackageName()));
            textviews[counter + filler].setText(String.format("%s", Math.round(DmgCalc.getAtkPwr(counter))));
            textviews[counter + filler].setVisibility(View.VISIBLE);

            if(DmgCalc.getBounce(counter, DmgCalc.getAtkPwr(counter)) && !ChosenMonster.equals("None") &&
                    !Shelling)
                textviews[counter + filler].setTextColor(Color.argb(255, 242, 16, 16));
            else textviews[counter + filler].setTextColor(Color.BLACK);

            if(Wpn.equals("LS") && !String.valueOf(SpiritGaugeColourSelect.getSelectedItem()).equals("No Colour") &&
                    DmgCalc.getMVName(counter).equals("   -(With Spirit Energy)")) {
                TextView TextChange = findViewById(getResources().getIdentifier(TextViewIDsNames[counter - 1], "id", getPackageName()));
                TextChange.setText("Jump Spirit Slash");
            }

            if(Wpn.equals("CB") && String.valueOf(ShieldChargeSelect.getSelectedItem()).equals("Blue Charge (Valor)") &&
                    DmgCalc.getMVName(counter).equals("Ultra Burst (2 hits)")) {
                TextView TextChange = findViewById(getResources().getIdentifier(TextViewIDsAttacks[counter + filler], "id", getPackageName()));
                TextChange.setText("Ultra Burst (2 hits) (Valor State)");
            }
        }
        else{
            TextView ValorShellingDisclaimer = findViewById(R.id.ValorShellingDisclaimer);
            ValorShellingDisclaimer.setText("No Shells loaded");
        }
    }

    private void DisplayBanners(String Wpn){
        if(ChosenArt.equals("-None-")) Banners.get(0).setText("Attacks");
        else Banners.get(0).setText(ChosenArt);

        Banners.get(0).setVisibility(View.VISIBLE);

        if(!ChosenMonster.equals("None")) {
            if(Wpn.equals("IG")){
                Banners.get(2).setText(DmgCalc.getExtract());
                Banners.get(2).setVisibility(View.VISIBLE);
            }
            Banners.get(1).setText(DmgCalc.getStagger());
            Banners.get(1).setVisibility(View.VISIBLE);
        }

        if((Wpn.equals("SA") || Wpn.equals("CB")) && ChosenArt.equals("-None-")){
            if(Wpn.equals("CB")) {
                Banners.get(4).setVisibility(View.VISIBLE);
                Banners.get(5).setVisibility(View.VISIBLE);
                Banners.get(6).setVisibility(View.VISIBLE);
            }
            Banners.get(2).setVisibility(View.VISIBLE);
            Banners.get(3).setVisibility(View.VISIBLE);
        }
    }

    private void DisplayInfo(String Wpn){
        AttackInfo.setVisibility(View.VISIBLE);
        if((Wpn.equals("CB") || Wpn.equals("GL")) && ChosenArt.equals("-None-")) {
            if(Wpn.equals("CB")){
                if(isImpact){
                    if (Skills.getCBPhialModifier() == 1) {
                        String Key = "(L)KO/Exhaust: " + String.format("%s", Math.round(30f * Skills.getCBPhialModifier())) + "/" +
                                String.format("%s", Math.round(5f * Skills.getCBPhialModifier())) +
                                "\nThese values are fixed and are based on raw damage ONLY";
                        Banners.get(4).setText(Key);
                    }
                    else {
                        String Key = "(S)KO/Exhaust: " + String.format("%s", Math.round(15f * Skills.getCBPhialModifier())) + "/" +
                                String.format("%s", Math.round(2.5f * Skills.getCBPhialModifier())) + "\n(L)KO/Exhaust: " +
                                String.format("%s", Math.round(30f * Skills.getCBPhialModifier())) + "/" +
                                String.format("%s", Math.round(5f * Skills.getCBPhialModifier())) +
                                "\nThese values are fixed and are based on raw damage ONLY";
                        Banners.get(4).setText(Key);
                    }

                    TextView BurstBanner = findViewById(R.id.BurstAttack_1);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) BurstBanner.getLayoutParams();
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.convert1), 0, 0);
                    BurstBanner.setLayoutParams(Margin);
                    Banners.get(4).setVisibility(View.VISIBLE);

                    TextView Extra =  findViewById(R.id.BurstAttack_2_Extra);
                    if (isImpact && Skills.getCBPhialModifier() == 1.35f) {
                        String Key = "KO/Exhaust: " + String.format("%s", Math.round(90f *
                                Skills.getCBPhialModifier())) + "/" + String.format("%s",
                                Math.round(15f * Skills.getCBPhialModifier()));
                        Extra.setText(Key);
                        Extra.setVisibility(View.VISIBLE);
                    } else if (isImpact) {
                        String Key = "KO/Exhaust: 90/15";
                        Extra.setText(Key);
                        Extra.setVisibility(View.VISIBLE);
                    } else if (!isImpact) {
                        Extra.setVisibility(View.GONE);
                    }

                    TextView ExtraExtend = findViewById(R.id.BurstAttack_Extend);
                    if (isImpact && Skills.getCBPhialModifier() == 1.35f) {
                        String ExtraText = "KO/Exhaust: " + String.format("%s", Math.round(30f *
                                Skills.getCBPhialModifier()) * NumberofPhials) + "/" + String.format("%s",
                                Math.round(5f * Skills.getCBPhialModifier()) * NumberofPhials);
                        ExtraExtend.setText(ExtraText);
                        ExtraExtend.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Banners.get(4).setText("Elemental bursts aren't affected by sharpness");
                    TextView BurstBanner = findViewById(R.id.BurstAttack_1);
                    ViewGroup.MarginLayoutParams Margin = (ViewGroup.MarginLayoutParams) BurstBanner.getLayoutParams();
                    Margin.setMargins(0, getResources().getDimensionPixelSize(R.dimen.convert2), 0, 0);
                    BurstBanner.setLayoutParams(Margin);
                    Banners.get(4).setVisibility(View.VISIBLE);

                    TextView ExtraExtend = findViewById(R.id.BurstAttack_Extend);
                    ExtraExtend.setVisibility(View.GONE);

                    TextView BurstAttack_2_Extra = findViewById(R.id.BurstAttack_2_Extra);
                    BurstAttack_2_Extra.setVisibility(View.GONE);
                }
            }
            else{
                TextView Shelling = findViewById(R.id.ValorShellingDisclaimer);
                Shelling.setText(R.string.shelling_damage_disclaimer);
            }
            ExtraInfo.setVisibility(View.VISIBLE);
        }

        if(!ChosenStyle.equals("Valor") && Wpn.equals("GL"))
            findViewById(R.id.ValorShellingInfo).setVisibility(View.GONE);
        else if(ChosenStyle.equals("Valor") && Wpn.equals("GL"))
            findViewById(R.id.ValorShellingInfo).setVisibility(View.VISIBLE);
    }

    private boolean ErrorCheck(View view, String Wpn, float RawElement, float RawSubElement){
        switch(Wpn){
            case "GS":
                if(!SynergyCheck && (LionsMawLevel1Check.isChecked() || LionsMawLevel2Check.isChecked()
                        || LionsMawLevel3Check.isChecked())){
                    Skills.setLionsMawModifier(!LionsMawOffCheck.isChecked(),0);
                    Snackbar.make(view, "Lions Maw attack boost negated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case "LS":
                if(SpiritGaugeColour.equals("-Blue (Valor)-") && !ChosenStyle.equals("Valor")){
                    Snackbar.make(view, "Please choose either 'No Colour' or '-Blue (Valor)-' for Valor Style", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                else if(((SpiritGaugeColour.equals("-Red-") || SpiritGaugeColour.equals("-Yellow-") ||
                        SpiritGaugeColour.equals("-White-")) && ChosenStyle.equals("Valor"))){
                    Snackbar.make(view, "Blue Spirit Gauge is only available in Valor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                break;
            case "DB":
                if((RawElement == RawSubElement) && (ChosenElement.equals(ChosenSubElement)) && RawElement > 0){
                    Snackbar.make(view, "Main and Sub element should be different", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                break;
            case "GL":
                String ShotType = String.valueOf(ShotTypeSelect.getSelectedItem());
                if((ShotType.equals("Long") && ShellNumber > 4) || (ShotType.equals("Wide") && ShellNumber > 3)) {
                    if(ShotType.equals("Long")) {
                        Toast.makeText(UI.this,"Long Types have a maximum clip size of 3 (4 with Load Up)",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(UI.this,"Wide Types have a maximum clip size of 2 (3 with Load Up)",
                                Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                break;
            case "SA":
                if (AwakenedCheck.isChecked() && Skills.SAPhialType().equals("Dragon Phial")) {
                    Snackbar.make(view, "Dragon Phials aren't available with awakened elements", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }

                if (((Skills.SAPhialType().equals("Element Phial") || Skills.SAPhialType().equals("Dragon Phial")) && RawElement == 0) ||
                        Skills.SAPhialType().equals("Dragon Phial") && !ChosenElement.equals("Dragon")){
                    Snackbar.make(view, "Please check your inputted element/phial", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                break;
            case "CB":
                String ChargeText = String.valueOf(ShieldChargeSelect.getSelectedItem());
                if ((ChosenStyle.equals("Striker") || ChosenStyle.equals("Adept")) &&
                        ChargeText.equals("Yellow Charge")) {
                    Snackbar.make(view, ChosenStyle + " Style doesn't have Yellow Shield", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                } else if (ChosenStyle.equals("Valor") && (ChargeText.equals("Yellow Charge") || ChargeText.equals("Red Charge"))) {
                    Snackbar.make(view, "Valor Style only has Blue Shield", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                } else if (!ChosenStyle.equals("Valor") && ChargeText.equals("Blue Charge (Valor)")) {
                    Snackbar.make(view, "Blue Shield is exclusive to Valor", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                break;
            case "HBG":
            case "LBG":
                if (AirborneCheck.isChecked() && !ChosenStyle.equals("Aerial") && SkillCheck){
                    if (!AerialShotSelect.isChecked()) {
                        Toast.makeText(UI.this, "Please select 'Aerial Shot' to use 'Airborne'",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    Toast.makeText(UI.this, "Please select Aerial Style to use 'Airborne'", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(AerialShotSelect.isChecked() && !ChosenStyle.equals("Aerial") && SkillCheck){
                    Toast.makeText(UI.this, "Please select Aerial Style/'Airborne' to use 'Aerial Shot'",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }

        return false;
    }

    public enum PageType {
        ABOUT(1),
        CODING(2),
        DATABASES(3);

        private int value;
        private static SparseArray<PageType> map = new SparseArray<>();


        PageType(int value) {
            this.value = value;
        }

        static {
            for (PageType pageType : PageType.values()) {
                map.put(pageType.value, pageType);
            }
        }

        public static PageType valueOf(int pageType) {
            return (PageType) map.get(pageType);
        }

        public int getValue() {
            return value;
        }
    }
}
