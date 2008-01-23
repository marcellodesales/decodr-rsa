///*
// * Criado em 20/11/2003
// *
// * Marcello Junior Marcello Junior
// * javaman@moomia.com
// */
//package net.jsurfer.cryptonline.persistence;
//
//import net.jsurfer.cryptonline.server.user.User;
//
///**
// * @author Marcello Junior
// *
// * Projeto desenvolvido em J2EE
// */
//public class TestAdapter {
//
//	public static void saveObject(){
//		PersistenceLayer pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
//		User eu = new User();
//		eu.setFirstName("Dani");
//		eu.setLastName("houly");
//		eu.setEmail("dani@hotmail.com");
//		eu.setPassword("querido");
//		
//		try {
//			pl.saveObject(eu);
//			
//		} catch (Exception ple) {
//			ple.printStackTrace();			
//		}					
//	}
//	
//	public static void getUser(){
//		try {
//			Object o = null;
//			o = PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("4847db51f8f771fe00f8f77267a20001",User.class);
//			((User)o).printAll();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	
//	
//	public static void getUser2(){
//		try {
//			Object o = null;
//			o = PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("email","dani@hotmail.com",User.class);
//			((User)o).printAll();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void delete(){
//		try {
//			User u = (User)PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("email","dani@hotmail.com",User.class);
//			u.printAll();
//			
//			PersistenceBrokerFactory.getInstance().getPersistenceLayer().deleteObject(u);
//				
//		} catch (Exception e) {
//			
//		}
//	}
//
//	public static void main(String[] args) {
//		//saveObject();
//		//getUser();		
//		//getUser2();
//		delete();
//	}
//}
