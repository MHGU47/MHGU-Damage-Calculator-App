package com.mhx.marcus.mhgendamagecalc;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DamageCalculation {
    //Classes
    public StatsValidation Stats;
    private MonsterCalculation M;
    private UI ui;
    private SkillsCalculation Skills = new SkillsCalculation();

    //Lists
    private List<Float> MVs = new ArrayList<>();
    private List<String> MV_NamesList = new ArrayList<>();

    //Stat Variables
    private String Weapon, /*Style,*/ ChosenElement, ChosenSubElement/*, Monster, HitzoneGroup, Hitzone, Sharpness, HunterArt*/;
    private float RawDamage, ElementalDamage, MainElm, SubElementalDamage, SubElm, Affinity;
    private Context context;
    //private int MV_Array, MV_Names_Array, MV_HA_Array, HA_Levels_Array, HA_ElementCheck_Array, ErrorNumber;
    private float SharpnessModifier_Atk, SharpnessModifier_Elm;
    private int[] MV, HA_MV, HA_ElementCheck;
    private String[] MV_Names, HA_Levels;
    private boolean DualElement = false;//, Bounce = false;

    //TODO 19/05/2019: Add in functionality for LS

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

        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.ElementalDamage = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.Affinity = Affinity;

        this.Weapon = ui.getIntent().getStringExtra("Weapon");

        M = new MonsterCalculation(context,
                ui.ChosenMonster + "RawHitzones_Cut",
                ui.ChosenMonster + "ElmHitzones_" + ChosenElement,
                ui.ChosenMonster + "_StaggerLimits",
                ui.HitzoneGroup + "Hitzones",
                ui.ChosenHitzone);

        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());

        MV = context.getResources().getIntArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_MV", "array", context.getPackageName()));
        MV_Names = context.getResources().getStringArray(context.getResources().getIdentifier(Weapon + "_" + ui.ChosenStyle + "_Names", "array", context.getPackageName()));

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
//    public DamageCalculation(Context context, UI ui, String Weapon, Boolean HA, String HunterArt,
//                             String Style, String Sharpness, float RawDamage, String ChosenElement,
//                             float ElementalDamage, String ChosenSubElement, float SubElementalDamage,
//                             float Affinity, String Monster, String HitzoneGroup, String Hitzone){
//        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);
//        this.ui = ui;
//        this.context = context;
//
//        this.RawDamage = RawDamage;
//        this.ElementalDamage = ElementalDamage;
//        this.ChosenElement = ChosenElement;
//        this.SubElementalDamage = SubElementalDamage;
//        this.ChosenSubElement = ChosenSubElement;
//        this.Affinity = Affinity;
//
//        this.Weapon = Weapon;
//        this.HunterArt = HunterArt;
//        this.HA = !HA;
//
//        this.Monster = Monster;
//        DualElement = SubElementalDamage > 0;
//
//        M = new MonsterCalculation(context,
//                Monster + "RawHitzones_Cut",
//                Monster + "ElmHitzones_" + ChosenElement,
//                Monster + "ElmHitzones_" + ChosenSubElement,
//                Monster + "_StaggerLimits",
//                HitzoneGroup + "Hitzones",
//                Hitzone,
//                "Dual Blades");
//
//        M.getHitzones(context, ChosenElement, ChosenSubElement, Skills, ui.WeaknessExploitCheck.isChecked());
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
//    }//Dual Blades

    public DamageCalculation(Context context, UI ui, String Weapon, float RawDamage, String ChosenElement,
                             float ElementalDamage, String ChosenSubElement, float SubElementalDamage,
                             float Affinity){
        Stats = new StatsValidation(RawDamage, ChosenElement, ElementalDamage, ChosenSubElement,
                SubElementalDamage, Affinity);
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

    public void Calculate(int counter){
        float TrueRaw, TrueAttack, HitzoneRaw;
        //, HitzoneElm, HitzoneSubElm, ModifiedRawHitzone,
        //        ModifiedElmHitzone, ModifiedSubElmHitzone;

        if(ui.ChosenArt.equals("-None-")) MV_NamesList.add(MV_Names[counter]);
        else MV_NamesList.add(HA_Levels[counter]);

        if (Weapon.equals("DB")) getDBElmMod(MV_Names[counter]);
        else if (Weapon.equals("LS")) LSMVCheck(counter);

        if(ui.ChosenArt.equals("-None-")){
            if(ui.AirborneCheck.isChecked() && ui.SkillCheck && (MV_Names[counter].contains("Jump")
                    || MV_Names[counter].contains("Aerial"))){
                Skills.setAirborneModifier(ui.AirborneCheck.isChecked());
            }
            else Skills.setAirborneModifier(false);
            TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * MV[counter] * 0.01f;
        }
        else TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) *
                    ((MV[counter]  * 0.01f) * BrimstoneCounterModifier(counter));

//        ModifiedRawHitzone = (M.getRawHitzoneValue() * (SharpnessModifier_Atk * SNSSharpnessMod() *
//                GSChargeMod_Atk(counter) * Skills.getGroupDSharp())) / 100;
        HitzoneRaw = TrueRaw * getCalculatedRawHitzone(counter);

//        ModifiedElmHitzone = (M.getElmHitzoneValue() * (SharpnessModifier_Elm * SNSSharpnessMod() *
//                GSChargeMod_Elm(counter) * Skills.getGroupDSharp())) / 100;
//        HitzoneElm = Skills.getTrueElm(ElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
//                ModifiedElmHitzone * HitMultiplier(counter);

//        ModifiedSubElmHitzone = (M.getSubElmHitzoneValue() * (SharpnessModifier_Elm * Skills.getGroupDSharp())) / 100;
//        HitzoneSubElm = Skills.getTrueSubElm(SubElementalDamage * getWolfsMawModifier(), ui.SkillCheck) *
//                ModifiedSubElmHitzone;

        //Hitzone Modification - End

        if (!ui.ChosenArt.equals("-None-")) TrueAttack = HitzoneRaw + (getCalculatedElm(counter) * HA_ElementCheck[counter]);
        else{
            if(MV_Names[counter].equals("Kick")) TrueAttack = 2;
            else TrueAttack = HitzoneRaw + getCalculatedElm(counter) + getCalculatedSubElm();
        }

        //Bounce = !((getCalculatedRawHitzone(counter) * 100) < 25);

        MVs.add(TrueAttack);
    }


    //Private Calculations

    private int HitMultiplier(int counter){
        switch (Weapon){
            case "LS":
                if (MV_Names[counter].contains("2 hits") || ((MV_Names[counter].contains("Jump Spirit Slash")
                        || MV_Names[counter].equals("   -(With Spirit Energy)")) && !ui.SpiritGaugeColour.equals("No Colour")))
                    return 2;
                else if (MV_Names[counter].contains("3 hits")) return 3;
                return 1;
            default:
                return 1;
        }
    }

    private float getCalculatedRawHitzone(int counter){
        return ((M.getRawHitzoneValue() * (SharpnessModifier_Atk * SNSSharpnessMod() *
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
                case "Lions Maw":
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
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("Lance_HA_CorkscrewJab_MV_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Dragon Blast":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GL_HA_DragonBlast_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Blast Dash":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("GL_HA_BlastDash_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Trance Slash":
                    if(!ui.DemonRiotOffCheck.isChecked() && ui.TempestAxeCheck.isChecked()){//Demon Riot and Tempest Axe
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_All_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_All_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else if(ui.DemonRiotOffCheck.isChecked() && ui.TempestAxeCheck.isChecked()){//Tempest Axe
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_TA_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_TA_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else if(!ui.DemonRiotOffCheck.isChecked() && !ui.TempestAxeCheck.isChecked()){//Demon Riot
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_DR_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_DR_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    else{//Base
                        MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_MV", "array", context.getPackageName()));
                        HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                        HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_TranceSlash_MV_ElmCheck", "array", context.getPackageName()));
                    }
                    break;
                case "Energy Charge":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("SA_HA_EnergyCharge_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Energy Blade":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_EnergyBlade_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Ripper Shield":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_RipperShield_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("CB_HA_RipperShield_MV_ElmCheck", "array", context.getPackageName()));
                    break;
                case "Extract Hunter":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_ExtractHunter_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
                    HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName()));
                    break;
                case "Swarm":
                    MV = context.getResources().getIntArray(context.getResources().getIdentifier("IG_HA_Swarm_MV", "array", context.getPackageName()));
                    HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Names", "array", context.getPackageName()));
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


    //Public retrievers/usage

    public boolean getBounce(int counter){
        return !((getCalculatedRawHitzone(counter) * 100) < 25);
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

    public void setHitzone(){
        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
    }

    public void setHitzone_DB(){
        M.getHitzones(context, ChosenElement, ChosenSubElement, Skills, ui.WeaknessExploitCheck.isChecked());
    }

    public boolean CalculateSkills(){
        if(!Weapon.equals("Prowler")){
            if(Weapon.equals("GS")){
                Skills.setCentreBladeModifier(ui.CentreBladeBonusCheck.isChecked());
            }
            else if(Weapon.equals(("SNS"))){
                Skills.setAffinityOilModifier(ui.AirborneCheck.isChecked());
            }
            Skills.setRepeatOffenderModifier(ui.RepeatOffenderCheck.isChecked());
            Skills.setCritBoostModifier(ui.CriticalBoostCheck.isChecked());
            Skills.setPowercharmModifier(ui.PowercharmCheck.isChecked());
            Skills.setPowertalonModifier(ui.PowertalonCheck.isChecked());
            Skills.setFelyneBoosterModifier(ui.FelyneBoosterCheck.isChecked());
            Skills.setCrisisModifier(ui.CrisisCheck.isChecked());
            Skills.setFurorModifier(ui.FurorCheck.isChecked());
            Skills.setBludgeonerModifier(ui.BludgeonerCheck.isChecked());

            Skills.setElementAtkUp(ui.ElementalAtkUpCheck.isChecked());
            Skills.setElementCrit(ui.ElementalCritCheck.isChecked(), Affinity);

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
        if(ui.WolfsMawLevel1Check.isChecked()) return 1.2f;
        else if(ui.WolfsMawLevel2Check.isChecked()) return 1.25f;
        else if(ui.WolfsMawLevel3Check.isChecked()) return 1.3f;
        else if(ui.WolfsMawOffCheck.isChecked()) return 1;
        else return 1;
    }
}
