/* Quicksort <n>: sorts an array of n integers initialized
   with random values.
   Output: the array contents before and after sorting.
 */

//class Library{
//    void println(string s) {}
//    void print(string s) {}
//    void printi(int i) {}
//    int stoi(string s, int i) {}
//   int random(int n) {}
//    void exit(int n) {} 
//}

class Quicksort {

	int[] a ;

	int partition(int low , int high) {
		int pivot = a[low];
		int i = low;
		int j = high;
		int tmp;

		while (1) {
			while (a[i] < pivot) i = i+1;
			while (a[j] > pivot) j = j-1;

			// if (i >= j) break;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
			i = i+1;
			j = j-1;
		}

		return j;
	}



	void initArray() {
		int i = 0;
		while(i < 7) {
			a[i] = 5;
			i = i+1;
		}
	}

	void quicksort(int low, int high) {
		initArray();
		if (low < high) {
			int mid = partition(low, high);
			quicksort(low, mid);
			quicksort(mid+1, high);
		}
	} 
	//  void printArray() {
	//  int i = 0;

	//Library.print("Array elements: ");
	//  while(i<a.length) {
	//   Library.printi(a[i]);
	//    Library.print (" ");
	//      i = i+1;
	//  }
	//  Library.print("\n");
	// }

	void main(string[] args) {

		//int n="ggg"/3;

		//   if (args.length != 1) {
		//    Library.println("Unspecified array length");
		//   Library.exit(1);
		//  }

		// n = Library.stoi(args[0],0);
		//  if (n<=0) {
		//   Library.println("Invalid array length");
		//  Library.exit(1);
		//  }
		Quicksort s = new Quicksort();

		s.a = new int[5];
		//   s.initArray();
		//  s.printArray();
		//   s.quicksort(0, n-1);
		//   s.printArray();
	}
}