package GAME_UNTRUST_PYRSKI_ROBERT_ROMANOVICH.gun;

public class Gun extends Thread {
	
	boolean is_fire=false;
	int countBullets=100;
	int stage=1;
	int damage=5;
	
	{
		start();
	}
	
	@Override
	public void run() {
		for (;true;) {
			try {
				Thread.sleep(stage);
			} catch (InterruptedException e) {}
		}
	}

}
