package modeloEconimicodeLeontief;

import java.util.*;

public class main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean parou = false;
		while (!parou) {
			System.out.println("----BEM VINDO AO PROJETO DO MODELO ECONOMICO DE LEONTIEF----");
			System.out.println("-----SELECIONE UMA DAS OPCOES A SEGUIR------");
			System.out.println("---> 1: MODELO FECHADO (INPUT-OUTPUT)");
			System.out.println("---> 2: MODELO ABERTO (DE PRODUCAO)");
			System.out.println("---> 0: FINALIZAR SESSAO\n");
			int escolha = in.nextInt();
			if (escolha == 0) {
				parou = true;
			} else if (escolha == 1) {
				boolean parouFechado = false;
				while (!parouFechado) {
					System.out.println("---> 1: INICIAR PROCESSO");
					System.out.println("---> 0: VOLTAR\n");
					int escolhaFechado = in.nextInt();
					if (escolhaFechado == 0) {
						parouFechado = true;
					} else if (escolhaFechado == 1) {
						System.out.println("DIGITE O TAMANHO DA MATRIZ");
						int tamanho = in.nextInt();
						double[][] matriz = new double[tamanho][tamanho];
						for (int auxLinha = 0; auxLinha < tamanho; auxLinha++) {
							for (int auxColuna = 0; auxColuna < tamanho; auxColuna++) {
								matriz[auxLinha][auxColuna] = in.nextDouble();
							}
						}
						if (verificar(matriz)) {
							double[][] identidade = new double[tamanho][tamanho];
							for (int i = 0, j = 0; i < tamanho; i++, j++) {
								identidade[i][j] = 1.0;
							}
							matriz = subtrair(matriz, identidade);
							matriz = escalonar(matriz);

						} else {
							System.out.println("MATRIZ INVALIDA (SOMA DAS COLUNAS SAO DIFERENTE DE 1) \n");
						}

					} else {
						System.out.println("NUMERO INVALIDO\n");
					}
				}
			} else if (escolha == 2) {

			} else {
				System.out.println("NUMERO INVALIDO\n");
			}
		}
	}

	private static double[][] escalonar(double[][] subtrair) {
		for (int aux = 0; aux < subtrair.length; aux++) {
			for (int i = 0; i < subtrair.length; i++) {
				double aux2 = subtrair[i][aux];
				for (int j = 0; j < subtrair.length; j++) {
					if (aux <= i) {
						subtrair[i][j] = subtrair[i][j] / aux2;
					}
				}
			}
			for (int i = aux; i < subtrair.length - 1; i++) {
				for (int j = 0; j < subtrair.length; j++) {
					subtrair[i + 1][j] = subtrair[i + 1][j] - subtrair[aux][j];
				}
			}
			
			if (aux > 0) {
				double[] auxEscalonar = new double [subtrair.length];
				for (int i = 0; i < subtrair.length; i++) {
					auxEscalonar[i] = subtrair[aux][i];
				}
				for (int i = 0; i < subtrair.length; i++) {
					auxEscalonar[i] = auxEscalonar[i]*subtrair[aux-1][aux];
				}
				for (int i = aux; i > 0; i--) {
					for (int j = aux; j < subtrair.length; j++) {
						subtrair[i-1][j] = subtrair[i-1][j] - auxEscalonar[j];
					}
				}
			}		
		}
		return subtrair;
	}

	private static double[][] subtrair(double[][] matriz, double[][] identidade) {
		double[][] resultado = new double[matriz.length][matriz.length];
		for (int auxLinha = 0; auxLinha < matriz.length; auxLinha++) {
			for (int auxColuna = 0; auxColuna < matriz.length; auxColuna++) {
				resultado[auxLinha][auxColuna] = identidade[auxLinha][auxColuna] - matriz[auxLinha][auxColuna];
			}
		}
		return resultado;
	}

	static boolean verificar(double[][] matriz) {
		for (int auxColuna = 0; auxColuna < matriz.length; auxColuna++) {
			double soma = 0;
			for (int auxLinha = 0; auxLinha < matriz.length; auxLinha++) {
				soma += matriz[auxLinha][auxColuna];
				if (auxLinha == matriz.length - 1 && soma != 1.0) {
					return false;
				}
			}
		}
		return true;
	}
}
