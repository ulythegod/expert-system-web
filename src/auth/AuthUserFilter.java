package auth;

import com.google.gson.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Класс AuthUserFilter реализует интерфейс javax.servlet.Filter и служит для перехвата
 * запросов к сервлетам, которые недоступны для неавторизованных пользователей системы.
 * У перехваченного запроса из сессии считывается атрибут authUser, который является ссылкой
 * на экземпляр класса {@link AuthUser}. Если данный атрибут не установлен, то
 * происходит перенаправление пользователя на страницу, указанную в параметре loginPageURL
 * настроек фильтра. В случае если запрос произведен с использованием технологии AJAX, то
 * неавторизованному пользователю в ответ на его запрос возвращается 401-й код ошибки
 * (неавторизован).
 */
public class AuthUserFilter implements Filter {
    private String loginPageURL;

    /**
     * Анализирует значение сессионного атрибута authUser и в зависимости от результата решает,
     * пропускать ли запрос дальше. Если атрибут authUser не установлен, то происходит
     * перенаправление пользователя на страницу, указанную в параметре loginPageURL настроек
     * фильтра. В случае если запрос произведен с использованием технологии AJAX, то
     * неавторизованному пользователю в ответ на его запрос возвращается 401-й код ошибки
     * (неавторизован).
     *
     * @param request   Запрос
     * @param response  Ответ
     * @param chain     Цепочка фильтров
     */
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession();
        AuthUser authUser = (AuthUser) session.getAttribute("authUser");

        if (authUser == null) {
            boolean isAjaxRequest = "XMLHttpRequest".equals(httpReq.getHeader("X-Requested-With"));
            if (isAjaxRequest) {
                httpResp.sendError(httpResp.SC_UNAUTHORIZED);
            } else if ("IFrame".equals(httpReq.getParameter("X-Requested-With"))) {
                JsonObject respObj = new JsonObject();
                respObj.addProperty("notAuth", true);
                respObj.addProperty("error", "Вы не авторизованы в системе");
                respObj.addProperty("loginPageURL", loginPageURL);
                httpResp.setContentType("text/html;charset=UTF-8");
                new Gson().toJson(respObj, httpResp.getWriter());
            } else {
                httpResp.sendRedirect(loginPageURL);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Считывает из настроек фильтра параметр loginPageURL. В случае его отсутствия генерирует
     * исключение.
     *
     * @param config Настройки фильтра
     */
    public void init(FilterConfig config) throws ServletException {
        loginPageURL = config.getInitParameter("loginPageURL");
        if (loginPageURL == null) {
            throw new ServletException("Initial parameter `loginPageURL' is not defined.");
        }
    }

    /**
     * Пустой метод.
     */
    public void destroy() {
    }
}
