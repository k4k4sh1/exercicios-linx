package br.com.linx.exercicios;

import java.util.Scanner;
/**
 * 
 * @author  lucas
 * @since   11/01/2018
 * @version 1.0
 */
public class Exercicio2Linx {

	public static Scanner scanner = new Scanner(System.in);
	public static int erros = 0;

	public static void main(String[] args) {

		int opcao = -1;

		StringBuffer sistemaOpcoes = new StringBuffer();

		sistemaOpcoes.append("EXERCÍCIO 2 - LISTAS E VETORES").append("\n").append("Escolha as opções abaixo:")
				.append("\n").append("1 - Informar um número múltiplo de 10 entre 100 e 1000.").append("\n")
				.append("0 - Sair.");

		do {
			System.out.println(sistemaOpcoes.toString());
			System.out.println("Digite a opção: ");

			try {
				opcao = Integer.parseInt(scanner.nextLine());

				if (opcao == 0) {
					System.out.println("Sistema finalizado.");
				} else if (opcao == 1) {
					informarNumeroMultiploDe10();
				} else {
					System.err.println("Não existe uma opção com o número informado.");
				}

			} catch (NumberFormatException e) {
				System.err.println("Informe um número.");
			}

		} while (opcao != 0);

		scanner.close();
	}

	private static void informarNumeroMultiploDe10() {

		do {
			erros = 0;
			System.out.println("Digite o número: ");

			try {
				int numero = Integer.parseInt(scanner.nextLine());

				if (validarNumero(numero)) {

					int[] dados = new int[10];

					for (int i = 1; i <= dados.length; i++) {
						int valor = 0;

						if (i % 3 == 0) {
							valor = (int) Math.round(i * 0.3 * numero);
						} else {
							valor = (int) Math.round(i * 0.1 * numero);
						}
						dados[i - 1] = valor;
					}

					do {
						erros = 0;

						System.out.println(
								"Digite a letra \"p\" para saber a somatória dos valores pares ou \"i\" para a somatória dos valores ímpares.");

						String resposta = scanner.nextLine().trim();
						
						if (resposta.equals("p")) {
							mostrarResultado(dados, calcularTotalValores(dados, true));
						} else if (resposta.equals("i")) {
							mostrarResultado(dados, calcularTotalValores(dados, false));
						} else {
							erros++;
							System.err.println("Informe uma opção válida.");
						}
						
					} while (erros != 0);

				}

			} catch (NumberFormatException e) {
				erros++;
				System.err.println("Informe um número.");
			}
		} while (erros != 0);

	}

	private static boolean validarNumero(int numero) {
		if (numero < 100 || numero > 1000) {
			erros++;
			System.err.println("O número informado deve estar entre 100 e 1000.");
			return false;
		}

		if (numero % 10 != 0) {
			erros++;
			System.err.println("O número informado não é múltiplo de 10.");
			return false;
		}

		return true;
	}

	private static int calcularTotalValores(int[] dados, boolean par) {
		int total = 0;
		for (int i = 1; i <= dados.length; i++) {
			if (i % 2 == 0 && par) {
				total += dados[i - 1];
			} 
			
			if (i % 2 != 0 && !par) {
				total += dados[i - 1];
			}
		}
		return total;
	}
	
	private static void mostrarResultado(int[] dados, int total) {
		
		int[][] resultado = new int[2][10];
		
		for (int i = 0; i < dados.length; i++) {
			resultado[0][i] = i + 1;
			resultado[1][i] = dados[i];
		}
		
		System.out.println("");
		
		for (int i = 0; i < resultado.length; i++) {
			for (int j = 0; j < resultado[i].length; j++) {
				System.out.printf("%5s", resultado[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("Resultado da somatória: " + total);
		System.out.println("");
	}

}