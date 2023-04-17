package br.ufrn.monitoramentotemperatura.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.monitoramentotemperatura.model.Message;

public interface TempClientInterface extends Remote {
	public void notificar(Message msg) throws RemoteException;
}
