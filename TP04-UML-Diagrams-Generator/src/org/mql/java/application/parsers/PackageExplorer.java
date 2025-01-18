package org.mql.java.application.parsers;

import java.io.File;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;


//Introspection
public class PackageExplorer {
	    public PackageModel parse(File packageDir) {
	    	PackageModel javaPackage = new PackageModel(packageDir.getName());

	        for (File file : packageDir.listFiles()) {
	            if (file.getName().endsWith(".class")) {
	                String className = file.getName().replace(".class", "");
	                ClassModel classModel = new ClassParser().parse(packageDir.getName(), className);
	                javaPackage.addClass(classModel);
	            }
	        }
	        return javaPackage;
	    }
}
//	public PackageExplorer() {
//       scan("org.mql.java.application"); 	}
//	
//	public void scan(String packageName) {
//		String classPath = System.getProperty("java.class.path");
//		System.out.println(classPath);
//		
//		String packagePath = packageName.replace(".","\\");
//		System.out.println(packagePath);
//		
//		String path = classPath +"\\"+ packagePath;
//		File dir = new File(path);
//		File content[]= dir.listFiles();
//		for (int i=0 ; i< content.length; i++ ) {
//			String name = packageName + "." + content[i].getName().replace(".class", "");
//			System.out.println(" - "+ name);
//			}
//	}
//
//	public static void main(String[] args) {
//		   new PackageExplorer();
//	   }
//}
