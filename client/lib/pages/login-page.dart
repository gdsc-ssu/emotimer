import 'package:flutter/material.dart';
import 'package:flutter_web_auth/flutter_web_auth.dart';
import 'package:gdsc_timer/shared/component/notification-body.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:in_app_notification/in_app_notification.dart';
import 'package:provider/provider.dart';
import 'package:uuid/uuid.dart';

class LoginButton extends StatelessWidget {
  String label;
  String assetFrom;
  late final Color color;
  VoidCallback onPressed;
  bool isLoading;

  LoginButton({required this.label, required this.assetFrom, required this.color, required this.onPressed, required this.isLoading});

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.only(top: 16),
        child: ElevatedButton.icon(
          onPressed: isLoading ? null : onPressed,
          icon: Image.asset(
            assetFrom,
            height: 16,
            alignment: Alignment.centerLeft,
          ),
          label: Text(label, style: const TextStyle(color: Color.fromRGBO(0, 0, 0, 1))),
          style: ElevatedButton.styleFrom(minimumSize: const Size.fromHeight(50), primary: color),
        ));
  }
}

class LoginPage extends StatefulWidget {
  bool isLoading = false;

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthStore>(context);

    return Scaffold(
        body: Center(
      child: Padding(
        padding: const EdgeInsets.all(32),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
                padding: const EdgeInsets.all(32),
                child: Image.asset(
                  'assets/icon/emotimer.png',
                  width: 150,
                  height: 150,
                )),
            LoginButton(
              label: "Login with kakao",
              assetFrom: "assets/icon/kakao.png",
              color: const Color.fromRGBO(247, 230, 0, 1.0),
              isLoading: widget.isLoading,
              onPressed: () async {
                widget.isLoading = true;
                try {
                  final clientState = Uuid().v4();
                  final result = await FlutterWebAuth.authenticate(
                      url: Uri.https('kauth.kakao.com', '/oauth/authorize', {
                        'response_type': 'code',
                        'client_id': '116cc9d3778c5d9578487bce8f08e024',
                        'redirect_uri': 'http://localhost:8080/callback/kakao',
                        'state': clientState,
                      }).toString(),
                      callbackUrlScheme: "emotimer");

                  final body = Uri.parse(result).queryParameters;
                  print(body);
                  auth.login(body['accessToken']!, body['refreshToken']!);
                  if(auth.isLoggedIn) {
                    Navigator.pushReplacementNamed(context, '/');
                  }
                } catch (error) {
                  print(error);
                } finally {
                  widget.isLoading = false;
                }
              },
            ),
            LoginButton(
              label: "Login with google",
              assetFrom: "assets/icon/google.png",
              color: Colors.white,
              isLoading: widget.isLoading,
              onPressed: () {},
            ),
            LoginButton(
              label: "Login with apple id",
              assetFrom: "assets/icon/apple.png",
              color: Colors.white70,
              isLoading: widget.isLoading,
              onPressed: () {},
            ),
          ],
        ),
      ),
    ));
  }
}
