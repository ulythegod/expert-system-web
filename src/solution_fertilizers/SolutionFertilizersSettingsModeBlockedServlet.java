package solution_fertilizers;

import auth.AuthUser;
import com.google.gson.Gson;
import core.data.*;
import core.data.storage.StorageManager;
import core.logic.DataManagerMode1;
import core.logic.DataManagerModeFertilizers;
import org.json.JSONArray;
import org.json.JSONObject;
import solution_fertilizers.logic.FertilizerCalculations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class SolutionFertilizersSettingsModeBlockedServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Сервлет SolutionFertilizersSettingModeServlet запущен");
        Locale l = new Locale("ru");
        ResourceBundle rb = ResourceBundle.getBundle("Resource", l);

        DataManagerModeFertilizers dmf = (DataManagerModeFertilizers) req.getServletContext().getAttribute("dataManagerModeFertilizers");
        DataManagerMode1 dm1 = (DataManagerMode1) req.getServletContext().getAttribute("dataManagerMode1");
        StorageManager sm = (StorageManager)req.getServletContext().getAttribute("storageManager");

        String newNameSolution = "";//Название нового решения
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        String testField = null; //поле
        boolean coeff1;

        /*Получаем текущее хозяйство*/
        AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
        if (authUser==null||authUser.getAgriculture()==null)
            resp.sendRedirect("/login.do?logout=true");

        Agricultures agriculture1 = authUser.getAgriculture();
        System.out.println("idAgr = " + agriculture1.getAgricultureId());

        /*Получаем активную земельную базу*/
        LandBase landBase = (LandBase)req.getSession().getAttribute("landBase");
        if(landBase == null) {
            req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_single_field_blocked.jsp").forward(req, resp);
            return;
        }

        newNameSolution = req.getParameter("newNameSolution");
        if(newNameSolution == null) {
            req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_single_field_blocked.jsp").forward(req, resp);
            return;
        }

        /*Получаем все поля активной земельной базы хозяйства */
        Collection<AgricultureField> fields = dm1.getAllFields(landBase);
        if (fields == null)
            return;

         /*Обрабатываем выбор поля*/
        testField = req.getParameter("field1");
        if (testField == null) {
            //Map<String, String> mapFields = new HashMap<String, String>();
            String text_field = rb.getString("FERT_FORM_SETT_FIELD_NUMBER");
            //for(AgricultureField f : fields)
            //mapFields.put(Integer.toString(f.getAgricultureFieldId()), text_field + f.getFieldNumber());
            //String json = new Gson().toJson(mapFields);
            JSONArray fieldsArray = new JSONArray();
            for(AgricultureField f : fields){
                JSONObject field = new JSONObject();
                field.put("field-id",f.getAgricultureFieldId());
                field.put("field-name",text_field + f.getFieldNumber());
                field.put("field-number",f.getFieldNumber());
               // System.out.println(f.getAgricultureFieldId()+" "+text_field + f.getFieldNumber()+" "+f.getFieldNumber());
                fieldsArray.put(field);
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(fieldsArray.toString());
            return;
        }

        AgricultureField field = dmf.getAgricultureField(fields, testField);
        System.out.println("Выбрано поле:" + field.getAgricultureFieldId());

        /*По выбраному полю инициализируем тип земли и площадь*/
        TypeOfSoil typeOfSoil1 = field.getTypeOfSoil();
        float area = field.getArea();
        System.out.println("Тип почвы "+ typeOfSoil1.getCodeTypeOfSoil() + " " + typeOfSoil1.getGroupOfTypesOfSoils().getNameGroupOfTypesOfSoils() +" площадь"+area);

        float depth = field.getTopsoilDepth();
        float bulk = field.getSoilBulk();

        Collection<Crop> crops = dmf.getAllCrops();
        Collection<GradeOfCrop> gradeOfCrops = null;
        Collection<RemovalOfMicroelement> removal = dmf.findAllRemovalOfMicroelement();

        if(crops == null)
            return;

        if(req.getParameter("area1") == null && req.getParameter("typeSoil1") == null && req.getParameter("corpSelect") == null){
            JSONObject result = new JSONObject();
            result.put("area1", BigDecimal.valueOf(area).setScale(2,BigDecimal.ROUND_HALF_UP));
            result.put("typeSoil1", typeOfSoil1.getCodeTypeOfSoil() + " " + typeOfSoil1.getNameTypeSoil());
            result.put("depth", BigDecimal.valueOf(depth).setScale(2,BigDecimal.ROUND_HALF_UP));
            result.put("bulk", BigDecimal.valueOf(bulk).setScale(2,BigDecimal.ROUND_HALF_UP));
            Map<String, String> mapGrades = new HashMap<String, String>();
            for(Crop c: crops){
                for(RemovalOfMicroelement r: removal) {
                    if(c.getCropId() == r.getCrop().getCropId()) {
                        gradeOfCrops = c.getGradeOfCrop();
                        for (GradeOfCrop g : gradeOfCrops) {
                            if (g.getNameGradeOfCrops() != null) {
                                mapGrades.put(Integer.toString(g.getGradeOfCropId()), g.getCodeGradeOfCrop() + " " + g.getCrop().getNameCrop() + " " + g.getNameGradeOfCrops());
                                continue;
                            } else {
                                mapGrades.put(Integer.toString(g.getGradeOfCropId()), g.getCodeGradeOfCrop() + " " + g.getCrop().getNameCrop());
                            }
                        }
                    }
                }
            }
            result.put("corpSelect", mapGrades);
            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin","*");
            resp.getWriter().write(result.toString());
            return;
        }

        /*инициализируем планируемую урожайность*/
        if(req.getParameter("planningYield") == null) {
            req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_single_field_blockedjsp").forward(req, resp);
            return;
        }
        String testPlanningYield = req.getParameter("planningYield");
        System.out.println("Планируемая урожайность: " + testPlanningYield);

        Collection<Predecessor> preds = dm1.getAllPredecessors();

        String testPred = req.getParameter("predSelect");
        if(testPred == null) {
            JSONObject result = new JSONObject();

            Map<String, String> mapPreds = new HashMap<String, String>();
            for(Predecessor r : preds) {
                if(r.getPredecessorId() == 1) {
                    String text_pred = rb.getString("PL_FORM_SETT_PRED_UNKN");
                    mapPreds.put(Integer.toString(r.getPredecessorId()), text_pred);
                    continue;
                }
                mapPreds.put(Integer.toString(r.getPredecessorId()), r.getCrop().getCodeCrop() + " " + r.getCrop().getNameCrop());
            }
            result.put("predSelect", mapPreds);
            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin","*");
            resp.getWriter().write(result.toString());
            return;
        }

        if(req.getParameter("predDoze") != null && req.getParameter("predYield") != null && req.getParameter("systFert") == null){
            Collection<ApplicationOfFertilizers> applicationOfFertilizers = dmf.findAllApplicationsOfFertilizers();
            Map<String, String> mapApplFert = new HashMap<String, String>();
            for(ApplicationOfFertilizers app: applicationOfFertilizers) {
                if(app.getDescriptionApplicationOfFertilizers().contains(rb.getString("FERT_FORM_PANEL_RESULTS_N").toLowerCase()) || app.getDescriptionApplicationOfFertilizers().contains(rb.getString("FERT_FORM_PANEL_RESULTS_P").toLowerCase()) || app.getDescriptionApplicationOfFertilizers().contains(rb.getString("FERT_FORM_PANEL_RESULTS_K").toLowerCase()))
                    mapApplFert.put(Integer.toString(app.getApplicationOfFertilizersId()), app.getCodeApplicationOfFertilizers() + " " + app.getDescriptionApplicationOfFertilizers());
            }
            JSONObject result = new JSONObject();

            result.put("systFert",mapApplFert);

            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin","*");
            resp.getWriter().write(result.toString());
            return;
        }

        FertilizerCalculations calculations;
        if (sm!=null){
            calculations = new FertilizerCalculations(sm);
        }else return;
        HashMap<String,Microelement> microelements = FertilizerCalculations.getMicroelements();

        Crop cropForCalculations = null;
        if (req.getParameter("corpSelect")!=null) {
            Collection<GradeOfCrop> corp1 = sm.findByField(GradeOfCrop.class, "gradeOfCropId", Integer.parseInt(req.getParameter("corpSelect")), "==");
            if (!corp1.isEmpty())
                cropForCalculations = corp1.iterator().next().getCrop();
            else return;
        } else return;

        boolean knowPred = true;
        Predecessor pred = null;
        testPred = req.getParameter("predSelect");
        if (testPred.equals("1")) {
            knowPred = false;
            testPred = "";
        }
        if(knowPred) {
            if (req.getParameter("predSelect") != null) {
                Collection<Predecessor> corp1 = sm.findByField(Predecessor.class, "predecessorId", Integer.parseInt(req.getParameter("corpSelect")), "==");
                if (!corp1.isEmpty())
                    pred = corp1.iterator().next();
                else return;
            } else return;
        }else return;

        String app_fert = "";
        ApplicationOfFertilizers application = null;
        if (req.getParameter("systFert")!=null) {
            Collection<ApplicationOfFertilizers> app1 = sm.findByField(ApplicationOfFertilizers.class, "applicationOfFertilizersId", Integer.parseInt(req.getParameter("systFert")), "==");
            if (!app1.isEmpty()) {
                app_fert = app1.iterator().next().getDescriptionApplicationOfFertilizers();
                application = app1.iterator().next();
            }
            else return;
        } else return;

        Boolean isN = null;
        Boolean isPh = null;
        Boolean isP = null;
        if (app_fert != null){
            isN=app_fert.contains(FertilizerCalculations.MICROELEMENT_NITROGEN.toLowerCase());
            //System.out.println(isN);
            isPh = app_fert.contains(FertilizerCalculations.MICROELEMENT_PHOSPHORUS.toLowerCase());
            //System.out.println(isPh);
            isP = app_fert.contains(FertilizerCalculations.MICROELEMENT_POTASSIUM.toLowerCase());
            //System.out.println(isP);
        }

        if (req.getParameter("coeff1")!=null||req.getParameter("coeff2")!=null||req.getParameter("coeff3")!=null){
            JSONObject result = new JSONObject();

            /*ПАНЕЛЬ С КОЭФФИЦИЕНТАМИ 1 - Коэффициенты выноса элементов питания с товарной и побочной продукцией*/
            if(req.getParameter("coeff1")!=null) {
                if(!isN)
                    result.put("nitrogen1", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                if(!isPh)
                    result.put("phosphorus1", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                if(!isP)
                    result.put("potassium1", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));
            }

            /*ПАНЕЛЬ С КОЭФФИЦИЕНТАМИ 2 - Коэффициенты использования вещества из удобрений*/
            if(req.getParameter("coeff2")!=null) {
                boolean coeff2 = Boolean.parseBoolean(req.getParameter("coeff2"));
                if (coeff2||req.getParameter("yearOfUsing")!=null){
                    if(!isN)
                        result.put("nitrogen2", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                    if(!isPh)
                        result.put("phosphorus2", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                    if(!isP)
                        result.put("potassium2", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));
                }
            }

            /*ПАНЕЛЬ С КОЭФФИЦИЕНТАМИ 3 - Коэффициенты использования вещества из почвы*/
            if(req.getParameter("coeff3")!=null) {
                if(!isN)
                    result.put("nitrogen3", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                if(!isPh)
                    result.put("phosphorus3", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));

                if(!isP)
                    result.put("potassium3", rb.getString("FERT_FORM_NO_MICROELEMENT_IN_FERT"));
            }

            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.getWriter().write(result.toString());
           return;
        }

        ResultMode1 res = null;

        /*TODO:РАССЧЕТ УДОБРЕНИЙ*/
        if (req.getParameter("calc")!=null){
            JSONObject result = new JSONObject();
            try {
                System.out.println("\n\nСТАРТ РАСЧЕТОВ ДЛЯ МОДУЛЯ РАСЧЕТА НОРМ УДОБРЕНИЙ\n");
                float predDoze = Float.parseFloat(req.getParameter("predDoze"));
                float predYield = Float.parseFloat(req.getParameter("predYield"));
                float humus = Float.parseFloat(req.getParameter("humus"));
                float potassium = Float.parseFloat(req.getParameter("potassium"));
                float phosphorus = Float.parseFloat(req.getParameter("phosphorus"));
                System.out.println("----- Данные на старте расчетов в сервлете для режима без Системы земледелия ------");
                System.out.println("Доза удобрений для предшественника: "+predDoze+" Урожайность предшественника: "+predYield);
                System.out.println("Содержание гумуса, % "+humus+" Содержание подвижного фосфора, мг/кг "+potassium+" Содержание обменного калия, мг/кг"+phosphorus);


                HashMap<String,Float[]> microelements_value = new HashMap<>();
                if (isN){
                    Float[] nitrogen_array = new Float[3];
                    nitrogen_array[0]=Float.parseFloat(req.getParameter("nitrogen1"));
                    nitrogen_array[1]=Float.parseFloat(req.getParameter("nitrogen2"));
                    nitrogen_array[2]=Float.parseFloat(req.getParameter("nitrogen3"));
                    microelements_value.put("nitrogen",nitrogen_array);
                }
                if(isPh){
                    Float[] phosphorus_array = new Float[3];
                    phosphorus_array[0]=Float.parseFloat(req.getParameter("phosphorus1"));
                    phosphorus_array[1]=Float.parseFloat(req.getParameter("phosphorus2"));
                    phosphorus_array[2]=Float.parseFloat(req.getParameter("phosphorus3"));
                    microelements_value.put("phosphorus",phosphorus_array);
                }
                if(isP){
                    Float[] potassium_array = new Float[3];
                    potassium_array[0]=Float.parseFloat(req.getParameter("potassium1"));
                    potassium_array[1]=Float.parseFloat(req.getParameter("potassium2"));
                    potassium_array[2]=Float.parseFloat(req.getParameter("potassium3"));
                    microelements_value.put("potassium",potassium_array);
                }
                int predYear = Integer.parseInt(req.getParameter("predYear"));

                System.out.println("доза подкормки предшественника: "+predDoze);
                System.out.println("урожай предшественника: "+predYield);
                System.out.println("гумус: "+humus);
                System.out.println("калий: "+potassium);
                System.out.println("фосфор: "+phosphorus);
                System.out.println("\nАзот:");
                if (microelements_value.containsKey("nitrogen")){
                    System.out.println("nitrogen1: "+microelements_value.get("nitrogen")[0]);
                    System.out.println("nitrogen2: "+microelements_value.get("nitrogen")[1]);
                    System.out.println("nitrogen3: "+microelements_value.get("nitrogen")[2]);
                }
                System.out.println("\nФосфор:");
                if (microelements_value.containsKey("phosphorus")){
                    System.out.println("phosphorus1: "+microelements_value.get("phosphorus")[0]);
                    System.out.println("phosphorus2: "+microelements_value.get("phosphorus")[1]);
                    System.out.println("phosphorus3: "+microelements_value.get("phosphorus")[2]);
                }
                System.out.println("\nКалий:");
                if (microelements_value.containsKey("potassium")){
                    System.out.println("potassium1: "+microelements_value.get("potassium")[0]);
                    System.out.println("potassium2: "+microelements_value.get("potassium")[1]);
                    System.out.println("potassium3: "+microelements_value.get("potassium")[2]);
                }
                System.out.println("год использования: "+predYear);

                    /*запуск рассчетных методов*/
                    Microelement microelement_nitrogen = FertilizerCalculations.getMicroelements().get(FertilizerCalculations.MICROELEMENT_NITROGEN);
                    Microelement microelement_phosphorus = FertilizerCalculations.getMicroelements().get(FertilizerCalculations.MICROELEMENT_PHOSPHORUS);
                    Microelement microelement_potassium = FertilizerCalculations.getMicroelements().get(FertilizerCalculations.MICROELEMENT_POTASSIUM);

                     /*Расчетные данные*/
                float takeawayN = 0;
                float takeawayPh = 0;
                float takeawayP = 0;
                if(isN)
                    takeawayN = calculations.calcutationsTakeaway(Float.parseFloat(testPlanningYield), cropForCalculations, microelement_nitrogen);
                if(isPh)
                    takeawayPh = calculations.calcutationsTakeaway(Float.parseFloat(testPlanningYield), cropForCalculations, microelement_phosphorus);

                if(isP)
                    takeawayP = calculations.calcutationsTakeaway(Float.parseFloat(testPlanningYield), cropForCalculations, microelement_potassium);
                float m = calculations.calculationsM(field.getArea(), field.getTopsoilDepth(), field.getSoilBulk());//масса пахотного слоя
                float phContent = 0;
                float pContent = 0;
                float value_humus = 0;
                float value_of_nitrogen_in_humus = 0;
                float value_forms_of_nitrogen = 0;
                float  number_digestible_N = 0;
                float  number_digestible_P = 0;
                float  number_digestible_Ph = 0;
                float real_possible_harvest_N = 0;
                float real_possible_harvest_P = 0;
                float real_possible_harvest_Ph = 0;
                float absorption = 0;
                float accumulated_balance = 0;
                float accumulated_balance_in_soil = 0;
                float common_amount_of_digestible_N = 0;
                float common_amount_of_digestible_Ph = 0;
                float common_amount_of_digestible_P = 0;
                float accumulated_balance_in_leavings = 0;
                float need_in_N = 0;
                float need_in_Ph = 0;
                float need_in_P = 0;
                float additional_N = 0;
                float additional_P = 0;
                float additional_Ph = 0;
                if(isPh) {
                    phContent = calculations.microelementContent(m, phosphorus);
                    number_digestible_Ph = calculations.startCalculationsAmountOfDigestibleMicroelements(microelement_phosphorus, field, cropForCalculations, typeOfSoil1, phosphorus, microelements_value.get("phosphorus")[2]);
                    real_possible_harvest_Ph = calculations.startCalculationsRealPossibleHarvest(number_digestible_Ph, cropForCalculations, microelement_phosphorus);
                    common_amount_of_digestible_Ph = number_digestible_Ph;
                    additional_Ph = calculations.additional(common_amount_of_digestible_Ph, takeawayPh);
                    need_in_Ph = calculations.startCalculationsFertilizerDosesForYield(microelement_phosphorus, field, cropForCalculations, typeOfSoil1, Float.parseFloat(req.getParameter("planningYield")), phosphorus, predDoze, predYield, microelements_value.get("phosphorus")[2], predYear);
                }
                if(isP) {
                    pContent = calculations.microelementContent(m, potassium);
                    number_digestible_P = calculations.startCalculationsAmountOfDigestibleMicroelements(microelement_potassium, field, cropForCalculations, typeOfSoil1, potassium, microelements_value.get("potassium")[2]);
                    real_possible_harvest_P = calculations.startCalculationsRealPossibleHarvest(number_digestible_P, cropForCalculations, microelement_potassium);
                    common_amount_of_digestible_P = number_digestible_P;
                    additional_P = calculations.additional(common_amount_of_digestible_P, takeawayP);
                    need_in_P = calculations.startCalculationsFertilizerDosesForYield(microelement_potassium, field, cropForCalculations, typeOfSoil1, Float.parseFloat(req.getParameter("planningYield")), potassium, predDoze, predYield, microelements_value.get("potassium")[2], predYear);
                }
                if(isN){
                    value_humus = calculations.valueHumus(humus, m);
                    value_of_nitrogen_in_humus = calculations.valueOfNitrogenInHumus(value_humus);
                    value_forms_of_nitrogen = calculations.valueFormsOfNitrogen(value_of_nitrogen_in_humus, cropForCalculations, typeOfSoil1);
                    number_digestible_N = calculations.startCalculationsAmountOfDigestibleMicroelements(microelement_nitrogen, field, cropForCalculations, typeOfSoil1, humus, microelements_value.get("nitrogen")[2]);
                    real_possible_harvest_N = calculations.startCalculationsRealPossibleHarvest(number_digestible_N, cropForCalculations, microelement_nitrogen);
                    absorption = calculations.absorption(calculations.getAverageDigestionCoefficient(microelement_nitrogen, Integer.toString(predYear)), predDoze);
                    accumulated_balance = calculations.accumulatedBalance(predYield);
                    accumulated_balance_in_soil = calculations.accumulatedBalanceInSoil(accumulated_balance);
                    accumulated_balance_in_leavings = calculations.accumulatedBalanceInLeavings(accumulated_balance_in_soil, predYear);
                    common_amount_of_digestible_N = number_digestible_N + absorption + accumulated_balance_in_leavings;
                    additional_N = calculations.additional(common_amount_of_digestible_N, takeawayN);
                    need_in_N = calculations.startCalculationsFertilizerDosesForYield(microelement_nitrogen, field,cropForCalculations, typeOfSoil1, Float.parseFloat(req.getParameter("planningYield")), humus, predDoze, predYield, microelements_value.get("nitrogen")[2], predYear);
                }

                String coeff1_requirement = "";
                String coeff2_requirement = "";
                String coeff3_requirement = "";
                boolean c1 = Boolean.parseBoolean(req.getParameter("coeff1"));
                boolean c2 = Boolean.parseBoolean(req.getParameter("coeff2"));
                boolean c3 = Boolean.parseBoolean(req.getParameter("coeff3"));
                if(!isN) {
                    result.put("need_in_N", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
                }
                if(!isP) {
                    result.put("need_in_P", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
                }
                if(!isPh) {
                    result.put("need_in_Ph", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
                }

                if(c1)
                    coeff1_requirement = rb.getString("FERT_FORM_TABLE_SOURCE_AGR");
                else
                    coeff1_requirement = rb.getString("FERT_FORM_COEF_DEFAULT");

                if(c2)
                   coeff2_requirement =  rb.getString("FERT_FORM_TABLE_SOURCE_AGR");
                else
                   coeff2_requirement =  rb.getString("FERT_FORM_COEF_DEFAULT");

                if(c3)
                    coeff3_requirement =  rb.getString("FERT_FORM_TABLE_SOURCE_AGR");
                else
                    coeff3_requirement =  rb.getString("FERT_FORM_COEF_DEFAULT");

                /*Сохранение результатов*/
                ResultsFertilizers resultsFertilizers = new ResultsFertilizers();
                resultsFertilizers.setDate(date);
                resultsFertilizers.setResultsFertilizersName(newNameSolution);
                if(res != null)
                    resultsFertilizers.setResultMode1(res);
                resultsFertilizers.setAgricultureField(field);
                resultsFertilizers.setCrop(cropForCalculations);
                resultsFertilizers.setPalnningYield(Float.parseFloat(testPlanningYield));
                resultsFertilizers.setPred(pred);
                resultsFertilizers.setYearOfUsing(Integer.toString(predYear));
                resultsFertilizers.setAverage_h(humus);
                resultsFertilizers.setAverage_p(potassium);
                resultsFertilizers.setAverage_ph(phosphorus);
                if(isN) {
                    resultsFertilizers.setRemoval_n(microelements_value.get("nitrogen")[0]);
                    resultsFertilizers.setCoeffFromFertN(microelements_value.get("nitrogen")[1]);
                    resultsFertilizers.setCoeffFromSoilN(microelements_value.get("nitrogen")[2]);
                }
                if(isPh){
                    resultsFertilizers.setRemoval_ph(microelements_value.get("phosphorus")[0]);
                    resultsFertilizers.setCoeffFromFertPh(microelements_value.get("phosphorus")[1]);
                    resultsFertilizers.setCoeffFromSoilPh(microelements_value.get("phosphorus")[2]);
                }
                if(isP){
                    resultsFertilizers.setRemoval_p(microelements_value.get("potassium")[0]);
                    resultsFertilizers.setCoeffFromFertP(microelements_value.get("potassium")[1]);
                    resultsFertilizers.setCoeffFromSoilP(microelements_value.get("potassium")[2]);
                }
                resultsFertilizers.setSystFert(application);
                resultsFertilizers.setPredDoze(predDoze);
                resultsFertilizers.setPredYeild(predYield);
                resultsFertilizers.setTakeaway_N(takeawayN);
                resultsFertilizers.setTakeaway_P(takeawayP);
                resultsFertilizers.setTakeaway_Ph(takeawayPh);
                resultsFertilizers.setM(m);
                resultsFertilizers.setP_Content(pContent);
                resultsFertilizers.setPh_Content(phContent);
                resultsFertilizers.setValue_humus(value_humus);
                resultsFertilizers.setValue_forms_of_nitrogen(value_forms_of_nitrogen);
                resultsFertilizers.setValue_of_nitrogen_in_humus(value_of_nitrogen_in_humus);
                resultsFertilizers.setNumber_digestible_N(number_digestible_N);
                resultsFertilizers.setNumber_digestible_P(number_digestible_P);
                resultsFertilizers.setNumber_digestible_Ph(number_digestible_Ph);
                resultsFertilizers.setReal_possible_harvest_N(real_possible_harvest_N);
                resultsFertilizers.setReal_possible_harvest_P(real_possible_harvest_P);
                resultsFertilizers.setReal_possible_harvest_Ph(real_possible_harvest_Ph);
                resultsFertilizers.setAbsorption(absorption);
                resultsFertilizers.setAccumulated_balance(accumulated_balance);
                resultsFertilizers.setAccumulated_balance_in_leavings(accumulated_balance_in_leavings);
                resultsFertilizers.setAccumulated_balance_in_soil(accumulated_balance_in_soil);
                resultsFertilizers.setCommon_amount_of_digestible_N(common_amount_of_digestible_N);
                resultsFertilizers.setCommon_amount_of_digestible_P(common_amount_of_digestible_P);
                resultsFertilizers.setCommon_amount_of_digestible_Ph(common_amount_of_digestible_Ph);
                resultsFertilizers.setNeed_in_N(need_in_N);
                resultsFertilizers.setNeed_in_P(need_in_P);
                resultsFertilizers.setNeed_in_Ph(need_in_Ph);
                resultsFertilizers.setAdditional_N(additional_N);
                resultsFertilizers.setAdditional_Ph(additional_Ph);
                resultsFertilizers.setAdditional_P(additional_P);
                resultsFertilizers.setCoeff1_requirement(coeff1_requirement);
                resultsFertilizers.setCoeff2_requirement(coeff2_requirement);
                resultsFertilizers.setCoeff3_requirement(coeff3_requirement);
                resultsFertilizers.setAgrSyst(false);

                dmf.saveResultFertilizers(resultsFertilizers);

            /*Общие результаты*/
                result.put("real_possible_harvest_N", BigDecimal.valueOf(real_possible_harvest_N).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("real_possible_harvest_Ph", BigDecimal.valueOf(real_possible_harvest_Ph).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("real_possible_harvest_P", BigDecimal.valueOf(real_possible_harvest_P).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("additional_N", BigDecimal.valueOf(additional_N).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("additional_Ph", BigDecimal.valueOf(additional_Ph).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("additional_P", BigDecimal.valueOf(additional_P).setScale(2,BigDecimal.ROUND_HALF_UP));
                if(additional_N < 0)
                    result.put("need_in_N", BigDecimal.valueOf(need_in_N).setScale(2,BigDecimal.ROUND_HALF_UP));
                else if(isN)
                    result.put("need_in_N", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
                if(additional_Ph < 0)
                    result.put("need_in_Ph", BigDecimal.valueOf(need_in_Ph).setScale(2,BigDecimal.ROUND_HALF_UP));
                else if(isPh)
                    result.put("need_in_Ph", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
                if(additional_P < 0)
                    result.put("need_in_P", BigDecimal.valueOf(need_in_P).setScale(2,BigDecimal.ROUND_HALF_UP));
                else if(isP)
                    result.put("need_in_P", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));

            /*Подробные результаты*/
                result.put("takeawayN", BigDecimal.valueOf(takeawayN).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("takeawayPh", BigDecimal.valueOf(takeawayPh).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("takeawayP", BigDecimal.valueOf(takeawayP).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("m", BigDecimal.valueOf(m).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("phContent", BigDecimal.valueOf(phContent).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("pContent", BigDecimal.valueOf(pContent).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("value_humus", BigDecimal.valueOf(value_humus).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("value_of_nitrogen_in_humus", BigDecimal.valueOf(value_of_nitrogen_in_humus).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("value_forms_of_nitrogen", BigDecimal.valueOf(value_forms_of_nitrogen).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("number_digestible_N", BigDecimal.valueOf(number_digestible_N).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("number_digestible_Ph", BigDecimal.valueOf(number_digestible_Ph).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("number_digestible_P", BigDecimal.valueOf(number_digestible_P).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("absorption", BigDecimal.valueOf(absorption).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("accumulated_balance", BigDecimal.valueOf(accumulated_balance).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("accumulated_balance_in_soil", BigDecimal.valueOf(accumulated_balance_in_soil).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("accumulated_balance_in_leavings", BigDecimal.valueOf(accumulated_balance_in_leavings).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("common_amount_of_digestible_N", BigDecimal.valueOf(common_amount_of_digestible_N).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("common_amount_of_digestible_Ph", BigDecimal.valueOf(common_amount_of_digestible_Ph).setScale(2,BigDecimal.ROUND_HALF_UP));
                result.put("common_amount_of_digestible_P", BigDecimal.valueOf(common_amount_of_digestible_P).setScale(2,BigDecimal.ROUND_HALF_UP));


            }catch(NullPointerException exc){
                exc.printStackTrace();
                result.put("error-null",true);
            }catch(NumberFormatException exc1){
                exc1.printStackTrace();
                result.put("error-number",true);
            }
            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.getWriter().write(result.toString());
            return;
        }

        //req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_single_field_blocked.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
