package com.mhx.marcus.mhgendamagecalc;

/**
 * Created by b7010863 on 07/02/2018.
 */

public class SkillsCalculation {

    private float ElmAffinity;
    private boolean ElmAffCheck;

    private float additiveRaw, multiplicativeRaw, additiveElm, multiplicativeElm, additiveElm_Sub, multiplicativeElm_Sub;

    //TODO: *NOTE* Weapon Specific
    private int AffinityOilModifier = 0;
    private float ChaosOilModifier = 0;
    private float CentreBladeModifier = 1;
    private boolean OilSynergyState = false;
    private int OilSynergyLevel = 0;
    private float LionsMawModifier = 1;
    private float MaxSpiritGaugeModifier = 1;
    private float SpiritGaugeLevelModifier = 1;
    private float SacrificialBladeModifier = 1;
    private float RedShieldModifier = 1;
    private float CBPhialModifier = 1;
    private float EnragedGuardModifier = 1;
    private float HeatGaugeModifier = 1;
    private float SAAtkPhialModifier = 1;
    private float SAElmPhialModifier = 1;
    private String SAPhialType;
    private float IGExtractModifier = 1;
    private int StingerModifier = 0;
    private float RapidFireModifier = 1;
    private float AtkCoatingModifier = 1;
    private float ElmCoatingModifier = 1;
    private int EvasiveManeuversModifier = 0;

    //TODO: *NOTE* Universal
    private float CritBoostModifier = 0;
    private float RepeatOffenderModifier = 0;
    private float TotalSkillCritModifier = 0;
    private float PowertalonModifier = 0;
    private float PowercharmModifier = 0;
    private float FelyneBoosterModifier = 0;
    private float CrisisModifier = 0;
    private float FurorModifier = 0;
    private float BludgeonerModifier = 0;
    private float BludgeonerTemp = 0;
    private float AirborneModifier = 1;
    private float ElementAtkUp = 0;
    private float ArtilleryModifier = 1;
    private float FelyneBombardier = 1;
    private float WeaknessExploitModifier = 0;
    private float GunpowderInfusionModifier = 1;
    private float ShotModifier = 1;
    private float ReloadModifier = 1;
    private float FelyneSharpshooterModifer = 1;
    private float FelyneTemperModifier = 1;

    private float GroupC_1 = 0;
    //Demondrug (+5), Mega Demondrug (+7)

    private float GroupC_2 = 0;
    //Attack Up S - Meal (+3),Attack Up M - Meal (+5), Attack Up L - Meal (+7)

    private float GroupD = 0;
    //Might Seed (+10), Might Pill (+25), Exciteshroom - Mycology (+10), Demon Horn (+10)
    //Demon S (+10), Demon Affinity S (+15), Cool Cat (+15)

    private float GroupDSharp = 1;

    private float GroupF = 0;
    //Attack Up S (+10), Attack Up M (+15), Attack Up L (+20)

    private float GroupG = 1;
    //Adrenaline +2 (+30%), Felyne Heroics (+35%)

    private float GroupH = 1;
    //Attack Up S - Hunting Horn (+10%), Attack Up S - Hunting Horn (Recital) (+15%)
    //Attack Up L - Hunting Horn (+15%), Attack Up L - Hunting Horn (Recital) (+20%)

    private float GroupI = 1;
    //Fortify - First Cart (+10%), Fortify - Second Cart (+20%)

    private float GroupJ_1 = 1;
    //Challenger +1 (+10%), Challenger +2 (+20%)

    private float GroupJ_2 = 0;
    //Unscathed (+20), Latent Power +1, Latent Power +2

    private float GroupK = 1;
    //Cold Blooded - Cold Area (+15), Cold Blooded - Cold Drink (+5)
    //Cold Blooded - Cold Area + Cold Drink (+20), Hot Blooded - Hot Area (+15)

    private float GroupPAdd = 0, GroupPAddSub = 0;
    //Element Atk Up +1, Element Atk Up +2

    private float GroupPMulti = 1, GroupPMultiSub = 1;
    //Element Atk Up +1, Element Atk Up +2

    private float GroupDCrit = 1;
    //Demon Affinity S (+15)

    private float GroupJCrit = 1;
    //Challenger +1 (+10%), Challenger +2 (+20%), Latent Power +1, Latent Power +2

    private float GroupO = 1;
    //Critical Eye +1, Critical Eye +2, Critical Eye +3

    public SkillsCalculation() {

    }//TODO:Empty constructor

    //TODO: Consider adding all the calculations into a single method

    private void SkillCheck(boolean Check){
        if(!Check){
            additiveRaw = 0;
            multiplicativeRaw = 1;
            additiveElm = 0;
            multiplicativeElm = 1;

            AirborneModifier = 1;
        }
        else{
            additiveRaw = GroupC_1 + GroupC_2 + GroupD + GroupF + GroupJ_2 + GroupK  +
                    PowercharmModifier + PowertalonModifier + FelyneBoosterModifier +
                    CrisisModifier + FurorModifier + BludgeonerModifier;
            multiplicativeRaw = GroupG * GroupH * GroupI * GroupJ_1 * AirborneModifier;

            additiveElm = GroupPAdd;
            multiplicativeElm = GroupPMulti * ElementAtkUp;

            additiveElm_Sub = GroupPAddSub;
            multiplicativeElm_Sub = GroupPMultiSub * ElementAtkUp;
        }
    }
    /*Checks whether the 'Skills' switch has be switched. If the parameter is false then all the
      values for the skills will be set to their defaults. This can only be called inside this class.*/

    //TODO: *NOTE* Physical Damage related methods

    public float getTrueRaw(float WpnAtk, float Affinity, boolean Check){
        SkillCheck(Check);
        return getCalculatedRawAtk(WpnAtk) * getCalculatedAffinity(Affinity, Check);
    }
    /*Calculates the true raw damage by combining the total raw damage with skills and the total
      affinity with skills. It does this by calling 'getCalculatedRawAtk', 'getCalculatedAffinity'
      and 'SkillCheck' which all have values passed to them through this method. The previously
      mentioned methods are also private, so they can only be accessed within this class.*/

    private float getCalculatedRawAtk(float RawAtk) {
        return (RawAtk + additiveRaw) * (getWeaponSpecificModifiers() * multiplicativeRaw);
    }
    //Calculates the total raw damage when all skills are factored in.

    public float getTrueElm(float Elm, boolean Check){
        SkillCheck(Check);
        return getCalculatedElement(Elm) * ElmAffinity;
    }

    float getTrueSubElm(float SubElm, boolean Check){
        SkillCheck(Check);
        return getCalculatedSubElement(SubElm) * ElmAffinity;
    }

    //Universal - Raw

    public void setGroupC_1(float C_1) {
        GroupC_1 = C_1;
    }

    public void setGroupC_2(float C_2) {
        GroupC_2 = C_2;
    }

    public void setGroupD(float D) {
        GroupD = D;
    }

    void setGroupDSharp(float D){
        GroupDSharp = D;
    }

    float getGroupDSharp(){
        return GroupDSharp;
    }

    public void setGroupF(float F) {
        GroupF = F;
    }

    public void setGroupG(float G) {
        GroupG = G;
    }

    public void setGroupH(float H) {
        GroupH = H;
    }

    public void setGroupI(float I) {
        GroupI = I;
    }

    public void setGroupJ_1(float J1) {
        GroupJ_1 = J1;
    }

    public void setGroupJ_2(float J2) {
        GroupJ_2 = J2;
    }

    public void setGroupK(float J) {
        GroupK = J;
    }

    void setArtilleryModifier(float A){
        ArtilleryModifier = A;
    }

    boolean ArtilleryCheck(){
        return ArtilleryModifier > 1;
    }//For Bowgun use

    float getArtilleryModifier(){
        return ArtilleryModifier;
    }

    public void setPowertalonModifier(boolean Check) {
        if(Check){
            PowertalonModifier = 9f;
        }
        else{
            PowertalonModifier = 0;
        }
    } //Was a float method

    public void setPowercharmModifier(boolean Check) {
        if(Check){
            PowercharmModifier = 6f;
        }
        else{
            PowercharmModifier = 0;
        }
    } //Was a float method

    public void setFelyneBoosterModifier(boolean Check) {
        if(Check){
            FelyneBoosterModifier = 3f;
        }
        else{
            FelyneBoosterModifier = 0;
        }
    } //Was a float method

    public void setCrisisModifier(boolean Check) {
        if(Check){
            CrisisModifier = 20f;
        }
        else{
            CrisisModifier = 0;
        }
    } //Was a float method

    public void setFurorModifier(boolean Check) {
        if(Check){
            FurorModifier = 20f;
        }
        else{
            FurorModifier = 0f;
        }
    } //Was a float method

    public void setAirborneModifier(boolean Check){
        if(Check){
            AirborneModifier = 1.1f;
        }
        else{
            AirborneModifier = 1;
        }
    }

    public void getBludgeonerModifier(float B) {
        BludgeonerModifier = B;
        BludgeonerTemp = B;
    }

    public void setBludgeonerModifier(boolean Check) {
        if(!Check){
            BludgeonerModifier = 0;
        }
        else{
            BludgeonerModifier = BludgeonerTemp;
        }
    } //Was a float method
    
    //Weapon Specific

    private float getWeaponSpecificModifiers(){
        return CentreBladeModifier * LionsMawModifier * MaxSpiritGaugeModifier *
                SpiritGaugeLevelModifier * SacrificialBladeModifier * RedShieldModifier *
                EnragedGuardModifier * IGExtractModifier * GunpowderInfusionModifier * ShotModifier *
                ReloadModifier * FelyneTemperModifier * FelyneSharpshooterModifer * RapidFireModifier *
                AtkCoatingModifier;
    }

    void setCentreBladeModifier(boolean Check){
        if(Check){
            CentreBladeModifier = 1.05f;
        }
        else{
            CentreBladeModifier = 1f;
        }
    }

    void setLionsMawModifier(boolean Check, int Level){
        if(Check){
            if(Level == 1){
                LionsMawModifier = 1.1f;
            }
            else if(Level == 2){
                LionsMawModifier = 1.2f;
            }
            else{
                LionsMawModifier = 1.33f;
            }
        }
        else{
            LionsMawModifier = 1f;
        }
    }

    void setMaxSpiritGaugeModifier(boolean Check){
        if(Check){
            MaxSpiritGaugeModifier = 1.13f;
        }
        else{
            MaxSpiritGaugeModifier = 1f;
        }
    }

    void setSpiritGaugeLevelModifier(String Colour){
        switch (Colour){
            case "-White-":
                SpiritGaugeLevelModifier = 1.05f;
                break;
            case "-Yellow-":
                SpiritGaugeLevelModifier = 1.1f;
                break;
            case "-Red-":
                SpiritGaugeLevelModifier = 1.2f;
                break;
            case "-Blue (Valor)-":
                SpiritGaugeLevelModifier = 1.18f;
                break;
            default:
                SpiritGaugeLevelModifier = 1f;
                break;
        }
    }

    void setSacrificialBladeModifier(boolean Check, int Level){
        if(Check){
            if(Level == 1){
                SacrificialBladeModifier = 1.1f;
            }
            else if(Level == 2){
                SacrificialBladeModifier = 1.2f;
            }
            else{
                SacrificialBladeModifier = 1.3f;
            }
        }
        else{
            SacrificialBladeModifier = 1f;
        }
    }

    /*void setShieldStatus(){

    }*/

    void setShieldModifier(boolean isRed, boolean isStriker, boolean isAxe, String MorphTo_){
        if((!isAxe && !MorphTo_.equals("Morph to Axe")) || MorphTo_.equals("Morph to Sword")){
           RedShieldModifier = 1f;
        }
        else{
            if(isRed && isStriker){
                RedShieldModifier = 1.2f;
            }else if(isRed){
                RedShieldModifier = 1.15f;
            }else{
                RedShieldModifier = 1f;
            }
        }

    }

    void setCBPhialModifier(boolean isCharged){
        if(isCharged){
            CBPhialModifier = 1.35f;
        }
        else{
            CBPhialModifier = 1;
        }
    }

    float getCBPhialAtk(boolean isImpact, float RawAtk, float Element){
        if(isImpact){
            if((FelyneBombardier * ArtilleryModifier) > 1.4){
                return ((((RawAtk + additiveRaw) * multiplicativeRaw) * 1.4f) * CBPhialModifier);
            }
            return ((((RawAtk + additiveRaw) * multiplicativeRaw) * (FelyneBombardier * ArtilleryModifier)) * CBPhialModifier);
        }
        else{
            return (Element + additiveElm) * multiplicativeElm * CBPhialModifier;
        }
    }

    float getCBPhialModifier(){
        return CBPhialModifier;
    }

    void setFelyneBombardierModifier(boolean Check){
        if(Check){
            FelyneBombardier = 1.1f;
        }
        else{
            FelyneBombardier = 1;
        }
    }

    float getFelyneBombardierModifier(){
        return FelyneBombardier;
    }

    void setEnragedGuardModifier(boolean Check, int Level){
        if(Check){
            if(Level == 1){
                EnragedGuardModifier = 1.1f;
            }
            else if(Level == 2){
                EnragedGuardModifier = 1.2f;
            }
            else{
                EnragedGuardModifier = 1.3f;
            }
        }
        else{
            EnragedGuardModifier = 1f;
        }
    }

    float getShellingModifier(){
        return ArtilleryModifier * FelyneBombardier;
    }

    void setSAPhialModifier(String Phial){
        switch(Phial){
            case "Power Phial":
                SAAtkPhialModifier = 1.2f;
                SAElmPhialModifier = 1;
                SAPhialType = Phial;
                break;
            case "Element Phial":
                SAAtkPhialModifier = 1;
                SAElmPhialModifier = 1.25f;
                SAPhialType = Phial;
                break;
            default:
                SAAtkPhialModifier = 1;
                SAElmPhialModifier = 1;
                SAPhialType = Phial;
                break;
        }
    }

    float getSAPhialAtkModifier(){
        return SAAtkPhialModifier;
    }

    float getSAPhialElmModifier(){
        return SAElmPhialModifier;
    }

    String SAPhialType(){
        return SAPhialType;
    }

    void setHeatGaugeModifier(float HG){
        HeatGaugeModifier = HG;
    }

    float getHeatGaugeModifier(){
        return HeatGaugeModifier;
    }

    float getDragonBreathModifier(Boolean Check){
        if(Check){
            return 10;
        }
        else{
            return  0;
        }
    }

    void setIGExtractModifier(float Modifier){
        IGExtractModifier = Modifier;
    }

    void setGunpowderInfusionModiifer(Boolean Check){
        if (Check){
            GunpowderInfusionModifier = 1.1f;
        }
        else{
            GunpowderInfusionModifier = 1;
        }
    }

    void setShotModifier(float Modifier){
        ShotModifier = Modifier;
    }

    void setReloadModifier(Boolean Check){
        if(Check) {
            ReloadModifier = 1.05f;
        }
        else{
            ReloadModifier = 1;
        }
    }

    void setFelyneTemperModifier(Boolean Check){
        if(Check) {
            FelyneTemperModifier = 1.05f;
        }
        else{
            FelyneTemperModifier = 1;
        }
    }

    void setFelyneSharpshooterModifer(Boolean Check){
        if(Check) {
            FelyneSharpshooterModifer = 1.05f;
        }
        else{
            FelyneSharpshooterModifer = 1;
        }
    }

    void setRapidFireModifier(Boolean Check, String ShotType){
        if(Check) {
            switch (ShotType){
                case "Normal":
                    RapidFireModifier = 0.8f;
                    break;
                case "Pierce":
                    RapidFireModifier = 0.7f;
                    break;
                default:
                    RapidFireModifier = 0.6f;
                    break;
            }
        }
        else{
            RapidFireModifier = 1;
        }
    }

    void setCoatingModifier(float RawModifier, float ElmModifier){
        AtkCoatingModifier = RawModifier;
        ElmCoatingModifier = ElmModifier;
    }

    //TODO: *NOTE* Affinity related methods

    private float getCalculatedAffinity(float Affinity, boolean Check){
        float CritOverflow;
        if(!Check){
            TotalSkillCritModifier = 0;
            CritBoostModifier = 0;

            /*if((Affinity + AffinityOilModifier) > 100){
                Affinity -= AffinityOilModifier;
            }
            if((Affinity + EvasiveManeuversModifier) > 100){
                Affinity -= EvasiveManeuversModifier;
            }*/
        }
        else{
            TotalSkillCritModifier = RepeatOffenderModifier + WeaknessExploitModifier + GroupDCrit +
                    GroupJCrit + GroupO;

            /*if((TotalSkillCritModifier + AffinityOilModifier + EvasiveManeuversModifier + Affinity) > 100){
                if((Affinity + AffinityOilModifier) > 100){
                    Affinity -= AffinityOilModifier;
                }
                if((Affinity + EvasiveManeuversModifier) > 100){
                    Affinity -= EvasiveManeuversModifier;
                }
                TotalSkillCritModifier = 100 - (Affinity + AffinityOilModifier + EvasiveManeuversModifier);
            }*/
        }

        CritOverflow = 1 + (CritBoostModifier + 0.25f) * ((TotalSkillCritModifier +
                getOilStackModifier(OilSynergyState, OilSynergyLevel) + EvasiveManeuversModifier +
                StingerModifier + Affinity)/100);

        if(CritOverflow > 1.25f) CritOverflow = 1.25f;

        return CritOverflow + CritBoostModifier;
        //return 1 + (CritBoostModifier + 0.25f) * ((TotalSkillCritModifier +
          //      AffinityOilModifier + EvasiveManeuversModifier + Affinity)/100);
    }

    public void setRepeatOffenderModifier(boolean Check){
        if(Check){
            RepeatOffenderModifier = 30f;
        }
        else{
            RepeatOffenderModifier = 0;
        }
    } //Was a float method

    void setAffinityOilModifier(boolean Check){
        if(Check){
            AffinityOilModifier = 30;
        }
        else{
            AffinityOilModifier = 0;
        }
    } //Was a float method

    void setChaosOilModifier(boolean Check, int Level){
        if(Check){
            if(Level == 1){
                ChaosOilModifier = 1.15f;
                OilSynergyLevel = Level;
            }
            else{
                ChaosOilModifier = 1.3f;
                OilSynergyLevel = Level;
            }
        }
        else{
            ChaosOilModifier = 0;
        }
    }

    void setOilSynergyState(boolean Check){
        OilSynergyState = Check;
    }

    private float getOilStackModifier(boolean Check, int Level){
        if(Check){
            if(Level == 1) return 30;
            else if(Level == 3)return 60;
            else return AffinityOilModifier;
        } else{
            if(AffinityOilModifier == 0 && ChaosOilModifier > 0) return ChaosOilModifier;
            else if(AffinityOilModifier > 0 && ChaosOilModifier == 0)return AffinityOilModifier;
            else return 0;
        }
    }

    public void setCritBoostModifier(boolean Check){
        if(Check) CritBoostModifier = 0.15f;
        else CritBoostModifier = 0;
    } //Was a float method

    void setStingerModifier(Boolean Check){
        if(Check){
            StingerModifier = 100;
        }
        else{
            StingerModifier = 0;
        }
    }

    void setEvasiveManeuversModifier(boolean Check, int Level){
        if(Check){
            if(Level == 2) EvasiveManeuversModifier = 20;
            else EvasiveManeuversModifier = 35;
        }
        else EvasiveManeuversModifier = 0;
    }

    void setWeaknessExploitModifier(boolean Check){
        if(Check) WeaknessExploitModifier = 50f;
        else WeaknessExploitModifier = 0f;
    }

    public void setGroupDCrit(float DA){
        GroupDCrit = DA;
    }

    public void setGroupJCrit(float CLP){
        GroupJCrit = CLP;
    }

    public void setGroupO(float CE){
        GroupO = CE;
    }

    //TODO: *NOTE* Element related methods

    private float getCalculatedElement(float Element){
        return (Element + additiveElm) * multiplicativeElm * ElmCoatingModifier;
    }

    private float getCalculatedSubElement(float SubElement){
        return (SubElement + additiveElm_Sub) * multiplicativeElm_Sub;
    }

    public void setElementAtkUp(boolean Check){
        if(Check){
            ElementAtkUp = 1.1f;
        }
        else{
            ElementAtkUp = 1;
        }
    }

    public void setElementCrit(boolean Check, float RawAffinity){
        if(Check){
            ElmAffinity = (1 + ( 0.25f  * ((TotalSkillCritModifier +
                    getOilStackModifier(OilSynergyState, OilSynergyLevel) + EvasiveManeuversModifier +
                    RawAffinity)/100)));
            ElmAffCheck = true;
        }
        else{
            ElmAffinity = 1;
            ElmAffCheck = false;
        }
    }

    public void setGroupP(float add, float multi){
        GroupPAdd = add;
        GroupPMulti = multi;
    }

    void setGroupP_Sub(float addSub, float multiSub){
        GroupPAddSub = addSub;
        GroupPMultiSub = multiSub;
    }

    public boolean CheckElmSkill(float Elm, boolean Check){
        SkillCheck(Check);
        return Elm == 0 && ((additiveElm > 0 ) || (multiplicativeElm > 1f) || ElmAffCheck);
    }
}