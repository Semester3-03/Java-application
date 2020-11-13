package com.BrewMES.demo.Persistence;

import com.BrewMES.demo.model.Batch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatchRepository extends CrudRepository<Batch, UUID> {
}
