/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.facade;

import com.muni.fi.pa165project.dto.RecordDTO;
import java.util.List;

/**
 *
 * @author Radoslav Karlik
 */
public interface TrackingFacade {
	
	void createRecord(RecordDTO recordDto);
	
	List<RecordDTO> getAllRecords(long userId);
	
}