package com.funbasetools.text.drawing.tables;

import java.util.Optional;

public class BorderTemplate {

    public static final BorderTemplate SINGLE_TABLE = safeCreate(Border.TOP_LEFT, Border.BOTTOM_RIGHT, Border.SINGLE_CROSS);
    public static final BorderTemplate DOUBLE_TABLE = safeCreate(Border.DOUBLE_TOP_LEFT, Border.DOUBLE_BOTTOM_RIGHT, Border.DOUBLE_CROSS);
    public static final BorderTemplate THICK_TABLE = safeCreate(Border.THICK_TOP_LEFT, Border.THICK_BOTTOM_RIGHT, Border.THICK_CROSS);
    public static final BorderTemplate HORIZONTAL_DOUBLE_TABLE = safeCreate(Border.DH_TOP_LEFT, Border.DH_BOTTOM_RIGHT, Border.DH_CROSS);
    public static final BorderTemplate VERTICAL_DOUBLE_TABLE = safeCreate(Border.DV_TOP_LEFT, Border.DV_BOTTOM_RIGHT, Border.DV_CROSS);
    public static final BorderTemplate HORIZONTAL_THICK_TABLE = safeCreate(Border.TH_TOP_LEFT, Border.TH_BOTTOM_RIGHT, Border.TH_CROSS);
    public static final BorderTemplate VERTICAL_THICK_TABLE = safeCreate(Border.TV_TOP_LEFT, Border.TV_BOTTOM_RIGHT, Border.TV_CROSS);

    public static Optional<BorderTemplate> create(
        final Border topLeftCorner,
        final Border bottomRightCorner,
        final Border crossBorder) {

        if (topLeftCorner.isCorner() && bottomRightCorner.isCorner()) {
            return Optional.of(safeCreate(topLeftCorner, bottomRightCorner, crossBorder));
        }

        return Optional.empty();
    }

    public static Border getTopRightCorner(
        final Border topLeftCorner,
        final Border bottomRightCorner) {

        return Border
            .getByBorderTypes(
                BorderType.NONE,
                BorderType.NONE,
                bottomRightCorner.getTop(),
                topLeftCorner.getRight()
            )
            .orElse(Border.TOP_RIGHT);
    }

    public static Border getBottomLeftCorner(
        final Border topLeftCorner,
        final Border bottomRightCorner) {

        return Border
            .getByBorderTypes(
                topLeftCorner.getBottom(),
                bottomRightCorner.getLeft(),
                BorderType.NONE,
                BorderType.NONE
            )
            .orElse(Border.BOTTOM_LEFT);
    }

    public static Border getTopCenterBorder(
        final Border topLeftCorner,
        final Border topRightCorner,
        final Border crossBorder) {

        return Border
            .getByBorderTypes(
                BorderType.NONE,
                topRightCorner.getLeft(),
                crossBorder.getTop(),
                topLeftCorner.getRight()
            )
            .orElse(Border.TOP_CENTER);
    }

    public static Border getMiddleRightBorder(
        final Border topRightCorner,
        final Border bottomRightCorner,
        final Border crossBorder) {

        return Border
            .getByBorderTypes(
                topRightCorner.getBottom(),
                BorderType.NONE,
                bottomRightCorner.getTop(),
                crossBorder.getRight()
            )
            .orElse(Border.MIDDLE_RIGHT);
    }

    public static Border getBottomCenterBorder(
        final Border bottomLeftCorner,
        final Border bottomRightCorner,
        final Border crossBorder) {

        return Border
            .getByBorderTypes(
                crossBorder.getBottom(),
                bottomRightCorner.getLeft(),
                BorderType.NONE,
                bottomLeftCorner.getRight()
            )
            .orElse(Border.BOTTOM_CENTER);
    }

    public static Border getMiddleLeftBorder(
        final Border topLeftCorner,
        final Border bottomLeftCorner,
        final Border crossBorder) {

        return Border
            .getByBorderTypes(
                topLeftCorner.getBottom(),
                crossBorder.getLeft(),
                bottomLeftCorner.getTop(),
                BorderType.NONE
            )
            .orElse(Border.MIDDLE_LEFT);
    }

    public static Border getHorizontalBorder(
        final Border before,
        final Border after) {

        return Border
            .getByBorderTypes(
                BorderType.NONE,
                after.getLeft(),
                BorderType.NONE,
                before.getRight()
            )
            .orElse(Border.HORIZONTAL);
    }

    public static Border getVerticalBorder(
        final Border upper,
        final Border under) {

        return Border
            .getByBorderTypes(
                upper.getBottom(),
                BorderType.NONE,
                under.getTop(),
                BorderType.NONE
            )
            .orElse(Border.VERTICAL);
    }

    private final Border topLeftCorner;
    private final Border bottomRightCorner;
    private final Border crossBorder;
    private final Border bottomLeftCorner;
    private final Border topRightCorner;
    private final Border topCenterBorder;
    private final Border middleRightBorder;
    private final Border bottomCenterBorder;
    private final Border middleLeftBorder;

    private BorderTemplate(
        final Border topLeftCorner,
        final Border bottomRightCorner,
        final Border crossBorder,
        final Border bottomLeftCorner,
        final Border topRightCorner,
        final Border topCenterBorder,
        final Border middleRightBorder,
        final Border bottomCenterBorder,
        final Border middleLeftBorder) {

        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
        this.crossBorder = crossBorder;
        this.bottomLeftCorner = bottomLeftCorner;
        this.topRightCorner = topRightCorner;
        this.topCenterBorder = topCenterBorder;
        this.middleRightBorder = middleRightBorder;
        this.bottomCenterBorder = bottomCenterBorder;
        this.middleLeftBorder = middleLeftBorder;
    }

    public Border getTopLeftCorner() {
        return topLeftCorner;
    }

    public Border getTopRightCorner() {
        return topRightCorner;
    }

    public Border getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    public Border getBottomRightCorner() {
        return bottomRightCorner;
    }

    public Border getCrossBorder() {
        return crossBorder;
    }

    public Border getTopCenterBorder() {
        return topCenterBorder;
    }

    public Border getMiddleRightBorder() {
        return middleRightBorder;
    }

    public Border getBottomCenterBorder() {
        return bottomCenterBorder;
    }

    public Border getMiddleLeftBorder() {
        return middleLeftBorder;
    }

    @Override
    public String toString() {
        final String nl = System.lineSeparator();
        final String sp = " ";

        return ""
            + topLeftCorner
            + getHorizontalBorder(topLeftCorner, topCenterBorder)
            + topCenterBorder
            + getHorizontalBorder(topCenterBorder, topRightCorner)
            + topRightCorner
            + nl
            + getVerticalBorder(topLeftCorner, middleLeftBorder) + sp
            + getVerticalBorder(topCenterBorder, crossBorder) + sp
            + getVerticalBorder(topRightCorner, middleRightBorder)
            + nl
            + middleLeftBorder
            + getHorizontalBorder(middleLeftBorder, crossBorder)
            + crossBorder
            + getHorizontalBorder(crossBorder, middleRightBorder)
            + middleRightBorder
            + nl
            + getVerticalBorder(middleLeftBorder, bottomLeftCorner) + sp
            + getVerticalBorder(crossBorder, bottomCenterBorder) + sp
            + getVerticalBorder(middleRightBorder, bottomRightCorner)
            + nl
            + bottomLeftCorner
            + getHorizontalBorder(bottomLeftCorner, bottomCenterBorder)
            + bottomCenterBorder
            + getHorizontalBorder(bottomCenterBorder, bottomRightCorner)
            + bottomRightCorner;
    }

    // private methods

    public static BorderTemplate safeCreate(
        final Border topLeftCorner,
        final Border bottomRightCorner,
        final Border crossBorder) {

        final Border bottomLeftCorner = getBottomLeftCorner(topLeftCorner, bottomRightCorner);
        final Border topRightCorner = getTopRightCorner(topLeftCorner, bottomRightCorner);
        final Border topCenterBorder = getTopCenterBorder(topLeftCorner, topRightCorner, crossBorder);
        final Border middleRightBorder = getMiddleRightBorder(topRightCorner, bottomRightCorner, crossBorder);
        final Border bottomCenterBorder = getBottomCenterBorder(bottomLeftCorner, bottomRightCorner, crossBorder);
        final Border middleLeftBorder = getMiddleLeftBorder(topLeftCorner, bottomLeftCorner, crossBorder);

        return new BorderTemplate(
            topLeftCorner,
            bottomRightCorner,
            crossBorder,
            bottomLeftCorner,
            topRightCorner,
            topCenterBorder,
            middleRightBorder,
            bottomCenterBorder,
            middleLeftBorder
        );
    }
}
