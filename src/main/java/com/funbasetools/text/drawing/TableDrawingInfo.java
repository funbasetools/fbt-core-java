package com.funbasetools.text.drawing;

import com.funbasetools.collections.Streams;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
public class TableDrawingInfo {

    public static TableDrawingInfo from(final Object[][] data) {

        final String[][] dataStrings = Optional
            .ofNullable(data)
            .map(arr -> new String[arr.length][])
            .orElse(new String[0][]);

        final List<Integer> columnLengths = new ArrayList<>();

        return Streams.of(data)
            .foldLeftWithIndex(
                TableDrawingInfo.builder().data(dataStrings),
                (builder, row, rowIdx) -> {
                    final String[] rowString = dataStrings[rowIdx] = Optional
                        .ofNullable(row)
                        .map(r -> new String[r.length])
                        .orElse(new String[0]);

                    final int rowCount = rowIdx + 1;

                    return Streams.of(row)
                        .foldLeftWithIndex(builder, (b, cell, colIdx) -> {
                            final String cellString = Optional.ofNullable(cell)
                                .map(Object::toString)
                                .orElse("");

                            if (rowString.length > columnLengths.size()) {
                                columnLengths.add(0);
                            }

                            rowString[colIdx] = cellString;
                            final int columnLength = StrictMath.max(columnLengths.get(colIdx), cellString.length());
                            columnLengths.set(colIdx, columnLength);

                            return b;
                        })
                        .rowCount(rowCount)
                        .columnCount(columnLengths.size())
                        .columnLengths(columnLengths.<Integer>toArray(new Integer[0]));
                })
            .build();
    }

    private final int columnCount;
    private final int rowCount;
    private final Integer[] columnLengths;
    private final String[][] data;

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public Integer[] getColumnLengths() {
        return columnLengths;
    }

    public String[][] getData() {
        return data;
    }
}
