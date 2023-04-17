package br.ufrn.monitoramentotemperatura.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
public static void main(String[] args) throws RemoteException, MalformedURLException {
		System.setProperty("java.security.policy", "file:./rmipolicy.policy");
		System.setProperty("java.rmi.server.hostname","127.0.0.1");
		
		TempServerInterface natalServer = new NatalServer();
		TempServerInterface londresServer = new LondresServer();
		TempServerInterface novaIorqueServer = new NovaIorqueServer();
		TempServerInterface osloServer = new OsloServer();
		TempServerInterface cairoServer = new CairoServer();
		

		LocateRegistry.createRegistry(1098);
		Naming.rebind("rmi://127.0.0.1:1098/TemperaturaNatalServer", natalServer);
		LocateRegistry.createRegistry(1099);
		Naming.rebind("rmi://127.0.0.1:1099/TemperaturaLondresServer", londresServer);
		LocateRegistry.createRegistry(1100);
		Naming.rebind("rmi://127.0.0.1:1100/TemperaturaNovaIorqueServer", novaIorqueServer);
		LocateRegistry.createRegistry(1101);
		Naming.rebind("rmi://127.0.0.1:1101/TemperaturaOsloServer", osloServer);
		LocateRegistry.createRegistry(1102);
		Naming.rebind("rmi://127.0.0.1:1102/TemperaturaCairoServer", cairoServer);
		
		
		System.out.println("Server de Monitoramento de Temperatura começou.");
		
	}
}
