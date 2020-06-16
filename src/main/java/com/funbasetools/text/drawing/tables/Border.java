package com.funbasetools.text.drawing.tables;

import com.funbasetools.collections.Streams;

import java.util.Optional;

import static com.funbasetools.text.drawing.tables.BorderType.*;

public enum Border {
    TOP('╵', SINGLE, NONE, NONE, NONE),
    RIGHT('╶', NONE, SINGLE, NONE, NONE),
    BOTTOM('╷', NONE, NONE, SINGLE, NONE),
    LEFT('╴', NONE, NONE, NONE, SINGLE),
    THICK_TOP('╹', THICK, NONE, NONE, NONE),
    THICK_RIGHT('╺', NONE, THICK, NONE, NONE),
    THICK_BOTTOM('╻', NONE, NONE, THICK, NONE),
    THICK_LEFT('╸', NONE, NONE, NONE, THICK),
    BOTTOM_CENTER('┴', SINGLE, SINGLE, NONE, SINGLE),
    BOTTOM_LEFT('└', SINGLE, SINGLE, NONE, NONE),
    BOTTOM_RIGHT('┘', SINGLE, NONE, NONE, SINGLE),
    MIDDLE_LEFT('├', SINGLE, SINGLE, SINGLE, NONE),
    MIDDLE_RIGHT('┤', SINGLE, NONE, SINGLE, SINGLE),
    TOP_CENTER('┬', NONE, SINGLE, SINGLE, SINGLE),
    TOP_LEFT('┌', NONE, SINGLE, SINGLE, NONE),
    TOP_RIGHT('┐', NONE, NONE, SINGLE, SINGLE),
    DOUBLE_BOTTOM_CENTER('╩', DOUBLE, DOUBLE, NONE, DOUBLE),
    DOUBLE_BOTTOM_LEFT('╚', DOUBLE, DOUBLE, NONE, NONE),
    DOUBLE_BOTTOM_RIGHT('╝', DOUBLE, NONE, NONE, DOUBLE),
    DOUBLE_MIDDLE_LEFT('╠', DOUBLE, DOUBLE, DOUBLE, NONE),
    DOUBLE_MIDDLE_RIGHT('╣', DOUBLE, NONE, DOUBLE, DOUBLE),
    DOUBLE_TOP_CENTER('╦', NONE, DOUBLE, DOUBLE, DOUBLE),
    DOUBLE_TOP_LEFT('╔', NONE, DOUBLE, DOUBLE, NONE),
    DOUBLE_TOP_RIGHT('╗', NONE, NONE, DOUBLE, DOUBLE),
    THICK_BOTTOM_CENTER('┻', THICK, THICK, NONE, THICK),
    THICK_BOTTOM_LEFT('┗', THICK, THICK, NONE, NONE),
    THICK_BOTTOM_RIGHT('┛', THICK, NONE, NONE, THICK),
    THICK_MIDDLE_LEFT('┣', THICK, THICK, THICK, NONE),
    THICK_MIDDLE_RIGHT('┫', THICK, NONE, THICK, THICK),
    THICK_TOP_CENTER('┳', NONE, THICK, THICK, THICK),
    THICK_TOP_LEFT('┏', NONE, THICK, THICK, NONE),
    THICK_TOP_RIGHT('┓', NONE, NONE, THICK, THICK),
    ROUNDED_BOTTOM_LEFT('╰', SINGLE, SINGLE, NONE, NONE),
    ROUNDED_BOTTOM_RIGHT('╯', SINGLE, NONE, NONE, SINGLE),
    ROUNDED_TOP_LEFT('╭', NONE, SINGLE, SINGLE, NONE),
    ROUNDED_TOP_RIGHT('╮', NONE, NONE, SINGLE, SINGLE),
    DH_BOTTOM_CENTER('╧', SINGLE, DOUBLE, NONE, DOUBLE),
    DH_BOTTOM_LEFT('╘', SINGLE, DOUBLE, NONE, NONE),
    DH_BOTTOM_RIGHT('╛', SINGLE, NONE, NONE, DOUBLE),
    DH_MIDDLE_LEFT('╞', SINGLE, DOUBLE, SINGLE, NONE),
    DH_MIDDLE_RIGHT('╡', SINGLE, NONE, SINGLE, DOUBLE),
    DH_TOP_CENTER('╤', NONE, DOUBLE, SINGLE, DOUBLE),
    DH_TOP_LEFT('╒', NONE, DOUBLE, SINGLE, NONE),
    DH_TOP_RIGHT('╕', NONE, NONE, SINGLE, DOUBLE),
    DH_CROSS('╪', SINGLE, DOUBLE, SINGLE, DOUBLE),
    TH_BOTTOM_CENTER('┷', SINGLE, THICK, NONE, THICK),
    TH_BOTTOM_LEFT('┕', SINGLE, THICK, NONE, NONE),
    TH_BOTTOM_RIGHT('┙', SINGLE, NONE, NONE, THICK),
    TH_MIDDLE_LEFT('┝', SINGLE, THICK, SINGLE, NONE),
    TH_MIDDLE_RIGHT('┥', SINGLE, NONE, SINGLE, THICK),
    TH_TOP_CENTER('┯', NONE, THICK, SINGLE, THICK),
    TH_TOP_LEFT('┍', NONE, THICK, SINGLE, NONE),
    TH_TOP_RIGHT('┑', NONE, NONE, SINGLE, THICK),
    TH_CROSS('┿', SINGLE, THICK, SINGLE, THICK),
    DV_BOTTOM_CENTER('╨', DOUBLE, SINGLE, NONE, SINGLE),
    DV_BOTTOM_LEFT('╙', DOUBLE, SINGLE, NONE, NONE),
    DV_BOTTOM_RIGHT('╜', DOUBLE, NONE, NONE, SINGLE),
    DV_MIDDLE_LEFT('╟', DOUBLE, SINGLE, DOUBLE, NONE),
    DV_MIDDLE_RIGHT('╢', DOUBLE, NONE, DOUBLE, SINGLE),
    DV_TOP_CENTER('╥', NONE, SINGLE, DOUBLE, SINGLE),
    DV_TOP_LEFT('╓', NONE, SINGLE, DOUBLE, NONE),
    DV_TOP_RIGHT('╖', NONE, NONE, DOUBLE, SINGLE),
    DV_CROSS('╫', DOUBLE, SINGLE, DOUBLE, SINGLE),
    TV_BOTTOM_CENTER('┸', THICK, SINGLE, NONE, SINGLE),
    TV_BOTTOM_LEFT('┖', THICK, SINGLE, NONE, NONE),
    TV_BOTTOM_RIGHT('┚', THICK, NONE, NONE, SINGLE),
    TV_MIDDLE_LEFT('┠', THICK, SINGLE, THICK, NONE),
    TV_MIDDLE_RIGHT('┨', THICK, NONE, THICK, SINGLE),
    TV_TOP_CENTER('┰', NONE, SINGLE, THICK, SINGLE),
    TV_TOP_LEFT('┎', NONE, SINGLE, THICK, NONE),
    TV_TOP_RIGHT('┒', NONE, NONE, THICK, SINGLE),
    TV_CROSS('╂', THICK, SINGLE, THICK, SINGLE),
    HORIZONTAL('─', NONE, SINGLE, NONE, SINGLE),
    VERTICAL('│', SINGLE, NONE, SINGLE, NONE),
    SINGLE_CROSS('┼', SINGLE, SINGLE, SINGLE, SINGLE),
    DOUBLE_HORIZONTAL('═', NONE, DOUBLE, NONE, DOUBLE),
    DOUBLE_VERTICAL('║', DOUBLE, NONE, DOUBLE, NONE),
    DOUBLE_CROSS('╬', DOUBLE, DOUBLE, DOUBLE, DOUBLE),
    THICK_HORIZONTAL('━', NONE, THICK, NONE, THICK),
    THICK_VERTICAL('┃', THICK, NONE, THICK, NONE),
    THICK_CROSS('╋', THICK, THICK, THICK, THICK),
    TT_CROSS('╀', THICK, SINGLE, SINGLE, SINGLE),
    TB_CROSS('╁', SINGLE, SINGLE, THICK, SINGLE),
    TL_CROSS('┽', SINGLE, SINGLE, SINGLE, THICK),
    TR_CROSS('┾', SINGLE, THICK, SINGLE, SINGLE),
    TST_CROSS('╈', SINGLE, THICK, THICK, THICK),
    TSB_CROSS('╇', THICK, THICK, SINGLE, THICK),
    TSL_CROSS('╊', THICK, THICK, THICK, SINGLE),
    TSR_CROSS('╉', THICK, SINGLE, THICK, THICK),
    ;

    private final char character;
    private final BorderType top;
    private final BorderType right;
    private final BorderType bottom;
    private final BorderType left;

    Border(char character, BorderType top, BorderType right, BorderType bottom, BorderType left) {
        this.character = character;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public char getCharacter() {
        return character;
    }

    public BorderType getTop() {
        return top;
    }

    public BorderType getRight() {
        return right;
    }

    public BorderType getBottom() {
        return bottom;
    }

    public BorderType getLeft() {
        return left;
    }

    public boolean isCorner() {
        final boolean[] b = new boolean[] {
            getTop() != NONE,
            getRight() != NONE,
            getBottom() != NONE,
            getLeft() != NONE,
        };

        return (b[0] && b[1] && !b[2] && !b[3])
            || (!b[0] && b[1] && b[2] && !b[3])
            || (!b[0] && !b[1] && b[2] && b[3])
            || (b[0] && !b[1] && !b[2] && b[3]);
    }

    public BorderType[] toBorderTypeArray() {
        return new BorderType[] {
           getTop(),
           getRight(),
           getBottom(),
           getLeft(),
        };
    }

    @Override
    public String toString() {
        return "" + character;
    }

    public static Optional<Border> getByBorderTypes(
        final BorderType top,
        final BorderType right,
        final BorderType bottom,
        final BorderType left
    ) {
        return Streams.of(values())
            .first(b ->
                b.getTop() == top
                && b.getRight() == right
                && b.getBottom() == bottom
                && b.getLeft() == left
            );
    }
}
