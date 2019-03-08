package com.dumbpug.dungeony;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.dungen.DunGen;
import com.dumbpug.dungeony.dungen.DunGenConfiguration;
import com.dumbpug.dungeony.dungen.DunGenPrinter;
import com.dumbpug.dungeony.dungen.Dungeon;
import com.dumbpug.dungeony.dungen.room.RoomResources;
import com.dumbpug.dungeony.dungen.room.RoomResourcesReader;

public class Dungeony extends ApplicationAdapter {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		RoomResources resources = RoomResourcesReader.getResources("rooms");
		
		System.out.println("ROOM COUNT         : " + resources.getRooms().size());
		System.out.println("ROOM GROUP COUNT   : " + resources.getRoomGroups().size());
		
		Dungeon dungeon = DunGen.generate("rooms", new DunGenConfiguration());
		
		System.out.println("DUNGEON TILE COUNT : " + dungeon.getTiles().size());
		
		DunGenPrinter.print(new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS").format(new Date()), "", dungeon);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
