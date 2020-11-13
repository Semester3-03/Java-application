package com.BrewMES.demo.Persistence;

import com.BrewMES.demo.model.Machine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MachineRepository extends CrudRepository<Machine, UUID> {
}
