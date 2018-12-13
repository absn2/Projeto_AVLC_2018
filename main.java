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
								matriz[auxLinha][auxColuna] = in.nextDouble() * 10;
							}
						}
						if (verificar(matriz)) {
							double[][] identidade = new double[tamanho][tamanho];
							for (int i = 0, j = 0; i < tamanho; i++, j++) {
								identidade[i][j] = 10;
							}
							matriz = subtrair(matriz, identidade);
							matriz = escalonar(matriz);
							matriz = escalonarFinal(matriz);
							String resultado = "";
							for (int i = 0; i < matriz.length;i++) {
								resultado += "\nSalario "+(i+1)+" : ";
								for (int j = i+1; j < matriz.length; j++) {
									if (j == matriz.length -1) {
										resultado += -1*matriz[i][j];
									} else {
										resultado += matriz[i][j];
									}
									
								}
							}
							System.out.println(resultado);


						} else {
							System.out.println("MATRIZ INVALIDA (SOMA DAS COLUNAS SAO DIFERENTE DE 1) \n");
						}

					} else {
						System.out.println("NUMERO INVALIDO\n");
					}
				}
			} else if (escolha == 2) {
				boolean parouAberto = false;
				while (!parouAberto) {
					System.out.println("---> 1: INICIAR PROCESSO");
					System.out.println("---> 0: VOLTAR\n");
					int escolhaAberto = in.nextInt();
					if (escolhaAberto == 0) {
						parouAberto = true;
					} else if (escolhaAberto == 1) {
						System.out.println("DIGITE O TAMANHO DA MATRIZ");
						int tamanho = in.nextInt();
						System.out.println("DIGITE A MATRIZ E A DEMANDA EXTERNA");
						double[][] matriz = new double[tamanho][tamanho];
						for (int auxLinha = 0; auxLinha < tamanho; auxLinha++) {
							for (int auxColuna = 0; auxColuna < tamanho; auxColuna++) {
								matriz[auxLinha][auxColuna] = in.nextDouble() * 10;
							}
						}
						double[] demanda = new double[tamanho];
						double[] produçao = new double[tamanho];
						for (int aux = 0; aux < tamanho; aux++) {
							demanda[aux] = in.nextDouble() * 10;
						}
						double[][] identidade = new double[tamanho][tamanho];
						for (int i = 0, j = 0; i < tamanho; i++, j++) {
							identidade[i][j] = 10;
						}
						matriz = subtrair(matriz, identidade);
						Matrix matrizI = new Matrix(matriz);
						matrizI = matrizI.inverse();

						for (int auxLinha = 0; auxLinha < tamanho; auxLinha++) {
							for (int auxColuna = 0; auxColuna < tamanho; auxColuna++) {
								produçao[auxLinha] += demanda[auxLinha] * matriz[auxLinha][auxColuna];
							}
						}
						for (int aux = 0; aux < tamanho; aux++) {
							System.out.println(produçao[aux]);
						}

					} else {
						System.out.println("NUMERO INVALIDO\n");
					}
				}

			} else {
				System.out.println("NUMERO INVALIDO\n");
			}
		}
	}
	
	private static double[][] escalonarFinal (double[][] matriz) {
		for (int i = 1; i < matriz.length; i++) {
			double[] matrizAux = new double [matriz.length];
			for (int iI = 0; iI < matriz.length; iI++) {
				matrizAux[iI] = matriz[i][iI];
			}
			for (int i2 = i; i2 < matriz.length; i2++) {
				double operador = matriz[i-1][i];
				double numero = matrizAux[i2];
				matrizAux[i2] = operador * numero;
			}
			for (int j = i; j < matriz.length; j++) {
				matriz[i-1][j] = matriz[i-1][j] - matrizAux[j];
			}
		}
		return matriz;
	}

	private static double[][] escalonar(double[][] subtrair) {
		for (int aux = 0; aux < subtrair.length; aux++) {
			for (int i = 0; i < subtrair.length; i++) {
				double aux2 = subtrair[i][aux];
				for (int j = 0; j < subtrair.length; j++) {
					if (aux <= i && subtrair[i][j] != 0) {
						subtrair[i][j] = subtrair[i][j] / aux2;
					}
				}
			}
			for (int i = aux; i < subtrair.length - 1; i++) {
				for (int j = 0; j < subtrair.length; j++) {
					subtrair[i + 1][j] = subtrair[i + 1][j] - subtrair[aux][j];
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
				if (auxLinha == matriz.length - 1 && soma != 10.0) {
					return false;
				}
			}
		}
		return true;
	}
}
