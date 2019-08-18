package auth;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;
import java.util.HashMap;

/**
 * Класс AuthUserEventListener реализует интерфейсы ServletContextListener, HttpSessionListener,
 * HttpSessionAttributeListener и является слушателем событий contextInitialized, sessionDestroyed,
 * attributeAdded, attributeRemoved, attributeReplaced. Основным предназначением данного класса
 * является создание и поддержка в актуальном состоянии списка всех авторизованных пользователей
 * системы. Ссылка на данный список хранится в атрибуте authUsers контекста веб-приложения
 * (ServletContext) и таким образом список доступен во всех сервлетах системы.
 */
public class AuthUserEventListener implements ServletContextListener,
                                              HttpSessionListener,
                                              HttpSessionAttributeListener {
    private ServletContext servletContext;
    private HashMap<String, AuthUser> authUsers = new HashMap<String, AuthUser>();

    public AuthUserEventListener() {
    }

    /**
     * Создает список авторизованных пользователей.
     *
     * @param sce Событие
     */
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.setAttribute("authUsers", authUsers);
    }

    /**
     * Пустой метод.
     *
     * @param sce Событие
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }

    /**
     * Пустой метод.
     *
     * @param hse Событие
     */
    public void sessionCreated(HttpSessionEvent hse) {
    }

    /**
     * Удаляет из сессии пользователя атрибут authUser.
     *
     * @param hse Событие
     */
    public void sessionDestroyed(HttpSessionEvent hse) {
        hse.getSession().removeAttribute("authUser");
    }

    /**
     * Если в сессию добавлен атрибут authUser, то значение этого атрибута
     * добавляется в список всех авторизованных пользователей.
     *
     * @param hsbe Событие
     */
    public void attributeAdded(HttpSessionBindingEvent hsbe) {
        if (hsbe.getName().equals("authUser")) {
            AuthUser authUser = (AuthUser) hsbe.getValue();
            authUsers.put(authUser.getLogin(), authUser);
        }
    }

    /**
     * Если из сессии удален атрибут authUser, то значение этого атрибута
     * также удаляется из списка всех авторизованных пользователей.
     *
     * @param hsbe Событие
     */
    public void attributeRemoved(HttpSessionBindingEvent hsbe) {
        if (hsbe.getName().equals("authUser")) {
            AuthUser authUser = (AuthUser) hsbe.getValue();
            authUsers.remove(authUser.getLogin());

        }
    }

    /**
     * Если в сессии изменен атрибут authUser, то старое значение этого атрибута
     * удаляется из списка всех авторизованных пользователей, а новое - добавляется.
     *
     * @param hsbe Событие
     */
    public void attributeReplaced(HttpSessionBindingEvent hsbe) {
        if (hsbe.getName().equals("authUser")) {
            HttpSession session  = hsbe.getSession();
            AuthUser oldAuthUser = (AuthUser) hsbe.getValue();
            AuthUser newAuthUser = (AuthUser) session.getAttribute("authUser");
            authUsers.remove(oldAuthUser.getLogin());
            authUsers.put(newAuthUser.getLogin(), newAuthUser);
        }
    }
}
