import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:gdsc_timer/shared/common-components.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:provider/provider.dart';

class Setting {
  String title;
  Widget? widget;
  GestureTapCallback? onTap;
  bool hasArrow;

  Setting(this.title, {this.widget = null, this.hasArrow = false, this.onTap});
}

class SettingsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final auth = Provider.of<AuthStore>(context);

    var settings = [
      Setting("Email", widget: Text("yano24@naver.com")),
      Setting("Timer duration", widget: Text("50:00"), hasArrow: true),
      Setting("Timer auto start", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Rest duration", widget: Text("15:00"), hasArrow: true),
      Setting("Rest auto start", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Alert", widget: Switch(value: false, onChanged: (v) {})),
      Setting("Reset"),
      Setting("Help", hasArrow: true),
      Setting("Terms of service", hasArrow: true),
      Setting("Version", widget: Text("1.0.1")),
      Setting("Singout", onTap: () {
        showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10.0)),
                  content: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [Text("Do you want to logout?")],
                  ),
                  actions: <Widget>[
                    TextButton(
                      child: Text("Confirm"),
                      onPressed: () async {
                        await auth.logout();
                        Navigator.pop(context);
                        Navigator.pushReplacementNamed(context, "/login");
                      },
                    ),
                    TextButton(
                      child: Text("Cancel"),
                      onPressed: () async {
                        Navigator.pop(context);
                      },
                    )
                  ]);
            });
      }),
    ];

    return Scaffold(
      appBar: Components.appBar("Settings"),
      body: ListView.separated(
        padding: const EdgeInsets.all(16),
        itemCount: settings.length,
        itemBuilder: (context, index) {
          var setting = settings[index];
          var item = <Widget>[
            Expanded(child: Text(setting.title, style: const TextStyle(color: AppColors.primary, fontSize: 16))),
          ];

          if (setting.widget != null) {
            item.add(Expanded(
                child: Align(
              alignment: Alignment.centerRight,
              child: setting.widget,
            )));
          }

          if (setting.hasArrow) {
            item.add(const Padding(
              padding: EdgeInsets.only(left: 8),
              child: Icon(Icons.arrow_forward_ios),
            ));
          }
          return ListTile(
            visualDensity: const VisualDensity(vertical: -4),
            title: SizedBox(
              height: 40,
              child: Row(
                children: item,
              ),
            ),
            onTap: setting.onTap,
          );
        },
        separatorBuilder: (context, index) {
          return const Divider();
        },
      ),
    );
  }
}
