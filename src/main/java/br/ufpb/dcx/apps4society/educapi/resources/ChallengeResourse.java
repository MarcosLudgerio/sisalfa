package br.ufpb.dcx.apps4society.educapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.ufpb.dcx.apps4society.educapi.domain.Context;
import br.ufpb.dcx.apps4society.educapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import br.ufpb.dcx.apps4society.educapi.dto.ChallengeNewDTO;
import br.ufpb.dcx.apps4society.educapi.services.ChallengeService;
import br.ufpb.dcx.apps4society.educapi.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value="/challenges")
public class ChallengeResourse {
	@Autowired
	private ChallengeService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Long id) throws ObjectNotFoundException {
		Challenge obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

    @RequestMapping(value = "/context", method = RequestMethod.GET)
    public ResponseEntity<?> findContexts(@RequestBody Context context) throws ObjectNotFoundException {
        List<Challenge> obj = service.findByContext(context);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> findContexts(@RequestBody User user) throws ObjectNotFoundException {
        List<Challenge> obj = service.findByUser(user);
        return ResponseEntity.ok().body(obj);
    }

	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ChallengeNewDTO objDto){
		Challenge obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ChallengeDTO objDto, @PathVariable Long id) throws ObjectNotFoundException{
		Challenge obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ObjectNotFoundException{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ChallengeDTO>> findAll(){
		List<Challenge> list = service.findAll();
		List<ChallengeDTO> listDto = list.stream().map(obj -> new ChallengeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ChallengeDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Challenge> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ChallengeDTO> listDto = list.map(obj -> new ChallengeDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

}
