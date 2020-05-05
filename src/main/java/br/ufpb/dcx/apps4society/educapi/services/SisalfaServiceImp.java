package br.ufpb.dcx.apps4society.educapi.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;
import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.repositories.ChallengeRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.ContextRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.UserRepository;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.DataAlreadyExistsException;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.DataNotFoundException;

public class SisalfaServiceImp implements SisalfaService{
	
	@Autowired
	UserRepository userRepositoty;
	
	@Autowired
	ContextRepository contextRepository;
	
	@Autowired
	ChallengeRepository challengeRepository;
	
	
	List<Context> users = new ArrayList<>();
	@Override
	public String addContext(Context context) throws RemoteException, DataAlreadyExistsException {
		contextRepository.save(context);
		return null;
	}

	@Override
	public List<Context> getAllContexts() throws RemoteException {
		
		return null;
	}

	@Override
	public List<Context> getAllContextsOfUser(String idUser) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context getContext(String idContext) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateContext(Context context) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContext(String idContext) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addChallenge(Challenge challenge) throws RemoteException, DataAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChallenge(Challenge newChallenge) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Challenge> getAllChallenges() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Challenge> getAllChallengesOfUser(String idUser) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Challenge getChallenge(String idChallenge) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Challenge> getChallengesByContext(String idContext) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteChallenge(String idChallenge) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addUser(User user) throws RemoteException, DataAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String idUser) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String idUser) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User newUser) throws RemoteException, DataNotFoundException {
		// TODO Auto-generated method stub
		
	}
	

}
