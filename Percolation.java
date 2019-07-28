/* *****************************************************************************
 *  Name:De Zhang
 *  Date:July 6th, 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] siteStatus;
    private int gridSize;
    private WeightedQuickUnionUF grid;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid must be at least 1 unit large");
        }
        int numSite = n * n + 2;
        siteStatus = new int[numSite];
        siteStatus[0] = 1;
        siteStatus[numSite - 1] = 1;
        grid = new WeightedQuickUnionUF(numSite);
        gridSize = n;
    }


    public void open(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize) {
            throw new IllegalArgumentException("Input site out of bounds");
        }
        if (!isOpen(row, col)) {
            int targetSite = (row - 1) * gridSize + col;
            int upSite = targetSite - gridSize;
            int rightSite = targetSite + 1;
            int lowSite = targetSite + gridSize;
            int leftSite = targetSite - 1;
            if (row != 1 && isOpen(row - 1, col)) {
                grid.union(targetSite, upSite);
            }
            if (col != gridSize && isOpen(row, col + 1)) {
                grid.union(targetSite, rightSite);
            }
            if (row != gridSize && isOpen(row + 1, col)) {
                grid.union(targetSite, lowSite);
            }
            if (col != 1 && isOpen(row, col - 1)) {
                grid.union(targetSite, leftSite);
            }
            if (row == 1) {
                grid.union(targetSite, 0);
            }
            if (row == gridSize) {
                grid.union(targetSite, gridSize * gridSize + 1);
            }
            siteStatus[(row - 1) * gridSize + col] = 1;
        }
    }

    public boolean isOpen(int row, int col) {
        return siteStatus[(row - 1) * gridSize + col] == 1;
    }

    public boolean isFull(int row, int col) {
        return grid.connected(0, (row - 1) * gridSize + col);
    }

    public boolean percolates() {
        return grid.connected(0, gridSize * gridSize + 1);
    }

    public int numberOfOpenSites() {
        int openSites = 0;
        for (int i = 1; i <= gridSize * gridSize; i++) {
            if (siteStatus[i] == 1) {
                openSites++;
            }
        }
        return openSites;
    }
}

