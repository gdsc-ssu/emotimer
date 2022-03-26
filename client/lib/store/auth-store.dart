import 'package:mobx/mobx.dart';
part 'auth-store.g.dart';


class AuthStore = _AuthStore with _$AuthStore;

abstract class _AuthStore with Store {
  @observable
  String username = '';
}