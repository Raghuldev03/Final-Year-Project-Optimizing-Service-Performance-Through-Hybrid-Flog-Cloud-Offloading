package cloud;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         long tm1=System.currentTimeMillis();
        VMScheduling vms=new VMScheduling();
        vms.readPM();
        vms.readVM();    
        vms.selectPM();
        vms.computeWeight();     
        long tm2=System.currentTimeMillis();
        
        long tim=tm2-tm1;
        System.out.println(tim);
        Graph1 gr=new Graph1();
        gr.display1(tim);
        gr.display3();
        gr.display2();
        gr.display4();
    } 
}
