package br.ufrn.monitoramentotemperatura.rmi;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.ufrn.monitoramentotemperatura.model.Message;
import br.ufrn.monitoramentotemperatura.model.Request;

public class NovaIorqueServer extends UnicastRemoteObject implements TempServerInterface {

	private volatile ArrayList<Request> requests = new ArrayList<Request>();

	private static final String API_KEY = "5f2cabd73aed334a5d71ef400639bb8f";
	private static final String LAT = "40.6643";
	private static final String LON = "-73.9385";
	private static final String NAME = "Nova Iorque";

	protected NovaIorqueServer() throws RemoteException {
		super();

		new MonitorTemperatura().start();
	}

	@Override
	public void monitorarTemperatura(Request req) throws RemoteException {
		requests.add(req);

		System.out.println("Nova requisição registrada no servidor de " + NAME);
	}

	@Override
	public void consultarTemperatura(TempClientInterface cliente) throws RemoteException {
		Integer temp = null;
		HttpRequest request = HttpRequest
				.newBuilder().uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + LAT + "&lon="
						+ LON + "&appid=" + API_KEY + "&units=metric"))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());
			String json = response.body();

			JsonReader reader = Json.createReader(new StringReader(json));
			JsonObject jsonObject = reader.readObject();
			reader.close();

			JsonObject main = jsonObject.getJsonObject("main");
			temp = main.getJsonNumber("temp").intValue();
			System.out.println("Temperatura em " + NAME + ": " + temp);

		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

		if (temp != null) {
			cliente.notificar(new Message("Temperatura em " + NAME + ": " + temp));
		} else {
			cliente.notificar(new Message("Não foi possível consultar temperatura"));
		}
	}

	private class MonitorTemperatura extends Thread {

		public void run() {

			for (;;) {

				if (requests.size() > 0) {

					Integer temp = null;
					HttpRequest request = HttpRequest.newBuilder()
							.uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + LAT + "&lon=" + LON
									+ "&appid=" + API_KEY + "&units=metric"))
							.method("GET", HttpRequest.BodyPublishers.noBody()).build();
					try {
						HttpResponse<String> response = HttpClient.newHttpClient().send(request,
								HttpResponse.BodyHandlers.ofString());
						String json = response.body();

						JsonReader reader = Json.createReader(new StringReader(json));
						JsonObject jsonObject = reader.readObject();
						reader.close();

						JsonObject main = jsonObject.getJsonObject("main");
						temp = main.getJsonNumber("temp").intValue();
						System.out.println("Temperatura em " + NAME + ": " + temp);

					} catch (IOException | InterruptedException e1) {
						e1.printStackTrace();
					}

					if (temp != null) {
						for (Request r : requests) {

							String msg = "A temperatura em " + NAME;
							switch (r.getOperacao()) {
							case IGUAL:
								msg += " está igual a ";
								if (r.getX() == temp) {
									try {
										r.getCliente().notificar(new Message(msg + temp));
									} catch (RemoteException e) {
										e.printStackTrace();
									}
								}
								break;
							case MAIOR:
								msg += " está maior que ";
								if (r.getX() < temp) {
									try {
										r.getCliente().notificar(new Message(msg + r.getX()));
									} catch (RemoteException e) {
										e.printStackTrace();
									}
								}
								break;
							case MENOR:
								msg += " está menor que ";
								if (r.getX() > temp) {
									try {
										r.getCliente().notificar(new Message(msg + r.getX()));
									} catch (RemoteException e) {
										e.printStackTrace();
									}
								}
								break;
							case INTERVALO:
								msg += " está entre " + r.getX() + " e " + r.getY();
								int maiorTemp = Math.max(r.getX(), r.getY());
								int menorTemp = Math.min(r.getX(), r.getY());
								if (maiorTemp > temp && menorTemp < temp) {
									try {
										r.getCliente().notificar(new Message(msg));
									} catch (RemoteException e) {
										e.printStackTrace();
									}
								}
								break;
							}
						}
					}
				}
				try {
					Thread.sleep(15 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
