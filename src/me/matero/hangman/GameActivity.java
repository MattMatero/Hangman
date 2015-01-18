package me.matero.hangman;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

public class GameActivity extends Activity{
	private String[] words;
	private Random rand;
	private String currentWord;
	private LinearLayout wordLayout;
	private TextView[] charViews;
	private GridView letters;
	private LetterAdapter letterAdapt;
	private ImageView[] bodyParts;
	private int numParts = 6;
	private int currentPart;
	private int numChars;
	private int numCorrect;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Resources res = getResources();
		words = res.getStringArray(R.array.words);
		rand = new Random();
		currentWord = "";
		wordLayout = (LinearLayout)findViewById(R.id.word);
		letters = (GridView)findViewById(R.id.letters);
		loadBodyParts();
		
		playGame();
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			super.onBackPressed();
			return true;
		case R.id.help:
			showHelp("Help", "Guess the word by selecting the letters.\n6 chances until you lose!", "Ok");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void playGame(){
		hideBodyParts();
		String newWord = words[rand.nextInt(words.length)];
		while(newWord.equals(currentWord)){
			newWord = words[rand.nextInt(words.length)];
		}
		currentWord = newWord;
		
		charViews = new TextView[currentWord.length()];
		wordLayout.removeAllViews();
		
		//breaks down the word to single charactrer views and adds them to the layout
		for(int i = 0; i < currentWord.length(); i++){
			charViews[i] = new TextView(this);
			charViews[i].setText(""+currentWord.charAt(i));
			
			charViews[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			charViews[i].setGravity(Gravity.CENTER);
			charViews[i].setTextColor(Color.WHITE);
			charViews[i].setBackgroundResource(R.drawable.letter_bg);
			wordLayout.addView(charViews[i]);
		}
		letterAdapt = new LetterAdapter(this);
		letters.setAdapter(letterAdapt);
		currentPart = 0;
		numChars=currentWord.length();
		numCorrect=0;
	}
	
	private void hideBodyParts(){
		for(int i = 0; i < numParts; i++){
			bodyParts[i].setVisibility(View.INVISIBLE);
		}
	}
	
	private void loadBodyParts(){
		bodyParts = new ImageView[numParts];
		bodyParts[0] = (ImageView)findViewById(R.id.head);
		bodyParts[1] = (ImageView)findViewById(R.id.body);
		bodyParts[2] = (ImageView)findViewById(R.id.arm1);
		bodyParts[3] = (ImageView)findViewById(R.id.arm2);
		bodyParts[4] = (ImageView)findViewById(R.id.leg1);
		bodyParts[5] = (ImageView)findViewById(R.id.leg2);
	}
	
	public void letterPressed(View v){
		char letter = ((TextView)v).getText().toString().charAt(0);
		v.setEnabled(false);
		v.setBackgroundResource(R.drawable.letter_down);
	
		boolean correct = false;
		for(int i = 0; i < currentWord.length(); i++){
			if(currentWord.charAt(i) ==letter){
				correct = true;
				numCorrect++;
				charViews[i].setTextColor(Color.BLACK);
			}
		}
		
		if(correct){
			if(numCorrect == numChars){
				//win
				disableButtons();
				showDialog("Winner!","You Win!\nThe answer was: " + currentWord, "Play Again?", "Exit");		
			}
		}else if(currentPart < numParts && currentPart != 5){
			bodyParts[currentPart].setVisibility(View.VISIBLE);
			currentPart++;
		}else{
			bodyParts[currentPart].setVisibility(View.VISIBLE);
			disableButtons();
			showDialog("Loser!","You Lost!\nThe answer was: " + currentWord, "Play Again?", "Exit");
		}
	}
	
	private void disableButtons(){
		int numLetters = letters.getChildCount();
		for(int i = 0; i<numLetters; i++){
			letters.getChildAt(i).setEnabled(false);
		}
	}
	
	private void showDialog(String title,String msg, String pos, String neg){
		AlertDialog.Builder msgBuild = new AlertDialog.Builder(this);
		msgBuild.setTitle(title);
		msgBuild.setMessage(msg);
		
		msgBuild.setPositiveButton(pos, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					GameActivity.this.playGame();
				}
		});
	
		msgBuild.setNegativeButton(neg, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
					GameActivity.this.finish();
			}
		});
		
		msgBuild.show();
	}
	
	private void showHelp(String title,String msg, String pos){
		AlertDialog.Builder msgBuild = new AlertDialog.Builder(this);
		msgBuild.setTitle(title);
		msgBuild.setMessage(msg);
		final AlertDialog help = msgBuild.create();
		msgBuild.setPositiveButton(pos, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					help.dismiss();
				}
		});
	
	
		
		msgBuild.show();
	}
}
