package hu.dpc.osgi;

import org.apache.camel.*;
import org.apache.camel.component.quartz2.QuartzConstants;
import org.apache.camel.component.quartz2.QuartzEndpoint;
import org.apache.camel.impl.DefaultMessage;
import org.quartz.*;

/**
 * Created by elek on 4/24/14.
 */
public class CamelExecutionJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        CamelContext camelContext = getCamelContext(context);
        String routeName = context.getMergedJobDataMap().getString("route");

        Route camelRoute = camelContext.getRoute(routeName);
        Endpoint endpoint = camelRoute.getEndpoint();
        Exchange exchange = endpoint.createExchange();
        Message message = new DefaultMessage();
        message.setBody("hello");
        exchange.setIn(message);
        try {
            endpoint.createProducer().process(exchange);
            System.out.println("Message has been sent to the context " + camelContext.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private CamelContext getCamelContext(JobExecutionContext context) throws JobExecutionException {
        SchedulerContext schedulerContext = getSchedulerContext(context);
        String camelContextName = context.getMergedJobDataMap().getString(QuartzConstants.QUARTZ_CAMEL_CONTEXT_NAME);
        CamelContext result = (CamelContext) schedulerContext.get(QuartzConstants.QUARTZ_CAMEL_CONTEXT + "-" + camelContextName);
        if (result == null) {
            throw new JobExecutionException("No CamelContext could be found with name: " + camelContextName);
        }
        return result;
    }


    private SchedulerContext getSchedulerContext(JobExecutionContext context) throws JobExecutionException {
        try {
            return context.getScheduler().getContext();
        } catch (SchedulerException e) {
            throw new JobExecutionException("Failed to obtain scheduler context for job " + context.getJobDetail().getKey());
        }
    }
}
