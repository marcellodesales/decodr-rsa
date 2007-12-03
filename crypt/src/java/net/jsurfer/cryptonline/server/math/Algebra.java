/**
 * Algebra.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.math;

public class Algebra{
	
	private static Algebra singleton = null;
	
	private Algebra(){
	}
	
	public static Algebra getInstance(){
		if (Algebra.singleton == null){
			Algebra.singleton = new Algebra();
		}
		return Algebra.singleton;
	}
	
	/**
	 * Method getQuotient. Gets the quociente of 2 numbers
	 *
	 * @param    a                   a  double
	 * @param    b                   a  double
	 *
	 * @return   a double
	 *
	 */
	public double getQuotient(double a, double b){
		double q = 1;
		while (b * q <= a)
			q++;
		q--;
		return q;
	}
	
	/**
	 * Method getReminder. Gets the reminder of the division of 2 numbers
	 *
	 * @param    a                   a  double
	 * @param    b                   a  double
	 *
	 * @return   a double
	 *
	 */
	public double getReminder(double a, double b){
        double q = 1;
        while (b * q <= a)
        	q++;
        q--;
		return (a - b * q);
	}
	
	/**
	 * Method isPrime. Know if a number is a prime.
	 *
	 * @param    x                   a  double
	 *
	 * @return   a boolean
	 *
	 */
	public boolean isPrime(double x){
    	double v = x;
     	double i = 2;
		Algebra algebra = Algebra.getInstance();
		while ((algebra.getReminder(v,i) != 0) && ((i*i) <= v))
       		i++;
		return (algebra.getReminder(v,i) != 0);
	}
	
	/**
	 * Method getMDC. The Commom Maximum Divisor
	 *
	 * @param    p                   a  double
	 * @param    q                   a  double
	 *
	 * @return   a double
	 *
	 */
	public double getMDC(double p, double q){
		Algebra algebra = Algebra.getInstance();
		double r = algebra.getReminder(p,q);
		while (r > 0){
			p = q;
			q = r;
			r = algebra.getReminder(p,q);
		}
		return q;
	}
	
	public double getEuler(double p, double q){
		return (p - 1) * (q - 1);
	}
	
	public double getPowerModuleN(double a, double e, double n){
		Algebra algebra = Algebra.getInstance();
		double pp = 1;
		while (e != 0){
			if ((e % 2) != 0)
				pp  = a * pp % n;
			e = algebra.getQuotient(e,2);
			a = a * a % n;
		}
		return pp;
	}
	
	public int getRandom(int range){
		return (int)(Math.random() * --range + 1);
	}

	/**
	 * Method getNewPrime. Retorna um novo n�mero primo de acordo com uma entrada
	 * e uma faixa especificada.
	 *
	 * @param    input               N�mero aleat�rio
	 * @param    range               Faixa. Usavel 2000.
	 *
	 * @return   a double O n�mero primo desejado.
	 *
	 */
	public double getNewPrime(double input, int range){
		Algebra algebra = Algebra.getInstance();
		double rand, number;
		do {
			number = algebra.getRandom(algebra.getRandom(range)) + input;
		} while (!algebra.isPrime(number));
		return number;
	}
}

