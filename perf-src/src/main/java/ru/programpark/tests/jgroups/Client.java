package ru.programpark.tests.jgroups;

import org.jgroups.*;
import ru.programpark.tests.dao.ArrayNode;
import ru.programpark.tests.dao.Node;
import ru.programpark.tests.perf.TestHelper;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 31.01.2015.
 */
public class Client {
	JChannel channel;

	private volatile long recieved;
	private volatile long disconnectedAt;
	private volatile long connectedAt;
	private volatile boolean running;
	private volatile long sent;
	private Thread senderThread;


	public void start(String cfg) throws Exception {
		try {
			URL resource = Client.class.getClassLoader().getResource(Client.class.getPackage().getName().replace(".","/") + "/" +  cfg);
			System.out.println("resource = " + resource);
			channel = new JChannel(resource);
			channel.setDiscardOwnMessages(true);
//			setupProtocols(channel);
			channel.addChannelListener(new ChannelListener() {
				@Override
				public void channelConnected(Channel channel) {
					System.out.println("connected = " + channel);
				}

				@Override
				public void channelDisconnected(Channel channel) {
					System.out.println("disconnected = " + channel);
					stopSender();
				}

				@Override
				public void channelClosed(Channel channel) {
					System.out.println("closed = " + channel);
				}
			});

			channel.setReceiver(new ReceiverAdapter() {
				public void receive(Message msg) {
					received(msg);
				}

				@Override
				public void viewAccepted(View view) {
					System.out.println("view = " + view);
					if (view.size() > 1) {
						startSender();
					} else {
						if (senderThread != null) stopSender();
					}
				}


			});
			channel.connect("ChatCluster");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		new TestHelper(new int[]{10}, ArrayNode.class).initCache();
	}

	private void stopSender() {
		running = false;
		disconnectedAt = System.currentTimeMillis();
		try {
			senderThread.join(TimeUnit.SECONDS.toMillis(4));
		} catch (InterruptedException e) {
			System.out.println("stop sender exception = " + e);
		}
	}

	private void startSender() {
		senderThread = new Thread() {
			@Override
			public void run() {
				connectedAt = System.currentTimeMillis();
				while (isRunning()) {
					Node node = TestHelper.nextNode();
					final Ntf ntf = new Ntf(node, ArrayNode.FIELDS.DATE.ordinal());
					send(ntf);
				}
			}
		};
		running = true;
		senderThread.start();
	}

	private void received(Message msg) {
		recieved++;
	}

	private void initSession() {
		try {
			Message msg = new Message(null, null, new byte[]{(byte) 1});
			channel.send(msg);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void send(Ntf ntf) {
		try {
			Message msg = new Message(null, null, ntf.toBytes());
			channel.send(msg);
			sent++;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	private void stop() {
		running = false;
		channel.close();
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("jgroups.bind_addr", "127.0.0.1");
		System.setProperty("java.net.preferIPv4Stack", "true");
		new TestHelper(new int[]{10}, ArrayNode.class).initCache();
		final Client client = new Client();
		client.start(args[0]);
		client.initSession();
		Thread.sleep(TimeUnit.SECONDS.toMillis(30));
		client.stop();
		System.out.println("client = " + client);
		System.out.println("sender = " + client.senderThread.isAlive());
	}

	@Override
	public String toString() {
		disconnectedAt = (disconnectedAt == 0) ? System.currentTimeMillis() : disconnectedAt;
		long duration = disconnectedAt - connectedAt;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		double thrptRecieved = ((double) recieved) / seconds;
		return new StringBuilder().append("Client{").
				append("thrptRecieved=").
				append(thrptRecieved).
				append("msg/s, running=").
				append(running).
				append(", recieved=").
				append(recieved).
				append(", sent=").
				append(sent).
				append(", worktime=").
				append(seconds).
				append('}').toString();
	}

	public boolean isRunning() {
		return running;
	}

}
