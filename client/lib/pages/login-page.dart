import 'package:flutter/material.dart';

class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: Padding(
        padding: const EdgeInsets.all(10),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            ElevatedButton.icon(
              onPressed: () {},
              icon: Image.asset(
                "assets/icon/kakao.png",
                height: 16,
                alignment: Alignment.centerLeft,
              ),
              label: const Text("Login with kakao",
                  style: TextStyle(color: Color.fromRGBO(0, 0, 0, 1))),
              style: ElevatedButton.styleFrom(
                minimumSize: const Size.fromHeight(50),
                primary: const Color.fromRGBO(247, 230, 0, 1.0),
              ),
            )
          ],
        ),
      ),
    ));
  }
}
