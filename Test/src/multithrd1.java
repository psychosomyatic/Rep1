class SharedObject {

	int i=0;
    public synchronized int inc() {
			return i++;
    }
    
    public synchronized int dec() {
		return i--;
}
}



class multithrd1 extends Thread {

	SharedObject shared;	   

	 public multithrd1(SharedObject shared) {
		this.shared = shared;
	   }
	 public void multithrd2(SharedObject shared) {
			this.shared = shared;
		   }
	
     public void run (){
	    shared.i = 0;
		while (shared.i < 20)	
		{
			shared.inc();
			System.out.print("This is my thread! ");
			System.out.println(shared.i);
			
			try {
				Thread.currentThread().sleep(3000); // sleep for 3 sec
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
      }
   
   
     public static void main(String[] args){
	   
		SharedObject shared = new SharedObject();
		
		Thread newThread1 = new Thread(new multithrd1(shared));
		Thread newThread2 = new Thread(new multithrd1(shared));
		newThread1.start();
		newThread2.start();
	
   }

}
