package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signInBut: Button = findViewById(R.id.signInBut)
        val signUpBut: Button = findViewById(R.id.signUpBut) // Исправлено имя переменной

        val nameSU: EditText = findViewById(R.id.nameSU)
        val emailSU: EditText = findViewById(R.id.emailSU)
        val passwordSU: EditText = findViewById(R.id.passwordSU)

        // Объявляем TextInputLayout для полей ввода
        val nameSUInputLayout: TextInputLayout = findViewById(R.id.nameSUInputLayout)
        val emailSUInputLayout: TextInputLayout = findViewById(R.id.emailSUInputLayout)
        val passwordSUInputLayout: TextInputLayout = findViewById(R.id.passwordSUInputLayout)

        signUpBut.setOnClickListener {
            val name = nameSU.text.toString().trim()
            val email = emailSU.text.toString().trim()
            val password = passwordSU.text.toString().trim()

            // Проверяем поля ввода на пустоту
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Если одно из полей пустое, показываем ошибку
                if (TextUtils.isEmpty(name)) {
                    nameSUInputLayout.error = "Введите имя"
                }
                if (TextUtils.isEmpty(email)) {
                    emailSUInputLayout.error = "Введите почту"
                }
                if (TextUtils.isEmpty(password)) {
                    passwordSUInputLayout.error = "Введите пароль"
                }
                return@setOnClickListener
            }

            // Проверяем почту на корректность
            val emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.ru$"
            if (!email.matches(emailPattern.toRegex())) {
                // Если почта не соответствует паттерну, показываем ошибку
                emailSUInputLayout.error = "Некорректная почта"
                return@setOnClickListener
            }

            // Здесь можно добавить логику для успешной регистрации
            // Например, сохранение данных пользователя или переход на другой экран
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

            // Переход на следующий экран после успешной регистрации
            val intent = Intent(this@SignUpActivity, CreateCodeActivity::class.java) // Замените на нужный экран
            startActivity(intent)
        }

        // Установим слушатель на кнопку входа
        signInBut.setOnClickListener {
            // Переходим на экран входа
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}