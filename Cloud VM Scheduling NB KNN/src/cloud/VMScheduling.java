 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloud;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;

import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;

import java.util.List;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
/**
 *
 * @author admin
 */
public class VMScheduling 
{
    Details dt=new Details();
    
    VMScheduling()
    {
        
    }
    
    public void readPM()
    {
        try
        {
            File fe=new File("PM1.txt");
            FileInputStream fis=new FileInputStream(fe);
            byte bt[]=new byte[fis.available()];
            fis.read(bt);
            fis.close();
            
            String g1=new String(bt);
            System.out.println("PM Details");
            System.out.println("============================");
            System.out.println(g1);
            
            String g2[]=g1.split("\n");
            for(int i=1;i<g2.length;i++)
                dt.pm.add(g2[i].trim().replace("\t", "#"));            
            
            
            Log.printLine("Starting CloudSim");
            CloudSim cs=new CloudSim();
            Calendar calendar = Calendar.getInstance();
            cs.init(1, calendar,false);
                
            String name="DC1";
            
            for(int i=0;i<dt.pm.size();i++)
            {
                String a1[]=dt.pm.get(i).toString().split("#");
                
                int id=i+1;
                String ins=a1[0];
                int cpu=Integer.parseInt(a1[1]);
                int ram1=Integer.parseInt(a1[2]);
                int bw2=Integer.parseInt(a1[3]);
                int storage=1000;
                List<Pe> peList1 = new ArrayList<Pe>();
                
                int mips1 = 1000000;
                peList1.add(new Pe(0, new PeProvisionerSimple(mips1))); 
                Host ht=new Host(id, new RamProvisionerSimple(ram1),new BwProvisionerSimple(bw2), storage, peList1,new VmSchedulerTimeShared(peList1));
                
                dt.pmList.add(ht); 
                
                System.out.println("PM-"+(i+1)+" is created... ");
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void readVM()
    {
        try
        {
            File fe=new File("VM1.txt");
            FileInputStream fis=new FileInputStream(fe);
            byte bt[]=new byte[fis.available()];
            fis.read(bt);
            fis.close();
            
            String g1=new String(bt);
            System.out.println("VM Details");
            System.out.println("============================");
            System.out.println(g1);
            
            String g2[]=g1.split("\n");
            for(int i=1;i<g2.length;i++)
                dt.vm.add(g2[i].trim().replace("\t", "#"));  
            
            
            for(int i=0;i<dt.vm.size();i++)
            {
                String v1[]=dt.vm.get(i).toString().split("#");
                String id1=v1[0];
                int vmid = i+1;
                int cid=i+1;
                int mips = 250;
                long size = 10000; //image size (MB)
                
                int pesNumber = Integer.parseInt(v1[1]); //number of cpus
                int ram = Integer.parseInt(v1[2]); // mem
                long bw = Long.parseLong(v1[3]);
               
                String vmm = "Xen"; //VMM name
                        
                Vm vm1 = new Vm(vmid,cid, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
                System.out.println("VM-"+id1+" is Created...");
                        
                //add the VM to the vmList
                dt.vmlist.add(vm1);
            } //for
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void selectPM()
    {
        try
        {
            dt.binPM=new int[dt.pm.size()][dt.vm.size()];
            for(int i=0;i<dt.pm.size();i++)
            {
                for(int j=0;j<dt.vm.size();j++)
                    dt.binPM[i][j]=0;
            }
            
            
            for(int i=0;i<dt.pm.size();i++)
            {
                Host ht=dt.pmList.get(i);
                int cpu1=ht.getNumberOfPes();
                int ram1=ht.getRam();
                long bw1=ht.getBw();
                
                PeProvisionerSimple CPUPro=new PeProvisionerSimple(cpu1);
                RamProvisionerSimple StePro=new RamProvisionerSimple(ram1);
                BwProvisionerSimple BwPro=new BwProvisionerSimple(bw1); 
                
                for(int j=0;j<dt.vm.size();j++)
                {
                    Vm vm=dt.vmlist.get(j);
                    int cpu2=vm.getNumberOfPes();
                    int ram2=vm.getRam();
                    int bw2=(int)vm.getBw();
                    
                   // boolean bo1=CPUPro.allocateMipsForVm(vm, cpu2);
                    //boolean bo2=StePro.allocateRamForVm(vm, ram2);
                    //boolean bo3=BwPro.allocateBwForVm(vm,bw2);
                    
                    boolean bo2=StePro.isSuitableForVm(vm, ram2);
                    boolean bo3=BwPro.isSuitableForVm(vm, bw2);
                    
                   // System.out.println(cpu1+" : "+cpu2+" : "+bo1);
                   // System.out.println(ram1+" : "+ram2+" : "+bo2);
                   // System.out.println(bw1+" : "+bw2+" : "+bo3);
                    
                    if(bo2 && bo3)
                    {
                        dt.binPM[i][j]=1;
                    }
                }
            }
            System.out.println("Binary Matrix for Selected PM for scheduling VM");
            for(int i=0;i<dt.pm.size();i++)
            {
                System.out.print((i+1)+" --> ");
                for(int j=0;j<dt.vm.size();j++)
                    System.out.print(dt.binPM[i][j]+" ");
                System.out.println();
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void computeWeight()
    {
        try
        {
            Optimization op=new Optimization();
            op.NB();
            
            Optimization op2=new Optimization();
            op2.KNN();
            
            
            double w1=  0.55;
            double w2=0.15;
            double w3= 0.15;
            
             dt.weight=new double[dt.pm.size()][dt.vm.size()];
            for(int i=0;i<dt.pm.size();i++)
            {
                for(int j=0;j<dt.vm.size();j++)
                    dt.weight[i][j]=0;
            }
            
            ArrayList sel1=new ArrayList();
            for(int j=0;j<dt.vm.size();j++)
            {
                ArrayList lt1=new ArrayList();
                for(int i=0;i<dt.pm.size();i++)    
                {
                    if(dt.binPM[i][j]==1)
                    {
                        String g1[]=dt.pm.get(i).toString().split("#");
                        
                        Host ht=dt.pmList.get(i);
                        int cpu1=Integer.parseInt(g1[1]);//ht.getNumberOfPes();
                        int ram1=ht.getRam();
                        long bw1=ht.getBw();
                        
                        Vm vm=dt.vmlist.get(j);
                        int cpu2=vm.getNumberOfPes();
                        int ram2=vm.getRam();
                        int bw2=(int)vm.getBw();
                        
                        double a1=cpu2-cpu1;
                        double a2=ram2-ram1;
                        double a3=bw2-bw1;
                        double e1=Math.sqrt((w1*(a1*a1))+(w2*(a2*a2))+(w3*(a3*a3)));
                        System.out.println(i+" : "+j+" = "+e1);
                        lt1.add(i+"#"+j+"#"+e1);
                    }
                }
                
                double ds1[][]=findPM(lt1);
                int ind1=(int)ds1[0][0];
                if(!sel1.contains(ind1))
                    sel1.add(ind1);
                else
                {
                    for(int k=1;k<ds1.length;k++)
                    {
                        int ind2=(int)ds1[k][0];
                        if(!sel1.contains(ind2))
                        {
                            sel1.add(ind2);
                            break;
                        }
                    }
                }
            }
            System.out.println("sel1 = "+sel1);
            
            for(int i=0;i<sel1.size();i++)
            {
                int ind1=Integer.parseInt(sel1.get(i).toString());
                String pm1=dt.pm.get(ind1).toString();
                String vm1=dt.vm.get(i).toString();
                System.out.println("VM = "+vm1+" is scheduled on PM = "+pm1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public double[][] findPM(ArrayList lt1)
    {
        double ds1[][]=new double[lt1.size()][3];
        try
        {
            
            for(int i=0;i<lt1.size();i++)
            {
                String g1[]=lt1.get(i).toString().split("#");
                ds1[i][0]=Double.parseDouble(g1[0]);
                ds1[i][1]=Double.parseDouble(g1[1]);
                ds1[i][2]=Double.parseDouble(g1[2]);                
            }
            
            for(int i=0;i<ds1.length;i++)
            {
                for(int j=0;j<ds1.length;j++)
                {
                    if(ds1[i][2]<ds1[j][2])
                    {
                        double t1=ds1[i][0];
                        ds1[i][0]=ds1[j][0];
                        ds1[j][0]=t1;
                        
                        double t2=ds1[i][1];
                        ds1[i][1]=ds1[j][1];
                        ds1[j][1]=t2;
                        
                        double t3=ds1[i][2];
                        ds1[i][2]=ds1[j][2];
                        ds1[j][2]=t3;
                    }
                }
            }
            
            //System.out.println("== "+ds1[0][0]+" : "+ds1[0][1]+" : "+ds1[0][2]);
           // if(ds1.length>1)
              //  System.out.println("== "+ds1[1][0]+" : "+ds1[1][1]+" : "+ds1[1][2]);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ds1;
    }
}
