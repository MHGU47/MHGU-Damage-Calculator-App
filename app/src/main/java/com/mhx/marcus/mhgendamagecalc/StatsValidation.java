package com.mhx.marcus.mhgendamagecalc;

/**
 * Created by b7010863 on 07/02/2018.
 */

public class StatsValidation {

    private float PhysAtk;
    private float ElmAtk;
    private float ElmAtk_Sub;
    private float Affinity;

    private String Elm_Type;
    private String SubElm_Type;

    public StatsValidation(float Atk, String Type, float Elm, float Crit){
        PhysAtk = Atk;
        Elm_Type = Type;
        ElmAtk = Elm;
        Affinity = Crit;
    }//Constructor for Blademaster weapons and Bow

    public StatsValidation(float Atk, float Crit){
        PhysAtk = Atk;
        Affinity = Crit;
    }//Constructor for Bowguns

    public StatsValidation(float Atk, String Type, float Elm, String SubType, float ElmSub, float Crit){
        PhysAtk = Atk;
        Elm_Type = Type;
        ElmAtk = Elm;
        SubElm_Type = SubType;
        ElmAtk_Sub = ElmSub;
        Affinity = Crit;
    }//Constructor for Dual Blades

    public boolean isValid(){//Used to check whether the inputted values are valid.
        return isValidAtk() && isValidAffinity() && isValidElm();
    }

    public boolean isValid_DB(){//Used to check whether the inputted values are valid.
        return isValidAtk() && isValidAffinity() && isValidElm() && isValidSubElm();
    }

    public boolean isValid_BG(){
        return isValidAtk() && isValidAffinity();
    }

    public boolean isValidAtk(){//Used to check whether the inputted attack value is valid.
        return PhysAtk >= 0;
    }

    public boolean isValidElm(){//Used to check whether the element and element type inputted are valid.
        boolean Valid = true;
        if (Elm_Type.equals("NONE") && ElmAtk > 0){
            Valid = false;
        }
        else if (!Elm_Type.equals("NONE") && ElmAtk == 0){
            Valid = false;
        }
        return Valid;
    }

    public boolean isValidSubElm(){//Used to check whether the inputted sub element for DB is valid.
        boolean Valid = true;
        if (SubElm_Type.equals("NONE") && ElmAtk_Sub > 0){
            Valid = false;
        }
        else if (!SubElm_Type.equals("NONE") && ElmAtk_Sub == 0){
            Valid = false;
        }
        //return Valid;
        return (!SubElm_Type.equals("NONE") && ElmAtk_Sub > 0) || (SubElm_Type.equals("NONE") && ElmAtk_Sub == 0);
    }

    public boolean isValidAffinity(){
        return (Affinity % 5 == 0) && Affinity <= 100 && Affinity >= -100;
    }
}
