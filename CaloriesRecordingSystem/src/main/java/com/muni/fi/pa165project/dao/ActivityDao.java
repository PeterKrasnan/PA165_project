/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.dao;

import com.muni.fi.pa165project.entity.Activity;
import com.muni.fi.pa165project.enums.Category;
import java.util.List;

/**
 *
 * @author Radoslav Karlik
 */
public interface ActivityDao {
	/**
	 * Finds activity by id
	 * @param id
	 * @return 
	 */
	Activity findById(long id);
	
	/**
	 * Insert activity into database
	 * @param activity 
	 */
	void create(Activity activity);
	
	/**
	 * Update activity in database
	 * @param activity 
	 */
	void update(Activity activity);
	
	/**
	 * Delete activity from database
	 * @param activity 
	 */
	void delete(Activity activity);
	
	/**
	 * Returns all activities
	 * @return 
	 */
	List<Activity> findAll();
	
	/**
	 * Finds all activities containing text
	 * @param text searched text
	 * @return list of activities
	 */
	List<Activity> findByName(String text);
	
	/**
	 * Finds all activities from category
	 * @param difficulty
	 * @return 
	 */
	List<Activity> findByCategory(Category category);
	
}
