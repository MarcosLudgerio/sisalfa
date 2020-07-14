package br.ufpb.dcx.apps4society.educapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private UserService userService;

	public Context find(Long id) throws ObjectNotFoundException {
		Optional<Context> obgOptional = repo.findById(id);
		return obgOptional.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Context.class.getName()));
	}

	@Transactional
	public Context insert(Context obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Context update(Context obj, Long idCreator) throws ObjectNotFoundException {
            Context newObj = this.find(obj.getId());
        if(this.contextBelongsToThisUser(newObj.getId(), idCreator)){
            updateData(newObj, obj);
            return repo.save(newObj);
        }
        return null;
	}
	
	public void delete(Long id){
		repo.deleteById(id);
	}

	public boolean contextBelongsToThisUser(Long id, Long idCreator) throws ObjectNotFoundException{
		Context context = repo.getOne(id);
		if(context.getCreator() == null)
            throw new ObjectNotFoundException("Creator not found");
		Long idCreatorContext = context.getCreator().getId();
		return idCreatorContext == idCreator;
	}

	
	public List<Context> findAll(){
		return repo.findAll();
	}

	public List<Context> findContextsByCreator(Long idCreator) throws ObjectNotFoundException {
		User creator = userService.find(idCreator);
		Optional<List<Context>> contextsByCreatorDTO = repo.findContextsByCreator(creator);
		return contextsByCreatorDTO.orElseThrow(() -> new ObjectNotFoundException("Not register found for this user, please verify user id"));
	}
	
	public Page<Context> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Context fromDTO(ContextDTO objDto) {
		return new Context(objDto.getId(), objDto.getName(), objDto.getCreator(), objDto.getImageUrl(), objDto.getSoundUrl(), objDto.getVideoUrl());
	}
	
	public Context fromDTO(ContextNewDTO objDto) {
		return new Context(objDto.getId(), objDto.getName(), objDto.getCreator(), objDto.getImageUrl(), objDto.getSoundUrl(), objDto.getVideoUrl());
	}
	
	private void updateData(Context newObj, Context obj) {
		newObj.setName(obj.getName());
		newObj.setCreator(obj.getCreator());
		newObj.setImageUrl(obj.getImageUrl());
		newObj.setSoundUrl(obj.getSoundUrl());
		newObj.setVideoUrl(obj.getVideoUrl());
	}

}
