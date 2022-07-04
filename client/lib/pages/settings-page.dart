import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:gdsc_timer/shared/common-components.dart';

class Setting {
  String title;
  Widget? widget;
  bool hasArrow;

  Setting(this.title, {this.widget = null, this.hasArrow = false});
}

class SettingsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var settings = [
      Setting("Timer duration", widget: Text("50:00"), hasArrow: true),
      Setting("Timer auto start", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Rest duration", widget: Text("15:00"), hasArrow: true),
      Setting("Rest auto start", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Alert", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Reset"),
      Setting("Help", hasArrow: true),
      Setting("Terms of service", hasArrow: true),
      Setting("Version", widget: Text("1.0.1")),
    ];

    return Scaffold(
      appBar: Components.appBar("Settings"),
      body: ListView.separated(
        padding: const EdgeInsets.all(16),
        itemCount: settings.length,
        itemBuilder: (context, index) {
          var item = <Widget>[
            Expanded(child: Text(settings[index].title, style: const TextStyle(color: AppColors.primary, fontSize: 16))),
          ];

          if (settings[index].widget != null) {
            item.add(Expanded(
                child: Align(
              alignment: Alignment.centerRight,
              child: settings[index].widget,
            )));
          }

          if (settings[index].hasArrow) {
            item.add(const Padding(
              padding: EdgeInsets.only(left: 8),
              child: Icon(Icons.arrow_forward_ios),
            ));
          }
          return ListTile(
            visualDensity: const VisualDensity(vertical: -4),
            title: SizedBox(
              height: 40,
              child: Row(children: item,),
            ),
            onTap: () {
              // Navigator.pushNamed(context, "/setting/$index");
            },
          );
        },
        separatorBuilder: (context, index) {
          return const Divider();
        },
      ),
    );
  }
}
