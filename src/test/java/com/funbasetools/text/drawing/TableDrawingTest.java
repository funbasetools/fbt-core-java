package com.funbasetools.text.drawing;

import static org.junit.Assert.assertEquals;

import com.funbasetools.text.drawing.tables.BorderTemplate;
import org.junit.Test;

public class TableDrawingTest {

    private static final String NEW_LINE = System.lineSeparator();

    @Test
    public void testDrawSingleBox() {
        // given
        final BorderTemplate borderTemplate = BorderTemplate.SINGLE_TABLE;
        final Object[][] content = new Object[][] {
            { "Box Content" }
        };

        // when
        final String output = TextDrawingUtil.getDrawFrom(ps -> TableDrawing.drawTable(content, borderTemplate, ps));

        // then
        assertEquals(
            "┌─────────────┐" + NEW_LINE +
                    "│ Box Content │" + NEW_LINE +
                    "└─────────────┘",
            output
        );
    }

    @Test
    public void testDrawDoubleBox() {
        // given
        final BorderTemplate borderTemplate = BorderTemplate.DOUBLE_TABLE;
        final Object[][] content = new Object[][] {
            { "Box Content" }
        };

        // when
        final String output = TextDrawingUtil.getDrawFrom(ps -> TableDrawing.drawTable(content, borderTemplate, ps));

        // then
        assertEquals(
            "╔═════════════╗" + NEW_LINE +
                    "║ Box Content ║" + NEW_LINE +
                    "╚═════════════╝",
            output
        );
    }

    @Test
    public void testDrawVerticalDoubleBox() {
        // given
        final BorderTemplate borderTemplate = BorderTemplate.VERTICAL_DOUBLE_TABLE;
        final Object[][] content = new Object[][] {
            { "Box Content" }
        };

        // when
        final String output = TextDrawingUtil.getDrawFrom(ps -> TableDrawing.drawTable(content, borderTemplate, ps));

        // then
        assertEquals(
            "╓─────────────╖" + NEW_LINE +
                    "║ Box Content ║" + NEW_LINE +
                    "╙─────────────╜",
            output
        );
    }

    @Test
    public void testDrawSingleOneRowTable() {
        // given
        final BorderTemplate borderTemplate = BorderTemplate.SINGLE_TABLE;
        final Object[][] content = new Object[][] {
            { "Table contents", "are the best thing", "to have" }
        };

        // when
        final String output = TextDrawingUtil.getDrawFrom(ps -> TableDrawing.drawTable(content, borderTemplate, ps));

        // then
        assertEquals(
            "┌────────────────┬────────────────────┬─────────┐" + NEW_LINE +
                    "│ Table contents │ are the best thing │ to have │" + NEW_LINE +
                    "└────────────────┴────────────────────┴─────────┘",
            output
        );
    }

    @Test
    public void testDrawSingleMultipleRowsTable() {
        // given
        final BorderTemplate borderTemplate = BorderTemplate.SINGLE_TABLE;
        final Object[][] content = new Object[][] {
            { "Table contents", "are the best thing", "to have" },
            { "But", "having this", "is even better" },
        };

        // when
        final String output = TextDrawingUtil.getDrawFrom(ps -> TableDrawing.drawTable(content, borderTemplate, ps));

        // then
        assertEquals(
            "┌────────────────┬────────────────────┬────────────────┐" + NEW_LINE +
                    "│ Table contents │ are the best thing │ to have        │" + NEW_LINE +
                    "├────────────────┼────────────────────┼────────────────┤" + NEW_LINE +
                    "│ But            │ having this        │ is even better │" + NEW_LINE +
                    "└────────────────┴────────────────────┴────────────────┘",
            output
        );
    }
}
