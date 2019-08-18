package auth;

import core.data.Agricultures;
import core.data.LandBase;

/**
 * Данный класс используется для хранения информации, необходимой для успешной авторизации
 * пользователя.
 */
public class AuthUser {
    private long userId;
    private String login;
    private String name;
    private int role;
    private String sessionId;
    private Agricultures agriculture;
    private LandBase landBase;


    /**
     * @param sessionId Идентификатор сессии
     * @param login     Логин пользователя
     * @param name      Имя пользователя
     * @param role    Права пользователя
     * @param userId    Числовой идентификатор пользователя
     * @param agriculture   Хозяйство, привязанное к пользователю
     */
    public AuthUser(long userId, String login, String name, int role, String sessionId, Agricultures agriculture) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.role = role;
        this.sessionId = sessionId;
        this.agriculture = agriculture;
    }

    /**
     * Возвращает идентификатор сессии.
     *
     * @return Идентификатор сессии
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Устанавливает идентификатор сессии.
     *
     * @param sessionId Идентификатор сессии
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return Логин пользователя
     */
    public String getLogin() {
        return login;
    }

    /**
     * Устанавливает логин пользователя.
     *
     * @param login Логин пользователя
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает числовой идентификатор пользователя.
     *
     * @return Числовой идентификатор пользователя
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Устанавливает числовой идентификатор пользователя.
     *
     * @param userId Числовой идентификатор пользователя
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name Имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Проверяет права пользователя
     * @return Уровень доступа
     */
    public int getRole() {
        return role;
    }

    /**
     * Устанавливает права пользователя
     * @param role Полнота прав (1 - полноправный, 2 - без возможности редкатирования)
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Возвращает хозяйство пользователя.
     *
     * @return Хозяйство пользователя
     */
    public Agricultures getAgriculture() {
        return agriculture;
    }

    /**
     * Устанавливает хозяйство пользователя.
     *
     * @param agriculture Хозяйство пользователя
     */
    public void setAgriculture(Agricultures agriculture) {
        this.agriculture = agriculture;
    }

    /**
     * Возвращает текущую земельную базу
     * @return Земельная база
     */
    public LandBase getLandBase() {
        return landBase;
    }

    /**
     * Устанавливает текущую земельную базу
     * @param landBase Земельная база
     */
    public void setLandBase(LandBase landBase) {
        this.landBase = landBase;
    }
}
