package com.superempires.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.superempires.game.objects.properties.Transform;

public abstract class GUITextElement extends GUIElement
{
	private GlyphLayout layout;
	private Label label;
	private String text;
	private Color color;
	private BitmapFont font;
	
	public GUITextElement(Transform transform, String text, GUI gui, BitmapFont font, Color color)
	{
		super(transform, gui);
		
		this.font = font;
		this.color = color;
		layout = new GlyphLayout(getFont(), text, color, getTransform().getWidth(), 1, false);

		label = new Label(text, new LabelStyle(getFont(), color));
		getTextBox().scaleBy(1);
		
		this.text = text;
	}
	
	public GUITextElement(float x, float y, String text, GUI gui, BitmapFont font, Color color)
	{
		super(null, gui);
		
		this.font = font;
		this.color = color;
		
		layout = new GlyphLayout(getFont(), text, color, 0, 1, false);
		
		label = new Label(text, new LabelStyle(getFont(), color));
		
		setTransform(new Transform(x, y, layout.width, layout.height));
	}
	
	@Override
	public void fontChangedEvent(BitmapFont font)
	{
		setLayout(new GlyphLayout(font, getText(), getColor(), getTransform().getWidth(), 1, false));
	}
	
	public BitmapFont getFont() { return font; }
	
	public Label getTextBox() { return label; }
	
	public GlyphLayout getLayout() { return layout; }
	public void setLayout(GlyphLayout layout) { this.layout = layout; }
	
	public String getText() { return text; }
	public void setText(String text) { this.text = text; getLayout().setText(getFont(), text); }

	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; layout = new GlyphLayout(getFont(), text, color, getTransform().getWidth(), 1, false); }
}
