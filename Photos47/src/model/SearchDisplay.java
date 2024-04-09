package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SearchDisplay {
    private String caption;
    private Image img;
    /**
     * A Necessary class to display photos. This is the constructor
     * @param caption Takes in the caption of a photo that fits searching criteria
     * @param img Takes in the image of the photo that fits searching criteria
     */
    public SearchDisplay(String caption, Image img){
        this.caption = caption;
        this.img = img;
    }
    /**
     * Gets the caption of the photo to display on search window
     * @return Returns the caption
     */
    public String getcaption(){
        return caption;
    }
    /**
     * Gets the image that is going to be displayed on search window
     * @return Returns the image
     */
    public Image getImage()
    {
        return img;
    }
    /**
     * Gets the imageView so it can be displayed properly in search window
     * @return Returns the imageView
     */
    public ImageView getImageView()
    {
        return new ImageView(img);
    }
    public String toString(){
        return "Caption: "+caption+", Image" + this.getImageView();
    }
}
