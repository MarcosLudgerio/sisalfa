package br.ufpb.dcx.apps4society.educapi.services.exceptions;

public class ObjectNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
