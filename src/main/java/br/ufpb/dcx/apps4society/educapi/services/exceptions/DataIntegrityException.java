package br.ufpb.dcx.apps4society.educapi.services.exceptions;

public class DataIntegrityException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
