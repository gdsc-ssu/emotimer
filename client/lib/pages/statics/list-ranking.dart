import 'package:flutter/material.dart';

import '../../shared/app_colors.dart';
import '../../shared/skeleton/src/widgets.dart';

class ListRankingItem {
  String emoji;
  String time;

  ListRankingItem({required this.emoji, required this.time});
}

class ListRanking extends StatelessWidget {
  bool isLoading;
  String title;
  List<ListRankingItem> items;

  ListRanking({Key? key, required this.isLoading, required this.title, required this.items}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Text(title, style: Theme.of(context).textTheme.headline6, textAlign: TextAlign.left),
      Skeleton(
        isLoading: isLoading,
        duration: const Duration(milliseconds: 1000),
        skeleton: Padding(padding: const EdgeInsets.symmetric(vertical: 16), child: SkeletonListView(
          shrinkWrap: true,
            scrollable: false,
            item: SkeletonListTile(
              hasSubtitle: false,
              hasLeading: false,
            ),
            padding: EdgeInsets.zero,
            itemCount: 3)),
        child: ListView.separated(
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
      ),
    ]);
  }
}
