package de.guderlei.pubsub.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.configProfile;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.dsProfile;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.rawPaxRunnerOption;

import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import de.guderlei.pubsub.model.Subscriber;

@RunWith(JUnit4TestRunner.class)
public class StartupDsSubscriber {
	@Inject
	BundleContext bundleContext;
	
	@Configuration
	public Option[] configure() {
		return options(equinox(), dsProfile(), configProfile(), 
				rawPaxRunnerOption("http.proxyHost", "proxy"),
				rawPaxRunnerOption("http.proxyPort", "3128"),
				provision(
				bundle(new File("./../model/build/libs/model-0.0.1.jar").toURI().toString()),
				bundle(new File("./../subscribers/ds_subscriber/build/libs/ds_subscriber-0.0.1.jar").toURI().toString()),
				bundle(new File("./../lib/compile/jsr305-1.3.9.jar").toURI().toString()),
				bundle(new File("./../lib/compile/org.apache.felix.log-1.0.0.jar").toURI().toString())
		));
	}
	
	/**
	 * Checks whether one {@link Subscriber} is registered
	 */
	@Test
	public void one_subscriber_is_registered()throws InterruptedException{
		ServiceTracker tracker = new ServiceTracker(bundleContext, Subscriber.class.getName(), null);
		tracker.open();
		
		Subscriber srvc = (Subscriber) tracker.waitForService(3000);
		assertNotNull(srvc);
		assertEquals("de.guderlei.pubsub.subscriber.SimpleSubscriber", srvc.getClass().getName());
		tracker.close();
	}
	
}
