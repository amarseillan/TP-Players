package ar.edu.itba.pod.tp.player;

import java.rmi.RemoteException;
import java.util.List;

import ar.edu.itba.pod.tp.interfaces.Player;
import ar.edu.itba.pod.tp.interfaces.PlayerDownException;

public class PlayerWorker implements Runnable {

	List<Player> players;
	PlayerServer server;

	public PlayerWorker(List<Player> players, PlayerServer player) {
		this.players = players;
		this.server = player;
	}

	@Override
	public void run() {
		int plays = 0;
		int loop = server.total;
		System.out.println("Thread #" + Thread.currentThread().getId()
				+ " running loops:" + loop);
		do {
			int opt = (int) (java.lang.Math.random() * players.size());
			Player other = players.get(opt);
			try {
				if (other != null) {
					server.play("Jugada número " + plays, other);
				}
			} catch (PlayerDownException e) {
				if (e.getMessage().startsWith("[LOSER]")) {
					players.remove(other);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (++plays < loop);

	}
}