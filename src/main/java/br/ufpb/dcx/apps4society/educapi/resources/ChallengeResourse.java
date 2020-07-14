package br.ufpb.dcx.apps4society.educapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.ufpb.dcx.apps4society.educapi.domain.User;
import br.ufpb.dcx.apps4society.educapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;
import br.ufpb.dcx.apps4society.educapi.dto.ChallengeDTO;
import br.ufpb.dcx.apps4society.educapi.services.ChallengeService;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value="/challenges")
public class ChallengeResourse {
	@Autowired
	private ChallengeService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<Challenge> find(@PathVariable Long id) throws ObjectNotFoundException {
		Challenge obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Void> insert(@Valid @RequestBody ChallengeDTO objDto){
		Challenge obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<Void> update(@Valid @RequestBody ChallengeDTO objDto, @PathVariable Long id) throws ObjectNotFoundException{
		Challenge obj = service.fromDTO(objDto);
		obj.setId(id);
		service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ObjectNotFoundException{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Challenge>> findAll(@RequestParam(name = "user", required = false) Long idCreator) throws ObjectNotFoundException{
		if (idCreator != null) {
			return ResponseEntity.ok().body(service.findChallengesByCreator(idCreator));
		}
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Page<ChallengeDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="word") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Challenge> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ChallengeDTO> listDto = list.map(obj -> new ChallengeDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

}
