package maps.core.logic;

/**
 * Определяет один уровень шкалы-индикатора метода обследования химического параметра.
 */
public class ChemicalParameterLevel {
    private final String name;
    private final String color;
    private final Float limit;

    /**
     * @param name    Название уровня (низкий, средний и т.п.)
     * @param color   Цвет уровня
     * @param limit   Верхний предел значений
     */
    public ChemicalParameterLevel(String name, String color, Float limit) {
        this.name = name;
        this.color = color;
        this.limit = limit;
    }

    /**
     * Возвращает название уровня.
     *
     * @return Название уровня
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает цвет уровня.
     *
     * @return Цвет уровня
     */
    public String getColor() {
        return color;
    }

    /**
     * Возвращает верхний предел значений.
     *
     * @return Верхний предел значений
     */
    public Float getLimit() {
        return limit;
    }
}
