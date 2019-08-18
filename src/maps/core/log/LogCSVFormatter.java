package maps.core.log;

import java.text.*;
import java.util.Date;
import java.util.logging.*;

/**
 * Класс LogCSVFormatter предназначен для использования объектом класса Logger для форматирования
 * записей журналов в формат CSV (Comma-separated values).
 */
public class LogCSVFormatter extends Formatter {
    private static final String fieldSeparator = ";";
    private static final String textQuote = "\"";
    private static final String escapedTextQuote = "\"\"";
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Возвращает запись журнала в виде строки в формате CSV.
     *
     * @param record  Запись журнала
     * @return        Строка в формате CSV
     */
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        Date date = new Date(record.getMillis());
        Level level = record.getLevel();
        String message = formatMessage(record);
        Object[] params = record.getParameters();

        builder.append(quoteIfNeeded(dateFormat.format(date)));
        builder.append(fieldSeparator).append(quoteIfNeeded(level.toString()));
        builder.append(fieldSeparator).append(quoteIfNeeded(message));
        for (int i = 0; i < params.length; i++) {
            String paramStr = (params[i] == null) ? "" : params[i].toString();
            builder.append(fieldSeparator).append(quoteIfNeeded(paramStr));
        }
        builder.append(lineSeparator);
        return builder.toString();
    }

    /**
     * Если строка содержит  символы разделителя полей или символы двойных кавычек,
     * то все содержимое строки заключается в двойные кавычки, а вложенные двойные кавычки
     * при этом удваиваются.
     *
     * @param str Исходная строка
     * @return    Преобразованная строка
     */
    protected String quoteIfNeeded(String str) {
        if (str.indexOf(fieldSeparator) != -1 ||
                str.indexOf(textQuote) != -1 ||
                str.indexOf(lineSeparator) != -1) {
            return textQuote + str.replace(textQuote, escapedTextQuote) + textQuote;
        }
        return str;
    }
}
