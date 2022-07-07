package com.gdsc.timerservice.api.dtos.timerhistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class TimerStatistics {

	private Integer month;

	private Integer week;

	private Integer day;

	private List<EmojiAndTotalTime> emojiAndTotalTimeList = new ArrayList<>();
}