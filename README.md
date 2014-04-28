Example proje
=========================================




To build this project use

```
mvn install
```


To run the project you can execute the following Maven goal

```
mvn camel:run
```

To deploy the project in OSGi. For example using Apache ServiceMix
or Apache Karaf. You can run the following command from its shell:

```
osgi:install -s mvn:hu.dpc.osgi/camel-demo/1.0-SNAPSHOT
```

