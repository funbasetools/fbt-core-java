package com.funbasetools.text.drawing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextDrawingUtilTest {

    @Test
    public void testDrawHorizontalLineOf() {
        // given
        final String base = "=+=";

        // when
        final String result = TextDrawingUtil
            .getDrawFrom(ps -> TextDrawingUtil.drawHorizontalLineOf(base, 25, ps));

        // then
        assertEquals(25, result.length());
        assertEquals(
            "=+==+==+==+==+==+==+==+==",
            result
        );
    }

    @Test
    public void testSimpleDrawHorizontal() {
        // given
        final String start = "╠";
        final String body = "═";
        final String end = "╣";

        // then
        assertEquals(
            "╠══════════════════╣",
            TextDrawingUtil.drawHorizontal(20, start, body, end)
        );
    }

    @Test
    public void testComplexDrawHorizontal() {
        // given
        final String start = "╔═╗";
        final String body = "...";
        final String end = "╭─╮";

        // then
        assertEquals(
            "╔═╗..............╭─╮",
            TextDrawingUtil.drawHorizontal(20, start, body, end)
        );
    }

    @Test
    public void testSimpleDrawHorizontalTo() {
        // given
        final String start = "╠═";
        final String body = "╍╍╍━━";
        final String end = "━►";

        // then
        assertEquals(
            "╠═╍╍╍━━╍╍╍━━╍╍╍━━╍━►",
            TextDrawingUtil.getDrawFrom(ps -> TextDrawingUtil.drawHorizontalTo(20, start, body, end, ps))
        );
    }
}
