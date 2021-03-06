package com.gdsc.timerservice.api.dtos.timerhistory;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageRecord {

	private Emoji emoji;

	private long totalTimeSeconds;

}
