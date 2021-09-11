/**
 * Custom dynamic array to be used as columns in connect 4.
 * a class for the dynamic array, makes settings and allocates memory.
 * @author Adam David
 * @param <T> accepts a generic data type
 */
public class Column<T> {

	/**
	/ default initial capacity or minimum capacity.
	*/
	private static final int DEFAULT_CAPACITY = 2;
	
	/**
	 * underlying array for storage.
	 */
	private T[] data;

	/**
	 *  size to store the amount of items.
	 */
	private int size = 0;
	/**
	 * capacity to store the length.
	 */
	private int capacity;

	/**
	 *  a constructer for the dynamic array, makes settings and allocates memory.
	 */
	@SuppressWarnings("unchecked")
	public Column() {
		// Constructor
		
		// Initial capacity of the storage should be DEFAULT_CAPACITY
		// make an array of generic Ts
		this.data = (T[]) new Object[DEFAULT_CAPACITY];
		this.capacity = DEFAULT_CAPACITY;
	}

	/**
	 * a constructer for the dynamic array, makes settings and allocates memory.
	 * @param initialCapacity lets you setup a custom capacity
	 */
	@SuppressWarnings("unchecked")
	public Column(int initialCapacity) {
		// Constructor	
		// Throw IllegalArgumentException if initialCapacity is smaller than 1
		// Use this _exact_ error message for the exception
		//    "Capacity must be positive."
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be positive");
		}
		this.data = (T[]) new Object[initialCapacity];
		this.capacity = initialCapacity;

		// Initial capacity of the storage should be initialCapacity
		

		
	}
	
	/**
	 *  getter for size attribute.
	 * 	@return the length (int)
	 */
	public int size() {	
		// Report the current number of elements
		// O(1)
		
		return this.size;
	}  
	/**
	 *  getter for capacity attribute.
	 * 	@return the capacity (int)
	 */
	public int capacity() { 
		// Report max number of elements before expansion
		// O(1)
		
		return this.capacity;
	}

	/**
	 * replace one value with another.
	 * @param index for the location of item
	 * @param value to be replacing
	 * @return the token removed
	 */
	public T set(int index, T value) {
		// Change the item at the given index to be the given value.
		// Return the old item at that index.
		// You cannot add new items with this method.
		
		// O(1)
		
		// For an invalid index, throw an IndexOutOfBoundsException
		// Use this code to produce the correct error message for
		// the exception:
		//	  "Index: " + index + " out of bounds!"
		// ---------------------
		T oldValue;

		// verify that index being accessed isnt below 0 or isnt above capacity-1
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
		}

		// replace old value with new value
		oldValue = this.data[index];
		this.data[index] = value;

		return oldValue;
		
	}

	/**
	 * return a token, no alterations to structure.
	 * @param index for the location of item
	 * @return the token at index
	 */
	public T get(int index) {
		// Return the item at the given index
		
		// O(1)
		
		// Use the exception (and error message) described in set()
		// for invalid indicies.
		// --------------------

		// verify that index being accessed isnt below 0 or isnt above capacity-1
		if (index < 0 || index > capacity-1) {
			throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
		}

		return this.data[index];
				
	}

	/**
	 * Add to end of list, expand capacity if needed.
	 * @param value to be added
	 */
	@SuppressWarnings("unchecked")
	public void add(T value) {
		// Append an element to the end of the storage.		
		// Double the capacity if no space available.
		
		// Amortized O(1)
		// -------------------------

		//scenario when array full
		if (this.size == this.capacity) {

			this.capacity = this.capacity * 2;
			T[] temp = (T[]) new Object[this.capacity];

			for (int i = 0; i < this.capacity/2;++i) {
				temp[i] = data[i];
			}

			this.data = temp;
			this.data[this.capacity/2] = value;
			++size;
		}

		//scenario when not full
		else if (this.capacity > this.size) {
			this.data[this.size] = value;
			++size;
		}

		//allows the capacity to never fill.
		if (this.size == this.capacity) {

			this.capacity = this.capacity * 2;
			T[] temp = (T[]) new Object[this.capacity];

			for (int i = 0; i < this.capacity/2;++i) {
				temp[i] = data[i];
			}

			this.data = temp;
		}
		return;
		
	} 

	/**
	 * Insert the given value at the given index. Shift elements if needed.
	 * @param index for the location of item
	 * @param value to be inserted
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		// Insert the given value at the given index. Shift elements if needed,  
		// double capacity if no space available, throw an exception if you cannot
		// insert at the given index. You _can_ append items with this method.
		
		// For the exception, use the same exception and message as set() and
		// get()... however remember that the condition of the exception is
		// different (different indexes are invalid).
		
		// O(N) where N is the number of elements in the storage
		// -------------------------
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
		}

		//scenario where index is not occupied (appending)
		if (index == size) {
			//check if capacity needs to be extended
			if (capacity == index) {
				//copy everything over and double capacity
				this.capacity = capacity *2;
				T[] temp = (T[]) new Object[this.capacity];
				for (int i = 0; i < this.capacity/2;++i) {
					temp[i] = this.data[i];
				}
				this.data = temp;
				//this would append - this should only add at the end
				this.data[index] = value;
			}
			//if there is enough room already
			else if (capacity > index) {
				this.data[index] = value;
				this.size++;
			}
		}

		//scenario when you are shifting elements
		else if (index < size) {
			//check if capacity needs to be extended
			if (size == capacity) {
				this.capacity = capacity*2;
				T[] temp = (T[]) new Object[this.capacity];
				for (int i = 0; i < this.capacity/2;++i) {
					temp[i] = this.data[i];
				}
				this.data = temp;
			}
			//insert logic
			T holder;
			T cur;
			holder = this.data[index];
			cur = value;
			this.data[index] = value;
			for (int i = index+1; i < size+1; ++i) {
				cur = this.data[i];
				this.data[i] = holder;
				holder = cur;
			}
			++size;
		}
		
	} 
	
	/**
	 * removing a token from list.
	 * @param index for the location of item
	 * @return the token removed
	 */
	@SuppressWarnings("unchecked")
	public T delete(int index) {
		// Remove and return the element at the given index. Shift elements
		// to remove the gap. Throw an exception when there is an invalid
		// index (see set(), get(), etc. above).
		
		// Halve capacity of the storage if the number of elements falls
		// below 1/3 of the capacity. Capacity should NOT go below DEFAULT_CAPACITY.
		
		// O(N) where N is the number of elements currently in the storage
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + " out of bounds!");
		}

		T element = this.data[index];

		// no shift scenario
		if (index == size-1) {
			this.data[index] = null;
		}

		//scenario with shift
		else if (index < (size-1) ) {
			for (int i = index; i < size-1; ++i) {
				data[i] = data[i+1];
			}
			data[size-1] = null;
		}
		--this.size;

		//capacity management
		if (this.size < this.capacity/3.0 && this.capacity/3 >= DEFAULT_CAPACITY) {
			this.capacity = this.capacity/2;
		}
		return element;

	}  

	
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//******************************************************
	
	/**
	 * Allows column to be printed in a custom format.
	 * @return string representing column
	 */
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the column for easy viewing.
		StringBuilder s = new StringBuilder("Column with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	
	/**
	 * Main is testing the functions of column.
	 * @param args takes in command line arguements
	 */
	public static void main(String args[]){
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellent first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!

		//create a column of integers
		Column<Integer> nums = new Column<>();
		if((nums.size() == 0) && (nums.capacity() == 2)){
			System.out.println("Yay 1");
		}

		//append some numbers 
		for(int i = 0; i < 3; i++) {
			nums.add(i*2);
		}
		
		if(nums.size() == 3 && nums.get(2) == 4 && nums.capacity() == 4){
			System.out.println("Yay 2");
		}
		
		//create a column of strings
		Column<String> msg = new Column<>();
		
		//insert some strings
		msg.add(0,"world");
		msg.add(0,"hello");
		msg.add(1,"new");
		msg.add(3,"!");
		
		//checking
		if (msg.get(0).equals("hello") && msg.set(1,"beautiful").equals("new") 
			&& msg.size() == 4 && msg.capacity() == 4){
			System.out.println("Yay 3");
		}
		
		//delete 
		if (msg.delete(1).equals("beautiful") && msg.get(1).equals("world")  
			&& msg.size() == 3 ){
			System.out.println("Yay 4");
			System.out.println(nums);
		}

		//shrinking
		nums.add(100);
		nums.add(0, -10);
		if (nums.delete(0) == -10 && nums.delete(1) == 2 && nums.delete(2) == 100
			&& nums.size() == 2 && nums.capacity() == 4) {
			System.out.println("Yay 5");
			
		}
		System.out.println(nums);
	}
	

	

}