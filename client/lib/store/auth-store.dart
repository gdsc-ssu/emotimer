import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:gdsc_timer/api/user-api-client.dart';
import 'package:gdsc_timer/shared/common.dart';
import 'package:dio/dio.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:mobx/mobx.dart';

import '../domain/user-setting.dart';

part 'auth-store.g.dart';

final authStorage = FlutterSecureStorage();

class AuthStore = _AuthStore with _$AuthStore;

abstract class _AuthStore with Store {
  @observable
  String username = '';

  @observable
  String email = '';

  @observable
  String accessToken = '';

  @observable
  String refreshToken = '';

  @observable
  UserSetting userSetting = UserSetting();

  @action
  Future<void> load() async {
    var accessToken = await authStorage.read(key: 'accessToken') ?? '';
    var refreshToken = await authStorage.read(key: 'refreshToken') ?? '';
    logger.d('accessToken: $accessToken');
    logger.d('refreshToken: $refreshToken');

    await login(accessToken, refreshToken);
  }

  Future<void> loadProfile() async {
    try {
      var client = new UserApiClient(makeDio());
      var response = await client.getUser();
      username = response.username;
      email = response.email;

      var setting = await client.getUserSetting();
      userSetting = UserSetting(timerDuration: setting.timerDuration, restDuration: setting.restDuration, restAutoStart: setting.restAutoStart);

    } catch (e) {
      logger.e(e);
      await logout();
    }
  }

  Dio makeDio() {
    var dio = Dio();
    dio.options.headers['Authorization'] = 'Bearer $accessToken';
    return dio;
  }

  @action
  Future<void> login(String accessToken, String refreshToken) async {
    try {
      if (JwtDecoder.isExpired(accessToken) || JwtDecoder.isExpired(refreshToken)) {
        logger.d("INVALID ACCESS / REFRESH TOKEN EXPIRATION");
        return;
      }
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;

      await authStorage.write(key: 'accessToken', value: accessToken);
      await authStorage.write(key: 'refreshToken', value: refreshToken);
      await loadProfile();
    } on FormatException catch (_, e) {
      logger.d("token format error: " + e.toString());
      return;
    }
  }

  @action
  Future<void> logout() async {
    accessToken = '';
    refreshToken = '';
    await authStorage.deleteAll();
  }

  @action
  Future<void> refresh() async {
    return;
  }

  @computed
  bool get isLoggedIn => refreshToken.isNotEmpty && !JwtDecoder.isExpired(refreshToken);
}
