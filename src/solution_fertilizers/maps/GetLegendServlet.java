package solution_fertilizers.maps;

import core.data.AgricultureField;
import core.data.Crop;
import core.data.LandBase;
import core.logic.DataManagerMode1;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GetLegendServlet extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Сервлет GetLegendServlet запущен");
        DataManagerMode1 dm = (DataManagerMode1) req.getServletContext().getAttribute("dataManagerMode1");
        /*Получаем активную земельную базу хозяйства*/
        LandBase landBase = (LandBase) req.getSession().getAttribute("landBase");
        if(landBase == null){
            return;
        }
        if (req.getParameter("id_legend") != null) {
            Collection<AgricultureField> r_fields = dm.getAllFields(landBase);
            Collection<Crop> r_crops = dm.getAllCrops();
            Map<String, String> allColorFields = new HashMap<String, String>();
            for (AgricultureField r : r_fields) {
                allColorFields.put(r.getTypeOfSoil().getNameTypeSoil(), r.getTypeOfSoil().getColor());
            }
            Map<String, String> allColorCrops = new HashMap<String, String>();
            Map<String, String> allPatternCrops = new HashMap<String, String>();
            for (Crop r : r_crops) {
                allColorCrops.put(r.getNameCrop(), r.getColor());
                allPatternCrops.put(r.getNameCrop(), r.getPattern());
            }
            JSONObject result = new JSONObject();
            result.put("allColorFields", allColorFields);
            result.put("allColorCrops", allColorCrops);
            result.put("allPatternCrops", allPatternCrops);
            if (req.getParameter("id_legend").equals("2")) {
                //Хардкод интенсификации
                Map<String, String> all_intensification = new HashMap<String, String>();
                all_intensification.put("Экстенсивный", "#00CC00");
                all_intensification.put("Нормальный", "#FFE100");
                all_intensification.put("Интенсивный", "#F5001D");
                result.put("all_intensification", all_intensification);
            }
            resp.setContentType("application/json");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.getWriter().write(result.toString());
        }
    }
}
