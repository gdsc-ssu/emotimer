import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:gdsc_timer/routes.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:gdsc_timer/store/timer-store.dart';
import 'package:provider/provider.dart';

void main() {
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
