package com.mhx.marcus.mhgendamagecalc;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class DamageCalculation {
    //Classes
    public StatsValidation Stats;
    private MonsterCalculation M;
    private UI ui;
    private SkillsCalculation Skills = new SkillsCalculation();

    //Stat Variables
    private String Weapon, Style, ChosenElement, Monster, HitzoneGroup, Hitzone, Sharpness;
    private float RawDamage, ElementalDamage, Affinity;
    private Context context;
    private int MV_Array, MV_Names_Array, HA_Levels_Array, HA_ElementCheck_Array;
    private float SharpnessModifier_Atk, SharpnessModifier_Elm;
    private int[] MV, HA_ElementCheck;
    private String[] MV_Names, HA_Levels;
    private List<String> HA_List = Arrays.asList("Ground Slash","Lions Maw (Wide Slash)",
            "Brimstone Slash","Moon Breaker",

            "Plesioth","Rathian","Cephadrome","Lavasioth",
            "Gypceros","Gendrome","Iodrome","Giadrome","Velocidrome","Bulldrome","Gold Rathian",
            "Silver Rathalos","Rathalos","Rathian","Yian Kut Ku","Yian Garuga",
            "Deadeye Yian Garuga","Malfestio","Nightcloak Malfestio");


    public DamageCalculation(Context context, UI ui, String Weapon, String Style, String Sharpness, float RawDamage,
                             String ChosenElement, float ElementalDamage, float Affinity, String Monster, String HitzoneGroup, String Hitzone){



        //String Stripped = SelectedMonster.replaceAll("\\s","");



        Stats = new StatsValidation(RawDamage,ChosenElement,ElementalDamage,Affinity);
        this.ui = ui;
        this.context = context;
        this.RawDamage = RawDamage;
        this.ElementalDamage = ElementalDamage;
        this.ChosenElement = ChosenElement;
        this.Affinity = Affinity;
        this.Weapon = Weapon;
        this.Monster = Monster;
        /*this.HitzoneGroup = HitzoneGroup;
        this.Hitzone = Hitzone;
        this.Style = Style;
        this.Sharpness = Sharpness;*/
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
        MV_Array = context.getResources().getIdentifier(Weapon + "_" + Style, "array", context.getPackageName());
        switch(String.valueOf(ui.HunterArtSelect.getSelectedItem())){
            case "Brimstone Slash":
                HA_Levels_Array = context.getResources().getIdentifier("GS_HA_BrimstoneSlash_Levels", "array", context.getPackageName());
                HA_ElementCheck_Array = context.getResources().getIdentifier("GS_HA_BrimstoneSlash_ElmCheck", "array", context.getPackageName());
                break;
            default:
                HA_Levels_Array = context.getResources().getIdentifier("HA_Levels", "array", context.getPackageName());
                HA_ElementCheck_Array = context.getResources().getIdentifier("HA_ElementCheck", "array", context.getPackageName());
                break;
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

    public int Calculate(int counter){
        float TrueRaw;

        MV = context.getResources().getIntArray(MV_Array);
        MV_Names = context.getResources().getStringArray(MV_Names_Array);
        HA_Levels = context.getResources().getStringArray(HA_Levels_Array);
        HA_ElementCheck = context.getResources().getIntArray(HA_ElementCheck_Array);







        if(MotionCheck == 0){
            if(ui.AirborneCheck.isChecked() && ui.SkillCheck && (MV_Names[counter].contains("Jump")
                    || MV_Names[counter].contains("Aerial"))){
                Skills.setAirborneModifier(ui.AirborneCheck.isChecked());
            }
            else{
                Skills.setAirborneModifier(false);
            }
            TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * MV[counter];
        }
        else{
            TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) *
                    (MV[counter] * BrimstoneCounterModifier(counter));

        }

        float ModifiedRawHitzone = (M.getRawHitzoneValue() * (SharpnessModifier_Atk *
                GSChargeMod_Atk(counter) * Skills.getGroupDSharp())) / 100;


        float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

        float ModifiedElmHitzone = (M.getElmHitzoneValue() * (SharpnessModifier_Elm *
                GSChargeMod_Elm(counter) * Skills.getGroupDSharp())) / 100;
        float HitzoneElm = Skills.getTrueElm(ElementalDamage, ui.SkillCheck) * ModifiedElmHitzone;

        //Hitzone Modification - End

        float TrueAttack;
        if (!ui.ChosenArt.equals("-None-")){
            TrueAttack = HitzoneRaw + (HitzoneElm * HA_ElementCheck[counter]);
        }
        else{
            if(MV_Names[counter].equals("Kick")){
                TrueAttack = 2;
            }
            else{
                TrueAttack = HitzoneRaw + HitzoneElm;
            }
        }

        //textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));

        //textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
        //textviews[i].setVisibility(View.VISIBLE);
        if ((ModifiedRawHitzone * 100) < 25){
            textviews[i].setTextColor(Color.argb(255, 242, 16, 16));
            Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
            textviews[i].setTextColor(Color.BLACK);
        }

        //textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
        if(MotionCheck == 0){
            textviews[i].setText(MV_Names[i]);
            Banner.setText("Attacks");
        }
        else{
            if(ui.ChosenArt.equals("Brimstone Slash")){
                textviews[i].setText(HA_Levels[counter]);
            }
            else{
                textviews[i].setText(HA_Levels[i]);
            }
            Banner.setText(ui.ChosenArt);
        }
        //textviews[i].setVisibility(View.VISIBLE);

                if(!ui.ChosenMonster.equals("None")){
        StaggerBanner.setText("Stagger/Break Limit: " + M.getStaggerValue());
        StaggerBanner.setVisibility(View.VISIBLE);
    }
                Info.setVisibility(View.VISIBLE);

















        String.format("%s", Math.round(TrueAttack));
        return Math.round(TrueAttack);
    }

    private float BrimstoneCounterModifier(int counter){
        if(MotionCheck == 1 && HA_Levels[counter].contains("Counter") && ui.ChosenArt.equals("Brimstone Slash"))
            return 1.5f;
        else return 1f;
    }

    private int CalculateMonters(){
        return 1;
    }

    private float GSChargeMod_Atk(int counter){
        if(MV_Names[counter].contains("Slap"))
            M.alterHitzones(context, Monster, MV_Names[counter], "", false);
        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
        if(MotionCheck == 0){
            if (MV_Names[counter].equals("   - Lv. 1 Strong Charge") || MV_Names[counter].equals("Lv. 1 Strong Charge")) {
                return 1.1f;
            }
            else if (MV_Names[counter].equals("   - Lv. 2 Strong Charge") || MV_Names[counter].equals("Lv. 2 Strong Charge")) {
                return 1.2f;
            }
            else if (MV_Names[counter].equals("   - Lv. 3 Strong Charge") || MV_Names[counter].equals("Lv. 3 Strong Charge")) {
                return 1.3f;
            }
            else if (MV_Names[counter].equals("Lv. 1 Charge")) {
                return 1.1f;
            }
            else if (MV_Names[counter].equals("   -Follow Up ")) {
                return 1.1f;
            }
            else if (MV_Names[counter].equals("Lv. 2 Charge")) {
                return 1.2f;
            }
            else if (MV_Names[counter].equals("   -Follow Up  ")) {
                return 1.2f;
            }
            else if (MV_Names[counter].equals("Lv. 3 Charge")) {
                return 1.3f;
            }
            else if (MV_Names[counter].equals("   -Follow Up   ")) {
                return 1.3f;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 1 Charge")) {
                return 1.1f;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 2 Charge")) {
                return 1.2f;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 3 Charge")) {
                return 1.3f;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.1 Dash Charge")){
                return 1.1f;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.2 Dash Charge")){
                return 1.2f;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.3 Dash Charge")){
                return 1.3f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.0 (Valor State)") || MV_Names[counter].equals("Draw Slash (Valor State)")){
                return 1f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.1 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.1 (Valor State)")){
                return 1.1f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.2 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.2 (Valor State)")){
                return 1.2f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.3 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.3 (Valor State)")){
                return 1.3f;
            }
        }
        else {
            if ((HA_Levels[counter].equals("Level I") || HA_Levels[counter].equals("     -Strong Attack Counter Hit ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1.1f;
            }
            else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter].equals("     -Strong Attack Counter Hit  ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1.2f;
            }
            else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter].equals("     -Strong Attack Counter Hit   ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1.33f;
            }
        }
        return 1;
    }

    private float GSChargeMod_Elm(int counter){
        if(MV_Names[counter].contains("Slap"))
            M.alterHitzones(context, Monster, MV_Names[counter], "", false);
        M.getHitzones(context, ChosenElement, Skills, ui.WeaknessExploitCheck.isChecked());
        if(MotionCheck == 0){
            if (MV_Names[counter].equals("   - Lv. 1 Strong Charge") || MV_Names[counter].equals("Lv. 1 Strong Charge")) {
                return 1.8f;
            }
            else if (MV_Names[counter].equals("   - Lv. 2 Strong Charge") || MV_Names[counter].equals("Lv. 2 Strong Charge")) {
                return 2.25f;
            }
            else if (MV_Names[counter].equals("   - Lv. 3 Strong Charge") || MV_Names[counter].equals("Lv. 3 Strong Charge")) {
                return 3f;
            }
            else if (MV_Names[counter].equals("Lv. 1 Charge")) {
                return 1.2f;
            }
            else if (MV_Names[counter].equals("   -Follow Up ")) {
                return 1f;
            }
            else if (MV_Names[counter].equals("Lv. 2 Charge")) {
                return 1.5f;
            }
            else if (MV_Names[counter].equals("   -Follow Up  ")) {
                return 1f;
            }
            else if (MV_Names[counter].equals("Lv. 3 Charge")) {
                return 2f;
            }
            else if (MV_Names[counter].equals("   -Follow Up   ")) {
                return 1f;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 1 Charge")) {
                return 1;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 2 Charge")) {
                return 1;
            }
            else if (MV_Names[counter].equals("Aerial Lv. 3 Charge")) {
                return 1;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.1 Dash Charge")){
                return 1.1f;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.2 Dash Charge")){
                return 1.2f;
            }
            else if (MV_Names[counter].equals("Adept Evade Lv.3 Dash Charge")){
                return 2.25f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.0 (Valor State)") || MV_Names[counter].equals("Draw Slash (Valor State)")){
                return 1.5f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.1 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.1 (Valor State)")){
                return 1.65f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.2 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.2 (Valor State)")){
                return 1.8f;
            }
            else if (MV_Names[counter].equals("Blue Charge Lv.3 (Valor State)") || MV_Names[counter].equals("Draw Slash Lv.3 (Valor State)")){
                return 2.25f;
            }
        }
        else {
            if ((HA_Levels[counter].equals("Level I") || HA_Levels[counter].equals("     -Strong Attack Counter Hit ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1;
            }
            else if ((HA_Levels[counter].equals("Level II") || HA_Levels[counter].equals("     -Strong Attack Counter Hit  ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1;
            }
            else if ((HA_Levels[counter].equals("Level III") || HA_Levels[counter].equals("     -Strong Attack Counter Hit   ")) && ui.ChosenArt.equals("Brimstone Slash")) {
                return 1;
            }
        }
        return 1;
    }

    /*if(MotionCheck == 0){
        if(AirborneCheck.isChecked() && SkillCheck && (MV_NamesSword[i].contains("Aerial") ||
                MV_NamesSword[i].contains("Jump"))){
            Skills.setAirborneModifier(AirborneCheck.isChecked());
        }
        else{
            Skills.setAirborneModifier(false);
        }
        Skills.setShieldModifier(RedCheck,StrikerCheck,false,MV_NamesSword[i]);
        TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * MotionSword[i];
    }
        else{
        TrueRaw = Skills.getTrueRaw(RawDamage, Affinity, ui.SkillCheck) * ChosenHunterArt[i];
    }

    int HitMultiplier = 1;
        if (MV_NamesSword[i].contains("2 hits")) {
        HitMultiplier = 2;
    }

    //Hitzone Modification - Start

    float ModifiedRawHitzone = (M.getRawHitzoneValue() * SelectedSharpnessAtkModifier *
            SkillSharpnessModifier) / 100;
    float HitzoneRaw = TrueRaw * ModifiedRawHitzone;

    float ModifiedElmHitzone = (M.getElmHitzoneValue() * SelectedSharpnessElmModifier *
            SkillSharpnessModifier) / 100;
    float HitzoneElm = (Skills.getTrueElm(RawElement, SkillCheck) * ModifiedElmHitzone) * HitMultiplier;

    //Hitzone Modification - End

    float TrueAttack;
        if (!ChosenArt.equals("-None-"))
    TrueAttack = HitzoneRaw + (HitzoneElm * HunterArtElementCheck[i]);
        else TrueAttack = HitzoneRaw + HitzoneElm;

    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsAttacks[i], "id", getPackageName()));
                    *//*Sets the value of 'textviews' to the position of the item in the 'TextViewIDs'
                    array e.g. If the current selected item in the array was 'CBAttack_1',then
                    'Counter' would be 0 etc.*//*
    textviews[i].setText(String.format("%s", Math.round(TrueAttack)));
    textviews[i].setVisibility(View.VISIBLE);
        if ((ModifiedRawHitzone * 100) < 25){
        textviews[i].setTextColor(Color.argb(255, 242, 16, 16));
        Snackbar.make(view, "Attacks in red will bounce/receive increased sharpness reduction", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
        else textviews[i].setTextColor(Color.BLACK);

                    *//*Sets the current textview to the id value of the desired textview and then
                    sets that textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*//*

    textviews[i] = (TextView) findViewById(getResources().getIdentifier(TextViewIDsNames[i], "id", getPackageName()));
        if(MotionCheck == 0){
        textviews[i].setText(MV_NamesSword[i]);
        Banner.setText("Attacks");
    }
        else{
        textviews[i].setText(HunterArtsLevels[i]);
        Banner.setText(ChosenArt);
    }
    textviews[i].setVisibility(View.VISIBLE);
                    *//*Sets the current textview to the id value of 'Counter' and then sets that
                    textviews value the value of 'test's current value. It also sets the
                    visibility of all the used textboxes to 'visible'.*//*

    getForLoopCarry(i);
                    *//*Method (function) used to carry over the value of 'i' so it can be used
                    elsewhere.*/
}
