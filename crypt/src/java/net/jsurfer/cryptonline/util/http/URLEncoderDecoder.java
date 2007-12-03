/*
 * Created on 30/11/2003
 * Cryptography on the Web
 */
package net.jsurfer.cryptonline.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
/**
 * @author Marcello Junior on 30/11/2003
 * Cryptography on the Web
 * Latin-1 or UTF-8
 */
public final class URLEncoderDecoder  {
	
	private URLEncoderDecoder(){}
	
	public static String encode(String message){
		try {
			return URLEncoder.encode(message,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String decode(String message){
		try {
			return URLDecoder.decode(message,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	public static void main(String[] args) {
		String latin = "Eu mere�o isso?";
		String encoded = URLEncoderDecoder.encode(latin);
		System.out.println(encoded); 
		System.out.println(URLEncoderDecoder.decode(encoded));
	}
}
