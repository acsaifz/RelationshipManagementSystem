package hu.acsaifz.rms.repository;

import hu.acsaifz.rms.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Long> {

}
