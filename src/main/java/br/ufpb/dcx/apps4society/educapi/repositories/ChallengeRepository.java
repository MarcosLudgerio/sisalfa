package br.ufpb.dcx.apps4society.educapi.repositories;

import br.ufpb.dcx.apps4society.educapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;;import java.util.List;
import java.util.Optional;


@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<List<Challenge>> findChallengesByCreator(User creator);
}
