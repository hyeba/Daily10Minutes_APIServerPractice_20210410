package com.neppplus.daily10minutes_apiserverpractice_20210410.utils

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    화면(액티비티)의 입장에서, 서버에 다녀오면 할 행동을 적는 가이드북.
//    행동 지침을 기록하는 개념: Interface

    interface JsonResponseHandler {
        fun onResponse(jsonObj : JSONObject)
    }

    companion object {

//        이 중괄호 안에 적히는 변수 / 함수는
//        ServerUtil.변수 or 함수() 등으로 클래스 자체의 기능으로 활용 가능
//        JAVA: static 개념에 대응됨

//        서버의 호스트 주소 저장
        val HOST_URL = "http://15.164.153.174"

//        서버에 로그인을 요청하는 기능 => 함수로 만들고 사용하자
//        필요 재료: 이메일, 비번 + 로그인 결과에 대한 처리 방안(가이드북)

        fun postRequestLogin(email : String, pw : String, handler : JsonResponseHandler) {

//            어느 주소로 가야하나? 호스트 주소/ 기능 주소
//            ex) 로그인 => http://15.164.153.174/user HOST/user => 최종 주소 완성

            val urlString = "${HOST_URL}/user"


//            갈 때 어떤 파라미터를 가져가야 하나? POST Vs GET 에 따라 다르다
//            POST -formData에 데이터 첨부
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            모든 정보 종합 + 어떤 메쏘드?

            val request = Request.Builder()
                .url(urlString) // 어디로 가는지?
                .post(formData) // POST 방식 - 필요 데이터 (formData) 들고 가도록
                .build()

//            정리된 정보를 들고 => 실제 API 요청 진행

//            클라이언트로서 동작하는 코드를 쉽게 작성하도록 도와주는 라이브러리 : OkHttp
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    서버에 연결 자체를 실패 (서버를 접근할 수 없는 상황)
//                    데이터 요금 소진, 서버가 터짐 등등의 이유로 아예 연결 자체에 실패

//                    반대 - 로그인 비번 틀림, 회원가입 이메일 중복 등등 로직 실패 => 연결은 성공, 결과만 실패
//                    여기에서 처리하지 않음

                }

                override fun onResponse(call: Call, response: Response) {

//                    서버의 응답을 받아내는 데 성공한 경우
//                    응답 (response) > 내부의 본문(body)만 활용 > String 형태로 저장해보자

//                    toString() X, string() 활용!!
                    val bodyString = response.body!!.string()

//                    bodyString은 인코딩 되어있는 상태라 사람이 읽기 어려움 (한글 깨짐)
//                    bodyString을 => JSONObject로 변환시키면 읽을 수 있게 됨

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버 응답", jsonObj.toString())

//                    받아낸 서버 응답 내용은 => 여기(ServerUtil)서 활용하는게 아니라
//                    화면에서 UI에 반영하기 위한 재료로 사용
//                    ex. code : 400 => 로그인 실패 토스트 (메인)

//                    완성한 jsonObj 변수를 => 액티비티에 넘겨주자 => 파싱 등등 처리는 액티비티에서 작성
                    handler?.onResponse(jsonObj)

                }


            })

        }



    }

}