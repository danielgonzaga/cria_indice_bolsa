package bolsaindice;

import java.io.*;

public class Indice {
	private String nis;
	private long posicao;
	
	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public long getPosicao() {
		return posicao;
	}

	public void setPosicao(long posicao) {
		this.posicao = posicao;
	}

	void escreve(DataOutput out) throws IOException {
		out.writeUTF(this.nis);
		out.writeLong(this.posicao);
	}
	
	void le(DataInput din) throws IOException {
        this.nis = din.readUTF();
		this.posicao = din.readLong();
	}
}