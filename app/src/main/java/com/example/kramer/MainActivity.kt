    package com.example.kramer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout


    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val butt: Button = findViewById(R.id.work)
        val hello: TextView = findViewById(R.id.hello)
        val f: TextInputLayout = findViewById(R.id.f)
        val s: TextInputLayout = findViewById(R.id.s)
        val t: TextInputLayout = findViewById(R.id.t)
        butt.setOnClickListener {
            val (matr, results) = getData_app(f, s, t)
            val (formMatr,vars) = format(matr)
            val det = detKramer3x(formMatr)
            if (det == 0) {
                hello.setText("Error: Det. is zero(")
            }
            val res = kramer(formMatr, results, det)
            hello.setText("Results: "+vars[0]+"="+res[0]+","+vars[1]+"="+res[1]+","+vars[2]+"="+res[2])
        }
    }
}
/*
    fun main(){
        println("Kramer's algorithm!")
        println("Authors: Andrey, Maksim")
        println("Write your 3 equations below")
        val (matr,results)=getData()
        val formMatr=format(matr)
        val det=detKramer3x(formMatr)
        if(det==0){
            print("Определитель нулевой( выход")
        }
        val res = kramer(formMatr,results,det).toString()
        print("Значения переменных $res")

    }

 */


    data class KrData(var matr: ArrayList<List<String>>, var results: ArrayList<Int>)
    data class KrDataFormatted(var matr: ArrayList<ArrayList<Int>>, var vars: ArrayList<String>)
    /*
    fun getData(): KrData {
        val matr: ArrayList<List<String>> = arrayListOf()
        val results: ArrayList<Int> = arrayListOf()
        val reg=Regex("(?=[+-])")
        repeat(3) {
            val stringlistfull = readLine()!!.split("=")
            var stringlist=stringlistfull[0].split(reg)
            if(stringlist[0]==""){
                stringlist=stringlist.drop(1)
            }
            matr.add(stringlist)
            results.add(stringlistfull[1].toInt())
        }
        return KrData(matr,results)
    }

    */
    fun getData_app(f:TextInputLayout,s:TextInputLayout,t:TextInputLayout): KrData {
        val matr: ArrayList<List<String>> = arrayListOf()
        val results: ArrayList<Int> = arrayListOf()
        val reg=Regex("(?=[+-])")

        val fstringlistfull = f.getEditText()!!.getText().split("=")
        var fstringlist=fstringlistfull[0].split(reg)
        if(fstringlist[0]==""){
            fstringlist=fstringlist.drop(1)
        }
        matr.add(fstringlist)
        results.add(fstringlistfull[1].toInt())

        val sstringlistfull = s.getEditText()!!.getText().split("=")
        var sstringlist=sstringlistfull[0].split(reg)
        if(sstringlist[0]==""){
            sstringlist=sstringlist.drop(1)
        }
        matr.add(sstringlist)
        results.add(sstringlistfull[1].toInt())

        val tstringlistfull = t.getEditText()!!.getText().split("=")
        var tstringlist=tstringlistfull[0].split(reg)
        if(tstringlist[0]==""){
            tstringlist=tstringlist.drop(1)
        }
        matr.add(tstringlist)
        results.add(tstringlistfull[1].toInt())

        return KrData(matr,results)
    }

    fun detKramer2x(matr:ArrayList<ArrayList<Int>>):Int{
        val a=matr[0][0]
        val b=matr[0][1]
        val c=matr[1][0]
        val d=matr[1][1]
        return(a*d-b*c)
    }

    fun detKramer3x(matr:ArrayList<ArrayList<Int>>):Int{
        val a=matr[0][0]
        val b=matr[0][1]
        val c=matr[0][2]
        val first=detKramer2x(arrayListOf(arrayListOf(matr[1][1],matr[1][2]),arrayListOf(matr[2][1],matr[2][2])))
        val second=detKramer2x(arrayListOf(arrayListOf(matr[1][0],matr[1][2]),arrayListOf(matr[2][0],matr[2][2])))
        val third=detKramer2x(arrayListOf(arrayListOf(matr[1][0],matr[1][1]),arrayListOf(matr[2][0],matr[2][1])))
        return(a*first-b*second+c*third)
    }

    fun kramer(matr:ArrayList<ArrayList<Int>>,answers:ArrayList<Int>,det:Int):List<Int> {
        val matrX:ArrayList<ArrayList<Int>> = arrayListOf(
            arrayListOf(answers[0],matr[0][1],matr[0][2]),
            arrayListOf(answers[1],matr[1][1],matr[1][2]),
            arrayListOf(answers[2],matr[2][1],matr[2][2]))
        val matrY:ArrayList<ArrayList<Int>> = arrayListOf(
            arrayListOf(matr[0][0],answers[0],matr[0][2]),
            arrayListOf(matr[1][0],answers[1],matr[1][2]),
            arrayListOf(matr[2][0],answers[2],matr[2][2]))
        val matrZ:ArrayList<ArrayList<Int>> = arrayListOf(
            arrayListOf(matr[0][0],matr[0][1],answers[0]),
            arrayListOf(matr[1][0],matr[1][1],answers[1]),
            arrayListOf(matr[2][0],matr[2][1],answers[2]))
        val detX=detKramer3x(matrX)
        val detY=detKramer3x(matrY)
        val detZ=detKramer3x(matrZ)
        val x=detX/det
        val y=detY/det
        val z=detZ/det
        return(listOf(x,y,z))
    }

    fun format(matr:ArrayList<List<String>>):KrDataFormatted {
        val numbers= listOf('1','2','3','4','5','6','7','8','9','-')
        val formattedMatr: ArrayList<ArrayList<Int>> = arrayListOf()
        val assocMatr: ArrayList<ArrayList<String>> = arrayListOf()
        repeat(matr.size) { i ->
            assocMatr.add(arrayListOf())
            val finalline:ArrayList<Int> = arrayListOf()
            for (ii in matr[i]) {
                var finalelement = ""
                for (symb in ii){
                    if(symb in numbers){
                        finalelement += symb
                    }
                    else{
                        if(symb!='+'){
                            assocMatr[i].add(symb.toString())
                        }
                    }
                }
                if(finalelement=="-"){
                    finalelement="-1"
                }
                if(finalelement=="+"){
                    finalelement="+1"
                }
                if(finalelement==""){
                    finalelement="+1"
                }
                finalline.add(finalelement.toInt())
            }
            formattedMatr.add(finalline)
        }
        repeat(3){x->
            var ii=0
            for(i in assocMatr[x]){
                ii+=1
                if(i in assocMatr[x].drop(ii)){
                    val n=assocMatr[x].lastIndexOf(i)
                    formattedMatr[x][ii-1]+=formattedMatr[x][n]
                }
            }
        }
        return KrDataFormatted(formattedMatr,assocMatr[0])
    }