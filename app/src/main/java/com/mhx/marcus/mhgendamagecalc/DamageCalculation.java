package com.mhx.marcus.mhgendamagecalc;

import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DamageCalculation {
    //Classes
    public StatsValidation Stats;
    private MonsterCalculation M;
    private UI ui;
    public SkillsCalculation Skills;

    //Lists
    private List<Float> MVs = new ArrayList<>();
    private List<String> MV_NamesList = new ArrayList<>();

    //Stat Variables
    private String Weapon, /*Style,*/ ChosenElement, ChosenSubElement, Element, ShotType/*, Monster, HitzoneGroup, Hitzone, Sharpness, HunterArt*/;
    private float RawDamage, ElementalDamage, MainElm, SubElementalDamage, SubElm, Affinity;
    private Context context;
    //private int MV_Array, MV_Names_Array, MV_HA_Array, HA_Levels_Array, HA_ElementCheck_Array, ErrorNumber;
    private float SharpnessModifier_Atk, SharpnessModifier_Elm;
    private int[] MV, ValorFullBurstMods, HA_ElementCheck;
    private String[] MV_Names, MV_Names_Extra, HA_Levels;
    private boolean DualElement = false;//, Bounce = false;
    private float [] MotionAtk;
    private String[] MotionName;

    List<String> ElementShots = Arrays.asList("Flaming S Lv1","Freeze S Lv1","Water S Lv1",
            "Thunder S Lv1","Dragon S Lv1","Flaming S Lv2","Freeze S Lv2","Water S Lv2",
            "Thunder S Lv2","Dragon S Lv2","P.Flaming S Lv1","P.Freeze S Lv1","P.Water S Lv1",
            "P.Thunder S Lv1","P.Flaming S Lv2","P.Freeze S Lv2","P.Water S Lv2","P.Thunder S Lv2");

//    public DamageCalculation(Context context, UI ui, String Weapon, Boolean HA, String HunterArt,
//                             String Style, String Sharpness, float RawDamage, String ChosenElement,
//                             float ElementalDamage, float Affinity, String Monster, String HitzoneGroup,
//                             String Hitzone){
//        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);
//
//        this.ui = ui;
//        this.context = context;
//
//        this.RawDamage = RawDamage;
//        this.ElementalDamage = ElementalDamage;
//        this.ChosenElement = ChosenElement;
//        this.Affinity = Affinity;
//
//        this.Weapon = Weapon;
//        this.HunterArt = HunterArt;
//        this.HA = !HA;
//
//        this.Monster = Monster;
//
//        M = new MonsterCalculation(context,
//                Monster + "RawHitzones_Cut",
//                Monster + "ElmHitzones_" + ChosenElement,
//                Monster + "_StaggerLimits",
//                HitzoneGroup + "Hitzones",
//                Hitzone);
//
//        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
//        SharpnessModifier_Atk = context.getResources().getIdentifier(Sharpness + "_Raw","integer", context.getPackageName());
//        SharpnessModifier_Elm = context.getResources().getIdentifier(Sharpness + "_Elm","integer", context.getPackageName());
//        SharpnessModifier_Atk /= 100;
//        SharpnessModifier_Elm /= 100;
//
//        MV_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_MV", "array", context.getPackageName());
//        MV_Names_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_Names", "array", context.getPackageName());
//
//        MV = context.getResources().getIntArray(MV_Array);
//        MV_Names = context.getResources().getStringArray(MV_Names_Array);
//
//        setHA_MV();
//        MVs.clear();
//        MV_NamesList.clear();
//    }//Standard Blademaster

    public DamageCalculation(Context context, UI ui, String Weapon,float RawDamage, String ChosenElement,
                             float ElementalDamage, float Affinity){
        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);
        Skills = ui.Skills;


        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.ElementalDamage = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.Affinity = Affinity;

        this.Weapon = ui.getIntent().getStringExtra("Weapon");

        switch(Weapon){
            case "Lance":
                M = new MonsterCalculation(context,
                        ui.ChosenMonster + "RawHitzones_Cut",
                        ui.ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ui.ChosenMonster + "RawHitzones_Impact",
                        ui.ChosenMonster + "_StaggerLimits",
                        ui.HitzoneGroup + "Hitzones",
                        ui.ChosenHitzone,
                        "Lance");
                M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
                break;
            case "IG":
                M = new MonsterCalculation(context,
                        ui.ChosenMonster + "RawHitzones_Cut",
                        ui.ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ui.ChosenMonster + "_StaggerLimits",
                        ui.ChosenMonster + "_Extracts",
                        ui.HitzoneGroup + "Hitzones",
                        ui.ChosenHitzone);
                M.getHitzones_IG(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
                break;
            default:
                M = new MonsterCalculation(context,
                        ui.ChosenMonster + "RawHitzones_Cut",
                        ui.ChosenMonster + "ElmHitzones_" + ChosenElement,
                        ui.ChosenMonster + "_StaggerLimits",
                        ui.HitzoneGroup + "Hitzones",
                        ui.ChosenHitzone);
                M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
                break;
        }

        setMVs();
        setSharpness();
        setHA_MV();
        MVs.clear();
        MV_NamesList.clear();
    }//Standard Blademaster

    /**
     * @param context Weapon context
     * @param ui UI class instance
     * @param Weapon Selected weapon
     * @param RawDamage Inputted damage ONLY
     * @param ChosenElement Selected element
     * @param ElementalDamage Inputted elemental damage ONLY
     * @param ChosenSubElement Selected subelement
     * @param SubElementalDamage Inputted subelemental damage ONLY
     * @param Affinity Inputted affinity
     */

    public DamageCalculation(Context context, UI ui, String Weapon, float RawDamage, String ChosenElement,
                             float ElementalDamage, String ChosenSubElement, float SubElementalDamage,
                             float Affinity){
        Stats = new StatsValidation(RawDamage, ChosenElement, ElementalDamage, ChosenSubElement,
                SubElementalDamage, Affinity);
        Skills = ui.Skills;

        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.MainElm = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.SubElm = SubElementalDamage;
        this.ChosenSubElement = ChosenSubElement;
        this.Affinity = Affinity;

        this.Weapon = ui.getIntent().getStringExtra("Weapon");

        DualElement = SubElementalDamage > 0;

        M = new MonsterCalculation(context,
                ui.ChosenMonster + "RawHitzones_Cut",
                ui.ChosenMonster + "ElmHitzones_" + ChosenElement,
                ui.ChosenMonster + "ElmHitzones_" + ChosenSubElement,
                ui.ChosenMonster + "_StaggerLimits",
                ui.HitzoneGroup + "Hitzones",
                ui.ChosenHitzone,
                "Dual Blades");

        M.getHitzones(context, ChosenElement, ChosenSubElement, Skills, ui.WeaknessExploitCheck.isChecked());

        MV = context.getResources().getIntArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_MV_" + ui.DBMode, "array", context.getPackageName()));
        MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_Names_" + ui.DBMode, "array", context.getPackageName()));

        setSharpness();
        setHA_MV();
        MVs.clear();
        MV_NamesList.clear();
    }//Dual Blades

    public DamageCalculation(Context context, UI ui, String Weapon,float RawDamage, float Affinity){
        Stats = new StatsValidation(RawDamage,Affinity);
        Skills = ui.Skills;


        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.Affinity = Affinity;

        this.Weapon = ui.getIntent().getStringExtra("Weapon");

        M = new MonsterCalculation(context,
                ui.ChosenMonster + "",
                ui.ChosenMonster + "_StaggerLimits",
                ui.HitzoneGroup + "Hitzones",
                ui.ChosenHitzone);
        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());

        //setMVs();
        //setSharpness();
        setHA_MV();
        MVs.clear();
        MV_NamesList.clear();
    }//Bowgun

    public void Calculate(int counter){
        float TrueRaw = 0;

        alterHitzones(counter);

        if(ui.ChosenArt.equals("-None-")) MV_NamesList.add(MV_Names[counter]);
        else MV_NamesList.add(HA_Levels[counter]);

        switch(Weapon) {
            case "GS":
                setLionsMawModifier();
                break;
            case "DB":
                getDBElmMod(MV_Names[counter]);
                break;
            case "LS":
                LSMVCheck(counter);
                break;
            case "CB":
                CBAxeAlter(counter);
                break;
        }

        switch(Weapon) {
            case "HBG":
            case "LBG":
                GunnerCalc(counter, TrueRaw);
                break;
            default:
                if(ui.ChosenArt.equals("-None-")){
                    if(ui.AirborneCheck.isChecked() && ui.SkillCheck && (MV_Names[counter].contains("Jump")
                            || MV_Names[counter].contains("Aerial") || MV_Names[counter].contains("Vault"))){
                        Skills.setAirborneModifier(ui.AirborneCheck.isChecked());
                    }
                    else Skills.setAirborneModifier(false);
                    TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * MV[counter] * 0.01f;
                }
                else TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) *
                        ((MV[counter]  * 0.01f) * BrimstoneCounterModifier(counter));

                MVs.add(getTrueAttack(counter, TrueRaw));
                SpecialAlter(counter, RawDamage);
                alterHA_MV(counter, TrueRaw);
                break;
        }

//        ModifiedRawHitzone = (M.getRawHitzoneValue() * (SharpnessModifier_Atk * SNSSharpnessMod() *
//                GSChargeMod_Atk(counter) * Skills.getGroupDSharp())) / 100;
//        HitzoneRaw = TrueRaw * getCalculatedRawHitzone(counter, TrueRaw);

//        ModifiedElmHitzone = (M.getElmHitzoneValue() * (SharpnessModifier_Elm * SNSSharpnessMod() *
//                GSChargeMod_Elm(counter) * Skills.getGroupDSharp())) / 100;
//        HitzoneElm = Skills.getTrueElm(ElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
//                ModifiedElmHitzone * HitMultiplier(counter);

//        ModifiedSubElmHitzone = (M.getSubElmHitzoneValue() * (SharpnessModifier_Elm * Skills.getGroupDSharp())) / 100;
//        HitzoneSubElm = Skills.getTrueSubElm(SubElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
//                ModifiedSubElmHitzone;

        //Hitzone Modification - End
//
//        if (!ui.ChosenArt.equals("-None-"))
//            TrueAttack = getCalculatedRawHitzone(counter, TrueRaw) + (getCalculatedElm(counter) *
//                    HA_ElementCheck[counter]);
//        else{
//            if(MV_Names[counter].equals("Kick")) TrueAttack = 2;
//            else TrueAttack = getCalculatedRawHitzone(counter, TrueRaw) + getCalculatedElm(counter) + getCalculatedSubElm();
//        }

//        MVs.add(getTrueAttack(counter, TrueRaw));
//        SpecialAlter(counter, RawDamage);
//        alterHA_MV(counter, TrueRaw);

        //TODO 11/07/2019: Add in SA Energy Charge functionality (for new and old code base)
        //TODO: Make sure Valor shelling works for GL (see old code base for reference)
        //TODO:
    }

    public void setGunnerMVs(String SelectedShot){
        //String Shot = String.valueOf(SelectedShot.getSelectedItem());
        if(ElementShots.contains(SelectedShot)){
            if (SelectedShot.equals("Flaming S Lv1") || SelectedShot.equals("Freeze S Lv1") || SelectedShot.equals("Water S Lv1") || SelectedShot.equals("Thunder S Lv1")) {
                MotionAtk[0] = 0.07f;
                MotionAtk[1] = 0.45f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (SelectedShot.equals("Flaming S Lv2") || SelectedShot.equals("Freeze S Lv2") || SelectedShot.equals("Water S Lv2") || SelectedShot.equals("Thunder S Lv2")) {
                MotionAtk[0] = 0.07f;
                MotionAtk[1] = 0.58f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (SelectedShot.equals("Dragon S Lv1")) {
                MotionAtk[0] = 0.01f;
                MotionAtk[1] = 2f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
                //getChosenElement("Dragon");
            }
            else if (SelectedShot.equals("Dragon S Lv2")) {
                MotionAtk[0] = 0.01f;
                MotionAtk[1] = 2.4f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
                //getChosenElement("Dragon");
            }
            else if (SelectedShot.equals("P.Flaming S Lv1") || SelectedShot.equals("P.Freeze S Lv1") || SelectedShot.equals("P.Water S Lv1") || SelectedShot.equals("P.Thunder S Lv1")) {
                MotionAtk[0] = 0.06f;
                MotionAtk[1] = 0.6f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }
            else if (SelectedShot.equals("P.Flaming S Lv2") || SelectedShot.equals("P.Freeze S Lv2") || SelectedShot.equals("P.Water S Lv2") || SelectedShot.equals("P.Thunder S Lv2")) {
                MotionAtk[0] = 0.15f;
                MotionAtk[1] = 1.35f;
                MotionAtk[2] = 0f;
                MotionAtk[3] = 0f;
                ShotType = "Elemental";
            }

            if(SelectedShot.contains("Flaming")) Element = "Fire";
            else if(SelectedShot.contains("Freeze")) Element = "Ice";
            else if(SelectedShot.contains("Water")) Element = "Water";
            else if(SelectedShot.contains("Thunder")) Element = "Thunder";
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

    private void GunnerCalc(int counter, float TrueRaw){
        List<String> HitzoneCatchList = Arrays.asList("Head", "Chin", "Horn", "NONE");
        List<String> StyleCatchList = Arrays.asList("Guild", "Striker", "Aerial", "Alchemy");

        Skills.setAerialShotModifierBG(ui.AerialShotSelect.isChecked(), ui.ChosenStyle.equals("Aerial"));
        Skills.setAirborneModifier(ui.AirborneCheck.isChecked());

        String Distance = String.valueOf(ui.DistanceSelect.getSelectedItem());
                //ShotText = String.valueOf(ui.SelectedShot.getSelectedItem());

        //float TrueRaw,
        float TrueAttack, HitzoneRaw, HitzoneElm;

        TrueRaw = Skills.getTrueRaw(RawDamage * 1.48f, Affinity, ui.SkillCheck) * ui.MotionAtk[counter];

        //Hitzone Modification - Start

        HitzoneRaw = (TrueRaw * M.getRawHitzoneValue()) / 100;
        M.setElmHitzoneValue(context,ui.ChosenMonster + "ElmHitzones_Fire");
        HitzoneElm = (RawDamage * ui.MotionAtk[counter] * M.getElmHitzoneValue()) / 100;

        //Hitzone Modification - End

        //TODO 05/11/2019: Double check the logic in ths switch
        switch(ui.ShotType) {
            case "Triblast":
            case "Crag":
            case "Clust":
                switch(counter){
                    case 0:
                        if (ui.AerialShotSelect.isChecked()) /*TrueAttack =*/ MVs.add(HitzoneRaw * Skills.getAerialShotModifier());
                        else /*TrueAttack =*/ MVs.add(HitzoneRaw * Skills.DistanceModifier(Distance));
                        break;
                    case 1:
                        if (ui.ShotType.equals("Crag"))
                            /*TrueAttack =*/ MVs.add(ui.MotionAtk[1] * (Skills.getArtilleryModifier() *
                                    Skills.getFelyneBombardierModifier()));
                        else /*TrueAttack =*/ MVs.add(ui.MotionAtk[1]);
                        break;
                    case 2:
                        TrueAttack = HitzoneElm;
                        MVs.add(TrueAttack);
                        break;
                    default:
                        TrueAttack = ui.MotionAtk[3];
                        MVs.add(TrueAttack);
                        break;
                }

                if (!HitzoneCatchList.contains(ui.ChosenHitzone) && counter == 3) {
                    //Info.setVisibility(View.VISIBLE);
                    //Banner.setText(ShotText);
                    break;
                }
                break;
            case "Cannon":
                TrueAttack = (TrueRaw * MotionAtk[counter] * M.getRawHitzoneValue()) / 100;

                if (counter == 0) {
                    if (ui.AerialShotSelect.isChecked()) {
                        TrueAttack *= Skills.getAerialShotModifier();
                        MVs.add(TrueAttack);
//                        Snackbar.make(view, "Aerial Shots are all Critical, no matter what distance", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                    }
                    else {
                        TrueAttack *= Skills.DistanceModifier(String.valueOf(ui.DistanceSelect.getSelectedItem()));
                        MVs.add(TrueAttack);
                    }
                }
                else if (counter == 1){
                    TrueAttack = MotionAtk[1] * (Skills.getArtilleryModifier() * Skills.getFelyneBombardierModifier());
                    MVs.add(TrueAttack);
                }
                else{
                    TrueAttack = MotionAtk[2];
                    MVs.add(TrueAttack);
                }

                if (MotionAtk[counter] == 0f){
//                    Info.setVisibility(View.VISIBLE);
//                    Banner.setText(ShotType);
                    return;
                }

                if(!HitzoneCatchList.contains(ui.ChosenHitzone) && counter == 2){
//                    Info.setVisibility(View.VISIBLE);
//                    Banner.setText(ShotText);
                    break;
                }
            default:
                break;
        }

        //MVs.add(getTrueAttack(counter, HitzoneRaw + HitzoneElm));

    }
    //Private Calculations

    private float getTrueAttack(int counter, float TrueRaw){
        if (!ui.ChosenArt.equals("-None-"))
            return getCalculatedRawHitzone(counter, TrueRaw) + (getCalculatedElm(counter) *
                    HA_ElementCheck[counter]);
        else{
            switch(Weapon){
                case "GL":
                    return getCalculatedRawHitzone(counter, TrueRaw * Skills.getHeatGaugeModifier()) +
                            getCalculatedElm(counter);
                case "SA":
                    return getCalculatedRawHitzone(counter, TrueRaw * Skills.getSAPhialAtkModifier() *
                            Skills.getDemonRiotModifier()) + (getCalculatedElm(counter) * Skills.getSAPhialElmModifier());
            }

            if(MV_Names[counter].equals("Kick")) return 2;
            else return getCalculatedRawHitzone(counter, TrueRaw) + getCalculatedElm(counter) + getCalculatedSubElm();
        }
    }

    private int HitMultiplier(int counter){
        switch (Weapon){
            case "LS":
                if (MV_Names[counter].contains("2 hits") || ((MV_Names[counter].contains("Jump Spirit Slash")
                        || MV_Names[counter].equals("   -(With Spirit Energy)")) && !ui.SpiritGaugeColour.equals("No Colour")))
                    return 2;
                else if (MV_Names[counter].contains("3 hits")) return 3;
                return 1;
            case "DB":
                return 1;
            default:
                if(MV_Names[counter].contains("Both hits") || MV_Names[counter].contains("2 hits")) return 2;
                return 1;
        }
    }

    private float getCalculatedRawHitzone(int counter, float TrueRaw){
        return TrueRaw * ((M.getRawHitzoneValue() * (SharpnessModifier_Atk * SNSSharpnessMod() *
                GSChargeMod_Atk(counter) * Skills.getGroupDSharp())) / 100);
    }

    private float getCalculatedElm(int counter){
        return Skills.getTrueElm(ElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
                ((M.getElmHitzoneValue() * (SharpnessModifier_Elm * SNSSharpnessMod() *
                        GSChargeMod_Elm(counter) * Skills.getGroupDSharp())) / 100) *
                HitMultiplier(counter);
    }

    private float getCalculatedSubElm(){
        if(Weapon.equals("DB"))
            return Skills.getTrueSubElm(SubElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
                    ((M.getSubElmHitzoneValue() * (SharpnessModifier_Elm * Skills.getGroupDSharp())) / 100);
        else return 0;
    }

    private void alterHitzones(int counter){
        switch(Weapon){
            case "GS":
            case "SNS":
            case "HH":
            case "Lance":
            case "Bow":
                M.alterHitzones(context, ui.ChosenMonster, MV_Names[counter], Weapon, ui.ChosenArt.equals("Blade Wire"));
                if (Weapon.equals("Lance")) M.getHitzones_Lance(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
                break;
            case "LBG":
            case "HBG":
                M.alterHitzones(context, ui.ChosenMonster);
                break;
            default:
                break;
        }
    }

    private void alterHA_MV(int counter, float TrueRaw){
        if(!ui.ChosenArt.equals("-None-")) {
            switch (ui.ChosenArt) {
                case "Sonic Smash":
                    if ((counter % 2) == 1) MVs.set(counter, MV[counter] * (1 + TrueRaw * 0.16f));
                    break;
                default:
                    break;
            }
        }
    }

    private void setMVs(){
        switch (Weapon){
            //TODO 04/07/2019: Make sure that GL shells, SA Tempest Axe/Phials and CB Phials are
            //TODO: calculated properly.
            //TODO 04/07/2019: Consider merging split MV arrays together for SA and CB
            case "GL":
                MV = context.getResources().getIntArray(context.getResources().getIdentifier("GL_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));
                MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier("GL_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));

                String[] ShellingNames = context.getResources().getStringArray(context.getResources().
                        getIdentifier("GL_Shelling_Names", "array", context.getPackageName()));

                int[] ShellingMVs = context.getResources().getIntArray(context.getResources().
                        getIdentifier("GL_Shelling_" + String.valueOf(ui.ShotTypeSelect.getSelectedItem()) + "_MV", "array", context.getPackageName()));

                String[] ValorNames = context.getResources().getStringArray(context.getResources().
                        getIdentifier("GL_Shelling_Valor_Mods_Names", "array", context.getPackageName()));

                int[] ValorMVs = context.getResources().getIntArray(context.getResources().
                        getIdentifier("GL_Shelling_Valor_Mods_MV", "array", context.getPackageName()));

                int[] FullBurstMods = context.getResources().getIntArray(context.getResources().
                        getIdentifier("GL_Full_Burst_Valor_Mods_MV", "array", context.getPackageName()));

                String[] tempGLNames = MV_Names;
                int[] tempGLMVs = MV;

                MV_Names = new String[tempGLNames.length + 12];
                MV = new int[MV_Names.length];
                ValorFullBurstMods = new int[7];

                System.arraycopy(tempGLNames, 0, MV_Names, 0, tempGLNames.length);
                System.arraycopy(ShellingNames, 0, MV_Names, tempGLNames.length, 6);
                System.arraycopy(ValorNames, 0, MV_Names, tempGLNames.length + ValorNames.length, 6);

                System.arraycopy(FullBurstMods, 0, ValorFullBurstMods, 0, FullBurstMods.length);

                switch(String.valueOf(ui.ShotLevelSelect.getSelectedItem())){
                    case "Level 1":
                        System.arraycopy(tempGLMVs, 0, MV, 0, tempGLMVs.length);
                        System.arraycopy(ShellingMVs, 0, MV, tempGLMVs.length, 6);
                        break;
                    case "Level 2":
                        System.arraycopy(tempGLMVs, 0, MV, 0, tempGLMVs.length);
                        System.arraycopy(ShellingMVs, 6, MV, tempGLMVs.length, 6);
                        break;
                    case "Level 3":
                        System.arraycopy(tempGLMVs, 0, MV, 0, tempGLMVs.length);
                        System.arraycopy(ShellingMVs, 12, MV, tempGLMVs.length, 6);
                        break;
                    case "Level 4":
                        System.arraycopy(tempGLMVs, 0, MV, 0, tempGLMVs.length);
                        System.arraycopy(ShellingMVs, 18, MV, tempGLMVs.length, 6);
                        break;
                    case "Level 5":
                        System.arraycopy(tempGLMVs, 0, MV, 0, tempGLMVs.length);
                        System.arraycopy(ShellingMVs, 24, MV, tempGLMVs.length, 6);
                        break;
                }
                System.arraycopy(ValorMVs, 0, MV, tempGLMVs.length + ValorMVs.length, 6);
                break;
            case "SA":
                MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_Axe_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));
                MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier("SA_Axe_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));

                String[] SwordNames = context.getResources().getStringArray(context.getResources().
                        getIdentifier("SA_Sword_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));;
                int[] SwordMVs = context.getResources().getIntArray(context.getResources().
                        getIdentifier("SA_Sword_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));

                String[] tempSANames = MV_Names;
                int[] tempSAMVs = MV;

                MV_Names = new String[tempSANames.length + SwordNames.length];
                MV = new int[tempSAMVs.length + SwordMVs.length];

                System.arraycopy(tempSANames, 0, MV_Names, 0, tempSANames.length);
                System.arraycopy(SwordNames, 0, MV_Names, tempSANames.length, SwordNames.length);

                System.arraycopy(tempSAMVs, 0, MV, 0, tempSAMVs.length);
                System.arraycopy(SwordMVs, 0, MV, tempSAMVs.length, SwordMVs.length);
                break;
            case "CB":
                MV = context.getResources().getIntArray(context.getResources().getIdentifier(Weapon + "_Sword_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));
                MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier(Weapon + "_Sword_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));

                int Charge;
                if(String.valueOf(ui.ShieldChargeSelect.getSelectedItem()).equals("No Charge"))
                    Charge = ui.SelectedPhialNoCharge;
                else{
                    Charge = ui.SelectedPhialCharge;
                }

                String[] AxeNames = context.getResources().getStringArray(context.getResources().
                        getIdentifier("CB_Axe_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));;
                int[] AxeMVs = context.getResources().getIntArray(context.getResources().
                        getIdentifier("CB_Axe_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));

                String[] PhialNames = context.getResources().getStringArray(context.getResources().
                        getIdentifier("CB_Burst_Names", "array", context.getPackageName()));

                int[] PhialMVs = context.getResources().getIntArray(Charge);

                String[] tempCBNames = MV_Names;
                int[] tempCBMVs = MV;

                MV_Names = new String[tempCBNames.length + AxeNames.length + PhialNames.length];
                MV = new int[tempCBMVs.length + AxeMVs.length + PhialMVs.length];

                System.arraycopy(tempCBNames, 0, MV_Names, 0, tempCBNames.length);
                System.arraycopy(AxeNames, 0, MV_Names, tempCBNames.length, AxeNames.length);
                System.arraycopy(PhialNames, 0, MV_Names, tempCBNames.length + AxeNames.length, PhialNames.length);

                System.arraycopy(tempCBMVs, 0, MV, 0, tempCBMVs.length);
                System.arraycopy(AxeMVs, 0, MV, tempCBMVs.length, AxeMVs.length);
                System.arraycopy(PhialMVs, 0, MV, tempCBMVs.length + AxeMVs.length, PhialMVs.length);
                break;
            case "IG":
                MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_" + ui.ChosenStyle + "_MV_" + ui.Extract, "array", context.getPackageName()));
                MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier("IG_" + ui.ChosenStyle + "_Names_" + ui.Extract, "array", context.getPackageName()));
                break;
            default:
                MV = context.getResources().getIntArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));
                MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));
                break;
        }
    }

    private void setHA_MV(){
        if(!ui.ChosenArt.equals("-None-")) {
            switch (ui.ChosenArt) {
                case "Brimstone Slash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_BrimstoneSlash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("GS_HA_BrimstoneSlash_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_BrimstoneSlash_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Ground Slash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_GroundSlash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Lions Maw (Wide Slash)":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_LionsMaw_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Moon Breaker":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Sakura Slash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("LS_HA_SakuraSlash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("LS_HA_SakuraSlash_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Unhinged Spirit":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("LS_HA_UnhingedSpirit_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Critical Juncture":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("LS_HA_CriticalJuncture_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Shoryugeki":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_Shoryugeki_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_Shoryugeki_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Round Force":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_RoundForce_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_RoundForce_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Sword Dance":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_SwordDance_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SNS_HA_SwordDance_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Blood Wind":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_BloodWind_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_BloodWind_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Aerial Slam":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_AerialSlam_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_AerialSlam_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Spiral Rend":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_SpiralRend_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("DB_HA_SpiralRend_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Spinning Meteor":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Hammer_HA_SpinningMeteor_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Hammer_HA_SpinningMeteor_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("Hammer_HA_SpinningMeteor_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Typhoon Trigger":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Hammer_HA_TyphoonTrigger_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Hammer_HA_TyphoonTrigger_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("Hammer_HA_TyphoonTrigger_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Provoke":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Hammer_HA_Provoke_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Sonic Smash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("HH_HA_SonicSmash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HH_HA_SonicSmash_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HH_HA_SonicSmash_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Shield Assault":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Lance_HA_ShieldAssault_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Lance_HA_ShieldAssault_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Corkscrew Jab":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Lance_HA_CorkscrewJab_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("Lance_HA_CorkscrewJab_MV_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Dragon Blast":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GL_HA_DragonBlast_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Blast Dash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GL_HA_BlastDash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Trance Slash":
                    if(!ui.DemonRiotOffCheck.isChecked() && ui.TempestAxeCheck.isChecked()){//Demon Riot and Tempest Axe
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_All_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_All_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else if(ui.DemonRiotOffCheck.isChecked() && ui.TempestAxeCheck.isChecked()){//Tempest Axe
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_TA_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_TA_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else if(!ui.DemonRiotOffCheck.isChecked() && !ui.TempestAxeCheck.isChecked()){//Demon Riot
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_DR_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_DR_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else{//Base
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    break;
                case "Energy Charge (Side Slash)":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_EnergyCharge_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Energy Blade":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_EnergyBlade_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Ripper Shield":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_RipperShield_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_RipperShield_MV_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Extract Hunter":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_ExtractHunter_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Swarm":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_Swarm_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Bug Blow":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_BugBlow_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("IG_HA_BugBlow_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_BugBlow_MV_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Anti-Monster Mine":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_AntiMonsterMine_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Anti-Monster Mine+":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_AntiMonsterMine2_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Claw Dance":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_ClawDance_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Explosive Roll":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_ExplosiveRoll_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Felyne Comet":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_FelyneComet_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Mini Barrel Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_MiniBarrelBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Barrel Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_BarrelBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Bounce Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_BounceBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Big Barrel Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_BigBarrelBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Mega Barrel Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_MegaBarrelBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Giga Barrel Bombay":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_GigaBarrelBombay_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Rath of Meow":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_RathofMeow_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Shock Tripper":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_ShockTripper_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Boomerang Type":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("Prowler_HA_BomerangType_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("Prowler_HA_MV_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                default:
                    //HA_MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_MV", "array", context.getPackageName()));
                    //HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    //HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_ElmCheck", "array", context.getPackageName()));
                    break;
            }
        }
    }

    private void setSharpness(){
        int SharpnessRaw, SharpnessElm;
        switch (ui.Sharpness){
            case "Red":
                SharpnessRaw = R.integer.Red_Raw;
                SharpnessElm = R.integer.Red_Elm;
                break;
            case "Orange":
                SharpnessRaw = R.integer.Orange_Raw;
                SharpnessElm = R.integer.Orange_Elm;
                break;
            case "Yellow":
                SharpnessRaw = R.integer.Yellow_Raw;
                SharpnessElm = R.integer.Yellow_Elm;
                break;
            case "Green":
                SharpnessRaw = R.integer.Green_Raw;
                SharpnessElm = R.integer.Green_Elm;
                break;
            case "Blue":
                SharpnessRaw = R.integer.Blue_Raw;
                SharpnessElm = R.integer.Blue_Elm;
                break;
            case "White":
                SharpnessRaw = R.integer.White_Raw;
                SharpnessElm = R.integer.White_Elm;
                break;
            default:
                SharpnessRaw = R.integer.Purple_Raw;
                SharpnessElm = R.integer.Purple_Elm;
                break;
        }

        SharpnessModifier_Atk = context.getResources().getInteger(SharpnessRaw);
        SharpnessModifier_Elm = context.getResources().getInteger(SharpnessElm);
        SharpnessModifier_Atk /= 100;
        SharpnessModifier_Elm /= 100;
    }

    private void SpecialAlter(int counter, float Damage){
        switch(Weapon){
            case "GL":
                GLShelling(counter);
                break;
            case "CB":
                CBPhials(counter, Damage);
                break;
        }
    }


    //Public retrievers/usage

    public boolean getBounce(int counter, int TrueRaw){
        return (getCalculatedRawHitzone(counter, TrueRaw) * 100) < 25;
    }

    public String getStagger(){
        return "Stagger/Break Limit: " + M.getStaggerValue();
    }

    public int getAtkPwr(int counter){
        return Math.round(MVs.get(counter));
    }

    public String getMVName(int counter){
        return String.valueOf(MV_NamesList.get(counter));
    }

    public int getMVSize(){
        return MV.length;
    }

    public String getExtract(){
        return M.getExtract();
    }

    public void setHitzone(){
        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
    }

    public void setHitzone_DB(){
        M.getHitzones(context, ChosenElement, ChosenSubElement, Skills, ui.WeaknessExploitCheck.isChecked());
    }

    public boolean CalculateSkills(){
        if(!Weapon.equals("Prowler")){
            if(!(Weapon.equals("HBG") || Weapon.equals("LBG"))){
                if(!Weapon.equals("Bow")) Skills.setBludgeonerModifier(ui.BludgeonerCheck.isChecked());

                Skills.setElementAtkUp(ui.ElementalAtkUpCheck.isChecked());
                Skills.setElementCrit(ui.ElementalCritCheck.isChecked(), Affinity);
            }

            switch(Weapon){
                case "GS":
                case "LS":
                    if(Weapon.equals("LS")){
                        Skills.setMaxSpiritGaugeModifier(ui.MaxSpiritGaugeCheck.isChecked());

                        if(ui.SacrificialBladeLevel1Check.isChecked()) Skills.setSacrificialBladeModifier(true,1);
                        else if(ui.SacrificialBladeLevel2Check.isChecked()) Skills.setSacrificialBladeModifier(true,2);
                        else if(ui.SacrificialBladeLevel3Check.isChecked()) Skills.setSacrificialBladeModifier(true,3);
                        else if(ui.SacrificialBladeOffCheck.isChecked()) Skills.setSacrificialBladeModifier(false,0);
                    }
                    Skills.setCentreBladeModifier(ui.CentreBladeBonusCheck.isChecked());
                    break;
                case "SNS":
                    Skills.setAffinityOilModifier(ui.AirborneCheck.isChecked());
                    break;
                case "Hammer":
                    Skills.setProvokeModifier(ui.ProvokeCheck.isChecked());
                    break;
                case "SA":
                    if(ui.DemonRiotLevel1Check.isChecked())
                        Skills.setDemonRiotModifier(!ui.DemonRiotOffCheck.isChecked(), ui.DemonRiotLevel1Check.getId());
                    else if(ui.DemonRiotLevel2Check.isChecked())
                        Skills.setDemonRiotModifier(!ui.DemonRiotOffCheck.isChecked(), ui.DemonRiotLevel2Check.getId());
                    else if(ui.DemonRiotLevel3Check.isChecked())
                        Skills.setDemonRiotModifier(!ui.DemonRiotOffCheck.isChecked(), ui.DemonRiotLevel3Check.getId());
                    else
                        Skills.setDemonRiotModifier(!ui.DemonRiotOffCheck.isChecked(), 0);
                    break;
                case "HBG":
                    Skills.setGunpowderInfusionModiifer(ui.GunpowderInfusionCheck.isChecked());
                    break;
            }

//            if(Weapon.equals("GS") || Weapon.equals("LS")){
//                if(Weapon.equals("LS")){
//                    Skills.setMaxSpiritGaugeModifier(ui.MaxSpiritGaugeCheck.isChecked());
//
//                    if(ui.SacrificialBladeLevel1Check.isChecked()) Skills.setSacrificialBladeModifier(true,1);
//                    else if(ui.SacrificialBladeLevel2Check.isChecked()) Skills.setSacrificialBladeModifier(true,2);
//                    else if(ui.SacrificialBladeLevel3Check.isChecked()) Skills.setSacrificialBladeModifier(true,3);
//                    else if(ui.SacrificialBladeOffCheck.isChecked()) Skills.setSacrificialBladeModifier(false,0);
//                }
//                Skills.setCentreBladeModifier(ui.CentreBladeBonusCheck.isChecked());
//            }
//            else if(Weapon.equals(("SNS"))) Skills.setAffinityOilModifier(ui.AirborneCheck.isChecked());
//            else if(Weapon.equals("HBG")) Skills.setGunpowderInfusionModiifer(ui.GunpowderInfusionCheck.isChecked());

            Skills.setRepeatOffenderModifier(ui.RepeatOffenderCheck.isChecked());
            Skills.setCritBoostModifier(ui.CriticalBoostCheck.isChecked());
            Skills.setPowercharmModifier(ui.PowercharmCheck.isChecked());
            Skills.setPowertalonModifier(ui.PowertalonCheck.isChecked());
            Skills.setFelyneBoosterModifier(ui.FelyneBoosterCheck.isChecked());
            Skills.setCrisisModifier(ui.CrisisCheck.isChecked());
            Skills.setFurorModifier(ui.FurorCheck.isChecked());

            return Skills.CheckElmSkill(ElementalDamage, ui.SkillCheck);
        }
        return false;
    }


    //Weapon Specific things

    private float BrimstoneCounterModifier(int counter){
        if(Weapon.equals("GS")) {
            if (!ui.ChosenArt.equals("-None-") && HA_Levels[counter].contains("Counter") && ui.ChosenArt.equals("Brimstone Slash"))
                return 1.5f;
            else return 1f;
        }
        else return 1f;
    }

    private void setLionsMawModifier(){
        if(ui.LionsMawLevel1Check.isChecked())
            Skills.setLionsMawModifier(!ui.LionsMawOffCheck.isChecked(), ui.LionsMawLevel1Check.getId());
        else if(ui.LionsMawLevel2Check.isChecked())
            Skills.setLionsMawModifier(!ui.LionsMawOffCheck.isChecked(), ui.LionsMawLevel2Check.getId());
        else if(ui.LionsMawLevel3Check.isChecked())
            Skills.setLionsMawModifier(!ui.LionsMawOffCheck.isChecked(), ui.LionsMawLevel3Check.getId());
        else
            Skills.setLionsMawModifier(!ui.LionsMawOffCheck.isChecked(), 0);
    }

    private float GSChargeMod_Atk(int counter){
        if(Weapon.equals("GS")) {
            if (MV_Names[counter].contains("Slap"))
                M.alterHitzones(context, ui.ChosenMonster, MV_Names[counter], "", false);
            if (ui.ChosenArt.equals("-None-")) {
                switch (MV_Names[counter]){
                    case "   - Lv. 1 Strong Charge":
                    case "Lv. 1 Strong Charge":
                    case "Lv. 1 Charge":
                    case "   -Follow Up ":
                    case "Aerial Lv. 1 Charge":
                    case "Adept Evade Lv.1 Dash Charge":
                    case "Blue Charge Lv.1 (Valor State)":
                    case "Draw Slash Lv.1 (Valor State)":
                        return 1.1f;
                    case "   - Lv. 2 Strong Charge":
                    case "Lv. 2 Strong Charge":
                    case "Lv. 2 Charge":
                    case "   -Follow Up  ":
                    case "Aerial Lv. 2 Charge":
                    case "Adept Evade Lv.2 Dash Charge":
                    case "Blue Charge Lv.2 (Valor State)":
                    case "Draw Slash Lv.2 (Valor State)":
                        return 1.2f;
                    case "   - Lv. 3 Strong Charge":
                    case "Lv. 3 Strong Charge":
                    case "Lv. 3 Charge":
                    case "   -Follow Up   ":
                    case "Aerial Lv. 3 Charge":
                    case "Adept Evade Lv.3 Dash Charge":
                    case "Blue Charge Lv.3 (Valor State)":
                    case "Draw Slash Lv.3 (Valor State)":
                        return 1.3f;
                    default:
                        return 1;
                }
//                if (MV_Names[counter].equals("   - Lv. 1 Strong Charge") || MV_Names[counter].equals("Lv. 1 Strong Charge")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("   - Lv. 2 Strong Charge") || MV_Names[counter].equals("Lv. 2 Strong Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("   - Lv. 3 Strong Charge") || MV_Names[counter].equals("Lv. 3 Strong Charge")) {
//                    return 1.3f;
//                } else if (MV_Names[counter].equals("Lv. 1 Charge")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("   -Follow Up ")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("Lv. 2 Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("   -Follow Up  ")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("Lv. 3 Charge")) {
//                    return 1.3f;
//                } else if (MV_Names[counter].equals("   -Follow Up   ")) {
//                    return 1.3f;
//                } else if (MV_Names[counter].equals("Aerial Lv. 1 Charge")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("Aerial Lv. 2 Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("Aerial Lv. 3 Charge")) {
//                   return 1.3f;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.1 Dash Charge")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.2 Dash Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.3 Dash Charge")) {
//                    return 1.3f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.0 (Valor State)") || MV_Names[counter].equals("Draw Slash (Valor State)")) {
//                    return 1f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.1 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.1 (Valor State)")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.2 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.2 (Valor State)")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.3 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.3 (Valor State)")) {
//                    return 1.3f;
//                }
            } else {
                if ((HA_Levels[counter].equals("Level I") || HA_Levels[counter]
                        .equals("   -Strong Attack Counter Hit ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1.1f;
                } else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter]
                        .equals("   -Strong Attack Counter Hit  ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1.2f;
                } else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter]
                        .equals("   -Strong Attack Counter Hit   ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1.33f;
                }
            }
        }
        return 1;
    }

    private float GSChargeMod_Elm(int counter){
        if(Weapon.equals("GS")) {
            if (MV_Names[counter].contains("Slap"))
                M.alterHitzones(context, ui.ChosenMonster, MV_Names[counter], "", false);
            M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
            if (ui.ChosenArt.equals("-None-")) {
                switch (MV_Names[counter]){
                    case "Adept Evade Lv.1 Dash Charge":
                        return 1.1f;
                    case "Lv. 1 Charge":
                    case "Adept Evade Lv.2 Dash Charge":
                        return 1.2f;
                    case "Lv. 2 Charge":
                    case "Draw Slash (Valor State)":
                    case "Blue Charge Lv.0 (Valor State)":
                        return 1.5f;
                    case "Blue Charge Lv.1 (Valor State)":
                    case "Draw Slash Lv.1 (Valor State)":
                        return 1.65f;
                    case "   - Lv. 1 Strong Charge":
                    case "Lv. 1 Strong Charge":
                    case "Blue Charge Lv.2 (Valor State)":
                    case "Draw Slash Lv.2 (Valor State)":
                        return 1.8f;
                    case "Lv. 3 Charge":
                        return 2;
                    case "   - Lv. 2 Strong Charge":
                    case "Lv. 2 Strong Charge":
                    case "Adept Evade Lv.3 Dash Charge":
                    case "Blue Charge Lv.3 (Valor State)":
                    case "Draw Slash Lv.3 (Valor State)":
                        return 2.25f;
                    case "   - Lv. 3 Strong Charge":
                    case "Lv. 3 Strong Charge":
                        return 3;
                    default:
                        return 1;
                }
//                if (MV_Names[counter].equals("   - Lv. 1 Strong Charge") || MV_Names[counter].equals("Lv. 1 Strong Charge")) {
//                    return 1.8f;
//                } else if (MV_Names[counter].equals("   - Lv. 2 Strong Charge") || MV_Names[counter].equals("Lv. 2 Strong Charge")) {
//                    return 2.25f;
//                } else if (MV_Names[counter].equals("   - Lv. 3 Strong Charge") || MV_Names[counter].equals("Lv. 3 Strong Charge")) {
//                    return 3f;
//                } else if (MV_Names[counter].equals("Lv. 1 Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("   -Follow Up ")) {
//                    return 1f;
//                } else if (MV_Names[counter].equals("Lv. 2 Charge")) {
//                    return 1.5f;
//                } else if (MV_Names[counter].equals("   -Follow Up  ")) {
//                    return 1f;
//                } else if (MV_Names[counter].equals("Lv. 3 Charge")) {
//                    return 2f;
//                } else if (MV_Names[counter].equals("   -Follow Up   ")) {
//                    return 1f;
//                } else if (MV_Names[counter].equals("Aerial Lv. 1 Charge")) {
//                    return 1;
//                } else if (MV_Names[counter].equals("Aerial Lv. 2 Charge")) {
//                    return 1;
//                } else if (MV_Names[counter].equals("Aerial Lv. 3 Charge")) {
//                    return 1;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.1 Dash Charge")) {
//                    return 1.1f;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.2 Dash Charge")) {
//                    return 1.2f;
//                } else if (MV_Names[counter].equals("Adept Evade Lv.3 Dash Charge")) {
//                    return 2.25f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.0 (Valor State)") || MV_Names[counter].equals("Draw Slash (Valor State)")) {
//                    return 1.5f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.1 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.1 (Valor State)")) {
//                    return 1.65f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.2 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.2 (Valor State)")) {
//                    return 1.8f;
//                } else if (MV_Names[counter].equals("Blue Charge Lv.3 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.3 (Valor State)")) {
//                    return 2.25f;
//                }
            } else {
                if ((HA_Levels[counter].equals("Level I") || HA_Levels[counter].equals("   -Strong Attack Counter Hit ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1;
                } else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter].equals("   -Strong Attack Counter Hit  ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1;
                } else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter].equals("   -Strong Attack Counter Hit   ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                    return 1;
                }
            }
        }
        return 1;
    }

    private void LSMVCheck(int counter){
        if(!ui.SpiritGaugeColour.equals("No Colour") &&
                getMVName(counter).contains("Jump Spirit Slash")){
            MV[MV.length - 1] = 48;
            MV[MV.length - 2] = 48;
        }
    }

    private float SNSSharpnessMod(){
        if(Weapon.equals("SNS")) return 1.06f;
        else return 1;
    }

    private void getDBElmMod(String MoveName){

        if(DualElement && ui.ChosenArt.equals("-None-") && !ui.DBMode.equals("Normal")){
            switch(MoveName){
                case "Draw/Dash Attack (6 hits)":
                    ElementalDamage = MainElm * 2.7f;
                    SubElementalDamage = SubElm * 2.7f;
                    break;
                case "Demon Dodge Jump Attack (4 hits)":
                case "   -Off Ledge (4 hits)":
                case "Vault Attack (4 hits)":
                case "Adept Evade (4 hits)":
                    ElementalDamage = MainElm * 2;
                    SubElementalDamage = SubElm * 2;
                    break;
                case "   -Follow Up (6 hits)":
                case "Demon Combo (6 hits)":
                case "Demon Flurry Rush (6 hits)":
                    ElementalDamage = MainElm * 2.1f;
                    SubElementalDamage = SubElm * 2.1f;
                    break;
                case "Upward Slash":
                    ElementalDamage = MainElm * 0;
                    SubElementalDamage = SubElm * 1;
                    break;
                case "Left Jumping Slash (3 hits)":
                case "   -Second Jump[L] (3 hits)":
                    ElementalDamage = MainElm * 1;
                    SubElementalDamage = SubElm * 2;
                    break;
                case "Right Jumping Slash (3 hits)":
                case "   -Second Jump[R] (3 hits)":
                    ElementalDamage = MainElm * 2;
                    SubElementalDamage = SubElm * 1;
                    break;
                case "Jump Attack (2 hits)":
                case "Idle Slash (2 hits)":
                case "Reverse Slash (2 hits)":
                    ElementalDamage = MainElm * 1;
                    SubElementalDamage = SubElm * 1;
                    break;
                case "Circle Slash (3 hits)":
                    ElementalDamage = MainElm * 1.7f;
                    SubElementalDamage = SubElm * 0.7f;
                    break;
                case "Horizontal Slash - Left (2 hits)":
                    ElementalDamage = MainElm * 0;
                    SubElementalDamage = SubElm * 2;
                case "Horizontal Slash - Right (2 hits)":
                    ElementalDamage = MainElm * 2;
                    SubElementalDamage = SubElm * 0;
                    break;
                case "Demon Flurry (7 hits)":
                    ElementalDamage = MainElm * 3.7f;
                    SubElementalDamage = SubElm * 2.7f;
                    break;
                case "Devil's Dance (12 hits)":
                    ElementalDamage = MainElm * 5.7f;
                    SubElementalDamage = SubElm * 5.7f;
                    break;
            }
        }
        else if(DualElement && ui.ChosenArt.equals("-None-") && ui.DBMode.equals("Normal")){
            switch(MoveName){
                case "Draw Attack (4 hits)":
                case "Dash Attack (4 hits)":
                    ElementalDamage = MainElm * 1.4f;
                    SubElementalDamage = SubElm * 1.4f;
                    break;
                case "Idle Slash (2 hits)":
                case "Reverse Slash (2 hits)":
                case "Mid-Air Attack (2 hits)":
                case "Jump Attack (2 hits)":
                    ElementalDamage = MainElm * 1;
                    SubElementalDamage = SubElm * 1;
                    break;
                case "Upward Slash":
                    ElementalDamage = MainElm * 0;
                    SubElementalDamage = SubElm * 1;
                    break;
                case "Horizontal Slash - Left (2 hits)":
                    ElementalDamage = MainElm * 0;
                    SubElementalDamage = SubElm * 2;
                    break;
                case "Horizontal Slash - Right (2 hits)":
                    ElementalDamage = MainElm * 2;
                    SubElementalDamage = SubElm * 0;
                    break;
                case "Circle Slash (3 hits)":
                    ElementalDamage = MainElm * 1.7f;
                    SubElementalDamage = SubElm * 0.7f;
                    break;
                case "Jumping Slash - Left (3 hits)":
                    ElementalDamage = MainElm * 1;
                    SubElementalDamage = SubElm * 2;
                    break;
                case "Jumping Slash - Right (3 hits)":
                    ElementalDamage = MainElm * 2;
                    SubElementalDamage = SubElm * 1;
                    break;
            }
        }
        else if(!DualElement && ui.ChosenArt.equals("-None-")){
            SubElementalDamage = 0;
            switch(MoveName){
                case "Draw/Dash Attack (6 hits)":
                    ElementalDamage = MainElm * 5.4f;
                    break;
                case "Demon Dodge Jump Attack (4 hits)":
                    ElementalDamage = MainElm * 4;
                    break;
                case "   -Follow Up (6 hits)":
                case "Demon Combo (6 hits)":
                case "Demon Flurry Rush (6 hits)":
                    ElementalDamage = MainElm * 4.2f;
                    break;
                case "   -Off Ledge (4 hits)":
                case "Vault Attack (4 hits)":
                case "Adept Evade (4 hits)":
                    ElementalDamage = MainElm * 4;
                    break;
                case "Left Jumping Slash (3 hits)":
                case "   -Second Jump[L] (3 hits)":
                case "Right Jumping Slash (3 hits)":
                case "   -Second Jump[R] (3 hits)":
                case "Jumping Slash - Left (3 hits)":
                case "Jumping Slash - Right (3 hits)":
                    ElementalDamage = MainElm * 3;
                    break;
                case "Jump Attack (2 hits)":
                case "Idle Slash (2 hits)":
                case "Reverse Slash (2 hits)":
                case "Horizontal Slash - Left (2 hits)":
                case "Horizontal Slash - Right (2 hits)":
                //case "Idle Slash (2 hits)":
                //case "Horizontal Slash - Left (2 hits)":
                //case "Horizontal Slash - Right (2 hits)":
                case "Mid-Air Attack (2 hits)":
                    ElementalDamage = MainElm * 2;
                    break;
                case "Upward Slash":
                    ElementalDamage = MainElm * 1;
                    break;
                case "Devil's Dance (12 hits)":
                    ElementalDamage = MainElm * 11.4f;
                    break;
                case "Circle Slash (3 hits)":
                    ElementalDamage = MainElm * 2.4f;
                    break;
                case "Demon Flurry (7 hits)":
                    ElementalDamage = MainElm * 6.4f;
                    break;
                case "Draw Attack (4 hits)":
                case "Dash Attack (4 hits)":
                    ElementalDamage = MainElm * 2.8f;
                    break;
            }
        }
        else if (!ui.ChosenArt.equals("-None-")){
            if(DualElement) {
                ElementalDamage = MainElm / 2;
                SubElementalDamage = SubElm / 2;
            }
            else{
                ElementalDamage = MainElm * 1;
                SubElementalDamage = SubElm * 0;
            }
        }

//        if (DualElement && ui.ChosenArt.equals("-None-")){
//            if (MoveName.equals("Draw/Dash Attack (6 hits)")) {
//                ElementalDamage = MainElm * 2.7f;
//                SubElementalDamage = SubElm * 2.7f;
//            }
//            else if (MoveName.equals("Demon Dodge Jump Attack (4 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("   -Follow Up (6 hits)")) {
//                ElementalDamage = MainElm * 2.1f;
//                SubElementalDamage = SubElm * 2.1f;
//            }
//            else if (MoveName.equals("Upward Slash")) {
//                ElementalDamage = MainElm * 0;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Demon Combo (6 hits)")) {
//                ElementalDamage = MainElm * 2.1f;
//                SubElementalDamage = SubElm * 2.1f;
//            }
//            else if (MoveName.equals("Demon Flurry Rush (6 hits)")) {
//                ElementalDamage = MainElm * 2.1f;
//                SubElementalDamage = SubElm * 2.1f;
//            }
//            else if (MoveName.equals("   -Off Ledge (4 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Left Jumping Slash (3 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("   -Second Jump[L] (3 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Right Jumping Slash (3 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("   -Second Jump[R] (3 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Jump Attack (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Vault Attack (4 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Devil's Dance (12 hits)")) {
//                ElementalDamage = MainElm * 5.7f;
//                SubElementalDamage = SubElm * 5.7f;
//            }
//            else if (MoveName.equals("Adept Evade (4 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Idle Slash (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Reverse Slash (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Circle Slash (3 hits)")) {
//                ElementalDamage = MainElm * 1.7f;
//                SubElementalDamage = SubElm * 0.7f;
//            }
//            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
//                ElementalDamage = MainElm * 0;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 0;
//            }
//            else if (MoveName.equals("Demon Flurry (7 hits)")) {
//                ElementalDamage = MainElm * 3.7f;
//                SubElementalDamage = SubElm * 2.7f;
//            }
//
//            //Normal Mode If Statment
//
//            if (MoveName.equals("Draw Attack (4 hits)")) {
//                ElementalDamage = MainElm * 1.4f;
//                SubElementalDamage = SubElm * 1.4f;
//            }
//            else if (MoveName.equals("Dash Attack (4 hits)")) {
//                ElementalDamage = MainElm * 1.4f;
//                SubElementalDamage = SubElm * 1.4f;
//            }
//            else if (MoveName.equals("Upward Slash")) {
//                ElementalDamage = MainElm * 0;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Idle Slash (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Reverse Slash (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
//                ElementalDamage = MainElm * 0;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Jumping Slash - Left (3 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 2;
//            }
//            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 0;
//            }
//            else if (MoveName.equals("Jumping Slash - Right (3 hits)")) {
//                ElementalDamage = MainElm * 2;
//                SubElementalDamage = SubElm * 1;
//            }
//            else if (MoveName.equals("Mid-Air Attack (2 hits)")) {
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 1;
//            }
//        }
//        else if ((!DualElement) && (ui.ChosenArt.equals("-None-"))){
//            SubElementalDamage = 0;
//            if (MoveName.equals("Draw/Dash Attack (6 hits)")) {
//                ElementalDamage = MainElm * 5.4f;
//            }
//            else if (MoveName.equals("Demon Dodge Jump Attack (4 hits)")) {
//                ElementalDamage = MainElm * 4;
//            }
//            else if (MoveName.equals("   -Follow Up (6 hits)")) {
//                ElementalDamage = MainElm * 4.2f;
//            }
//            else if (MoveName.equals("Upward Slash")) {
//                ElementalDamage = MainElm * 1;
//            }
//            else if (MoveName.equals("Demon Combo (6 hits)")) {
//                ElementalDamage = MainElm * 4.2f;
//            }
//            else if (MoveName.equals("Demon Flurry Rush (6 hits)")) {
//                ElementalDamage = MainElm * 4.2f;
//            }
//            else if (MoveName.equals("   -Off Ledge (4 hits)")) {
//                ElementalDamage = MainElm * 4;
//            }
//            else if (MoveName.equals("Left Jumping Slash (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("   -Second Jump[L] (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("Right Jumping Slash (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("   -Second Jump[R] (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("Jump Attack (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Vault Attack (4 hits)")) {
//                ElementalDamage = MainElm * 4;
//            }
//            else if (MoveName.equals("Devil's Dance (12 hits)")) {
//                ElementalDamage = MainElm * 11.4f;
//            }
//            else if (MoveName.equals("Adept Evade (4 hits)")) {
//                ElementalDamage = MainElm * 4;
//            }
//            else if (MoveName.equals("Idle Slash (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Reverse Slash (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Circle Slash (3 hits)")) {
//                ElementalDamage = MainElm * 2.4f;
//            }
//            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Demon Flurry (7 hits)")) {
//                ElementalDamage = MainElm * 6.4f;
//            }
//
//            //Normal Mode If Statment
//
//            if (MoveName.equals("Draw Attack (4 hits)")) {
//                ElementalDamage = MainElm * 2.8f;
//            }
//            else if (MoveName.equals("Dash Attack (4 hits)")) {
//                ElementalDamage = MainElm * 2.8f;
//            }
//            else if (MoveName.equals("Upward Slash")) {
//                ElementalDamage = MainElm * 1;
//            }
//            else if (MoveName.equals("Idle Slash (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Jumping Slash - Left (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//            else if (MoveName.equals("Jumping Slash - Right (3 hits)")) {
//                ElementalDamage = MainElm * 3;
//            }
//            else if (MoveName.equals("Mid-Air Attack (2 hits)")) {
//                ElementalDamage = MainElm * 2;
//            }
//        }
//        else if (!ui.ChosenArt.equals("-None-")){
//            if(DualElement) {
//                ElementalDamage /= 2;
//                SubElementalDamage /= 2;
//            }
//            else{
//                ElementalDamage = MainElm * 1;
//                SubElementalDamage = SubElm * 0;
//            }
//        }
    }

    private float getWolfsMawModifier(){
        if(Weapon.equals("DB")){
            if(ui.WolfsMawLevel1Check.isChecked()) return 1.2f;
            else if(ui.WolfsMawLevel2Check.isChecked()) return 1.25f;
            else if(ui.WolfsMawLevel3Check.isChecked()) return 1.3f;
            else if(ui.WolfsMawOffCheck.isChecked()) return 1;
        }
        return 1;
    }

    private void GLShelling(int counter){
        Skills.setFelyneBombardierModifier(ui.FelyneBombardierCheck.isChecked());
        M.setElmHitzoneValue_GL(context, ui.ChosenMonster + "ElmHitzones_Fire");
        M.getHitzones(context, "Fire", Skills, false);
        switch(MV_Names[counter]){
            case "Shells":
                MVs.set(counter, (MV[counter] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked()))
                        * Skills.getShellingModifier());
                break;
            case "   -Fire Damage":
                MVs.set(counter, ((MV[counter] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) *
                        M.getElmHitzoneValue()) / 100f);
                break;
            case "Charged Shell":
                if(ui.ChosenStyle.equals("Alchemy")) return;

                MVs.set(counter, ((MV[counter - 2] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) *
                        (MV[counter] / 100f)) * Skills.getShellingModifier());
                break;
            case "   -Charged Fire Damage":
                if(ui.ChosenStyle.equals("Alchemy")) return;

                MVs.set(counter, (((MV[counter - 2] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) *
                        (MV[counter - 1] / 100f)) * M.getElmHitzoneValue()) / 100);
                break;
            case "Wyverns Fire (All hits)":
                float Mod = 1;
                if(ui.ChosenStyle.equals("Alchemy")) Mod = 0.5f;
                else if(ui.ChosenStyle.equals("Valor")){
                    switch(ui.ShellNumber){
                        case 0:
                            Mod = 0.8f;
                            break;
                        case 1:
                            Mod = 0.9f;
                            break;
                        case 2:
                            Mod = 1f;
                            break;
                        case 3:
                            Mod = 1.4f;
                            break;
                        case 4:
                            Mod = 1.5f;
                            break;
                        case 5:
                            Mod = 1.6f;
                            break;
                        case 6:
                            Mod = 1.6f;
                            break;
                    }
                }

                MVs.set(counter, ((MV[counter] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) *
                        Skills.getShellingModifier()) * Mod);
                break;
            case "Full Burst":
                float ShotTypeMod = MV[counter] / 100f;
                if(ui.ChosenStyle.equals("Striker")) return;
                else if(ui.ChosenStyle.equals("Valor")) ShotTypeMod = ValorFullBurstMods[ui.ShellNumber] / 100f;

                MVs.set(counter, (((MV[counter - 5] + Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) *
                        Skills.getShellingModifier() * ShotTypeMod) * ui.ShellNumber));
                break;
            default:
                if(MV_Names[counter].contains("Shot")) MVs.set(counter, (((MV[getMVSize() - 12]/*Normal Shell MV*/) +
                        Skills.getDragonBreathModifier(ui.DragonBreathCheck.isChecked())) * Skills.getShellingModifier()) *
                        MV[counter] / 100f);
                break;
        }
    }

    private void SAPhials(int counter, float Damage){

    }

    private void CBAxeAlter(int counter){
        //M.setElmHitzoneValue_GL(context, ui.ChosenMonster + "ElmHitzones_Fire");
        //M.getHitzones(context, "Fire", Skills, false);
        if (MV_Names[counter].equals("Ultra Burst (2 hits)") && ui.NumberofPhials == 0)
            MV[counter] = 80;
        else if (MV_Names[counter].equals("Ultra Burst (2 hits)") && ui.NumberofPhials > 0)
            MV[counter] = 105;

        if(MV_Names[counter].equals("Ultra Burst (2 hits) (Valor State)") && ui.NumberofPhials == 0)
            MV[counter] = 75;
        else if(MV_Names[counter].equals("Ultra Burst (2 hits) (Valor State)") && ui.NumberofPhials > 0)
            MV[counter] = 115;

        if(MV_Names[counter].equals("Valor Stance - Ultra Burst (2 hits)") && ui.NumberofPhials == 0)
            MV[counter] = 75;
        else if(MV_Names[counter].equals("Valor Stance - Ultra Burst (2 hits)") && ui.NumberofPhials > 0)
            MV[counter] = 85;

        if((MV_Names[counter].equals("Aerial Super Burst") ||
                MV_Names[counter].equals("Super Burst"))&& ui.NumberofPhials == 0)
            MV[counter] = 42;
        else if((MV_Names[counter].equals("Aerial Super Burst") ||
                MV_Names[counter].equals("Super Burst"))&& ui.NumberofPhials > 0)
            MV[counter] = 75;

        if(MV_Names[counter].equals("Aerial Ultra Burst") && ui.NumberofPhials == 0)
            MV[counter] = 60;
        else if(MV_Names[counter].equals("Aerial Ultra Burst") && ui.NumberofPhials > 0)
            MV[counter] = 100;

        if(MV_Names[counter].contains("Double Swing Burst (2 hits)") && ui.NumberofPhials == 0)
            MV[counter] = 50;
        else if(MV_Names[counter].contains("Double Swing Burst (2 hits)") && ui.NumberofPhials > 0)
            MV[counter] = 65;
    }

    private void CBPhials(int counter, float RawDamage){
        if(MV_Names[counter].contains("(L)") || MV_Names[counter].contains("(S)") || MV_Names[counter].contains("(All three)")){
            Skills.setFelyneBombardierModifier(ui.FelyneBombardierCheck.isChecked());

            if(ui.isImpact){
                MVs.set(counter, Skills.getCBPhialAtk(ui.isImpact, RawDamage, ElementalDamage) * MV[counter] / 100f);
                if(MV_Names[counter].contains("(S)")) MVs.set(counter, (MVs.get(counter) / 2f));
            }
            else MVs.set(counter, (Skills.getCBPhialAtk(ui.isImpact, RawDamage, ElementalDamage *
                    M.getElmHitzoneValue()) * MV[counter]) / 10000);
        }
        else if(MV_Names[counter].contains("Ultra Axe Burst (All Phials)")){
            MVs.set(counter, (Skills.getCBPhialAtk(ui.isImpact, RawDamage, ElementalDamage) * MV[counter] / 100f) *
                    ui.NumberofPhials);
        }
    }
}