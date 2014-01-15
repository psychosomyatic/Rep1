/* IMPORTANT: class must not be public. */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class TestClass {
    public static void main(String args[] ) throws Exception {
        
        // Read input from stdin and provide input before running
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int S = Integer.parseInt(line.split(" ")[0].trim());
        int N = Integer.parseInt(line.split(" ")[1].trim());
        HashSet<Integer> hs = new HashSet<Integer>();
        HashMap<Integer, HashSet<Integer>> hm = new HashMap<Integer, HashSet<Integer>>(); 
        for (int i = 0; i < S; i++) {
            hs.add(i);
        }
        for (int i = 0; i < N; i++) {
            line = br.readLine();
        	int s1 = Integer.parseInt(line.split(" ")[0].trim());
        	int s2 = Integer.parseInt(line.split(" ")[1].trim());
        	HashSet<Integer> s = new HashSet<Integer>();
	        	if(s1>=0 && s2>=0){
		            if(!hm.containsKey(s1)){
		            	s.add(s2);
		            	hm.put(s1,s);
		            }
		            else{
		            	s = hm.get(s1);
		            	s.add(s2);
		            	hm.remove(s1);
		            	hm.put(s1,s);
		            }
		            s = new HashSet<Integer>();
		            if(!hm.containsKey(s2)){
		            	s.add(s1);
		            	hm.put(s2,s);
		            }
		            else{
		            	s = hm.get(s2);
		            	s.add(s1);
		            	hm.remove(s2);
		            	hm.put(s2,s);
		            }
		        }
        }
        
        boolean krun = true;
        int i=0;
        int maxfriends = 0;
        int nodeAdd = 0;
        HashSet<Integer> hsresult = new HashSet<Integer>();
        HashSet<Integer> hscover = new HashSet<Integer>();
        while(krun){
        	i=0;
        	nodeAdd=-1;
        	maxfriends = 0;
	        while(i<S){
	        	if(hm.containsKey(i)){
	        		if(maxfriends < hm.get(i).size())
	        		{
	        			maxfriends = hm.get(i).size();
	        			nodeAdd = i;
	        		}
	        	}
	        	i++;
	        }
	        hs.remove(nodeAdd);
	        hscover = new HashSet<Integer>(hs);
	        if((nodeAdd != -1) && hm.containsKey(nodeAdd))
	        {
	        	hscover.retainAll(hm.get(nodeAdd));
	        	hs.removeAll(hscover);
	        
	        	hsresult.add(nodeAdd);
	        	hm.remove(nodeAdd);
	        }
	        if(hs.size()==0 || nodeAdd == -1)
	        	krun=false;
        }
       
        System.out.println(hsresult.size());
        Iterator<Integer> itr = hsresult.iterator();
        while(itr.hasNext()){
            System.out.print(itr.next()+" ");
        }
    }
}