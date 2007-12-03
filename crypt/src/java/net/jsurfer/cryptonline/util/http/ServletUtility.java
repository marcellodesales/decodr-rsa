/**
 * ServletUtility.java
 *
 * @author Marcello Junior
 */
package net.jsurfer.cryptonline.util.http;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class ServletUtility{
	
	/** http://www.biblioteca.ufal.br */
	public static String COMPLETE_DNS = "http://www.biblioteca.ufal.br";
	public static String CONTEXT_PATH = "http://localhost:8080";
	
	/*public static final String SUCESS_GENERIC_PAGE = CONTEXT_PATH+"status/sucessGeneric.jsp";
	public static final String ERROR_GENERIC_PAGE = CONTEXT_PATH+"status/errorGeneric.jsp";
	public static final String ERROR_FATAL_GENERIC_PAGE = CONTEXT_PATH+"status/errorFatal.jsp";
	
	public static final String INVITATION_CONFIRM = "http://localhost:8080"+CONTEXT_PATH+"servlet/br.ufal.graw.web.user.ConfirmInvitation";
	public static final String INVITATION_GUEST = COMPLETE_DNS+CONTEXT_PATH+"signIn/externalUser.jsp";
	 */
	
	/** CONTEXT_PATH + /bibliweb/ */
	public static final String MAIN = CONTEXT_PATH+"/bibliweb/";
	/** MAIN + SERVLET = /MAIN/servlet/ */
	public static final String SERVLET_DIR = MAIN+"servlet/";
	/** MAIN + /admin/ */
	public static final String ADMIN_PAGE = MAIN+"admin/";
	/** MAIN + /admin/newusers/ */
	public static final String ADMIN_CREATE_USER_PAGE = ADMIN_PAGE+"newusers/";
	/** MAIN + /admin/newexemplars/ */
	public static final String ADMIN_CREATE_EXEMPLAR_PAGE = ADMIN_PAGE+"newexemplars/";
	/** MAIN + /admin/newexemplarcopies/ */
	public static final String ADMIN_CREATE_EXEMPLARCOPY_PAGE = ADMIN_PAGE+"newexemplarcopies/";
	/** MAIN + /admin/users/user.jsp + userID */
	public static final String ADMIN_PAGE_USERINFOR = MAIN+"admin/users/user.jsp";
	/** MAIN + /admin/users/user.jsp + userID */
	public static final String ADMIN_PAGE_EXEMPLARINFOR = MAIN+"admin/exemplars/exemplar.jsp";
	/** MAIN + /admin/exemplarcopies/exemplarcopy.jsp + exemplarCopyID */
	public static final String ADMIN_PAGE_EXEMPLARCOPYINFOR = MAIN+"admin/exemplarcopies/exemplarcopy.jsp";
	
	/** The main User page url. \n user/index.jsp*/
	public static final String USER_MAIN_PAGE  = CONTEXT_PATH+"user/";

	public static final long DISK_SPACE_FOR_EACH_PHOTO = 100*1024; //15MB
	
	public static void sendRedirect(HttpServletResponse response, String page, String message){
		try{
			page = ServletUtility.removeParameter("message",page);
			String caracter = page.indexOf("?")==-1 ? "?" : "&";
			if (message != null){
				message = URLEncoder.encode(message,"UTF-8");
				page += caracter+"message="+message;
			}
			response.sendRedirect(page);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void sendRedirect(HttpServletResponse response, String page, String message, String from){
		try{
			page = ServletUtility.removeParameter("message",page);
			String caracter = page.indexOf("?")==-1 ? "?" : "&";
			
			if (message != null){
				message = URLEncoder.encode(message,"UTF-8");
				page += caracter+"message="+message;
			}
			if (from != null){
				from = URLEncoder.encode(from,"UTF-8");
				page = ServletUtility.removeParameter("from",page);
				page += "&from="+from;
			}
			response.sendRedirect(page);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	/** Remove da url os parametros que tenham o nome de parameterName */
	private static String removeParameter(String parameterName, String url){
		int start = url.indexOf(parameterName);
		int end ;
		
		while (start!=-1) {
			if (start==-1){ // A url nao contem o parametro
				return url;
			}
		
			/* Procurar proximo separador */
			end = url.indexOf("&",start);
			if (end==-1) { //Nao existem mais parametros
				end = url.length()-1;
			}else{ //Existem mais parametros
				start++; //Necess�rio para pegar a ?
			}
			url = url.substring(0,start-1)+url.substring(end+1,url.length());
			start = url.indexOf(parameterName);
		}
		return url;
	}

	public static void sendRedirect(HttpServletResponse response, String page){
		try{
			response.sendRedirect(page);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void main(String args[]){
		try{
			System.out.println(ServletUtility.removeParameter("message","http://localhost?message=\"oi pessoal\""));
		}catch(Exception ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
}

