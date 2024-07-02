/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.core.converters.CSVLoader;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.SparseInstance;

//import weka.classifiers.functions.SMOreg;
import weka.classifiers.bayes.NaiveBayes;//nb
import weka.classifiers.lazy.IBk;//knn
/**
 *
 * @author admin 
 */
public class Optimization
{
    Details dt=new Details();
    Optimization()
    {
        
    }
    
    public void NB()
    {
        try
        {
            
            double e1=0;
            for(int i=0;i<dt.pm.size();i++)
            {
                String g1[]=dt.pm.get(i).toString().split("#");
                e1=e1+Double.parseDouble(g1[1]);
            }
            e1=e1/(double)dt.pm.size();
            
            System.out.println(e1);
            String newdt="a1,a2,a3,c1\n";
            
            for(int i=0;i<dt.pm.size();i++)
            {
                String g1[]=dt.pm.get(i).toString().split("#");
                double c1=Double.parseDouble(g1[1]);
                if(c1<=e1)
                    //newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"1\n";    // for svm
                    newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"Low\n";
                else
                   //newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"2\n";     // for svm
                    newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"High\n";
            }
                        
            //System.out.println(newdt);
            
            File fe1=new File("trsvm1.csv");
            FileOutputStream fos=new FileOutputStream(fe1);
            fos.write(newdt.getBytes());
            fos.close();
            
            CSVLoader csv1=new CSVLoader();
            csv1.setSource(new File("trsvm1.csv"));
            Instances datas=csv1.getDataSet();
			
            int cIdx=datas.numAttributes()-1;
            datas.setClassIndex(cIdx);                 
            
            
            NaiveBayes nb=new NaiveBayes();
            
            nb.buildClassifier(datas);
            
             Evaluation ev=new Evaluation(datas);
            ev.crossValidateModel(nb, datas, 10, new Random(1));
            
            System.out.println("=================== VM Utilization Navie Bayes================");
            
            for(int i=0;i<dt.vm.size();i++)
            {
                String g1[]=dt.vm.get(i).toString().split("#");
                Instance ins1=new SparseInstance(3);
                ins1.setValue(0, Integer.parseInt(g1[1]));
                ins1.setValue(1, Integer.parseInt(g1[2]));
                ins1.setValue(2, Integer.parseInt(g1[3]));
                ins1.setDataset(datas);
                    
                double r1=nb.classifyInstance(ins1);                
               // System.out.println("---- "+r1);
                if(r1==1.0)
                    System.out.println("VM = "+g1[0]+" has High Usage");
                else
                    System.out.println("VM = "+g1[0]+" has Low Usage");
            }
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void KNN()
    {
        try
        {
            
            double e1=0;
            for(int i=0;i<dt.pm.size();i++)
            {
                String g1[]=dt.pm.get(i).toString().split("#");
                e1=e1+Double.parseDouble(g1[1]);
            }
            e1=e1/(double)dt.pm.size();
            
            //System.out.println(e1);
            String newdt="a1,a2,a3,c1\n";
            
            for(int i=0;i<dt.pm.size();i++)
            {
                String g1[]=dt.pm.get(i).toString().split("#");
                double c1=Double.parseDouble(g1[1]);
                if(c1<=e1)                    
                    newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"Low\n";
                else                   
                    newdt=newdt+g1[1]+","+g1[2]+","+g1[3]+","+"High\n";
            }
                        
            
            
            File fe1=new File("trsvm2.csv");
            FileOutputStream fos=new FileOutputStream(fe1);
            fos.write(newdt.getBytes());
            fos.close();
            
            CSVLoader csv1=new CSVLoader();
            csv1.setSource(new File("trsvm2.csv"));
            Instances datas=csv1.getDataSet();
			
            int cIdx=datas.numAttributes()-1;
            datas.setClassIndex(cIdx);                 
            
            
            IBk knn=new IBk();
            
            knn.buildClassifier(datas);
            
             Evaluation ev=new Evaluation(datas);
            ev.crossValidateModel(knn, datas, 10, new Random(1));
            
            System.out.println("=================== VM Utilization KNN================");
            
            for(int i=0;i<dt.vm.size();i++)
            {
                String g1[]=dt.vm.get(i).toString().split("#");
                Instance ins1=new SparseInstance(3);
                ins1.setValue(0, Integer.parseInt(g1[1]));
                ins1.setValue(1, Integer.parseInt(g1[2]));
                ins1.setValue(2, Integer.parseInt(g1[3]));
                ins1.setDataset(datas);
                    
                double r1=knn.classifyInstance(ins1);                
               
                if(r1==1.0)
                    System.out.println("VM = "+g1[0]+" has High Usage");
                else
                    System.out.println("VM = "+g1[0]+" has Low Usage");
            }
            dt.mae=ev.meanAbsoluteError();
            dt.rae=ev.relativeAbsoluteError();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
