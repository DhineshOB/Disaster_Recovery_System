

/**
 * @author Dhinesh OB
 * @version 1.3 
 */

import java.io.IOException;


public class mainClass {
	
	 public static void main(String args[])
	    {
		 	
		 	
		 			final DRVM myvm = new DRVM(	"T08-vm01-Ubuntu32-Dhinesh");
		

	
		
		myvm.setAlarm();
		myvm.helloVM();	
		
		Thread t1 = new Thread() {
			public void run() {
				while (true) {
					try {
												boolean result = myvm.pingVM();
						if (result == true) {
														System.out.println("Able To Ping VM");
						} else {

							String state = myvm.getVMState();
							if (state.equalsIgnoreCase("poweredoff")) {
								System.out.println("VM is Powered-off by user. Waiting for Vm to be Live.");
							} 
							else {
								System.out.println("VM is in power on state so its failure.");
								boolean hresult = myvm.pingSecondHost();
								if (hresult == true) {
									System.out.println("Another host is working.Please wait...");
									try {
											myvm.cloneFromSnapshot("", "");    
																						Thread.sleep(100000);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									System.out.println("Second Host is not working..Wait till it resumes.");
								}

							}

						}

					} catch (IOException e) {

						e.printStackTrace();
					}
					try {
						Thread.sleep(50000); 					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}
		 	};
		 	
		 	Thread t2= new Thread(){
		 		public void run(){
		 			while(true)
		 			{
		 				myvm.snapShot("", "create");
		 				try {
							Thread.sleep(200000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		 			}
		 		}
		 	};
		 	myvm.getAlarm();	
		 	t1.start();
		 	t2.start();
	    }
}