package solution_fertilizers;

import core.data.LandBase;
import core.data.*;
import core.logic.DataManagerMode1;
import core.logic.DataManagerModeFertilizers;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

public class SolutionFertilizersResultsModeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Сервлет SolutionFertilizersResultsModeServlet запущен");
        Locale l = new Locale("ru");
        ResourceBundle rb = ResourceBundle.getBundle("Resource", l);

        DataManagerModeFertilizers dmf = (DataManagerModeFertilizers) req.getServletContext().getAttribute("dataManagerModeFertilizers");

        /*Получаем активную земельную базу хозяйства*/
        LandBase landBase = (LandBase)req.getSession().getAttribute("landBase");
        if(landBase == null) {
            req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_results_mode.jsp").forward(req, resp);
            return;
        }

        Collection<ResultsFertilizers> results = dmf.findAllResultsFertilizers();
        String[] checkedRes = req.getParameterValues("checkResult");
        if(checkedRes != null) {
            ArrayList<ResultsFertilizers> resultsToDelete = new ArrayList<ResultsFertilizers>();
            for(int i = 0; i < checkedRes.length; i++){
                for(ResultsFertilizers result1 : results){
                    if(result1.getResultsFertilizersId() == Integer.parseInt(checkedRes[i])) {
                        System.out.println("будет удалено: " + checkedRes[i]);
                        resultsToDelete.add(result1);
                    }
                }
            }
            dmf.deleteResultsFertilizers(resultsToDelete);
            results =  dmf.findAllResultsFertilizers();
        }

        /*Все сохраненные результаты для текущего хозяйства отправляем для отображения в сводку*/
        req.setAttribute("results", results);
        req.setAttribute("typeResult",req.getParameter("typeResult"));

        /*Обрабатываем выбор конкретного результата в сводке*/
        String idResult = req.getParameter("idResult");
        if(idResult != null) {
            System.out.println("Выбран результат с id = " + idResult);
            ResultsFertilizers result = dmf.getResultsFertilizers(results, idResult);
            System.out.println("!!"+result.getResultsFertilizersName());
            /*Отправляем данные для отображения*/
            JSONObject res = new JSONObject();

            /*Исходные данные сверху таблицы*/
            res.put("newNameSolution", rb.getString("FERT_TABLE_RES")+" "+result.getResultsFertilizersName());
            if(result.getResultMode1() != null)
                res.put("agrSyst", result.getResultMode1().getNameResult());
            else
                res.put("agrSyst", rb.getString("FERT_TABLE_AGR_SYST_ISNT_NEEDED"));
            res.put("field", rb.getString("FERT_FORM_SETT_FIELD_NUMBER")+" "+result.getAgricultureField().getFieldNumber());
            res.put("area", BigDecimal.valueOf(result.getAgricultureField().getArea()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("typeOfSoil", result.getAgricultureField().getTypeOfSoil().getCodeTypeOfSoil()+" "+result.getAgricultureField().getTypeOfSoil().getNameTypeSoil());
            res.put("depth",  BigDecimal.valueOf(result.getAgricultureField().getTopsoilDepth()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("bulk",  BigDecimal.valueOf(result.getAgricultureField().getSoilBulk()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("crop", result.getCrop().getNameCrop());
            res.put("planningYield", BigDecimal.valueOf(result.getPalnningYield()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("pred", result.getPred().getCrop().getNameCrop());
            res.put("systFert", result.getSystFert().getCodeApplicationOfFertilizers()+" "+result.getSystFert().getDescriptionApplicationOfFertilizers());
            res.put("predDoze", result.getPredDoze());
            res.put("predYield", result.getPredYeild());
            res.put("humus", BigDecimal.valueOf(result.getAverage_h()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("phosphorus", BigDecimal.valueOf(result.getAverage_ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("potassium",BigDecimal.valueOf( result.getAverage_p()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("nitrogen1", BigDecimal.valueOf(result.getRemoval_n()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("phosphorus1", BigDecimal.valueOf(result.getRemoval_ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("potassium1", BigDecimal.valueOf(result.getRemoval_p()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("coeff1Info", result.getCoeff1_requirement());

            res.put("nitrogen2", BigDecimal.valueOf(result.getCoeffFromFertN()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("potassium2", BigDecimal.valueOf(result.getCoeffFromFertP()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("phosphorus2", BigDecimal.valueOf(result.getCoeffFromFertPh()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("yearOfUsing", rb.getString("FERT_FORM_SYS_FERT_YEAR")+" "+result.getYearOfUsing());
            res.put("coeff2Info", result.getCoeff2_requirement());

            res.put("nitrogen3", BigDecimal.valueOf(result.getCoeffFromSoilN()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("potassium3", BigDecimal.valueOf(result.getCoeffFromSoilP()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("phosphorus3", BigDecimal.valueOf(result.getCoeffFromSoilPh()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("coeff3Info", result.getCoeff3_requirement());

            /*Общие результаты*/
            res.put("real_possible_harvest_N", BigDecimal.valueOf(result.getReal_possible_harvest_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("real_possible_harvest_Ph", BigDecimal.valueOf(result.getReal_possible_harvest_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("real_possible_harvest_P", BigDecimal.valueOf(result.getReal_possible_harvest_P()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("additional_N", BigDecimal.valueOf(result.getAdditional_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("additional_Ph", BigDecimal.valueOf(result.getAdditional_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("additional_P", BigDecimal.valueOf(result.getAdditional_P()).setScale(2,BigDecimal.ROUND_HALF_UP));
            if(result.getAdditional_N() < 0)
                res.put("need_in_N", BigDecimal.valueOf(result.getNeed_in_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            else
                res.put("need_in_N", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
            if(result.getAdditional_Ph() < 0)
                res.put("need_in_Ph", BigDecimal.valueOf(result.getNeed_in_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            else
                res.put("need_in_Ph", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));
            if(result.getAdditional_P() < 0)
                res.put("need_in_P", BigDecimal.valueOf(result.getNeed_in_P()).setScale(2,BigDecimal.ROUND_HALF_UP));
            else
                res.put("need_in_P", rb.getString("FERT_TABLE_MICROELEMENT_ISNT_NEEDED"));


              /*Подробные результаты*/
            res.put("takeawayN", BigDecimal.valueOf(result.getTakeaway_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("takeawayPh", BigDecimal.valueOf(result.getTakeaway_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("takeawayP", BigDecimal.valueOf(result.getTakeaway_P()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("m", BigDecimal.valueOf(result.getM()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("phContent", BigDecimal.valueOf(result.getPh_Content()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("pContent", BigDecimal.valueOf(result.getP_Content()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("value_humus", BigDecimal.valueOf(result.getValue_humus()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("value_of_nitrogen_in_humus", BigDecimal.valueOf(result.getValue_of_nitrogen_in_humus()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("value_forms_of_nitrogen", BigDecimal.valueOf(result.getValue_forms_of_nitrogen()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("number_digestible_N", BigDecimal.valueOf(result.getNumber_digestible_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("number_digestible_Ph", BigDecimal.valueOf(result.getNumber_digestible_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("number_digestible_P", BigDecimal.valueOf(result.getNumber_digestible_P()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("absorption", BigDecimal.valueOf(result.getAbsorption()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("accumulated_balance", BigDecimal.valueOf(result.getAccumulated_balance()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("accumulated_balance_in_soil", BigDecimal.valueOf(result.getAccumulated_balance_in_soil()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("accumulated_balance_in_leavings", BigDecimal.valueOf(result.getAccumulated_balance_in_leavings()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("common_amount_of_digestible_N", BigDecimal.valueOf(result.getCommon_amount_of_digestible_N()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("common_amount_of_digestible_Ph", BigDecimal.valueOf(result.getCommon_amount_of_digestible_Ph()).setScale(2,BigDecimal.ROUND_HALF_UP));
            res.put("common_amount_of_digestible_P", BigDecimal.valueOf(result.getCommon_amount_of_digestible_P()).setScale(2,BigDecimal.ROUND_HALF_UP));

//            req.setAttribute("result1", result);
//            req.getRequestDispatcher("/planning_formation/solution_fertilizers_table_all_results.jsp").forward(req, resp);
            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.getWriter().write(res.toString());
            return;
        }

        req.getRequestDispatcher("/solution_fertilizers/solution_fertilizers_results_mode.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
