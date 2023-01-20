/* Alice is a caretaker of n gardens and she wants to plant flowers to maximize the total beauty of all her gardens... You are given a 0-indexed integer array flowers of size n, where flowers[i] is the number of flowers already planted in the ith garden... Flowers that are already planted cannot be removed... You are then given another integer newFlowers, which is the maximum number of flowers that Alice can additionally plant... You are also given the integers target, full, and partial... A garden is considered complete if it has at least target flowers... The total beauty of the gardens is then determined as the sum of the following:
* The number of complete gardens multiplied by full...
* The minimum number of flowers in any of the incomplete gardens multiplied by partial... If there are no incomplete gardens, then this value will be 0...
Return the maximum total beauty that Alice can obtain after planting at most newFlowers flowers...
  * Eg 1: flowers = [1,3,1,1]  newFlowers = 7   target = 6  full = 12  partial = 2      Output = 14
  * Explanation of above case -> {Alice can plant - 2 flowers in the 0th garden, - 3 flowers in the 1st garden, - 1 flower in the 2nd garden, - 1 flower in the 3rd garden, The gardens will then be [3,6,2,2]. She planted a total of 2 + 3 + 1 + 1 = 7 flowers. There is 1 garden that is complete. The minimum number of flowers in the incomplete gardens is 2. Thus, the total beauty is 1 * 12 + 2 * 1 = 12 + 2 = 14. No other way of planting flowers can obtain a total beauty higher than 14.} 
  * Eg 2: flowers = [2,4,5,3]  newFlowers = 10  target = 5  full = 2   partial = 6      Output = 26
  * Explanation of above case -> {Alice can plant - 2 flowers in 0th garden, - 0 flowers in 1st garden, - 0flowers in 2nd garden and 1 flower in 4th garden. The gardens will be then [4,4,5,4]. She planted a total of 2 + 0 + 0 + 1 = 3 flowers. 1 garden is complete. The minimum number of flowers in incomplete garden is 4. Thus, total beauty is 1 * 2 + 4 * 6 = 2 + 24 = 26.}*/
import java.util.*;
public class Beauty
{
    public long MaximizeBeauty(int flowers[], long newFlowers, int target, int full, int partial)
    {
        long maxBeauty = 0l;
        if(full >= partial)     // If full garden has greater value, we try to full the maximum gardens...
            maxBeauty = PriorityToFull(flowers, newFlowers, target, full, partial);
        else if(full < partial)  // If partial garden has greater value, we try to maximize the minimum flowers in partial garden...
            maxBeauty = PriorityToPartial(flowers, newFlowers, target, full, partial);
        return maxBeauty;
    }
    public int PriorityToFull(int flowers[], long newFlowers, int target, int full, int partial)
    {
        Arrays.sort(flowers);          // Sorting the gardens in increasing order...
        int i = flowers.length-1, count = 0, loop = 0;
        while(loop == 0)
        {
            if(flowers[i] >= target)    // If a garden is already full...
                count++;
            else if(flowers[i] < target)
            {
                if(flowers[i] + newFlowers >= target)    // If we have enough flowers to full a garden...
                {
                    newFlowers = newFlowers - (target - flowers[i]);    // Update the available flowers...
                    count++;
                }
                else
                    loop--;    // If there are not enough flowers to full a garden...
            }
            i--;
        }
        int Incomplete = flowers.length - count, least = target, j = 0;
        System.out.println("Incomplete gardens : "+Incomplete);
        while(newFlowers != 0)
        {
            if(j == Incomplete)
                j = 0;      // We iterate over every Incomplete garden, until no flowers are left...
            flowers[j] = flowers[j] + 1;    // We add a flower to every Incomplete garden one by one...
            System.out.println("Flowers : "+flowers[j]+" index : "+j);
            newFlowers--;
            j++;
        }
        for(int k = 0; k < Incomplete; k++)
            least = Math.min(least, flowers[k]);    // We take the Incomplete garden with minimum flowers...
        System.out.println("The Number of Full Gardens : "+count);
        System.out.println("The Minimum flowers in Incomplete Garden : "+least);
        System.out.println("Beauty of Full Gardens : "+(full * count));
        System.out.println("Beauty of Incomplete Gardens : "+(partial * least));
        return (partial*least) + (full*count);    // Return the answer according to the formula...
    }
    public int PriorityToPartial(int flowers[], long newFlowers, int target, int full, int partial)
    {
        int count = 0, j = flowers.length-1, least = target;
        Arrays.sort(flowers);     // Sorting the array...
        while(flowers[j] >= target)
        {
            count++;    // Checking the number of gardens which are full...
            j--;
        }
        int Incomplete = flowers.length - count;    // Getting the number of Incomplete gardens...
        j = 0;
        while(newFlowers != 0)     // Until no flowers are available to be planted...
        {
            if(j == Incomplete)
                j = 0;
            if(target - flowers[j] > 1)    // If the current garden, does not have the maximized minimum flowers...
                flowers[j] = flowers[j] + 1;
            newFlowers--;
            j++;
        }
        System.out.println("Incomplete gardens : "+Incomplete);
        for(int k = 0; k < Incomplete; k++)
            least = Math.min(least, flowers[k]);   // Getting the least flowers of the Incomplete garden...
        System.out.println("The Number of Full Gardens : "+count);
        System.out.println("The Minimum flowers in Incomplete Garden : "+least);
        System.out.println("Beauty of Full Gardens : "+(full * count));
        System.out.println("Beauty of Incomplete Gardens : "+(partial * least));
        return (partial*least) + (full*count);    // Getting the total beauty by the formula...
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int x;
        System.out.print("Enter the number of Gardens : ");
        x = sc.nextInt();
        int flowers[] = new int[x];
        for(int i = 0; i < flowers.length; i++)
        {
            System.out.print("Enter the number of flowers in "+(i+1)+" th Garden : ");
            flowers[i] = sc.nextInt();
        }
        System.out.print("Enter the number of new flowers available : ");
        long x1 = sc.nextLong();
        System.out.print("Enter the full garden's Beauty : ");
        int k = sc.nextInt();
        System.out.print("Enter the Partially full garden's Beauty : ");
        int f = sc.nextInt();
        System.out.print("Enter the target value : ");
        int t = sc.nextInt();
        Beauty beauty = new Beauty();    // object creation...
        System.out.println("The Maximum Beauty of the Gardens : "+beauty.MaximizeBeauty(flowers, x1, t, k, f));
        sc.close();
    }
}

// Time Complexity  - O(n log n) time...
// Space Complexity - O(1) space...

/* DEDUCTIONS :- 
 * 1. Since we get maximum beauty on the values of full and partial, thus there are two cases if either of them is greater than another...
 * 2. If full is greater, we tend to maximize the number of full gardens and with the leftover flowers we tend to increase the minimum of the Incomplete gardens...
 * 3. If partial is greater, we simply maximize each incomplete garden to value one less than the target possible to attain the maximum beauty in this case...
 * 4. Since the Algorithms in both cases are greedy, the maximization using Iterative for both subproblems will do well...
*/