package com.muni.fi.pa165project.dao;

import com.muni.fi.pa165project.entity.Record;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Radoslav Karlik
 */
@Repository
public class RecordDaoImpl implements RecordDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Record findById(long id) {
		return this.em.find(Record.class, id);
	}
	
	@Override
	public void create(Record record) {
		this.em.persist(record);
	}
	
	@Override
	public void update(Record record) {
		this.em.merge(record);
	}

	@Override
	public void delete(Record record) {
		boolean isManaged = this.em.contains(record);
		
		if (isManaged) {
			this.em.remove(record);
		} else {
			Record actual = this.findById(record.getId());

			if (actual != null) {
				this.em.remove(actual);
			}	
		}
	}
	
	@Override
	public List<Record> findAll() {
		return this.em.createQuery("SELECT r from Record r", 
				Record.class)
				.getResultList();
	}
	
	@Override
	public List<Record> findByTime(LocalDate time) {
	    return this.findByTime(time.atStartOfDay(), time.atTime(23, 59, 59, 0));
	}
	
	@Override
	public List<Record> findByTime(LocalDateTime from, LocalDateTime to) {
		return this.em.createQuery("SELECT r from Record r WHERE r.atTime BETWEEN :from AND :to",
				Record.class)
				.setParameter("from", from)
				.setParameter("to", to)
				.getResultList();
	}
	
}
