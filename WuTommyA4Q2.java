import java.io.BufferedReader;
import java.io.FileReader;

/**
 * WuTommyA4Q2
 */
public class WuTommyA4Q2 
{
    
    public static void main(String[] args) 
    {
        long startTime;
        long endTime;
        long elapsedTime;
        
        try 
        {
            DictionaryOrdered dicOrdered = new DictionaryOrdered(100);
            BufferedReader br = new BufferedReader(new FileReader("GreatExpectations.txt"));
            String bufferString = br.readLine();
            
            startTime = System.nanoTime();

            while (bufferString != null) 
            {
                String[] words = bufferString.split(" ");
                for (String word : words) 
                {
                    dicOrdered.insert(word);
                }
                bufferString = br.readLine();
            }

            endTime = System.nanoTime();

            elapsedTime = endTime - startTime;
            System.out.println("Elapsed time: " + (double) elapsedTime / 1000000000);
            System.out.println("" + dicOrdered.getSize() + " words.");

            br.close();
        } 
        catch (Exception e) 
        {
            System.err.println(e);
        }
    }
    

}

/**
 * DictionaryOrdered
 */
class DictionaryOrdered
{
    int dicArraySize;
    String[] dicArray;

    public DictionaryOrdered(int initialSize) 
    {
        this.dicArraySize = 0;
        dicArray = new String[initialSize];
    }

    public int getSize() 
    {
        return this.dicArraySize;
    }

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

    public boolean search(String wordToFind)
    {
        int start = 0;
        int end = this.dicArraySize - 1;
        int mid = 0;

        while (start <= end)
        {
            mid = (start + end) / 2;
            if (this.dicArray[mid].equals(wordToFind))
            {
                return true;
            }
            else
            {
                //  this.dicArray[mid]     <     wordToFind
                if (this.dicArray[mid].compareTo(wordToFind) < 0)
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

    private void doubleSpace()
    {
        String[] newDicArray = new String[this.dicArray.length * 2];

        for (int i = 0; i < this.dicArraySize; i++) 
        {
            newDicArray[i] = this.dicArray[i];
        }

        this.dicArray = newDicArray;
    }

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
 * DictionaryOpen
 */
class DictionaryOpen 
{
    int dicArraySize;
    String[] dicArray;

    public DictionaryOpen(int initialSize) 
    {
        int primeInitialSize = getPrime(initialSize);

        this.dicArraySize = 0;
        this.dicArray = new String[primeInitialSize];
    }

    public int getSize() 
    {
        return this.dicArraySize;
    }

    public void insert(String newWord)
    {
        if (this.dicArraySize >= this.dicArray.length * 0.6)
        {
            doubleSpace();
        }

        insert(newWord, this.dicArray);
    }

    private void insert(String newWord, String[] table)
    {
        int hash = hornerHash(newWord, this.dicArray.length);

        while (table[hash] != null)
        {
            hash += stepSize(newWord);
        }

        table[hash] = newWord;
    }


    // public boolean search(String wordToFind)
    // {

    // }

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

    private int hornerHash(String key, int arrLength)
    {
        int hashVal = 0;
        
        for (int j = 0; j < key.length(); j++) 
        {
            int letter = key.charAt(j) - 96;
            hashVal = (hashVal * 27 + letter) % arrLength;
        }

        return hashVal;
    }

    private int getSumOfChars(String toSum)
    {
        int sum = 0;
        
        for (int i = 0; i < toSum.length(); i++) 
        {
            sum += toSum.charAt(i) - 96;
        }

        return sum;
    }

    private int stepSize(String toEval)
    {
        return 41 - (getSumOfChars(toEval) % 41);
    }

    private void doubleSpace()
    {
        String[] newDicArray = new String[this.dicArray.length * 2];

        for (int i = 0; i < this.dicArraySize; i++) 
        {
            newDicArray[i] = this.dicArray[i];
        }

        this.dicArray = newDicArray;
    }

}


// /**
//  * DictionaryChain
//  */
// class DictionaryChain 
// {
//     int dicSize;
    
//     public DictionaryChain(int dicSize) 
//     {
//         this.dicSize = dicSize;
//     }

//     public int getSize() 
//     {
//         return this.dicSize;
//     }

//     public void insert(String newWord)
//     {

//     }

//     public boolean search(String wordToFind)
//     {

//     }
// }