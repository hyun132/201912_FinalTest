package com.example.a201912_finaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    var totalWinMoney = 0L  // 0을 long타입으로 대입 => 그냥 0은 Int로 간주, 50억과 같은 킅 수 담지 못함
    var usedMoney = 0L


    val winLottoNumArr = ArrayList<Int>()
    var bonusNumber = 0 // 보너스 번호를 담기 위 한 변수
    val winLottoNumTextViewList = ArrayList<TextView>()
    val myLottoNumTextViewList = ArrayList<TextView>()

    val mHnadler = Handler()
    var isNowBuying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)

        setValues()
        setupEvents()
    }

    override fun setupEvents() {

        buyOneLottoBtn.setOnClickListener {
//            6개의 숫자를 랜덤으로 생성 => 텍스트뷰 6개에 반영
            makeWinLottoNum()
//            몇등인지 판단하기
            checkLottoRank()
        }

        buyAutoLottoBtn.setOnClickListener {
            if(!isNowBuying) {
                buyLottoLoop()
                isNowBuying = true
                buyAutoLottoBtn.text="자동구매 일시 정지"
            }else{
                mHnadler.removeCallbacks(buyingLottoRunnable)
                isNowBuying=false
                buyAutoLottoBtn.text = "자동구매 재개"
            }
        }

    }
    fun buyLottoLoop() {
    mHnadler.post(buyingLottoRunnable)

    }

    val buyingLottoRunnable = object : Runnable{
        override fun run() {

            if (usedMoney < 10000000){
                makeWinLottoNum()
                checkLottoRank()
                buyLottoLoop()
            }else{
                runOnUiThread{
                    Toast.makeText(mContext,"로또 구매를 종료합니다.",Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    fun checkLottoRank(){

//        등수 판단?
//        내가 가진 숫자들과 / 당첨번호를 하나하나 비교해서, 같은 숫자가 몇개인지? 세어야함.
//        이 갯수에 따라서 등수를 판정.
//        갯수가 6개 : 1등, 5개: 3등, 4개: 4등 3개 : 5등

//        같은 숫자의 갯수를 세어주는 변수
        var correctCount=0

//        내가 가진 숫자들을 모두 꺼내보자.
//        몇 개의 숫자를 맞췃는지를 correctCount에 저장
        for(myNumTxt in myLottoNumTextViewList){
//            각 텍스트뷰에 적힌 숫자가 String형태 => Int로 변환
            val num = myNumTxt.text.toString().toInt()

            Log.d("적혀있는 숫자들",num.toString())

//            당첨번호를 둘러보자
            for (winNum in winLottoNumArr){
//                같은 숫자를 찾았다면
                if(num==winNum) {
//                    당첨번호에 들어있다! 갯수 1 증가
                    correctCount++
                    break
                }
            }
        }

        if (correctCount==6){
//            1등 당첨 +=50억
            totalWinMoney +=5000000000
        }else if(correctCount==5){

//            보너스 번호가 맞다면 : 2등
//              내 번호를 둘러보는데 보너스 번호와 같은게 있다
            var isSecondRank = false
            for(myNumTxt in myLottoNumTextViewList){
                val myNumber = myLottoNumTextViewList.toString().toInt()
                if(myNumber==bonusNumber){
                    isSecondRank = true
                }
            }
            if(isSecondRank){
                totalWinMoney+=50000000
            }else{
//            3등 당첨 +=150만원
            totalWinMoney +=1500000
            }
        }else if(correctCount==4){
            totalWinMoney +=50000
        }else if(correctCount==3){
            totalWinMoney +=5000
        }else{
//            꽝
        }

//        금액을 세자리마다 , 를 찍도록 가공
        totalWinMoneyTxt.text = String.format("%,d 원",totalWinMoney)

//        사용금액 : 한장 살때마다 천원씩 증가.

        usedMoney+=1000
        usedMoneyTxt.text = String.format("%,d 원",usedMoney)
    }

    fun makeWinLottoNum(){
//        기존의 당첨번호 모두 삭제
//        6개의 당첨 번호 생성 => 6번 반복
//        랜덤으로 숫자를 생성=> 아무 제약 없는 랜덤이 아니라 => 1~45 의 범위./ 중복 허용 x 제약조건.
//        제약조건을 통과한다면 => 당첨번호 목록으로 추가. => 배열(ArrayList)을 사용.
//        작은 숫자부터 나타나도록 정렬
//        여기까지 완료되면 6개의 텍스트뷰에 반영.
        winLottoNumArr.clear()
//        기존의 보너스번호도 0으로 돌려주자
        bonusNumber=0

//        당첨번호 6개를 만들기 위한 for문
        for (i in 0..5){
//            제약조건을 만족할 때 까지(몇번 돌아야 만족인지 모름.) 무한 반복 while
            while (true) {
                val randomInt = Random().nextInt(45) + 1  // 0~44의 랜덤값 + 1 => 1~45의 랜덤

                var isDuplOk = true

                for (num in winLottoNumArr) {
                    if (randomInt == num) {
//                    이미 뽑아둔 당첨번호와, 랜덤으로 뽑은 번호가 같다 => 이미 나온 번호를 또 뽑았다
//                  중복검사 통과 x
                        isDuplOk = false
                        break
                    }
                }
                if (isDuplOk) {
                    winLottoNumArr.add(randomInt)
                    break
                }
            }
        }
//        Collections 클래스의 기능을 이용해서 ArrayList내부의 값을 정렬.
        Collections.sort(winLottoNumArr)

//        보너스 번호도 생성
//        1~45의 값, 기존 당첨번호 6개와 중복되지 않아야함. => 1개만
//        써도 괜찮은 보너스번호가 나올때까지 무한반복
        while (true){
            val tempNum = Random().nextInt(45)+1

            var isDuplOk=true

            for (winNum in winLottoNumArr){
                if(tempNum == winNum){
                    isDuplOk=false
                }
            }

            if(isDuplOk==true){
                bonusNumber = tempNum
//
                break
            }
        }

//        6개의 당첨번호 / 각 자리의 텍스트뷰를 매칭 for문
        for (i in 0..5){
//            상황에 맞는 텍스트뷰 / 당첨번호를 뽑아와서
            val tempTextView = winLottoNumTextViewList.get(i)
            val winNum = winLottoNumArr.get(i)
//            값으로 세팅
            tempTextView.text = winNum.toString()
        }

    }

    override fun setValues() {

//        당첨번호 텍스트뷰들을 배열로 담아둠
        winLottoNumTextViewList.add(lottoNumTxt01)
        winLottoNumTextViewList.add(lottoNumTxt02)
        winLottoNumTextViewList.add(lottoNumTxt03)
        winLottoNumTextViewList.add(lottoNumTxt04)
        winLottoNumTextViewList.add(lottoNumTxt05)
        winLottoNumTextViewList.add(lottoNumTxt06)

//        내가 뽑은 번호 텍스트뷰들을 배열로 담아둠
        myLottoNumTextViewList.add(myNumTxt01)
        myLottoNumTextViewList.add(myNumTxt02)
        myLottoNumTextViewList.add(myNumTxt03)
        myLottoNumTextViewList.add(myNumTxt04)
        myLottoNumTextViewList.add(myNumTxt05)
        myLottoNumTextViewList.add(myNumTxt06)
    }

}
