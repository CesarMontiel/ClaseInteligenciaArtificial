package NavieBayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NaiveBayes {
	private HashMap<String, String[]> variables;
	List<HashMap<String, HashMap<String,Double>>> probabilidades;
	List<Registro> bd_training;
	List<Registro> bd_test;
	int tamaño_bd_training = 100;
	int tamaño_bd_test = 1;
	
	public NaiveBayes() {
		variables = new HashMap<>();
		probabilidades = new ArrayList<>();
		variables.put("x1", new String[]{"A","B","C"});
		variables.put("x2", new String[]{"X","Y","Z"});
		variables.put("x3", new String[]{"a","b","c"});
		variables.put("x4", new String[]{"1","2","3"});
		variables.put("y", new String[]{"Cesar","Jaime","Montiel"});
	}
	
	public void run() {
		bd_training = generarBD(tamaño_bd_training);
		bd_test = generarBD(tamaño_bd_test);
		imprimirBD(bd_training," de entrenamiento");
		entrenar();
		imprimirBD(bd_test," de prueba");
		predecir();
	}
	private List<Registro> generarBD(int tamaño) {
		List<Registro> bd = new ArrayList<>();
		for (int i = 0; i < tamaño; i++)
			bd.add(
				new Registro(
					variables.get("x1")[(int)(Math.random()*variables.get("x1").length)],
					variables.get("x2")[(int)(Math.random()*variables.get("x2").length)],
					variables.get("x3")[(int)(Math.random()*variables.get("x3").length)],
					variables.get("x4")[(int)(Math.random()*variables.get("x4").length)],
					variables.get("y")[(int)(Math.random()*variables.get("y").length)]
				)
			);
		return bd;
	}
	private void imprimirBD(List<Registro> db, String texto) {
		System.out.println("\nBase de Datos "+texto);
		for(Registro r : db)
			System.out.println(r.toString());
	}
	private void entrenar() {
		HashMap<String, HashMap<String,Double>> pX1,pX2,pX3,pX4;
		pX1 = probabilidadRespectoY("x1");
		pX2 = probabilidadRespectoY("x2");
		pX3 = probabilidadRespectoY("x3");
		pX4 = probabilidadRespectoY("x4");
		probabilidades.add(pX1);
		probabilidades.add(pX2);
		probabilidades.add(pX3);
		probabilidades.add(pX4);
	}
	private void predecir(){
		for(Registro r : bd_test) {
			predecirRegistro(r);
		}
	}
	
	private void predecirRegistro(Registro r) {
		HashMap<String, Double> prediccion = new HashMap<>();
		HashMap<String, Double> acumulado = new HashMap<>();
		String valorPredicho = "";
		double total = 0, aleatorio = Math.random(), probAcumulada = 0;
		for(String y : variables.get("y")) {
			double probabilidadTotal = probabilidades.get(0).get(r.getX1()).get(y)
					* probabilidades.get(1).get(r.getX2()).get(y) 
					* probabilidades.get(2).get(r.getX3()).get(y) 
					* probabilidades.get(3).get(r.getX4()).get(y);
			prediccion.put(y, probabilidadTotal);
			total += probabilidadTotal;
		}
		
		for(String y : variables.get("y")) {
			prediccion.put(y, prediccion.get(y)/total);
			probAcumulada +=prediccion.get(y);
			if(valorPredicho.equals("") && aleatorio < probAcumulada)
				valorPredicho = y;
			acumulado.put(y,probAcumulada);
		}

		System.out.println("\n"+r);
		System.out.println(prediccion);
		System.out.println(acumulado);
		System.out.println("Aleatorio:"+aleatorio);
		System.out.println("El valor predecido es "+valorPredicho);
	}
	
	private HashMap<String, HashMap<String,Double>> probabilidadRespectoY(String variable){
		System.out.println("\nInformación de la Variable " + variable);
		HashMap<String, Integer> contadorVariable = new HashMap<>();
		HashMap<String, HashMap<String,Integer>> contadorRespectoY = new HashMap<>();
		HashMap<String, HashMap<String,Double>> probabilidadRespectoY = new HashMap<>();
		
		for(String valor : variables.get(variable)) {
			contadorVariable.put(valor, 0);
			HashMap<String, Integer> auxContador = new HashMap<>();
			HashMap<String, Double> auxProbabilidad = new HashMap<>();
			for(String y : variables.get("y")) {
				auxContador.put(y, 0);
				auxProbabilidad.put(y,0d);
			}
			contadorRespectoY.put(valor,auxContador);
			probabilidadRespectoY.put(valor, auxProbabilidad);
		}
		
		for(Registro r: bd_training) {
			contadorVariable.put(r.getVariable(variable),contadorVariable.get(r.getVariable(variable))+1);
			contadorRespectoY.get(r.getVariable(variable)).put(	r.getVariable("y"), contadorRespectoY.get(r.getVariable(variable)).get(r.getY())+1 );
		}
		for(String valor:variables.get(variable))
			for(String y : variables.get("y"))
				probabilidadRespectoY.get(valor).put(y,	contadorVariable.get(valor)!=0?(double)contadorRespectoY.get(valor).get(y)/contadorVariable.get(valor):0d);
		
		System.out.println(contadorVariable);
		System.out.println(contadorRespectoY);
		System.out.println(probabilidadRespectoY);
		return probabilidadRespectoY;
	}
}
