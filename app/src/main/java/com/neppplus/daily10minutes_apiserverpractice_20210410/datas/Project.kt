package com.neppplus.daily10minutes_apiserverpractice_20210410.datas

import androidx.core.os.persistableBundleOf
import org.json.JSONObject

class Project(
    var id : Int,
    var title : String,
    var imageUrl : String,
    var description : String) {

//    보조 생성자 추가 => Project() 만으로도 만들 수 있게

    constructor() : this(0, "", "", "")

//    기능 추가 => JSON 넣으면(input) : Project로 변환 (return) => 단순 기능. companion onject 이용

    companion object{

        fun getProjectFromJson(jsonObj : JSONObject) : Project{
            val project = Project()

//            jsonObj에서 정보 추출 => Project의 하위 항목 반영

            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("img_url")
            project.description = jsonObj.getString("description")

//            완성된 project가 결과로 나가도록

            return project
        }

    }


}