package model.dto;

import org.json.simple.JSONObject;

public class FoodSafeRecipeDto {

    private int    rcpSeq;          // 레시피 번호
    private String rcpNm;           // 레시피 이름
    private String hashTag;         // 헤쉬 테그
    private String attFileNoMain;   // 메인 사진
    private String attFileNoMk;     // 작은 사진
    private String rcpPartsDtls;    // 요리 재료

    public FoodSafeRecipeDto() { }

    public FoodSafeRecipeDto(JSONObject entity) {
        this.rcpSeq = Integer.parseInt((String) entity.get("RCP_SEQ"));
        this.rcpNm = (String) entity.get("RCP_NM");
        this.hashTag = (String) entity.get("HASH_TAG");
        this.attFileNoMain = (String) entity.get("ATT_FILE_NO_MAIN");
        this.attFileNoMk = (String) entity.get("ATT_FILE_NO_MK");
        this.rcpPartsDtls = (String) entity.get("RCP_PARTS_DTLS");
    }

    public int getRcpSeq() {
        return rcpSeq;
    }

    public void setRcpSeq(int rcpSeq) {
        this.rcpSeq = rcpSeq;
    }

    public String getRcpNm() {
        return rcpNm;
    }

    public void setRcpNm(String rcpNm) {
        this.rcpNm = rcpNm;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getAttFileNoMain() {
        return attFileNoMain;
    }

    public void setAttFileNoMain(String attFileNoMain) {
        this.attFileNoMain = attFileNoMain;
    }

    public String getAttFileNoMk() {
        return attFileNoMk;
    }

    public void setAttFileNoMk(String attFileNoMk) {
        this.attFileNoMk = attFileNoMk;
    }

    public String getRcpPartsDtls() {
        return rcpPartsDtls;
    }

    public void setRcpPartsDtls(String rcpPartsDtls) {
        this.rcpPartsDtls = rcpPartsDtls;
    }

    @Override
    public String toString() {
        return "FoodSafeRecipeDto{" +
                "rcpSeq=" + rcpSeq +
                ", rcpNm='" + rcpNm + '\'' +
                ", hashTag='" + hashTag + '\'' +
                ", attFileNoMain='" + attFileNoMain + '\'' +
                ", attFileNoMk='" + attFileNoMk + '\'' +
                ", rcpPartsDtls='" + rcpPartsDtls + '\'' +
                '}';
    }
}
