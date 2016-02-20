package com.example.mikhail.stockstore.Classes;

/**
 * Created by mikhail on 21.02.16.
 */
// Интерфейс для различных режимов
public interface IDifferentMode {
    // Получить текущий режим
    String getMode();

    // Установить режим
    void setMode(String mode);
}
