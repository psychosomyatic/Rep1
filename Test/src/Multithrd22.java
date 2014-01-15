public class Multithrd22 {

	int i = 0;

	public int inc() {
		return i++;
	}

	public int dec() {
		return i--;
	}
	
	public static void main(String[] args) {

		Multithrd22 shared = new Multithrd22();

		Thread newThread1 = new Thread(new Inc(shared));
		Thread newThread2 = new Thread(new Inc(shared));
		Thread newThread3 = new Thread(new Inc(shared));
		Thread newThreaddec1 = new Thread(new Dec(shared));
		Thread newThreaddec2 = new Thread(new Dec(shared));
		Thread newThreaddec3 = new Thread(new Dec(shared));// create N (say 3) Incrementer threads
		
		newThread1.start();
		newThread2.start();
		newThread3.start();
		newThreaddec1.start();
		newThreaddec2.start();
		newThreaddec3.start();
		//...and M (say 3) dec Threads

}

}


class Dec implements Runnable {

	Multithrd22 shared;

	public Dec(Multithrd22 shared) {
		this.shared = shared;
	}

	public void run() {

		for (int i = 0; i < 10; i++) {
			shared.dec();
			System.out.println(shared.i);
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}


class Inc extends Thread {

	Multithrd22 shared;

	public Inc(Multithrd22 shared) {
		this.shared = shared;
	}

	public void run() {

		for (int i = 0; i < 10; i++) {
			shared.inc();
			System.out.println(shared.i);
		}

	}
	
		
}