package hu.dpc.osgi;

import org.apache.camel.Exchange;
import org.apache.camel.component.quartz2.CamelJob;
import org.apache.camel.component.quartz2.QuartzComponent;
import org.apache.camel.component.quartz2.QuartzConstants;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by elek on 4/24/14.
 */
public class Scheduling {

    public void schedule(Exchange exchange) {
        QuartzComponent quartzComponent = (QuartzComponent) exchange.getContext().getComponent("quartz2");
        Scheduler scheduler = quartzComponent.getScheduler();


        JobDetail job = newJob(CamelExecutionJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(QuartzConstants.QUARTZ_CAMEL_CONTEXT_NAME, exchange.getContext().getManagementName())
                .usingJobData("route", "scheduled")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        try {
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Scheduling");
    }
}
