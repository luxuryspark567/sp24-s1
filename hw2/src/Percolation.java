import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufForBack;
    private final int virtualIndexTop;
    private final int virtualIndexBottom;
    private final boolean[][] grid;
    private int openSites = 0;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N has to be > 0");
        }
        n = N;
        grid = new boolean[N][N];
        virtualIndexTop = N * N;
        virtualIndexBottom = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufForBack = new WeightedQuickUnionUF(N * N + 1);
    }

    private void valIndex(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
    }

    private int rowReturn(int row, int col) {
        return row * n + col;
    }

    public void open(int row, int col) {
        valIndex(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
            int x = rowReturn(row, col);
            connectS(row, col);
        }
    }

    private void connectS(int row, int col) {
        int index = rowReturn(row, col);
        if (row == 0) {
            uf.union(index, virtualIndexTop);
            ufForBack.union(index, virtualIndexTop);
        }
        if (row == n - 1) {
            uf.union(index, virtualIndexBottom);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(index, rowReturn(row - 1, col));
            ufForBack.union(index, rowReturn(row - 1, col));
        }
        if (row < n - 1 && isOpen(row + 1, col)) {
            uf.union(index, rowReturn(row + 1, col));
            ufForBack.union(index, rowReturn(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(index, rowReturn(row, col - 1));
            ufForBack.union(index, rowReturn(row, col - 1));
        }
        if (col < n - 1 && isOpen(row, col + 1)) {
            uf.union(index, rowReturn(row, col + 1));
            ufForBack.union(index, rowReturn(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        valIndex(row, col);
        return grid[row][col];
    }


    public boolean isFull(int row, int col) {
        valIndex(row, col);
        return ufForBack.connected(rowReturn(row, col), virtualIndexTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(virtualIndexTop, virtualIndexBottom);
    }
}
