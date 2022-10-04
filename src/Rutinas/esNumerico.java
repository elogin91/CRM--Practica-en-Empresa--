package Rutinas;


public class esNumerico {
	private static boolean resul;
	
	public static boolean VerificarNumerico (String num) {
		resul = false;
		
		try {
			if (num != null) {
				Integer.parseInt(num);
				resul = true;
			}
		} catch (NumberFormatException e) {
			resul = false;
		}
		
		return resul;
	}
}
