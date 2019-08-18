package maps.core.logic;

/**
 * Определяет набор химических параметров.
 */
public enum ChemicalParameter {

  /**
   * Фосфор
   */
  PHOSPHORUS("Фосфор", null, "P2O5", "мг/кг почвы", true, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(1, "Метод Кирсанова", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#40E0D0", 26F),
      new ChemicalParameterLevel("низкое", "#E0FFFF", 51F),
      new ChemicalParameterLevel("среднее", "#00FFFF", 101F),
      new ChemicalParameterLevel("повышенное", "#ADD8E6", 151F),
      new ChemicalParameterLevel("высокое", "#0000FF", 251F),
      new ChemicalParameterLevel("очень высокое", "#00008B", null)
    }),
    new ChemicalParameterMethod(2, "Метод Чирикова", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#40E0D0", 21F),
      new ChemicalParameterLevel("низкое", "#E0FFFF", 51F),
      new ChemicalParameterLevel("среднее", "#00FFFF", 101F),
      new ChemicalParameterLevel("повышенное", "#ADD8E6", 151F),
      new ChemicalParameterLevel("высокое", "#0000FF", 201F),
      new ChemicalParameterLevel("очень высокое", "#00008B", null)
    }),
    new ChemicalParameterMethod(3, "Метод Мачигина", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#40E0D0", 11F),
      new ChemicalParameterLevel("низкое", "#E0FFFF", 16F),
      new ChemicalParameterLevel("среднее", "#00FFFF", 31F),
      new ChemicalParameterLevel("повышенное", "#ADD8E6", 46F),
      new ChemicalParameterLevel("высокое", "#0000FF", 61F),
      new ChemicalParameterLevel("очень высокое", "#00008B", null)
    }),
    new ChemicalParameterMethod(4, "Метод Эгнера-Рима", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#40E0D0", 51F),
      new ChemicalParameterLevel("низкое", "#E0FFFF", 71F),
      new ChemicalParameterLevel("среднее", "#00FFFF", 141F),
      new ChemicalParameterLevel("повышенное", "#0000FF", null)
    })
  }),

  /**
   * Калий
   */
  POTASSIUM("Калий", null, "K2O", "мг/кг почвы", true, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(5, "Метод Кирсанова", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#FFFF00", 41F),
      new ChemicalParameterLevel("низкое", "#FFD700", 81F),
      new ChemicalParameterLevel("среднее", "#FFA500", 121F),
      new ChemicalParameterLevel("повышенное", "#F4A460", 171F),
      new ChemicalParameterLevel("высокое", "#D2691E", 251F),
      new ChemicalParameterLevel("очень высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(6, "Метод Чирикова", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#FFFF00", 21F),
      new ChemicalParameterLevel("низкое", "#FFD700", 41F),
      new ChemicalParameterLevel("среднее", "#FFA500", 81F),
      new ChemicalParameterLevel("повышенное", "#F4A460", 121F),
      new ChemicalParameterLevel("высокое", "#D2691E", 181F),
      new ChemicalParameterLevel("очень высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(7, "Метод Мачигина", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#FFFF00", 101F),
      new ChemicalParameterLevel("низкое", "#FFD700", 201F),
      new ChemicalParameterLevel("среднее", "#FFA500", 301F),
      new ChemicalParameterLevel("повышенное", "#F4A460", 401F),
      new ChemicalParameterLevel("высокое", "#D2691E", 601F),
      new ChemicalParameterLevel("очень высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(8, "Метод Масловой", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#FFFF00", 51F),
      new ChemicalParameterLevel("низкое", "#FFD700", 101F),
      new ChemicalParameterLevel("среднее", "#FFA500", 151F),
      new ChemicalParameterLevel("повышенное", "#F4A460", 201F),
      new ChemicalParameterLevel("высокое", "#D2691E", 301F),
      new ChemicalParameterLevel("очень высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(9, "Метод Эгнера-Рима", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFD700", 71F),
      new ChemicalParameterLevel("среднее", "#FFA500", 141F),
      new ChemicalParameterLevel("повышенное", "#F4A460", null)
    })
  }),

  /**
   * Азот
   */
  NITROGEN("Азот", null, "N", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(10, "Метод Тюрина-Кононовой", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#9ACD32", 31F),
      new ChemicalParameterLevel("низкое", "#ADFF2F", 41F),
      new ChemicalParameterLevel("среднее", "#90EE90", 51F),
      new ChemicalParameterLevel("повышенное", "#32CD32", 71F),
      new ChemicalParameterLevel("высокое", "#008000", 101F),
      new ChemicalParameterLevel("очень высокое", "#006400", null)
    }),
    new ChemicalParameterMethod(11, "Метод Корнфилда", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#9ACD32", 101F),
      new ChemicalParameterLevel("низкое", "#ADFF2F", 151F),
      new ChemicalParameterLevel("среднее", "#90EE90", 201F),
      new ChemicalParameterLevel("повышенное", "#32CD32", null)
    })
  }),

  /**
   * Кислотность
   */
  ACIDITY("Кислотность", "Кисл.", null, "pH", true, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(12, "Метод ЦИНАО", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("сильнокислая", "#6B8E23", 4.6F),
      new ChemicalParameterLevel("среднекислая", "#9ACD32", 5.1F),
      new ChemicalParameterLevel("слабокислая", "#ADFF2F", 5.6F),
      new ChemicalParameterLevel("близкая к нейтральной", "#90EE90", 6.1F),
      new ChemicalParameterLevel("нейтральная", "#32CD32", 7.1F),
      new ChemicalParameterLevel("слабощелочная", "#008000", 8.1F),
      new ChemicalParameterLevel("щелочная", "#006400", null)
    })
  }),

  /**
   * Гумус
   */
  HUMUS("Гумус", "Гум.", null, "%", true, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(13, "Метод Тюрина в модиф. ЦИНАО", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#40E0D0", 2.1F),
      new ChemicalParameterLevel("низкое", "#E0FFFF", 4.1F),
      new ChemicalParameterLevel("среднее", "#00FFFF", 6.1F),
      new ChemicalParameterLevel("повышенное", "#ADD8E6", 8.1F),
      new ChemicalParameterLevel("высокое", "#0000FF", 10.1F),
      new ChemicalParameterLevel("очень высокое", "#00008B", null)
    })
  }),

  /**
   * Кальций
   */
  CALCIUM("Кальций", null, "Ca", "мг-экв/100г почвы", false, new ChemicalParameterMethod[] {
    // TODO: узнать название метода
    new ChemicalParameterMethod(14, "Неизвестный метод", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#00FFFF", 2.6F),
      new ChemicalParameterLevel("низкое", "#0000FF", 5.1F),
      new ChemicalParameterLevel("среднее", "#90EE90", 10.1F),
      new ChemicalParameterLevel("повышенное", "#008000", 15.1F),
      new ChemicalParameterLevel("высокое", "#00008B", 20.1F),
      new ChemicalParameterLevel("очень высокое", "#006400", null)
    })
  }),

  /**
   * Магний
   */
  MAGNESIUM("Магний", null, "Mg", "мг-экв/100г почвы", false, new ChemicalParameterMethod[] {
    // TODO: узнать название метода
    new ChemicalParameterMethod(15, "Неизвестный метод", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("очень низкое", "#00FFFF", 0.6F),
      new ChemicalParameterLevel("низкое", "#0000FF", 1.1F),
      new ChemicalParameterLevel("среднее", "#90EE90", 2.1F),
      new ChemicalParameterLevel("повышенное", "#008000", 3.1F),
      new ChemicalParameterLevel("высокое", "#00008B", 4.1F),
      new ChemicalParameterLevel("очень высокое", "#006400", null)
    })
  }),

  /**
   * Сера
   */
  SULFUR("Сера", null, "S", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    // TODO: узнать название метода
    new ChemicalParameterMethod(16, "Неизвестный метод", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 6.1F),
      new ChemicalParameterLevel("среднее", "#808000", 12.1F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Марганец
   */
  MANGANESE("Марганец", null, "Mn", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(17, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 31F),
      new ChemicalParameterLevel("среднее", "#808000", 71F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(18, "Вытяжка ацетатно-аммонийного раствора", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 10.1F),
      new ChemicalParameterLevel("среднее", "#808000", 20.1F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Цинк
   */
  ZINC("Цинк", null, "Zn", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(19, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 0.8F),
      new ChemicalParameterLevel("среднее", "#808000", 1.6F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(20, "Вытяжка ацетатно-аммонийного раствора", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 2.1F),
      new ChemicalParameterLevel("среднее", "#808000", 5.1F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Медь
   */
  COPPER("Медь", null, "Cu", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(21, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 1.6F),
      new ChemicalParameterLevel("среднее", "#808000", 3.4F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(22, "Вытяжка ацетатно-аммонийного раствора", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 0.21F),
      new ChemicalParameterLevel("среднее", "#808000", 0.51F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Кобальт
   */
  COBALT("Кобальт", null, "Co", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(23, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 1.1F),
      new ChemicalParameterLevel("среднее", "#808000", 2.3F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    }),
    new ChemicalParameterMethod(24, "Вытяжка ацетатно-аммонийного раствора", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 0.16F),
      new ChemicalParameterLevel("среднее", "#808000", 0.31F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Бор
   */
  BORON("Бор", null, "B", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(25, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 0.34F),
      new ChemicalParameterLevel("среднее", "#808000", 0.8F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  }),

  /**
   * Молибден
   */
  MOLYBDENUM("Молибден", null, "Mo", "мг/кг почвы", false, new ChemicalParameterMethod[] {
    new ChemicalParameterMethod(26, "Метод Пейве-Ринькиса", new ChemicalParameterLevel[] {
      new ChemicalParameterLevel("низкое", "#FFFF00", 0.11F),
      new ChemicalParameterLevel("среднее", "#808000", 0.23F),
      new ChemicalParameterLevel("высокое", "#8B4513", null)
    })
  });

  private final String title;
  private final String shortTitle;
  private final String formula;
  private final String unit;
  private final boolean isBackground;
  private final ChemicalParameterMethod[] methods;

  ChemicalParameter(String title, String shortTitle, String formula, String unit,
      boolean isBackground, ChemicalParameterMethod[] methods) {
    this.title = title;
    this.shortTitle = shortTitle;
    this.formula = formula;
    this.unit = unit;
    this.isBackground = isBackground;
    this.methods = methods;
  }

  /**
   * Возвращает имя константы в нижнем регистре.
   *
   * @return Имя константы в нижнем регистре
   */
  public String getName() {
    return name().toLowerCase();
  }

  /**
   * Возвращает полное название химического параметра.
   *
   * @return Полное название химического параметра
   */
  public String getTitle() {
    return title;
  }

  /**
   * Возвращает сокращенное название химического параметра.
   *
   * @return Сокращенное название химического параметра
   */
  public String getShortTitle() {
    return shortTitle;
  }

  /**
   * Возвращает формулу.
   *
   * @return Формула
   */
  public String getFormula() {
    return formula;
  }

  /**
   * Возвращает единицу измерения.
   *
   * @return Единица измерения
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Возвращает, может ли выступать этот параметр в качестве фонового на электронной картограмме.
   *
   * @return true, если это фоновый параметр
   */
  public boolean getIsBackground() {
    return isBackground;
  }

  /**
   * Возвращает список возможных методов обследований.
   *
   * @return Список возможных методов обследований
   */
  public ChemicalParameterMethod[] getMethods() {
    return methods;
  }
}
