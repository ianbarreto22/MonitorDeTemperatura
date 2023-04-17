package br.ufrn.monitoramentotemperatura.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.ufrn.monitoramentotemperatura.model.Operacao;
import br.ufrn.monitoramentotemperatura.model.Request;


public class Teste {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		TempServerInterface natalServer = (TempServerInterface) Naming.lookup("rmi://127.0.0.1:1098/TemperaturaNatalServer");
		TempClientInterface client = new TempClient();
		//natalServer.consultarTemperatura(client);
		//natalServer.consultarTemperatura(client);
		natalServer.request(new Request(Operacao.MAIOR, 0, client));
	}

}
