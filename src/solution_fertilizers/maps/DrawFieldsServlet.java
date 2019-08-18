package solution_fertilizers.maps;

import auth.AuthUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.data.AgricultureField;
import core.data.Point;
import core.data.TypeOfSoil;
import core.logic.DataManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class DrawFieldsServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    System.out.println("-----------------DO GET MAP");

    DataManager dm = (DataManager)req.getServletContext().getAttribute("dataManagerMode1");
    AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");

    Collection<AgricultureField> f = dm.getAllFields(authUser.getAgriculture());
    System.out.println(f.size());
    for(AgricultureField field : f) {
      Collection<Point> points = field.getPoligon();
      for(Point p : points) {
        p.getLatitude();
        p.getLongitude();
      }
        TypeOfSoil typeOfSoil = field.getTypeOfSoil();
        System.out.println(points.size() + " soil: " + ((typeOfSoil != null)?typeOfSoil.getCodeTypeOfSoil():"null"));
    }

    Gson gson = new Gson();
    JsonObject respObj = new JsonObject();
    respObj.add("fields", gson.toJsonTree(f));
    //respObj.add("agriculture", gson.toJsonTree(authUser.getAgriculture()));
    respObj.add("agriculture_latitude", gson.toJsonTree(authUser.getAgriculture().getLatitude()));
    respObj.add("agriculture_longitude", gson.toJsonTree(authUser.getAgriculture().getLongitude()));
    gson.toJson(respObj, resp.getWriter());
  }

  private ArrayList<AgricultureField> getAllFields() {
    ArrayList<AgricultureField> fields = new ArrayList<AgricultureField>();
    AgricultureField field;
    ArrayList<Point> points;
    Point point;

    field = new AgricultureField();
    field.setAgricultureFieldId(1);
    field.setFieldNumber(1);
    field.setTypeOfSoil(new TypeOfSoil(1, "1.1", "Серые  лесные почвы водоразделов с уклоном до 2 градусов"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.28291487F);
    point.setLongitude(40.34849167F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28277194F);
    point.setLongitude(40.37861824F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.28848982F);
    point.setLongitude(40.37363912F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.28782189F);
    point.setLongitude(40.37218094F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28486822F);
    point.setLongitude(40.36960602F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.28729787F);
    point.setLongitude(40.34883499F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28653564F);
    point.setLongitude(40.34900665F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);       // FIXME: Ошибка в названии поля и метода
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(2);
    field.setFieldNumber(2);
    field.setTypeOfSoil(new TypeOfSoil(2, "1.2", "Серые лесные и слабоглееватые ложбин и эрозионно-аккомулятивные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.28286723F);
    point.setLongitude(40.34359932F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28277194F);
    point.setLongitude(40.34754753F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.28458237F);
    point.setLongitude(40.34797668F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.286488F);
    point.setLongitude(40.34780502F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28725023F);
    point.setLongitude(40.34771919F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.28696439F);
    point.setLongitude(40.3463459F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28543992F);
    point.setLongitude(40.34428596F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(3);
    field.setFieldNumber(3);
    field.setTypeOfSoil(new TypeOfSoil(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.28786952F);
    point.setLongitude(40.34900665F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28563048F);
    point.setLongitude(40.36909103F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.28825062F);
    point.setLongitude(40.37106514F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.2902037F);
    point.setLongitude(40.37578583F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28987026F);
    point.setLongitude(40.36986351F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.2893939F);
    point.setLongitude(40.36093712F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29015607F);
    point.setLongitude(40.35252571F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.28975117F);
    point.setLongitude(40.35153866F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.28953681F);
    point.setLongitude(40.34986496F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(4);
    field.setFieldNumber(4);
    field.setTypeOfSoil(new TypeOfSoil(4, "1.4", "Серые  лесные на водоразделах с уклоном до 3, в сочетании со слабосмытыми серыми лесными почвами"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29501453F);
    point.setLongitude(40.36359787F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29506215F);
    point.setLongitude(40.37269592F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29358563F);
    point.setLongitude(40.37020683F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29244248F);
    point.setLongitude(40.37080765F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28991789F);
    point.setLongitude(40.36969185F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.28948917F);
    point.setLongitude(40.36110878F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29191852F);
    point.setLongitude(40.36231041F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.29382378F);
    point.setLongitude(40.3636837F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(5);
    field.setFieldNumber(5);
    field.setTypeOfSoil(new TypeOfSoil(5, "1.5", "Комплекс среднеполугидроморфных зональных и полугидроморфных  дерновоподзолистых почв уклон 3-5 градусов и ложбин"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29582421F);
    point.setLongitude(40.34986496F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29463349F);
    point.setLongitude(40.35913467F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29325222F);
    point.setLongitude(40.36110878F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29306169F);
    point.setLongitude(40.36179543F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.2902037F);
    point.setLongitude(40.3603363F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29148982F);
    point.setLongitude(40.34849167F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29351419F);
    point.setLongitude(40.34977913F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(6);
    field.setFieldNumber(6);
    field.setTypeOfSoil(new TypeOfSoil(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30182485F);
    point.setLongitude(40.34317017F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30077719F);
    point.setLongitude(40.34093857F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29801503F);
    point.setLongitude(40.34085274F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29725302F);
    point.setLongitude(40.34222603F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.29544318F);
    point.setLongitude(40.34394264F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29048951F);
    point.setLongitude(40.34548759F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28953681F);
    point.setLongitude(40.34694672F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.28810771F);
    point.setLongitude(40.34763336F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.28934627F);
    point.setLongitude(40.34909248F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.29077532F);
    point.setLongitude(40.34823418F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(11);
    point.setLatitude(56.29168036F);
    point.setLongitude(40.34729004F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(12);
    point.setLatitude(56.29229958F);
    point.setLongitude(40.34737587F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(13);
    point.setLatitude(56.29625286F);
    point.setLongitude(40.34737587F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(7);
    field.setFieldNumber(7);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30177723F);
    point.setLongitude(40.34317017F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30053908F);
    point.setLongitude(40.35106659F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30039621F);
    point.setLongitude(40.35535812F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.2981579F);
    point.setLongitude(40.35801888F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.29577658F);
    point.setLongitude(40.35844803F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29653862F);
    point.setLongitude(40.34832001F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(8);
    field.setFieldNumber(8);
    field.setTypeOfSoil(new TypeOfSoil(8, "1.8", "Дерново-глеевые и гидроморфные депрессий(БИГ, БНТГ)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30206295F);
    point.setLongitude(40.37887573F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29430009F);
    point.setLongitude(40.37793159F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29544318F);
    point.setLongitude(40.37424088F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29772928F);
    point.setLongitude(40.37243843F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.29906277F);
    point.setLongitude(40.37037849F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.30011048F);
    point.setLongitude(40.37286758F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(9);
    field.setFieldNumber(9);
    field.setTypeOfSoil(new TypeOfSoil(9, "1.9", "Дерновые и серые лесные глееватые и глеевые почвы оврагов и балок"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30046764F);
    point.setLongitude(40.36960602F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29961044F);
    point.setLongitude(40.36982059F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30251534F);
    point.setLongitude(40.37733078F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30349153F);
    point.setLongitude(40.37664413F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(10);
    field.setFieldNumber(10);
    field.setTypeOfSoil(new TypeOfSoil(1, "1.1", "Серые  лесные почвы водоразделов с уклоном до 2 градусов"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30530098F);
    point.setLongitude(40.34334183F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30220581F);
    point.setLongitude(40.34282684F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30201533F);
    point.setLongitude(40.34660339F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30111054F);
    point.setLongitude(40.35089493F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.30096767F);
    point.setLongitude(40.35415649F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.30201533F);
    point.setLongitude(40.3572464F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.30358676F);
    point.setLongitude(40.36179543F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.303682F);
    point.setLongitude(40.36325455F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.3029201F);
    point.setLongitude(40.36437035F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.30134864F);
    point.setLongitude(40.36904812F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(11);
    point.setLatitude(56.30358676F);
    point.setLongitude(40.37527084F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(12);
    point.setLatitude(56.3053486F);
    point.setLongitude(40.37810326F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(11);
    field.setFieldNumber(11);
    field.setTypeOfSoil(new TypeOfSoil(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.3076341F);
    point.setLongitude(40.37836075F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.3053486F);
    point.setLongitude(40.37827492F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30525337F);
    point.setLongitude(40.34334183F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30782455F);
    point.setLongitude(40.34420013F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(12);
    field.setFieldNumber(12);
    field.setTypeOfSoil(new TypeOfSoil(5, "1.5", "Комплекс среднеполугидроморфных зональных и полугидроморфных  дерновоподзолистых почв уклон 3-5 градусов и ложбин"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31072883F);
    point.setLongitude(40.37836075F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30768171F);
    point.setLongitude(40.37836075F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30777694F);
    point.setLongitude(40.34402847F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31072883F);
    point.setLongitude(40.34420013F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(13);
    field.setFieldNumber(13);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31077644F);
    point.setLongitude(40.34428596F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31363289F);
    point.setLongitude(40.34531593F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31396612F);
    point.setLongitude(40.37827492F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31077644F);
    point.setLongitude(40.37827492F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(14);
    field.setFieldNumber(14);
    field.setTypeOfSoil(new TypeOfSoil(8, "1.8", "Дерново-глеевые и гидроморфные депрессий(БИГ, БНТГ)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31706034F);
    point.setLongitude(40.34042358F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31567988F);
    point.setLongitude(40.34445763F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31368049F);
    point.setLongitude(40.3452301F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31396612F);
    point.setLongitude(40.37810326F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31791716F);
    point.setLongitude(40.37810326F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(15);
    field.setFieldNumber(15);
    field.setTypeOfSoil(new TypeOfSoil(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31796476F);
    point.setLongitude(40.37801743F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.32086826F);
    point.setLongitude(40.37793159F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.3202971F);
    point.setLongitude(40.3530407F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31896435F);
    point.setLongitude(40.34711838F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31867876F);
    point.setLongitude(40.33913612F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.32086826F);
    point.setLongitude(40.3357029F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.31896435F);
    point.setLongitude(40.33518791F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.31710794F);
    point.setLongitude(40.34033775F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(16);
    field.setFieldNumber(16);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.32053509F);
    point.setLongitude(40.33098221F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.32132043F);
    point.setLongitude(40.33214092F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.32086826F);
    point.setLongitude(40.33411503F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31979732F);
    point.setLongitude(40.33420086F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31886915F);
    point.setLongitude(40.33390045F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.31791716F);
    point.setLongitude(40.32849312F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(17);
    field.setFieldNumber(17);
    field.setTypeOfSoil(new TypeOfSoil(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31734595F);
    point.setLongitude(40.32797813F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31839316F);
    point.setLongitude(40.33501625F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31510863F);
    point.setLongitude(40.34377098F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31263315F);
    point.setLongitude(40.34342766F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31439457F);
    point.setLongitude(40.32909393F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(18);
    field.setFieldNumber(18);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31263315F);
    point.setLongitude(40.34351349F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30968141F);
    point.setLongitude(40.34291267F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31068122F);
    point.setLongitude(40.33106804F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31434696F);
    point.setLongitude(40.32909393F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(19);
    field.setFieldNumber(19);
    field.setTypeOfSoil(new TypeOfSoil(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31072883F);
    point.setLongitude(40.33098221F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30653898F);
    point.setLongitude(40.33321381F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30672944F);
    point.setLongitude(40.34256935F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.3096338F);
    point.setLongitude(40.34291267F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(20);
    field.setFieldNumber(20);
    field.setTypeOfSoil(new TypeOfSoil(2, "1.2", "Серые лесные и слабоглееватые ложбин и эрозионно-аккомулятивные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30658659F);
    point.setLongitude(40.33321381F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30334867F);
    point.setLongitude(40.33278465F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30401533F);
    point.setLongitude(40.34231186F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30672944F);
    point.setLongitude(40.34265518F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(21);
    field.setFieldNumber(21);
    field.setTypeOfSoil(new TypeOfSoil(4, "1.4", "Серые  лесные на водоразделах с уклоном до 3, в сочетании со слабосмытыми серыми лесными почвами"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30396771F);
    point.setLongitude(40.34231186F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30230105F);
    point.setLongitude(40.34179688F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30237248F);
    point.setLongitude(40.34063816F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30172961F);
    point.setLongitude(40.33922195F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.30072957F);
    point.setLongitude(40.33819199F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.30072957F);
    point.setLongitude(40.3368187F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29987236F);
    point.setLongitude(40.33244133F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.30339629F);
    point.setLongitude(40.33287048F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(22);
    field.setFieldNumber(22);
    field.setTypeOfSoil(new TypeOfSoil(9, "1.9", "Дерновые и серые лесные глееватые и глеевые почвы оврагов и балок"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29901514F);
    point.setLongitude(40.33269882F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29987236F);
    point.setLongitude(40.33733368F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.3001581F);
    point.setLongitude(40.33922195F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.2977769F);
    point.setLongitude(40.33939362F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.29539555F);
    point.setLongitude(40.34282684F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29025134F);
    point.setLongitude(40.34420013F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28777425F);
    point.setLongitude(40.34694672F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.28710731F);
    point.setLongitude(40.34385681F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.28882227F);
    point.setLongitude(40.33939362F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.28920336F);
    point.setLongitude(40.33475876F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(23);
    field.setFieldNumber(23);
    field.setTypeOfSoil(new TypeOfSoil(1, "1.1", "Серые  лесные почвы водоразделов с уклоном до 2 градусов"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.28243843F);
    point.setLongitude(40.30231476F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28334367F);
    point.setLongitude(40.3203392F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.28260518F);
    point.setLongitude(40.33081055F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.28262901F);
    point.setLongitude(40.34196854F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28605924F);
    point.setLongitude(40.34248352F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.2879648F);
    point.setLongitude(40.33802032F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28739314F);
    point.setLongitude(40.3323555F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.28710731F);
    point.setLongitude(40.32943726F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.28644036F);
    point.setLongitude(40.32342911F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.28682148F);
    point.setLongitude(40.3127861F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(11);
    point.setLatitude(56.285297F);
    point.setLongitude(40.30609131F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(12);
    point.setLatitude(56.28329602F);
    point.setLongitude(40.30231476F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(24);
    field.setFieldNumber(24);
    field.setTypeOfSoil(new TypeOfSoil(5, "1.5", "Комплекс среднеполугидроморфных зональных и полугидроморфных  дерновоподзолистых почв уклон 3-5 градусов и ложбин"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29015607F);
    point.setLongitude(40.31536102F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28748842F);
    point.setLongitude(40.32008171F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.2873455F);
    point.setLongitude(40.32239914F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.28806007F);
    point.setLongitude(40.32866478F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28801244F);
    point.setLongitude(40.33158302F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.28844117F);
    point.setLongitude(40.33329964F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29268064F);
    point.setLongitude(40.33226967F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(25);
    field.setFieldNumber(25);
    field.setTypeOfSoil(new TypeOfSoil(8, "1.8", "Дерново-глеевые и гидроморфные депрессий(БИГ, БНТГ)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.28453473F);
    point.setLongitude(40.30128479F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.28539228F);
    point.setLongitude(40.3030014F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.28663092F);
    point.setLongitude(40.30669212F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.28772661F);
    point.setLongitude(40.31201363F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.28777425F);
    point.setLongitude(40.31518936F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.28725023F);
    point.setLongitude(40.31862259F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.28996553F);
    point.setLongitude(40.31441689F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.29134692F);
    point.setLongitude(40.30943871F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.29249011F);
    point.setLongitude(40.30780792F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.29125166F);
    point.setLongitude(40.30162811F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(26);
    field.setFieldNumber(26);
    field.setTypeOfSoil(new TypeOfSoil(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29139456F);
    point.setLongitude(40.30171394F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29534793F);
    point.setLongitude(40.30042648F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29539555F);
    point.setLongitude(40.30308723F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29244248F);
    point.setLongitude(40.30797958F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(27);
    field.setFieldNumber(27);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29565751F);
    point.setLongitude(40.30364513F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29589565F);
    point.setLongitude(40.30544758F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29641955F);
    point.setLongitude(40.3057909F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29651481F);
    point.setLongitude(40.30776501F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.2929188F);
    point.setLongitude(40.31673431F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29465731F);
    point.setLongitude(40.3317976F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.29268064F);
    point.setLongitude(40.33222675F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.29017989F);
    point.setLongitude(40.31540394F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(28);
    field.setFieldNumber(28);
    field.setTypeOfSoil(new TypeOfSoil(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29580039F);
    point.setLongitude(40.31806469F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29699107F);
    point.setLongitude(40.33132553F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29463349F);
    point.setLongitude(40.33175468F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29349037F);
    point.setLongitude(40.32141209F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(29);
    field.setFieldNumber(29);

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30022953F);
    point.setLongitude(40.30394554F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29889608F);
    point.setLongitude(40.30330181F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29715777F);
    point.setLongitude(40.30570507F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29694345F);
    point.setLongitude(40.30797958F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.29306169F);
    point.setLongitude(40.3178072F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.29346656F);
    point.setLongitude(40.32141209F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.30032478F);
    point.setLongitude(40.31145573F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(30);
    field.setFieldNumber(30);
    field.setTypeOfSoil(new TypeOfSoil(2, "1.2", "Серые лесные и слабоглееватые ложбин и эрозионно-аккомулятивные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29699107F);
    point.setLongitude(40.33132553F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.29896752F);
    point.setLongitude(40.33128262F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.29818172F);
    point.setLongitude(40.31463146F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.29582421F);
    point.setLongitude(40.31802177F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(31);
    field.setFieldNumber(31);
    field.setTypeOfSoil(new TypeOfSoil(4, "1.4", "Серые  лесные на водоразделах с уклоном до 3, в сочетании со слабосмытыми серыми лесными почвами"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.29901514F);
    point.setLongitude(40.3312397F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30077719F);
    point.setLongitude(40.33106804F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30025334F);
    point.setLongitude(40.3116703F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.2981579F);
    point.setLongitude(40.31458855F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(32);
    field.setFieldNumber(32);
    field.setTypeOfSoil(new TypeOfSoil(9, "1.9", "Дерновые и серые лесные глееватые и глеевые почвы оврагов и балок"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30215819F);
    point.setLongitude(40.29888153F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.3012415F);
    point.setLongitude(40.29909611F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30020572F);
    point.setLongitude(40.30377388F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30072957F);
    point.setLongitude(40.33115387F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.30444388F);
    point.setLongitude(40.33184052F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(33);
    field.setFieldNumber(33);
    field.setTypeOfSoil(new TypeOfSoil(1, "1.1", "Серые  лесные почвы водоразделов с уклоном до 2 градусов"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.302182F);
    point.setLongitude(40.29866695F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30439627F);
    point.setLongitude(40.29836655F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.3062771F);
    point.setLongitude(40.33141136F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30446769F);
    point.setLongitude(40.33171177F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(34);
    field.setFieldNumber(34);
    field.setTypeOfSoil(new TypeOfSoil(5, "1.5", "Комплекс среднеполугидроморфных зональных и полугидроморфных  дерновоподзолистых почв уклон 3-5 градусов и ложбин"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30444388F);
    point.setLongitude(40.29828072F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.30625329F);
    point.setLongitude(40.29810905F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30841971F);
    point.setLongitude(40.33102512F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30677705F);
    point.setLongitude(40.33218384F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.30625329F);
    point.setLongitude(40.33141136F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(35);
    field.setFieldNumber(35);
    field.setTypeOfSoil(new TypeOfSoil(8, "1.8", "Дерново-глеевые и гидроморфные депрессий(БИГ, БНТГ)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30849113F);
    point.setLongitude(40.33115387F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31049078F);
    point.setLongitude(40.33029556F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.30891963F);
    point.setLongitude(40.29849529F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.30634852F);
    point.setLongitude(40.29828072F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(36);
    field.setFieldNumber(36);
    field.setTypeOfSoil(new TypeOfSoil(3, "1.3", "Комплекс серых лесных слабоглееватых и глееватых почв ложбин (до 10 градусов)"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.30896724F);
    point.setLongitude(40.29836655F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31082405F);
    point.setLongitude(40.29853821F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31272837F);
    point.setLongitude(40.32883644F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31044317F);
    point.setLongitude(40.33020973F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(37);
    field.setFieldNumber(37);
    field.setTypeOfSoil(new TypeOfSoil(7, "1.7", "Комплексы пойменные и пойменные глееватые 10-30%"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31327584F);
    point.setLongitude(40.29828072F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31082405F);
    point.setLongitude(40.29845238F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31277597F);
    point.setLongitude(40.32875061F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31439457F);
    point.setLongitude(40.32772064F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(38);
    field.setFieldNumber(38);
    field.setTypeOfSoil(new TypeOfSoil(6, "1.6", "Пойменные глеевые и пойменные болотные почвенные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31329964F);
    point.setLongitude(40.29836655F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31444217F);
    point.setLongitude(40.32780647F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31720315F);
    point.setLongitude(40.32686234F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31587029F);
    point.setLongitude(40.32231331F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31506103F);
    point.setLongitude(40.3119278F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.31701274F);
    point.setLongitude(40.305233F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.31786956F);
    point.setLongitude(40.29973984F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.31844076F);
    point.setLongitude(40.29802322F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(39);
    field.setFieldNumber(39);
    field.setTypeOfSoil(new TypeOfSoil(2, "1.2", "Серые лесные и слабоглееватые ложбин и эрозионно-аккомулятивные"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31924994F);
    point.setLongitude(40.29810905F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.31853596F);
    point.setLongitude(40.29939651F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.31772676F);
    point.setLongitude(40.30557632F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31667953F);
    point.setLongitude(40.30883789F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(5);
    point.setLatitude(56.31577508F);
    point.setLongitude(40.31252861F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(6);
    point.setLatitude(56.31582268F);
    point.setLongitude(40.31518936F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(7);
    point.setLatitude(56.31639392F);
    point.setLongitude(40.32111168F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(8);
    point.setLatitude(56.31767915F);
    point.setLongitude(40.32694817F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(9);
    point.setLatitude(56.31915475F);
    point.setLongitude(40.32883644F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(10);
    point.setLatitude(56.31991632F);
    point.setLongitude(40.32926559F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);
//--------------------------------------------------------------------------------------------------
    field = new AgricultureField();
    field.setAgricultureFieldId(40);
    field.setFieldNumber(40);
    field.setTypeOfSoil(new TypeOfSoil(4, "1.4", "Серые  лесные на водоразделах с уклоном до 3, в сочетании со слабосмытыми серыми лесными почвами"));

    points = new ArrayList<Point>();

    point = new Point();
    point.setNumber(1);
    point.setLatitude(56.31929754F);
    point.setLongitude(40.29810905F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(2);
    point.setLatitude(56.32058268F);
    point.setLongitude(40.29853821F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(3);
    point.setLatitude(56.32096346F);
    point.setLongitude(40.32943726F);
    point.setField(field);
    points.add(point);

    point = new Point();
    point.setNumber(4);
    point.setLatitude(56.31986872F);
    point.setLongitude(40.32909393F);
    point.setField(field);
    points.add(point);

    field.setPoligon(points);
    fields.add(field);

    return fields;
  }
}
