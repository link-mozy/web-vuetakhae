package model.dto;

import org.json.simple.JSONObject;

public class FoodSafeManualDto {
    private int manualId;
    private int rcpSeq;
    private String manualText;
    private String manualImg;

    public FoodSafeManualDto() {
    }

    public FoodSafeManualDto(int manualId, int rcpSeq) {
        this.manualId = manualId;
        this.rcpSeq = rcpSeq;
    }

    public int getManualId() {
        return manualId;
    }

    public void setManualId(int manualId) {
        this.manualId = manualId;
    }

    public int getRcpSeq() {
        return rcpSeq;
    }

    public void setRcpSeq(int rcpSeq) {
        this.rcpSeq = rcpSeq;
    }

    public String getManualText() {
        return manualText;
    }

    public void setManualText(String manualText) {
        this.manualText = manualText;
    }

    public String getManualImg() {
        return manualImg;
    }

    public void setManualImg(String manualImg) {
        this.manualImg = manualImg;
    }

    @Override
    public String toString() {
        return "FoodSafeManualDto{" +
                "manualId=" + manualId +
                ", rcpSeq=" + rcpSeq +
                ", manualText='" + manualText + '\'' +
                ", manualImg='" + manualImg + '\'' +
                '}';
    }
}
