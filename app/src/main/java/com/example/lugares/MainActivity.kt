package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal
import java.time.LocalDate
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth
            //Metodo de login
        binding.btLogin.setOnClickListener(){
            hacerlogin();
        }
            //Metodo de registro
        binding.btRegister.setOnClickListener(){
            hacerRegister();
        }

    }

    private fun hacerlogin(){
        var email = binding.etEmailinput.text.toString()
        var clave = binding.etClaveInput.text.toString()

        auth.signInWithEmailAndPassword(email,clave).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                Log.d("Autenticando","Autenticando")
                val user = auth.currentUser
                if (user != null){
                    actualiza(user)
                }
            }else{
                Log.d("Autenticado","Fallo")
                Toast.makeText(baseContext, "Fallo", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun hacerRegister(){
        var email = binding.etEmailinput.text.toString()
        var clave = binding.etClaveInput.text.toString()

        auth.createUserWithEmailAndPassword(email,clave).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                Log.d("Creando Usuario","Registrado")
                val user = auth.currentUser
                if (user != null){
                    actualiza(user)
                }
            }else{
                Log.d("Creando Usuario","Fallo")
                Toast.makeText(baseContext, "Fallo", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun actualiza(user: FirebaseUser?) {
        if (user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    //Autenticado no pida mas inicio de sesion a menos que la cerremos
    public override fun onStart(){
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }

    }

