package solution_fertilizers.logic;

import core.data.*;
import core.data.storage.StorageManager;
import core.logic.DataManagerModeFertilizers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class FertilizerCalculations {

    /*среднее содержание общего (валового) азота, константное значение, оно равно 5%*/
    private static final float AVERAGE_TOTAL_NITROGEN=5;
    /*содержание азота в растительных остатках, константное значение, оно равно 2%*/
    private static final float NITROGEN_IN_PLANT_RESIDUES=2;

    private static final String COEFFICIENT_MICROELEMENT_YEAR_ALL="Итого";
    private static final String COEFFICIENT_MICROELEMENT_YEAR_ONE="1";
    private static final String COEFFICIENT_MICROELEMENT_YEAR_TWO="2";

    public static final String MICROELEMENT_NITROGEN="Азот";
    public static final String MICROELEMENT_POTASSIUM="Калий";
    public static final String MICROELEMENT_PHOSPHORUS="Фосфор";
    private static HashMap<String,Microelement> microelements;

    private StorageManager sm;
    private DataManagerModeFertilizers dmf;

    public FertilizerCalculations() {}

    public FertilizerCalculations(StorageManager sm) {
        this.sm=sm;
        /*TODO: можно вынести в контекст приложения*/
        microelements = new HashMap<String,Microelement> ();
        microelements.put(MICROELEMENT_NITROGEN, (Microelement) sm.findByField(Microelement.class,"microelementName", MICROELEMENT_NITROGEN,"==").iterator().next());
        microelements.put(MICROELEMENT_POTASSIUM, (Microelement) sm.findByField(Microelement.class,"microelementName", MICROELEMENT_POTASSIUM,"==").iterator().next());
        microelements.put(MICROELEMENT_PHOSPHORUS, (Microelement) sm.findByField(Microelement.class,"microelementName", MICROELEMENT_PHOSPHORUS,"==").iterator().next());
    }

//    public static float adm(){
//        System.out.println("-----------------");
//        System.out.println("РАСЧЕТ ДВУ");
//        System.out.println("КУЛЬТУРА: Озимая пшеница");
//        System.out.println("ТИП ПОЧВЫ: ...");
//        float S = 1*10000;//м2
//        System.out.println("Поле из примера"+", площадь:"+S);
//        /*h - глубина пахотного слоя (см)*/
//        float h = 22;
//            /*ρ – объемная масса почвы пахотного слоя (т/м3)*/
//        float p = 1.25f;
//            /*1. расчет m – масса пахотного слоя (т/га)*/
//        float m=S*0.01f*h*p;
//        System.out.println("глубина пахотного слоя, h: "+h+", объемная масса почвы пахотного слоя, p:"+p+", масса пахотного слоя, m (т/га):"+m+" кг/га: "+ m*1000);
//        float valuePh=0;
//        float valueP = 0;
//        float valueN = 0;
//
//        float cPh = 170;
//        float cP = 140;
//        float cH = 2.5f;
//        System.out.println("Содержание подвижных соединений фосфора (мг/кг): "+cPh);
//        System.out.println("Содержание обменного калия (мг/кг): "+cP);
//        System.out.println("Содержание гумуса (%): "+cH);
//        valuePh=m*cPh*0.001f/1;
//        valueP=m*cP*0.001f/1;
//        System.out.println(" Запас подвижных соединений фосфора в пахотном слое (кг/га): "+valuePh);
//        System.out.println(" Запас подвижных соединений калия в пахотном слое (кг/га): "+valueP);
//
//        /*3.(ДЛЯ АЗОТА)определение запаса минерального азота в почве*/
//            /*x_h - запас гумуса в почве*/
//            float value_humus=cH;
//            value_humus=value_humus*m*1000/100;
//            System.out.println("запас гумуса в почве (кг/га): "+value_humus);
//            value_humus=value_humus*AVERAGE_TOTAL_NITROGEN/100;
//            System.out.println("запас азота в гумусе (кг/га): "+value_humus);
//            float hmc_value = 1.5f;
//            System.out.println("кмг: "+hmc_value);
//            valueN=value_humus*hmc_value/100;
//            System.out.println("количество допустимых растениями минеральных форм азота (кг): "+valueN);
//
//            /*4.определяется количество усваиваемых элементов*/
//        float digestion_coefficient=5;
//        float number_digestible_elements = valuePh*digestion_coefficient/100;
//        System.out.println("количество усваиваемых элементов (кг): "+number_digestible_elements);
//        System.out.println("-----------------");
//
//        return number_digestible_elements;
//    }
//
//    public static float dvu(float number_digestible_elements){
//        /*5.определение уровня возможного урожая */
//        float removal=1.1f;
//        float reallyPossibleHarvest = number_digestible_elements*100/removal;
//        System.out.println("!уровень возможного урожая (ДВУ) (кг/га): "+reallyPossibleHarvest);
//        System.out.println("-----------------");
//
//        return reallyPossibleHarvest;
//    }
//
//    public static void doze(){
//        float need_microelement=0;
//        System.out.println("-----------------");
//        System.out.println("РАСЧЕТ ДОЗ УДОБРЕНИЙ");
//        System.out.println("КУЛЬТУРА: озимая пшеница");
//        System.out.println("ТИП ПОЧВЫ: ...");
//        System.out.println("планируемая урожайность: (ц/га)"+21);
//        System.out.println("подкормка предшественника (кг/га): "+30);
//        System.out.println("урожайность предшественника (ц/га): "+27.5);
//            /*1.рассчет выноса питательных веществ с планируемым урожаем*/
//        float removal=1.1f;
//        float takeaway=removal*21;
//        System.out.println("вынос питательных веществ (кг): "+takeaway);
//
//        /*2.рассчет ДВУ*/
//        float rph=adm();
//        float value=rph;

                /*3.(ДЛЯ АЗОТА)рассчет возможнго усвоения элементов питания*/
//            float utilization_rate=10;
//            float absorption = 30*utilization_rate*0.1f/100;
//            value+=absorption;
//            System.out.println("возможное усвоение элементов питания из ранее внесенных удобрений (кг): "+absorption);

                /*4.(ДЛЯ АЗОТА)определение возможное использования элемента, накопленного предшественником*/
//            float accumulated_balance=27.5f*1.5f*100;
//            System.out.println("содержание элемента, который может остаться в почве(кг): "+accumulated_balance);
//            accumulated_balance = accumulated_balance*NITROGEN_IN_PLANT_RESIDUES/100;
//            System.out.println("общее накопление азота в почве: "+accumulated_balance);
//            float cmh_N=30;
//            accumulated_balance = accumulated_balance*cmh_N/100;
//            value+=accumulated_balance;
//            System.out.println("количество усвоенного азота: "+accumulated_balance);


            /*5.определение общее количество элементов, которые растение способно усвоить из почвы*/
//        float all_quantity=value;
//        System.out.println("общее количество элементов, которые растение способно усвоить из почвы: "+all_quantity);
//
//            /*6.определение нужды растений в дополнительном внесении элементов*/
//        float additional=all_quantity-takeaway;
//        System.out.println("нужда растений в дополнительном внесении элементов: "+additional);
//        if (additional<0){
//            float absorption_coeff = 50;;
//            need_microelement=abs(additional)*100/absorption_coeff;
//        }
//        System.out.println("!потребность в минеральных удобрениях: "+need_microelement);
//        System.out.println("-----------------");
//    }
            public float calculationsM(float S, float h, float p){
                float m=S*h*p*0.01f;

                return m;
            }

            public float microelementContent(float m, float c){

                System.out.println("содержание микроэлемента: "+c);
               float  value=m*c*0.001f/1;

                return value;
            }

            public float valueHumus(float value_humus, float m){

                value_humus=value_humus*m/100;

                return value_humus;
            }

            public float valueOfNitrogenInHumus(float value_humus){
                value_humus=value_humus*AVERAGE_TOTAL_NITROGEN*1000/100;

                return value_humus;
            }

            public float valueFormsOfNitrogen(float value_humus, Crop crop, TypeOfSoil soil){
                float value_h = 0;
                HumusMineralizationCoefficient coeff = getHMCForGroupOfCropAndTypeOfSoil(soil,crop.getGroupOfCropsForCoeff());
                if (coeff!=null)
                    value_h = coeff.getCmhValue();
                System.out.println("кмг: "+value_h);

               float value=value_humus*value_h/100;
               System.out.println("!!! "+value);

                return value;
            }


    /*РАСЧЕТ ДВУ - ДЕЙСТВИТЕЛЬНО ВОЗМОЖНОГО УРОЖАЯ*/
    public float startCalculationsAmountOfDigestibleMicroelements(Microelement microelement, AgricultureField field, Crop crop, TypeOfSoil soil, float elementContent, float microelement3){
        if (microelement!=null&&field!=null&&crop!=null&&soil!=null){
            System.out.println("-----------------");
            System.out.println("РАСЧЕТ ДВУ");
            System.out.println("КУЛЬТУРА: "+crop.getNameCrop());
            System.out.println("ТИП ПОЧВЫ: "+soil.getCodeTypeOfSoil()+" "+soil.getNameTypeSoil());
            /*S – площадь 1 га в м2*/
            float S = field.getArea()*10000;
            System.out.println("Поле "+field.getFieldNumber()+", площадь:"+S);
            /*h - глубина пахотного слоя (см)*/
            float h = field.getTopsoilDepth();
            /*ρ – объемная масса почвы пахотного слоя (т/м3)*/
            float p = field.getSoilBulk();
            /*1. расчет m – масса пахотного слоя (т/га)*/
            float m=calculationsM(S, h, p);
            System.out.println("глубина пахотного слоя, h: "+h+", объемная масса почвы пахотного слоя, p:"+p+", масса пахотного слоя, m:"+m);
            float value=0;

            System.out.println("\nМИКРОЭЛЕМЕНТ: "+microelement.getMicroelementName()+"\n");
            /*2. (ДЛЯ ФОСФОРА И КАЛИЯ)определение запаса подвижных соединений фосфора и калия в пахотном слое*/
            if (microelement.equals(microelements.get(MICROELEMENT_PHOSPHORUS))||microelement.equals(microelements.get(MICROELEMENT_POTASSIUM))){
                /*TODO:взять из бд group-microelements*/
                float c=elementContent;
                value=microelementContent(m, c);
                System.out.println("запас подвижных соединений: "+value);
            }

            /*3.(ДЛЯ АЗОТА)определение запаса минерального азота в почве*/
            /*x_h - запас гумуса в почве*/
            if (microelement.equals(microelements.get(MICROELEMENT_NITROGEN))){
                /*TODO:взять из бд group-microelements*/
                float value_humus=elementContent;
                value_humus=valueHumus(value_humus, m);
                System.out.println("запас гумуса в почве: "+value_humus);
                value_humus=valueOfNitrogenInHumus(value_humus);
                System.out.println("запас азота в гумусе: "+value_humus);
                value=valueFormsOfNitrogen(value_humus, crop, soil);
                System.out.println("количество допустимых растениями минеральных форм азота: "+value);
            }

            /*4.определяется количество усваиваемых элементов*/
            float digestion_coefficient=microelement3;
            System.out.println("value "+value+" microelement3 "+microelement3);
            float number_digestible_elements = (value*digestion_coefficient)/100;
            System.out.println("количество усваиваемых элементов: "+number_digestible_elements);

            return number_digestible_elements;
        }
        return 0;
    }

    public float startCalculationsRealPossibleHarvest(float number_digestible_elements, Crop crop, Microelement microelement){

        /*5.определение уровня возможного урожая */
        float removal=getRemovalForCropAndMicroelement(crop, microelement);
        float reallyPossibleHarvest = number_digestible_elements*100/removal;
        System.out.println("!уровень возможного урожая (ДВУ): "+reallyPossibleHarvest);
        System.out.println("-----------------");

        return reallyPossibleHarvest;
    }

    public float calcutationsTakeaway(float prod, Crop crop, Microelement microelement){
        float removal=getRemovalForCropAndMicroelement(crop, microelement);
        float takeaway=(removal*10)*prod;

        return takeaway;
    }

    public float absorption(float utilization_rate, float top_dressing_predecessor){
        float absorption = top_dressing_predecessor*utilization_rate/100;

        return absorption;
    }

    public float accumulatedBalance(float harvest_predecessor){
        float accumulated_balance=harvest_predecessor*1.5f*100;

        return accumulated_balance;
    }

    public float accumulatedBalanceInSoil(float accumulated_balance){
        accumulated_balance = accumulated_balance*NITROGEN_IN_PLANT_RESIDUES/100;

        return accumulated_balance;
    }

    public float accumulatedBalanceInLeavings(float accumulated_balance, int yearOfUsing){
        if(yearOfUsing == 1)
            accumulated_balance = accumulated_balance*30/100;
        else if(yearOfUsing == 2 || yearOfUsing == 3)
            accumulated_balance = accumulated_balance*20/100;
        return accumulated_balance;
    }

    public float additional(float all_quantity, float takeaway){
        float additional = all_quantity - takeaway;

        return additional;
    }

    /*РАСЧЕТ ДОЗ УДОБРЕНИЯ ДЛЯ ПЛАНИРУЕМОЙ УРОЖАЙНОСТИ*/
    public float startCalculationsFertilizerDosesForYield(Microelement microelement, AgricultureField field, Crop crop, TypeOfSoil soil, float prod, float elementContent, float top_dressing_predecessor, float harvest_predecessor, float microelement3, int yearOfUsing){
        float need_microelement=0;
        if (microelement!=null&&field!=null&&crop!=null&&soil!=null){
            System.out.println("-----------------");
            System.out.println("РАСЧЕТ ДОЗ УДОБРЕНИЙ");
            System.out.println("КУЛЬТУРА: "+crop.getNameCrop());
            System.out.println("ТИП ПОЧВЫ: "+soil.getCodeTypeOfSoil()+" "+soil.getNameTypeSoil());
            System.out.println("ожидаемая урожайность: "+prod);
            System.out.println("подкормка предшественника: "+top_dressing_predecessor);
            System.out.println("урожай предшественника: "+harvest_predecessor);
            System.out.println("Год использования: "+yearOfUsing);
            System.out.println("\nМИКРОЭЛЕМЕНТ: "+microelement.getMicroelementName()+"\n");
            /*1.рассчет выноса питательных веществ с планируемым урожаем*/
            float takeaway=calcutationsTakeaway(prod, crop, microelement);
            System.out.println("вынос питательных веществ: "+takeaway);

            /*2.рассчет ДВУ*/
            float amountOfDigestibleMicroelements = startCalculationsAmountOfDigestibleMicroelements(microelement, field, crop, soil,elementContent, microelement3);
            System.out.println("количество усваиваемых элементов: "+amountOfDigestibleMicroelements);
            System.out.println("дву: "+ startCalculationsRealPossibleHarvest(amountOfDigestibleMicroelements, crop, microelement));
            float value=amountOfDigestibleMicroelements;

            if (microelement.equals(microelements.get(MICROELEMENT_NITROGEN))){
                /*3.(ДЛЯ АЗОТА)рассчет возможнго усвоения элементов питания*/
                float utilization_rate=getAverageDigestionCoefficient(microelement, Integer.toString(yearOfUsing));
                value+=absorption(utilization_rate, top_dressing_predecessor);
                System.out.println("возможное усвоение элементов питания из ранее внесенных удобрений: "+absorption(utilization_rate, top_dressing_predecessor));

                /*4.(ДЛЯ АЗОТА)определение возможное использования элемента, накопленного предшественником*/
                float accumulated_balance=accumulatedBalance(harvest_predecessor);
                System.out.println("содержание элемента, который может остаться в почве: "+accumulated_balance);
                accumulated_balance = accumulatedBalanceInSoil(accumulated_balance);
                System.out.println("общее накопление азота в почве: "+accumulated_balance);
                accumulated_balance = accumulatedBalanceInLeavings(accumulated_balance, yearOfUsing);
                value+=accumulated_balance;
                System.out.println("количество усвоенного азота: "+accumulated_balance);
            }

            /*5.определение общее количество элементов, которые растение способно усвоить из почвы*/
            float all_quantity=value;
            System.out.println("общее количество элементов, которые растение способно усвоить из почвы: "+all_quantity);

            /*6.определение нужды растений в дополнительном внесении элементов*/
            float additional=additional(all_quantity, takeaway);
            System.out.println("нужда растений в дополнительном внесении элементов: "+additional);
            if (additional<0){
                float absorption_coeff = getAverageDigestionCoefficient(microelement, COEFFICIENT_MICROELEMENT_YEAR_ALL);
                need_microelement=abs(additional)*0.1f/absorption_coeff;
            }
            System.out.println("!потребность в минеральных удобрениях: "+need_microelement);
            System.out.println("-----------------");
        }
        return need_microelement;
    }

    private HumusMineralizationCoefficient getHMCForGroupOfCropAndTypeOfSoil(TypeOfSoil type, GroupOfCropForCoeff group) {
        String[] fieldName = new String[]{"typeOfSoil", "groupOfCrops"};
        Object[] value = new Object[]{type, group};
        String[] condition = new String[]{"==", "=="};
        Collection <HumusMineralizationCoefficient_2_TypeOfSoil_2_GroupsOfCrops> res = this.sm.findByField(HumusMineralizationCoefficient_2_TypeOfSoil_2_GroupsOfCrops.class, fieldName, value, condition);
        HumusMineralizationCoefficient coeff = null;
        if (res!=null && !res.isEmpty())
            coeff = res.iterator().next().getHumusMineralizationCoefficient();
        return coeff;
    }

    public float getRemovalForCropAndMicroelement(Crop crop, Microelement microelement) {
        System.out.println("crop id: "+crop.getCropId()+" crop: "+crop.getNameCrop());
        String[] fieldName = new String[]{"crop", "microelement"};
        Object[] value = new Object[]{crop, microelement};
        String[] condition = new String[]{"==", "=="};
        Collection <RemovalOfMicroelement> res = this.sm.findByField(RemovalOfMicroelement.class, fieldName, value, condition);
        float removal = 0;
        if (res!=null && !res.isEmpty()) {
            removal = res.iterator().next().getValue();
            System.out.println("func M: "+microelement.getMicroelementName()+" removal: "+removal);
        }
        return removal;
    }

    public float getAverageDigestionCoefficient(Microelement microelement, String year){
        String[] fieldName = new String[]{"microelement", "yearOfUsing"};
        Object[] value = new Object[]{microelement, year};
        String[] condition = new String[]{"==", "=="};
        Collection <CoefficientOfUsingMicroelementFromFert> res = this.sm.findByField(CoefficientOfUsingMicroelementFromFert.class, fieldName, value, condition);
        float average=0;
        if (res!=null&&!res.isEmpty()){
            CoefficientOfUsingMicroelementFromFert coeff = res.iterator().next();
            average = (coeff.getMaxValue()+coeff.getMinValue())/2;
        }
        return average;
    }

    public float getCoefficientUsingMicroelementFromSoil(Microelement microelement, float amountOfM){
        /*float coeff = 0.0f;
        Collection<CoefficientOfUsingMicroelementFromSoil> commonCollection = dmf.findAllCoefficientOfUsingMicroelementFromSoil();
        for (CoefficientOfUsingMicroelementFromSoil c: commonCollection){
            if(c.getMicroelement().getMicroelementId() == microelement.getMicroelementId() && c.getAmountInSoil() == amountOfM)
                coeff = c.getValue();
        }*/
        String[] fieldName = new String[]{"microelement", "amountInSoil"};
        Object[] value = new Object[]{microelement, amountOfM};
        String[] condition = new String[]{"==", "=="};
        Collection <CoefficientOfUsingMicroelementFromSoil> res = this.sm.findByField(CoefficientOfUsingMicroelementFromSoil.class, fieldName, value, condition);
        float coeff=0;
        if (res!=null&&!res.isEmpty())
            coeff=res.iterator().next().getValue();
        return coeff;
    }

    public Map<String, String> getMapForMicroelement(Microelement microelement){

        Collection<CoefficientOfUsingMicroelementFromSoil> commonCollection = dmf.findAllCoefficientOfUsingMicroelementFromSoil();
        Map<String, String> mapResults = new HashMap<String, String>();
        for(CoefficientOfUsingMicroelementFromSoil c: commonCollection){
            if(c.getMicroelement().getMicroelementId() == microelement.getMicroelementId())
                mapResults.put(Integer.toString(c.getCoefficientOfUsingMicroelementFromSoilId()), Float.toString(c.getAmountInSoil()));
        }

        return mapResults;
    }

    public static HashMap<String, Microelement> getMicroelements() {
        return microelements;
    }
}

