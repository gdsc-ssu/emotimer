import 'package:dio/dio.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:retrofit/retrofit.dart';

part 'user-api-client.g.dart';

@RestApi(baseUrl: "https://emotimer.ml")
abstract class UserApiClient {
  factory UserApiClient(Dio dio, {String baseUrl}) = _UserApiClient;

  @GET("/api/users/me")
  Future<GetUserResponse> getUser();

  @GET("/api/users/me/setting")
  Future<GetUserSettingResponse> getUserSetting();

  @PATCH("/api/users/me/setting")
  Future<void> patchUserSetting(UserSettingRequest request);

}

@JsonSerializable()
class GetUserResponse {
  String username;
  String email;
  GetUserResponse({required this.username, required this.email});

  factory GetUserResponse.fromJson(Map<String, dynamic> json) => _$GetUserResponseFromJson(json);

  Map<String, dynamic> toJson() => _$GetUserResponseToJson(this);
}

@JsonSerializable()
class GetUserSettingResponse {
  int timerDuration;
  int restDuration;
  bool restAutoStart;

  GetUserSettingResponse({required this.timerDuration, required this.restDuration, required this.restAutoStart});

  factory GetUserSettingResponse.fromJson(Map<String, dynamic> json) => _$GetUserSettingResponseFromJson(json);

  Map<String, dynamic> toJson() => _$GetUserSettingResponseToJson(this);
}


@JsonSerializable()
class UserSettingRequest {
  int timerDuration;
  int restDuration;
  bool restAutoStart;

  UserSettingRequest({required this.timerDuration, required this.restDuration, required this.restAutoStart});

  factory UserSettingRequest.fromJson(Map<String, dynamic> json) => _$UserSettingRequestFromJson(json);

  Map<String, dynamic> toJson() => _$UserSettingRequestToJson(this);
}
