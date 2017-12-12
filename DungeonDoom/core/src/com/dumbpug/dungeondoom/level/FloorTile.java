package com.dumbpug.dungeondoom.level;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.dumbpug.dungeondoom.Constants;

public class FloorTile {
	
	/** The materials to use in creating the floor tile. */
	private Material material;
	
	/** The floor tile model. */
	private ModelInstance instance;
	
	/**
	 * Create a new instance of the FloorTile class.
	 * @param material
	 */
	public FloorTile(Material material) {
		Texture texture = new Texture("floor.jpg");
	    material        = new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
		
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		
		MeshPartBuilder meshBuilder;
		
		meshBuilder = modelBuilder.part("floor", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.rect(5f, 0f, 5f,   5f, 0f, 0f,  0f, 0f, 0f,  0f, 0f, 5f,  0f, 1f, 0f);
		
		Model model = modelBuilder.end();
		
		this.instance = new ModelInstance(model);
	}
	
	/**
	 * Set the cell position of this floor tile.
	 * @param x
	 * @param z
	 */
	public void setCellPosition(int x, int z) {
		this.instance.transform.setToTranslation(x * Constants.CELL_SIZE, 0f, z * Constants.CELL_SIZE);
	}
	
	/**
	 * Render the floor tile segment.
	 * @param modelBatch
	 * @param environment
	 */
	public void render(ModelBatch modelBatch, Environment environment) {
		modelBatch.render(instance, environment);
	}
}
