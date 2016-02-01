package jiconfont.swing;

import jiconfont.IconBuilder;
import jiconfont.IconCode;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
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

  public final IconBuilderSwing setSize(int size) {
    return setSize((float) size);
  }

  public final Icon buildIcon() {
    return new ImageIcon(buildImage());
  }

  public final Image buildImage() {
    return buildImage(Character.toString(getIcon().getUnicode()));
  }

  private BufferedImage buildImage(String text) {
    Font font = buildFont();
    JLabel label = new JLabel(text);
    label.setFont(font);
    Dimension dim = label.getPreferredSize();
    label.setSize(dim);
    int width = dim.width;
    int height = dim.height;
    BufferedImage bufImage =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    label.paint(bufImage.createGraphics());
    return bufImage;
  }

  protected final Font buildFont() {
    try {
      Font font =
        Font.createFont(Font.TRUETYPE_FONT, getIcon().getFontInputStream());
      return font.deriveFont(getSize());
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