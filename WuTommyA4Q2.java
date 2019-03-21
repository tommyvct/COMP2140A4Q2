import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Hash Table Implementations of a Dictionary. <p>
 * Main class for Assignment 3, Question 2.
 * This Class read a book file, split lines into words and add files into
 * 3 different kind of hash tables.
 * 
 * @author Tommy Wu (7852291)
 */
public class WuTommyA4Q2 
{
    
    public static void main(String[] args) 
    {
        long startTime;
        long elapsedTime;
        DictionaryOrdered ordered = null;
        DictionaryOpen open = null;
        DictionaryChain chain = null;
        
        // Ordered array
        try 
        {
            // import book
            ordered = new DictionaryOrdered(100);
            BufferedReader br = new BufferedReader(new FileReader("GreatExpectations.txt"));
            String bufferString = br.readLine();
            
            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                String[] words = bufferString.split(" ");

                for (String word : words) 
                {
                    ordered.insert(word);
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Using ordered array");
            System.out.println("Elapsed time: " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + ordered.getSize() + " words.");

            br.close();

            // start searching
            br = new BufferedReader(new FileReader("A4Q2TestWords.txt"));
            bufferString = br.readLine();
            int found = 0;
            int notFound = 0;

            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                if (ordered.search(bufferString))
                {
                    found++;
                }
                else
                {
                    notFound++;
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Search finished in " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + found + " words found, " + notFound + " words not found.");

        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }

        System.out.println();

        // Open addressing
        try 
        {
            // import book
            open = new DictionaryOpen(100);
            BufferedReader br = new BufferedReader(new FileReader("GreatExpectations.txt"));
            String bufferString = br.readLine();
            
            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                String[] words = bufferString.split(" ");

                for (String word : words) 
                {
                    open.insert(word);
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Using open addressing");
            System.out.println("Elapsed time: " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + open.getSize() + " words.");

            br.close();

            // start searching
            br = new BufferedReader(new FileReader("A4Q2TestWords.txt"));
            bufferString = br.readLine();
            int found = 0;
            int notFound = 0;

            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                if (open.search(bufferString))
                {
                    found++;
                }
                else
                {
                    notFound++;
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Search finished in " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + found + " words found, " + notFound + " words not found.");

        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }

        System.out.println();

        // Separate chain
        try 
        {
            // importing book
            chain = new DictionaryChain(100);
            BufferedReader br = new BufferedReader(new FileReader("GreatExpectations.txt"));
            String bufferString = br.readLine();
            
            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                String[] words = bufferString.split(" ");

                for (String word : words) 
                {
                    chain.insert(word);
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Using separate chaining");
            System.out.println("Elapsed time: " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + chain.getSize() + " words.");

            br.close();

            // start searching
            br = new BufferedReader(new FileReader("A4Q2TestWords.txt"));
            bufferString = br.readLine();
            int found = 0;
            int notFound = 0;

            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                if (chain.search(bufferString))
                {
                    found++;
                }
                else
                {
                    notFound++;
                }

                bufferString = br.readLine();
            }

            elapsedTime = System.nanoTime() - startTime;

            System.out.println("Search finished in " + (double) elapsedTime / 1000000000 + " seconds.");
            System.out.println("" + found + " words found, " + notFound + " words not found.");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }
    }
}

/**
 * Hash table made from ordered array.
 * 
 * @author Tommy Wu (7852291)
 */
class DictionaryOrdered
{
    private int dicArraySize;
    private String[] dicArray;

    /**
     * Constructor for {@code DictionaryOrdered} object.
     * Constructs a empty hash table.
     * 
     * @param initialSize initial size of hash table array.
     */
    public DictionaryOrdered(int initialSize) 
    {
        this.dicArraySize = 0;
        dicArray = new String[initialSize];
    }

    /**
     * Getter for actual table size.
     * 
     * @return actual table size.
     */
    public int getSize() 
    {
        return this.dicArraySize;
    }

    /**
     * Insert a new String into this hash table.
     * 
     * @param newWord String to insert
     */
    public void insert(String newWord)
    {
        String newWordInLC = newWord.toLowerCase();
        newWordInLC = newWordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");

        if (search(newWordInLC) || newWordInLC.equals(""))
        {
            return;
        }

        if (this.dicArray.length - this.dicArraySize < 2)
        {
            doubleSpace();
        }

        if (this.dicArraySize == 0)
        {
            this.dicArray[0] = newWordInLC;
            this.dicArraySize++;            
        }
        else
        {
            for (int i = 0; i < this.dicArraySize; i++) 
            {
                if (this.dicArray[i].compareTo(newWordInLC) > 0)
                {
                    insertAt(i, newWordInLC);
                    this.dicArraySize++;
                    return;
                }
            }

            this.dicArray[this.dicArraySize] = newWordInLC;
            this.dicArraySize++;
        }
    }

    /**
     * Search given word at this hash table.
     * Used binary search as implementation.
     * 
     * @param wordToFind String to find in table
     * @return {@code true} if found
     */
    public boolean search(String wordToFind)
    {
        int start = 0;
        int end = this.dicArraySize - 1;
        int mid = 0;
        String wordInLC = wordToFind.toLowerCase();
        wordInLC = wordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");

        while (start <= end)
        {
            mid = (start + end) / 2;
            if (this.dicArray[mid].equals(wordInLC))
            {
                return true;
            }
            else
            {
                //  this.dicArray[mid]     <     wordInLC
                if (this.dicArray[mid].compareTo(wordInLC) < 0)
                {
                    start = mid + 1;
                }
                else
                {
                    end = mid - 1;
                }
            }
        }

        return false;
    }

    /**
     * Private method for double the fix-sized array.
     */
    private void doubleSpace()
    {
        String[] newDicArray = new String[this.dicArray.length * 2];

        for (int i = 0; i < this.dicArraySize; i++) 
        {
            newDicArray[i] = this.dicArray[i];
        }

        this.dicArray = newDicArray;
    }

    /**
     * Private method for insert given String at given location. 
     * The method that actually insert things into table.
     * 
     * @param location where to insert
     * @param toInsert what to insert
     */
    private void insertAt(int location, String toInsert)
    {
        for (int i = this.dicArraySize; i >= location + 1; i--) 
        {
            this.dicArray[i] = this.dicArray[i - 1];
        }

        this.dicArray[location] = toInsert;
    }
}


/**
 * Open adderssing Hash Table.
 * 
 * @author Tommy Wu (7852291)
 */
class DictionaryOpen 
{
    private int dicArraySize;
    private String[] dicArray;

    /**
     * Constructor for {@code DictionaryOpen} object.
     * Constructs a empty hash table.
     * 
     * @param initialSize initial size of hash table array.
     */
    public DictionaryOpen(int initialSize) 
    {
        int primeInitialSize = getPrime(initialSize);

        this.dicArraySize = 0;
        this.dicArray = new String[primeInitialSize];
    }

    /**
     * Getter for actual table size.
     * 
     * @return actual table size.
     */
    public int getSize() 
    {
        return this.dicArraySize;
    }

    /**
     * Insert a new String into this hash table.
     * 
     * @param newWord String to insert
     */
    public void insert(String newWord)
    {
        String newWordInLC = newWord.toLowerCase();
        newWordInLC = newWordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");

        if (search(newWordInLC) || newWordInLC.equals(""))
        {
            return;
        }

        if (this.dicArraySize >= this.dicArray.length * 0.6)
        {
            doubleSpace();
        }

        insert(newWordInLC, this.dicArray);
        this.dicArraySize++;
    }

    /**
     * Private insert method that actually insert words
     * into given String[].
     * 
     * @param newWord String to insert
     * @param table String[] to insert into
     */
    private void insert(String newWord, String[] table)
    {
        int hash = hornerHash(newWord, table.length);

        while (hash < table.length && table[hash] != null)
        {
            hash += stepSize(newWord);
            hash %= this.dicArraySize;
        }

        table[hash] = newWord;
    }


    /**
     * Hash based search method.
     * 
     * @param wordToFind String to search
     * @return {@code true} if found
     */
    public boolean search(String wordToFind)
    {
        String wordInLC = wordToFind.toLowerCase();
        wordInLC = wordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");
        int hash = hornerHash(wordInLC, this.dicArray.length);
        
        while (hash < this.dicArray.length)
        {
            if (this.dicArray[hash] == null)
            {
                return false;
            }

            if (this.dicArray[hash].equals(wordInLC))
            {
                return true;
            }
            else
            {
                hash += stepSize(wordInLC);
            }
        }

        return false;
    }

    /**
     * Private method to find the next prime number of given number
     * 
     * @param min an int
     */
    private int getPrime(int min)
    {
        for (int j = min + 1; true; j++) 
        {
            if (isPrime(j))
            {
                return j;
            }
        }
    }

    /**
     * Private method to tell is given int is prime or not.
     * 
     * @param n int to be tested
     * @return {@code true} if {@code n} is prime
     */
    private boolean isPrime(int n)
    {
        for (int j = 2; (j*j <= n); j++) 
        {
            if (n % j == 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * First hash method for this hash table.
     * Calculate hash by given key and maximum array length
     * 
     * @param key int to be hashed
     * @param arrLength maximum array length
     * @return hash value of given String
     */
    private int hornerHash(String key, int arrLength)
    {
        int hashVal = 0;
        
        for (int j = 0; j < key.length(); j++) 
        {
            int letter = key.charAt(j) - 96;
            if (letter < 0)
            {
                continue;
            }
            hashVal = (hashVal * 27 + letter) % arrLength;
        }

        return hashVal;
    }

    /**
     * Calculate the sum of every char in a String.
     * 
     * @param toSum String to calculate
     * @return the sum of every char in the given String.
     */
    private int getSumOfChars(String toSum)
    {
        int sum = 0;
        
        for (int i = 0; i < toSum.length(); i++) 
        {
            if ((toSum.charAt(i) - 96) < 0)
            {
                continue;
            }

            sum += toSum.charAt(i) - 96;
        }

        return sum;
    }

    /**
     * Part of second hash function.
     * Calculate how much slots to skip.
     * 
     * @param toEval String to be hased for second time
     * @return hash value
     */
    private int stepSize(String toEval)
    {
        return 41 - (getSumOfChars(toEval) % 41);
    }

    /**
     * Private method for double the fix-sized array.
     */
    private void doubleSpace()
    {
        String[] newDicArray = new String[this.dicArray.length * 2];

        for (int i = 0; i < this.dicArraySize; i++) 
        {
            if (this.dicArray[i] != null)
            {
                insert(this.dicArray[i], newDicArray);
            }
        }

        this.dicArray = newDicArray;
    }

}


/**
 * Hash table made that used LinkedList to solve conflict.
 * 
 * @author Tommy Wu (7852291)
 */
class DictionaryChain 
{
    private int dicArraySize;
    private LinkedList[] dicArray;

    /**
     * Constructor for {@code DictionaryOrdered} object.
     * Constructs a empty hash table.
     * 
     * @param initialSize initial size of hash table array.
     */
    public DictionaryChain(int initialSize) 
    {
        int primeInitialSize = getPrime(initialSize);

        this.dicArraySize = 0;
        this.dicArray = new LinkedList[primeInitialSize];
    }

    /**
     * Getter for actual table size.
     * 
     * @return actual table size.
     */
    public int getSize() 
    {
        return this.dicArraySize;
    }

    /**
     * Insert a new String into this hash table.
     * 
     * @param newWord String to insert
     */
    public void insert(String newWord)
    {
        String newWordInLC = newWord.toLowerCase();
        newWordInLC = newWordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");

        if (search(newWordInLC) || newWordInLC.equals(""))
        {
            return;
        }

        if (this.dicArraySize > 2 * dicArray.length)
        {
            doubleSpace();
        }

        int hash = hash(newWordInLC);

        if (this.dicArray[hash] == null)
        {
            this.dicArray[hash] = new LinkedList();
        }

        this.dicArray[hash].insert(newWordInLC);
        this.dicArraySize++;
    }

    /**
     * Hash based search method.
     * 
     * @param wordToFind String to search
     * @return {@code true} if found
     */
    public boolean search(String wordToFind)
    {
        String wordInLC = wordToFind.toLowerCase();
        wordInLC = wordInLC.replaceAll("[^a-zA-Z']+|\\B'\\b|\\b'\\B", "");
        int hash = hash(wordInLC);
        
        if (this.dicArray[hash] == null)
        {
            return false;
        }
        else
        {
            return this.dicArray[hash].find(wordInLC);
        }
    }

    /**
     * Private method to find the next prime number of given number
     * 
     * @param min an int
     */
    private int getPrime(int min)
    {
        for (int j = min + 1; true; j++) 
        {
            if (isPrime(j))
            {
                return j;
            }
        }
    }

    /**
     * Private method to tell is given int is prime or not.
     * 
     * @param n int to be tested
     * @return {@code true} if {@code n} is prime
     */
    private boolean isPrime(int n)
    {
        for (int j = 2; (j*j <= n); j++) 
        {
            if (n % j == 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Hash function for this table.
     * 
     * @param toHash String to be hashed
     * @return hash value of given String
     */
    private int hash(String toHash)
    {
        return getSumOfChars(toHash) % this.dicArray.length;
    }

    /**
     * Calculate the sum of every char in a String.
     * 
     * @param toSum String to calculate
     * @return the sum of every char in the given String.
     */
    private int getSumOfChars(String toSum)
    {
        int sum = 0;
        
        for (int i = 0; i < toSum.length(); i++) 
        {
            if ((toSum.charAt(i) - 96) < 0)
            {
                continue;
            }
            sum += toSum.charAt(i) - 96;
        }

        return sum;
    }

    /**
     * Private method for double the fix-sized array.
     */
    private void doubleSpace()
    {
        LinkedList[] newDicArray = new LinkedList[this.dicArray.length * 2];

        for (int i = 0; i < this.dicArray.length; i++) 
        {
            newDicArray[i] = this.dicArray[i];
        }

        this.dicArray = newDicArray;
    }
}

/**
 * LinkedList
 */
class LinkedList 
{
    private Node top;

    public LinkedList()
    {
        top = null;
    }

    /**
     * @return the top
     */
    public Node getTop() 
    {
        return top;
    }

    public void insert(String toInsert)
    {
        
        if (this.top == null)
        {
            this.top = new Node(toInsert);
        }
        else
        {
            Node curr = top;

            while (curr.getNext() != null)
            {
                curr = curr.getNext();
            }

            curr.setNext(new Node(toInsert));
        }
    }
    
    public boolean find(String toFind)
    {
        Node curr = this.top;

        while (curr != null)
        {
            if (curr.getData().equals(toFind))
            {
                return true;
            }
            else
            {
                curr = curr.getNext();
            }
        }

        return false;
    }
    
}

/**
 * Node
 */
class Node 
{
    private String data;
    private Node next;

    public Node(String newString)
    {
        this.data = newString;
        next = null;
    }

    /**
     * @return the data
     */
    public String getData() 
    {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) 
    {
        this.data = data;
    }

    /**
     * @return the next
     */
    public Node getNext() 
    {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Node next) 
    {
        this.next = next;
    }
}