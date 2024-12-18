package com.example.smarthouse

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class AuthCodeActivity : AppCompatActivity() {

    private val pinDots = arrayOfNulls<View>(4) // Для точек PIN-кода
    private var pinBuilder: StringBuilder = StringBuilder() // Для хранения введенного PIN-кода
    private val PIN_LENGTH = 4 // Длина PIN-кода
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_code)

        pinDots[0] = findViewById(R.id.dot1)
        pinDots[1] = findViewById(R.id.dot2)
        pinDots[2] = findViewById(R.id.dot3)
        pinDots[3] = findViewById(R.id.dot4)

        sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE) // Инициализация SharedPreferences

        // Привязка кнопок от 1 до 9
        for (i in 1..9) {
            val buttonID = "button$i"
            val resID = resources.getIdentifier(buttonID, "id", packageName)
            val button: Button = findViewById(resID)
            button.setOnClickListener(NumberButtonClickListener(i))
        }
        }

    private inner class NumberButtonClickListener(private val number: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            if (pinBuilder.length < PIN_LENGTH) {
                pinBuilder.append(number)
                updatePinIndicator()

                // Проверка, если введен полный PIN-код
                if (pinBuilder.length == PIN_LENGTH) {
                    verifyPin() // Проверка введенного PIN-кода
                }
            }
        }
    }

    private fun updatePinIndicator() {
        for (i in 0 until PIN_LENGTH) {
            pinDots[i]?.setBackgroundResource(
                if (i < pinBuilder.length) R.drawable.ellipse_4 // Устанавливаем стиль для заполненной точки
                else R.drawable.ellipse_5 // Устанавливаем стиль для пустой точки
            )
        }
    }

    private fun verifyPin() {
        // Получение сохраненного PIN-кода из SharedPreferences
        val savedPin = sharedPreferences.getString("pin_code", null)

        if (savedPin != null && savedPin == pinBuilder.toString()) {
            Toast.makeText(this, "PIN-код верный", Toast.LENGTH_SHORT).show()
            // Здесь можно перейти на следующий экран, например, в главную активность
            // startActivity(Intent(this, MainActivity::class.java))
            // finish()
        } else {
            Toast.makeText(this, "Неверный PIN-код", Toast.LENGTH_SHORT).show()
            clearPin() // Очистка введенного PIN-кода при ошибке
        }
    }

    private fun clearPin() {
        pinBuilder.setLength(0) // Очистка введенного PIN-кода
        updatePinIndicator() // Обновление индикатора PIN-кода
    }
}