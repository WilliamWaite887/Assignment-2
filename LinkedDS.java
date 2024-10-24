

public class LinkedDS<T extends Comparable<? super T>> implements SequenceInterface<T>, ReorderInterface, Comparable<LinkedDS<T>> {

    private class Node {
		T data;
		Node next;
		Node prev;

		public Node(T data){
			this.data = data;
		this.next = null;
		this.prev = null;
		}
	}

	private Node head;
	private Node tail;
	private int size;
	
	public LinkedDS(){
		head = null;
		tail = null;
		size = 0;
    }

    public LinkedDS(LinkedDS<T> other){
		if (other == null || other.head == null){
			head = null;
			tail = null;
			size = 0;
		} else {
			head = new Node(other.head.data);
			Node curr = head;
			Node otherCurr = other.head.next;
			while (otherCurr!=other.head){
				Node newNode = new Node(otherCurr.data);
				curr.next = newNode;
				newNode.prev = curr;
				curr = newNode;
				otherCurr = otherCurr.next;
			}
			tail = curr;
			tail.next = head;
			head.prev = tail;
			size = other.size;
		}

    }
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Node curr = head;
		
		for (int i=0; i < size; i++){
			System.out.println("Node " + i + ": " + curr.data);
			sb.append(curr.data);
			curr = curr.next;
		}
		return sb.toString();
		
	}
	@Override
	public int compareTo(LinkedDS<T> other){
		if (this.size != other.size){
			return this.size-other.size;
		}
		Node current = this.head;
		Node otherCurrent = other.head;
		do {
			int comp = current.data.compareTo(otherCurrent.data);
			if (comp != 0) {
				return comp;
			}
			current = current.next;
			otherCurrent = otherCurrent.next;

		} while (current != this.head && otherCurrent != other.head);
        
		if (current != null){
			return 1;
		}

		if (otherCurrent != null){
			return -1;
		}

		
		return 0;
	}
/** Logically reverse the data in the ReorderInterface object so that the item
	 * that was logically first will now be logically last and vice
	 * versa.  The physical implementation of this can be done in
	 * many different ways.
	 * Runtime: O(n)
	 */
	@Override
    public void reverse(){
		if (head == null){
			return;
		}
		if (head == tail){
			return;
		}
		Node curr = head;
		Node temp = null;
		for (int i = 0; i < size; i++){
			temp = curr.next;
			curr.next = curr.prev;
			curr.prev = temp;
			curr = temp;
		}
	temp = head;
    head = tail;
    tail = temp;
	}

	/** Remove the logically last item and put it at the
	 * front.  As with reverse(), this can be done physically in
	 * different ways depending on the underlying implementation.
	 * Runtime: O(1)
	 */
	@Override
	public void rotateRight(){
		Node temp = tail;
		tail = tail.prev;
		tail.next = head;
		head.prev = temp;
		temp.next = head;
		temp.prev = tail;
		head = temp;
	}

	/** Remove the logically first item and put it at the
	 * end.  As above, this can be done in different ways.
	 * Runtime: O(1)
	 */
	@Override
	public void rotateLeft(){
		Node temp = head;
		head = head.next;
		head.prev = tail;
		tail.next = temp;
		temp.prev = tail;
		temp.next = head;
		tail = temp;

	}

	/** (EXTRA CREDIT) Shuffle the items according to the given permutation. The permutation is defined using two parallel arrays, one containing
	 * the original positions and the other containing the new positions. For example, if oldPositions[0] contains 5 then newPositions[0] contains the
	 * new position for the item at position 5.
	 * Runtime: O(n); You may use arrays in this method.
	 * 
	 * @param oldPositions the int[] array of old positions
	 * @param newPositions the int[] array of new positions
	 * @throws IndexOutOfBoundsException if any of the old or new positions is < 0 or > size-1
	 * @throws IllegalArgumentException if oldPositions.length != newPositions.length or if there are duplicate values in either oldPositions or newPositions
	 */
	@Override
	public void shuffle(int[] oldPositions, int[] newPositions){

	}
	@Override
    public void append(T item){
		Node newItem = new Node(item);
		if (tail == null){
			head = newItem;
			tail = newItem;
			newItem.next = head;
			newItem.prev = tail; // I am trying to make a circular doubly linked list so that it is easier to make more efficient running code later on.
		} else{
		tail.next = newItem;
		newItem.prev = tail;
		newItem.next = head;
		tail = newItem;
		}
		size++;
	}

	/** Add a new item to the head (logical beginning) of the SequenceInterface<T>
	 * Runtime: O(1)
	 * @param item the item to be added.
	 */
	@Override
	public void prefix(T item){
		Node newItem = new Node(item);
		if (head == null){
			head = newItem;
			tail = newItem;
			newItem.prev = tail;
			newItem.next = head;
		}else{
			newItem.next = head;
			newItem.prev = tail;
			head.prev = newItem;
			tail.next = newItem;
			head = newItem;
		}
		size++;
	System.out.println("Prefix added: " + item);
    System.out.println("Head: " + head.data + ", Head.next: " + head.next.data + ", Head.prev: " + head.prev.data);
    System.out.println("Tail: " + tail.data + ", Tail.next: " + tail.next.data + ", Tail.prev: " + tail.prev.data);
	}

	/** Add a new item at a given position in the SequenceInterface<T>. Inserting at position size() is 
	 * the same as appending. All existing items in the SequenceInterface<T> should maintain their relative order.
	 * Runtime: O(n)
	 * @param item the T item to be added.
	 * @param position the int position of the added item
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()
	 */
	@Override
	public void insert(T item, int position){
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position out of bounds");
		}
		if (position == 0){
			prefix(item);
			return;
		}
		if (position == size){
			append(item);
			return;
		}
		Node previous = head;
		for (int i = 0; i < position-1; i++){
			previous = previous.next;
		}
		Node newNode = new Node(item);
		newNode.next = previous.next;
		newNode.prev = previous;
		previous.next.prev = newNode;
		previous.next = newNode;
		size++;
        // node before = root
        //for int i = 0; i < position; i++
        // before = before.next
        //Node item = new Node(item)
        //item.next = before.next
        //before.next = item 
    }

	/** Return the item at a given logical position in the SequenceInterface<T>
	 * Runtime: O(n)
	 * @param position the int logical position
	 * @return the T item at position
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()-1
	 */
	@Override
	public T itemAt(int position){
		if (position < 0) {
			throw new IndexOutOfBoundsException("Position out of bounds");
		}
		if (position >= size){
			throw new IndexOutOfBoundsException("Position out of bounds");
		}

		Node curr = head;
		if (position < 0 || position >= size) {
		}
		for (int i = 0; i < position; i++){
			curr = curr.next;
		}
		return curr.data;
	}
	@Override
	public boolean isEmpty(){
		return size == 0;
	}

	/**
	 * Runtime: O(1)
	 * @return the number of items currently in the SequenceInterface<T>
	 */
	@Override
	public int size(){
		return size;
	}

	/**
	 * Runtime: O(1)
	 * @return the logical first item in the SequenceInterface<T> or null if the SequenceInterface<T> is empty
	 */
	@Override
	public T first(){
		if (size == 0){
			return null;
		}
		return head.data;
	}

	/**
	 * Runtime: O(1)
	 * @return the logical last item in the SequenceInterface<T> or null if the SequenceInterface<T> is empty,
	 */
	@Override
	public T last(){
		if (size == 0){
			return null;
		}
		return tail.data;
	}

	/** Find any predecessor (i.e., item right before) of a given item in the SequenceInterface<T>
	 * Runtime: O(n)
	 * @param item the T item
	 * @return a T item that is right before item in the SequenceInterface<T>. If item occurs multiple times in the 
	 *         SequenceInterface<T>, any non-null predecessor is returned. If item doesn't exist or occurs once at the head
	 *         of the SequenceInterface<T>, null is returned.
	 */
	@Override
	public T predecessor(T item){
		Node curr = head;
		Node currItem = new Node(item);
		while (curr.next != head){
			if (curr.next.data == currItem.data){
				return curr.data;
			}
			curr = curr.next;
		}
		return null;
	}

	/** Return the number of occurrences in the SequenceInterface<T> of a given item
	 * Runtime: O(n)
	 * @param item the T item
	 * @return the number of occurrences in the SequenceInterface<T> of item
	 */
	@Override
	public int getFrequencyOf(T item){
		int count = 0;
		Node firstItem = new Node(item);
		Node curr = head;
		for (int i = 0; i<size; i++){
			curr = curr.next;
			if (curr.data == firstItem.data){
				count++;
			}
		}
		return count;
	}

	/** Reset the SequenceInterface<T> to an empty Sequence.
	 * Runtime: O(1)
	 */
	@Override
	public void clear(){
		head = null;
		tail = null;
		size=0;

	}

	/** Return the logical position in the SequenceInterface<T> of the last occurrence of a given item
	 * Runtime: O(n)
	 * @param item an item
	 * @return the int logical position in the SequenceInterface<T> of the last occurrence of item
	 *         or -1 if item doesn't exist in the SequenceInterface<T>
	 */
	@Override
	public int lastOccurrenceOf(T item){
		Node lastItem = new Node(item);
		Node curr = tail;
		for (int i = size-1; i >= 0; i--){
			if (curr.data == lastItem.data){
				return i;
			}
			curr = curr.prev;
		}
		return -1;
	}


	/** Delete the first item of the SequenceInterface<T>
	 * Runtime: O(1)
	* @return the deleted item
	* @throws EmptySequenceException if the SequenceInterface<T> is empty
	*/
	@Override
	public T deleteHead(){
		if (head == null){
			throw new EmptySequenceException("empty list");
		}
		T temp = head.data;
		if (size == 1){
			head =null;
			tail = null;
		}else{
		head = head.next;
		tail.next = head;
		head.prev = tail;
		}
		size--;
		return temp;

	}

	/** Delete the last item of the SequenceInterface<T>
	 * Runtime: O(1)
	 * @return the deleted item
	 * @throws EmptySequenceException if the Sequence is empty
	 */
	@Override
	public T deleteTail(){

		if (head == null){
			throw new EmptySequenceException("empty list");
		}
		T temp = tail.data;
		if (size == 1){
			head =null;
			tail = null;
		}else{
		tail = tail.prev;
		tail.next = head;
		head.prev = tail;
		}
		size--;
		return temp;
	}

	/** Delete a given number of items from the end of the SequenceInterface<T>
	 * Runtime: O(n)
	 * @param numItems the int number of items to delete
	 * @return true if the operation is successful and false if the SequenceInterface<T> has less than
	 *         numItems. If the SequenceInterface<T> is not long enough, no change will happen.
	 */
	@Override
	public boolean trim(int numItems){
		if (numItems < 0 || numItems > size){
			return false;
		}
		for (int i = 0; i < numItems; i++){
			tail = tail.prev;
			if (tail != null){
				tail.next = head;
			}
		}
		head.prev = tail;
		size-=numItems;
		if (size == 0){
			head = null;
			tail = null;
		}
		return true;
	}

	/** Delete a given number items from a given position in the SequenceInterface<T>
	 * Runtime: O(n)
	 * @param start the int starting position of the cut
	 * @param numItems the int number of items to cut
	 * @return true if the operation is successful and false if start+numItems > the size of the SequenceInterface<T>.
	 *  	   If the SequenceInterface<T> is not long enough, no change will happen.
	 */
	@Override
	public boolean cut(int start, int numItems){
		if (start < 0){
			return false;
		}
		if (start >= size){
			return false;
		}
		if (numItems < 0){
			return false;
		}
		if ((start+numItems) >size){
			return false;
		}
		Node current = head;
		for (int i = 0; i < start; i++){ // finding the starting position
			current = current.next; // current should equal the starting position now
		}// this is  the same thing as itemAt


		for (int i=0; i < numItems; i++){
			Node nextNode = current.next;

			if (current.prev != null){
				current.prev.next = current.next; // removing current by pointing the previous node to the next item
			}
			if (current.next != null){
				current.next.prev = current.prev; // making it doubly linked
			}
			if (current == head){
				head = current.next;
				if (head != null){
					head.prev = tail;
					tail.next = head;
				}
			}
			if (current == tail){
				tail = current.prev;
				if (tail != null){
					tail.next = head;
					head.prev = tail;
				}
			}
			current = nextNode;
		}


		size-=numItems;
		if (size == 0) {
			head = null;
			tail = null;
		}
		return true;
	}

	/** (EXTRA CREDIT) Return a slice containing all items that are less than or equal to a given item in the SequenceInterface<T>
	 * Runtime: O(n)
	 * 
	 * @param item the T item
	 * @return a SequenceInterface<T> containing all items that are less than or equal to the item, maintaining their relative order. 
	 *         No change should happen to the SequenceInterface<T> on which the method is called.
	 */
	@Override
	public SequenceInterface<T> slice(T item){
		return null;
	}

	/** (EXTRA CREDIT) Return a slice containing a given number of items of the SequenceInterface<T> starting from a given position
	 * Runtime: O(n)
	 * @param start the int start position
	 * @param numItems the int number of items
	 * @return a SequenceInterface<T> containing numItems items of the SequenceInterface<T>, starting from start and with their relative order. 
  	 *         No change should happen to the SequenceInterface<T> on which the method is called. If the SequenceInterface<T>
	 *         is not long enough, the method should return null.
	 */
	@Override
	public SequenceInterface<T> slice(int start, int numItems){
		return null;
	}

}



    

