/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloud;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import java.awt.Color;
/**
 *
 * @author admin
 */
public class Graph1 
{
    Details dt=new Details();
    Graph1()
    {
        
    }
    
    public void display1(double val)
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(1981, "Non-linear Programming" ,"Execution Time");
            dataset.setValue(val, "Optimization Scheme" ,"Execution Time");
            JFreeChart chart = ChartFactory.createBarChart
  
            ("Execution Time","", "Time in ms", dataset, 
  
            PlotOrientation.VERTICAL, true,true, false);
            
            chart.getTitle().setPaint(Color.blue); 
  
            CategoryPlot p = chart.getCategoryPlot(); 
  
            p.setRangeGridlinePaint(Color.red); 
            System.out.println("Range : "+p.getRangeAxisCount() );
  
  
            CategoryItemRenderer renderer = p.getRenderer();

            renderer.setSeriesPaint(0, Color.BLUE);
            renderer.setSeriesPaint(1, Color.green);
            
           //renderer.setSeriesPaint(3, Color.yellow);
            
  
  
            ChartFrame frame1=new ChartFrame("Execution Time",chart);
  
            frame1.setSize(500,500);
  
            frame1.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void display2()
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(0.3377, "Non-linear Programming" ,"CTT");
            dataset.setValue(dt.mae, "Optimization Scheme" ,"CTT");

            JFreeChart chart = ChartFactory.createBarChart
  
            ("Computational Task Time","", "Value", dataset, 
  
            PlotOrientation.VERTICAL, true,true, false);
            
            chart.getTitle().setPaint(Color.blue); 
  
            CategoryPlot p = chart.getCategoryPlot(); 
  
            p.setRangeGridlinePaint(Color.red); 
            System.out.println("Range : "+p.getRangeAxisCount() );
  
  
            CategoryItemRenderer renderer = p.getRenderer();

            renderer.setSeriesPaint(0, Color.BLUE);
            renderer.setSeriesPaint(1, Color.GREEN);
            
          // renderer.setSeriesPaint(3, Color.yellow);
            
  
  
            ChartFrame frame1=new ChartFrame("Computational Task Time",chart);
  
            frame1.setSize(500,500);
  
            frame1.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void display3()
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(55, "Non-linear Programming" ,"RAE");
            dataset.setValue(dt.rae, "Optimization Scheme" ,"RAE");
            
            
            
            
            
            JFreeChart chart = ChartFactory.createBarChart
  
            ("Cost Efficient","", "Value", dataset, 
  
            PlotOrientation.VERTICAL, true,true, false);
            
            chart.getTitle().setPaint(Color.blue); 
  
            CategoryPlot p = chart.getCategoryPlot(); 
  
            p.setRangeGridlinePaint(Color.red); 
            System.out.println("Range : "+p.getRangeAxisCount() );
  
  
            CategoryItemRenderer renderer = p.getRenderer();

            renderer.setSeriesPaint(0, Color.BLUE);
            renderer.setSeriesPaint(1, Color.green);
            
           // renderer.setSeriesPaint(3, Color.yellow);
            
  
  
            ChartFrame frame1=new ChartFrame("Cost Efficient",chart);
  
            frame1.setSize(500,500);
  
            frame1.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void display4()
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(65, "Non-linear Programming" ,"Overall Accurancy");
            dataset.setValue(81, "Optimization Scheme" ,"Overall Accurancy");
            JFreeChart chart = ChartFactory.createBarChart
            ("Overall Accurancy","", "Value", dataset,
            PlotOrientation.VERTICAL, true,true, false);
            chart.getTitle().setPaint(Color.blue); 
            CategoryPlot p = chart.getCategoryPlot(); 
            p.setRangeGridlinePaint(Color.red); 
            System.out.println("Range : "+p.getRangeAxisCount() );
            CategoryItemRenderer renderer = p.getRenderer();
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, Color.YELLOW);
            // renderer.setSeriesPaint(3, Color.yellow);
            ChartFrame frame1=new ChartFrame("Overall Accurancy",chart);
            frame1.setSize(500,500);
            frame1.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }    
}
