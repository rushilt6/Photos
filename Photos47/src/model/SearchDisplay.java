package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SearchDisplay {
    private String caption;
    private Image img;
    public SearchDisplay(String caption, Image img){
        this.caption = caption;
        this.img = img;
    }
    public String getcaption(){
        return caption;
    }
    public Image getImage()
    {
        return img;
    }
    public ImageView getImageView()
    {
        return new ImageView(img);
    }
    public String toString(){
        return "Caption: "+caption+", Image" + this.getImageView();
    }
}
