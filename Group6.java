package sortingCompetitionMC;
import java.util.Collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Group6  {

	int primeS = 0;
	ArrayList<String> arr = new ArrayList<String>();
	public Group6(int z, ArrayList<String> ars){
		primeS = z;
		arr = ars;
	}
public int getPS(){
	return primeS;
}
public void add(String s){
	arr.add(s);
}
public ArrayList<String> getarr(){
	return arr;
}







	public static void main(String[] args) throws InterruptedException, FileNotFoundException {

		if (args.length < 2) {
			System.out.println(
					"Please run with two command line arguments: input and output file names");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];
		
		// Uncomment to test comparator methods
		//SortingCompetitionComparator.runComparatorTests();

		String[] data = readData(inputFileName); // read data as strings
		
		String[] toSort = data.clone(); // clone the data

		sort(toSort); // call the sorting method once for JVM warmup
		
		toSort = data.clone(); // clone again

		Thread.sleep(10); // to let other things finish before timing; adds stability of runs

		long start = System.currentTimeMillis();

		sort(toSort); // sort again

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		writeOutResult(toSort, outFileName); // write out the results

	}


	private static String[] readData(String inputFileName) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<>();
		Scanner in = new Scanner(new File(inputFileName));

		while (in.hasNext()) {
			input.add(in.next());
		}

		in.close();

		// the string array is passed just so that the correct type can be created
		return input.toArray(new String[0]);
	}

	// YOUR SORTING METHOD GOES HERE.
	// You may call other methods and use other classes.
	// Note: you may change the return type of the method.
	// You would need to provide your own function that prints your sorted array to
	// a file in the exact same format that my program outputs
	private static void sort (String[] toSort) {
		int x = 0;
	ArrayList<Group6> bucket = new ArrayList<Group6>();
		while(x < toSort.length){
			int primeSum = SortingCompetitionComparator.getSumPrimeFactors(Integer.parseInt(toSort[x]));
			for(int y = 0; y < bucket.size(); ++y)
			{
				if(primeSum == bucket.get(y).getPS()){
					bucket.get(y).add(toSort[x]);
					primeSum = -1;
				}
			}
			if (primeSum != -1){
			bucket.add(new Group6(primeSum, new ArrayList<String>()));
			bucket.get(bucket.size()-1).add(toSort[x]);
			}
		++x;

		}
		Collections.sort(bucket, new Comparator<Group6>() {
			@Override
		public int compare(  Group6 s1, Group6 s2) {
			int sum1 = s1.getPS();
			int sum2 = s2.getPS();
			
			
			
			if (sum1 < sum2) {
				return -1;
			} else {
				return 1;
			}
			
		}
		});
// Sort the elements of each bucket
    for (int i = 0; i < bucket.size(); i++) {
      Collections.sort((bucket.get(i).getarr()), Collections.reverseOrder());
    }



// Get the sorted array
    int index = 0;
    for (int i = 0; i < bucket.size(); i++) {
      for (int j = 0, size = bucket.get(i).getarr().size(); j < size; j++) {
        toSort[index++] = bucket.get(i).getarr().get(j);
      }
    }
  
      return;
	}
   

	// Arrays.sort(toSort, new SortingCompetitionComparator());
	

	private static class SortingCompetitionComparator implements Comparator<String> {
		
		
		@Override
		public int compare(String s1, String s2) {
			int num1 = Integer.parseInt(s1);
			int num2 = Integer.parseInt(s2);
			
			int sum1 = getSumPrimeFactors(num1);
			int sum2 = getSumPrimeFactors(num2);
			
			if (sum1 < sum2) {
				return -1;
			} else if (sum1 > sum2) {
				return 1;
			}
			
			// got here, so the two sums are equal. Compare the numbers 
			// in the opposite order:
			return num2 - num1;
			
		}
		//

		private static int getSumPrimeFactors(int n) {
			if (n == 1 || n == 0) return 0; // special cases: don't have prime factors 
			
			int sum = 0;
			// checking all numbers until n, including n itself
			// since n can itself be prime
			for (int i = 2; i <= n; ++i) {
				// if i divides n and is prime, add it to the sum
				if (n % i == 0 && isPrime(i)) {
					sum += i;
				}
			}
			
			return sum;
		}
		
		private static boolean isPrime(int n) {
			if (n == 2) return true;
			
			if (n % 2 == 0) return false; // an even number is not prime
			
			// going up to the largest candidate, checking only odd numbers
			for (int i = 3; i < Math.sqrt(n) + 1; i += 2) {
				if (n % i == 0) return false;
			}
			
			return true;
		}

		public static void runComparatorTests() {		
			System.out.println("isPime(12) = " + isPrime(12)); // not prime
			System.out.println("isPime(2) = " + isPrime(2)); // prime
			System.out.println("isPime(11) = " + isPrime(11)); // prime
			System.out.println("isPime(999999937) = " + isPrime(999999937)); // prime
			System.out.println("isPime(23785129) = " + isPrime(23785129)); // not prime
			System.out.println("isPime(40464469) = " + isPrime(40464469)); // not prime
			
			
			System.out.println("getSumPrimeFactors(11) = " + getSumPrimeFactors(11)); //should be 11
			System.out.println("getSumPrimeFactors(20) = " + getSumPrimeFactors(20)); //should be 7
			System.out.println("getSumPrimeFactors(100) = " + getSumPrimeFactors(100)); //should be 7
			System.out.println("getSumPrimeFactors(999999937) = " + getSumPrimeFactors(999999937)); //should be 999999937
			System.out.println("getSumPrimeFactors(23785129) = " + getSumPrimeFactors(23785129)); //should be 4877
			System.out.println("getSumPrimeFactors(40464469) = " + getSumPrimeFactors(40464469)); //should be 13174
			
		}
		

	}
	
	private static void writeOutResult(String[] sorted, String outputFilename) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(outputFilename);
		for (String s : sorted) {
			out.println(s);
		}
		out.close();
	}
	
	
}
