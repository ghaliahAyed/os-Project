import java.util.*;
import java.io.*;

public class Main {
   static Scanner input = new Scanner(System.in);
   static Partition[] partitions;
   static char allocationApproach = ' ';

   public static void main(String[] args) {
   
   	// Number of Partitions
      int M = 0;
      int sizeOfPartition = 0;
   
      int choice = 0;
      boolean flag = true, validate = true;
      int start = 0, end = 0;
      String pID;
      int pSize;
      
      while (validate) {
         try {
            System.out.print("Enter the number of partitions: ");
            M = input.nextInt();
         
            while (M <= 0) {
               System.out.print("Negative/Zero number is not allaowed! Enter the number of partitions again: ");
               M = input.nextInt();
            }
         
            validate = false;
         
         } catch (Exception e) {
            System.out.print("Invalid input! ");
            input.nextLine();
         }
      }
   
      partitions = new Partition[M];
      for (int i = 0; i < partitions.length; i++) {
         validate = true;
      
         while (validate) {
            try {
               System.out.print("Enter the size of partition #" + (i + 1) + " in KB: ");
               sizeOfPartition = input.nextInt();
            
               while (sizeOfPartition < 0) {
                  System.out.print("Negative number is not allowed! Enter the size of partition #" + (i + 1)
                     	+ " in KB again: ");
                  sizeOfPartition = input.nextInt();
               }
               validate = false;
            } catch (Exception e) {
               System.out.print("Invalid input! ");
               input.nextLine();
            }
         
         }
         end = start + (sizeOfPartition);
      
         partitions[i] = new Partition(sizeOfPartition, start, end);
         start = end ;
      
      }
   
      System.out.print("Enter the memory allocation approach [First-fit (F), Best-fit (B), or Worst-fit (W)]: ");
      allocationApproach = input.next().charAt(0);
      while (allocationApproach != 'F' && allocationApproach != 'f' && allocationApproach != 'B'
      		&& allocationApproach != 'b' && allocationApproach != 'W' && allocationApproach != 'w') {
         System.out.print(
            	"Invalid symbole!\nEnter the memory allocation approach [First-fit (F), Best-fit (B), or Worst-fit (W)]: ");
         allocationApproach = input.next().charAt(0);
      
      }
      printMemoryStatus();
   
      while (flag) {
         validate = true;
         while (validate) {
            try {
               choice = menue();
               validate = false;
            } catch (Exception e) {
               System.out.println("Invalid input!");
               input.nextLine();
            }
         }
      
         switch (choice) {
            case 1:
               System.out.print("Enter the process ID and size: ");
               pID = input.next();
               pSize = input.nextInt();
               if (allocate(pID, pSize))
                  System.out.println("Processes was allocated successfully.");
               else
                  System.out.println("SORRY! processes allocation was unsuccessful.");
               printMemoryStatus();
               break;
         
            case 2:
               System.out.print("Enter the process ID: ");
            
               if (deAllocate(input.next()))
                  System.out.println("Processes was released successfully.");
               else
                  System.out.println("SORRY! invalid processes ID/Name.");
               printMemoryStatus();
               break;
         
            case 3:
               System.out.println();
               print();
               try {
                  report();
               } catch (Exception e) {
               }
               break;
         
            case 4:
               flag = false;
               break;
         
            default:
               System.out.println("Invalid choice!");
         
         }
      
      }
   
   }

   public static int menue() throws Exception {
      System.out.print("\n****What would you like to do****\n" + " 1. Allocate a block of memory.\n"
         	+ " 2. De-allocate a block of memory.\n"
         	+ " 3. Report detailed information about regions of free and allocated memory blocks.\n"
         	+ " 4. Exit the program.\n" + "Enter your choice: ");
      return input.nextInt();
   }

   public static boolean firstFit(String ID, int size) {
      for (int i = 0; i < partitions.length; i++)
         if (partitions[i].getPartitionStatus().equalsIgnoreCase("free")
         		&& partitions[i].getPartitionSize() >= size) {
            partitions[i].setProcessID(ID);
            partitions[i].setProcessSize(size);
            return true;
         }
      return false;
   
   }

   public static boolean bestFit(String ID, int size) {
      int bestSize = -1;
   
      for (int i = 0; i < partitions.length; i++)
         if (partitions[i].getPartitionStatus().equalsIgnoreCase("free") && partitions[i].getPartitionSize() >= size)
            if (bestSize == -1) // to change its value when first find a free and >=size partition
               bestSize = i;
            else if (partitions[bestSize].getPartitionSize() > partitions[i].getPartitionSize())
               bestSize = i;
   
      if (bestSize > -1) {
         partitions[bestSize].setProcessID(ID);
         partitions[bestSize].setProcessSize(size);
         return true;
      }
   
      return false;
   
   }

   public static boolean worstFit(String ID, int size) {
      int largestSize = -1;
      for (int i = 0; i < partitions.length; i++)
         if (partitions[i].getPartitionStatus().equalsIgnoreCase("free") && partitions[i].getPartitionSize() >= size)
            if (largestSize == -1)
               largestSize = i;
            else if (partitions[largestSize].getPartitionSize() < partitions[i].getPartitionSize())
               largestSize = i;
   
      if (largestSize > -1) {
         partitions[largestSize].setProcessID(ID);
         partitions[largestSize].setProcessSize(size);
         return true;
      }
   
      return false;
   }

   public static boolean allocate(String ID, int size) {
   
      switch (allocationApproach) {
      
         case 'F':
         case 'f':
            return firstFit(ID, size);
      
         case 'B':
         case 'b':
            return bestFit(ID, size);
      
         case 'W':
         case 'w':
            return worstFit(ID, size);
      
      }
      return true;
   }

   public static boolean deAllocate(String ID) {
      for (int i = 0; i < partitions.length; i++)
         if (partitions[i].getProcessID().equalsIgnoreCase(ID)) {
            partitions[i].setDefault();
            return true;
         }
      return false;
   }

   public static void print() {
      for (int i = 0; i < partitions.length; i++) {
         System.out.println("--------------Partition #" + (i + 1) + "--------------");
         partitions[i].printStatus();
         System.out.println("----------------------------------------");
         System.out.println();
      }
   
   }

   public static void report() throws IOException {
      BufferedWriter Write = new BufferedWriter(new FileWriter("Report.txt"));
      for (int i = 0; i < partitions.length; i++) {
         Write.write("Partition Status: " + partitions[i].getPartitionStatus());
         Write.newLine();
         Write.write("Size of a Partition: " + partitions[i].getPartitionSize());
         Write.newLine();
         Write.write("Starting Address: " + partitions[i].getStartingAddress());
         Write.write(" End Address: " + partitions[i].getEndingAddress());
         Write.newLine();
         Write.write("Process Currently Allocated: " + partitions[i].getProcessID());
         Write.newLine();
         Write.write("Fragmentation Size: " + partitions[i].getFragmentationSize());
         Write.newLine();
         Write.newLine();
      
      }
   
      Write.close();
   }

   public static void printMemoryStatus() {
      System.out.print("Memory State: [ ");
      for (int i = 0; i < partitions.length; i++) {
         if (partitions[i].getPartitionStatus().equalsIgnoreCase("free"))
            System.out.print("H");
         else
            System.out.print(partitions[i].getProcessID());
         if (i != partitions.length - 1)
            System.out.print(" | ");
      }
   
      System.out.println(" ]");
   }

}
