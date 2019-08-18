package solution_fertilizers.logic;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

import auth.AuthUser;
import constants.PortalPropertiesKeys;
import constants.ServletContextKeys;
import core.data.*;
import core.data.storage.StorageManager;
import org.apache.commons.csv.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Сервлет предназначен для загрузки на сервер и отображения на карте результатов агрохимического
 * обследования почв.
 */
public class ChemicalCompositionServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger("out");

    /**
     * Если в запросе есть параметр action=getChemicalSurvey, то сервлет возвращает  в JSON-формате
     * поля хозяйства и уже загруженные результаты агрохимического обследования. В противном случае
     * он просто показывает пользователю страницу без полей и данных обследования.
     *
     * @param req  Запрос
     * @param resp Ответ
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ChemicalCompositionServlet started");

        if (req.getParameter("action")==null)
            return;
        String action = req.getParameter("action");

        /*ФОРМИРОВАНИЕ JSON СО СПИСКАМИ ФАЙЛОВ DATA*/
        if ("getChemicalSurveyFiles".equals(action)) {
            HashMap<String,String> file_url = new HashMap<>();
            HttpSession session = req.getSession();

            JSONObject respObj = new JSONObject();
            JSONArray dataJson = new JSONArray();

            AuthUser authUser = (AuthUser) session.getAttribute("authUser");
            StorageManager sm = (StorageManager) req.getServletContext().getAttribute("storageManager");

            /*получаем коллекцию с датами загрузки файлов почвообследования для земельного хозяйства*/
            Collection<AgrochemicalSoilSurvey> agr = (Collection<AgrochemicalSoilSurvey>)sm.findByField(AgrochemicalSoilSurvey.class, "landBase", authUser.getLandBase(), "==");
            ArrayList<AgrochemicalSoilSurvey> agrSorted = new ArrayList<AgrochemicalSoilSurvey>();
            for(AgrochemicalSoilSurvey as : agr)
                agrSorted.add(as);
            /*сортируем коллекцию*/
            Collections.sort(agrSorted, new Comparator<AgrochemicalSoilSurvey>() {
                public int compare(AgrochemicalSoilSurvey a, AgrochemicalSoilSurvey b) {
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'__'HH-mm-ss");
                        Date dateA = format.parse(a.getDate());
                        Date dateB = format.parse(b.getDate());
                        return dateB.compareTo(dateA);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

            /*формируем json cо списками файлов для data и methods*/
            if(agrSorted.size() > 0){
                Properties properties = (Properties) req.getServletContext().getAttribute(ServletContextKeys.PORTAL_PROPERTIES);
                String dir = properties.getProperty(PortalPropertiesKeys.CHEMICAL_COMPOSITION_FILES_DIR);
                StringBuilder fullPathBuilder = getFullPathBuilder(dir,authUser);
                String slash = System.getProperty("file.separator");
                for(AgrochemicalSoilSurvey as : agrSorted){
                    StringBuilder fullPathBuilder_agr = new StringBuilder();
                    fullPathBuilder_agr.append(fullPathBuilder);
                    fullPathBuilder_agr.append(as.getDate());
                    fullPathBuilder_agr.append(slash);

                    String fullPath = fullPathBuilder_agr.toString();
                    respObj.put("url",fullPath);
                    File folder = new File(fullPath);
                    File[] listOfFiles = folder.listFiles();
                    respObj.put("files",listOfFiles==null);
                    if (listOfFiles==null)
                        continue;
                    respObj.put("files_count",listOfFiles.length);
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            JSONObject fileJson = new JSONObject();
                            String name = file.getName()+" "+as.getDate();
                            fileJson.put("name",name);
                            file_url.put(name,file.getPath());
                            if (file.getName().contains("data"))
                                dataJson.put(fileJson);
                        }
                    }
                }
                respObj.put("data",dataJson);

                session.setAttribute("files-url",file_url);
            }else
                respObj.put("errorEmptyFiles",true);

            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(respObj.toString());
        }

        /*ФОРМИРОВАНИЕ СРЕДНИХ ЗНАЧЕНИЙ ДЛЯ МИКРОЭЛЕМЕНТОВ ИЗ ФАЙЛОВ*/
        if ("getAverageDataFromFiles".equals(action)) {
            InputStream in2 = null;
            resp.setContentType("application/json;charset=UTF-8");
            JSONObject res = null;
            try{
                String dataURL = req.getParameter("nameFileData");
                int numField = Integer.parseInt(req.getParameter("numberField"));

                HttpSession session = req.getSession();
                HashMap<String,String> file_url = (HashMap<String,String>)session.getAttribute("files-url");
                if (file_url.isEmpty())
                    throw new Exception("Ошибка. Список загруженных файлов пуст");

                if (file_url.get(dataURL)!=null){
                    in2 = new FileInputStream(file_url.get(dataURL));
                    res = parseCSVFiles(in2,numField);
                    resp.getWriter().write(res.toString());
                }else throw new Exception("Ошибка. Файл не найден");

            } catch(Exception exc){
                logger.log(Level.WARNING, exc.getMessage(), exc);
                res = new JSONObject();
                res.put("error", exc.getMessage());
                resp.getWriter().write(res.toString());
            }finally {
                if (in2 != null) in2.close();
            }
        }

    }

    /**
     * Обрабатывает загрузку на сервер файлов с методами и данными обследования. Возвращает
     * результаты обследования или текст ошибки в JSON-формате.
     *
     * @param request  Запрос
     * @param response Ответ
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ChemicalCompositionServlet started");

        Properties properties = (Properties) request.getServletContext().getAttribute(ServletContextKeys.PORTAL_PROPERTIES);
        String dir = properties.getProperty(PortalPropertiesKeys.CHEMICAL_COMPOSITION_FILES_DIR);

        String res_error = "";
        JSONObject res = new JSONObject();
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                InputStream in1 = null, in2 = null;
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (!item.isFormField() && item.getSize() > 0) {
                        if ("methods".equals(item.getFieldName())) {
                            in1 = item.getInputStream();
                        } else if ("data".equals(item.getFieldName())) {
                            in2 = item.getInputStream();
                        }
                    }
                }
                res_error = saveFiles(multiparts, dir, request);
                if (res_error!=null)
                    res.put("error-save",res_error);

                if (request.getSession().getAttribute("CurrentUserField") != null && in1 != null && in2 != null) {
                    AgricultureField field = (AgricultureField) request.getSession().getAttribute("CurrentUserField");
                    res = parseCSVFiles(in2,field.getFieldNumber());
                    if (res == null) {
                        res = new JSONObject();
                        res.put("error", "Загруженные файлы имеют неправильный формат");
                    }
                } else {
                    res.put("error", "Вы должны загрузить 2 CSV-файла");
                }

                if (in1 != null) in1.close();
                if (in2 != null) in2.close();

            } catch (Exception ex) {
                res.put("error", "При загрузке файлов произошла ошибка");
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        } else {
            res.put("error", "Sorry this Servlet only handles file upload request");
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(res.toString());
    }

    /**
     * Сохраняет загруженные файлы на сервере.
     *
     * @param multiparts Исходные файлы
     * @param baseDir    Коренвая дирректория с файлами
     */

    private String saveFiles(List<FileItem> multiparts, String baseDir, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthUser authUser = (AuthUser) session.getAttribute("authUser");
        StorageManager sm = (StorageManager) request.getServletContext().getAttribute("storageManager");
        //JSONObject res = new JSONObject();
        try {
            StringBuilder fullPathBuilder = getFullPathBuilder(baseDir, authUser);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'__'HH-mm-ss");
            fullPathBuilder.append(formatter.format(date));
            String slash = System.getProperty("file.separator");
            fullPathBuilder.append(slash);

            String fullPath = fullPathBuilder.toString();

            //HashMap<String,String> file_url = new HashMap<>();
            for (FileItem item : multiparts) {
                saveFile(item, fullPath + item.getName());
                //res.put("name",item.getFieldName());
                //res.put("url",fullPath + item.getName());
                String name = item.getName()+" "+formatter.format(date);
                /*if ("methods".equals(item.getFieldName()))
                    res.put("methods",name);
                else if ("data".equals(item.getFieldName())) {
                    res.put("data",name);
                }*/
                //file_url.put(name,fullPath + item.getName());
            }

            AgrochemicalSoilSurvey ass = new AgrochemicalSoilSurvey();
            ass.setDate(formatter.format(date));
            ass.setLandBase(authUser.getLandBase());

            sm.create(ass);
            //session.setAttribute("files-url",file_url);
            return null;

        }catch (Exception e){
            logger.log(Level.WARNING,e.getMessage(),e);
            return e.getMessage();
        }
    }

    private StringBuilder getFullPathBuilder(String baseDir, AuthUser authUser){
        Agricultures agriculture = authUser.getAgriculture();
        String slash = System.getProperty("file.separator");
        StringBuilder fullPathBuilder = new StringBuilder();
        fullPathBuilder.append(baseDir);
        if (!baseDir.endsWith(slash))
            fullPathBuilder.append(slash);
        fullPathBuilder.append(agriculture.getAgricultureId());
        fullPathBuilder.append("_");
        fullPathBuilder.append(agriculture.getName());
        fullPathBuilder.append(slash);
        return fullPathBuilder;
    }

    /**
     * Сохраняет файл на сервере.
     *
     * @param fileIn       Исходный файл
     * @param fileFullName Полный путь к файлу
     */

    private void saveFile(FileItem fileIn, String fileFullName) throws Exception {
        File fileOut = new File(fileFullName);
        fileOut.getParentFile().mkdirs();
        fileIn.write(fileOut);
    }

    /**
     * Разбирает загруженные файлы с методами и данными обследования.
     *
     * @param in2 Входной поток файла с данными обследования
     * @return Результаты обследования или null в случае ошибки
     */
    private JSONObject parseCSVFiles(InputStream in2, Integer numField) {
        CSVFormat csvFormat = CSVFormat.EXCEL.withDelimiter(';').withHeader();

        float totalPhosphorus = 0f;
        float totalPotassium = 0f;
        float totalHumus = 0f;
        int pointNumber=0;

        try {
            Reader reader = new InputStreamReader(in2);
            try {
                for (CSVRecord record : csvFormat.parse(reader)) {
                    Integer fieldNumber = Integer.parseInt(record.get("Field"));
                    if (fieldNumber==numField){
                        totalPhosphorus+=Float.parseFloat(record.get("Phosphorus").replace(',', '.'));
                        totalPotassium+=Float.parseFloat(record.get("Potassium").replace(',', '.'));
                        totalHumus+=Float.parseFloat(record.get("Humus").replace(',', '.'));
                        pointNumber++;
                    }
                }
                totalPhosphorus/=pointNumber;
                totalPotassium/=pointNumber;
                totalHumus/=pointNumber;

            } finally {
                reader.close();
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }

        JSONObject res = new JSONObject();
        res.put("phosphorus", BigDecimal.valueOf(totalPhosphorus).setScale(2,BigDecimal.ROUND_HALF_UP));
        res.put("potassium",BigDecimal.valueOf(totalPotassium).setScale(2,BigDecimal.ROUND_HALF_UP));
        res.put("humus",BigDecimal.valueOf(totalHumus).setScale(2,BigDecimal.ROUND_HALF_UP));
        return res;
    }
}
