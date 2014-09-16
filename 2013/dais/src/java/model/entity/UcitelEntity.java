package model.entity;


import core.model.entity.IEntity;
import java.util.Date;


public class UcitelEntity implements IEntity
{
	private int id;
	private String jmeno;
	private String prijmeni;
	private Date datumNarozeni;
	public enum POHLAVI {MUZ, ZENA};
	private POHLAVI pohlavi;


	@Override
	public void reset()
	{
		id            = 0;
		jmeno         = null;
		prijmeni      = null;
		datumNarozeni = null;
		pohlavi       = null;
	}


	public void setId(int id) { this.id = id; }
	public void setJmeno(String jmeno) { this.jmeno = jmeno; }
	public void setPrijmeni(String prijmeni) { this.prijmeni = prijmeni; }
	public void setDatumNarozeni(Date datumNarozeni) { this.datumNarozeni = datumNarozeni; }
	public void setPohlavi(POHLAVI pohlavi) { this.pohlavi = pohlavi; }


	public int     getId()    { return id; }
	public String  getJmeno() { return jmeno; }
	public String  getPrijmeni() { return prijmeni; }
	public Date    getDatumNarozeni() { return datumNarozeni; }
	public POHLAVI getPohlavi() { return pohlavi; }


}

