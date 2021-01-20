package mx.edu.ittepic

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList
import kotlin.reflect.typeOf

class Lienzo(p: MainActivity): View(p){

    var puntero = p
    var bolaX = 0f
    var bolaY = 0f
    var separadorAltura = 0f
    var separadorAnchura = 0f
    var inicializar = false
    var bandera = false
    var sensorProximidad = false
    val radio = 80
    val timer = object : CountDownTimer(2000, 100){
        override fun onTick(millisUntilFinished: Long) {
            // se usará [0] y [1] -> puntero.p
            // se usará [0]-> 5 = sin tapar -> puntero.p2
            if (!colisionX())
                bolaX += (puntero.p[0] * -1)
            else {
                if(bolaX-radio < separadorAnchura)  bolaX = 100f
                else if(bolaX+radio > width) bolaX = width-75f
            }
            if (!colisionY())
                bolaY += puntero.p[1] * 3
            else {
                if(bolaY-radio < separadorAltura) bolaY = 80f
                else if(bolaY+radio > height) bolaY = height-90f
            }
            sensorProximidad = puntero.p2[0] != 5f
            invalidate()
        }

        override fun onFinish() { start() }

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val p = Paint()

        if(!inicializar){
            bolaX = 100f
            bolaY = height - 104f
            separadorAltura = height/10f
            separadorAnchura = width/5f
            inicializar = true
            p.textSize = 65f
            c.drawText("Toca la pantalla para comenzar...", 50f, separadorAltura*5, p)
        } else {
            p.strokeWidth = 10f
            p.style = Paint.Style.FILL
            if(!sensorProximidad){
                p.color = Color.WHITE
                c.drawRect(0f, 0f, separadorAnchura*5, separadorAltura*10, p)
                p.color = Color.GREEN
                c.drawCircle(separadorAnchura*5-100, separadorAltura/2, 50f, p)
                p.color = Color.BLACK
            } else {
                p.color = Color.BLACK
                c.drawRect(0f, 0f, separadorAnchura*5, separadorAltura*10, p)
                p.color = Color.MAGENTA
                c.drawCircle(separadorAnchura*5-100, separadorAltura/2, 50f, p)
                p.color = Color.WHITE
            }
            p.style = Paint.Style.STROKE
            dibujarLineasHorizontales(c, p)
            dibujarLineasVerticales(c, p)
            p.style = Paint.Style.FILL
            c.drawCircle(bolaX, bolaY, 65f, p)
        }
    }

    private fun dibujarLineasHorizontales(c: Canvas, p: Paint) {
        // Fila 1
        c.drawLine(2*separadorAnchura, separadorAltura, 3*separadorAnchura, separadorAltura, p)
        // Fila 2
        c.drawLine(separadorAnchura, 2*separadorAltura, 3*separadorAnchura, 2*separadorAltura, p)
        // Fila 3
        c.drawLine(separadorAnchura, 3*separadorAltura, 2*separadorAnchura, 3*separadorAltura, p)
        c.drawLine(3*separadorAnchura, 3*separadorAltura, 4*separadorAnchura, 3*separadorAltura, p)
        // Fila 4
        c.drawLine(0f, 4*separadorAltura, separadorAnchura, 4*separadorAltura, p)
        c.drawLine(2*separadorAnchura, 4*separadorAltura, 3*separadorAnchura, 4*separadorAltura, p)
        c.drawLine(4*separadorAnchura, 4*separadorAltura, 5*separadorAnchura, 4*separadorAltura, p)
        // Fila 5
        c.drawLine(separadorAnchura, 5*separadorAltura, 2*separadorAnchura, 5*separadorAltura, p)
        c.drawLine(3*separadorAnchura, 5*separadorAltura, 4*separadorAnchura, 5*separadorAltura, p)
        // Fila 6
        c.drawLine(2*separadorAnchura, 6*separadorAltura, 3*separadorAnchura, 6*separadorAltura, p)
        c.drawLine(4*separadorAnchura, 6*separadorAltura, 5*separadorAnchura, 6*separadorAltura, p)
        // Fila 7
        c.drawLine(0f, 7*separadorAltura, 2*separadorAnchura, 7*separadorAltura, p)
        c.drawLine(3*separadorAnchura, 7*separadorAltura, 4*separadorAnchura, 7*separadorAltura, p)
        // Fila 8
        c.drawLine(2*separadorAnchura, 8*separadorAltura, 3*separadorAnchura, 8*separadorAltura, p)
        // Fila 9
        c.drawLine(separadorAnchura, 9*separadorAltura, 2*separadorAnchura, 9*separadorAltura, p)
        // Laterales
        c.drawLine(0f, 0f, 10*separadorAnchura, 0f, p)
        c.drawLine(0f, 10*separadorAltura, 10*separadorAnchura, 10*separadorAltura, p)
    }

    private fun dibujarLineasVerticales(c: Canvas, p: Paint){
        // Fila 1
        c.drawLine(separadorAnchura, separadorAltura, separadorAnchura, 3*separadorAltura, p)
        c.drawLine(separadorAnchura, 5*separadorAltura, separadorAnchura, 6*separadorAltura, p)
        c.drawLine(separadorAnchura, 8*separadorAltura, separadorAnchura, 9*separadorAltura, p)
        // Fila 2
        c.drawLine(2*separadorAnchura, 0f, 2*separadorAnchura, separadorAltura, p)
        c.drawLine(2*separadorAnchura, 4*separadorAltura, 2*separadorAnchura, 5*separadorAltura, p)
        c.drawLine(2*separadorAnchura, 6*separadorAltura, 2*separadorAnchura, 9*separadorAltura, p)
        // Fila 3
        c.drawLine(3*separadorAnchura, 2*separadorAltura, 3*separadorAnchura, 4*separadorAltura, p)
        c.drawLine(3*separadorAnchura, 6*separadorAltura, 3*separadorAnchura, 7*separadorAltura, p)
        c.drawLine(3*separadorAnchura, 9*separadorAltura, 3*separadorAnchura, 10*separadorAltura, p)
        // Fila 4
        c.drawLine(4*separadorAnchura, 0f, 4*separadorAnchura, 2*separadorAltura, p)
        c.drawLine(4*separadorAnchura, 3*separadorAltura, 4*separadorAnchura, 5*separadorAltura, p)
        c.drawLine(4*separadorAnchura, 7*separadorAltura, 4*separadorAnchura, 9*separadorAltura, p)
        // Laterales
        c.drawLine(0f, 0f, 0f, 10*separadorAltura, p)
        c.drawLine(5*separadorAnchura, 0f, 5*separadorAnchura, 10*separadorAltura, p)
    }

    private fun colisionX(): Boolean{
        // validación con bordes
        if ( bolaX-radio < 0f || bolaX+radio > width ) return true
        // validación con lineas horizontales
        //Fila 1
        if(bolaY>=0f && bolaY<=separadorAltura) {
            if (bolaX + radio >= 2 * separadorAnchura || bolaX - radio <= 2 * separadorAnchura) return true
            if (bolaX + radio >= 4 * separadorAnchura || bolaX - radio <= 4 * separadorAnchura) return true
        }
        //Fila 2
        if(bolaY>=separadorAltura && bolaY<=separadorAltura*2) {
            if (bolaX + radio >= separadorAnchura || bolaX - radio <= separadorAnchura) return true
            if (bolaX + radio >= 4 * separadorAnchura || bolaX - radio <= 4 * separadorAnchura) return true
        }
        //Fila 3
        if(bolaY>=separadorAltura*2 && bolaY<=separadorAltura*3){
            if ( bolaX+radio >= separadorAnchura || bolaX-radio <= separadorAnchura ) return true
            if ( bolaX+radio >= 3*separadorAnchura || bolaX-radio <= 3*separadorAnchura ) return true
        }
        //Fila 4
        if(bolaY>=separadorAltura*3 && bolaY<=separadorAltura*4){
            if ( bolaX+radio >= 3*separadorAnchura || bolaX-radio <= 3*separadorAnchura ) return true
            if ( bolaX+radio >= 4*separadorAnchura || bolaX-radio <= 4*separadorAnchura ) return true
        }
        //Fila 5
        if(bolaY>=separadorAltura*4 && bolaY<=separadorAltura*5) {
            if (bolaX + radio >= 2 * separadorAnchura || bolaX - radio <= 2 * separadorAnchura) return true
            if (bolaX + radio >= 4 * separadorAnchura || bolaX - radio <= 4 * separadorAnchura) return true
        }
        //Fila 6
        if (bolaY>=separadorAltura*5 && bolaY<=separadorAltura*6) {
            if ( bolaX+radio >= separadorAnchura || bolaX-radio <= separadorAnchura ) return true
        }
        //Fila 7
        if(bolaY>=separadorAltura*6 && bolaY<=separadorAltura*7) {
            if (bolaX + radio >= 2 * separadorAnchura || bolaX - radio <= 2 * separadorAnchura) return true
            if (bolaX + radio >= 3 * separadorAnchura || bolaX - radio <= 3 * separadorAnchura) return true
        }
        //Fila 8
        if(bolaY>=separadorAltura*7 && bolaY<=separadorAltura*8){
            if ( bolaX+radio >= 2*separadorAnchura || bolaX-radio <= 2*separadorAnchura ) return true
            if ( bolaX+radio >= 4*separadorAnchura || bolaX-radio <= 4*separadorAnchura ) return true
        }
        //Fila 9
        if(bolaY>=separadorAltura*8 && bolaY<=separadorAltura*9) {
            if (bolaX <= separadorAnchura){
                if (bolaX + radio > separadorAnchura) return true
            } else if (bolaX > separadorAnchura && bolaX < 2*separadorAnchura){
                if (bolaX - radio <= separadorAnchura) return true
            } else if (bolaX > 2*separadorAnchura && bolaX < 4*separadorAnchura){
                if (bolaX+radio > 4*separadorAnchura){ bolaX-20; return true }
                if (bolaX-radio < 2*separadorAnchura){ bolaX+20; return true }
            }else if(bolaX > 4*separadorAnchura){
                if (bolaX-radio < 4*separadorAnchura) return true
            }
        }
        //Fila 10
        if(bolaY>=separadorAltura*9 && bolaY<=separadorAltura*10) {
            if (bolaX >= 3*separadorAnchura){
                if (bolaX - radio <= 3 * separadorAnchura) return true
            } else {
                if (bolaX + radio >= 3 * separadorAnchura) return true
            }
        }
        return false
    }

    private fun colisionY(): Boolean{
        if ( bolaY-radio <= 0f || bolaY+radio>=separadorAltura*10 ) return true
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (!bandera) {
                timer.start()
                bandera = true
            }
        }
        invalidate()
        return true
    }

}