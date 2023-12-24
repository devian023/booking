package com.jpm.booking.controller.model;

public class ViewShowRequest {
    private String showNumber;
    private int numberOfRows;
    private int numberOfSeatsPerRow;
    private int cancellationWindowInMinutes;

    public String getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfSeatsPerRow() {
        return numberOfSeatsPerRow;
    }

    public void setNumberOfSeatsPerRow(int numberOfSeatsPerRow) {
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInMinutes;
    }

    public void setCancellationWindowInMinutes(int cancellationWindowInMinutes) {
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
    }
}
