package com.example.numbaseball_kotlin

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val random = Random()

    val comRnd = ArrayList<Int>()

    val userRnd = ArrayList<Int>()

    var s : Int = 0
    var b : Int = 0
    var o : Int = 0

    var round = 0

    var context : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setComRndNum()




        btn_ok.setOnClickListener {

            if(s==3){
                showToast("게임을 더 즐기기 위해선 초기화를 해주세요")
                return@setOnClickListener
            }
            checkNum(et_ball1, et_ball2, et_ball3)

        }

        btn_reset.setOnClickListener {


            mShowDialog("초기화", "초기화 하시겠습니까?", "확인","취소", DialogInterface.OnClickListener { dialogInterface, i ->


                showToast("초기화 되었습니다")
                resetData()

                tv_context.text = ""
                context = ""
                round = 0
                setComRndNum()

            })


        }


        et_ball3.setOnEditorActionListener(getEditorActionListener(btn_ok))


    }

    fun mShowDialog(title:String , msg:String, pBtn:String, nBtn :String , dialog : DialogInterface.OnClickListener){

        var builder = AlertDialog.Builder(this)

        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton(pBtn,dialog)


        if(nBtn != ""){
            builder.setNegativeButton(nBtn, DialogInterface.OnClickListener { dialogInterface, i ->


            })
        }

        builder.show()

    }


    fun getEditorActionListener(view: View): TextView.OnEditorActionListener { // 키보드에서 done(완료) 클릭 시 , 원하는 뷰 클릭되게 하는 메소드
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }


    fun checkNum(vararg ets : EditText){

        userRnd.clear()

        var sumUserNum : String = ""

        for( et in ets){

            if(et.text.isEmpty()){
                Toast.makeText(baseContext,"값을 입력해주세요", Toast.LENGTH_SHORT).show()
                et.requestFocus()
                break
            }

            val userNum = et.text.toString().toInt()

            if(userRnd.contains(userNum)){
                Toast.makeText(baseContext,"중복된 값입니다 ", Toast.LENGTH_SHORT).show()
                et.requestFocus()
                break
            }else{
                userRnd.add(userNum)
                sumUserNum += userNum.toString()
            }




        }
        if(userRnd.size != 3){
            return
        }


        for( i in comRnd.indices){

            for(j in userRnd.indices){

                if(i==j && comRnd[i]==userRnd[j]){
                    s++
                }else if(comRnd[i]==userRnd[j]){
                    b++
                }

            }
        }


        if(s == 0 && b == 0){

            o = 3
        }


        round++

        context+= round.toString()+"회 / "+sumUserNum+" S : "+s+" B: "+b+" O : "+o+"\n"

        tv_context.setText(context)


        Log.e("QQQQQQQQQ : ", comRnd.toString()+" , "+ userRnd.toString()+" , s: "+ s+" ,b : "+ b+" , o: "+ o)




        if(s==3){
            mShowDialog("성공","성공하셨습니다 초기화 하시겠습니까?", "확인", "취소", DialogInterface.OnClickListener { dialogInterface, i ->


                showToast("초기화 되었습니다")
                resetData()

                tv_context.text = ""
                context = ""
                round = 0
                setComRndNum()

            })

        }else{
            resetData()
        }

        if(round == 9) {

            mShowDialog("실패","실패하셨습니다 초기화 하시겠습니까?", "확인", "취소", DialogInterface.OnClickListener { dialogInterface, i ->


                showToast("초기화 되었습니다")
                resetData()

                tv_context.text = ""
                context = ""
                round = 0
                setComRndNum()

            })

        }



    }


    fun resetData(){
        userRnd.clear()
        s = 0
        b = 0
        o = 0
        et_ball1.setText("")
        et_ball2.setText("")
        et_ball3.setText("")
        et_ball1.requestFocus()


    }
    fun setComRndNum(){

        comRnd.clear()

        comRnd.add(rand(0,9))

        while (comRnd.size != 3 ){

            val num = rand(0,9)

            if(!comRnd.contains(num)){
                comRnd.add(num)
            }

        }

    }



    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }


    fun showToast(msg : String){

        Toast.makeText(this@MainActivity,msg, Toast.LENGTH_SHORT).show()

    }
}