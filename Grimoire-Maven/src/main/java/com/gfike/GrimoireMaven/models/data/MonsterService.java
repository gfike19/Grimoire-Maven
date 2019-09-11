package com.gfike.GrimoireMaven.models.data;

import com.gfike.GrimoireMaven.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonsterService {

    @Autowired
    MonsterDao monsterDao;

    public List<Monster> listUsers() {
        List<Monster> mons = (List<Monster>) monsterDao.findAll();
        return mons;
    }
}
