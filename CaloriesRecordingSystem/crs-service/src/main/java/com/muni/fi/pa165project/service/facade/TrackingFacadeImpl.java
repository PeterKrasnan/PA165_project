package com.muni.fi.pa165project.service.facade;

import com.muni.fi.pa165project.dto.RecordDTO;
import com.muni.fi.pa165project.dto.RecordDetailDTO;
import com.muni.fi.pa165project.dto.filters.RecordTimeFilterDTO;
import com.muni.fi.pa165project.entity.Activity;
import com.muni.fi.pa165project.entity.Record;
import com.muni.fi.pa165project.entity.User;
import com.muni.fi.pa165project.facade.TrackingFacade;
import com.muni.fi.pa165project.service.ActivityService;
import com.muni.fi.pa165project.service.BurnedCaloriesService;
import com.muni.fi.pa165project.service.RecordService;
import com.muni.fi.pa165project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Radoslav Karlik
 * @author Radim Podola
 * @author Lukáš Císar
 */
@Service
@Transactional
public class TrackingFacadeImpl extends FacadeBase implements TrackingFacade {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

	@Autowired
	private BurnedCaloriesService burnedCaloriesService;
	
    @Override
    public Long createRecord(RecordDetailDTO recordDetailDto) {
        Record record = super.map(recordDetailDto, Record.class);

        User user = this.userService.findById(recordDetailDto.getUserId());
        Activity activity = this.activityService.findById(recordDetailDto.getActivityId());

        record.setWeight(user.getWeight());
        record.setActivity(activity);
        record.setBurnedCalories(
            (int) this.burnedCaloriesService.calculateAmountOfCalories(
                    record.getActivity().getId(),
                    record.getDuration(),
                    record.getWeight()
            )
        );
        user.addRecord(record);
        //TODO fix this dirty hack
        recordService.update(record);
        List<Record> records = recordService.getFilteredRecords(user.getId(), record.getAtTime(), record.getAtTime());
        if (records.isEmpty())
            return null;
        return records.get(0).getId();
    }

    @Override
    public void editRecord(RecordDetailDTO recordDetailDto) {
        Record record = super.map(recordDetailDto, Record.class);

        record.setBurnedCalories(
			(int) this.burnedCaloriesService.calculateAmountOfCalories(
				recordDetailDto.getActivityId(),
				recordDetailDto.getDuration(),
				recordDetailDto.getWeight()
			)
		);
		
        this.recordService.update(record);
    }

    @Override
    public void removeRecord(long id) {
        this.recordService.remove(id);
    }

    @Override
    public RecordDetailDTO getRecord(long id) {
        Record record = this.recordService.getRecord(id);
        RecordDetailDTO recordDetailDto = super.map(record, RecordDetailDTO.class);
        recordDetailDto.setActivityName(record.getActivity().getName());
        recordDetailDto.setUserId(record.getUser().getId());
        
        return recordDetailDto;
    }

    @Override
    public  List<RecordDTO> getAllRecords(long userId) {
		List<Record> records = this.recordService.getAllRecordsOfUser(userId);
		
		return super.mapToList(records, RecordDTO.class);
	}

    @Override
    public List<RecordDTO> getLastNRecords(long userId, int count) {
        List<Record> records = this.recordService.getLastNRecordsOfUser(userId, count);
		
		return super.mapToList(records, RecordDTO.class);
    }

    @Override
    public List<RecordDTO> getFilteredRecords(RecordTimeFilterDTO timeFilter) {
		long userId = timeFilter.getUserId();
		List<Record> filteredRecords = this.recordService.
		    getFilteredRecords(userId, timeFilter.getFrom(), timeFilter.getTo());
    	
		return super.mapToList(filteredRecords, RecordDTO.class);
    }

    @Override
    public int getWeekProgressOfBurnedCalories(long userId) {
        return userService.getProgressOfWeeklyCaloriesGoal(userId);
    }

}
