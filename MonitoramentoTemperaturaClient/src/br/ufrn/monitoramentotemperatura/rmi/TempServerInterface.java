package br.ufrn.monitoramentotemperatura.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.monitoramentotemperatura.model.Request;


public interface TempServerInterface extends Remote {
	public void consultarTemperatura(TempClientInterface cliente) throws RemoteException;
	public void request(Request req) throws RemoteException;
}
