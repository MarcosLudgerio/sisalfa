package br.ufpb.dcx.apps4society.educapi.services;

import java.util.*;

import br.ufpb.dcx.apps4society.educapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;
import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.dto.ChallengeDTO;
import br.ufpb.dcx.apps4society.educapi.repositories.ChallengeRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.ContextRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.UserRepository;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

import javax.swing.text.html.Option;

@Service
public class ChallengeService {
	@Autowired
	private ChallengeRepository repo;
	@Autowired
	private UserService userService;
	@Autowired
	private ContextService contextService;
	
	public Challenge find(Long id) throws ObjectNotFoundException {
		Optional<Challenge> obgOptional = repo.findById(id);
		return obgOptional.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Challenge.class.getName()));
	}
	
	@Transactional
	public Challenge insert(Challenge obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Challenge update(Challenge obj) throws ObjectNotFoundException {
		Challenge newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Long id) throws ObjectNotFoundException{
		Challenge obj = find(id);
		for(Context x: obj.getContexts()) {
			x.getChallenges().remove(obj);
			contextService.insert(x);
		}
		repo.deleteById(id);

	}
	
	public List<Challenge> findAll(){
		return repo.findAll();
	}

	public List<Challenge> findChallengesByCreator(Long idCreator) throws ObjectNotFoundException{
		User creator = userService.find(idCreator);
		Optional<List<Challenge>> challengesByCreator = repo.findChallengesByCreator(creator);
		return challengesByCreator.orElseThrow(() -> new ObjectNotFoundException("Not exist challenges post of this creator" + Challenge.class.getName()));
	}
	
	public Page<Challenge> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Challenge fromDTO(ChallengeDTO objDto) {
		return new Challenge(objDto.getId(), objDto.getWord(), objDto.getCreator(), objDto.getSoundUrl(), objDto.getVideoUrl(), objDto.getImageUrl(), objDto.getContexts());
	}
	
	private void updateData(Challenge newObj, Challenge obj) {
		newObj.setWord(obj.getWord());
		newObj.setSoundUrl(obj.getSoundUrl());
		newObj.setVideoUrl(obj.getVideoUrl());
		newObj.setImageUrl(obj.getImageUrl());
		newObj.setContexts(obj.getContexts());
	}

}
