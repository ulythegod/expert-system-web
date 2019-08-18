package solution_fertilizers.maps;

import auth.AuthUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.data.TypeOfSoil;
import core.logic.DataManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Сервлет предназначен для генерирования JavaScript-программы, которая делает доступной
 * в JavaScript-кодe такую справочную информацию о типах почв
 */
public class GetListTypeSoilServlet extends HttpServlet {
  private class GroundType {
    int id;
    String code;
    String name;
    String color;
    public GroundType(int id, String code, String name, String color) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.color = color;
    }
  }


  /**
   * Генерирует JavaScript-программу, добавляющую справочную информацию в пространство имен ref.
   *
   * @param req   Запрос
   * @param resp  Ответ
   */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
    DataManager dm = (DataManager)req.getServletContext().getAttribute("dataManagerMode1");
    Collection<TypeOfSoil> typeOfSoils = dm.getAllTypeSoils();

    StringBuilder sb = new StringBuilder(10000);
    Gson gson = new GsonBuilder().create();
    // Создаем новое пространство имен под справочники
    sb.append("window.refs = window.refs || {};\n\n");
    // Начало замыкания
    sb.append("(function() {\n");
    // Генерируем справочник типов почв
    ArrayList<GroundType> groundTypes = new ArrayList<GroundType>();
    for(TypeOfSoil t : typeOfSoils) {
      int id = t.getTypeOfSoilId();
      String code = t.getCodeTypeOfSoil();
      String name = t.getNameTypeSoil();
      String color = t.getColor();
      groundTypes.add(new GroundType(id, code, name, color));
    }
    /*
    groundTypes.add(new GroundType(1, "1.1", "Серые  лесные почвы водоразделов с уклоном до 2 градусов", "rgb(255,0,0)"));
    groundTypes.add(new GroundType(2, "1.2", "Серые лесные и слабоглееватые ложбин и эрозионно-аккомулятивные", "rgb(255,153,0)"));
    groundTypes.add(new GroundType(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)", "rgb(153,204,0)"));
    groundTypes.add(new GroundType(4, "1.4", "Серые  лесные на водоразделах с уклоном до 3, в сочетании со слабосмытыми серыми лесными почвами", "rgb(51,153,102)"));
    groundTypes.add(new GroundType(5, "1.5", "Комплекс среднеполугидроморфных зональных и полугидроморфных  дерновоподзолистых почв уклон 3-5 градусов и ложбин", "rgb(51,204,204)"));
    groundTypes.add(new GroundType(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные", "rgb(51,102,255)"));
    groundTypes.add(new GroundType(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%", "rgb(128,0,128)"));
    groundTypes.add(new GroundType(8, "1.8", "Дерново-глеевые и гидроморфные депрессий(БИГ, БНТГ)", "rgb(255,0,255)"));
    groundTypes.add(new GroundType(9, "1.9", "Дерновые и серые лесные глееватые и глеевые почвы оврагов и балок", "rgb(0,255,0)"));
*/
    sb.append("var groundTypes = ").append(gson.toJson(groundTypes)).append(";\n\n");
    sb.append("groundTypes.lookupId = ").append(getLookupIdFunction("groundTypes", "id"));

    // Экспортируем справочники в пространство имен refs
    sb.append("window.refs.GROUND_TYPES = groundTypes;\n");
    // Конец замыкания
    sb.append("})();\n");
    // Устанавливаем тип и кодировку ответа
    resp.setContentType("application/javascript; charset=UTF-8");
    // Выводим результаты
    resp.getWriter().println(sb);
  }

  /**
   * Возвращает код JavaScript-функции, которая позволяет в массиве объектов искать
   * данные по ключу.
   *
   * @param varName   Имя переменной-массива объектов
   * @param key       Ключ для поиска
   * @return          Код JavaScript-функции
   */
  private String getLookupIdFunction(String varName, String key) {
    StringBuilder sb = new StringBuilder(500);

    sb.append("(function(arr, key) { \n");
    sb.append("  var lookupMap = {}; \n");
    sb.append("  for (var i = 0; i < arr.length; i++) { \n");
    sb.append("    lookupMap[ arr[i][key] ] = arr[i]; \n");
    sb.append("  } \n");
    sb.append("  return function(id) { \n");
    sb.append("    return lookupMap[id]; \n");
    sb.append("  } \n");
    sb.append("})(").append(varName).append(",'").append(key).append("'); \n\n");

    return sb.toString();
  }
}
