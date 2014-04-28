# Camel example project

## Camel quartz

The current project demonstrates how is it possible to access the quartz instance from a camel route.  Most of the cases a separated bundle with own quartz instance could be more robust solution but technically it's possible to access the already created quartz instance but it requires at least one camel route which uses quarts endpoint.

* ```timerToLog``` route is just a dummy log to create quartz instance for the camel component.
* ```schedule``` route demonstrates how is it possible to access the quartz instance and schedule task programatically
* ```scheduled``` the instance which will be scheduled programatically

## Build

To build this project use

```
mvn install
```

To deploy the project in OSGi. For example using Apache ServiceMix
or Apache Karaf. You can run the following command from its shell:

```
osgi:install -s mvn:hu.dpc.osgi/camel-demo/1.0-SNAPSHOT
```


