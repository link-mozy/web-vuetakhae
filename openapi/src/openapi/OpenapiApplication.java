package openapi;

import java.io.BufferedInputStream;
import java.net.URL;

import model.dto.FoodSafeManualDto;
import model.dto.FoodSafeRecipeDto;
import model.service.FoodSafeService;
import model.service.FoodSafeServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OpenapiApplication {

	private static final String BASE_URL = "http://openapi.foodsafetykorea.go.kr/api/";
	private static final String BASE_KEY = "87bbd3eb96fa4636a4fb";
	static int START_INDEX = 1;
	static int END_INDEX = 1000;

	private FoodSafeService foodSafeService;
	
	public OpenapiApplication() throws Exception {
		foodSafeService = new FoodSafeServiceImpl();
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(readUrl());
	    JSONObject json =  (JSONObject) jsonobject.get("COOKRCP01");
	    JSONObject jsonResult =  (JSONObject) json.get("RESULT");
	    boolean successFlag = jsonResult.get("CODE").equals("INFO-000");
	    String jsonTotal =  (String) json.get("total_count");

	    String manual = "MANUAL";
	    String manualImg = "MANUAL_IMG";

		FoodSafeRecipeDto foodSafeRecipeDto = null;
		FoodSafeManualDto foodSafeManualDto = null;

		// successFlag 값을 기준으로 실행해야함...
	    JSONArray array = (JSONArray)json.get("row");
		for(int i = 0 ; i < array.size(); i++){

			JSONObject entity = (JSONObject)array.get(i);
			// 확인 출력
			foodSafeRecipeDto = new FoodSafeRecipeDto(entity);
			foodSafeService.insertRecipe(foodSafeRecipeDto);

			int j = 1;
			for(; j < 20; j++) {
				String manualText = (String) entity.get(manual + String.format("%02d",j));
				String manualImagePath = (String) entity.get(manualImg + String.format("%02d",j));
				if(manualText.isEmpty()) {
					break;
				}
				foodSafeManualDto = new FoodSafeManualDto(j, foodSafeRecipeDto.getRcpSeq()); // j: manualId, i: recipeSeq
				foodSafeManualDto.setManualText(manualText);
				if(!manualImagePath.isEmpty()) {
					foodSafeManualDto.setManualImg(manualImagePath);
				}
				foodSafeService.insertManual(foodSafeManualDto);
			}
	    }
	}
	
	
	private static String readUrl() throws Exception {
		StringBuilder apiUrl = new StringBuilder();
		apiUrl.append(BASE_URL)
			.append(BASE_KEY).append("/")		// 키 입력
			.append("COOKRCP01").append("/")	// 서비스명 입력
			.append("json").append("/")			// 요청파일 타입 입력
			.append(START_INDEX).append("/")	// 요청 시작 위치
			.append(END_INDEX);					// 요청 종료 위치
		
        BufferedInputStream reader = null;
        try {
            URL url = new URL(apiUrl.toString());
            reader = new BufferedInputStream(url.openStream());
            StringBuffer buffer = new StringBuffer();
            int i;
            byte[] b = new byte[4096];
            while( (i = reader.read(b)) != -1){
              buffer.append(new String(b, 0, i));
            }
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
	
	public static void main(String[] args) {
		try {
			new OpenapiApplication();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
