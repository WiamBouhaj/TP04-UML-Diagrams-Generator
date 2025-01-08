package org.mql.java.application.parsers;

import java.io.File;


//Introspection
public class PackageExplorer {
	public PackageExplorer() {
       scan("org.mql.java.application"); 	}
	
	public void scan(String packageName) {
		String classPath = System.getProperty("java.class.path");
		System.out.println(classPath);
		
		String packagePath = packageName.replace(".","\\");
		System.out.println(packagePath);
		
		String path = classPath +"\\"+ packagePath;
		File dir = new File(path);
		File content[]= dir.listFiles();
		for (int i=0 ; i< content.length; i++ ) {
			String name = packageName + "." + content[i].getName().replace(".class", "");
			System.out.println(" - "+ name);
			}
	}

	public static void main(String[] args) {
		   new PackageExplorer();
	   }
}
