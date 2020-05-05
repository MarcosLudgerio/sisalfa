package br.ufpb.dcx.apps4society.educapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.dto.ContextDTO;
import br.ufpb.dcx.apps4society.educapi.dto.ContextNewDTO;
import br.ufpb.dcx.apps4society.educapi.repositories.ContextRepository;
import br.ufpb.dcx.apps4society.educapi.repositories.UserRepository;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@Service
public class ContextService {
	
	@Autowired
	private ContextRepository repo;
	@Autowired
	private UserRepository userRepository;
	
	public Context find(Long id) throws ObjectNotFoundException {
		Optional<Context> obgOptional = repo.findById(id);
		return obgOptional.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Context.class.getName()));
	}
	
	@Transactional
	public Context insert(Context obj) {
		obj.setId(null);
		userRepository.save(obj.getCreator());
		return repo.save(obj);
	}
	
	public Context update(Context obj) throws ObjectNotFoundException {
		Context newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Long id) throws ObjectNotFoundException{
		find(id);
		repo.deleteById(id);
	}
	
	public List<Context> findAll(){
		return repo.findAll();
	}
	
	public Page<Context> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Context fromDTO(ContextDTO objDto) {
		return new Context(objDto.getId(), objDto.getName(), null, objDto.getImageUrl(), objDto.getSoundUrl(), objDto.getVideoUrl());
	}
	
	public Context fromDTO(ContextNewDTO objDto) {
		User user = new User(objDto.getUserId(), null, null, null);
		return new Context(objDto.getId(), objDto.getName(), user, objDto.getImageUrl(), objDto.getSoundUrl(), objDto.getVideoUrl());
	}
	
	private void updateData(Context newObj, Context obj) {
		newObj.setName(obj.getName());
		newObj.setImageUrl(obj.getImageUrl());
		newObj.setSoundUrl(obj.getSoundUrl());
		newObj.setVideoUrl(obj.getVideoUrl());
	}

}
