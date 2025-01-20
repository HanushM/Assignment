class Biker implements Runnable {
    private String name;
    private long startTime;
    private long endTime;
    private long totalTime;
 
    public Biker(String name)
    {
        this.name = name;
    }
 
   
    public void run()
    {
        this.startTime = System.currentTimeMillis();
        try
        {  
            Thread.sleep(5000 + (long) (Math.random() * 5000));  
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        this.endTime = System.currentTimeMillis();
        this.totalTime = this.endTime - this.startTime;
    }
 
    public String getName() {
        return name;
    }
 
    public long getStartTime() {
        return startTime;
    }
 
    public long getEndTime() {
        return endTime;
    }
 
    public long getTotalTime() {
        return totalTime;
    }
   
    synchronized public static void countDown(int count)
    {
         System.out.println("Countdown: ");
            try
            {
                for (int i = count; i > 0; i--)
                {
                    System.out.println(i);
                    Thread.sleep(1000);
                }
                System.out.println("GO!");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }    
 
    }
 
    public static void sortRacers(Biker[] bikers)
    {
        for (int i = 0; i < bikers.length - 1; i++)
        {
            for (int j = 0; j < bikers.length - 1 - i; j++)
            {
                if (bikers[j].getTotalTime() > bikers[j + 1].getTotalTime())
                {
                    Biker temp = bikers[j];
                    bikers[j] = bikers[j + 1];
                    bikers[j + 1] = temp;
                }
            }
        }
    }
   
    public String toString()
    {
        return String.format("Name: %s | Start Time: %d | End Time: %d | Time Taken: %d ms",name, startTime, endTime, totalTime);
    }
}
 
public class BikeRacing
{
 
    public static void main(String[] args) throws InterruptedException
    {
        ThreadGroup tg = new ThreadGroup("Racers");
 
       
        Biker[] bikers = new Biker[10];
 
        Biker.countDown(10);
 
        for (int i = 0; i < 10; i++)
        {
            bikers[i] = new Biker("Racer-" + (i + 1));
        }
 
        Thread t1 = new Thread(tg,bikers[0]);
        Thread t2 = new Thread(tg,bikers[1]);
        Thread t3 = new Thread(tg,bikers[2]);
        Thread t4 = new Thread(tg,bikers[3]);
        Thread t5 = new Thread(tg,bikers[4]);
        Thread t6 = new Thread(tg,bikers[5]);
        Thread t7 = new Thread(tg,bikers[6]);
        Thread t8 = new Thread(tg,bikers[7]);
        Thread t9 = new Thread(tg,bikers[8]);
        Thread t10 = new Thread(tg,bikers[9]);
 
        t1.setName("Racer-1");
        t2.setName("Racer-2");
        t3.setName("Racer-3");
        t4.setName("Racer-4");
        t5.setName("Racer-5");
        t6.setName("Racer-6");
        t7.setName("Racer-7");
        t8.setName("Racer-8");
        t9.setName("Racer-9");
        t10.setName("Racer-10");
 
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
       
 
	//tg.join();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();
 
       /* Thread[] thread = new Thread[10];
       
        for (int i = 0; i < 10; i++)
        {
            bikers[i] = new Biker("Racer-" + (i + 1));
            thread[i] = new Thread(tg, bikers[i]);
            thread[i].setName("Racer-" + (i + 1));
           
        }
 
        for(int i=0;i<10;i++)
        {
            thread[i].start();
        }
       
        for (int i = 0; i < 10; i++)
        {
            thread[i].join();
        } */
 
       
       Biker.sortRacers(bikers);
       
 
       
        System.out.println("\nRankings:");
        for (int i = 0; i < bikers.length; i++)
        {
            System.out.println("Rank: " + (i + 1) + " " + bikers[i]);
        }
    }
}
 
 