package br.ufrn.monitoramentotemperatura.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.ufrn.monitoramentotemperatura.model.Message;

public class TempClient extends UnicastRemoteObject implements TempClientInterface {
	
	protected TempClient()  throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notificar(Message msg) throws RemoteException {
		System.out.println(msg);
	}

}
