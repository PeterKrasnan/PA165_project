/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.service;

import com.muni.fi.pa165project.entity.Activity;
import java.util.List;

/**
 *
 * @author Radoslav Karlik
 */
public interface ActivityService {
	
	void create(Activity activity);
	
	Activity findById(long id);
	
	void update(Activity activity);

	List<Activity> getAllActivities();
}