package maps.core.logic;

/**
 * Определяет метод обследования химического параметра.
 */
public class ChemicalParameterMethod {
  private final int id;
  private final String name;
  private final ChemicalParameterLevel[] levels;

  /**
   * @param id      Идентификатор метода обследования
   * @param name    Название метода обследования
   * @param levels  Набор уровней шкалы-индикатора
   */
  public ChemicalParameterMethod(int id, String name, ChemicalParameterLevel[] levels) {
    this.id = id;
    this.name = name;
    this.levels = levels;
  }

  /**
   * Возвращает идентификатор метода обследования.
   *
   * @return Идентификатор метода обследования.
   */
  public int getId() {
    return id;
  }

  /**
   * Возвращает название метода обследования.
   *
   * @return Название метода обследования.
   */
  public String getName() {
    return name;
  }

  /**
   * Возвращает набор уровней шкалы-индикатора.
   *
   * @return Набор уровней шкалы-индикатора.
   */
  public ChemicalParameterLevel[] getLevels() {
    return levels;
  }
}
