package com.muni.fi.pa165project.service.facade;

import com.muni.fi.pa165project.dto.ActivityDTO;
import com.muni.fi.pa165project.dto.ActivityDetailDTO;
import com.muni.fi.pa165project.dto.BurnedCaloriesDTO;
import com.muni.fi.pa165project.dto.filters.ActivityFilterDTO;
import com.muni.fi.pa165project.entity.Activity;
import com.muni.fi.pa165project.entity.BurnedCalories;
import com.muni.fi.pa165project.entity.User;
import com.muni.fi.pa165project.enums.Category;
import com.muni.fi.pa165project.facade.ActivityFacade;
import com.muni.fi.pa165project.service.ActivityService;
import com.muni.fi.pa165project.service.BurnedCaloriesService;
import com.muni.fi.pa165project.service.UserService;
import com.muni.fi.pa165project.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
/**
 *
 * @author Radoslav Karlik
 */
@Service
@Transactional
public class ActivityFacadeImpl implements ActivityFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private BurnedCaloriesService burnedCaloriesService;

	@Autowired
	private UserService userService;

	@Override
	public Long createActivity(ActivityDTO activityDTO) {
		Activity activity = mapper.map(activityDTO, Activity.class);
		this.activityService.create(activity);
		return activity.getId();
	}

	@Override
	public void editActivity(ActivityDTO activityDTO) {
		Activity activity = mapper.map(activityDTO, Activity.class);
		this.activityService.update(activity);
	}

	@Override
	public void removeActivity(long id) {
		this.activityService.remove(id);
	}

	@Override
	public ActivityDetailDTO getActivityDetail(Long id) {
		Activity activity = this.activityService.findById(id);
		ActivityDetailDTO activityDTO = mapper.map(activity, ActivityDetailDTO.class);
		if (activityDTO != null
				&& activityDTO.getBurnedCalories() != null
				&& !activityDTO.getBurnedCalories().isEmpty())
			for (BurnedCaloriesDTO b : activityDTO.getBurnedCalories()) {
				b.setActivityId(id);
			}
		return activityDTO;
	}

	@Override
	public List<ActivityDTO> getAllActivities() {
		List<Activity> activities = this.activityService.getAllActivities();
		return mapper.mapToList(activities, ActivityDTO.class);
	}

	@Override
	public List<ActivityDTO> getActivities(ActivityFilterDTO activityFilter) {
		Collection<Category> categories = activityFilter.getCategories();
		List<Activity> filteredActivities = this.activityService.getFilteredActivities(categories);
		return mapper.mapToList(filteredActivities, ActivityDTO.class);
	}

	@Override
	public void addBurnedCalorie(BurnedCaloriesDTO burnedCaloriesDTO) {
		BurnedCalories bc = mapper.map(burnedCaloriesDTO, BurnedCalories.class);

		long activityId = burnedCaloriesDTO.getActivityId();

		Activity activity = this.activityService.findById(activityId);
		activity.addBurnedCaloriesItem(bc);
		this.activityService.update(activity);
	}

	@Override
	public void editBurnedCalorie(BurnedCaloriesDTO burnedCaloriesDTO) {
		BurnedCalories bc = mapper.map(burnedCaloriesDTO, BurnedCalories.class);
		this.burnedCaloriesService.updateBurnedCalories(bc);
	}

	@Override
	public void removeBurnedCalorie(BurnedCaloriesDTO burnedCaloriesDTO) {
		Activity activity = this.activityService.findById(burnedCaloriesDTO.getActivityId());
		activity.getBurnedCalories().removeIf(bc -> Objects.equals(bc.getId(), burnedCaloriesDTO.getId()));
		this.activityService.update(activity);
	}

	@Override
	public List<ActivityDTO> getActivitiesSortedByBurnedCalories(long userId) {
		User user  = this.userService.findById(userId);
		double weight = user.getWeight();
		
		Function<Long, Integer> fn = (activityId) -> this.burnedCaloriesService.getBurnedCaloriesPerHour(activityId, weight);

		List<Activity> sortedActivities = this.activityService.getActivitiesSortedByBurnedCalories(fn);
		List<ActivityDTO> sortedActivitiesDTO = mapper.mapToList(sortedActivities, ActivityDTO.class);
		
		return sortedActivitiesDTO;
	}
	
}
