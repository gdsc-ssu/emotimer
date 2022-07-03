import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:gdsc_timer/shared/common-components.dart';
import 'package:gdsc_timer/shared/component/chart.dart';

class StaticsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Components.appBar("Statics"),
      body: DefaultTabController(
        length: 3,
        initialIndex: 0,
        child: Column(
          children: <Widget>[
            const TabBar(indicatorColor: Colors.transparent, unselectedLabelColor: Colors.grey, labelColor: AppColors.primary, tabs: [
              Tab(text: "Daily"),
              Tab(text: "Weekly"),
              Tab(text: "Monthly"),
            ]),
            Expanded(
              child: TabBarView(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(24),
                    child: SingleChildScrollView(
                      child: Column(
                        children: [
                          Padding(
                            padding: EdgeInsets.symmetric(horizontal: 20),
                            child: ArrowPageNavigator(currentPage: 0, maxPage: 10, pageTitleRenderer: (context, page) => "Page $page",),
                          ),
                          StaticsChart(),
                          Divider(height: 32, thickness: 1),
                          ListRanking(title: "Weekly Ranking", items: [
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                          ]),
                          Divider(height: 32, thickness: 1),
                          ListRanking(title: "Others", items: [
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                            ListRankingItem(
                              emoji: "💯",
                              time: "0:00",
                            ),
                          ]),
                        ],
                      ),
                    ),
                  ),
                  Center(child: Text("주별")),
                  Center(child: Text("일별")),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

typedef IndexedWidgetBuilder = String Function(BuildContext context, int index);

class ArrowPageNavigator extends StatefulWidget {
  int currentPage;
  int maxPage;
  IndexedWidgetBuilder pageTitleRenderer;

  ArrowPageNavigator({Key? key, required this.currentPage, required this.maxPage, required this.pageTitleRenderer}) : super(key: key);

  @override
  _ArrowPageNavigatorState createState() => _ArrowPageNavigatorState();
}

class _ArrowPageNavigatorState extends State<ArrowPageNavigator> {
  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        IconButton(
            onPressed: widget.currentPage > 0
                ? () {
                    setState(() {
                      widget.currentPage--;
                    });
                  }
                : null,
            icon: const Icon(Icons.arrow_back_ios)),
        Expanded(
          child: Text(widget.pageTitleRenderer(context, widget.currentPage),
              style: const TextStyle(fontSize: 16, color: AppColors.primary, fontWeight: FontWeight.bold), textAlign: TextAlign.center),
        ),
        IconButton(
            onPressed: widget.currentPage < widget.maxPage
                ? () {
                    setState(() {
                      widget.currentPage++;
                    });
                  }
                : null,
            icon: const Icon(Icons.arrow_forward_ios)),
      ],
    );
  }
}

class ListRankingItem {
  String emoji;
  String time;

  ListRankingItem({required this.emoji, required this.time});
}

class ListRanking extends StatelessWidget {
  String title;
  List<ListRankingItem> items;

  ListRanking({Key? key, required this.title, required this.items}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Text(title, style: Theme.of(context).textTheme.headline6, textAlign: TextAlign.left),
      ListView.separated(
        shrinkWrap: true,
        physics: const NeverScrollableScrollPhysics(),
        itemCount: items.length,
        padding: const EdgeInsets.symmetric(vertical: 20),
        separatorBuilder: (context, index) => const Divider(height: 0, thickness: 0.5),
        itemBuilder: (context, index) {
          var item = items[index];
          return ListTile(
            visualDensity: const VisualDensity(vertical: -4),
            title: Row(children: [
              Container(height: 16, width: 16, color: AppColors.primary),
              Padding(
                padding: EdgeInsets.only(left: 8),
                child: Text(item.emoji),
              ),
            ]),
            trailing: Text(item.time),
          );
        },
      ),
    ]);
  }
}
