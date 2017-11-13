/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.service;

import com.muni.fi.pa165project.dao.RecordDao;
import com.muni.fi.pa165project.dao.UserDao;
import com.muni.fi.pa165project.entity.Record;
import com.muni.fi.pa165project.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Radoslav Karlik
 */
@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	private RecordDao recordDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void createRecord(Record record) {
		this.recordDao.create(record);
	}

	@Override
	public List<Record> getAllRecordsOfUser(long userId) {
		User user = this.userDao.findById(userId);
		return new ArrayList<>(user.getActivityRecords());
	}
	
}