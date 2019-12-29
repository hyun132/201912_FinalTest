package com.example.a201912_finaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    val winLottoNumArr = ArrayList<Int>()

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
        }

    }

    fun makeWinLottoNum(){
//        기존의 당첨번호 모두 삭제
//        6개의 당첨 번호 생성 => 6번 반복
//        랜덤으로 숫자를 생성=> 아무 제약 없는 랜덤이 아니라 => 1~45 의 범위./ 중복 허용 x 제약조건.
//        제약조건을 통과한다면 => 당첨번호 목록으로 추가. => 배열(ArrayList)을 사용.
//        작은 숫자부터 나타나도록 정렬
//        여기까지 완료되면 6개의 텍스트뷰에 반영.
        winLottoNumArr.clear()
        for (i in 0..5){
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

        for (num in winLottoNumArr){
            Log.d("당첨번호:","${num}")
        }

    }

    override fun setValues() {
        }

}
