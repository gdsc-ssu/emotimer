

import 'package:flutter/material.dart';

class MainPage extends StatelessWidget {


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("메인페이지"),
      ),
      body: Column(
        children: [
          ElevatedButton(onPressed: () { Navigator.pushNamed(context, "/timer"); }, child: Text("타이머")),
          ElevatedButton(onPressed: () { Navigator.pushNamed(context, "/setting"); }, child: Text("세팅")),
          ElevatedButton(onPressed: () { Navigator.pushNamed(context, "/login"); }, child: Text("로그인")),
        ],
      )
    );
  }
}