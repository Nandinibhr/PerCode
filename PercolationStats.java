import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholdArray;
	private int row, col;
	private static final int t=0;
	private static final  double CONFIDENCE_95 = 1.96;

	public PercolationStats(int n, int trials) // perform trials independent
												// experiments on an n-by-n grid
	{				
		if (n <=0  || trials < 1) {
			throw new IllegalArgumentException();
		}

		thresholdArray = new double[trials];

		for (int i = 0; i < trials; i++) {
			thresholdArray[i] = percTreshold(n);
		}
	}

	private double percTreshold(int n) // private function to find threshold
	{
		Percolation per = new Percolation(n);

		while (!per.percolates()) 
		{
			row = StdRandom.uniform(n)+1;
			col = StdRandom.uniform(n)+1;

			per.open(row, col);
		}

		double thresholdvalue = (double) per.numberOfOpenSites() / (double) (n * n);
		
		return thresholdvalue;
	}
	
	

	public double mean() // sample mean of percolation threshold
	{
		return StdStats.mean(thresholdArray);
	}
	
	

	public double stddev() // sample standard deviation of percolation threshold
	{
		// System.out.println(+);
		return StdStats.stddev(thresholdArray);
	}
	
	

	public double confidenceLo() // low endpoint of 95% confidence interval
	{
		return mean() - (CONFIDENCE_95 * stddev()) / (Math.sqrt(t));
	}
	
	

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		return mean() + (CONFIDENCE_95 * stddev()) / (Math.sqrt(t));
	}

	
	
	public static void main(String[] args) // test client (described below)
	{

		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		/*
		 * File file = new File("input6.txt.txt");
		 * 
		 * try {
		 * 
		 * Scanner sc = new Scanner(file); int n = sc.nextInt(); Percolation per
		 * = new Percolation(n);
		 * 
		 * while (sc.hasNextLine()) { int i = sc.nextInt(); int j =
		 * sc.nextInt(); //System.out.println( "Row "+i+ " Col "+j);
		 * per.open(i,j);
		 * 
		 * } sc.close(); }
		 * 
		 * catch (FileNotFoundException e) { e.printStackTrace();
		 */

		PercolationStats stats = new PercolationStats(n, t);
		System.out.println("% java-algs4 PercolationStats " + n + "  " + t);
		System.out.println("mean                    = " + stats.mean()); 
		System.out.println("stddev                  = " + stats.stddev()); 
		System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());

	}
}
