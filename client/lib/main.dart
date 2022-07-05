import 'dart:io';

import 'package:flutter/material.dart';
import 'package:gdsc_timer/routes.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:gdsc_timer/store/timer-store.dart';
import 'package:provider/provider.dart';
import 'package:window_manager/window_manager.dart';
import 'package:flutter/foundation.dart';


void main() async {

  if (!kIsWeb && (Platform.isMacOS || Platform.isWindows)) {
    WidgetsFlutterBinding.ensureInitialized();
    await windowManager.ensureInitialized();
    windowManager.setResizable(false);
    windowManager.waitUntilReadyToShow(const WindowOptions(
      size: Size(400, 700),
      center: true,
      backgroundColor: Colors.transparent,
      skipTaskbar: false,
      titleBarStyle: TitleBarStyle.hidden,
    ), () async {
      await windowManager.show();
      await windowManager.focus();
    });
  }

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return Provider<TimerStore>(
      create: (_) => TimerStore(),
      child: Provider<AuthStore>(
        create: (_) => AuthStore(),
        child: MaterialApp(
          title: 'Flutter Demo',
          theme: ThemeData(
            primarySwatch: Colors.blue,
          ),
          routes: routes,
          initialRoute: '/',
        ),
      ),
    );
  }
}
