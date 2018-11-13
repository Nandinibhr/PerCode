import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	 private int n;
	 private final boolean[][] grid;
	 private final boolean[] id;
	 private final int vtop, vbottom;

	private  int site = 0, nopen = 0;

	private final WeightedQuickUnionUF uf;

	public Percolation(int size) // create n-by-n grid, with all sites blocked
	{
		n = size;
				
		if (n <= 0)
		{
			throw new IllegalArgumentException();
		}
		
		uf = new WeightedQuickUnionUF((n * n) + 2);
		vtop = 0;
		vbottom = (n * n) + 1;

		// created n-by-n grid, with all sites blocked
		grid = new boolean[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				grid[row][col] = false;
			}
		}

		// created array with n*n+2 elements
		id = new boolean[n * n + 2];
		for (int i = 0; i < id.length; i++) {
			id[i] = true;
		}

	}

	private int mapping2Dto1D(int a, int b)
	{
		int val = n * a + b + 1;
		return val;
	}

	private boolean isIndicesValid(int row, int col)
	{
		if (row < 0 || row >= n || col < 0 || col >= n) 
		{
			throw new IllegalArgumentException();
		}
		else
			return false;
		
	}
	
	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{
		// System.out.println("row "+row+","+"col "+col);
		row = row - 1;
		col = col - 1;
		
		site = mapping2Dto1D(row, col);

		 isIndicesValid(row, col);

		/*
		 * //System.out.println("row "+row+","+"col "+col); if (row < 0 || row >
		 * n||col < 0 || col >n) throw new
		 * IllegalArgumentException("Illegal Arguments are entered");
		 * 
		 * if (row < 0 || row >= n) throw new
		 * IndexOutOfBoundsException("row index i out of bounds row " +row); if
		 * (col < 0 || col >= n) throw new
		 * IndexOutOfBoundsException("col index j out of bounds col "+col);
		 * 
		 * //System.out.println("row "+row+","+"col "+col);
		 */
		if (!grid[row][col])
		{
			grid[row][col] = true;
			nopen++;
		}

		if (row == 0) {
			// System.out.println("P : "+site);
			uf.union(site, 0);
		}
		if (row == n - 1) {
			uf.union(site, (n * n) + 1);
		}

		if ((col - 1) >= 0 && grid[row][col - 1]) {
			uf.union(site, mapping2Dto1D(row, col - 1));
		}

		if ((col + 1) <= n - 1 && grid[row][col + 1] == true) {
			uf.union(site, mapping2Dto1D(row, col + 1));
		}

		if ((row - 1) >= 0 && grid[row - 1][col] == true) {
			uf.union(site, mapping2Dto1D(row - 1, col));

		}
		// System.out.println("n : "+n);
		if ((row + 1) <= n - 1 && grid[row + 1][col] == true) {
			uf.union(site, mapping2Dto1D(row + 1, col));
		}

	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		// System.out.println("row value " + row +"," + "col value " +col );
				
		isIndicesValid(row-1, col-1);
		 
		if (grid[row-1][col-1]) 
			return true; 
		else
			return false;
	  	
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		row = row - 1;
		col = col - 1;

		 isIndicesValid( row, col);
		 
		/*
		 * if (row < 0 || row > n||col < 0 || col >n) throw new
		 * IllegalArgumentException("Illegal Arguments are entered");
		 * 
		 * if (row < 0 || row >= n) throw new
		 * IndexOutOfBoundsException("row index i out of bounds"); if (col < 0
		 * || col >= n) throw new
		 * IndexOutOfBoundsException("col index j out of bounds");
		 */
		site = mapping2Dto1D(row, col);

		if (uf.connected(0, site))
		{
			return true;
		}
		else
        {
			return false;
		}
	}
	
	

	public int numberOfOpenSites() // number of open sites
	{
		return nopen;
	}

	public boolean percolates() // does the system percolate?
	{
		if (uf.connected(vtop, vbottom))		
			return true;
		else		
			return false;
		
	}
}
