package com.gdsc.timerservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emoji {

	RED_HEART("β€οΈ"), // κ°¬μ±
	CLOSED_BOOK("π"), // μ±
	SLIGHTLY_SMILING_FACE("π"), // κΈ°λΆμ’μ μΌ (μΌλ°)
	RICE("π"), // λ°₯(μμ¬)
	JOYSTICK("πΉ"), // κ²μ (μ¬λμκ°)
	CRYING_FACE("π₯²"), // μ¬νμΌ (μΌλ°)
	FLEXED_BICEPS("πͺ"), // μ΄λ
	ONE_HUNDRED("π―"), // κ³΅λΆ
	ANGER_SYMBOL("π’"), // νλλ μΌ (μΌλ°)
	ONCOMING_FIST("βοΈ"); // κ³΅λΆ

	private final String emoji;
}
