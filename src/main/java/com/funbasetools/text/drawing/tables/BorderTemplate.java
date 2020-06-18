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
        final Border topLeft,
        final Border bottomRight,
        final Border cross) {

        if (topLeft.isCorner() && bottomRight.isCorner()) {
            return Optional.of(safeCreate(topLeft, bottomRight, cross));
        }

        return Optional.empty();
    }

    public static Border getTopRight(
        final Border topLeft,
        final Border bottomRight) {

        return Border
            .getByBorderTypes(
                BorderType.NONE,
                BorderType.NONE,
                bottomRight.getTop(),
                topLeft.getRight()
            )
            .orElse(Border.TOP_RIGHT);
    }

    public static Border getBottomLeft(
        final Border topLeft,
        final Border bottomRight) {

        return Border
            .getByBorderTypes(
                topLeft.getBottom(),
                bottomRight.getLeft(),
                BorderType.NONE,
                BorderType.NONE
            )
            .orElse(Border.BOTTOM_LEFT);
    }

    public static Border getTopCenter(
        final Border topLeft,
        final Border topRight,
        final Border cross) {

        return Border
            .getByBorderTypes(
                BorderType.NONE,
                topRight.getLeft(),
                cross.getTop(),
                topLeft.getRight()
            )
            .orElse(Border.TOP_CENTER);
    }

    public static Border getMiddleRight(
        final Border topRight,
        final Border bottomRight,
        final Border cross) {

        return Border
            .getByBorderTypes(
                topRight.getBottom(),
                BorderType.NONE,
                bottomRight.getTop(),
                cross.getRight()
            )
            .orElse(Border.MIDDLE_RIGHT);
    }

    public static Border getBottomCenter(
        final Border bottomLeft,
        final Border bottomRight,
        final Border cross) {

        return Border
            .getByBorderTypes(
                cross.getBottom(),
                bottomRight.getLeft(),
                BorderType.NONE,
                bottomLeft.getRight()
            )
            .orElse(Border.BOTTOM_CENTER);
    }

    public static Border getMiddleLeft(
        final Border topLeft,
        final Border bottomLeft,
        final Border cross) {

        return Border
            .getByBorderTypes(
                topLeft.getBottom(),
                cross.getLeft(),
                bottomLeft.getTop(),
                BorderType.NONE
            )
            .orElse(Border.MIDDLE_LEFT);
    }

    public static Border getHorizontalLine(
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

    public static Border getVerticalLine(
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

    public static Border getHorizontalBetween(final Border before, final Border after) {
        return Border
            .getByBorderTypes(BorderType.NONE, after.getLeft(), BorderType.NONE, before.getRight())
            .orElse(Border.HORIZONTAL);
    }

    public static Border getVerticalBetween(final Border upper, final Border under) {
        return Border
            .getByBorderTypes(upper.getBottom(), BorderType.NONE, under.getTop(), BorderType.NONE)
            .orElse(Border.VERTICAL);
    }

    private final Border topLeft;
    private final Border bottomRight;
    private final Border cross;
    private final Border bottomLeft;
    private final Border topRight;
    private final Border topCenter;
    private final Border middleRight;
    private final Border bottomCenter;
    private final Border middleLeft;
    private final Border firstTopHorizontal;
    private final Border centerTopHorizontal;
    private final Border lastTopHorizontal;
    private final Border firstMiddleHorizontal;
    private final Border centerMiddleHorizontal;
    private final Border lastMiddleHorizontal;
    private final Border firstBottomHorizontal;
    private final Border centerBottomHorizontal;
    private final Border lastBottomHorizontal;
    private final Border firstLeftVertical;
    private final Border firstCenterVertical;
    private final Border firstRightVertical;
    private final Border middleLeftVertical;
    private final Border middleCenterVertical;
    private final Border middleRightVertical;
    private final Border lastLeftVertical;
    private final Border lastCenterVertical;
    private final Border lastRightVertical;

    private BorderTemplate(
        final Border topLeft,
        final Border bottomRight,
        final Border cross,
        final Border bottomLeft,
        final Border topRight,
        final Border topCenter,
        final Border middleRight,
        final Border bottomCenter,
        final Border middleLeft) {

        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.cross = cross;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.topCenter = topCenter;
        this.middleRight = middleRight;
        this.bottomCenter = bottomCenter;
        this.middleLeft = middleLeft;
        this.firstTopHorizontal = getHorizontalBetween(topLeft, topCenter);
        this.centerTopHorizontal = getHorizontalBetween(topCenter, topCenter);
        this.lastTopHorizontal = getHorizontalBetween(topCenter, topRight);
        this.firstMiddleHorizontal = getHorizontalBetween(middleLeft, cross);
        this.centerMiddleHorizontal = getHorizontalBetween(cross, cross);
        this.lastMiddleHorizontal = getHorizontalBetween(cross, middleRight);
        this.firstBottomHorizontal = getHorizontalBetween(bottomLeft, bottomCenter);
        this.centerBottomHorizontal = getHorizontalBetween(bottomCenter, bottomCenter);
        this.lastBottomHorizontal = getHorizontalBetween(bottomCenter, bottomRight);
        this.firstLeftVertical = getVerticalLine(topLeft, middleLeft);
        this.firstCenterVertical = getVerticalLine(topCenter, cross);
        this.firstRightVertical = getVerticalLine(topRight, middleRight);
        this.middleLeftVertical = getVerticalLine(middleLeft, middleLeft);
        this.middleCenterVertical = getVerticalLine(cross, cross);
        this.middleRightVertical = getVerticalLine(middleRight, middleRight);
        this.lastLeftVertical = getVerticalLine(middleLeft, bottomLeft);
        this.lastCenterVertical = getVerticalLine(cross, bottomCenter);
        this.lastRightVertical = getVerticalLine(middleRight, bottomRight);
    }

    public Border getTopLeft() {
        return topLeft;
    }

    public Border getTopRight() {
        return topRight;
    }

    public Border getBottomLeft() {
        return bottomLeft;
    }

    public Border getBottomRight() {
        return bottomRight;
    }

    public Border getCross() {
        return cross;
    }

    public Border getTopCenter() {
        return topCenter;
    }

    public Border getMiddleRight() {
        return middleRight;
    }

    public Border getBottomCenter() {
        return bottomCenter;
    }

    public Border getMiddleLeft() {
        return middleLeft;
    }

    public Border getCenterTopHorizontal() {
        return centerTopHorizontal;
    }

    public Border getLastTopHorizontal() {
        return lastTopHorizontal;
    }

    public Border getFirstMiddleHorizontal() {
        return firstMiddleHorizontal;
    }

    public Border getCenterMiddleHorizontal() {
        return centerMiddleHorizontal;
    }

    public Border getLastMiddleHorizontal() {
        return lastMiddleHorizontal;
    }

    public Border getFirstBottomHorizontal() {
        return firstBottomHorizontal;
    }

    public Border getCenterBottomHorizontal() {
        return centerBottomHorizontal;
    }

    public Border getLastBottomHorizontal() {
        return lastBottomHorizontal;
    }

    public Border getFirstLeftVertical() {
        return firstLeftVertical;
    }

    public Border getFirstCenterVertical() {
        return firstCenterVertical;
    }

    public Border getFirstRightVertical() {
        return firstRightVertical;
    }

    public Border getMiddleLeftVertical() {
        return middleLeftVertical;
    }

    public Border getMiddleCenterVertical() {
        return middleCenterVertical;
    }

    public Border getMiddleRightVertical() {
        return middleRightVertical;
    }

    public Border getLastLeftVertical() {
        return lastLeftVertical;
    }

    public Border getLastCenterVertical() {
        return lastCenterVertical;
    }

    public Border getLastRightVertical() {
        return lastRightVertical;
    }

    @Override
    public String toString() {
        final String nl = System.lineSeparator();
        final String sp = " ";

        return ""
            + topLeft + firstTopHorizontal + topCenter + lastTopHorizontal + topRight + nl
            + firstLeftVertical + sp + firstCenterVertical + sp + firstRightVertical + nl
            + middleLeft + centerMiddleHorizontal + cross + lastMiddleHorizontal + middleRight + nl
            + lastLeftVertical + sp + lastCenterVertical + sp + lastRightVertical + nl
            + bottomLeft + firstBottomHorizontal + bottomCenter + lastBottomHorizontal + bottomRight;
    }

    // private methods

    public static BorderTemplate safeCreate(
        final Border topLeftCorner,
        final Border bottomRightCorner,
        final Border crossBorder) {

        final Border bottomLeftCorner = getBottomLeft(topLeftCorner, bottomRightCorner);
        final Border topRightCorner = getTopRight(topLeftCorner, bottomRightCorner);
        final Border topCenterBorder = getTopCenter(topLeftCorner, topRightCorner, crossBorder);
        final Border middleRightBorder = getMiddleRight(topRightCorner, bottomRightCorner, crossBorder);
        final Border bottomCenterBorder = getBottomCenter(bottomLeftCorner, bottomRightCorner, crossBorder);
        final Border middleLeftBorder = getMiddleLeft(topLeftCorner, bottomLeftCorner, crossBorder);

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

    public Border getFirstTopHorizontal() {
        return firstTopHorizontal;
    }
}
