package me.matero.hangman;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LetterAdapter extends BaseAdapter{
	private String[] letters;
	private LayoutInflater letterInf;
	
	public LetterAdapter(Context c){
		letters= new String[26];
		for(int i = 0; i < 26; i++){
			letters[i]=""+(char)(i+'A');
		}
		letterInf = LayoutInflater.from(c);
	}
	
	@Override
	public int getCount() {
		return letters.length;
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button letterButton;
		if(convertView ==  null){
			letterButton = (Button)letterInf.inflate(R.layout.letter, parent,false);
		}else{
			letterButton = (Button)convertView;
		}
		
		letterButton.setText(letters[position]);
		letterButton.setTextColor(Color.WHITE);
		return letterButton;
	}

}
