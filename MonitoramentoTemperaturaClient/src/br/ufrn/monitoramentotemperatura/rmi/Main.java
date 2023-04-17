package br.ufrn.monitoramentotemperatura.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.ufrn.monitoramentotemperatura.model.Operacao;
import br.ufrn.monitoramentotemperatura.model.Request;

public class Main {
public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		System.setProperty("java.security.policy", "file:./rmipolicy.policy");
	
		TempServerInterface natalServer = (TempServerInterface) Naming.lookup("rmi://127.0.0.1:1098/TemperaturaNatalServer");
		
		TempServerInterface londresServer = (TempServerInterface) 
				Naming.lookup("rmi://127.0.0.1:1099/TemperaturaLondresServer");
		
		TempServerInterface novaIorqueServer = (TempServerInterface) 
				Naming.lookup("rmi://127.0.0.1:1100/TemperaturaNovaIorqueServer");
		
		TempServerInterface osloServer = (TempServerInterface) 
				Naming.lookup("rmi://127.0.0.1:1101/TemperaturaOsloServer");
		
		TempServerInterface cairoServer = (TempServerInterface) 
				Naming.lookup("rmi://127.0.0.1:1102/TemperaturaCairoServer");

		TempClientInterface client = new TempClient();
		Scanner scanner = new Scanner(System.in);
		
		String opcoesCidades = "\n 1 - Natal \n 2 - Londres \n 3 - Nova Iorque \n 4 - Oslo \n 5 - Cairo";
		
		for(;;){
			System.out.println("Digite a cidade desejada:");
			System.out.println(opcoesCidades);
			int cidadeNum = scanner.nextInt();
			Request request = new Request();
			if(cidadeNum >= 1 && cidadeNum <= 5) {
				System.out.println("Digite a operação a ser realizada: ");
				System.out.println("\n 1 - Consultar temperatura atual \n 2 - Adicionar monitor de temperatura\n");
				
				boolean consultar = false;
				int operacaoNum = scanner.nextInt();
				int opcaoNum=0, x=0, y=0;
			    switch (operacaoNum){
					case 1:
					    consultar = true;
						break;
						
					case 2:						
						System.out.println("Digite a opção de monitoramento:");
						System.out.println("\n 1 - Menor \n 2 - Maior \n 3 - Igual \n 4 - Intervalo");
						opcaoNum = scanner.nextInt() - 1;
						x = scanner.nextInt();
						if(opcaoNum == 3) {
							y = scanner.nextInt();
						}
						break;
				 }
				
				 switch (cidadeNum) {
					case 1: //Natal
						if(consultar) {
							natalServer.consultarTemperatura(client);
						} else {
							natalServer.request(new Request(Operacao.values()[opcaoNum], x,y, client));
						}
						break;
						
					case 2:
						if(consultar) {
							londresServer.consultarTemperatura(client);
						} else {
							londresServer.request(new Request(Operacao.values()[opcaoNum], x,y, client));
						}
						break;
					case 3:
						if(consultar) {
							novaIorqueServer.consultarTemperatura(client);
						} else {
							novaIorqueServer.request(new Request(Operacao.values()[opcaoNum], x,y, client));
						}
						break;
						
					case 4:
						if(consultar) {
							osloServer.consultarTemperatura(client);
						} else {
							osloServer.request(new Request(Operacao.values()[opcaoNum], x,y, client));
						}
						break;
						
					case 5:
						if(consultar) {
							cairoServer.consultarTemperatura(client);
						} else {
							cairoServer.request(new Request(Operacao.values()[opcaoNum], x,y, client));
						}
						break;
						
				  }
				
			}
		}
	}
}

