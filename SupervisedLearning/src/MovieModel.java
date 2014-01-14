
import java.util.*;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import weka.classifiers.trees.J48;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffLoader.ArffReader;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.classifiers.Evaluation;
import weka.core.Utils;
import weka.filters.unsupervised.attribute.*;
import weka.filters.*;

public class MovieModel{
	
	public static void main(String args[]) throws Exception{
		
		BufferedReader loader = new BufferedReader(new FileReader("C://Users//somgupta.ORADEV//Downloads//Movie_Analysis.arff"));
		 ArffReader arff = new ArffReader(loader);
		 Instances data = arff.getData();
		 data.setClassIndex(data.numAttributes() - 1);
		 
		 double[] z = new double[data.numInstances()];
		 for(int i=0;i<data.numInstances();i++)
			 z[i] = 0.5;
		 
		 ReplaceMissingValues rmv = new ReplaceMissingValues();
		 rmv.setInputFormat(data);
		 data = Filter.useFilter(data, rmv);
		 for(int i=0;i<data.numAttributes()-1;i++)
			 for(int j=i+1;j<data.numAttributes();j++)
			 {
				 
				 //data.get(j).replaceMissingValues(z);
				 double[] b = data.attributeToDoubleArray(j);
				 //data.get(i).replaceMissingValues(z);
				 double[] a = data.attributeToDoubleArray(i);

				 
				 double corr = Utils.correlation(a, b, a.length);
				 if(corr>0.5)
					 System.out.println(i+" "+j+" "+corr);
			 }
		
	}
	
}