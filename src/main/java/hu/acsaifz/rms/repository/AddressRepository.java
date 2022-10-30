package hu.acsaifz.rms.repository;

import hu.acsaifz.rms.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Long> {

}
