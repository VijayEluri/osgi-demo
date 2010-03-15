package de.guderlei.pubsub.subscriber;

import org.osgi.service.log.LogService;

import de.guderlei.pubsub.model.Message;
import de.guderlei.pubsub.model.Subscriber;

/**
 * simple {@link Subscriber} implementation. The Service will be managed by DS / SCR
 * 
 * @author rguderlei
 *
 */
public class SimpleSubscriber implements Subscriber {
	
	private LogService log;
	
	/**sets the log service reference
	 * @param log
	 */
	public void setLog(LogService log){
		if(log != null){
			this.log = log;
		}
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void receive(Message message) {
		log.log(LogService.LOG_INFO, "SCR Subscriber: " + message.toString());
	}

}
