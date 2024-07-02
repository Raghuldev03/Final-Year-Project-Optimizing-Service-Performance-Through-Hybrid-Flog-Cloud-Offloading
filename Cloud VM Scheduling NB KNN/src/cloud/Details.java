/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloud;
import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
/**
 *
 * @author admin
 */
public class Details 
{
    static ArrayList vm=new ArrayList(); //vm-id#cpu#mem#bw
    static ArrayList pm=new ArrayList(); //type#cpu#mem#bw
    
    static List<Vm> vmlist=new ArrayList<Vm>(); // VM
    static List<Host> pmList=new ArrayList<Host>(); // PM
    
    static int binPM[][];
    static double weight[][];
    
    static double mae=0;
    static double rae=0;
}
