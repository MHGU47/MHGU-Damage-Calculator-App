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
    private String Weapon, Style, ChosenElement, ChosenSubElement, Monster, HitzoneGroup, Hitzone, Sharpness, HunterArt;
    private float RawDamage, ElementalDamage, SubElementalDamage, Affinity;
    private Context context;
    private int MV_Array, MV_Names_Array, MV_HA_Array, HA_Levels_Array, HA_ElementCheck_Array;
    private float SharpnessModifier_Atk, SharpnessModifier_Elm;
    private int[] MV, HA_MV, HA_ElementCheck;
    private String[] MV_Names, HA_Levels;
    private boolean DualElement = false, HA = false, Bounce = false;
    private List<String> HA_List = Arrays.asList("Ground Slash","Lions Maw (Wide Slash)",
            "Brimstone Slash","Moon Breaker",

            "Plesioth","Rathian","Cephadrome","Lavasioth",
            "Gypceros","Gendrome","Iodrome","Giadrome","Velocidrome","Bulldrome","Gold Rathian",
            "Silver Rathalos","Rathalos","Rathian","Yian Kut Ku","Yian Garuga",
            "Deadeye Yian Garuga","Malfestio","Nightcloak Malfestio");


    public DamageCalculation(Context context, UI ui, String Weapon, Boolean HA, String HunterArt,
                             String Style, String Sharpness, float RawDamage, String ChosenElement,
                             float ElementalDamage, float Affinity, String Monster, String HitzoneGroup,
                             String Hitzone){
        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);

        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.ElementalDamage = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.Affinity = Affinity;

        this.Weapon = Weapon;
        this.HunterArt = HunterArt;
        this.HA = !HA;

        this.Monster = Monster;

        M = new MonsterCalculation(context,
                Monster + "RawHitzones_Cut",
                Monster + "ElmHitzones_" + ChosenElement,
                Monster + "_StaggerLimits",
                HitzoneGroup + "Hitzones",
                Hitzone);

        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
        SharpnessModifier_Atk = context.getResources().getIdentifier(Sharpness + "_Raw","integer", context.getPackageName());
        SharpnessModifier_Elm = context.getResources().getIdentifier(Sharpness + "_Elm","integer", context.getPackageName());
        SharpnessModifier_Atk /= 100;
        SharpnessModifier_Elm /= 100;

        MV_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_MV", "array", context.getPackageName());
        MV_Names_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_Names", "array", context.getPackageName());

        MV = context.getResources().getIntArray(MV_Array);
        MV_Names = context.getResources().getStringArray(MV_Names_Array);

        setHA_MV();
        MVs.clear();
        MV_NamesList.clear();
    }//Standard Blademaster

    /**
     * @param context Weapon context
     * @param ui UI class instance
     * @param Weapon Selected weapon
     * @param Style Selected Style
     * @param Sharpness Selected Sharpness
     * @param RawDamage Inputted damage ONLY
     * @param ChosenElement Selected element
     * @param ElementalDamage Inputted elemental damage ONLY
     * @param ChosenSubElement Selected subelement
     * @param SubElementalDamage Inputted subelemental damage ONLY
     * @param Affinity Inputted affinity
     * @param Monster Selected monster
     * @param HitzoneGroup Hitzone group of selected monster
     * @param Hitzone Selected hitzone of the selected monster
     */
    public DamageCalculation(Context context, UI ui, String Weapon, Boolean HA, String HunterArt,
                             String Style, String Sharpness, float RawDamage, String ChosenElement,
                             float ElementalDamage, String ChosenSubElement, float SubElementalDamage,
                             float Affinity, String Monster, String HitzoneGroup, String Hitzone){
        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);
        this.ui = ui;
        this.context = context;

        this.RawDamage = RawDamage;
        this.ElementalDamage = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.SubElementalDamage = SubElementalDamage;
        this.ChosenSubElement = ChosenSubElement;
        this.Affinity = Affinity;

        this.Weapon = Weapon;
        this.HunterArt = HunterArt;
        this.HA = !HA;

        this.Monster = Monster;
        DualElement = SubElementalDamage > 0;

        M = new MonsterCalculation(context,
                Monster + "RawHitzones_Cut",
                Monster + "ElmHitzones_" + ChosenElement,
                Monster + "ElmHitzones_" + ChosenSubElement,
                Monster + "_StaggerLimits",
                HitzoneGroup + "Hitzones",
                Hitzone,
                "Dual Blades");

        M.getHitzones(context, ChosenElement, ChosenSubElement, Skills, ui.WeaknessExploitCheck.isChecked());
        SharpnessModifier_Atk = context.getResources().getIdentifier(Sharpness + "_Raw","integer", context.getPackageName());
        SharpnessModifier_Elm = context.getResources().getIdentifier(Sharpness + "_Elm","integer", context.getPackageName());
        SharpnessModifier_Atk /= 100;
        SharpnessModifier_Elm /= 100;

        MV_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_MV", "array", context.getPackageName());
        MV_Names_Array = context.getResources().getIdentifier(Weapon + "_" + Style + "_Names", "array", context.getPackageName());

        MV = context.getResources().getIntArray(MV_Array);
        MV_Names = context.getResources().getStringArray(MV_Names_Array);

        setHA_MV();
        MVs.clear();
        MV_NamesList.clear();
    }//Dual Blades

    public void Calculate(int counter){
        float TrueRaw, TrueAttack, HitzoneRaw, HitzoneElm, ModifiedRawHitzone, ModifiedElmHitzone;

        if (Weapon.equals("DB")) getDBElmMod(MV_Names[counter]);

        if(!HA){
            if(ui.AirborneCheck.isChecked() && ui.SkillCheck && (MV_Names[counter].contains("Jump")
                    || MV_Names[counter].contains("Aerial"))){
                Skills.setAirborneModifier(ui.AirborneCheck.isChecked());
            }
            else Skills.setAirborneModifier(false);
            TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * MV[counter];
        }
        else TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) *
                    (MV[counter] * BrimstoneCounterModifier(counter));

        ModifiedRawHitzone = (M.getRawHitzoneValue() * (SharpnessModifier_Atk *
                GSChargeMod_Atk(counter) * Skills.getGroupDSharp())) / 100;
        HitzoneRaw = TrueRaw * ModifiedRawHitzone;

        ModifiedElmHitzone = (M.getElmHitzoneValue() * (SharpnessModifier_Elm *
                GSChargeMod_Elm(counter) * Skills.getGroupDSharp())) / 100;
        HitzoneElm = Skills.getTrueElm(ElementalDamage, ui.SkillCheck) * ModifiedElmHitzone;

        //Hitzone Modification - End

        if (!HunterArt.equals("-None-")) TrueAttack = HitzoneRaw + (HitzoneElm * HA_ElementCheck[counter]);
        else{
            if(MV_Names[counter].equals("Kick")) TrueAttack = 2;
            else TrueAttack = HitzoneRaw + HitzoneElm;
        }

        Bounce = !((ModifiedRawHitzone * 100) < 25);

        MVs.add(TrueAttack);

        if(!HA) MV_NamesList.add(MV_Names[counter]);
        else MV_NamesList.add(HA_Levels[counter]);
    }

    public boolean getBounce(){
        return Bounce;
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

    private void setHA_MV(){
        if(!HunterArt.equals("-None-")) {
            switch (HunterArt) {
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
                    //TODO 14/05/2019: Add the rest of the hunter arts for other weapons
                default:
                    //HA_MV = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_MV", "array", context.getPackageName()));
                    //HA_Levels = context.getResources().getStringArray(context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName()));
                    //HA_ElementCheck = context.getResources().getIntArray(context.getResources().getIdentifier("GS_HA_MoonBreaker_ElmCheck", "array", context.getPackageName()));
                    break;
            }
        }
        else{
            HA_MV = context.getResources().getIntArray(context.getResources().getIdentifier("HA_MV", "array", context.getPackageName()));
            HA_Levels_Array = context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName());
            HA_ElementCheck_Array = context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName());
        }
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

    private float BrimstoneCounterModifier(int counter){
        if(Weapon.equals("GS")) {
            if (HA && HA_Levels[counter].contains("Counter") && HunterArt.equals("Brimstone Slash"))
                return 1.5f;
            else return 1f;
        }
        else return 1f;
    }

    private float GSChargeMod_Atk(int counter){
        if(Weapon.equals("GS")) {
            if (MV_Names[counter].contains("Slap"))
                M.alterHitzones(context, Monster, MV_Names[counter], "", false);
            if (!HA) {
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
                        .equals("     -Strong Attack Counter Hit ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1.1f;
                } else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter]
                        .equals("     -Strong Attack Counter Hit  ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1.2f;
                } else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter]
                        .equals("     -Strong Attack Counter Hit   ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1.33f;
                }
            }
        }
        return 1;
    }

    private float GSChargeMod_Elm(int counter){
        if(Weapon.equals("GS")) {
            if (MV_Names[counter].contains("Slap"))
                M.alterHitzones(context, Monster, MV_Names[counter], "", false);
            M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
            if (!HA) {
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
                if ((HA_Levels[counter].equals("Level I") || HA_Levels[counter].equals("     -Strong Attack Counter Hit ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1;
                } else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter].equals("     -Strong Attack Counter Hit  ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1;
                } else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter].equals("     -Strong Attack Counter Hit   ")) && HunterArt.equals("Brimstone Slash")) {
                    return 1;
                }
            }
        }
        return 1;
    }

    private void getDBElmMod(String MoveName){

        if ((DualElement) && (!HA)){
            if (MoveName.equals("Draw/Dash Attack (6 hits)")) {
                ElementalDamage *= 2.7f;
                SubElementalDamage *= 2.7f;
            }
            else if (MoveName.equals("Demon Dodge Jump Attack (4 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("     -Follow Up (6 hits)")) {
                ElementalDamage *= 2.1f;
                SubElementalDamage *= 2.1f;
            }
            else if (MoveName.equals("Upward Slash")) {
                ElementalDamage *= 0;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Demon Combo (6 hits)")) {
                ElementalDamage *= 2.1f;
                SubElementalDamage *= 2.1f;
            }
            else if (MoveName.equals("Demon Flurry Rush (6 hits)")) {
                ElementalDamage *= 2.1f;
                SubElementalDamage *= 2.1f;
            }
            else if (MoveName.equals("     -Off Ledge (4 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Left Jumping Slash (3 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("     -Second Jump[L] (3 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Right Jumping Slash (3 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("     -Second Jump[R] (3 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Jump Attack (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Vault Attack (4 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Devil's Dance (12 hits)")) {
                ElementalDamage *= 5.7f;
                SubElementalDamage *= 5.7f;
            }
            else if (MoveName.equals("Adept Evade (4 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Idle Slash (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Reverse Slash (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Circle Slash (3 hits)")) {
                ElementalDamage *= 1.7f;
                SubElementalDamage *= 0.7f;
            }
            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
                ElementalDamage *= 0;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 0;
            }
            else if (MoveName.equals("Demon Flurry (7 hits)")) {
                ElementalDamage *= 3.7f;
                SubElementalDamage *= 2.7f;
            }

            //Normal Mode If Statment

            if (MoveName.equals("Draw Attack (4 hits)")) {
                ElementalDamage *= 1.4f;
                SubElementalDamage *= 1.4f;
            }
            else if (MoveName.equals("Dash Attack (4 hits)")) {
                ElementalDamage *= 1.4f;
                SubElementalDamage *= 1.4f;
            }
            else if (MoveName.equals("Upward Slash")) {
                ElementalDamage *= 0;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Idle Slash (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Reverse Slash (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
                ElementalDamage *= 0;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Jumping Slash - Left (3 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 2;
            }
            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 0;
            }
            else if (MoveName.equals("Jumping Slash - Right (3 hits)")) {
                ElementalDamage *= 2;
                SubElementalDamage *= 1;
            }
            else if (MoveName.equals("Mid-Air Attack (2 hits)")) {
                ElementalDamage *= 1;
                SubElementalDamage *= 1;
            }
        }
        else if ((!DualElement) && (!HA)){
            SubElementalDamage = 0;
            if (MoveName.equals("Draw/Dash Attack (6 hits)")) {
                ElementalDamage *= 5.4f;
            }
            else if (MoveName.equals("Demon Dodge Jump Attack (4 hits)")) {
                ElementalDamage *= 4;
            }
            else if (MoveName.equals("     -Follow Up (6 hits)")) {
                ElementalDamage *= 4.2f;
            }
            else if (MoveName.equals("Upward Slash")) {
                ElementalDamage *= 1;
            }
            else if (MoveName.equals("Demon Combo (6 hits)")) {
                ElementalDamage *= 4.2f;
            }
            else if (MoveName.equals("Demon Flurry Rush (6 hits)")) {
                ElementalDamage *= 4.2f;
            }
            else if (MoveName.equals("     -Off Ledge (4 hits)")) {
                ElementalDamage *= 4;
            }
            else if (MoveName.equals("Left Jumping Slash (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("     -Second Jump[L] (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("Right Jumping Slash (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("     -Second Jump[R] (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("Jump Attack (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Vault Attack (4 hits)")) {
                ElementalDamage *= 4;
            }
            else if (MoveName.equals("Devil's Dance (12 hits)")) {
                ElementalDamage *= 11.4f;
            }
            else if (MoveName.equals("Adept Evade (4 hits)")) {
                ElementalDamage *= 4;
            }
            else if (MoveName.equals("Idle Slash (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Reverse Slash (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Circle Slash (3 hits)")) {
                ElementalDamage *= 2.4f;
            }
            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Demon Flurry (7 hits)")) {
                ElementalDamage *= 6.4f;
            }

            //Normal Mode If Statment

            if (MoveName.equals("Draw Attack (4 hits)")) {
                ElementalDamage *= 2.8f;
            }
            else if (MoveName.equals("Dash Attack (4 hits)")) {
                ElementalDamage *= 2.8f;
            }
            else if (MoveName.equals("Upward Slash")) {
                ElementalDamage *= 1;
            }
            else if (MoveName.equals("Idle Slash (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Horizontal Slash - Left (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Jumping Slash - Left (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("Horizontal Slash - Right (2 hits)")) {
                ElementalDamage *= 2;
            }
            else if (MoveName.equals("Jumping Slash - Right (3 hits)")) {
                ElementalDamage *= 3;
            }
            else if (MoveName.equals("Mid-Air Attack (2 hits)")) {
                ElementalDamage *= 2;
            }
        }
        else if (HA){
            if(DualElement) {
                ElementalDamage /= 2;
                SubElementalDamage /= 2;
            }
            else{
                ElementalDamage *= 1;
                SubElementalDamage *= 0;
            }
        }
    }
}
