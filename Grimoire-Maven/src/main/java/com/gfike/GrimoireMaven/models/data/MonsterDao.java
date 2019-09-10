package com.gfike.GrimoireMaven.models.data;

import com.gfike.GrimoireMaven.models.Monster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MonsterDao extends CrudRepository<Monster, Integer> {


}
