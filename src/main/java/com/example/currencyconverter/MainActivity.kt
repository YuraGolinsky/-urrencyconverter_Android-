package com.example.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Курси валют: покупка та продаж
        val buyRates = mapOf(
            "UAH" to 1.0,       // Гривня як базова валюта
            "USD" to 41.3,      // Курс покупки долара
            "EUR" to 43.43      // Курс покупки євро
        )
        val sellRates = mapOf(
            "UAH" to 1.0,       // Гривня як базова валюта
            "USD" to 41.5,      // Курс продажу долара
            "EUR" to 44.0       // Курс продажу євро
        )

        // Ініціалізація елементів інтерфейсу
        val inputAmount = findViewById<EditText>(R.id.inputAmount) // Поле для введення суми
        val fromCurrencySpinner = findViewById<Spinner>(R.id.fromCurrencySpinner) // Вибір валюти "з"
        val toCurrencySpinner = findViewById<Spinner>(R.id.toCurrencySpinner) // Вибір валюти "в"
        val convertButton = findViewById<Button>(R.id.convertButton) // Кнопка "Конвертувати"
        val resultText = findViewById<TextView>(R.id.resultText) // Текст для відображення результату

        // Налаштування списків валют
        val currencyOptions = buyRates.keys.toList() // Отримання списку валют
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromCurrencySpinner.adapter = adapter // Адаптер для вибору валюти "з"
        toCurrencySpinner.adapter = adapter // Адаптер для вибору валюти "в"

        // Обробка натискання кнопки "Конвертувати"
        convertButton.setOnClickListener {
            // Отримання введеної суми
            val amount = inputAmount.text.toString().toDoubleOrNull()
            val fromCurrency = fromCurrencySpinner.selectedItem.toString() // Вибрана валюта "з"
            val toCurrency = toCurrencySpinner.selectedItem.toString() // Вибрана валюта "в"

            // Перевірка на коректність введеної суми
            if (amount == null || amount <= 0) {
                resultText.text = "Введіть коректну суму"
                return@setOnClickListener
            }

            var convertedAmount = 0.0
            // Якщо ми конвертуємо з гривні в іншу валюту
            if (fromCurrency == "UAH") {
                if (toCurrency == "USD") {
                    // Купівля доларів
                    convertedAmount = amount / sellRates["USD"]!! // Потрібно використовувати курс продажу для гривні
                } else if (toCurrency == "EUR") {
                    // Купівля євро
                    convertedAmount = amount / sellRates["EUR"]!! // Потрібно використовувати курс продажу для гривні
                }
            }
            // Якщо ми конвертуємо в гривню
            else if (toCurrency == "UAH") {
                if (fromCurrency == "USD") {
                    // Продавати долари
                    convertedAmount = amount * buyRates["USD"]!! // Потрібно використовувати курс покупки для долара
                } else if (fromCurrency == "EUR") {
                    // Продавати євро
                    convertedAmount = amount * buyRates["EUR"]!! // Потрібно використовувати курс покупки для євро
                }
            }

            // Відображення результату
            resultText.text = "Результат: %.2f $toCurrency".format(convertedAmount)
        }
    }
}
