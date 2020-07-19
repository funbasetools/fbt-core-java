package com.funbasetools.text.drawing;

import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import com.funbasetools.text.drawing.tables.BorderTemplate;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;

public final class TableDrawing {

    public static void drawTable(
        final Object[][] data,
        final BorderTemplate borderTemplate,
        final PrintStream printStream) {

        final TableDrawingInfo tableInfo = TableDrawingInfo.from(data);
        Streams.ofNullable(tableInfo.getData())
            .forEachWithIndex((row, rowIdx) -> drawRow(row, rowIdx, tableInfo, borderTemplate, printStream));
    }

    private static void drawRow(
        final String[] row,
        final int rowIndex,
        final TableDrawingInfo tableInfo,
        final BorderTemplate borderTemplate,
        final PrintStream printStream) {

        final boolean isFirstRow = rowIndex == 0;
        final boolean isLastRow = rowIndex == tableInfo.getRowCount() - 1;
        final int columnCount = tableInfo.getColumnCount();
        final Stream<Integer> columnLengths = Streams.ofNullable(tableInfo.getColumnLengths());

        drawRowTopLine(
            borderTemplate,
            columnCount,
            columnLengths,
            isFirstRow,
            printStream
        );

        drawRowContent(
            row,
            borderTemplate,
            columnCount,
            columnLengths,
            isFirstRow,
            isLastRow,
            printStream
        );

        if (isLastRow) {
            drawTableBottomBorder(
                borderTemplate,
                columnCount,
                columnLengths,
                printStream
            );
        }
    }

    private static void drawRowTopLine(
        final BorderTemplate bt,
        final int columnCount,
        final Stream<Integer> columnLengths,
        final boolean isFirstRow,
        final PrintStream printStream) {

        columnLengths
            .forEachWithIndex((colLength, colIdx) -> {
                final boolean isFirstColumn = colIdx == 0;
                final boolean isLastColumn = colIdx == columnCount - 1;

                if (isFirstRow) {
                    if (isFirstColumn) {
                        printStream.print(bt.getTopLeft());
                        TextDrawingUtil.drawHorizontalLineOf(bt.getFirstTopHorizontal(), colLength + 2, printStream);
                        if (isLastColumn) {
                            printStream.println(bt.getTopRight());
                        }
                        else {
                            printStream.print(bt.getTopCenter());
                        }
                    }
                    else if (isLastColumn) {
                        TextDrawingUtil.drawHorizontalLineOf(bt.getLastTopHorizontal(), colLength + 2, printStream);
                        printStream.println(bt.getTopRight());
                    }
                    else {
                        TextDrawingUtil.drawHorizontalLineOf(bt.getCenterTopHorizontal(), colLength + 2, printStream);
                        printStream.print(bt.getTopCenter());
                    }
                }
                else {
                    if (isFirstColumn) {
                        printStream.print(bt.getMiddleLeft());
                        TextDrawingUtil.drawHorizontalLineOf(bt.getFirstMiddleHorizontal(), colLength + 2, printStream);
                        if (isLastColumn) {
                            printStream.println(bt.getMiddleRight());
                        }
                        else {
                            printStream.print(bt.getCross());
                        }
                    }
                    else if (isLastColumn) {
                        TextDrawingUtil.drawHorizontalLineOf(bt.getLastMiddleHorizontal(), colLength + 2, printStream);
                        printStream.println(bt.getMiddleRight());
                    }
                    else {
                        TextDrawingUtil.drawHorizontalLineOf(bt.getCenterMiddleHorizontal(), colLength + 2, printStream);
                        printStream.print(bt.getCross());
                    }
                }
            });
    }

    private static void drawRowContent(
        final String[] row,
        final BorderTemplate bt,
        final int columnCount,
        final Stream<Integer> columnLengths,
        final boolean isFirstRow,
        final boolean isLastRow,
        final PrintStream printStream) {

        columnLengths
            .forEachWithIndex((colLength, colIdx) -> {
                final boolean isFirstColumn = colIdx == 0;
                final boolean isLastColumn = colIdx == columnCount - 1;

                if (isFirstColumn) {
                    if (isFirstRow) {
                        printStream.print(bt.getFirstLeftVertical());
                    }
                    else if (isLastColumn) {
                        printStream.print(bt.getLastLeftVertical());
                    }
                    else {
                        printStream.print(bt.getMiddleLeftVertical());
                    }
                }
                else {
                    if (isFirstRow) {
                        printStream.print(bt.getFirstCenterVertical());
                    }
                    else if (isLastRow) {
                        printStream.print(bt.getLastCenterVertical());
                    }
                    else {
                        printStream.print(bt.getLastCenterVertical());
                    }
                }

                printStream.print(" ");
                printStream.print(StringUtils.rightPad(row[colIdx], colLength + 1));

                if (isLastColumn) {
                    if (isFirstRow) {
                        printStream.println(bt.getFirstRightVertical());
                    }
                    else if (isLastRow) {
                        printStream.println(bt.getMiddleRightVertical());
                    }
                    else {
                        printStream.println(bt.getMiddleCenterVertical());
                    }
                }
            });
    }

    private static void drawTableBottomBorder(
        final BorderTemplate bt,
        final int columnCount,
        final Stream<Integer> columnLengths,
        final PrintStream printStream) {

        columnLengths
            .forEachWithIndex((colLength, colIdx) -> {
                final boolean isFirstColumn = colIdx == 0;
                final boolean isLastColumn = colIdx == columnCount - 1;

                if (isFirstColumn) {
                    printStream.print(bt.getBottomLeft());
                    TextDrawingUtil.drawHorizontalLineOf(bt.getFirstBottomHorizontal(), colLength + 2, printStream);
                    if (isLastColumn) {
                        printStream.print(bt.getBottomRight());
                    }
                    else {
                        printStream.print(bt.getBottomCenter());
                    }
                }
                else if (isLastColumn) {
                    TextDrawingUtil.drawHorizontalLineOf(bt.getLastBottomHorizontal(), colLength + 2, printStream);
                    printStream.print(bt.getBottomRight());
                }
                else {
                    TextDrawingUtil.drawHorizontalLineOf(bt.getCenterBottomHorizontal(), colLength + 2, printStream);
                    printStream.print(bt.getBottomCenter());
                }
            });
    }

    private TableDrawing() {
    }
}
