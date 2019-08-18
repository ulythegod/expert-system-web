package maps.core.log;

import auth.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.logging.*;

/**
 * <p>
 * Класс LogFilter реализует интерфейс javax.servlet.Filter и предназначен для перехвата и
 * протоколирования всех запросов ко всем сервлетам в файлы access.ГГГГ-ММ-ДД.log.
 * <p>
 * Каждая запись такого файла состоит из следующих полей:
 * <ul>
 *   <li>Request Date – дата и время запроса;</li>
 *   <li>Level – уровень сообщения (не используется);</li>
 *   <li>Message – текст сообщения (не используется);</li>
 *   <li>Session Id – идентификатор сессии;</li>
 *   <li>Login – логин пользователя (не заполнено для неавторизованного пользователя);</li>
 *   <li>Request Method – метод запроса (GET, POST или другой метод,
 *       поддерживаемый HTTP-протоколом);</li>
 *   <li>Request URI – идентификатор запрашиваемого ресурса (часть URL, начиная от хоста и
 *       до конца либо до символа "?");</li>
 *   <li>Query String – строка с параметрами запроса (часть URL, начиная со следующего за
 *       "?" символа и до конца);</li>
 *   <li>Status – HTTP-код ответа сервера;</li>
 *   <li>IP Address – IP-адрес клиента.</li>
 * </ul>
 * <p>
 * Поля Level и Message не используются и оставлены только для совместимости с API класса Logger.
 * В качестве формата файла используется CSV.
 */
public class LogFilter implements javax.servlet.Filter {
    private static final Logger logger = Logger.getLogger("access");

    /**
     * Добавляет в журнал информацию о каждом запросе, который проходит через этот фильтр.
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

        chain.doFilter(request, response);

        logger.log(Level.INFO, "", new Object[] {
            session.getId(),
            (authUser != null) ? authUser.getLogin() : "",
            httpReq.getMethod(),
            httpReq.getRequestURI(),
            httpReq.getQueryString(),
            httpResp.getStatus(),
            httpReq.getRemoteAddr()
        });
    }

    /**
     * Пустой метод.
     *
     * @param config Настройки фильтра
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Пустой метод.
     */
    public void destroy() {
    }
}
