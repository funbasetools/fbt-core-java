package com.funbasetools.text.drawing.tables;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorderTemplateTest {

    private static final String newLine = System.lineSeparator();

    @Test
    public void testSingle() {
        final String expected =
            "┌─┬─┐" + newLine +
            "│ │ │" + newLine +
            "├─┼─┤" + newLine +
            "│ │ │" + newLine +
            "└─┴─┘";

        assertEquals(expected, BorderTemplate.SINGLE_TABLE.toString());
    }

    @Test
    public void testDouble() {
        final String expected =
            "╔═╦═╗" + newLine +
            "║ ║ ║" + newLine +
            "╠═╬═╣" + newLine +
            "║ ║ ║" + newLine +
            "╚═╩═╝";

        assertEquals(expected, BorderTemplate.DOUBLE_TABLE.toString());
    }

    @Test
    public void testThick() {
        final String expected =
            "┏━┳━┓" + newLine +
            "┃ ┃ ┃" + newLine +
            "┣━╋━┫" + newLine +
            "┃ ┃ ┃" + newLine +
            "┗━┻━┛";

        assertEquals(expected, BorderTemplate.THICK_TABLE.toString());
    }

    @Test
    public void testHorizontalDouble() {
        final String expected =
            "╒═╤═╕" + newLine +
            "│ │ │" + newLine +
            "╞═╪═╡" + newLine +
            "│ │ │" + newLine +
            "╘═╧═╛";

        assertEquals(expected, BorderTemplate.HORIZONTAL_DOUBLE_TABLE.toString());
    }

    @Test
    public void testVerticalDouble() {
        final String expected =
            "╓─╥─╖" + newLine +
            "║ ║ ║" + newLine +
            "╟─╫─╢" + newLine +
            "║ ║ ║" + newLine +
            "╙─╨─╜";

        assertEquals(expected, BorderTemplate.VERTICAL_DOUBLE_TABLE.toString());
    }

    @Test
    public void testHorizontalThick() {
        final String expected =
            "┍━┯━┑" + newLine +
            "│ │ │" + newLine +
            "┝━┿━┥" + newLine +
            "│ │ │" + newLine +
            "┕━┷━┙";

        assertEquals(expected, BorderTemplate.HORIZONTAL_THICK_TABLE.toString());
    }

    @Test
    public void testVerticalThick() {
        final String expected =
            "┎─┰─┒" + newLine +
            "┃ ┃ ┃" + newLine +
            "┠─╂─┨" + newLine +
            "┃ ┃ ┃" + newLine +
            "┖─┸─┚";

        assertEquals(expected, BorderTemplate.VERTICAL_THICK_TABLE.toString());
    }
}
