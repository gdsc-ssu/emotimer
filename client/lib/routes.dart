import 'package:flutter/material.dart';
import 'package:gdsc_timer/pages/login-page.dart';
import 'package:gdsc_timer/pages/main-page.dart';
import 'package:gdsc_timer/pages/settings-page.dart';
import 'package:gdsc_timer/pages/statics-page.dart';
import 'package:gdsc_timer/pages/timer-page.dart';

final Map<String, WidgetBuilder> routes = {
  '/': (context) => MainPage(),
  '/login': (context) => LoginPage(),
  '/setting': (context) => SettingsPage(),
  '/statics': (context) => StaticsPage(),
  '/timer': (context) => TimerPage()
};
