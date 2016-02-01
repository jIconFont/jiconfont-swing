package jiconfont.swing;

import jiconfont.IconBuilder;
import jiconfont.IconCode;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright (c) 2016 jIconFont <BR>
 * <BR>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:<BR>
 * <BR>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.<BR>
 * <BR>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class IconBuilderSwing
  extends IconBuilder<IconBuilderSwing, Color, Float> {

  public static IconBuilderSwing newIcon(IconCode icon) {
    return new IconBuilderSwing(icon);
  }

  protected IconBuilderSwing(IconCode icon) {
    super(icon);
    setColor(Color.BLACK);
  }

  public final Image buildImage() {
    return buildImage(Character.toString(getIcon().getUnicode()));
  }

  public final Icon buildIcon() {
    return new ImageIcon(buildImage());
  }

  public final IconBuilderSwing setSize(int size) {
    return setSize((float) size);
  }

  private BufferedImage buildImage(String label) {
    Font font = buildFont();
    font = font.deriveFont(getSize());
    FontRenderContext frc = new FontRenderContext(null, true, true);
    TextLayout layout = new TextLayout(label, font, frc);
    Rectangle r = layout.getPixelBounds(null, 0, 0);
    BufferedImage bi =
      new BufferedImage(r.width + 2, r.height + 2, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = (Graphics2D) bi.getGraphics();
    g2d.setColor(getColor());
    layout.draw(g2d, 0, -r.y);
    g2d.dispose();
    return bi;
  }

  protected final Font buildFont() {
    try {
      InputStream is =
        IconBuilderSwing.class.getResourceAsStream(getIcon().getFontPath());
      return Font.createFont(Font.TRUETYPE_FONT, is);
    }
    catch (Exception ex) {
      Logger.getLogger(IconBuilderSwing.class.getName()).log(Level.SEVERE,
        "Font load failure", ex);
    }
    return null;
  }

  @Override
  protected Class<IconBuilderSwing> getIconFontClass() {
    return IconBuilderSwing.class;
  }
}