package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LineActor extends Actor {
    private float x1, y1, x2, y2;
    private ShapeRenderer shapeRenderer;

    public LineActor(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end(); // must end batch before using ShapeRenderer

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // line color
        shapeRenderer.line(x1, y1, x2, y2);
        shapeRenderer.end();

        batch.begin(); // resume batch after drawing
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

}
