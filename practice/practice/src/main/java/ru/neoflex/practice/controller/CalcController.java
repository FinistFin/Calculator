package ru.neoflex.practice.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class CalcController {
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/calculator", new CalculatorHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("Server is running on localhost:8080/calculator");
	}
	static class CalculatorHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String[] params = exchange.getRequestURI().getQuery().split("&");
			int a = Integer.parseInt(params[0].split("=")[1]);
			int b = Integer.parseInt(params[1].split("=")[1]);

			String response = "";
			if (exchange.getRequestURI().getPath().contains("add")) {
				response = Integer.toString(a + b);
			} else if (exchange.getRequestURI().getPath().contains("subtract")) {
				response = Integer.toString(a - b);
			}

			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}