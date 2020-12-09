package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSafeManualDto {
	private int manualId;
	private int rcpSeq;
	private String manualText;
	private String mnualImg;
}
