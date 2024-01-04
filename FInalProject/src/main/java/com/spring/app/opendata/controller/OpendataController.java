package com.spring.app.opendata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/opendata/*")
public class OpendataController {

	// === #203. 기상청 공공데이터(오픈데이터)를 가져와서 날씨정보 보여주기 === //
	//  @RequestMapping(value="weatherXML.action", method = {RequestMethod.GET})
	//  또는	
		@GetMapping("weatherXML.gw")
		public String weatherXML() {
			return "opendata/weatherXML";
			//   /board/src/main/webapp/WEB-INF/views/opendata/weatherXML.jsp 파일을 생성한다. 
		}
		
		
		
		//////////////////////////////////////////////////////
		@ResponseBody
		@PostMapping(value="weatherXMLtoJSON.gw", produces="text/plain;charset=UTF-8") 
		public String weatherXMLtoJSON(HttpServletRequest request) { 
		
		String str_jsonObjArr = request.getParameter("str_jsonObjArr");
			/*  확인용
			//	System.out.println(str_jsonObjArr);
			//  [{"locationName":"속초","ta":"2.4"},{"locationName":"북춘천","ta":"-2.3"},{"locationName":"철원","ta":"-2.0"},{"locationName":"동두천","ta":"-0.7"},{"locationName":"파주","ta":"-1.2"},{"locationName":"대관령","ta":"-3.0"},{"locationName":"춘천","ta":"-1.6"},{"locationName":"백령도","ta":"1.1"},{"locationName":"북강릉","ta":"3.4"},{"locationName":"강릉","ta":"4.3"},{"locationName":"동해","ta":"4.1"},{"locationName":"서울","ta":"-0.3"},{"locationName":"인천","ta":"-0.2"},{"locationName":"원주","ta":"-2.2"},{"locationName":"울릉도","ta":"4.2"},{"locationName":"수원","ta":"1.4"},{"locationName":"영월","ta":"-4.5"},{"locationName":"충주","ta":"-3.0"},{"locationName":"서산","ta":"2.5"},{"locationName":"울진","ta":"3.9"},{"locationName":"청주","ta":"-0.7"},{"locationName":"대전","ta":"2.9"},{"locationName":"추풍령","ta":"3.2"},{"locationName":"안동","ta":"-2.3"},{"locationName":"상주","ta":"1.5"},{"locationName":"포항","ta":"4.7"},{"locationName":"군산","ta":"2.6"},{"locationName":"대구","ta":"1.6"},{"locationName":"전주","ta":"5.7"},{"locationName":"울산","ta":"4.0"},{"locationName":"창원","ta":"4.4"},{"locationName":"광주","ta":"2.8"},{"locationName":"부산","ta":"4.3"},{"locationName":"통영","ta":"5.7"},{"locationName":"목포","ta":"4.5"},{"locationName":"여수","ta":"5.6"},{"locationName":"흑산도","ta":"7.8"},{"locationName":"완도","ta":"7.3"},{"locationName":"고창","ta":"3.5"},{"locationName":"순천","ta":"5.3"},{"locationName":"홍성","ta":"1.7"},{"locationName":"제주","ta":"8.5"},{"locationName":"고산","ta":"9.1"},{"locationName":"성산","ta":"7.5"},{"locationName":"서귀포","ta":"8.8"},{"locationName":"진주","ta":"3.5"},{"locationName":"강화","ta":"-0.9"},{"locationName":"양평","ta":"-1.3"},{"locationName":"이천","ta":"-2.0"},{"locationName":"인제","ta":"-0.5"},{"locationName":"홍천","ta":"-2.6"},{"locationName":"태백","ta":"-1.5"},{"locationName":"정선군","ta":"-1.9"},{"locationName":"제천","ta":"-4.4"},{"locationName":"보은","ta":"-1.2"},{"locationName":"천안","ta":"-1.0"},{"locationName":"보령","ta":"3.6"},{"locationName":"부여","ta":"0.6"},{"locationName":"금산","ta":"3.7"},{"locationName":"세종","ta":"-0.8"},{"locationName":"부안","ta":"5.8"},{"locationName":"임실","ta":"1.6"},{"locationName":"정읍","ta":"5.8"},{"locationName":"남원","ta":"0.1"},{"locationName":"장수","ta":"1.4"},{"locationName":"고창군","ta":"4.3"},{"locationName":"영광군","ta":"4.2"},{"locationName":"김해시","ta":"4.1"},{"locationName":"순창군","ta":"1.0"},{"locationName":"북창원","ta":"5.9"},{"locationName":"양산시","ta":"3.9"},{"locationName":"보성군","ta":"5.1"},{"locationName":"강진군","ta":"4.4"},{"locationName":"장흥","ta":"4.9"},{"locationName":"해남","ta":"6.2"},{"locationName":"고흥","ta":"5.4"},{"locationName":"의령군","ta":"5.7"},{"locationName":"함양군","ta":"4.9"},{"locationName":"광양시","ta":"5.4"},{"locationName":"진도군","ta":"7.0"},{"locationName":"봉화","ta":"-3.3"},{"locationName":"영주","ta":"-4.3"},{"locationName":"문경","ta":"-3.1"},{"locationName":"청송군","ta":"0.5"},{"locationName":"영덕","ta":"4.7"},{"locationName":"의성","ta":"-0.6"},{"locationName":"구미","ta":"2.3"},{"locationName":"영천","ta":"3.4"},{"locationName":"경주시","ta":"4.5"},{"locationName":"거창","ta":"0.8"},{"locationName":"합천","ta":"0.7"},{"locationName":"밀양","ta":"1.1"},{"locationName":"산청","ta":"4.2"},{"locationName":"거제","ta":"5.8"},{"locationName":"남해","ta":"6.2"}]  
			*/
			//	return str_jsonObjArr;  -- 지역 97개 모두 차트에 그리기에는 너무 많으므로 아래처럼 작업을 하여 지역을  21개(String[] locationArr 임)로 줄여서 나타내기로 하겠다.
		
		str_jsonObjArr = str_jsonObjArr.substring(1, str_jsonObjArr.length()-1);
		
		String[] arr_str_jsonObjArr = str_jsonObjArr.split("\\},");
		
		for(int i=0; i<arr_str_jsonObjArr.length; i++) {
			arr_str_jsonObjArr[i] += "}";
		}
		
		/*   
		for(String jsonObj : arr_str_jsonObjArr) {
			System.out.println(jsonObj);
		}
		*/	
		// {"locationName":"속초","ta":"15.7"}
		// {"locationName":"북춘천","ta":"24.9"}
		// {"locationName":"철원","ta":"23.8"}
		// {"locationName":"동두천","ta":"26.3"}
		// {"locationName":"파주","ta":"25.5"}
		// {"locationName":"대관령","ta":"10.8"}
		// {"locationName":"춘천","ta":"26.7"}
		// {"locationName":"백령도","ta":"13.8"}
		// ........ 등등  
		// {"locationName":"밀양","ta":"24.7"}
		// {"locationName":"산청","ta":"24.2"}
		// {"locationName":"거제","ta":"21.0"}
		// {"locationName":"남해","ta":"22.7"}}
		
		
		String[] locationArr = {"서울","인천","수원","춘천","강릉","청주","홍성","대전","안동","포항","대구","전주","울산","부산","창원","여수","광주","목포","제주","울릉도","백령도"};
		String result = "[";
		
		for(String jsonObj : arr_str_jsonObjArr) {
		
			for(int i=0; i<locationArr.length; i++) {
				if( jsonObj.indexOf(locationArr[i]) >= 0 && jsonObj.indexOf("북") == -1 && jsonObj.indexOf("서청주") == -1 ) { // 북춘천,춘천,북강릉,강릉,북창원,창원이 있으므로  "북" 이 있는 것은 제외하도록 한다. 또한 서청주(예)도 제외하도록 한다.
					result += jsonObj+",";  // [{"locationName":"춘천","ta":"26.7"},{"locationName":"백령도","ta":"13.8"}, ..... {"locationName":"제주","ta":"18.9"}, 
					break;
				}
			}
		}// end of for------------------------------
		
		result = result.substring(0, result.length()-1);  // [{"locationName":"춘천","ta":"26.7"},{"locationName":"백령도","ta":"13.8"}, ..... {"locationName":"제주","ta":"18.9"}
		result = result + "]";                            // [{"locationName":"춘천","ta":"26.7"},{"locationName":"백령도","ta":"13.8"}, ..... {"locationName":"제주","ta":"18.9"}]
		
		/*  확인용
		System.out.println(result);
		// [{"locationName":"춘천","ta":"26.7"},{"locationName":"백령도","ta":"13.8"},{"locationName":"강릉","ta":"18.4"},{"locationName":"서울","ta":"27.7"},{"locationName":"인천","ta":"23.8"},{"locationName":"울릉도","ta":"19.2"},{"locationName":"수원","ta":"26.5"},{"locationName":"청주","ta":"26.8"},{"locationName":"대전","ta":"26.4"},{"locationName":"안동","ta":"24.3"},{"locationName":"포항","ta":"19.4"},{"locationName":"대구","ta":"22.7"},{"locationName":"전주","ta":"26.3"},{"locationName":"울산","ta":"20.7"},{"locationName":"창원","ta":"21.9"},{"locationName":"광주","ta":"25.6"},{"locationName":"부산","ta":"22.0"},{"locationName":"목포","ta":"23.2"},{"locationName":"여수","ta":"23.0"},{"locationName":"홍성","ta":"25.0"},{"locationName":"제주","ta":"18.9"}]
		
		*/
		return result;	
	} 		
	
}
