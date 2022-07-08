
import 'package:flutter/material.dart';

class NoContent extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: const [
          Text(":(", style: TextStyle(fontSize: 50)),
          Padding(padding: EdgeInsets.all(10)),
          Text("타이머를 아직 사용하시지 않았군요!"),
          Text("표시할 데이터가 없습니다."),
        ],),
    );
  }
}
