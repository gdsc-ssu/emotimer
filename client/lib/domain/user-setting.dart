class UserSetting {
  int timerDuration;
  int restDuration;
  bool restAutoStart;

  UserSetting({this.timerDuration = 60 * 25, this.restDuration = 60 * 5, this.restAutoStart = true});
}
