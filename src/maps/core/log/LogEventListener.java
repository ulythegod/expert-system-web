package maps.core.log;

import auth.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.util.logging.*;

/**
 * <p>
 * Класс LogEventListener реализует интерфейс HttpSessionAttributeListener, является слушателем
 * событий attributeAdded, attributeReplaced и предназначен для протоколирования всех успешных
 * попыток аутентификации в файлы auth.ГГГГ-ММ-ДД.log.
 * <p>
 * Каждая запись такого файла состоит из следующих полей:
 * <ul>
 *   <li>Date – дата и время;</li>
 *   <li>Level – уровень сообщения (не используется);</li>
 *   <li>Message – текст сообщения (не используется);</li>
 *   <li>Login – логин пользователя;</li>
 *   <li>Session Id – идентификатор сессии.</li>
 * </ul>
 * <p>
 * Поля Level и Message не используются и оставлены только для совместимости с API класса Logger.
 * В качестве формата файла используется CSV.
 */
public class LogEventListener implements HttpSessionAttributeListener {
    private static final Logger logger = Logger.getLogger("auth");

    public LogEventListener() {
    }

    /**
     * Если в сессию добавлен атрибут authUser, то в журнал добавляется запись об успешной
     * аутентификации.
     *
     * @param hsbe Событие
     */
    public void attributeAdded(HttpSessionBindingEvent hsbe) {
        if (hsbe.getName().equals("authUser")) {
            AuthUser authUser = (AuthUser) hsbe.getValue();
            logger.log(Level.INFO, "", new Object[] {
                authUser.getLogin(),
                hsbe.getSession().getId()
            });
        }
    }

    /**
     * Пустой метод.
     *
     * @param hsbe Событие
     */
    public void attributeRemoved(HttpSessionBindingEvent hsbe) {
    }

    /**
     * Если в сессии изменен атрибут authUser, то в журнал добавляется запись об успешной
     * аутентификации.
     *
     * @param hsbe Событие
     */
    public void attributeReplaced(HttpSessionBindingEvent hsbe) {
        if (hsbe.getName().equals("authUser")) {
            HttpSession session  = hsbe.getSession();
            AuthUser newAuthUser = (AuthUser) session.getAttribute("authUser");
            logger.log(Level.INFO, "", new Object[] {
                newAuthUser.getLogin(),
                session.getId()
            });
        }
    }
}
