package sorting_hat.data;

import java.util.ArrayList;
import java.util.HashMap;
import sorting_hat.ui.SortingHatTile;
import static sorting_hat.SortingHatConstants.*;

/**
 * This factory class builds the sorting algorithm objects to be
 * used for sorting in the game.
 *
 * @author Richard McKenna & _____________________
 */
public class SortingHatAlgorithmFactory
{
    // STORES THE SORTING ALGORITHMS WE MAY WISH TO USE
    static HashMap<SortingHatAlgorithmType, SortingHatAlgorithm> premadeSortingHatAlgorithms = null;

    /**
     * For getting a particular sorting algorithm. Note that the first
     * time it is called it initializes all the sorting algorithms and puts 
     * them in a hash map to be retrieved as needed to setup levels when loaded.
     */
    public static SortingHatAlgorithm buildSortingHatAlgorithm( SortingHatAlgorithmType algorithmType,
                                                                ArrayList<SortingHatTile> initDataToSort)
    {
        // INIT ALL THE ALGORITHMS WE'LL USE IF IT HASN'T DONE SO ALREADY
        if (premadeSortingHatAlgorithms == null)
        {
            premadeSortingHatAlgorithms = new HashMap();
            premadeSortingHatAlgorithms.put(SortingHatAlgorithmType.BUBBLE_SORT, new BubbleSortAlgorithm(initDataToSort, BUBBLE_SORT));
            premadeSortingHatAlgorithms.put(SortingHatAlgorithmType.SELECTION_SORT, new SelectionSortAlgorithm(initDataToSort, SELECTION_SORT));
        }
        // RETURN THE REQUESTED ONE
        return premadeSortingHatAlgorithms.get(algorithmType);
    }
}

/**
 * This class builds all the transactions necessary for performing
 * bubble sort on the data structure. This can then be used to
 * compare to student moves during the game.
 */
class BubbleSortAlgorithm extends SortingHatAlgorithm
{
    /**
     * Constructor only needs to init the inherited stuff.
     */
    public BubbleSortAlgorithm(ArrayList<SortingHatTile> initDataToSort, String initName)
    {
        // INVOKE THE PARENT CONSTRUCTOR
        super(initDataToSort, initName);
    }
    
    /**
     * Build and return all the transactions necessary to sort using bubble sort.
     */
    public ArrayList<SortTransaction> generateSortTransactions()
    {
        // HERE'S THE LIST OF TRANSACTIONS
        ArrayList<SortTransaction> transactions = new ArrayList();
        
        // FIRST LET'S COPY THE DATA TO A TEMPORARY ArrayList
        ArrayList<SortingHatTile> copy = new ArrayList();
        for (int i = 0; i < dataToSort.size(); i++)
            copy.add(dataToSort.get(i));

        // NOW SORT THE TEMPORARY DATA STRUCTURE
        for (int i = copy.size()-1; i > 0; i--)
        {
            for (int j = 0; j < i; j++)
            {
                // TEST j VERSUS j+1
                if (copy.get(j).getID() > copy.get(j+1).getID())
                {
                    // BUILD AND KEEP THE TRANSACTION
                    SortTransaction sT = new SortTransaction(j, j+1);
                    transactions.add(sT);
                    
                    // SWAP
                    SortingHatTile temp = copy.get(j);
                    copy.set(j, copy.get(j+1));
                    copy.set(j+1, temp);
                }
            }
        }
        return transactions;
    }
}

class SelectionSortAlgorithm extends SortingHatAlgorithm
{
    /**
     * Constructor only needs to init the inherited stuff.
     */
    public SelectionSortAlgorithm(ArrayList<SortingHatTile> initDataToSort, String initName)
    {
        // INVOKE THE PARENT CONSTRUCTOR
        super(initDataToSort, initName);
    }
    
    /**
     * Build and return all the transactions necessary to sort using bubble sort.
     */
    public ArrayList<SortTransaction> generateSortTransactions()
    {
        // HERE'S THE LIST OF TRANSACTIONS
        ArrayList<SortTransaction> transactions = new ArrayList();
        
        // FIRST LET'S COPY THE DATA TO A TEMPORARY ArrayList
        ArrayList<SortingHatTile> copy = new ArrayList();
        for (int i = 0; i < dataToSort.size(); i++)
            copy.add(dataToSort.get(i));
    int lowPos;
        // NOW SORT THE TEMPORARY DATA STRUCTURE
        for (int i = 0; i < copy.size() - 1; i++)
        {
            
             lowPos = i;
            for (int j = i + 1; j < copy.size(); j++)
            {
     
           
            if (copy.get(lowPos).getID() > copy.get(j).getID())
            {
                lowPos = j;   
            }
            
            }
            if(lowPos !=i)
            {
            SortTransaction sT = new SortTransaction(lowPos, i);
                transactions.add(sT);
                
                SortingHatTile temp = copy.get(i);
                    copy.set(i, copy.get(lowPos));
                   copy.set(lowPos, temp);
                   
            }
           
    }
        return transactions;
    }
}