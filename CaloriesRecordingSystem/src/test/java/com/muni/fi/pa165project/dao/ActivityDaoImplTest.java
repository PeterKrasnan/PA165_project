package com.muni.fi.pa165project.dao;

import com.muni.fi.pa165project.config.TestConfig;
import com.muni.fi.pa165project.entity.Activity;
import com.muni.fi.pa165project.enums.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Peter Krasnan
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ActivityDaoImplTest {

    @Autowired
    private ActivityDao activityDao;

    private Activity activity;

    @Before
    public void init() {
        activity = new Activity();
        activity.setDescription("description");
        activity.setName("Initial Activity");
        activity.setCategory(Category.AEROBICS);
        activityDao.create(activity);
    }

    @Test
    @Rollback(true)
    public void createTest() {
        Activity a = new Activity();

        a.setName("A Activity");
        a.setDescription("");

        this.activityDao.create(a);

        List<Activity> activities = activityDao.findAll();

        Assert.assertTrue("Activities is null.", activities != null);
        Assert.assertTrue("Activities is empty.", !activities.isEmpty());
        Assert.assertTrue("Incorrect number of results for activities.", activities.size() == 2);
        Assert.assertTrue("Activities do not contain newly created activity", activities.contains(a));
    }

    @Test
    @Rollback(true)
    public void updateTest() {
        Activity a = activityDao.findById(activity.getId());
        String desc = "Changed description";
        a.setDescription(desc);

        activityDao.update(a);

        Activity b = activityDao.findById(activity.getId());

        Assert.assertTrue("Activity is not updated correctly", b != null && b.getDescription().equals(desc));
    }

    @Test
    @Rollback(true)
    public void deleteTest() {
        int initialCount = activityDao.findAll().size();

        activityDao.delete(activity);

        int finalCount = activityDao.findAll().size();

        Assert.assertTrue("Activity was not deleted", (initialCount == (finalCount + 1)));
    }

    @Test
    @Rollback(true)
    public void findByIdTest() {
        Activity a = activityDao.findAll().get(0);
        Activity b = activityDao.findById(a.getId());

        Assert.assertTrue("Activity was not found correctly by id", b != null && a.equals(b));
    }

    @Test
    @Rollback(true)
    public void findAllTest() {
        List<Activity> activities = activityDao.findAll();

        Assert.assertTrue("Activities is null", activities != null);
        Assert.assertTrue("Activities is empty", !activities.isEmpty());
        Assert.assertTrue("Activities have incorrect number of results", activities.size() == 1);
    }

    @Test
    @Rollback(true)
    public void findByNameTest() {
        List<Activity> activities = activityDao.findByName("Activity");

        Assert.assertTrue("Activies is null", activities != null);
        Assert.assertTrue("Activities does not contain right activity", activities.contains(activity));
    }

    @Test
    @Rollback(true)
    public void findByCategoryTest() {
        List<Activity> activities = activityDao.findByCategory(Category.AEROBICS);

        Assert.assertTrue("Activities is null", activities != null);
        Assert.assertTrue("Activities are empty", !activities.isEmpty());
        Assert.assertTrue("Activities have incorrect number of results", activities.size() == 1);
    }


}
