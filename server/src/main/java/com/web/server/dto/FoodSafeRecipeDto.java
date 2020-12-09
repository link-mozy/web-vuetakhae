package com.web.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSafeRecipeDto {
	
	private int rcpSeq;
	private String rcpNm;
	private String rcpPartsDtls;
	private String attFileNoMain;
	private String attFileNoMk;
	private String hashTag;

	private List<FoodSafeManualDto> manuals;
}
