package auth;

import constants.PortalPropertiesKeys;
import constants.ServletContextKeys;
import core.data.*;
import core.data.storage.StorageManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Класс AuthUserServlet реализует интерфейс HttpServlet и предназначен для обработки запросов
 * к логинной форме.
 * <p>
 * Хранение всех пар логин-пароль реализовано с помощью текстового файла /WEB-INF/users.properties
 * следующей структуры:
 * <pre>
 * {@code
 * users.user1.login=login
 * users.user1.password=password
 * users.user2.login=login
 * users.user2.password=password
 * ...
 * users.userN.login=login
 * users.userN.password=password
 * }</pre>
 * <p>
 * Логины должны быть уникальными. Пары записей, в которых хотя бы одно значение отсутствует,
 * пропускаются. Этот файл загружается один раз при инициализации сервлета, но может и
 * отсутствовать, тогда в журнал работы приложения будет добавлено соответствующее предупреждение.
 */
public class AuthUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger("out");
    private HashMap<String, String> users = new HashMap<String, String>();
    private String redirectAfterLogin;

    /**
     * Считывает из настроек сервлета параметр redirectAfterLogin и загружает список пользователей.
     *
     * @param config Настройки сервлета
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        redirectAfterLogin =  config.getInitParameter("redirectAfterLogin");
        if (redirectAfterLogin == null) {
            redirectAfterLogin = "/";
        }
        loadUsers();
    }

    /**
     * Показывает пользователю логинную форму.
     *
     * @param req   Запрос
     * @param resp  Ответ
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if(req.getParameter("logout") != null)
            req.getSession().invalidate();
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    /**
     * Получает данные с логинной формы и проверяет их. Если пользователь успешно аутентифицирован,
     * то перенаправляет его на страницу, указанную в параметре redirectAfterLogin настроек
     * сервлета. В противном случае возвращает пользователя на страницу с логинной формой и
     * описанием ошибки.
     *
     * @param req   Запрос
     * @param resp  Ответ
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        List<String> loginErrors = new ArrayList<String>();

        if (allowUser(login, password, loginErrors)) {
            StorageManager sm = (StorageManager) req.getServletContext().getAttribute("storageManager");
            List<User> user = (List)sm.findByField(User.class, "login", login, "==");
            HttpSession session = req.getSession();
            AuthUser authUser = new AuthUser(user.get(0).getUserId(), login, user.get(0).getName(), user.get(0).getRole(), session.getId(), user.get(0).getAgriculture());
            session.setAttribute("authUser", authUser);

            /*Поиск активной земельной базы (последняя по дате создания)*/
            LandBase landBase = null;
            Collection<LandBase> landBases = authUser.getAgriculture().getLandBases();
            ArrayList<LandBase> landBasesSorted = new ArrayList<LandBase>();
            for(LandBase lb : landBases)
                landBasesSorted.add(lb);
            Collections.sort(landBasesSorted, new Comparator<LandBase>() {
                public int compare(LandBase a, LandBase b) {
                    try {
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date dateA = format.parse(a.getDate());
                        Date dateB = format.parse(b.getDate());
                        return dateB.compareTo(dateA);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            if(landBasesSorted.size() > 0)
                landBase = landBasesSorted.get(0);
            session.setAttribute("landBase", landBase);

            authUser.setLandBase(landBase);

            System.out.println("USER: " + authUser.getUserId() + " " + authUser.getLogin() + " " + authUser.getName());
            if(authUser.getRole() == 1)
                System.out.println("FULL RIGHTS");
            if(authUser.getRole() == 2)
                System.out.println("READ ONLY RIGHTS");
            if(landBase != null)
                System.out.println("LAND BASE: " + landBase.getDate());
            else
                System.out.println("LAND BASE: NONE");
            resp.sendRedirect(redirectAfterLogin);
        } else {
            req.setAttribute("loginErrors", loginErrors);
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    /**
     * Загружает список пользователей из файла /WEB-INF/users.properties.
     */
    protected void loadUsers() {
        String propFileName = "/WEB-INF/users.properties";
        Properties prop = new Properties();

        try {
            InputStream in = getServletContext().getResourceAsStream(propFileName);
            if (in == null) {
                throw new FileNotFoundException("Property file `" + propFileName +
                                                "' not found.");
            }
            try {
                prop.load(in);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }

        for (int i = 1; ; i++) {
            String login = prop.getProperty("users.user" + i + ".login");
            String password = prop.getProperty("users.user" + i + ".password");
            if (login == null && password == null) {
                break;
            }
            if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                logger.info("The user #" + i + " has been skipped.");
                continue;
            }
            users.put(login, password);
        }
    }

    /**
     * Возвращает результат аутентификации пользователя по логинным данным. Сначала проверяет
     * логин и пароль на наличие в списке всех пользователей, а затем проверяет логин на отсутствие
     * в списке уже авторизованных пользователей.
     *
     * @param login       Логин пользователя
     * @param password    Пароль пользователя
     * @param loginErrors Список ошибок, возникших в результате аутентификации
     * @return            Результат аутентификации пользователя
     */
    protected boolean allowUser(String login, String password, List<String> loginErrors) {
        Locale l = new Locale("ru");
        ResourceBundle rb = ResourceBundle.getBundle("Resource", l);

        if (login == null || password == null || !password.equals(users.get(login))) {
            loginErrors.add(rb.getString("AUTH_ERROR"));
            return false;
        }
        HashMap<String, AuthUser> authUsers = (HashMap<String, AuthUser>)
                                                getServletContext().getAttribute("authUsers");
        if (authUsers.get(login) != null) {
            loginErrors.add(rb.getString("AUTH_EXIST"));
            return false;
        }
        return true;
    }
}
