import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';

typedef OnChangePage = void Function(int page);
typedef IndexedTitleBuilder = String Function(int index);

class ArrowPageNavigator extends StatefulWidget {
  int currentPage;
  final int maxPage;
  final IndexedTitleBuilder pageTitleRenderer;
  final OnChangePage onChangePage;

  ArrowPageNavigator({Key? key, this.currentPage = 0, required this.maxPage, required this.pageTitleRenderer, required this.onChangePage}) : super(key: key);

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
                widget.onChangePage(widget.currentPage);
              });
            }
                : null,
            icon: const Icon(Icons.arrow_back_ios)),
        Expanded(
          child: Text(widget.pageTitleRenderer(widget.currentPage),
              style: const TextStyle(fontSize: 16, color: AppColors.primary, fontWeight: FontWeight.bold), textAlign: TextAlign.center),
        ),
        IconButton(
            onPressed: widget.currentPage < widget.maxPage
                ? () {
              setState(() {
                widget.currentPage++;
                widget.onChangePage(widget.currentPage);
              });
            }
                : null,
            icon: const Icon(Icons.arrow_forward_ios)),
      ],
    );
  }
}
