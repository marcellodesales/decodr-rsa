/**
 * Rsa.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.rsa;

import net.jsurfer.cryptonline.server.math.Algebra;

import java.util.Vector;
import java.util.Iterator;
import java.text.DecimalFormat;

public class Rsa{
	
	public static final String DELIMITER = "-";
	public static final String SETA_LOG = "-> ";
	
	private double p;
	private double q;
	private double e;
	private double n;
	private double d;
	private double fi;
	
	private double t,u;
	
	private Vector log;
	private DecimalFormat decimalFormat = new DecimalFormat("0");
	
	public Rsa(){
		this.log = new Vector();
		this.setNewPrimes();
		this.setNewPublicKeys();
		this.setNewPrivateKeys();
	}
	
	public Rsa(double p, double q){
		this.log = new Vector();		
		this.p = p;
		this.q = q;
		this.setNewPublicKeys();
		this.setNewPrivateKeys();
	}
	
	public Rsa(double n, double e, double d){		
		this.n = n;
		this.e = e;
		this.d = d;
	}
	
	public double getPublicKey_N(){
		return this.n;
	}

	public double getPublicKey_E(){
		return this.e;
	}
	
	public double getPrivateKey_D(){
		return this.d;
	}
	
	public void setPublicKey_N(double n){
		this.n = n;
	}
	
	public void setPublicKey_E(double e){
		this.e = e;
	}
	
	public void setPrivateKey_D(double d){
		this.d = d;
	}
	
	private void setNewPrimes(){
		Algebra algebra = Algebra.getInstance();
		String hasE = "E";
		
		while (hasE.indexOf("E") != -1){
			this.p = algebra.getNewPrime(33,15000);
			this.q = algebra.getNewPrime(23,15000);
			hasE = String.valueOf(p*q);
		}
		
		this.log.add(Rsa.SETA_LOG + "Configurando primos aleat�rios");
		this.log.add("        P = "+this.decimalFormat.format(this.p));
		this.log.add("        Q = "+this.decimalFormat.format(this.q));
	}
	
	private void setNewPublicKeys(){
		this.n = this.p * this.q;
		this.log.add(Rsa.SETA_LOG + "Calculando Chaves P�blicas");
		this.log.add("          N = P * Q; N = "+this.decimalFormat.format(this.n));
		
		this.e = this.getPublicKeyE();
		this.log.add("          E = "+this.decimalFormat.format(this.e));
		this.log.add("");
		
		this.log.add("       Chave P�blica (N,E) = ("+this.decimalFormat.format(this.n)+" , "+this.decimalFormat.format(this.e)+")");
		this.log.add("");
	}
	
	private void setNewPrivateKeys(){
		this.log.add(Rsa.SETA_LOG+"Calculando Chaves Privadas");
		this.d = this.getDinverseE();
		this.log.add("");
		this.log.add("      Chave Privada (N,D) = ("+this.decimalFormat.format(this.n)+","+this.decimalFormat.format(this.d)+");");
	}
	
	private double getPublicKeyE(){
		Algebra algebra = Algebra.getInstance();
		//FI = (P-1) * (Q-1);
		this.fi = algebra.getEuler(this.p,this.q);
		
		this.log.add(Rsa.SETA_LOG + "FI = (P-1) * (Q-1); FI = "+this.decimalFormat.format(this.fi));
		this.log.add("");
		this.log.add(Rsa.SETA_LOG + "Calculando (E)");
		
		double aux = 2;
		double mdc = algebra.getMDC(aux,this.fi);
		this.log.add("           Enquanto mdc(n >= 2 , "+this.decimalFormat.format(fi)+") != 1");
		while (mdc != 1) {
			
			this.log.add("           mdc("+this.decimalFormat.format(aux)+" , "+this.decimalFormat.format(fi)+") = "+this.decimalFormat.format(mdc));
			
			aux++;
			mdc = algebra.getMDC(aux,this.fi);
		}
		this.log.add("           mdc("+this.decimalFormat.format(aux)+" , "+this.decimalFormat.format(fi)+") = "+this.decimalFormat.format(mdc)+" Correto!");
		
		return aux;
	}
	
	public double getDinverseE(){
		Algebra algebra = Algebra.getInstance();
		double u1 = 1; double u2 = 0; double u3 = this.fi;
		double v1 = 0; double v2 = 1; double v3 = this.e;
		double t1,t2,t3,vv,qq;
		
		this.log.add("         Inicializando (p1,p2,p3) = (1, 0 , FI(n))");
		this.log.add("         Inicializando (q1,q2,q3) = (0, 1 ,  E  ))");
		this.log.add("         Enquanto q3 != 0");
		this.log.add("             quoc = p3 / q3");
		this.log.add("             (t1,t2,t3) = (p1,p2,p3) - quoc * (q1,q2,q3)");
		this.log.add("             Depois d� valores:");
		this.log.add("             (p1,p2,p3) = (q1,q2,q3)");
		this.log.add("             (q1,q2,q3) = (t1,t2,t3)");
		
		while (v3 > 0){
			this.log.add("");
			this.log.add("           ("+this.decimalFormat.format(v3)+" <> 0) , ent�o:");
			qq  = algebra.getQuotient(u3,v3);
			
			this.log.add("             quoc = "+this.decimalFormat.format(u3)+" / "+this.decimalFormat.format(v3)+" = "+this.decimalFormat.format(qq));
			
			t1 = u1 - qq * v1;
			t2 = u2 - qq * v2;
			t3 = u3 - qq * v3;
			
			u1 = v1; u2 = v2; u3 = v3;
			v1 = t1; v2 = t2; v3 = t3;
						
			this.log.add("             (t1,t2,t3) = ("+this.decimalFormat.format(u1)+","+this.decimalFormat.format(u2)+","+this.decimalFormat.format(u3)+") - "+this.decimalFormat.format(qq)+" * ("+this.decimalFormat.format(v1)+","+this.decimalFormat.format(v2)+","+this.decimalFormat.format(v3)+") = ("+this.decimalFormat.format(t1)+","+this.decimalFormat.format(t2)+","+this.decimalFormat.format(t3)+")");
			this.log.add("             (p1,p2,p3) = ("+this.decimalFormat.format(v1)+","+this.decimalFormat.format(v2)+","+this.decimalFormat.format(v3)+")");
			this.log.add("             (q1,q2,q3) = ("+this.decimalFormat.format(t1)+","+this.decimalFormat.format(t2)+","+this.decimalFormat.format(t3)+")");
		}
		this.log.add("");
		this.log.add("         q3 � zero(0). Verifica agora o valor de p2. Caso seja negativo, inverte somando-se com FI. (representar o n�mero negativo de z(n) por um positivo.)");
        this.log.add("");
        this.log.add("         u2 = "+this.decimalFormat.format(u2)+";");
		
		vv = u2;
		double inverse;
		if (vv < 0){
			inverse = vv + this.fi;
			this.log.add("          Como u2 � negativo, faz-se:");
			this.log.add("          D = u2 + FI; D = "+this.decimalFormat.format(u2)+" + "+this.decimalFormat.format(this.fi)+" = "+this.decimalFormat.format(inverse));
		} else {
          	inverse = vv;
			this.log.add("         D = u2; D = "+this.decimalFormat.format(u2));
		}
		return inverse;
	}
	
	public double getExtendedMDC(double p, double q){
		Algebra algebra = Algebra.getInstance();
		double mdc;
   		double a = p;
   		double b = q;

  		if (b == 0){
      		mdc = a;
			this.t = 1;
			this.u = 0;
		}
		
		double x2 = 1;  double x1 = 0;
  		double y2 = 0;  double y1 = 1;
		double quoc,r;
  		while (b > 0){
    		quoc = algebra.getQuotient(a,b);
    		r = a - b * quoc;
			this.t = x2 - quoc * x1;
    		this.u = y2 - quoc * y1;
			x2 = x1;
    		x1 = this.t;
    		y2 = y1;
    		y1 = this.u;

			a = b;
    		b = r;
		}
  		mdc = a;
  		this.t = x2;
  		this.u = y2;
		return mdc;
	}
	
	public void printAll(){
		System.out.println("#### All RSA Information ####");
		System.out.println();
		System.out.println("Primo P: "+this.p);
		System.out.println("Primo Q: "+this.q);
		System.out.println();
		System.out.println("Chaves Publica (N, E) = ("+this.n+", "+this.e+")");
		System.out.println("Chaves Privada (N, D) = ("+this.n+", "+this.d+")");
		System.out.println();
		//System.out.println("MDC extendido =>  a * (t) + b * (u) = mdc ");
		//System.out.println(this.p+" * ("+this.t+") + "+this.q+" * ("+this.u+") = "+this.getExtendedMDC(this.p,this.q));
	}
	
	public String getChriptedMessage(String originalMessage){
		RsaSender sender = new RsaSender(originalMessage,this);
		return sender.getChriptedMessage();
	}
	
	public void printLog(){
		Iterator it = this.log.iterator();
		while (it.hasNext()){
			String logLine = (String)it.next();
			System.out.println(logLine);
		}
	}
	
	public Iterator getLog(){
		return this.log.iterator();
	}
	
//	public String getDechriptedMessage(String chriptedMessage){
//		RsaReceiver receiver = new RsaReceiver(chriptedMessage,this);
//		return receiver.getOriginalMessage();
//	}
		
	public static void main(String[] args){
		Rsa rsa = new Rsa(244961,5,97589);
		//rsa.printLog();
		//rsa.printAll();
		
		String origem = "Marcello Alves de Sales Junior";
		
		RsaSender sender = new RsaSender(origem,rsa);
		//sender.printLog();
		//System.out.println("Ascii: "+sender.getAsciiMessage());
		//System.out.println("Cripted: "+sender.getChriptedMessage());
		
		//RsaReceiver receiver = new RsaReceiver(sender.getChriptedMessage(),rsa);
		RsaReceiver receiver = new RsaReceiver("239709-58994-243-212757-139527-126332-68732-213675-239736-1-101278-164725-218124-1-11883-1-75143-174841-227106-32-185185-63417-24492-227229-164270-153488-15507-100100-147656-24546-164725-32768-24546-32-133012-15507-219038-182228-120163-232402-190211-169819-1-1-36128-239736-18496-26489-13404-35918-3391-239736-161051",rsa);
		receiver.printLog();
		//RsaReceiver receiver = new RsaReceiver("5252-1754-5014-1746-2515-5559-6727-515-224-6768-2515-5014-3420-6768-5400",rsa);
		//System.out.println("Original: |"+receiver.getOriginalMessage()+"|");
	}
}


