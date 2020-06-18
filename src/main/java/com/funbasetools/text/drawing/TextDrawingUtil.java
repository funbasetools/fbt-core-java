package com.funbasetools.text.drawing;

import com.funbasetools.collections.Streams;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.Function;

public final class TextDrawingUtil {

    private TextDrawingUtil() {
    }

    public static String getDrawFrom(Consumer<PrintStream> printer) {
        final String utf8 = StandardCharsets.UTF_8.toString();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try(final PrintStream ps = new PrintStream(baos, true, utf8)) {
            printer.accept(ps);
            return baos.toString(utf8);
        }
        catch (Exception ignore) {
            return "";
        }
    }

    public static String drawHorizontal(
        final int length,
        final String start,
        final String body,
        final String end) {

        return getDrawFrom(printStream -> drawHorizontalTo(length, start, body, end, printStream));
    }

    public static void drawHorizontalTo(
        final int length,
        final String start,
        final String body,
        final String end,
        final PrintStream printStream) {

        final int indexBeforeEnd = Math.max(0, length - end.length());

        final Function<Integer, Integer> startStep =
            accLength -> drawStartTo(start, length, accLength, printStream);

        final Function<Integer, Integer> bodyStep =
            accLength -> drawBodyTo(body, indexBeforeEnd, accLength, printStream);

        final Function<Integer, Integer> endStep =
            accLength -> drawEndTo(end, length, accLength, printStream);

        startStep
            .andThen(bodyStep)
            .andThen(endStep)
            .apply(0);
    }

    public static void drawHorizontalLineOf(
        final Object base,
        final int length,
        final PrintStream printStream) {

        drawHorizontalLineOf(base.toString(), length, printStream);
    }

    public static void drawHorizontalLineOf(
        final String base,
        final int length,
        final PrintStream printStream) {

        final int times = length / base.length();
        final int remainder = length % base.length();

        printStream.print(StringUtils.repeat(base, times));
        printStream.print(StringUtils.substring(base, 0, remainder));
    }

    // private methods

    private static int drawStartTo(
        final String text,
        final int length,
        final int accLength,
        final PrintStream printStream) {

        final String startText = StringUtils.substring(text,0, length);
        printStream.print(startText);

        return accLength + startText.length();
    }

    private static int drawBodyTo(
        final String text,
        final int indexBeforeEnd,
        final int accLength,
        final PrintStream printStream) {

        return Streams
            .from(1)
            .foldLeftWhile(
                accLength,
                (accLen, it) -> accLen < indexBeforeEnd,
                (accLen, it) -> {
                    final String bodyText = StringUtils.substring(text, 0, indexBeforeEnd - accLen);
                    printStream.print(bodyText);

                    return accLen + bodyText.length();
                }
            );
    }

    private static int drawEndTo(
        final String text,
        final int length,
        final int accLength,
        final PrintStream printStream) {

        final String endText = StringUtils.substring(text, 0, length - accLength);
        printStream.print(endText);

        return accLength + endText.length();
    }
}
