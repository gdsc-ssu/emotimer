import 'package:dio/dio.dart';
import 'package:gdsc_timer/domain/emoji.dart';
import 'package:gdsc_timer/store/timer-store.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:retrofit/retrofit.dart';

part 'timer-api-client.g.dart';

@RestApi(baseUrl: "https://emotimer.ml")
abstract class TimerApiClient {
  factory TimerApiClient(Dio dio, {String baseUrl}) = _TimerApiClient;

  @POST("/api/timer")
  Future<SetTimerResponse> setTimer(@Body() SetTimerRequest request);

  @GET("/api/timer")
  Future<GetTimerResponse> getTimer();
}

@JsonSerializable()
class SetTimerRequest {
  int totalTime;
  Emoji? emoji;

  SetTimerRequest({required this.totalTime, this.emoji});

  factory SetTimerRequest.fromJson(Map<String, dynamic> json) => _$SetTimerRequestFromJson(json);

  Map<String, dynamic> toJson() => _$SetTimerRequestToJson(this);
}

@JsonSerializable()
class SetTimerResponse {
  DateTime? startedAt;
  int totalTimeSeconds;
  int remainedSeconds;
  Emoji? emoji;

  SetTimerResponse({this.startedAt, required this.totalTimeSeconds, required this.remainedSeconds, this.emoji});

  factory SetTimerResponse.fromJson(Map<String, dynamic> json) => _$SetTimerResponseFromJson(json);

  Map<String, dynamic> toJson() => _$SetTimerResponseToJson(this);
}

@JsonSerializable()
class GetTimerResponse {
  DateTime? startedAt;
  int totalTimeSeconds;
  int? remainedSeconds;
  Emoji emoji;
  TimerStatus timerStatus;

  GetTimerResponse(
      {required this.startedAt, required this.totalTimeSeconds, required this.remainedSeconds, required this.emoji, required this.timerStatus});

  factory GetTimerResponse.fromJson(Map<String, dynamic> json) => _$GetTimerResponseFromJson(json);

  Map<String, dynamic> toJson() => _$GetTimerResponseToJson(this);
}
