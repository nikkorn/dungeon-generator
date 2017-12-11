package com.dumbpug.dungeondoom;

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
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Wall {
	
	/** The materials to use in creating the wall sides. */
	private Material top, right, bottom, left;
	
	/** The wall model. */
	private ModelInstance instance;
	
	/**
	 * Create a new instance of the Wall class.
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public Wall(Material top, Material right, Material bottom, Material left) {
		
		Texture texture   = new Texture("wall.png");
	    Material material = new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
		
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		
		MeshPartBuilder meshBuilder;
		
		meshBuilder = modelBuilder.part("top", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.rect(0f, 0f, 0f,   0f, 5f, 0f,  5f, 5f, 0f,  5f, 0f, 0f,  0f, 1f, 0f);
		
		Node node = modelBuilder.node();
		node.translation.set(5, 0, 0);
		node.rotation.set(Vector3.Y, -90);
		
		meshBuilder = modelBuilder.part("right", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.rect(0f, 0f, 0f,   0f, 5f, 0f,  5f, 5f, 0f,  5f, 0f, 0f,  0f, 1f, 0f);
		
		node = modelBuilder.node();
		node.translation.set(5, 0, 5);
		node.rotation.set(Vector3.Y, -180);
		
		meshBuilder = modelBuilder.part("bottom", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.rect(0f, 0f, 0f,   0f, 5f, 0f,  5f, 5f, 0f,  5f, 0f, 0f,  0f, 1f, 0f);
		
		node = modelBuilder.node();
		node.translation.set(0, 0, 5);
		node.rotation.set(Vector3.Y, -270);
		
		meshBuilder = modelBuilder.part("left", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.rect(0f, 0f, 0f,   0f, 5f, 0f,  5f, 5f, 0f,  5f, 0f, 0f,  0f, 1f, 0f);
		
		Model model = modelBuilder.end();
		
		this.instance = new ModelInstance(model);
	}
	
	/**
	 * Render the wall segment.
	 * @param modelBatch
	 * @param environment
	 */
	public void render(ModelBatch modelBatch, Environment environment) {
		modelBatch.render(instance, environment);
	}
}
