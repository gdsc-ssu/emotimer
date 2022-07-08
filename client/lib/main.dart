import 'dart:io';

import 'package:flutter/material.dart';
import 'package:gdsc_timer/routes.dart';
import 'package:gdsc_timer/shared/common.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:gdsc_timer/store/timer-store.dart';
import 'package:in_app_notification/in_app_notification.dart';
import 'package:provider/provider.dart';
import 'package:window_manager/window_manager.dart';
import 'package:flutter/foundation.dart';

final timerStore = TimerStore();
final authStore = AuthStore();

void main() async {
  logger.d('--- EMOTIMER START ---');
  WidgetsFlutterBinding.ensureInitialized();
  if (!kIsWeb && (Platform.isMacOS || Platform.isWindows)) {
    logger.d('Preparing desktop window configuration');
    await windowManager.ensureInitialized();
    windowManager.setResizable(false);
    windowManager.waitUntilReadyToShow(
        const WindowOptions(
          size: Size(400, 700),
          backgroundColor: Colors.transparent,
          skipTaskbar: false,
          title: 'emotimer',
          titleBarStyle: TitleBarStyle.hidden,
        ), () async {
      await windowManager.show();
      await windowManager.focus();
    });
  }
  await authStore.load();

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return Provider<TimerStore>(
      create: (_) => timerStore,
      child: Provider<AuthStore>(
          create: (_) => authStore,
          child: InAppNotification(
            child: MaterialApp(
              title: 'Flutter Demo',
              theme: ThemeData(
                primarySwatch: Colors.blue,
              ),
              routes: routes,
              initialRoute: authStore.isLoggedIn ? '/' : '/login',
            ),
          )),
    );
  }
}
