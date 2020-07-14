package br.ufpb.dcx.apps4society.educapi.repositories;

import br.ufpb.dcx.apps4society.educapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpb.dcx.apps4society.educapi.domain.Context;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContextRepository extends JpaRepository<Context, Long> {
    Optional<List<Context>> findContextsByCreator(User creator);
}
