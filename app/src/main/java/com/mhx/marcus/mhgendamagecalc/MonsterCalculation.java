package com.mhx.marcus.mhgendamagecalc;

import android.content.Context;
import android.support.design.widget.Snackbar;

import java.nio.channels.SelectionKey;
import java.nio.file.StandardWatchEventKinds;

/**
 * Created by b7010863 on 05/03/2018.
 */

public class MonsterCalculation {
    private int RawHitzoneSelector, ExtraRawHitzoneSelector, ElmHitzoneSelector, SubElmHitzoneSelector,
            SelectedGroup, StaggerHitzoneSelector, ExtractSelector;

    private String Hitzone;
    private int SelectedRawHitzoneValue = 0;
    private int SelectedElmHitzoneValue = 0;
    private int SelectedSubElmHitzoneValue = 0;
    private int SelectedStaggerValue = 0;
    private int SelectedExtract = 0;


    public MonsterCalculation(Context context, String PhysicalHitzone, String ElementHitzone,
                                        String Stagger, String HitzoneGroup, String Hitzone){
        RawHitzoneSelector = context.getResources().getIdentifier(PhysicalHitzone, "array", context.getPackageName());
        ElmHitzoneSelector = context.getResources().getIdentifier(ElementHitzone, "array", context.getPackageName());
        StaggerHitzoneSelector = context.getResources().getIdentifier(Stagger, "array", context.getPackageName());
        SelectedGroup = context.getResources().getIdentifier(HitzoneGroup, "array", context.getPackageName());

        this.Hitzone = Hitzone;
    }//Blademaster and Bow

    public MonsterCalculation(Context context, String PhysicalHitzone, String ElementHitzone,
                              String ExtraInfo, String Stagger, String HitzoneGroup,
                              String Hitzone, String Weapon){
        RawHitzoneSelector = context.getResources().getIdentifier(PhysicalHitzone, "array", context.getPackageName());
        ElmHitzoneSelector = context.getResources().getIdentifier(ElementHitzone, "array", context.getPackageName());
        if(Weapon.equals("Dual Blades"))
            SubElmHitzoneSelector = context.getResources().getIdentifier(ExtraInfo, "array", context.getPackageName());
        else
            ExtraRawHitzoneSelector = context.getResources().getIdentifier(ExtraInfo, "array", context.getPackageName());
        StaggerHitzoneSelector = context.getResources().getIdentifier(Stagger, "array", context.getPackageName());
        SelectedGroup = context.getResources().getIdentifier(HitzoneGroup, "array", context.getPackageName());
        this.Hitzone = Hitzone;
    }//Dual Blades and Lance

    public MonsterCalculation(Context context, String PhysicalHitzone, String ElementHitzone,
                              String Stagger, String Extract, String HitzoneGroup, String Hitzone){
        RawHitzoneSelector = context.getResources().getIdentifier(PhysicalHitzone, "array", context.getPackageName());
        ElmHitzoneSelector = context.getResources().getIdentifier(ElementHitzone, "array", context.getPackageName());
        StaggerHitzoneSelector = context.getResources().getIdentifier(Stagger, "array", context.getPackageName());
        SelectedGroup = context.getResources().getIdentifier(HitzoneGroup, "array", context.getPackageName());
        ExtractSelector = context.getResources().getIdentifier(Extract, "array", context.getPackageName());

        this.Hitzone = Hitzone;
    }//Insect Glaive

    public MonsterCalculation(Context context, String ChosenMonster, String Stagger,
                              String HitzoneGroup, String Hitzone){
        RawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Shot", "array", context.getPackageName());
        ExtraRawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Impact", "array", context.getPackageName());
        StaggerHitzoneSelector = context.getResources().getIdentifier(Stagger, "array", context.getPackageName());
        SelectedGroup = context.getResources().getIdentifier(HitzoneGroup, "array", context.getPackageName());

        this.Hitzone = Hitzone;
    }//Bowguns

    public void alterHitzones(Context context, String ChosenMonster, String Move, String Weapon, Boolean BladeWireCheck){
        if (Move.contains("KO") || Move.contains("Slap") || Move.contains("Heavy"))
            RawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Impact", "array", context.getPackageName());
        else{
            if(Weapon.equals("Bow") && !BladeWireCheck)
                RawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Shot", "array", context.getPackageName());
            else
                RawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Cut", "array", context.getPackageName());
        }
    }//For Sns, GS, HH, Lance and Bow use only

    public void alterHitzones(Context context, String ChosenMonster){
        RawHitzoneSelector = context.getResources().getIdentifier(ChosenMonster + "RawHitzones_Cut", "array", context.getPackageName());
    }//For Bowgun use only

    public void setElmHitzoneValue(Context context, String ElementHitzone){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);
        int[] ElmHitzoneValues = context.getResources().getIntArray(context.getResources().getIdentifier(ElementHitzone,
                "array", context.getPackageName()));

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)){
            ++i;
        }

        SelectedElmHitzoneValue = ElmHitzoneValues[i];
    }//For Bowgun use only

    public void setElmHitzoneValue_GL(Context context, String ElementHitzone){
        ElmHitzoneSelector = context.getResources().getIdentifier(ElementHitzone, "array", context.getPackageName());
    }//For GL use only



    public void getHitzones(Context context, String ChosenElement, SkillsCalculation Skills, boolean WE){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] ElmHitzoneValues = {};
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);
        if(!ChosenElement.equals("NONE"))
            ElmHitzoneValues = context.getResources().getIntArray(ElmHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        SelectedRawHitzoneValue = RawHitzoneValues[i];
        SelectedStaggerValue = StaggerValues[i];
        if(ChosenElement.equals("NONE")) SelectedElmHitzoneValue = 0;
        else SelectedElmHitzoneValue = ElmHitzoneValues[i];
        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.

        //if(SelectedRawHitzoneValue >= 45) Skills.setWeaknessExploitModifier(WE);
        //else Skills.setWeaknessExploitModifier(false);

        Skills.setWeaknessExploitModifier(WE && SelectedRawHitzoneValue >= 45);
    }//Blademaster and Bow

    public void getHitzones(Context context, String ChosenElement, String ChosenSubElement, SkillsCalculation Skills, boolean WE){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] ElmHitzoneValues = {};
        int[] SubElmHitzoneValues = {};
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);
        if(!ChosenElement.equals("NONE"))
            ElmHitzoneValues = context.getResources().getIntArray(ElmHitzoneSelector);

        if(!ChosenSubElement.equals("NONE"))
            SubElmHitzoneValues = context.getResources().getIntArray(SubElmHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        SelectedRawHitzoneValue = RawHitzoneValues[i];
        SelectedStaggerValue = StaggerValues[i];
        if(ChosenElement.equals("NONE")) SelectedElmHitzoneValue = 0;
        else{
            SelectedElmHitzoneValue = ElmHitzoneValues[i];
            if(ChosenSubElement.equals("NONE")) SelectedSubElmHitzoneValue = 0;
            else SelectedSubElmHitzoneValue = SubElmHitzoneValues[i];
        }

        if(SelectedRawHitzoneValue >= 45) Skills.setWeaknessExploitModifier(WE);

        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.

    }// Dual Blades

    public void getHitzones(Context context, SkillsCalculation Skills, boolean WE){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        SelectedRawHitzoneValue = RawHitzoneValues[i];
        SelectedStaggerValue = StaggerValues[i];
        if(SelectedRawHitzoneValue >= 45) Skills.setWeaknessExploitModifier(WE);
        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.
    }//Bowguns

    public void getHitzones_IG(Context context, String ChosenElement, SkillsCalculation Skills, boolean WE){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] ElmHitzoneValues = {};
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);
        int[] ExtractValues = context.getResources().getIntArray(ExtractSelector);
        if(!ChosenElement.equals("NONE"))
            ElmHitzoneValues = context.getResources().getIntArray(ElmHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        SelectedRawHitzoneValue = RawHitzoneValues[i];
        SelectedStaggerValue = StaggerValues[i];
        SelectedExtract = ExtractValues[i];
        if(ChosenElement.equals("NONE")) SelectedElmHitzoneValue = 0;
        else SelectedElmHitzoneValue = ElmHitzoneValues[i];
        if(SelectedRawHitzoneValue >= 45) Skills.setWeaknessExploitModifier(WE);

        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.
    }

    public void getHitzones_Lance(Context context, String ChosenElement, SkillsCalculation Skills, boolean WE){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] ExtraRawHitzoneValues = context.getResources().getIntArray(ExtraRawHitzoneSelector);
        int[] ElmHitzoneValues = {};
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);
        if(!ChosenElement.equals("NONE"))
            ElmHitzoneValues = context.getResources().getIntArray(ElmHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        if(RawHitzoneValues[i] > ExtraRawHitzoneValues[i])
            SelectedRawHitzoneValue = RawHitzoneValues[i];
        else SelectedRawHitzoneValue = ExtraRawHitzoneValues[i];

        SelectedStaggerValue = StaggerValues[i];
        if(ChosenElement.equals("NONE")) SelectedElmHitzoneValue = 0;
        else SelectedElmHitzoneValue = ElmHitzoneValues[i];
        if(SelectedRawHitzoneValue >= 45) Skills.setWeaknessExploitModifier(WE);

        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.

    }

    public void getHitzones_Stinger(Context context, SkillsCalculation Skills){
        CharSequence[] SelectedGroupText = context.getResources().getTextArray(SelectedGroup);

        int[] RawHitzoneValues = context.getResources().getIntArray(RawHitzoneSelector);
        int[] ExtraRawHitzoneValues = context.getResources().getIntArray(ExtraRawHitzoneSelector);
        int[] StaggerValues = context.getResources().getIntArray(StaggerHitzoneSelector);

        // Selected___Hitzone is used to hold the value of the selected hitzone for element
        // and physical damage.

        int i = 0;
        while(!SelectedGroupText[i].equals(Hitzone)) ++i;
        if(ExtraRawHitzoneValues[i] >= 45){
            SelectedRawHitzoneValue = ExtraRawHitzoneValues[i];
            Skills.setStingerModifier(true);
        }
        else{
            SelectedRawHitzoneValue = RawHitzoneValues[i];
            Skills.setStingerModifier(false);
        }
        SelectedStaggerValue = StaggerValues[i];
        // This while loop increments the 'i' variable until the condition is false. When
        // the loop is broken, it then uses the value of 'i' to pick the correct hitzone values
        // for the corresponding 'Selected___Value'. This works due to the hitzones and
        // their corresponding values in their xml files are all listed in specific orders
        // to make sure they match up properly.
    }



    public int getRawHitzoneValue(){
        return SelectedRawHitzoneValue;
    }

    public int getElmHitzoneValue(){
        return SelectedElmHitzoneValue;
    }

    public int getSubElmHitzoneValue(){
        return SelectedSubElmHitzoneValue;
    }

    public int getStaggerValue(){
        return SelectedStaggerValue;
    }

    public String getExtract(){
        if (SelectedExtract == 1) return "Extract: Red";
        else if (SelectedExtract == 2) return "Extract: Orange";
        else if (SelectedExtract == 3) return "Extract: White";
        else if (SelectedExtract == 4) return "Extract: Green";
        else return "";
    }
}