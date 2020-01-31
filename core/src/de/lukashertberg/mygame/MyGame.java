package de.lukashertberg.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	//Texture man;

	Texture[] man;
	Texture[] dizzyE;
	int manY;
	int manX;

	int dizzyY;
	int dizzyX;

	int manState = 0;
	int manStatedizzy = 0;
	int pause = 0;

	Texture dizzy;

	float gravity = 0.3f;
	float velocity = 0; // GEschwindigkeit
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
	/*
    Münzen und Bomben
     */
	ArrayList<Integer> coinbXs = new ArrayList<Integer>(); //X Position
	ArrayList<Integer> coinYs = new ArrayList<Integer>(); //Y Position

	ArrayList<Integer> bombXs = new ArrayList<Integer>(); //X Position
	ArrayList<Integer> bombYs = new ArrayList<Integer>(); //Y Position

	ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>(); // Körper zum Überladen
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>(); //Körper zum Überladen;
	Texture bomb;
	Texture coin;
	Random random;
	int coincount = 0;
	int bombcount = 0;

	Rectangle manRectangle;

	BitmapFont font;
	int score = 0;

	/*
    Spielstatus
     */
	int gamestate = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");

		man = new Texture[5];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");
		man[4] = new Texture("frame-5.png");

		dizzyE = new Texture[25];
		dizzyE[0] = new Texture("SpeedyGranularCrocodileskink-max-1mb-0.png");
		dizzyE[1] = new Texture("SpeedyGranularCrocodileskink-max-1mb-1.png");
		dizzyE[2] = new Texture("SpeedyGranularCrocodileskink-max-1mb-2.png");
		dizzyE[3] = new Texture("SpeedyGranularCrocodileskink-max-1mb-3.png");
		dizzyE[4] = new Texture("SpeedyGranularCrocodileskink-max-1mb-4.png");
		dizzyE[5] = new Texture("SpeedyGranularCrocodileskink-max-1mb-5.png");
		dizzyE[6] = new Texture("SpeedyGranularCrocodileskink-max-1mb-6.png");
		dizzyE[7] = new Texture("SpeedyGranularCrocodileskink-max-1mb-7.png");
		dizzyE[8] = new Texture("SpeedyGranularCrocodileskink-max-1mb-8.png");
		dizzyE[9] = new Texture("SpeedyGranularCrocodileskink-max-1mb-9.png");
		dizzyE[10] = new Texture("SpeedyGranularCrocodileskink-max-1mb-10.png");
		dizzyE[11] = new Texture("SpeedyGranularCrocodileskink-max-1mb-11.png");
		dizzyE[12] = new Texture("SpeedyGranularCrocodileskink-max-1mb-12.png");
		dizzyE[13] = new Texture("SpeedyGranularCrocodileskink-max-1mb-13.png");
		dizzyE[14] = new Texture("SpeedyGranularCrocodileskink-max-1mb-14.png");
		dizzyE[14] = new Texture("SpeedyGranularCrocodileskink-max-1mb-15.png");
		dizzyE[15] = new Texture("SpeedyGranularCrocodileskink-max-1mb-16.png");
		dizzyE[16] = new Texture("SpeedyGranularCrocodileskink-max-1mb-17.png");
		dizzyE[17] = new Texture("SpeedyGranularCrocodileskink-max-1mb-18.png");
		dizzyE[18] = new Texture("SpeedyGranularCrocodileskink-max-1mb-19.png");
		dizzyE[19] = new Texture("SpeedyGranularCrocodileskink-max-1mb-20.png");
		dizzyE[20] = new Texture("SpeedyGranularCrocodileskink-max-1mb-21.png");
		dizzyE[21] = new Texture("SpeedyGranularCrocodileskink-max-1mb-22.png");
		dizzyE[22] = new Texture("SpeedyGranularCrocodileskink-max-1mb-23.png");
		dizzyE[23] = new Texture("SpeedyGranularCrocodileskink-max-1mb-24.png");
		dizzyE[24] = new Texture("SpeedyGranularCrocodileskink-max-1mb-25.png");



		random = new Random();
		coin = new Texture("Sepp2.png");
		bomb = new Texture("monster.png");

		//man = new Texture("frame-1.png");
		manY = (Gdx.graphics.getWidth() - man[0].getHeight()) / 2;
		manX = (Gdx.graphics.getHeight() - man[0].getWidth()) / 3;

		dizzyY = (Gdx.graphics.getWidth() - man[0].getHeight()) / 2;
		dizzyX = (Gdx.graphics.getHeight() - man[0].getWidth()) / 3;




		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10.0f);




	}
	public void createCoin(){
		//Zufällige Höhe der Münze maximal wäre ganz oben
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int) height);
		//die Münze erscheint ganz rechts
		coinbXs.add(Gdx.graphics.getWidth());
		//wir bewegen die Münze in der render_methode
	}

	public void createBomb(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int) height);
		//die Münze erscheint ganz rechts
		bombXs.add(Gdx.graphics.getWidth());
	}

	@Override
	public void render() {
		batch.begin();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gamestate == 0) {
			if (Gdx.input.justTouched()) {
				gamestate = 1; //wir beginnen das Spiel
			}
		} else if (gamestate == 1) {
			//Spiel läuft

			if (coincount < 100) { //alle 100 Millisekunden soll eine Münze erscheinen
				coincount++;
			} else {
				coincount = 0; //wir fangen neu an zu zählen und erstellen eine Münze
				createCoin();
			}


			coinRectangles.clear(); // alle Coin-Körper löschen
			for (int i = 0; i < coinbXs.size(); i++) {
				batch.draw(coin, coinbXs.get(i), coinYs.get(i));
				coinbXs.set(i, coinbXs.get(i) - 4);
				coinRectangles.add(new Rectangle(coinbXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
			}
			if (bombcount < 250) {
				bombcount++;
			} else {
				bombcount = 0;
				createBomb();
			}

			bombRectangles.clear();
			for (int i = 0; i < bombXs.size(); i++) {
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i) - 4);
				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
			}
			if (Gdx.input.justTouched()) {
				velocity -= 10;
			}

			if (pause < 7) { //Nur bei jedem 8. Frame machen wir etwas
				pause++;
			} else {
				pause = 0;
				if (manState < man.length - 1) {
					manState++;
				} else {
					manState = 0;
				}
			}


			velocity += gravity;
			manY -= velocity;

			if (manY <= 0) {
				manY = 0;
			}
			if (manY >= Gdx.graphics.getHeight() - man[manState].getHeight()) {
				manY = Gdx.graphics.getHeight() - man[manState].getHeight();
				velocity = 5.0f;
			}
			manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[0].getWidth() / 2, manY, man[manState].getWidth(), man[manState].getHeight());

			for (int i = 0; i < coinRectangles.size(); i++) {
				if (Intersector.overlaps(manRectangle, coinRectangles.get(i))) {
					//Gdx.app.log("Coin", "Kollidiert");
					score++;
					coinRectangles.remove(i);
					coinbXs.remove(i);
					coinYs.remove(i);
					break;
				}
			}

			for (int i = 0; i < bombRectangles.size(); i++) {
				if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {
					//Gdx.app.log("Bomb", "Kollidiert");
					gamestate = 2;
				}

			}


		}else if (gamestate == 2){
			//Game over
			if (Gdx.input.justTouched()){
				score = 0;
				gamestate = 1;
				velocity = 0;
				coinbXs.clear();
				coinYs.clear();
				coinRectangles.clear();
				coincount = 0;
				bombXs.clear();
				bombYs.clear();
				bombRectangles.clear();
				bombcount = 0;
				manY = Gdx.graphics.getHeight()/2;
			}
		}
		if (gamestate ==2){
			if (pause < 7) { //Nur bei jedem 8. Frame machen wir etwas
				pause++;
			} else {
				pause = 0;
				if (manStatedizzy < dizzyE.length - 1) {
					manStatedizzy++;
				} else {
					manStatedizzy = 0;
				}
			}


			batch.draw(dizzyE[manStatedizzy], Gdx.graphics.getWidth()/2-dizzyE[0].getWidth()/3, dizzyY);
		}else {
			batch.draw(man[manState], Gdx.graphics.getWidth()/2-man[0].getWidth()/2, manY);
		}




		font.draw(batch, String.valueOf(score), 100, 200);


		//beende das Malen
		batch.end();

	}



		@Override
		public void dispose () {
			batch.dispose();
			background.dispose();
			/*for (int i = 0; i < man.length; i++) {
				man[i].dispose();}
			 */
			bomb.dispose();
			coin.dispose();
			font.dispose();



		}




}




