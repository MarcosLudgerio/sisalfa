package br.ufpb.dcx.apps4society.educapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;
import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.dto.ChallengeDTO;
import br.ufpb.dcx.apps4society.educapi.dto.ChallengeNewDTO;
import br.ufpb.dcx.apps4society.educapi.repositories.ChallengeRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.ContextRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.UserRepository;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@Service
public class ChallengeService {
	
	@Autowired
	private ChallengeRepository repo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContextRepository contextRepository;
	
	public Challenge find(Long id) throws ObjectNotFoundException {
		Optional<Challenge> obgOptional = repo.findById(id);
		return obgOptional.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Challenge.class.getName()));
	}

	public List<Challenge> findByContext(Context context) throws ObjectNotFoundException {
        List<Challenge> challenges = repo.findByContexts(context);
        if(challenges.size() == 0)
            throw new ObjectNotFoundException("No challenge in this context");
        return challenges;
    }

    public List<Challenge> findByUser(User user) throws ObjectNotFoundException {
        List<Challenge> challenges = repo.findChallengesByCreator(user);
        if(challenges.size() == 0)
            throw new ObjectNotFoundException("Create not get challenge");
        return challenges;
    }


	@Transactional
	public Challenge insert(Challenge obj) {
		obj.setId(null);
		userRepository.save(obj.getCreator());
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
			contextRepository.save(x);
		}
		repo.deleteById(id);

	}
	
	public List<Challenge> findAll(){
		return repo.findAll();
	}
	
	public Page<Challenge> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Challenge fromDTO(ChallengeDTO objDto) {
		return new Challenge(objDto.getId(), objDto.getWord(), null, objDto.getSoundUrl(), objDto.getVideoUrl(), objDto.getImageUrl());
	}
	
	public Challenge fromDTO(ChallengeNewDTO objDto) {
		User user = new User(objDto.getId(), null, null, null);
		Challenge obj = new Challenge(objDto.getId(), objDto.getWord(), user, objDto.getSoundUrl(), objDto.getVideoUrl(), objDto.getImageUrl());
		return obj;
	}
	
	private void updateData(Challenge newObj, Challenge obj) {
		newObj.setWord(obj.getWord());
		newObj.setSoundUrl(obj.getSoundUrl());
		newObj.setVideoUrl(obj.getVideoUrl());
		newObj.setImageUrl(obj.getImageUrl());
	}

}
