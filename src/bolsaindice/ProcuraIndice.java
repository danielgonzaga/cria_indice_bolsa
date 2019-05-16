package bolsaindice;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.io.RandomAccessFile;


class OrdenaNis implements Comparator<Indice> {
	public int compare(Indice e1, Indice e2) 
	{
		return e1.getNis().compareTo(e2.getNis());
	}
}

public class ProcuraIndice {
	
	public static void main(String args[]) throws Exception
	{
		String linha;
		String colunas[];
        long posicao;
        ArrayList<Indice> a = new ArrayList<Indice>(3000);
		RandomAccessFile f1 = new RandomAccessFile("bolsa.csv", "r");
		RandomAccessFile f2 = new RandomAccessFile("bolsa.ind", "rw");
		f1.readLine();
		while(f1.getFilePointer() < f1.length())
		{
			posicao = f1.getFilePointer();
			linha = f1.readLine();
			colunas = linha.split("\t");
            Indice e = new Indice();
            e.setNis(colunas[7]);
            e.setPosicao(posicao);
            a.add(e);
		}
		
		Collections.sort(a, new OrdenaNis());
		
		for(int i=0;i<a.size();i++) {
			a.get(i).escreve(f2);
		}
		
		
		System.out.print("Digite um NIS a ser buscado: ");
		Scanner sc = new Scanner(System.in);
		String nisDigit = sc.nextLine();
		sc.close();
		
		long inicio = 0;
		long fim = ((f2.length())/24);
		long meio = 0;
		boolean encontra = false;
		Indice e2 = new Indice();
		
		while(inicio <= fim && encontra == false) {
			meio = (inicio + fim)/2;
			f2.seek(meio*24);
			e2.le(f2);
			
			long nisArqConv = Long.parseLong(e2.getNis());
			long nisDigitConv = Long.parseLong(nisDigit);
			
			if(nisArqConv == nisDigitConv) {
				encontra = true;
			} else if(nisArqConv < nisDigitConv) {
				inicio = meio + 1;
			} else if(nisArqConv > nisDigitConv) {
				fim = meio - 1;
			}
		}
		
		if (encontra == true) {
			System.out.println("NIS encontrado!");
			f1.seek(e2.getPosicao());
			posicao = f1.getFilePointer();
			linha = f1.readLine();
			colunas = linha.split("\t");
			System.out.println("Índice: " + (f2.getFilePointer()/24));
			System.out.println("UF: " + colunas[0]);
			System.out.println("Código SIAFI Município: " + colunas[1]);
			System.out.println("Nome Município: " + colunas[2]);
			System.out.println("Código Função: " + colunas[3]);
			System.out.println("Código Subfunção: " + colunas[4]);
			System.out.println("Código Programa: " + colunas[5]);
			System.out.println("Código Ação: " + colunas[6]);
			System.out.println("NIS Favorecido: " + colunas[7]);
			System.out.println("Nome Favorecido: " + colunas[8]);
			System.out.println("Fonte-Finalidade: " + colunas[9]);
			System.out.println("Valor Parcela: " + colunas[10]);
			System.out.println("Mês Competência: " + colunas[11]);
			
		}
		
        f1.close();
        f2.close();
	}
}
