import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:gdsc_timer/shared/common.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:mobx/mobx.dart';
part 'auth-store.g.dart';

final authStorage = FlutterSecureStorage();

class AuthStore = _AuthStore with _$AuthStore;

abstract class _AuthStore with Store {
  @observable
  String username = '';

  @observable
  String accessToken = '';

  @observable
  String refreshToken = '';

  @action
  Future<void> load() async {
    accessToken = await authStorage.read(key: 'accessToken') ?? '';
    refreshToken = await authStorage.read(key: 'refreshToken') ?? '';
    logger.d('accessToken: $accessToken');
    logger.d('refreshToken: $refreshToken');
  }

  @action
  void login(String accessToken, String refreshToken) {
    try{
      if (JwtDecoder.isExpired(accessToken) || JwtDecoder.isExpired(refreshToken)) {
        logger.d("INVALID ACCESS / REFRESH TOKEN EXPIRATION");
        return;
      }
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;

      authStorage.write(key: 'accessToken', value: accessToken);
      authStorage.write(key: 'refreshToken', value: refreshToken);
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
