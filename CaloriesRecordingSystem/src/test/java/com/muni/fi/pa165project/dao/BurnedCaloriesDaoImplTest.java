/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.dao;

import com.muni.fi.pa165project.config.TestConfig;
import com.muni.fi.pa165project.entity.Activity;
import com.muni.fi.pa165project.entity.BurnedCalories;
import com.muni.fi.pa165project.enums.Difficulty;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author Radoslav Karlik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class BurnedCaloriesDaoImplTest {

	@Autowired
	private BurnedCaloriesDao bcDao;

	@Autowired
	private ActivityDao acDao;

	private BurnedCalories bc1;
	private BurnedCalories bc2;
	private BurnedCalories bc3;

	private Activity ac;
	
	@Transactional
	private void createDummyActivity() {
		this.ac = new Activity();
		this.ac.setName("Activity 1");
		this.ac.setDescription("test activity");
		this.acDao.create(ac);
	}
	
	@Before
	public void init() {
		createDummyActivity();

		this.bc1 = new BurnedCalories();
		this.bc1.setUpperWeightBoundary(100);
		this.bc1.setActivity(ac);
		this.bc1.setDifficulty(Difficulty.MEDIUM);
		
		this.bc2 = new BurnedCalories();
		this.bc2.setUpperWeightBoundary(200);
		this.bc2.setActivity(ac);
		this.bc2.setDifficulty(Difficulty.EASY);
		
		this.bc3 = new BurnedCalories();
		this.bc3.setUpperWeightBoundary(300);
		this.bc3.setActivity(ac);
		this.bc3.setDifficulty(Difficulty.HARD);

		this.bcDao.create(bc1);
		this.bcDao.create(bc2);
		this.bcDao.create(bc3);
	}

	@Test
	@Rollback(true)
	public void testCreate() {
		BurnedCalories bc = new BurnedCalories();

		bc.setUpperWeightBoundary(75);
		bc.setAmount(200);
		bc.setDifficulty(Difficulty.MEDIUM);
		bc.setActivity(ac);

		this.bcDao.create(bc);

		List<BurnedCalories> bcs = this.bcDao.findAll();
		Assert.assertTrue(bcs.size() == 4);
	}

	@Test
	@Rollback(true)
	public void testUpdate() {
		this.bc2.setUpperWeightBoundary(201);
		this.bcDao.update(bc2);

		BurnedCalories bc2FromDB = this.bcDao.findById(this.bc2.getId());

		Assert.assertEquals(201, bc2FromDB.getUpperWeightBoundary());
	}

	@Test
	@Rollback(true)
	public void testRemove() {
		int initialCount = this.bcDao.findAll().size();

		this.bcDao.delete(bc1);

		int finalCount = this.bcDao.findAll().size();

		Assert.assertEquals(initialCount, finalCount + 1);
	}

	@Test
	@Rollback(true)
	public void testFindAll() {
		List<BurnedCalories> bcs = this.bcDao.findAll();

		Assert.assertTrue(bcs.size() == 3);
	}

}
